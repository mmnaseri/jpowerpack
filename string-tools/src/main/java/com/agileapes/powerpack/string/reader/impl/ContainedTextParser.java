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

import com.agileapes.powerpack.string.exception.MissingExpectedTokenException;
import com.agileapes.powerpack.string.reader.DocumentReader;
import com.agileapes.powerpack.string.reader.SnippetParser;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 18:00)
 */
public class ContainedTextParser implements SnippetParser {

    private static class Container {

        private char opening;
        private char closing;

        private Container(char opening, char closing) {
            this.opening = opening;
            this.closing = closing;
        }

        public char getOpening() {
            return opening;
        }

        public char getClosing() {
            return closing;
        }

        @Override
        public int hashCode() {
            int result = (int) opening;
            result = 31 * result + (int) closing;
            return result;
        }
    }

    private final Set<Container> containers = new HashSet<Container>();
    private final Character escape;
    private boolean acceptUnenclosed;
    private boolean acceptNested;

    public ContainedTextParser(String opening, String closing) {
        this(opening, closing, null);
    }

    public ContainedTextParser(String opening, String closing, Character escape) {
        this(opening, closing, escape, true, true);
    }

    public ContainedTextParser(String opening, String closing, Character escape, boolean acceptUnenclosed, boolean acceptNested) {
        this.escape = escape;
        this.acceptUnenclosed = acceptUnenclosed;
        this.acceptNested = acceptNested;
        opening = opening.trim();
        closing = closing.trim();
        if (opening.length() != closing.length() || opening.length() == 0) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < opening.length(); i ++) {
            containers.add(new Container(opening.charAt(i), closing.charAt(i)));
        }
    }

    @Override
    public String parse(DocumentReader reader) {
        //We will first find out whether a container can be applied to the
        //portion of the text being read
        final char firstChar = reader.peek(1).charAt(0);
        //This is the container against which the closing test will occur
        Container test = null;
        final Set<Character> opening = new HashSet<Character>();
        for (Container container : containers) {
            opening.add(container.getOpening());
            if (container.getOpening() == firstChar) {
                test = container;
            }
        }
        //If the test container is still null, it means that no opening or closing can occur
        if (test == null) {
            //If the parser is to accept a string without enclosures, we proceed to read
            //the next token.
            if (acceptUnenclosed) {
                return reader.nextToken();
            } else {
                //This section constructs a pattern matching the expected opening for the
                //contained text. This is to provide a (somewhat) more informative error
                String pattern = "[";
                for (Character character : opening) {
                    if ("{}\\[]().*?:$^-+".contains(character.toString())) {
                        pattern += "\\";
                    }
                    pattern += character;
                }
                pattern += "]";
                throw new MissingExpectedTokenException(pattern);
            }
        }
        String result = "";
        //we will skip over the opening
        reader.nextChar();
        int open = 1;
        while (true) {
            if (!reader.hasMore()) {
                throw new MissingExpectedTokenException(String.valueOf(test.getClosing()));
            }
            final char next = reader.nextChar();
            //If an opening was found within this contained instance, then
            //we know that we have to expect a "closing" for that opening within this same instance
            if (next == test.getOpening() && acceptNested) {
                open ++;
            }
            if (next == test.getClosing()) {
                if (escape != null && result.length() > 0 && result.charAt(result.length() - 1) == escape) {
                    //If the closing was escaped, then it is ignored, and instead, the escaping is removed
                    if (open == 1) {
                        result = result.substring(0, result.length() - 1);
                    }
                } else {
                    open --;
                    if (open == 0) {
                        break;
                    }
                }
            }
            result += next;
        }
        return result;
    }

}
