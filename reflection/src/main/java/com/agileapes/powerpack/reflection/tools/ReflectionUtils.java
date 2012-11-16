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

package com.agileapes.powerpack.reflection.tools;

import com.agileapes.powerpack.reflection.tools.impl.WriteAccessMethodFilter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:42)
 */
public abstract class ReflectionUtils {

    public static Method[] getMethods(Class<?> type, MethodFilter filter) {
        final ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : type.getDeclaredMethods()) {
            if (filter.accept(method)) {
                methods.add(method);
            }
        }
        if (type.getSuperclass() != null) {
            Collections.addAll(methods, getMethods(type.getSuperclass(), filter));
        }
        return methods.toArray(new Method[methods.size()]);
    }

    public static Field[] getFields(Class<?> type, PropertyFilter filter) {
        final ArrayList<Field> fields = new ArrayList<Field>();
        for (Field field : type.getDeclaredFields()) {
            if (filter.accept(field)) {
                fields.add(field);
            }
        }
        if (type.getSuperclass() != null) {
            Collections.addAll(fields, getFields(type.getSuperclass(), filter));
        }
        return fields.toArray(new Field[fields.size()]);
    }

    public static String getPropertyName(String accessorName) {
        if (accessorName.matches("(get|set)[A-Z].*")) {
            accessorName = accessorName.substring(3);
        } else if (accessorName.matches("is[A-Z].*")) {
            accessorName = accessorName.substring(2);
        } else {
            throw new IllegalArgumentException("Given name is not a valid getter/setter name: " + accessorName);
        }
        return accessorName.substring(0, 1).toLowerCase() + accessorName.substring(1);
    }

    public static Method getSetter(Class<?> type, final String propertyName) {
        final Method[] methods = getMethods(type, new WriteAccessMethodFilter() {
            @Override
            public boolean accept(Method method) {
                return super.accept(method) && getPropertyName(method.getName()).equals(propertyName);
            }
        });
        return methods.length > 0 ? methods[0] : null;
    }

}
