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

/**
 * This interface will allow users to define which properties should be included in the conversion
 * and which properties must be further broken down and undergo conversion of their own
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public interface ConversionFilter {

    /**
     * This method is expected to return {@code true} for all the properties that are expected
     * to have a counterpart in their converted version
     * @param type            the target object receiving the properties
     * @param propertyType    the type of the property being converted
     * @param property        the name of the property being converted
     * @return {@code true} if the property should be included
     */
    boolean include(Class<? extends ConfigurableBean> type, Class<?> propertyType, String property);

    /**
     * This method must return {@code true} whenever a given source object should be further broken
     * down to pieces and converted
     * @param type      the type of the receiving {@link ConfigurableBean} instance
     * @param source    the source object for which a conversion decision is being made
     * @return {@code false} if the object should be transferred as is.
     */
    boolean convert(Class<? extends ConfigurableBean> type, Object source);

}
