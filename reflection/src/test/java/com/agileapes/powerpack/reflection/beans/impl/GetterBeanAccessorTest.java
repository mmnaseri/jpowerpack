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
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;
import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.test.model.RefusingBean;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 16:12)
 */
public class GetterBeanAccessorTest {

    public static final String TITLE = "Killer UX Design";
    public static final String AUTHOR = "Jodie Moule";
    private final BeanAccessorFactory factory = new GetterBeanAccessorFactory();

    private GetterBeanAccessor<Book> getBeanAccessor() {
        final Book book = new Book();
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        return (GetterBeanAccessor<Book>) factory.getBeanAccessor(book);
    }

    @Test
    public void testGetPropertyValue() throws Exception {
        final Object title = getBeanAccessor().getPropertyValue("title");
        Assert.assertEquals(title, TITLE);
    }

    @Test
    public void testGetBean() throws Exception {
        Assert.assertNotNull(getBeanAccessor().getBean());
    }

    @Test
    public void testGetProperties() throws Exception {
        final Set<String> properties = CollectionUtils.asSet("title", "author", "class");
        final GetterBeanAccessor<Book> descriptor = getBeanAccessor();
        Assert.assertEquals(descriptor.getProperties().size(), properties.size());
        for (String propertyName : descriptor.getProperties()) {
            Assert.assertTrue(properties.contains(propertyName));
        }
    }

    @Test
    public void testHasProperty() throws Exception {
        final Set<String> properties = CollectionUtils.asSet("title", "author", "class");
        final GetterBeanAccessor<Book> beanAccessor = getBeanAccessor();
        for (String property : properties) {
            Assert.assertTrue(beanAccessor.hasProperty(property));
        }
    }

    @Test
    public void testGetPropertyType() throws Exception {
        Assert.assertEquals(getBeanAccessor().getPropertyType("title"), String.class);
    }

    @Test
    public void testGetBeanType() throws Exception {
        Assert.assertEquals(getBeanAccessor().getBeanType(), Book.class);
    }

    @Test
    public void testCanWrite() throws Exception {
        Assert.assertTrue(getBeanAccessor().canWrite("title"));
        Assert.assertTrue(getBeanAccessor().canWrite("author"));
        Assert.assertFalse(getBeanAccessor().canWrite("class"));
    }

    @Test
    public void testGetAccessMethod() throws Exception {
        final PropertyAccessMethod accessMethod = getBeanAccessor().getAccessMethod("title");
        Assert.assertNotNull(accessMethod);
        Assert.assertEquals(accessMethod.getAccessType(), AccessType.METHOD);
        Assert.assertEquals(accessMethod.getPropertyName(), "title");
        Assert.assertEquals(accessMethod.getAccessorName(), "getTitle");
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testCanWriteForNonExistentProperty() throws Exception {
        final GetterBeanAccessor<Book> accessor = getBeanAccessor();
        accessor.canWrite("myProperty ");
    }

    @Test(expectedExceptions = PropertyReadAccessException.class)
    public void testReadError() throws Exception {
        final GetterBeanAccessor<RefusingBean> accessor = new GetterBeanAccessor<RefusingBean>(new RefusingBean());
        accessor.getPropertyValue("name");
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testGetPropertyValueForNonExistentProperty() throws Exception {
        final GetterBeanAccessor<Book> accessor = getBeanAccessor();
        accessor.getPropertyValue("property");
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testGetTypedPropertyValueForNonExistentProperty() throws Exception {
        final GetterBeanAccessor<Book> accessor = getBeanAccessor();
        accessor.getPropertyValue("property", Class.class);
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testGetPropertyValueWithInvalidType() throws Exception {
        final GetterBeanAccessor<Book> accessor = getBeanAccessor();
        accessor.getPropertyValue("title", Class.class);
    }
}
