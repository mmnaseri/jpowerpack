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
import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;

/**
 * This interface allows a bean to expose its properties, building on top of {@link BeanDescriptor}
 * to allow the user to actually access the values of the properties themselves, and not just their
 * signature or listing
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:26)
 */
public interface BeanAccessor<B> extends BeanDescriptor<B> {

    /**
     * This method will return the value of the given property as is currently available through the
     * implementing class
     * @param propertyName    the name of the property
     * @param <T>             the expected type of the property
     * @return the value of the property (might be {@link null}
     * @throws NoSuchPropertyException if the property name is invalid
     * @throws PropertyReadAccessException if could not read the value of the property
     * @see #getPropertyValue(String, Class)
     */
    <T> T getPropertyValue(String propertyName) throws NoSuchPropertyException, PropertyReadAccessException;

    /**
     * This method will return the value of the given property as is currently available through the
     * implementing class
     * @param propertyName    the name of the property
     * @param propertyType    the expected type of the property
     * @param <T>             generic type parameter for the property
     * @return the value of the property (might be {@code null})
     * @throws NoSuchPropertyException if the property name is invalid
     * @throws PropertyReadAccessException if could not read the value of the property
     * @see #getPropertyValue(String)
     */
    <T> T getPropertyValue(String propertyName, Class<T> propertyType) throws NoSuchPropertyException, PropertyReadAccessException;

    /**
     * This will expose the bean itself. Useful when accessing a third bean through a wrapper implementing
     * this interface
     * @return the wrapped instance
     */
    B getBean();

}
