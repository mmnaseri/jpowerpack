package com.agileapes.powerpack.reflection.compare.result;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This indicates that the type of a property has been changed from one implementation to the next
 */
public class TypeModificationComparisonResult extends ModificationComparisonResult {

    private final Class<?> originalType;
    private final Class<?> newType;


    public TypeModificationComparisonResult(String property, Class<?> originalType, Class<?> newType) {
        super(property);
        this.originalType = originalType;
        this.newType = newType;
    }

    @Override
    protected String getMnemonic() {
        return "#";
    }

    public Class<?> getOriginalType() {
        return originalType;
    }

    public Class<?> getNewType() {
        return newType;
    }

}
