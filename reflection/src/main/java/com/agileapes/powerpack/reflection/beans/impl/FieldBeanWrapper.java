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

package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyWriteAccessException;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;
import com.agileapes.powerpack.reflection.tools.impl.NamedPropertyFilter;

import java.lang.reflect.Field;

/**
 * This wrapper will read and write property values by accessing the fields themselves, violating the
 * Java&trade; beans specification, and thus, foregoing any (possible) configuration done to property
 * values through their accessor methods.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 2:05)
 */
public class FieldBeanWrapper<B> extends AccessorBeanWrapper<B> {

    public FieldBeanWrapper(B bean) {
        super(bean);
    }

    @Override
    protected GetterBeanAccessor<B> getAccessor(B bean) {
        return new FieldBeanAccessor<B>(bean);
    }

    @Override
    public void setPropertyValue(String propertyName, Object propertyValue) throws NoSuchPropertyException, PropertyWriteAccessException {
        final Field[] fields = ReflectionUtils.getFields(getBeanType(), new NamedPropertyFilter(propertyName));
        Throwable exception = null;
        for (Field field : fields) {
            if (propertyValue == null || field.getType().isInstance(propertyValue)) {
                field.setAccessible(true);
                try {
                    field.set(getBean(), propertyValue);
                } catch (Throwable e) {
                    exception = e;
                    continue;
                }
                return;
            }
        }
        throw new PropertyWriteAccessException(propertyName, exception);
    }

}
