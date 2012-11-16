package com.agileapes.powerpack.reflection.compare.impl;

import com.agileapes.powerpack.reflection.compare.ComparisonStrategy;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This comparison strategy relies on the given objects' own implementation of the equals method.
 */
public class DefaultComparisonStrategy implements ComparisonStrategy<Object> {

    @Override
    public boolean equals(Object first, Object second) {
        return first == null && second == null || first != null && second != null && first.equals(second);
    }

}
