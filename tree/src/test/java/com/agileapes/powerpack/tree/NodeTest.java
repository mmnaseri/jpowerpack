package com.agileapes.powerpack.tree;

import com.agileapes.powerpack.tree.impl.StandardNode;
import com.agileapes.powerpack.tree.impl.StandardNodeFactory;
import com.agileapes.powerpack.tree.traverse.NodeTraverseCallback;
import com.agileapes.powerpack.tree.traverse.TraverseOrder;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 17:43)
 */
public class NodeTest {

    @Test
    public void testMaintenanceOfSimpleTree() throws Exception {
        final StandardNodeFactory factory = new StandardNodeFactory();
        final StandardNode root = factory.getNode("a");
        root.appendChild(factory.getNode("b"));
        final StandardNode c = factory.getNode("c");
        root.appendChild(c);
        root.appendChild(factory.getNode("d"));
        c.appendChild(factory.getNode("e"));
        root.insertBefore(c, factory.getNode("f"));
        final StringBuilder builder = new StringBuilder();
        root.traverse(TraverseOrder.PRE_ORDER, new NodeTraverseCallback() {
            @Override
            public void execute(Node node) {
                builder.append(node.getName()).append("(");
            }
        });
        Assert.assertEquals(builder.toString(), "a(b(f(c(e(d(");
        Assert.assertEquals(root.getChildren().size(), 4);
        Assert.assertEquals(c.getChildren().size(), 1);
    }

    @Test
    public void testPath() throws Exception {
        final StandardNodeFactory factory = new StandardNodeFactory();
        final StandardNode node = factory.getNode("a");
        node.appendChild(factory.getNode("b"));
        final StandardNode c = factory.getNode("c");
        node.appendChild(c);
        final StandardNode d = factory.getNode("d");
        c.appendChild(d);
        final StandardNode e = factory.getNode("e");
        d.appendChild(e);
        d.insertBefore(e, factory.getNode(null));
        d.insertBefore(e, factory.getNode(null));
        d.insertBefore(e, factory.getNode(null));
        Assert.assertEquals(e.getPath(), "/#0/#1/#0/#3");
    }

    @Test
    public void testMatching() throws Exception {
        final StandardNodeFactory factory = new StandardNodeFactory();
        final StandardNode test = factory.getNode("test");
        test.setAttribute("first", "123");
        test.setAttribute("second", "/hello/");
        test.setAttribute("third", "{123}");
        Assert.assertTrue(test.matches("test"));
        Assert.assertTrue(test.matches("test[]"));
        Assert.assertTrue(test.matches("test[first]"));
        Assert.assertTrue(test.matches("test[first=123]"));
        Assert.assertTrue(test.matches("test[first=\\d+]"));
        Assert.assertTrue(test.matches("test[first=\\d+,second,th.*]"));
    }
}
