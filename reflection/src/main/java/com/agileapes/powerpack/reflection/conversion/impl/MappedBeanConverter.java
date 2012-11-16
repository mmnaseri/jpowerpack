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

import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.beans.impl.MappedBeanWrapper;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

/**
 * This converter will take an object and create a deeply traversed mapped wrapper instance.
 * After this, calling {@link com.agileapes.powerpack.reflection.beans.impl.MappedBeanWrapper#getBean()}
 * will enable you to access a map of all the properties in this object
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public class MappedBeanConverter extends AbstractTypedBeanConverter<MappedBeanWrapper> {

    @Override
    public MappedBeanWrapper convert(Object source) throws ConversionFailureException {
        return convert(source, new ConversionFilter() {
            @Override
            public boolean include(Class<? extends ConfigurableBean> type, Class<?> propertyType, String property) {
                return true;
            }

            @Override
            public boolean convert(Class<? extends ConfigurableBean> type, Object source) {
                return (!source.getClass().isPrimitive() && !source.getClass().getName().startsWith("java.lang."));
            }
        });
    }

    @Override
    public MappedBeanWrapper convert(Object source, ConversionFilter conversionFilter) throws ConversionFailureException {
        return getConverter().convert(source, MappedBeanWrapper.class, conversionFilter);
    }

}
