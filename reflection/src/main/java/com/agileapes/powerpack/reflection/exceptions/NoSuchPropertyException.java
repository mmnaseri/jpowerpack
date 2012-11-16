package com.agileapes.powerpack.reflection.exceptions;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:28)
 */
public class NoSuchPropertyException extends GeneralBeansException {

    private final String propertyName;
    private final Class<?> propertyType;

    public NoSuchPropertyException(String propertyName) {
        this(propertyName, null);
    }

    public NoSuchPropertyException(String propertyName, Class<?> propertyType) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Class<?> getPropertyType() {
        return propertyType;
    }

}
