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
 * This factory will enable you to wrap objects inside a predefined wrapper
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 23:25)
 * @see com.agileapes.powerpack.reflection.beans.impl.AccessorBeanWrapper
 * @see com.agileapes.powerpack.reflection.beans.impl.FieldBeanWrapper
 */
public interface BeanWrapperFactory {

    /**
     * This method will return the wrapped instance
     * @param object    object to be wrapped
     * @param <B>       the instance type
     * @return wrapped object
     */
    <B> BeanWrapper<B> getWrapper(B object);

}
