package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.beans.BeanWrapper;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;
import com.agileapes.powerpack.reflection.exceptions.PropertyWriteAccessException;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 2:00)
 */
public class AccessorBeanWrapper<B> implements BeanWrapper<B> {
    
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
    public Class<B> getBeanType() {
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

}
