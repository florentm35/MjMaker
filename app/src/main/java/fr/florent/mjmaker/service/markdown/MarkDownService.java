package fr.florent.mjmaker.service.markdown;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.ins.InsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MarkDownService {

    private static MarkDownService _instance;

    public static MarkDownService getInstance() {
        if (_instance == null) {
            _instance = new MarkDownService();
        }
        return _instance;
    }

    public String parseMarkDown(String text) {
        List<Extension> extensions = Arrays.asList(StrikethroughExtension.create(),
                InsExtension.create()
        );
        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();
        Node document = parser.parse(text);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
        return renderer.render(document);
    }

    public String processVariable(Map<String, String> values, String text) {

        StringBuilder str = new StringBuilder();

        Integer beginExpression = null;
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (currentChar == '$') {
                beginExpression = i;
            } else if (currentChar != '{' && beginExpression != null && i - beginExpression == 1) {
                // it's not an expression
                buffer.append("$")
                        .append(currentChar);
                beginExpression = null;
            } else if (currentChar == '{' && beginExpression != null && i - beginExpression == 1) {
                str.append(buffer);
                buffer.setLength(0);
            } else if (currentChar == '}' && beginExpression != null && i - beginExpression > 1) {
                String variable = buffer.toString().trim();
                String value = "";
                if(values.containsKey(variable)) {
                    value = values.get(variable);
                }
                str.append(value);
                buffer.setLength(0);
            } else {
                buffer.append(currentChar);
            }

        }
        return str.append(buffer).toString();

    }

}
