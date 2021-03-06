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

package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.conversion.TypeMapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This type mapper will map all primitive types to their object-oriented, class type counterparts
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (8/2/12)
 */
public class DefaultTypeMapper implements TypeMapper {

    private static final Map<String, Class<?>> primitives = new ConcurrentHashMap<String, Class<?>>();

    static {
        primitives.put("int", Integer.class);
        primitives.put("long", Long.class);
        primitives.put("short", Short.class);
        primitives.put("double", Double.class);
        primitives.put("float", Float.class);
        primitives.put("boolean", Boolean.class);
        primitives.put(List.class.getName(), ArrayList.class);
        primitives.put(Map.class.getName(), HashMap.class);
        primitives.put(Set.class.getName(), HashSet.class);
        primitives.put(Collection.class.getName(), HashSet.class);
    }

    @Override
    public Class<?> getType(Class<?> type) {
        if (type == null) {
            return null;
        }
        if (primitives.containsKey(type.getName())) {
            return primitives.get(type.getName());
        }
        return type;
    }
}
