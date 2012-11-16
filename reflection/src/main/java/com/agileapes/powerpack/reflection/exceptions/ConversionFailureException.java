package com.agileapes.powerpack.reflection.exceptions;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/16, 18:40)
 */
public class ConversionFailureException extends GeneralBeansException {

    public ConversionFailureException() {
        super();
    }

    public ConversionFailureException(String message) {
        super(message);
    }

    public ConversionFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionFailureException(Throwable cause) {
        super(cause);
    }
}
