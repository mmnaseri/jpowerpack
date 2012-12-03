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
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 23:36)
 */
public class PositionAwareDocumentReaderTest {

    @Test
    public void testBasicFlow() throws Exception {
        final String document = "123";
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader(document);
        for (int j = 0; j < 1; j ++) {
            Assert.assertTrue(reader.taken().isEmpty());
            Assert.assertEquals(reader.rest(), document);
            reader.take(1);
            Assert.assertEquals(reader.taken(), document.substring(0, 1));
            Assert.assertEquals(reader.rest(), document.substring(1));
            for (int i = 1; i < document.length(); i ++) {
                Assert.assertEquals(reader.taken().length(), i);
                Assert.assertEquals(reader.rest().length(), document.length() - i);
                reader.skip(Pattern.compile("\\d"));
            }
            reader.reset();
        }
    }

    @Test
    public void testHas() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("1 2 3");
        Assert.assertTrue(reader.has(Pattern.compile("\\d\\s\\d")));
        Assert.assertFalse(reader.has(Pattern.compile("[a-z]")));
    }

    @Test(expectedExceptions = DocumentReaderError.class)
    public void testHasNextWithoutDesignator() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader(" a b c");
        reader.hasTokens();
    }

    @Test
    public void testHasNextForTrue() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader(" a b c", new NonWhitespaceTokenDesignator());
        Assert.assertTrue(reader.hasTokens());
    }

    @Test
    public void testHasNextForFalse() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("", new NonWhitespaceTokenDesignator());
        Assert.assertFalse(reader.hasTokens());
    }

    @Test
    public void testHasMore() throws Exception {
        final String document = "a b c";
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader(document);
        for (int i = 0; i < document.length(); i ++) {
            Assert.assertTrue(reader.hasMore());
            reader.skip(Pattern.compile(".", Pattern.DOTALL));
        }
        Assert.assertFalse(reader.hasMore());
    }

    @Test
    public void testMatchesForFalse() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("ab123_2 hello there");
        Assert.assertFalse(reader.matches(Pattern.compile("\\d+")));
        Assert.assertFalse(reader.matches(Pattern.compile("[a-z]+")));
        Assert.assertFalse(reader.matches(Pattern.compile("[a-z]+\\d+")));
        Assert.assertFalse(reader.matches(Pattern.compile("[a-z]+\\d+_")));
        Assert.assertFalse(reader.matches(Pattern.compile("[a-z]+\\d+_\\d+")));
        Assert.assertFalse(reader.matches(Pattern.compile("[a-z]+\\d+_\\d+\\s+")));
        Assert.assertFalse(reader.matches(Pattern.compile("[a-z]+\\d+_\\d+\\s+hello")));
        Assert.assertFalse(reader.matches(Pattern.compile("[a-z]+\\d+_\\d+\\s+hello\\s+")));
    }

    @Test
    public void testMatchesForTrue() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("ab123_2 hello there");
        Assert.assertTrue(reader.matches(Pattern.compile("[a-z]+\\d+_\\d+\\s+hello\\s+[a-z]+")));
    }

    @Test(expectedExceptions = NoMoreTextError.class)
    public void testNextCharWithNoMoreToGo() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("");
        reader.nextChar();
    }

    @Test
    public void testNextChar() throws Exception {
        final String document = "abcd";
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader(document);
        for (int i = 0; i < document.length(); i ++) {
            Assert.assertTrue(reader.hasMore());
            Assert.assertEquals(reader.nextChar(), document.charAt(i));
        }
        Assert.assertFalse(reader.hasMore());
    }

    @Test(expectedExceptions = NoMoreTokensError.class)
    public void testNextTokenWithNoMoreToGo() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("");
        reader.nextToken();
    }

    @Test
    public void testNextToken() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader(" abc   def ", new NonWhitespaceTokenDesignator());
        String[] expected = new String[]{"abc", "def"};
        Assert.assertTrue(reader.hasMore());
        for (String token : expected) {
            Assert.assertTrue(reader.hasTokens());
            Assert.assertEquals(reader.nextToken(), token);
        }
        Assert.assertFalse(reader.hasTokens());
    }

    @Test
    public void testNextTokenWithPrefixToken() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("  abc12def34  abc12 def34", new PatternTokenDesignator(Pattern.compile("(\\s*[a-z]+\\d+)+?")));
        final String[] expected = {"abc12", "def34", "abc12", "def34"};
        for (String token : expected) {
            Assert.assertTrue(reader.hasTokens());
            Assert.assertEquals(reader.nextToken().trim(), token);
        }
        Assert.assertFalse(reader.hasTokens());
    }

    @Test(expectedExceptions = NoMoreTokensError.class)
    public void testNextTokenNotAvailable() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("abc", new PatternTokenDesignator(Pattern.compile("\\d+")));
        reader.nextToken();
    }

    @Test
    public void testReadNumber() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("\t123abc");
        final String read = reader.read(Pattern.compile("\\d+"), true);
        Assert.assertEquals(read, "123");
        Assert.assertEquals(reader.rest(), "abc");
    }

    @Test(expectedExceptions = NoMoreTextError.class)
    public void testReadWithNoMoreToGo() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("");
        reader.read(Pattern.compile("\\d+"), true);
    }

    @Test(expectedExceptions = NoMoreTextError.class)
    public void testExpectWithNoMoreToGo() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("");
        reader.expect(Pattern.compile("\\d+"), true);
    }

    @Test
    public void testExpectNumber() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader(" 123abc");
        final String expect = reader.expect(Pattern.compile("\\d+"), true);
        Assert.assertEquals(expect, "123");
        Assert.assertEquals(reader.rest(), "abc");
    }

    @Test(expectedExceptions = MissingExpectedTokenError.class)
    public void testExpectationsUnfulfilled() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("testing");
        reader.expect(Pattern.compile("\\d+"), true);
    }

    @Test(expectedExceptions = ReaderOverreachError.class)
    public void testOverreachingTake() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("");
        reader.take(1);
    }

    @Test(expectedExceptions = ReaderOverreachError.class)
    public void testOverreachingPeek() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("");
        reader.peek(1);
    }

    @Test
    public void testPeek() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("abcd");
        while (reader.hasMore()) {
            final char character = reader.peek(1).charAt(0);
            Assert.assertEquals(character, reader.nextChar());
        }
    }

    @Test
    public void testPut() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("abcghi");
        reader.take(3);
        final String taken = reader.taken();
        final String rest = reader.rest();
        final String addition = "def";
        Assert.assertEquals(reader.taken(), taken);
        Assert.assertEquals(reader.rest(), rest);
        reader.put(addition);
        Assert.assertEquals(reader.taken(), taken);
        Assert.assertEquals(reader.rest(), addition + rest);
        reader.reset();
        Assert.assertTrue(reader.taken().isEmpty());
        Assert.assertEquals(reader.rest(), taken + addition + rest);
    }

    @Test
    public void testPosition() throws Exception {
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("a\nbcd\ne");
        Assert.assertEquals(reader.getLine(), 1);
        Assert.assertEquals(reader.getColumn(), 1);
        reader.nextChar();
        Assert.assertEquals(reader.getLine(), 1);
        Assert.assertEquals(reader.getColumn(), 2);
        reader.nextChar();
        Assert.assertEquals(reader.getLine(), 2);
        Assert.assertEquals(reader.getColumn(), 1);
        reader.nextChar();
        Assert.assertEquals(reader.getLine(), 2);
        Assert.assertEquals(reader.getColumn(), 2);
        reader.nextChar();
        Assert.assertEquals(reader.getLine(), 2);
        Assert.assertEquals(reader.getColumn(), 3);
        reader.nextChar();
        Assert.assertEquals(reader.getLine(), 2);
        Assert.assertEquals(reader.getColumn(), 4);
        reader.nextChar();
        Assert.assertEquals(reader.getLine(), 3);
        Assert.assertEquals(reader.getColumn(), 1);
        reader.nextChar();
        Assert.assertEquals(reader.getLine(), 3);
        Assert.assertEquals(reader.getColumn(), 2);
        Assert.assertFalse(reader.hasMore());
    }

}
