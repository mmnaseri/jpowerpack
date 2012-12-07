package com.agileapes.powerpack.tree.traverse.impl;

import com.agileapes.powerpack.tree.Node;
import com.agileapes.powerpack.tree.traverse.NodeTraverseCallback;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 14:59)
 */
public class PathConstructorTraverseCallback implements NodeTraverseCallback {

    private String path = "";

    @Override
    public void execute(Node node) {
        path = "/#" + node.getIndex() + path;
    }

    public String getPath() {
        return path;
    }
}
