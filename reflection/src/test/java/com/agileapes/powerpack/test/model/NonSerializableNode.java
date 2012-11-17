/*
 * Copyright (c) 2012 M. M. Naseri <m.m.naseri@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 */

package com.agileapes.powerpack.test.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/17, 14:46)
 */
public class NonSerializableNode {

    private NonSerializableNode[] nodes;
    private String title;

    public NonSerializableNode[] getNodes() {
        return nodes;
    }

    public void setNodes(NonSerializableNode[] nodes) {
        this.nodes = nodes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addNode(NonSerializableNode node) {
        if (getNodes() == null) {
            setNodes(new NonSerializableNode[0]);
        }
        final ArrayList<NonSerializableNode> list = new ArrayList<NonSerializableNode>();
        Collections.addAll(list, getNodes());
        list.add(node);
        setNodes(list.toArray(new NonSerializableNode[list.size()]));
    }

    public void addNode(String title) {
        final NonSerializableNode node = new NonSerializableNode();
        node.setTitle(title);
        addNode(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NonSerializableNode that = (NonSerializableNode) o;
        if (!this.title.equals(that.title)) {
            return false;
        }
        if (this.nodes == null && that.nodes == null) {
            return true;
        }
        if (this.nodes == null || that.nodes == null) {
            return false;
        }
        if (this.nodes.length != that.nodes.length) {
            return false;
        }
        for (int i = 0; i < nodes.length; i++) {
            if (!this.nodes[i].equals(that.nodes[i])) {
                return false;
            }
        }
        return true;
    }

}
