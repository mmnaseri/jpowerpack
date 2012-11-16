package com.agileapes.powerpack.reflection.compare.result;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This comparison indicates that a property of the original bean is not present or accessible using the current
 * accessor in the second bean
 */
public class PropertyRemovalComparisonResult extends ComparisonResult {

    public PropertyRemovalComparisonResult(String property) {
        super(property);
    }

    @Override
    protected String getMnemonic() {
        return "-";
    }

}
