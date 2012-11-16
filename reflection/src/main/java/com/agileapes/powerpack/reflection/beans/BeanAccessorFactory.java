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

/**
 * This factory will return accessors for any given object.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 17:45)
 * @see BeanAccessor
 * @see com.agileapes.powerpack.reflection.beans.impl.GetterBeanAccessor
 * @see com.agileapes.powerpack.reflection.beans.impl.FieldBeanAccessor
 */
public interface BeanAccessorFactory {

    /**
     * Will return a (cached) wrapper for the given object
     * @param object    the object to be accessed through a wrapper
     * @param <B>       bean type
     * @return accessor for the given bean
     */
    <B> BeanAccessor<B> getBeanAccessor(B object);

}
