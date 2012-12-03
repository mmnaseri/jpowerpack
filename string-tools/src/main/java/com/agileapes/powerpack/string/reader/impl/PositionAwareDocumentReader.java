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

import com.agileapes.powerpack.string.exception.*;
import com.agileapes.powerpack.string.reader.DocumentReader;
import com.agileapes.powerpack.string.reader.ReaderSnapshot;
import com.agileapes.powerpack.string.reader.SnippetParser;
import com.agileapes.powerpack.string.reader.TokenDesignator;
import com.agileapes.powerpack.string.text.PositionAwareTextHandler;
import com.agileapes.powerpack.string.text.impl.SimplePositionHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 17:12)
 */
public class PositionAwareDocumentReader implements DocumentReader, PositionAwareTextHandler {

    public static final Pattern WHITESPACE = Pattern.compile("\\s+");
    private String document;
    private final TokenDesignator tokenDesignator;
    private SimplePositionHandler positionHandler = new SimplePositionHandler();
    private int cursor = 0;

    public PositionAwareDocumentReader(String document) {
        this(document, null);
    }

    public PositionAwareDocumentReader(String document, TokenDesignator tokenDesignator) {
        this.document = document;
        this.tokenDesignator = tokenDesignator;
    }

    @Override
    public String taken() {
        return document.substring(0, cursor);
    }

    @Override
    public String rest() {
        return document.substring(cursor);
    }

    @Override
    public void skip(Pattern pattern) {
        final Matcher matcher = pattern.matcher(rest());
        if (matcher.find() && matcher.start() == 0) {
            cursor += matcher.group().length();
            positionHandler.readString(matcher.group());
        }
    }

    @Override
    public boolean has(Pattern pattern) {
        final Matcher matcher = pattern.matcher(rest());
        return matcher.find() && matcher.start() == 0;
    }

    @Override
    public boolean hasNext() {
        for (int i = cursor; i < document.length() - 1; i ++) {
            if (tokenDesignator.isToken(document.substring(cursor, i + 1))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasMore() {
        return cursor != document.length();
    }

    @Override
    public boolean matches(Pattern pattern) {
        return pattern.matcher(rest()).matches();
    }

    @Override
    public char nextChar() {
        if (!hasMore()) {
            throw new NoMoreTextException();
        }
        positionHandler.readChar();
        final char result = rest().charAt(0);
        cursor ++;
        return result;
    }

    @Override
    public String nextToken() {
        if (!hasMore()) {
            throw new NoMoreTokensException();
        }
        if (tokenDesignator == null) {
            throw new DocumentReaderError("The document reader does not know how to read tokens");
        }
        String result = null;
        String substring = "";
        for (int i = cursor; i < document.length(); i ++) {
            substring += document.charAt(i);
            if (tokenDesignator.isToken(substring)) {
                result = substring;
            } else if (result != null) {
                break;
            }
        }
        if (result == null) {
            throw new NoMoreTokensException();
        } else {
            cursor += result.length();
            positionHandler.readString(result);
            return result;
        }
    }

    @Override
    public String read(Pattern pattern, boolean skipWhitespaces) {
        if (!hasMore()) {
            throw new NoMoreTextException();
        }
        if (skipWhitespaces) {
            skip(WHITESPACE);
        }
        final Matcher matcher = pattern.matcher(rest());
        if (matcher.find() && matcher.start() == 0) {
            final String result = matcher.group();
            cursor += result.length();
            positionHandler.readString(result);
            return result;
        }
        return null;
    }

    @Override
    public String expect(Pattern pattern, boolean skipWhitespaces) {
        if (!hasMore()) {
            throw new NoMoreTextException();
        }
        final String result = read(pattern, skipWhitespaces);
        if (result == null) {
            throw new MissingExpectedTokenException(pattern);
        }
        return result;
    }

    @Override
    public void reset() {
        cursor = 0;
        positionHandler.reset();
    }

    @Override
    public void put(String string) {
        document = taken() + string + rest();
    }

    @Override
    public String parse(SnippetParser parser) {
        return parser.parse(this);
    }

    @Override
    public ReaderSnapshot snapshot() {
        return new SerializableReaderSnapshot(document, cursor);
    }

    @Override
    public void restore(ReaderSnapshot snapshot) {
        document = snapshot.getDocument();
        cursor = snapshot.getCursor();
        if (cursor > document.length()) {
            throw new ReaderOverreachException();
        }
        positionHandler.reset();
        positionHandler.readString(taken());
    }

    @Override
    public void take(int offset) {
        cursor += offset;
        if (cursor > document.length()) {
            throw new ReaderOverreachException();
        }
    }

    @Override
    public String peek(int length) {
        final String rest = rest();
        if (length > rest.length()) {
            throw new ReaderOverreachException();
        }
        return rest.substring(0, length);
    }

    @Override
    public int getColumn() {
        return positionHandler.getColumn();
    }

    @Override
    public int getLine() {
        return positionHandler.getLine();
    }

}
