package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;
import com.agileapes.powerpack.reflection.tools.impl.ReadAccessMethodFilter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 1:44)
 */
public class GetterBeanAccessor<B> implements BeanAccessor<B> {

    private final B bean;
    private final Map<String, Method> getters = new HashMap<String, Method>();
    private final Map<String, Boolean> canWrite = new HashMap<String, Boolean>();

    public GetterBeanAccessor(B bean) {
        this.bean = bean;
        initialize();
    }

    protected void initialize() {
        final Method[] methods = ReflectionUtils.getMethods(getBeanType(), new ReadAccessMethodFilter());
        for (Method method : methods) {
            getters.put(ReflectionUtils.getPropertyName(method.getName()), method);
        }
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
    public Class<B> getBeanType() {
        //noinspection unchecked
        return (Class<B>) bean.getClass();
    }

    @Override
    public boolean canWrite(String propertyName) throws NoSuchPropertyException {
        if (canWrite.containsKey(propertyName)) {
            return canWrite.get(propertyName);
        }
        canWrite.put(propertyName, checkCanWrite(propertyName));
        return canWrite(propertyName);
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

}
