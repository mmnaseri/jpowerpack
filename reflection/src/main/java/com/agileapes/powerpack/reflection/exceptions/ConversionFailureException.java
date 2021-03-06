/*
 * Copyright (c) 2012 M. M. Naseri <m.m.naseri@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 */

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
