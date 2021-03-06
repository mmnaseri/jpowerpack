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

package com.agileapes.powerpack.reflection.conversion;

import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This is intended as an easy-to-use facade for the conversion of one object of a given type
 * into another object of a desired type. The target object only needs to expose write access to
 * its properties.
 */
public interface BeanConverter {

    /**
     * This method will convert the source object to an instance of the targetType
     * @param source        the source object
     * @param targetType    the target type
     * @param <T>           the generic binding the return type
     * @return an instance of the converted object
     */
    <T extends ConfigurableBean> T convert(Object source, Class<T> targetType) throws ConversionFailureException;

    /**
     * This method will convert the source object to an instance of the targetType
     * @param source        the source object
     * @param targetType    the target type
     * @param conversionFilter    this filter decides whether a property is included in conversion or not
     * @param <T>           the generic binding the return type
     * @return an instance of the converted object
     */
    <T extends ConfigurableBean> T convert(Object source, Class<T> targetType, ConversionFilter conversionFilter) throws ConversionFailureException;

}
