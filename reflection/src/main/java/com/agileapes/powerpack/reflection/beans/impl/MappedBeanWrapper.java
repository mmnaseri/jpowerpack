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
import com.agileapes.powerpack.tools.collections.CacheMissHandler;
import com.agileapes.powerpack.tools.collections.CachedMap;
import com.agileapes.powerpack.tools.collections.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This bean wrapper will hold an internal map of the given object, which can be later
 * accessed via {@link #getBean()}
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public class MappedBeanWrapper implements BeanWrapper<Object> {

    protected Map<String, Object> map = new ConcurrentHashMap<String, Object>();
    private Set<String> nullProperties = new CopyOnWriteArraySet<String>();
    private Map<String, Class<?>> types = new CachedMap<String, Class<?>>(new CacheMissHandler<String, Class<?>>() {
        @Override
        public Class<?> handle(String propertyName) {
            return map.containsKey(propertyName) ? map.get(propertyName).getClass() : null;
        }
    });

    public MappedBeanWrapper() {
    }

    public MappedBeanWrapper(Object bean) throws PropertyAccessException, NoSuchPropertyException {
        final BeanAccessor<Object> accessor = new GetterBeanAccessor<Object>(bean);
        for (String property : accessor.getProperties()) {
            types.put(property, accessor.getPropertyType(property));
            setPropertyValue(property, accessor.getPropertyValue(property));
        }
    }

    @Override
    public void setPropertyValue(String propertyName, Object propertyValue) throws NoSuchPropertyException, PropertyAccessException {
        if (propertyValue == null) {
            nullProperties.add(propertyName);
            return;
        } else if (nullProperties.contains(propertyName)) {
            nullProperties.remove(propertyName);
        }
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
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        if (nullProperties.contains(propertyName)) {
            return null;
        }
        //noinspection unchecked
        return (T) map.get(propertyName);
    }

    @Override
    public <T> T getPropertyValue(String propertyName, Class<T> type) throws NoSuchPropertyException, PropertyAccessException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        if (nullProperties.contains(propertyName)) {
            return null;
        }
        final Object value = map.get(propertyName);
        if (!type.isInstance(value)) {
            throw new NoSuchPropertyException(propertyName, type);
        }
        //noinspection unchecked
        return (T) value;
    }

    @Override
    public Class<?> getPropertyType(String propertyName) throws NoSuchPropertyException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return types.get(propertyName);
    }

    @Override
    public boolean hasProperty(String propertyName) {
        return map.containsKey(propertyName) || nullProperties.contains(propertyName);
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
        //noinspection unchecked
        return CollectionUtils.union(map.keySet(), nullProperties);
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
