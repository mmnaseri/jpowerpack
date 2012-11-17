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
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;
import com.agileapes.powerpack.reflection.tools.impl.ReadAccessMethodFilter;
import com.agileapes.powerpack.tools.collections.*;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This implementation will access property values through their getter methods
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 1:44)
 */
public class GetterBeanAccessor<B> implements BeanAccessor<B>, AccessMethodAware {

    private final B bean;
    private final Map<String, Method> getters = new HashMap<String, Method>();
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<String, Boolean> canWrite = new LazilyInitializedMap<String, Boolean>(new CacheMissHandler<String, Boolean>() {
        @Override
        public Boolean handle(String key) {
            return checkCanWrite(key);
        }
    });
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final Map<String, PropertyAccessMethod> accessMethod = new LazilyInitializedMap<String, PropertyAccessMethod>(new CacheMissHandler<String, PropertyAccessMethod>() {
        @Override
        public PropertyAccessMethod handle(String key) {
            return determineAccessMethod(key);
        }
    });

    public GetterBeanAccessor(B bean) {
        this.bean = bean;
        initialize();
        mark();
    }

    protected void initialize() {
        getters.putAll(CollectionUtils.makeMap(new ItemMapper<Method, String>() {
            @Override
            public String map(Method method) {
                return ReflectionUtils.getPropertyName(method.getName());
            }
        }, ReflectionUtils.getMethods(getBeanType(), new ReadAccessMethodFilter())));
        mark();
    }

    private void mark() {
        ((LazilyInitializedMap<String, Boolean>) canWrite).markAll(getProperties());
        ((LazilyInitializedMap<String, PropertyAccessMethod>) accessMethod).markAll(getProperties());
    }

    @Override
    public <T> T getPropertyValue(String propertyName) throws NoSuchPropertyException, PropertyReadAccessException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return readProperty(propertyName);
    }

    @Override
    public <T> T getPropertyValue(String propertyName, Class<T> propertyType) throws NoSuchPropertyException, PropertyReadAccessException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        if (!propertyType.isAssignableFrom(getPropertyType(propertyName))) {
            throw new NoSuchPropertyException(propertyName, propertyType);
        }
        return readProperty(propertyName);
    }

    @Override
    public B getBean() {
        return bean;
    }

    @Override
    public Collection<String> getProperties() {
        return Collections.unmodifiableCollection(getters.keySet());
    }

    @Override
    public boolean hasProperty(String propertyName) {
        return getProperties().contains(propertyName);
    }

    @Override
    public Class<?> getPropertyType(String propertyName) throws NoSuchPropertyException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return determinePropertyType(propertyName);
    }

    @Override
    public Class<?> getBeanType() {
        return bean.getClass();
    }

    @Override
    public boolean canWrite(String propertyName) throws NoSuchPropertyException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return canWrite.get(propertyName);
    }

    @Override
    public PropertyAccessMethod getAccessMethod(String propertyName) throws NoSuchPropertyException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return accessMethod.get(propertyName);
    }

    protected boolean checkCanWrite(String propertyName) {
        return ReflectionUtils.getSetter(getBeanType(), propertyName) != null;
    }

    protected Class<?> determinePropertyType(String propertyName) {
        return getters.get(propertyName).getReturnType();
    }

    protected <T> T readProperty(String propertyName) {
        try {
            //noinspection unchecked
            return (T) getters.get(propertyName).invoke(bean);
        } catch (Throwable e) {
            throw new PropertyReadAccessException(propertyName, e);
        }
    }

    protected PropertyAccessMethod determineAccessMethod(String propertyName) {
        return new PropertyAccessMethod(propertyName, AccessType.METHOD, getters.get(propertyName).getName());
    }

}
