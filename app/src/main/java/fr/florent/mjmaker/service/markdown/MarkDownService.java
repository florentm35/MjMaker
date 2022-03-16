package fr.florent.mjmaker.service.markdown;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.ins.InsExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

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

}
