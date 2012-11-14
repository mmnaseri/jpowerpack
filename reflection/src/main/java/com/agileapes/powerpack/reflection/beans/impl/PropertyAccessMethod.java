package com.agileapes.powerpack.reflection.beans.impl;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:39)
 */
public class PropertyAccessMethod {

    private final AccessType accessType;
    private final String accessorName;
    private final String propertyName;

    public PropertyAccessMethod(String propertyName, AccessType accessType, String accessorName) {
        this.accessType = accessType;
        this.accessorName = accessorName;
        this.propertyName = propertyName;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public String getAccessorName() {
        return accessorName;
    }

    public String getPropertyName() {
        return propertyName;
    }

}
