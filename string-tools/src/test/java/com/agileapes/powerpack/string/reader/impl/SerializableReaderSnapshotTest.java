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

package com.agileapes.powerpack.string.reader.impl;

import com.agileapes.powerpack.string.exception.ReaderOverreachError;
import com.agileapes.powerpack.string.reader.DocumentReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 19:08)
 */
public class SerializableReaderSnapshotTest {

    @Test
    public void testSerializationOfSnapshot() throws Exception {
        final ByteArrayOutputStream interim = new ByteArrayOutputStream();
        final ObjectOutputStream outputStream = new ObjectOutputStream(interim);
        final DocumentReader reader = new PositionAwareDocumentReader("a\\n b c d");
        reader.take(3);
        final String taken = reader.taken();
        final String rest = reader.rest();
        //noinspection NonSerializableObjectPassedToObjectStream
        outputStream.writeObject(reader.snapshot());
        outputStream.close();
        reader.reset();
        final ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(interim.toByteArray()));
        final SerializableReaderSnapshot snapshot = (SerializableReaderSnapshot) inputStream.readObject();
        reader.restore(snapshot);
        Assert.assertEquals(reader.taken(), taken);
        Assert.assertEquals(reader.rest(), rest);
    }

    @Test(expectedExceptions = ReaderOverreachError.class)
    public void testOverreachingRestore() throws Exception {
        final SerializableReaderSnapshot abc = new SerializableReaderSnapshot("abc", 5);
        final PositionAwareDocumentReader reader = new PositionAwareDocumentReader("");
        reader.restore(abc);
    }

}
