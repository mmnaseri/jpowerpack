package com.agileapes.powerpack.reflection.compare.result;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This indicates that an error prevented the process of a comparison to be carried out.
 */
public class FailedComparisonResult extends ComparisonResult {

    private final Throwable cause;

    public FailedComparisonResult(String property, Throwable cause) {
        super(property);
        this.cause = cause;
    }

    @Override
    protected String getMnemonic() {
        return "!";
    }

    public Throwable getCause() {
        return cause;
    }

}
