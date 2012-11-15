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
        properties = CollectionUtils.map(new ItemMapper<Method, String>() {
            @Override
            public String map(Method method) {
                return method.getName();
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
