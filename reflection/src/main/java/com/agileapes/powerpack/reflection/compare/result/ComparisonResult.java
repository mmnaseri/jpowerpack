package com.agileapes.powerpack.reflection.compare.result;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This abstract class is the base class for the result of the comparison of a property's value between two items
 */
public abstract class ComparisonResult {

    private String property;

    public ComparisonResult(String property) {
        this.property = property;
    }

    protected abstract String getMnemonic();

    public String getProperty() {
        return property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComparisonResult that = (ComparisonResult) o;
        return !(property != null ? !property.equals(that.property) : that.property != null);

    }

    @Override
    public int hashCode() {
        return property != null ? property.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getMnemonic() + property;
    }
}
