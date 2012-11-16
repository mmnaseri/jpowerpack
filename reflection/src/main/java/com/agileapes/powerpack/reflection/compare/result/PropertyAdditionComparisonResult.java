package com.agileapes.powerpack.reflection.compare.result;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This comparison indicates that the second bean has an additional property (maybe due to extension) that
 * the first bean lacked
 */
public class PropertyAdditionComparisonResult extends ComparisonResult {

    public PropertyAdditionComparisonResult(String property) {
        super(property);
    }

    @Override
    protected String getMnemonic() {
        return "+";
    }

}
