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
import com.agileapes.powerpack.string.reader.TokenDesignator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 0:30)
 */
public class PatternTokenDesignator implements TokenDesignator {

    private final Pattern pattern;

    public PatternTokenDesignator(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public TokenDescriptor getToken(String string) {
        final Matcher matcher = pattern.matcher(string);
        if (matcher.find() && matcher.start() == 0) {
            return new SimpleTokenDescriptor(matcher.group().length());
        }
        return null;
    }

}
