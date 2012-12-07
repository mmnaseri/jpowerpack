package com.agileapes.powerpack.tree;

import com.agileapes.powerpack.tree.traverse.NodeTraverseCallback;
import com.agileapes.powerpack.tree.traverse.TraverseOrder;

import java.util.List;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 12:20)
 */
public interface Node {

    void setNamespace(String namespace);

    String getNamespace();

    void setName(String name);

    String getName();

    String getQualifiedName();

    void setAttribute(String name, String value);

    boolean hasAttribute(String name);

    String getAttribute(String name);

    Set<String> getAttributeNames();

    void setValue(String value);

    String getValue();

    List<Node> getChildren();

    void appendChild(Node child);

    void insertBefore(Node origin, Node child);

    void insertAfter(Node origin, Node child);

    boolean hasChildren();

    Node getLastChild();

    Node getFirstChild();

    void removeChild(Node child);

    void setNextSibling(Node nextSibling);

    Node getNextSibling();

    void setPreviousSibling(Node previousSibling);

    Node getPreviousSibling();

    Node getParent();

    void setParent(Node parent);

    NodeType getType();

    void setType(NodeType type);

    int getIndex();

    void setIndex(int index);

    <C extends NodeTraverseCallback> C traverse(TraverseOrder order, C callback);

    String getPath();

    List<Node> find(String query);

    boolean matches(String description);

}
