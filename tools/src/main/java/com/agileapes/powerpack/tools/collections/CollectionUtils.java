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

package com.agileapes.powerpack.tools.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 1:32)
 */
public abstract class CollectionUtils {

    public static <E> Collection<? extends E> filter(Collection<? extends E> collection, ItemSelector<E> selector) {
        final CopyOnWriteArrayList<E> result = new CopyOnWriteArrayList<E>();
        for (E item : collection) {
            if (selector.select(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static <K, E> Map<K, E> makeMap(ItemMapper<E, K> mapper, Collection<E> collection) {
        final Map<K, E> map = new ConcurrentHashMap<K, E>();
        for (E item : collection) {
            map.put(mapper.map(item), item);
        }
        return map;
    }

    public static <K, E> Map<K, E> makeMap(ItemMapper<E, K> mapper, E ... array) {
        final ArrayList<E> collection = new ArrayList<E>();
        Collections.addAll(collection, array);
        return makeMap(mapper, collection);
    }

    public static <K, V> Map<K, V> changeKeys(Map<K, V> original, ItemRelocationCallback<K, V> relocator) {
        final Map<K, V> map = new HashMap<K, V>();
        for (K key : original.keySet()) {
            final V value = original.get(key);
            map.put(relocator.relocate(key, value), value);
        }
        original.clear();
        original.putAll(map);
        return original;
    }

    public static <T, E> Collection<T> map(ItemMapper<E, T> mapper, E ... array) {
        return map(mapper, new CopyOnWriteArrayList<E>(array));
    }

    public static <T, E> Collection<T> map(ItemMapper<E, T> mapper, Collection<E> collection) {
        final CopyOnWriteArrayList<T> result = new CopyOnWriteArrayList<T>();
        for (E e : collection) {
            result.add(mapper.map(e));
        }
        return result;
    }

    public static <T> Set<T> asSet(T ... array) {
        final HashSet<T> set = new HashSet<T>();
        Collections.addAll(set, array);
        return set;
    }

    public static final class MapBuilder<K, V> {

        private final Map<K, V> map;
        private K[] keys;

        private MapBuilder() {
            this(new ConcurrentHashMap<K, V>());
        }

        public MapBuilder(Map<K, V> map) {
            this.map = map;
        }

        public MapBuilder<K, V> keys(K ... keys) {
            this.keys = keys;
            return this;
        }

        public Map<K, V> values(V ... values) {
            if (keys.length != values.length) {
                throw new IllegalStateException();
            }
            for (int i = 0; i < keys.length; i++) {
                K key = keys[i];
                map.put(key, values[i]);
            }
            return map;
        }

    }

    public static <K, V> MapBuilder<K, V> mapOf(Class<K> keyType, Class<V> valueType) {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> mapOf(Map<K, V> map) {
        return new MapBuilder<K, V>(map);
    }

}
