package fr.florent.mjmaker.service.markdown.custom.linebreak;

import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LineBreakExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {

    public static Extension create() {
        return new LineBreakExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new LineBreakParserFactory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(LineBreakHtmlNodeRerender::new);
    }
}
