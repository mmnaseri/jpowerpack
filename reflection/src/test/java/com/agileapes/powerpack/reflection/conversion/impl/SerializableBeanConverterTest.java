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
import com.agileapes.powerpack.test.model.NonSerializableNode;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;
import java.util.List;

/**
 * As the goal of this converter is to enable you to make plain old Java objects
 * serializable, we are going to do just that.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/17, 14:37)
 */
public class SerializableBeanConverterTest {

    private NonSerializableNode getNode() {
        final NonSerializableNode node = new NonSerializableNode();
        node.setTitle("1");
        node.addNode("1.1");
        node.addNode("1.2");
        node.addNode("1.3");
        return node;
    }

    @Test
    /**
     * This test demonstrates that the SerializableBeanConverter does a correct job of
     * the deep conversion associated with the operation
     */
    public void testConvert() throws Exception {
        final NonSerializableNode node = getNode();
        final SerializableBeanConverter converter = new SerializableBeanConverter();
        final SerializableBeanWrapper converted = converter.convert(node);
        Assert.assertNotNull(converted);
        Assert.assertFalse(converted.getProperties().isEmpty());
        Assert.assertEquals(converted.getProperties().size(), 3);
        Assert.assertEquals(converted.getPropertyValue("class"), NonSerializableNode.class);
        Assert.assertEquals(converted.getPropertyValue("title"), "1");
        Assert.assertTrue(converted.getPropertyValue("nodes") instanceof List);
        @SuppressWarnings("unchecked") final List<SerializableBeanWrapper> nodes = (List<SerializableBeanWrapper>) converted.getPropertyValue("nodes");
        Assert.assertEquals(nodes.size(), 3);
        for (int i = 0; i < 3; i++) {
            SerializableBeanWrapper wrapper = nodes.get(i);
            Assert.assertEquals(wrapper.getProperties().size(), 3);
            Assert.assertEquals(wrapper.getPropertyValue("class"), NonSerializableNode.class);
            Assert.assertEquals(wrapper.getPropertyValue("title"), "1." + (i + 1));
            Assert.assertNull(wrapper.getPropertyValue("nodes"));
        }
    }

    @Test
    public void testConversionBackwards() throws Exception {
        final NonSerializableNode node = getNode();
        final SerializableBeanConverter converter = new SerializableBeanConverter();
        final SerializableBeanWrapper converted = converter.convert(node);
        final NonSerializableNode convertedBack = new AccessibleBeanConverter().convert(converted, NonSerializableNode.class);
        Assert.assertEquals(convertedBack, node);
    }

    @Test
    public void testSerialization() throws Exception {
        final NonSerializableNode node = getNode();
        final SerializableBeanConverter converter = new SerializableBeanConverter();
        final SerializableBeanWrapper converted = converter.convert(node);
        final ByteArrayOutputStream interim = new ByteArrayOutputStream();
        final ObjectOutputStream outputStream = new ObjectOutputStream(interim);
        outputStream.writeObject(converted);
        outputStream.close();
        final ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(interim.toByteArray()));
        final Object object = inputStream.readObject();
        Assert.assertNotNull(object);
        Assert.assertTrue(object instanceof SerializableBeanWrapper);
        final NonSerializableNode serialized = new AccessibleBeanConverter().convert((SerializableBeanWrapper) object, NonSerializableNode.class);
        Assert.assertEquals(serialized, node);
    }

}
