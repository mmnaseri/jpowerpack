package com.agileapes.powerpack.reflection.exceptions;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:28)
 */
public class GeneralBeansException extends Error {

    public GeneralBeansException() {
    }

    public GeneralBeansException(String message) {
        super(message);
    }

    public GeneralBeansException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralBeansException(Throwable cause) {
        super(cause);
    }
}
