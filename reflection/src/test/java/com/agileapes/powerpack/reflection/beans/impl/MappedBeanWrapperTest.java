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
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/17, 16:44)
 */
public class MappedBeanWrapperTest {

    @Test
    public void testBuildMap() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        Assert.assertEquals(wrapper.getBeanType(), Map.class);
        wrapper.setPropertyValue("int", 1);
        wrapper.setPropertyValue("double", 2d);
        Assert.assertTrue(wrapper.hasProperty("int"));
        Assert.assertEquals(wrapper.getPropertyType("int"), Integer.class);
        Assert.assertEquals(wrapper.getPropertyValue("int"), 1);
        Assert.assertTrue(wrapper.hasProperty("double"));
        Assert.assertEquals(wrapper.getPropertyType("double"), Double.class);
        Assert.assertEquals(wrapper.getPropertyValue("double", Double.class), 2d);
        final Map<String, Object> map = wrapper.getBean();
        Assert.assertEquals(map.size(), wrapper.getProperties().size());
        for (String property : wrapper.getProperties()) {
            Assert.assertTrue(map.containsKey(property));
        }
        Assert.assertNotNull(wrapper.toString());
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testCanWriteForNonExistentProperty() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        wrapper.canWrite("property");
    }

    @Test
    public void testCanWriteForAllProperties() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        wrapper.setPropertyValue("first", 1);
        wrapper.setPropertyValue("second", 1);
        wrapper.setPropertyValue("third", 1);
        wrapper.setPropertyValue("fourth", 1);
        for (String property : wrapper.getProperties()) {
            Assert.assertTrue(wrapper.canWrite(property));
        }
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testGetPropertyValueForNonExistentProperty() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        wrapper.getPropertyValue("property");
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testGetTypedPropertyValueForNonExistentProperty() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        wrapper.getPropertyValue("property", Object.class);
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testGetPropertyValueWithWrongType() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        wrapper.setPropertyValue("property", "123");
        wrapper.getPropertyValue("property", Integer.class);
    }

    @Test
    public void testNullProperty() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        wrapper.setPropertyValue("property", null);
        Assert.assertNull(wrapper.getPropertyValue("property"));
        Assert.assertNull(wrapper.getPropertyValue("property", Object.class));
    }

    @Test(expectedExceptions = NoSuchPropertyException.class)
    public void testNonExistentPropertyType() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        wrapper.getPropertyType("property");
    }

    @Test
    public void testEquality() throws Exception {
        final MappedBeanWrapper first = new MappedBeanWrapper(new Book());
        final MappedBeanWrapper second = new MappedBeanWrapper();
        second.setPropertyValue("class", Book.class);
        second.setPropertyValue("author", null);
        second.setPropertyValue("title", null);
        Assert.assertEquals(first, second);
    }

    @Test
    public void testChangeNullValue() throws Exception {
        final MappedBeanWrapper wrapper = new MappedBeanWrapper();
        Assert.assertFalse(wrapper.hasProperty("property"));
        wrapper.setPropertyValue("property", null);
        Assert.assertTrue(wrapper.hasProperty("property"));
        Assert.assertNull(wrapper.getPropertyValue("property"));
        wrapper.setPropertyValue("property", 1);
        Assert.assertNotNull(wrapper.getPropertyValue("property"));
        Assert.assertEquals(wrapper.getPropertyValue("property"), 1);
    }
}
