package com.agileapes.powerpack.reflection.beans;

import com.agileapes.powerpack.reflection.beans.impl.PropertyAccessMethod;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:41)
 */
public interface AccessMethodAware {

    PropertyAccessMethod getAccessMethod(String propertyName) throws NoSuchPropertyException;

}
