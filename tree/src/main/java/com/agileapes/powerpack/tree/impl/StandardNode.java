package com.agileapes.powerpack.tree.impl;

import com.agileapes.powerpack.tree.Node;
import com.agileapes.powerpack.tree.NodeType;
import com.agileapes.powerpack.tree.description.NodeDescription;
import com.agileapes.powerpack.tree.exception.NoSuchNodeException;
import com.agileapes.powerpack.tree.traverse.NodeTraverseCallback;
import com.agileapes.powerpack.tree.traverse.TraverseOrder;
import com.agileapes.powerpack.tree.traverse.impl.PathConstructorTraverseCallback;

import java.util.*;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 14:32)
 */
public class StandardNode extends AbstractSearchableNode {

    public static final String NAME_QUALIFIER = ":";
    private String namespace = "";
    private String name = "";
    private Map<String, String> attributes = new HashMap<String, String>();
    private String value;
    private List<Node> children = new ArrayList<Node>();
    private Node nextSibling;
    private Node previousSibling;
    private Node parent;
    private NodeType type;
    private int index;

    StandardNode(String name) {
        this.name = name;
    }

    @Override
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getQualifiedName() {
        return getNamespace() + NAME_QUALIFIER + getName();
    }

    @Override
    public void setAttribute(String name, String value) {
        attributes.put(name, value);
    }

    @Override
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    @Override
    public String getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    private void initializeChild(Node child) {
        if (child == null) {
            throw new NullPointerException();
        }
        child.setParent(this);
        child.setIndex(0);
        child.setNextSibling(null);
        child.setPreviousSibling(null);
    }

    private void fixIndexes() {
        for (int i = 0; i < children.size(); i++) {
            Node node = children.get(i);
            node.setIndex(i);
        }
    }

    @Override
    public void appendChild(Node child) {
        if (!hasChildren()) {
            initializeChild(child);
            children.add(child);
        } else {
            insertAfter(getLastChild(), child);
        }
    }

    @Override
    public void insertBefore(Node origin, Node child) {
        final int index = children.indexOf(origin);
        if (index == -1) {
            throw new NoSuchNodeException();
        }
        initializeChild(child);
        child.setPreviousSibling(origin.getPreviousSibling());
        if (origin.getPreviousSibling() != null) {
            origin.getPreviousSibling().setNextSibling(child);
        }
        child.setNextSibling(origin);
        origin.setPreviousSibling(child);
        children.add(index, child);
        fixIndexes();
    }

    @Override
    public void insertAfter(Node origin, Node child) {
        final int index = children.indexOf(origin);
        if (index == -1) {
            throw new NoSuchNodeException();
        }
        initializeChild(child);
        child.setNextSibling(origin.getNextSibling());
        if (origin.getNextSibling() != null) {
            origin.getNextSibling().setPreviousSibling(child);
        }
        child.setPreviousSibling(origin);
        origin.setNextSibling(child);
        children.add(index + 1, child);
        fixIndexes();
    }

    @Override
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    @Override
    public Node getLastChild() {
        if (!hasChildren()) {
            return null;
        }
        return children.get(children.size() - 1);
    }

    @Override
    public Node getFirstChild() {
        if (!hasChildren()) {
            return null;
        }
        return children.get(0);
    }

    @Override
    public void removeChild(Node child) {
        if (!children.remove(child)) {
            throw new NoSuchNodeException();
        }
        fixIndexes();
    }

    @Override
    public void setNextSibling(Node nextSibling) {
        this.nextSibling = nextSibling;
    }

    @Override
    public Node getNextSibling() {
        return nextSibling;
    }

    @Override
    public void setPreviousSibling(Node previousSibling) {
        this.previousSibling = previousSibling;
    }

    @Override
    public Node getPreviousSibling() {
        return previousSibling;
    }

    @Override
    public Node getParent() {
        return parent;
    }

    @Override
    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public <C extends NodeTraverseCallback> C traverse(TraverseOrder order, C callback) {
        if (!TraverseOrder.UPWARDS.equals(order)) {
            if (TraverseOrder.PRE_ORDER.equals(order)) {
                callback.execute(this);
            }
            for (Node child : children) {
                child.traverse(order, callback);
            }
            if (TraverseOrder.POST_ORDER.equals(order)) {
                callback.execute(this);
            }
        } else {
            callback.execute(this);
            if (parent != null) {
                parent.traverse(order, callback);
            }
        }
        return callback;
    }

    @Override
    public String getPath() {
        return traverse(TraverseOrder.UPWARDS, new PathConstructorTraverseCallback()).getPath();
    }

    @Override
    public boolean matches(String description) {
        return NodeDescription.compile(description).test(this);
    }

    @Override
    public void setType(NodeType type) {
        this.type = type;
    }

}
