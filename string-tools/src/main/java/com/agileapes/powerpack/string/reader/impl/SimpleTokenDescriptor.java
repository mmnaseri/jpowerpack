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

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 0:49)
 */
public class SimpleTokenDescriptor implements TokenDescriptor {

    private final int offset;
    private final int length;

    public SimpleTokenDescriptor(int length) {
        this(length, 0);
    }

    public SimpleTokenDescriptor(int length, int offset) {
        this.offset = offset;
        this.length = length;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLength() {
        return length;
    }

}
