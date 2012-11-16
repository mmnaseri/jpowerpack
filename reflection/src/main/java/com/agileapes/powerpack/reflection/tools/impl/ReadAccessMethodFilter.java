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

package com.agileapes.powerpack.reflection.tools.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:44)
 */
public class ReadAccessMethodFilter extends PatternMethodFilter {

    public ReadAccessMethodFilter() {
        super("get[A-Z].*|is[A-Z].*");
    }

    @Override
    public boolean accept(Method method) {
        return super.accept(method) && Modifier.isPublic(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers())
                && method.getParameterTypes().length == 0 && ((method.getName().startsWith("is") &&
                method.getReturnType().isPrimitive() && method.getReturnType().getName().equals("boolean"))
                || (method.getName().startsWith("get")));
    }
}
