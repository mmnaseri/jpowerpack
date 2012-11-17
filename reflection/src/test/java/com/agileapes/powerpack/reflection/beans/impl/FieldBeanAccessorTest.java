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

import com.agileapes.powerpack.reflection.beans.BeanAccessorFactory;
import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.test.model.Printable;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 16:20)
 */
public class FieldBeanAccessorTest {

    public static final String TITLE = "Killer UX Design";
    public static final String AUTHOR = "Jodie Moule";

    private final BeanAccessorFactory factory = new FieldBeanAccessorFactory();

    private FieldBeanAccessor<Book> getBeanAccessor() {
        final Book book = new Book();
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        return (FieldBeanAccessor<Book>) factory.getBeanAccessor(book);
    }

    private Set<String> getProperties() {
        return CollectionUtils.asSet("identifier", "title", "author");
    }

    @Test
    public void testGetProperties() throws Exception {
        final Collection<String> properties = getBeanAccessor().getProperties();
        final Set<String> expected = getProperties();
        Assert.assertEquals(properties.size(), expected.size());
        for (String property : properties) {
            Assert.assertTrue(expected.contains(property));
        }
    }

    @Test
    public void testGetPropertyValue() throws Exception {
        final FieldBeanAccessor<Book> beanAccessor = getBeanAccessor();
        Assert.assertEquals(beanAccessor.getPropertyValue("author"), AUTHOR);
        Assert.assertEquals(beanAccessor.getPropertyValue("title"), TITLE);
        Assert.assertEquals(beanAccessor.getPropertyValue("identifier"), Printable.DEFAULT_ID);
    }

    @Test
    public void testCanWrite() throws Exception {
        for (String propertyName : getProperties()) {
            Assert.assertTrue(getBeanAccessor().canWrite(propertyName));
        }
    }

    @Test
    public void testPropertyType() throws Exception {
        final FieldBeanAccessor<Book> accessor = getBeanAccessor();
        Assert.assertTrue(accessor.hasProperty("author"));
        Assert.assertEquals(accessor.getPropertyType("author"), String.class);
    }

    @Test
    public void testPropertyAccessType() throws Exception {
        final FieldBeanAccessor<Book> accessor = getBeanAccessor();
        Assert.assertTrue(accessor.hasProperty("author"));
        final PropertyAccessMethod accessMethod = accessor.getAccessMethod("author");
        Assert.assertNotNull(accessMethod);
        Assert.assertEquals(accessMethod.getPropertyName(), "author");
        Assert.assertEquals(accessMethod.getAccessorName(), "author");
        Assert.assertEquals(accessMethod.getAccessType(), AccessType.FIELD);
    }
}
