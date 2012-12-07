package com.agileapes.powerpack.tree.exception;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 14:37)
 */
public class NoSuchNodeException extends Error {

    private static final long serialVersionUID = 2099883013565234502L;

    public NoSuchNodeException() {
    }

    public NoSuchNodeException(String message) {
        super(message);
    }

    public NoSuchNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchNodeException(Throwable cause) {
        super(cause);
    }
}
