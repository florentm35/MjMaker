package fr.florent.mjmaker.service.markdown.custom.linebreak;

import org.commonmark.parser.block.AbstractBlockParserFactory;
import org.commonmark.parser.block.BlockStart;
import org.commonmark.parser.block.MatchedBlockParser;
import org.commonmark.parser.block.ParserState;

public class LineBreakParserFactory extends AbstractBlockParserFactory {
    @Override
    public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
        int nextNonSpace = state.getNextNonSpaceIndex();
        CharSequence line = state.getLine().getContent();

        if (isLineBreak(line, nextNonSpace)) {
            return BlockStart.of(new LineBreakPaser()).atIndex(line.length());
        } else {
            return BlockStart.none();
        }
    }

    private boolean isLineBreak(CharSequence line, int index) {

        int charNumber = 0;
        for (int i = index; i < line.length(); i++) {
            if (line.charAt(i) == '$') {
                charNumber++;
            } else {
                return false;
            }
        }

        return charNumber >= 3;
    }
}
