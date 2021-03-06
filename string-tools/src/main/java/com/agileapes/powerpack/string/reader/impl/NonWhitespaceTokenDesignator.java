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

package com.agileapes.powerpack.string.reader.impl;

import com.agileapes.powerpack.string.reader.TokenDescriptor;

import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 18:44)
 */
public class NonWhitespaceTokenDesignator extends PatternTokenDesignator {

    public NonWhitespaceTokenDesignator() {
        super(Pattern.compile("\\S+"));
    }

    @Override
    public TokenDescriptor getToken(String string) {
        int whitespaces = 0;
        while (string.length() > 0 && Character.isWhitespace(string.charAt(0))) {
            string = string.substring(1);
            whitespaces ++;
        }
        final TokenDescriptor token = super.getToken(string);
        if (token != null) {
            return new SimpleTokenDescriptor(token.getLength(), token.getOffset() + whitespaces);
        }
        return null;
    }

}
