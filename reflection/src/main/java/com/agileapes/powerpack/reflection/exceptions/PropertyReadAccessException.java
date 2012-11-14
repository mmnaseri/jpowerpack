package com.agileapes.powerpack.reflection.exceptions;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:36)
 */
public class PropertyReadAccessException extends PropertyAccessException {

    public PropertyReadAccessException(String propertyName, Throwable cause) {
        super(propertyName, cause);
    }

}
