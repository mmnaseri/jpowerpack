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

package com.agileapes.powerpack.reflection.beans;

import com.agileapes.powerpack.reflection.beans.impl.PropertyAccessMethod;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;

/**
 * An <code>AccessMethodAware</code> class is a class that can tell you how its properties are
 * being accessed, whether through an accessor method of a specific name, or through the fields
 * themselves.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:41)
 */
public interface AccessMethodAware {

    /**
     * This method will return the access method for the given property
     * @param propertyName    the name of the queried property
     * @return the access method
     * @throws NoSuchPropertyException if no such property is registered with the implementing class
     */
    PropertyAccessMethod getAccessMethod(String propertyName) throws NoSuchPropertyException;

}
