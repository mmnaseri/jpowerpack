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

package com.agileapes.powerpack.string.exception;

import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 17:54)
 */
public class MissingExpectedTokenException extends DocumentReaderError {

    private static final long serialVersionUID = -5189141979630615395L;
    private final String expectedPattern;

    public MissingExpectedTokenException(Pattern expectedPattern) {
        this("Expected token matching (" + expectedPattern + ") could not be found", expectedPattern);
    }

    public MissingExpectedTokenException(String message, Pattern expectedPattern) {
        super(message);
        this.expectedPattern = expectedPattern.toString();
    }

    public MissingExpectedTokenException(String expectedPattern) {
        this("Expected token matching (" + expectedPattern + ") could not be found", expectedPattern);
    }

    public MissingExpectedTokenException(String message, String expectedPattern) {
        super(message);
        this.expectedPattern = expectedPattern;
    }

    public String  getExpectedPattern() {
        return expectedPattern;
    }
}
