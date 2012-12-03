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

package com.agileapes.powerpack.string.text.impl;

import com.agileapes.powerpack.string.text.PositionAwareTextHandler;

/**
 * This class is designed as a helper class for holding position information
 * separate from the rest of the business logic of classes that want to be
 * aware of the positioning of the cursor, and do not want to deal with that
 * information on their own at the same time.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 16:51)
 */
public class SimplePositionHandler implements PositionAwareTextHandler {

    private static final int BEGINNING_OF_LINE = 1;
    private static final int BEGINNING_OF_DOCUMENT = 1;
    private int column = BEGINNING_OF_LINE;
    private int line = BEGINNING_OF_DOCUMENT;

    public SimplePositionHandler() {
    }

    public SimplePositionHandler(int line, int column) {
        this.column = column;
        this.line = line;
    }

    public void readChar() {
        column ++;
    }

    public void readLine() {
        line ++;
        column = BEGINNING_OF_LINE;
    }

    public void readChars(int count) {
        column += count;
    }

    public void reset() {
        line = BEGINNING_OF_DOCUMENT;
        column = BEGINNING_OF_LINE;
    }

    public void readString(String string) {
        for (int i = 0; i < string.length(); i ++) {
            final char character = string.charAt(i);
            readChar();
            if (character == '\n') {
                readLine();
            }
        }
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getLine() {
        return line;
    }
}
