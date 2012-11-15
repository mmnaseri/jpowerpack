package com.agileapes.powerpack.tools.collections;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 15:56)
 */
public interface CacheMissHandler<K, V> {

    V handle(K key);

}
