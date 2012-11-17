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

import com.agileapes.powerpack.reflection.beans.AccessMethodAware;
import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.beans.BeanWrapper;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;
import com.agileapes.powerpack.reflection.exceptions.PropertyWriteAccessException;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * This implementation will access property values through their defined getter and setter methods
 * as defined by the Java&trade; bean specification.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 2:00)
 */
public class AccessorBeanWrapper<B> implements BeanWrapper<B>, AccessMethodAware {
    
    private final B bean;
    private final BeanAccessor<B> accessor;

    public AccessorBeanWrapper(B bean) {
        this.bean = bean;
        this.accessor = getAccessor(bean);
    }

    protected GetterBeanAccessor<B> getAccessor(B bean) {
        return new GetterBeanAccessor<B>(bean);
    }

    @Override
    public <T> T getPropertyValue(String propertyName) throws NoSuchPropertyException, PropertyReadAccessException {
        return accessor.getPropertyValue(propertyName);
    }

    @Override
    public <T> T getPropertyValue(String propertyName, Class<T> propertyType) throws NoSuchPropertyException, PropertyReadAccessException {
        return accessor.getPropertyValue(propertyName, propertyType);
    }

    @Override
    public B getBean() {
        return bean;
    }

    @Override
    public Collection<String> getProperties() {
        return accessor.getProperties();
    }

    @Override
    public boolean hasProperty(String propertyName) {
        return accessor.hasProperty(propertyName);
    }

    @Override
    public Class<?> getPropertyType(String propertyName) throws NoSuchPropertyException {
        return accessor.getPropertyType(propertyName);
    }

    @Override
    public Class<?> getBeanType() {
        return accessor.getBeanType();
    }

    @Override
    public boolean canWrite(String propertyName) throws NoSuchPropertyException {
        return accessor.canWrite(propertyName);
    }

    @Override
    public void setPropertyValue(String propertyName, Object propertyValue) throws NoSuchPropertyException, PropertyWriteAccessException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        if (!canWrite(propertyName)) {
            throw new PropertyWriteAccessException(propertyName, null);
        }
        final Method setter = ReflectionUtils.getSetter(getBeanType(), propertyName);
        try {
            setter.invoke(bean, propertyValue);
        } catch (Throwable e) {
            throw new PropertyWriteAccessException(propertyName, e);
        }
    }

    @Override
    public PropertyAccessMethod getAccessMethod(String propertyName) throws NoSuchPropertyException {
        return ((AccessMethodAware) accessor).getAccessMethod(propertyName);
    }

}
