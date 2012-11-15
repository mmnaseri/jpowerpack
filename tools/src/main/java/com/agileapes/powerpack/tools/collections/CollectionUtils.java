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

}
