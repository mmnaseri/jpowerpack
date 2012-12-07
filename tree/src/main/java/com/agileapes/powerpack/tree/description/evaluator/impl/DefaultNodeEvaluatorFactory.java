package com.agileapes.powerpack.tree.description.evaluator.impl;

import com.agileapes.powerpack.tree.Node;
import com.agileapes.powerpack.tree.description.evaluator.NodeDescriptionEvaluator;
import com.agileapes.powerpack.tree.description.evaluator.NodeEvaluatorFactory;

import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 17:19)
 */
public class DefaultNodeEvaluatorFactory implements NodeEvaluatorFactory {
    @Override
    public void register(NodeDescriptionEvaluator evaluator) {
    }

    @Override
    public boolean evaluate(String name, Node node, List<String> parameters) {
        return false;
    }
}
