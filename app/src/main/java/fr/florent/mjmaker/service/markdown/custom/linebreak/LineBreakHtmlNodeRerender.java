package fr.florent.mjmaker.service.markdown.custom.linebreak;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.Set;

import lombok.AllArgsConstructor;

public class LineBreakHtmlNodeRerender extends LineBreakNodeRerender {

    private final HtmlNodeRendererContext context;
    private final HtmlWriter html;

    public LineBreakHtmlNodeRerender(HtmlNodeRendererContext context) {
        this.context = context;
        this.html = context.getWriter();
    }

    @Override
    public void render(Node node) {
        html.tag("br/");
        html.tag("br/");
    }
}
