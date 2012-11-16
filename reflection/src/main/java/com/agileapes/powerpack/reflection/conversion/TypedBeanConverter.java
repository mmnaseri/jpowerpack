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
 * This interface encapsulates the behaviour of a converter that can only convert source
 * objects to certain, pre-defined target types.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public interface TypedBeanConverter<B extends ConfigurableBean> {

    /**
     * This method will convert the given object using a default {@link ConversionFilter} instance
     * @param source    the object ot be converted
     * @return the converted instance
     * @throws ConversionFailureException
     */
    B convert(Object source) throws ConversionFailureException;

    /**
     * This method will convert the given object using the provided conversion filter
     * @param source              the object to be converted
     * @param conversionFilter    the conversion filter used by the engine
     * @return the converted instance
     * @throws ConversionFailureException
     */
    B convert(Object source, ConversionFilter conversionFilter) throws ConversionFailureException;

}
