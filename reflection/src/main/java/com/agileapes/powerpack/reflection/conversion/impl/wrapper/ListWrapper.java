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

package com.agileapes.powerpack.reflection.conversion.impl.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (8/1/12)
 *
 * The ListWrapper is an implementation of the {@link java.util.List} interface which is able to tell the
 * developer the exact runtime type of the object that was stored in it prior to being converted
 */
public class ListWrapper<E> extends ArrayList<E> {

    private final List<Class<?>> types = new ArrayList<Class<?>>();

    public List<Class<?>> getTypes() {
        return Collections.unmodifiableList(types);
    }

    public void setType(int index, Class<?> type) {
        types.set(index, type);
    }

    public void add(E element, Class<?> type) {
        add(element);
        types.add(type);
    }

    public Class<?> getType(int index) {
        return types.get(index);
    }

}
