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
        //this is the actual book instance
        final Book book = new Book();
        book.setTitle("My Book");
        book.setAuthor("The Author");
        //now we convert this using a TypedBeanConverter
        final SerializableBeanWrapper serializableBook = new SerializableBeanConverter().convert(book);
        final AccessibleBeanConverter converter = new AccessibleBeanConverter();
        //using the AccessibleBeanConverter we now convert this back to its original type
        final Book convertedBook = converter.convert(serializableBook, Book.class);
        //using a comparator we discern that the too are similar
        Assert.assertTrue(new CachedBeanComparator().compare(book, convertedBook).isEmpty());
        //to further assure ourselves
        Assert.assertEquals(convertedBook.getAuthor(), book.getAuthor());
        Assert.assertEquals(convertedBook.getTitle(), book.getTitle());
    }

}
