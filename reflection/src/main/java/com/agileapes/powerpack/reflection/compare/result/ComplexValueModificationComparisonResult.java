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
