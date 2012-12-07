package com.agileapes.powerpack.tree.impl;

import com.agileapes.powerpack.tree.NodeFactory;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 17:42)
 */
public class StandardNodeFactory implements NodeFactory<StandardNode> {

    @Override
    public StandardNode getNode(String name) {
        return new StandardNode(name);
    }

}
