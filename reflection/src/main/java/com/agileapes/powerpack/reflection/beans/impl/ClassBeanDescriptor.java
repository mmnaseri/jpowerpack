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
import com.agileapes.powerpack.reflection.beans.BeanDescriptor;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;
import com.agileapes.powerpack.reflection.tools.impl.ReadAccessMethodFilter;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import com.agileapes.powerpack.tools.collections.ItemMapper;

import java.lang.reflect.Method;
import java.util.*;

/**
 * This descriptor will wrap a given class object, not the instances themselves, telling you exactly
 * what accessible properties they offer, and which of them is writable
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:56)
 */
public class ClassBeanDescriptor<B> implements BeanDescriptor<B>, AccessMethodAware {

    private final Class<B> beanClass;
    private Collection<String> properties;
    private Collection<String> writable = new HashSet<String>();
    private Map<String, Class<?>> types = new HashMap<String, Class<?>>();
    private Map<String, PropertyAccessMethod> accessMethod = new HashMap<String, PropertyAccessMethod>();

    public ClassBeanDescriptor(Class<B> beanClass) {
        this.beanClass = beanClass;
        final Method[] getters = ReflectionUtils.getMethods(beanClass, new ReadAccessMethodFilter());
        properties = CollectionUtils.pluck(new ItemMapper<Method, String>() {
            @Override
            public String map(Method method) {
                return ReflectionUtils.getPropertyName(method.getName());
            }
        }, getters);
        for (Method getter : getters) {
            final String propertyName = ReflectionUtils.getPropertyName(getter.getName());
            types.put(propertyName, getter.getReturnType());
            accessMethod.put(propertyName, new PropertyAccessMethod(propertyName, AccessType.METHOD, getter.getName()));
            if (ReflectionUtils.getSetter(beanClass, propertyName) != null) {
                writable.add(propertyName);
            }
        }
    }

    @Override
    public Collection<String> getProperties() {
        return Collections.unmodifiableCollection(properties);
    }

    @Override
    public boolean hasProperty(String propertyName) {
        return properties.contains(propertyName);
    }

    @Override
    public Class<?> getPropertyType(String propertyName) throws NoSuchPropertyException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return types.get(propertyName);
    }

    @Override
    public Class<B> getBeanType() {
        return beanClass;
    }

    @Override
    public boolean canWrite(String propertyName) throws NoSuchPropertyException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return writable.contains(propertyName);
    }

    @Override
    public PropertyAccessMethod getAccessMethod(String propertyName) throws NoSuchPropertyException {
        if (!hasProperty(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return accessMethod.get(propertyName);
    }

}
