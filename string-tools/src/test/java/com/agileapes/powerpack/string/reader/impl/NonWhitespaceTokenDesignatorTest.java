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

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 23:59)
 */
public class NonWhitespaceTokenDesignatorTest {

    @Test
    public void testAccepting() throws Exception {
        final NonWhitespaceTokenDesignator designator = new NonWhitespaceTokenDesignator();
        String[] acceptable = new String[]{"abcd", "123", "abc123", "a_c"};
        for (String token : acceptable) {
            Assert.assertEquals(token.length(), designator.getToken(token).getLength());
        }
    }

    @Test
    public void testRejecting() throws Exception {
        final NonWhitespaceTokenDesignator designator = new NonWhitespaceTokenDesignator();
        String[] unacceptable = new String[]{" ", ""};
        for (String token : unacceptable) {
            Assert.assertNull(designator.getToken(token));
        }
    }

}
