package com.agileapes.powerpack.reflection.beans;

import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;

import java.util.Collection;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:26)
 */
public interface BeanDescriptor<B> {

    Collection<String> getProperties();

    boolean hasProperty(String propertyName);

    Class<?> getPropertyType(String propertyName) throws NoSuchPropertyException;

    Class<B> getBeanType();

    boolean canWrite(String propertyName) throws NoSuchPropertyException;

}
