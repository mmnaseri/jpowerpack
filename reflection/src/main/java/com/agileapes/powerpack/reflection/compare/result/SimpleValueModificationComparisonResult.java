package com.agileapes.powerpack.reflection.compare.result;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This indicates that a simpler modification has resulted in the two beans being different
 */
public class SimpleValueModificationComparisonResult extends ModificationComparisonResult {

    private final Object first;
    private final Object second;

    public SimpleValueModificationComparisonResult(String property, Object first, Object second) {
        super(property);
        this.first = first;
        this.second = second;
    }

    @Override
    protected String getMnemonic() {
        return "~";
    }

    public Object getFirst() {
        return first;
    }

    public Object getSecond() {
        return second;
    }
}
