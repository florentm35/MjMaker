package fr.florent.mjmaker.service.markdown.custom.linebreak;

import org.commonmark.node.Block;
import org.commonmark.node.Visitor;

/**
 * Create a double line break
 */
public class LineBreak extends Block {

    @Override
    public void accept(Visitor visitor) {
        // not need
    }
}
