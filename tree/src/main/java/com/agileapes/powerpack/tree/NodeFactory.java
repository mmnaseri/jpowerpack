package com.agileapes.powerpack.tree;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 17:42)
 */
public interface NodeFactory<N extends Node> {

    N getNode(String name);

}
