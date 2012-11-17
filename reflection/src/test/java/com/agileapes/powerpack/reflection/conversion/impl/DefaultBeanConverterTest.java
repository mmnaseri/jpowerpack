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

import com.agileapes.powerpack.reflection.beans.impl.AccessorBeanWrapper;
import com.agileapes.powerpack.reflection.beans.impl.MappedBeanWrapper;
import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/17, 4:08)
 */
public class DefaultBeanConverterTest {

    @Test
    public void testConvert() throws Exception {
        final Book book = new Book();
        book.setTitle("The Title");
        book.setAuthor("The Author");
        final DefaultBeanConverter converter = new DefaultBeanConverter();
        final MappedBeanWrapper wrapper = converter.convert(book, MappedBeanWrapper.class, new SerializableBeanConversionFilter());
        Assert.assertNotNull(wrapper);
        final Set<String> names = CollectionUtils.asSet("author", "title", "class");
        Assert.assertEquals(wrapper.getProperties().size(), names.size());
        for (String property : wrapper.getProperties()) {
            Assert.assertTrue(names.contains(property));
        }
        Assert.assertEquals(book.getTitle(), wrapper.getPropertyValue("title"));
        Assert.assertEquals(book.getAuthor(), wrapper.getPropertyValue("author"));
    }

}
