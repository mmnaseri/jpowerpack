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

package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.test.model.Printable;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 17:30)
 */
public class FieldBeanWrapperTest {

    @Test
    public void testSetPropertyValue() throws Exception {
        final Book book = new Book();
        final FieldBeanWrapper<Book> wrapper = new FieldBeanWrapper<Book>(book);
        Assert.assertEquals(wrapper.getPropertyValue("identifier"), Printable.DEFAULT_ID);
        wrapper.setPropertyValue("identifier", null);
        Assert.assertNull(wrapper.getPropertyValue("identifier"));
    }

}
