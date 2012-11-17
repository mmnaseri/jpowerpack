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

import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;

import java.util.Collection;

/**
 * A BeanDescriptor will explain facts about the Java&trade; bean at hand.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:26)
 */
public interface BeanDescriptor<B> {

    /**
     * The properties available to the outside world through this wrapper
     * @return the name of the properties
     */
    Collection<String> getProperties();

    /**
     * Will determine whether a given property is available inside the given bean or not?
     * @param propertyName    the name of the property
     * @return {@code true} in case the property is available
     */
    boolean hasProperty(String propertyName);

    /**
     * This will tell the user the type of the queried property
     * @param propertyName    the property name
     * @return the type of the property
     * @throws NoSuchPropertyException if this property does not exist
     */
    Class<?> getPropertyType(String propertyName) throws NoSuchPropertyException;

    /**
     * This will return the type of the wrapped bean itself.
     * @return the bean type
     */
    Class<?> getBeanType();

    /**
     * This method will determine whether the given property (if available) is writable or not
     * @param propertyName    the name of the property
     * @return {@code true} if the property is not write-protected.
     * @throws NoSuchPropertyException if the property does not exist
     */
    boolean canWrite(String propertyName) throws NoSuchPropertyException;

}
