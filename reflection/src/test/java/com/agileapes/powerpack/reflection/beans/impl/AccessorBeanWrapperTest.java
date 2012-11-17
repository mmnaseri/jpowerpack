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

import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyWriteAccessException;
import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.test.model.HidingRabbit;
import com.agileapes.powerpack.test.model.ImmutableBean;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 16:41)
 */
public class AccessorBeanWrapperTest {

    @Test
    public void testPropertiesListing() throws Exception {
        final Book book = new Book();
        final AccessorBeanWrapper<Book> wrapper = new AccessorBeanWrapper<Book>(book);
        final Set<String> properties = CollectionUtils.asSet("title", "author", "class");
        Assert.assertEquals(wrapper.getProperties().size(), properties.size());
        for (String propertyName : wrapper.getProperties()) {
            Assert.assertTrue(properties.contains(propertyName));
        }
    }

    @Test
    public void testSetPropertyValue() throws Exception {
        final Book book = new Book();
        book.setTitle("first");
        final AccessorBeanWrapper<Book> wrapper = new AccessorBeanWrapper<Book>(book);
        wrapper.setPropertyValue("title", "second");
        Assert.assertEquals(book.getTitle(), "second");
        Assert.assertEquals(wrapper.getPropertyValue("title", String.class), book.getTitle());
        Assert.assertEquals(wrapper.getPropertyValue("title"), book.getTitle());
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testSetNonExistentPropertyValue() throws Exception {
        final Book book = new Book();
        final AccessorBeanWrapper<Book> wrapper = new AccessorBeanWrapper<Book>(book);
        wrapper.setPropertyValue("someProperty", null);
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testGetPropertyTypeOfNonExistentProperty() throws Exception {
        final Book book = new Book();
        final AccessorBeanWrapper<Book> wrapper = new AccessorBeanWrapper<Book>(book);
        wrapper.getPropertyType("someProperty");
    }

    @Test(expectedExceptions = PropertyWriteAccessException.class)
    public void testReadOnlyProperty() throws Exception {
        final ImmutableBean bean = new ImmutableBean("bean");
        final AccessorBeanWrapper<ImmutableBean> wrapper = new AccessorBeanWrapper<ImmutableBean>(bean);
        Assert.assertTrue(wrapper.hasProperty("name"));
        wrapper.setPropertyValue("name", null);
    }

    @Test(expectedExceptions = PropertyWriteAccessException.class)
    public void testWriteError() throws Exception {
        final HidingRabbit rabbit = new HidingRabbit();
        final AccessorBeanWrapper<HidingRabbit> wrapper = new AccessorBeanWrapper<HidingRabbit>(rabbit);
        Assert.assertTrue(wrapper.hasProperty("location"));
        Assert.assertTrue(wrapper.canWrite("location"));
        wrapper.setPropertyValue("location", HidingRabbit.ILLEGAL_LOCATION);
    }

    @Test
    public void testAccessMethodForExistingProperty() throws Exception {
        final HidingRabbit rabbit = new HidingRabbit();
        final AccessorBeanWrapper<HidingRabbit> wrapper = new AccessorBeanWrapper<HidingRabbit>(rabbit);
        final PropertyAccessMethod accessMethod = wrapper.getAccessMethod("location");
        Assert.assertNotNull(accessMethod);
        Assert.assertEquals(accessMethod.getPropertyName(), "location");
        Assert.assertEquals(accessMethod.getAccessorName(), "getLocation");
        Assert.assertEquals(accessMethod.getAccessType(), AccessType.METHOD);
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testAccessMethodForNonExistentProperty() throws Exception {
        final AccessorBeanWrapper<String> wrapper = new AccessorBeanWrapper<String>("");
        wrapper.getAccessMethod("myProperty");
    }
}
