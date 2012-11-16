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

package com.agileapes.powerpack.reflection.beans;

import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyWriteAccessException;

/**
 * This interface is basest of bean manipulation contracts, setting out a standard way of configuring
 * the contents of a Java&trade; bean.
 *
 * A <code>ConfigurableBean</code> is a Java&trade; bean that allows the outside world to change its
 * properties values through a standard method, {@link #setPropertyValue(String, Object)}.
 *
 * Of course, this is only a way of writing into properties. To read values from such a bean, it must
 * implement {@link BeanAccessor} or expose its properties through other means.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:34)
 */
public interface ConfigurableBean {

    /**
     * This method will allow to the user to change the value of a given property through
     * its name
     * @param propertyName     the name of the property
     * @param propertyValue    the new value of this property
     * @throws NoSuchPropertyException if a property matching the given signature (name + type) cannot be found
     * @throws PropertyWriteAccessException if there was an error writing the value to the property
     */
    void setPropertyValue(String propertyName, Object propertyValue) throws NoSuchPropertyException, PropertyWriteAccessException;

}
