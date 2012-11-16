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
import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 14:49)
 */
public class ClassBeanDescriptorTest {

    private ClassBeanDescriptor<Book> getDescriptor() {
        return new ClassBeanDescriptor<Book>(Book.class);
    }

    @Test
    public void testGetProperties() throws Exception {
        final Set<String> properties = CollectionUtils.asSet("title", "author", "class");
        final ClassBeanDescriptor<Book> descriptor = getDescriptor();
        Assert.assertEquals(descriptor.getProperties().size(), properties.size());
        for (String propertyName : descriptor.getProperties()) {
            Assert.assertTrue(properties.contains(propertyName));
        }
    }

    @Test
    public void testHasPropertyForExistingProperty() throws Exception {
        Assert.assertTrue(getDescriptor().hasProperty("title"));
    }

    @Test
    public void testHasPropertyForNonExistentProperty() throws Exception {
        Assert.assertFalse(getDescriptor().hasProperty("isbn"));
    }

    @Test
    public void testGetPropertyTypeForExistingProperty() throws Exception {
        Assert.assertEquals(getDescriptor().getPropertyType("title"), String.class);
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testGetPropertyTypeForNonExistentProperty() throws Exception {
        getDescriptor().getPropertyType("isbn");
    }

    @Test
    public void testGetBeanType() throws Exception {
        Assert.assertEquals(getDescriptor().getBeanType(), Book.class);
    }

    @Test
    public void testCanWrite() throws Exception {
        final ClassBeanDescriptor<Book> descriptor = getDescriptor();
        Assert.assertTrue(descriptor.canWrite("title"));
        Assert.assertTrue(descriptor.canWrite("author"));
        Assert.assertFalse(descriptor.canWrite("class"));
    }

    @Test
    public void testGetAccessMethod() throws Exception {
        final PropertyAccessMethod accessMethod = getDescriptor().getAccessMethod("title");
        Assert.assertEquals(accessMethod.getAccessType(), AccessType.METHOD);
        Assert.assertEquals(accessMethod.getPropertyName(), "title");
        Assert.assertEquals(accessMethod.getAccessorName(), "getTitle");
    }
}
