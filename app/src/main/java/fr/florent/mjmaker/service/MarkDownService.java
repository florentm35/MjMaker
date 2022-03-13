package fr.florent.mjmaker.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

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
        Parser parser = Parser.builder().build();
        Node document = parser.parse(text);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

}
