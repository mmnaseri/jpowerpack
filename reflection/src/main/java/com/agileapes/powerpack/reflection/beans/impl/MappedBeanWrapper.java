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

import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.beans.BeanWrapper;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyAccessException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This bean wrapper will hold an internal map of the given object, which can be later
 * accessed via {@link #getBean()}
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public class MappedBeanWrapper implements BeanWrapper<Object> {

    protected Map<String, Object> map = new ConcurrentHashMap<String, Object>();

    public MappedBeanWrapper() {
    }

    public MappedBeanWrapper(Object bean) throws PropertyAccessException, NoSuchPropertyException {
        final BeanAccessor<Object> accessor = new GetterBeanAccessor<Object>(bean);
        for (String property : accessor.getProperties()) {
            setPropertyValue(property, accessor.getPropertyValue(property));
        }
    }

    @Override
    public void setPropertyValue(String propertyName, Object propertyValue) throws NoSuchPropertyException, PropertyAccessException {
        try {
            map.put(propertyName, propertyValue);
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public boolean canWrite(String propertyName) throws NoSuchPropertyException {
        return true;
    }

    @Override
    public <T> T getPropertyValue(String propertyName) throws NoSuchPropertyException, PropertyAccessException {
        if (!map.containsKey(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        //noinspection unchecked
        return (T) map.get(propertyName);
    }

    @Override
    public <T> T getPropertyValue(String propertyName, Class<T> type) throws NoSuchPropertyException, PropertyAccessException {
        if (!map.containsKey(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        final Object value = map.get(propertyName);
        if (!type.isInstance(value)) {
            throw new NoSuchPropertyException(propertyName, type);
        }
        //noinspection unchecked
        return (T) value;
    }

    @Override
    public boolean hasProperty(String propertyName) {
        return map.containsKey(propertyName);
    }

    @Override
    public Class<?> getPropertyType(String propertyName) throws NoSuchPropertyException {
        if (!map.containsKey(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return map.get(propertyName).getClass();
    }

    @Override
    public Class<?> getBeanType() {
        return Map.class;
    }

    @Override
    public Map<String, Object> getBean() {
        return map;
    }

    @Override
    public Set<String> getProperties() {
        return map.keySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MappedBeanWrapper that = (MappedBeanWrapper) o;
        return !(map != null ? !map.equals(that.map) : that.map != null);
    }

}
