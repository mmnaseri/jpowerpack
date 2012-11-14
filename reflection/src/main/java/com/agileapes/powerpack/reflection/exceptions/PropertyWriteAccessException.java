package com.agileapes.powerpack.reflection.exceptions;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:35)
 */
public class PropertyWriteAccessException extends PropertyAccessException {

    public PropertyWriteAccessException(String propertyName, Throwable cause) {
        super(propertyName, cause);
    }

}
