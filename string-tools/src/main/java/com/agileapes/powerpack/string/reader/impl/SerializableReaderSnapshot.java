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

import com.agileapes.powerpack.string.reader.ReaderSnapshot;

import java.io.Serializable;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 17:06)
 */
public class SerializableReaderSnapshot implements ReaderSnapshot, Serializable {

    private static final long serialVersionUID = 644294211151932568L;
    private final String document;
    private final int cursor;

    public SerializableReaderSnapshot(String document, int cursor) {
        this.document = document;
        this.cursor = cursor;
    }

    @Override
    public String getDocument() {
        return document;
    }

    @Override
    public int getCursor() {
        return cursor;
    }
}
