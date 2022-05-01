package fr.florent.mjmaker.service.markdown.custom.linebreak;

import org.commonmark.node.Block;
import org.commonmark.parser.block.AbstractBlockParser;
import org.commonmark.parser.block.BlockContinue;
import org.commonmark.parser.block.ParserState;

public class LineBreakPaser extends AbstractBlockParser {

    @Override
    public Block getBlock() {
        return new LineBreak();
    }

    @Override
    public BlockContinue tryContinue(ParserState parserState) {
        return BlockContinue.none();
    }
}
