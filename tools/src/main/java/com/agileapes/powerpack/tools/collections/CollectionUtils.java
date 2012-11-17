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

import com.agileapes.powerpack.tools.general.Filter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * A set of utilities for working with collections
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 1:32)
 */
public abstract class CollectionUtils {

    /**
     * This method will filter the contents of a collection, returning a collection that only
     * holds those items meeting the criteria specified by the filter
     * @param collection    the source collection
     * @param selector      the selector
     * @param <E>           type of collection items
     * @return the filtered collection
     * @throws UnsupportedOperationException if the input collection is not of a supported type
     * <p>Supported types are:</p>
     * <ul>
     *     <li>{@link Set}</li>
     *     <li>{@link List}</li>
     *     <li>{@link Queue}</li>
     * </ul>
     */
    public static <E> Collection<? extends E> filter(Collection<? extends E> collection, Filter<E> selector) {
        final Collection<E> result;// = new CopyOnWriteArrayList<E>();
        if (collection instanceof Set<?>) {
            result = new CopyOnWriteArraySet<E>();
        } else if (collection instanceof List<?>) {
            result = new CopyOnWriteArrayList<E>();
        } else if (collection instanceof Queue<?>) {
            result = new ConcurrentLinkedQueue<E>();
        } else {
            throw new UnsupportedOperationException("Given collection type is not supported: " + collection.getClass().getCanonicalName());
        }
        for (E item : collection) {
            if (selector.accept(item)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * This method will take an arbitrary collection, and create a {@link Map} out of it,
     * assigning keys to each of the items in the collection by using an {@link ItemMapper}
     * @param mapper        the mapper used to generate the keys
     * @param collection    the original collection
     * @param <K>           the type for the keys
     * @param <E>           the type for the items
     * @return map of all items
     */
    public static <K, E> Map<K, E> makeMap(ItemMapper<E, K> mapper, Collection<E> collection) {
        final Map<K, E> map = new ConcurrentHashMap<K, E>();
        for (E item : collection) {
            map.put(mapper.map(item), item);
        }
        return map;
    }

    /**
     * This method will take an arbitrary array, and create a {@link Map} out of it,
     * assigning keys to each of the items in the collection by using an {@link ItemMapper}
     * @param mapper        the mapper used to generate the keys
     * @param array         the original array
     * @param <K>           the type for the keys
     * @param <E>           the type for the items
     * @return map of all items
     */
    public static <K, E> Map<K, E> makeMap(ItemMapper<E, K> mapper, E ... array) {
        final ArrayList<E> collection = new ArrayList<E>();
        Collections.addAll(collection, array);
        return makeMap(mapper, collection);
    }

    /**
     * This method will iterate a map, changing the keys to all of the items using an
     * {@link ItemRelocationCallback}
     * @param original     the original map
     * @param relocator    the relocator callback
     * @param <K>          the type of the keys
     * @param <V>          the type of the values
     * @return the modified map
     */
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

    /**
     * This method will <em>pluck</em> an arbitrary feature of the each of the items in the
     * given array and create a generic collection of these selected features. The relation
     * must be one-to-one between the original array and the resulting collection
     * @param mapper    the mapper used to select the feature
     * @param array     the array of input objects
     * @param <T>       the type of selected feature
     * @param <E>       the type of input items
     * @return collection of selected features
     */
    public static <T, E> Collection<T> pluck(ItemMapper<E, T> mapper, E... array) {
        return pluck(mapper, new CopyOnWriteArrayList<E>(array));
    }

    /**
     * This method will <em>pluck</em> an arbitrary feature of the each of the items in the
     * given collection and create a generic collection of these selected features. The relation
     * must be one-to-one between the original collection and the resulting collection
     * @param mapper        the mapper used to select the feature
     * @param collection    the collection of input objects
     * @param <T>           the type of selected feature
     * @param <E>           the type of input items
     * @return collection of selected features
     */
    public static <T, E> Collection<T> pluck(ItemMapper<E, T> mapper, Collection<E> collection) {
        final CopyOnWriteArrayList<T> result = new CopyOnWriteArrayList<T>();
        for (E e : collection) {
            result.add(mapper.map(e));
        }
        return result;
    }

    /**
     * This method works like {@link Arrays#asList(Object[])}, but returns an instance of
     * {@link Set} instead of a list
     * @param array    the input array
     * @param <T>      the type of input items
     * @return the resulting set
     */
    public static <T> Set<T> asSet(T ... array) {
        final HashSet<T> set = new HashSet<T>();
        Collections.addAll(set, array);
        return set;
    }

    public static <E> Set<E> union(Set<E> first, Set<E> second) {
        final CopyOnWriteArraySet<E> result = new CopyOnWriteArraySet<E>();
        result.addAll(first);
        result.addAll(second);
        return result;
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

        /**
         * This will accept the keys for which values will be later
         * received
         * @param keys    the keys
         * @return a reference to {@link MapBuilder} allowing chaining of operations.
         * @see #values(Object[])
         */
        public MapBuilder<K, V> keys(K ... keys) {
            this.keys = keys;
            return this;
        }

        /**
         * This method will fill in the map with the provided keys, matching each of the values
         * to a key that has the same position. For this reason, the number of keys and values
         * is expected to be the same
         * @param values given values
         * @return resulting map
         * @throws IllegalStateException if the number of keys and values differ
         */
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

    /**
     * This method will create a map with the given values and keys
     * @param keyType      the type of keys
     * @param valueType    the type of values
     * @return the resulting map
     */
    public static <K, V> MapBuilder<K, V> mapOf(Class<K> keyType, Class<V> valueType) {
        return new MapBuilder<K, V>();
    }

    /**
     * This method will fill a map with the given values and keys
     * @param map    the initial map
     * @param <K>    the type of keys
     * @param <V>    the type of values
     * @return the resulting map
     */
    public static <K, V> MapBuilder<K, V> mapOf(Map<K, V> map) {
        return new MapBuilder<K, V>(map);
    }

}
