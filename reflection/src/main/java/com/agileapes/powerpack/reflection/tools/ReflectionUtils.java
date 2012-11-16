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

    /**
     * This method will scan the given class and all its super classes, looking for methods
     * that are accepted by the given filter. The methods occur in order of precedence in the
     * resulting array, meaning that if method 'x' was defined in both a child class and its
     * parent, the method occurring in the child (which has calling precedence) wil have index
     * precedence over the one defined in the parent, and get an index number closer to {@code 0}
     * @param type      the type to be scanned
     * @param filter    the filter for methods
     * @return an array of methods picked by the filter
     * @see #getFields(Class, PropertyFilter)
     */
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

    /**
     * This method will traverse the hierarchy of the given type upwards, collecting fields
     * that are accepted by the {@link PropertyFilter}. As the traversal happens bottom-up,
     * fields with the same name but happening at different points in the hierarchy will be
     * sorted according to their distance from the original type.
     * @param type      the type to start scanning.
     * @param filter    the field filter
     * @return an array of fields picked by the filter
     * @see #getMethods(Class, MethodFilter)
     */
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

    /**
     * This method will take any String representing the name of a standard property
     * accessor method and convert it to the name that can address the property behind
     * that accessor
     * @param accessorName    the name of the accessor
     * @return the name of the property
     * @throws IllegalArgumentException if the name provided is not an standard name for
     * an accessor
     */
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

    /**
     * This method will return a reference to the setter for the given property inside
     * the specified type's class (or higher along its hierarchy, as specified by
     * {@link #getMethods(Class, MethodFilter)}).
     * @param type            the type
     * @param propertyName    the property name
     * @return reference to setter method, or {@code null} if none can be found
     */
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
