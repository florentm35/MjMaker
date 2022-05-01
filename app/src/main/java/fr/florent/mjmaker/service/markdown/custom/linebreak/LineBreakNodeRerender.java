package fr.florent.mjmaker.service.markdown.custom.linebreak;

import org.commonmark.node.Node;
import org.commonmark.renderer.NodeRenderer;

import java.util.Collections;
import java.util.Set;

abstract class LineBreakNodeRerender implements NodeRenderer {

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.<Class<? extends Node>>singleton(LineBreak.class);
    }
}
