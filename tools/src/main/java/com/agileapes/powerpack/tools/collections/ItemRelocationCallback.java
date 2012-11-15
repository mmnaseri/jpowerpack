package com.agileapes.powerpack.tools.collections;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 18:03)
 */
public interface ItemRelocationCallback<K, V> {

    K relocate(K key, V value);

}
