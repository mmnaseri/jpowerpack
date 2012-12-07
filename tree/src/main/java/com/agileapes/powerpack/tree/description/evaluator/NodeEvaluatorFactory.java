package com.agileapes.powerpack.tree.description.evaluator;

import com.agileapes.powerpack.tree.Node;

import java.util.List;

public interface NodeEvaluatorFactory {

    void register(NodeDescriptionEvaluator evaluator);

    boolean evaluate(String name, Node node, List<String> parameters);

}
