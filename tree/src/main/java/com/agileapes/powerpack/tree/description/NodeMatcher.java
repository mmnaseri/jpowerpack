package com.agileapes.powerpack.tree.description;

import com.agileapes.powerpack.tree.Node;
import com.agileapes.powerpack.tree.description.evaluator.NodeEvaluatorFactory;
import com.agileapes.powerpack.tree.description.evaluator.impl.DefaultNodeEvaluatorFactory;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 17:18)
 */
public class NodeMatcher {

    private final NodeDescription description;
    private final NodeEvaluatorFactory factory;

    /**
     * This constructor configures this instance of the matcher for the given {@link NodeDescription}
     * and {@link NodeEvaluatorFactory} instances.
     * @param description    the compiled description
     * @param factory        the evaluation factory. If set to <code>null</code>, the default
     *                       evaluation factory provided by the framework will be used. At the
     *                       moment, that means {@link DefaultNodeEvaluatorFactory}
     */
    NodeMatcher(NodeDescription description, NodeEvaluatorFactory factory) {
        if (factory == null) {
            factory = new DefaultNodeEvaluatorFactory();
        }
        this.description = description;
        this.factory = factory;
    }

    /**
     * This method will test the given node against the specified description and
     * evaluator factory
     * @param node    the node
     * @return <code>true</code> if the description matches that of the node
     */
    public boolean test(Node node) {
        if (!node.getName().matches(description.getName())) {
            return false;
        }
        if (description.getIndex() != -1 && node.getIndex() != description.getIndex()) {
            return false;
        }
        for (AttributeDescriptor descriptor : description.getAttributes()) {
            boolean found = false;
            for (String name : node.getAttributeNames()) {
                if (name.matches(descriptor.getName()) && node.getAttribute(name).matches(descriptor.getValue())) {
                    found = true;
                }
            }
            if (!found) {
                return false;
            }
        }
        for (EvaluatorDescriptor descriptor : description.getEvaluators()) {
            if (!factory.evaluate(descriptor.getName(), node, descriptor.getParameters())) {
                return false;
            }
        }
        return true;
    }

}
