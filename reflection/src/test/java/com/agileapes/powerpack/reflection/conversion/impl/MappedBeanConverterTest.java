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

package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.beans.impl.MappedBeanWrapper;
import com.agileapes.powerpack.test.model.Book;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/17, 14:31)
 */
public class MappedBeanConverterTest {

    @Test
    public void testConvert() throws Exception {
        final Book book = new Book();
        book.setTitle("Book Title");
        book.setAuthor("Book Author");
        final MappedBeanWrapper wrapper = new MappedBeanConverter().convert(book);
        final Map<String,Object> map = wrapper.getBean();
        Assert.assertNotNull(map);
        Assert.assertFalse(map.isEmpty());
        Assert.assertEquals(map.size(), 3);
        Assert.assertEquals(map.get("class"), Book.class);
        Assert.assertEquals(map.get("title"), book.getTitle());
        Assert.assertEquals(map.get("author"), book.getAuthor());
    }

}
