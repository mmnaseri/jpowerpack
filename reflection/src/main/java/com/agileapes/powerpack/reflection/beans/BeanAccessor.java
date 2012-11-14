package com.agileapes.powerpack.reflection.beans;

import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:26)
 */
public interface BeanAccessor<B> extends BeanDescriptor<B> {

    <T> T getPropertyValue(String propertyName) throws NoSuchPropertyException, PropertyReadAccessException;

    <T> T getPropertyValue(String propertyName, Class<T> propertyType) throws NoSuchPropertyException, PropertyReadAccessException;

    B getBean();

}
