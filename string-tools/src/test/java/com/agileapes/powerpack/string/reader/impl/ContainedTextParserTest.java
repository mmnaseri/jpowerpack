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

import com.agileapes.powerpack.string.exception.DocumentReaderError;
import com.agileapes.powerpack.string.exception.MissingExpectedTokenError;
import com.agileapes.powerpack.string.reader.DocumentReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 18:40)
 */
public class ContainedTextParserTest {

    @Test(expectedExceptions = DocumentReaderError.class, expectedExceptionsMessageRegExp = ".*read tokens.*")
    public void testNonEnclosedWithoutDesignator() throws Exception {
        DocumentReader reader = new PositionAwareDocumentReader("abc");
        reader.parse(new ContainedTextParser("(", ")"));
    }

    @Test
    public void testNonEnclosedWithDesignator() throws Exception {
        final DocumentReader reader = new PositionAwareDocumentReader("a b c", new NonWhitespaceTokenDesignator());
        final String parsed = reader.parse(new ContainedTextParser("(", ")"));
        Assert.assertEquals(parsed, "a");
    }

    @Test
    public void testEnclosedWithoutNestingWithoutEscaping() throws Exception {
        final DocumentReader reader = new PositionAwareDocumentReader("(a b\\) c) d");
        final String parsed = reader.parse(new ContainedTextParser("(", ")", null, false, false, null));
        Assert.assertEquals(parsed, "a b\\");
    }

    @Test
    public void testEnclosedWithoutNestingWithEscaping() throws Exception {
        final DocumentReader reader = new PositionAwareDocumentReader("(a \\) b \\) c) d");
        final String parsed = reader.parse(new ContainedTextParser("(", ")", '\\', false, false, null));
        Assert.assertEquals(parsed, "a ) b ) c");
    }

    @Test
    public void testEnclosedWithNestingWithoutEscaping() throws Exception {
        final DocumentReader reader = new PositionAwareDocumentReader("(a b\\) c) d");
        final String parsed = reader.parse(new ContainedTextParser("(", ")", '\\', false, false, null));
        Assert.assertEquals(parsed, "a b) c");
    }

    @Test
    public void testEnclosedWithNestingWithoutAcceptingEscaping() throws Exception {
        final DocumentReader reader = new PositionAwareDocumentReader("(a b\\) c) d");
        final String parsed = reader.parse(new ContainedTextParser("(", ")", null, false, false, null));
        Assert.assertEquals(parsed, "a b\\");
    }

    @Test
    public void testEnclosedWithNestingWithEscaping() throws Exception {
        final DocumentReader reader = new PositionAwareDocumentReader("(a (b \\) c)\\) d)");
        final String parsed = reader.parse(new ContainedTextParser("(", ")", '\\', false, true, null));
        Assert.assertEquals(parsed, "a (b \\) c)) d");
    }

    @Test(expectedExceptions = MissingExpectedTokenError.class)
    public void testUnenclosedWithoutAccepting() throws Exception {
        final DocumentReader reader = new PositionAwareDocumentReader("hello");
        reader.parse(new ContainedTextParser("(", ")", '\\', false, false, null));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testZeroLengthOpeningAndClosing() throws Exception {
        new ContainedTextParser("", "");
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testUnequalLengthsForOpeningAndClosing() throws Exception {
        new ContainedTextParser("", "1");
    }

    @Test(expectedExceptions = MissingExpectedTokenError.class)
    public void testMissingClosingChar() throws Exception {
        DocumentReader reader = new PositionAwareDocumentReader("(abc");
        reader.parse(new ContainedTextParser("(", ")"));
    }

    @Test
    public void testCustomTokenDesignator() throws Exception {
        final ContainedTextParser parser = new ContainedTextParser("(", ")", new PatternTokenDesignator(Pattern.compile("\\d+\\s+\\d+")));
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("1 34 bcd");
        final String parsed = parser.parse(reader);
        Assert.assertEquals(parsed, "1 34");
    }

}
