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

import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.beans.BeanAccessorFactory;
import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.beans.impl.GetterBeanAccessorFactory;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyAccessException;

/**
 * This implementation provides a solid framework for converting anything to a {@link ConfigurableBean}
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 */
public class DefaultBeanConverter extends AbstractCachedBeanConverter {

    private static BeanAccessorFactory DEFAULT_FACTORY = new GetterBeanAccessorFactory();

    private final BeanAccessorFactory factory;

    public DefaultBeanConverter() {
        this(DEFAULT_FACTORY);
    }

    public DefaultBeanConverter(BeanAccessorFactory factory) {
        this.factory = factory;
    }

    @Override
    protected <T extends ConfigurableBean> T convertObject(Object source, Class<T> targetType, ConversionFilter conversionFilter, T target) throws ConversionFailureException {
        final BeanAccessor<Object> accessor = factory.getBeanAccessor(source);
        for (String propertyName : accessor.getProperties()) {
            try {
                if (!conversionFilter.include(targetType, accessor.getPropertyType(propertyName), propertyName)) {
                    continue;
                }
            } catch (NoSuchPropertyException ignored) {
            }
            final Object original;
            try {
                original = accessor.getPropertyValue(propertyName);
            } catch (Throwable e) {
                throw new ConversionFailureException("Could not access source property", e);
            }
            try {
                if (original == null) {
                    target.setPropertyValue(propertyName, null);
                } else {
                    target.setPropertyValue(propertyName, convertItem(original, targetType, conversionFilter));
                }
            } catch (NoSuchPropertyException e) {
                throw new ConversionFailureException("No such property: " + propertyName, e);
            } catch (PropertyAccessException e) {
                throw new ConversionFailureException("Could not write to property: " + propertyName, e);
            }
        }
        return target;
    }
}
