package com.agileapes.powerpack.tools.collections;

import java.util.Collection;
import java.util.Collections;
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

    public static <T, E> Collection<T> map(ItemMapper<E, T> mapper, E ... array) {
        final Collection<E> collection = new CopyOnWriteArrayList<E>(array);
        Collections.addAll(collection, array);
        return map(mapper, collection);
    }

    public static <T, E> Collection<T> map(ItemMapper<E, T> mapper, Collection<E> collection) {
        final CopyOnWriteArrayList<T> result = new CopyOnWriteArrayList<T>();
        for (E e : collection) {
            result.add(mapper.map(e));
        }
        return result;
    }

}
