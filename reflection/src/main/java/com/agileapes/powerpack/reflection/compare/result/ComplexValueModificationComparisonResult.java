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

package com.agileapes.powerpack.reflection.compare.result;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This comparison result means that a deeper look into the value of the property values will tell you
 * how different they are
 */
public class ComplexValueModificationComparisonResult extends ModificationComparisonResult {

    private final Set<ComparisonResult> comparisonDetails;

    public ComplexValueModificationComparisonResult(String property, Set<ComparisonResult> comparisonDetails) {
        super(property);
        this.comparisonDetails = comparisonDetails;
    }

    @Override
    protected String getMnemonic() {
        return "~";
    }

    public Set<ComparisonResult> getComparisonDetails() {
        return comparisonDetails;
    }

}
