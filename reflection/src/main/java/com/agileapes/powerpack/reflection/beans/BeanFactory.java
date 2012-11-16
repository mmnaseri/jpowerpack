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
 * This is a factory bean that will return instances of other beans, given their types.
 * This is here mainly as a way of integrating with Spring framework's bean initialization
 * interfaces.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/16, 18:42)
 */
public interface BeanFactory {

    /**
     * Will return a (pre-configured) instance of the given type
     * @param targetType    desired type
     * @param <T>           the instance type
     * @return object instance
     */
    <T> T getBean(Class<T> targetType);

}
