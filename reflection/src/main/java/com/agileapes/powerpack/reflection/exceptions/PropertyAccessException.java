package com.agileapes.powerpack.reflection.exceptions;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:35)
 */
public abstract class PropertyAccessException extends BeansException {

    private final String propertyName;

    protected PropertyAccessException(String propertyName, Throwable cause) {
        super(cause);
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

}
