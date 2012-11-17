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

import com.agileapes.powerpack.reflection.beans.impl.SerializableBeanWrapper;
import com.agileapes.powerpack.reflection.compare.BeanComparator;
import com.agileapes.powerpack.reflection.compare.impl.CachedBeanComparator;
import com.agileapes.powerpack.test.model.Book;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/17, 3:54)
 */
public class AccessibleBeanConverterTest {

    @Test
    public void testConvert() throws Exception {
        final AccessibleBeanConverter converter = new AccessibleBeanConverter();
        final Book book = new Book();
        book.setTitle("My Book");
        book.setAuthor("The Author");
        final SerializableBeanWrapper serializableBook = new SerializableBeanConverter().convert(book);
        final Book convertedBook = converter.convert(serializableBook, Book.class);
        BeanComparator comparator = new CachedBeanComparator();
        Assert.assertTrue(comparator.compare(book, convertedBook).isEmpty());
    }

}
