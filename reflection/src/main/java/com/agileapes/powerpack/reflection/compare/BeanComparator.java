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

import com.agileapes.powerpack.reflection.compare.result.ComparisonResult;
import com.agileapes.powerpack.reflection.exceptions.BeanComparisonException;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This interface will compare two Java beans of the same base type and return a result set indicating how and if
 * the two are different. In the comparison, the first item is always the scale against which the other item is weighed.
 */
public interface BeanComparator {

    /**
     * This will compare the two items using the {@link com.agileapes.powerpack.reflection.compare.impl.DefaultComparisonStrategy}
     * @param first     base item
     * @param second    second item
     * @param <E>       base type
     * @return the result of the comparison
     * @throws BeanComparisonException this exception will be raised if the causality of the comparison has been compromised;
     * i.e. the result of this comparison is dependent on the result of another comparison that cannot be determined prior to
     * the current comparison
     * @see #compare(Object, Object, ComparisonStrategy)
     * @see ComparisonStrategy#equals(Object, Object)
     */
    <E> Set<ComparisonResult> compare(E first, E second) throws BeanComparisonException;

    /**
     * This method will scrutinize the two given items, determining their exact differences (if any) and return them
     * in the form of a set of ComparisonResult items.
     *
     * @param first                 base item
     * @param second                second item
     * @param comparisonStrategy    the strategy used to compare to items prior to examining the details
     * @param <E>       base type
     * @return the result of the comparison
     * @throws BeanComparisonException this exception will be raised if the causality of the comparison has been compromised;
     * i.e. the result of this comparison is dependent on the result of another comparison that cannot be determined prior to
     * the current comparison
     * @see ComparisonStrategy#equals(Object, Object)
     */
    <E> Set<ComparisonResult> compare(E first, E second, ComparisonStrategy<Object> comparisonStrategy) throws BeanComparisonException;

}
