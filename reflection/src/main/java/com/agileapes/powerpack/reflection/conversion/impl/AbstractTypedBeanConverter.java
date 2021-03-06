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

import com.agileapes.powerpack.reflection.beans.BeanAccessorFactory;
import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.conversion.BeanConverter;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;
import com.agileapes.powerpack.reflection.conversion.TypedBeanConverter;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

/**
 * This bean converter will configure the conversion process, but does not provide the specifics,
 * and as such, needs to remain abstract.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public abstract class AbstractTypedBeanConverter<B extends ConfigurableBean> implements TypedBeanConverter<B> {

    private static final ConversionFilter DEFAULT_CONVERSION_FILTER = new DefaultConversionFilter();
    private final BeanConverter converter;

    public AbstractTypedBeanConverter() {
        converter = new DefaultBeanConverter();
    }

    public AbstractTypedBeanConverter(BeanAccessorFactory factory) {
        converter = new DefaultBeanConverter(factory);
    }

    protected BeanConverter getConverter() {
        return converter;
    }

    @Override
    public B convert(Object source) throws ConversionFailureException {
        return convert(source, DEFAULT_CONVERSION_FILTER);
    }

    @Override
    public abstract B convert(Object source, ConversionFilter conversionFilter) throws ConversionFailureException;

}
