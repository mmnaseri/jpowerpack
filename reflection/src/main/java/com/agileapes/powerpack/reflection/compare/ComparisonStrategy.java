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

package com.agileapes.powerpack.reflection.compare;

import com.agileapes.powerpack.reflection.compare.impl.DefaultComparisonStrategy;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * The ComparisonStrategy is supposed to create a way for the developers to determine if and when two items should be
 * declared identical (or similar)
 */
public interface ComparisonStrategy<E> {

    /**
     * This is the default comparison strategy used by most classes in this framework
     */
    public static final ComparisonStrategy<Object> DEFAULT = new DefaultComparisonStrategy();

    /**
     * This will determine if two items of the given kind can be compared, and if so, whether they are similar
     * @param first     the first item
     * @param second    the second item
     * @return {@link true} in case the items are to be considered identical. Note that in most cases, receiving a
     * {@link false} from this method would not mean that the items are considered unequal. It would simply mean that
     * the two items cannot be considered equals without any further examination.
     */
    boolean equals(E first, E second);

}
