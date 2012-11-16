package com.agileapes.powerpack.reflection.compare.impl;

import com.agileapes.powerpack.test.model.Book;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/16, 19:05)
 */
public class DefaultComparisonStrategyTest {

    private boolean equals(Object first, Object second) {
        return new DefaultComparisonStrategy().equals(first, second);
    }

    @Test
    public void testEqualityOfNulls() throws Exception {
        Assert.assertTrue(equals(null, null));
    }

    @Test
    public void testInequalityOfNullAndElse() throws Exception {
        Assert.assertFalse(equals(null, 1));
        Assert.assertFalse(equals(1, null));
    }

    @Test
    public void testEqualityOfNonIdenticalSameValues() throws Exception {
        Assert.assertTrue(equals("1", "1"));
    }

    @Test
    public void testNonEqualityOfNonIdenticalSameValuesForNonBuiltIns() throws Exception {
        Assert.assertFalse(equals(new Book(), new Book()));
    }

}
