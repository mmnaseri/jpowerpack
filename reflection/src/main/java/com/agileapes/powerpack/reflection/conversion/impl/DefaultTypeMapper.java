package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.conversion.TypeMapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
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
