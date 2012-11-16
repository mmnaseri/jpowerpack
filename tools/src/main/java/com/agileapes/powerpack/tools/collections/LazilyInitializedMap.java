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

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 16:02)
 */
public class LazilyInitializedMap<K, V> extends CachedMap<K, V> {

    private final Set<K> marked = new CopyOnWriteArraySet<K>();

    public LazilyInitializedMap(CacheMissHandler<K, V> missHandler) {
        super(missHandler);
    }

    @Override
    public boolean containsKey(Object key) {
        //noinspection SuspiciousMethodCalls
        return super.containsKey(key) || marked.contains(key);
    }

    @Override
    public V get(Object key) {
        @SuppressWarnings("unchecked") final K convertedKey = (K) key;
        if (marked.contains(convertedKey)) {
            final V value = getMissHandler().handle(convertedKey);
            if (value != null) {
                put(convertedKey, value);
                return value;
            }
        } else if (super.containsKey(convertedKey)) {
            return super.get(convertedKey);
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (marked.contains(key)) {
            marked.remove(key);
        }
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K k : m.keySet()) {
            if (marked.contains(k)) {
                marked.remove(k);
            }
        }
        super.putAll(m);
    }

    @Override
    public Set<K> keySet() {
        final Set<K> set = new HashSet<K>();
        set.addAll(super.keySet());
        set.addAll(marked);
        return set;
    }


    public void mark(K key) {
        marked.add(key);
    }

    public void markAll(Collection<K> keys) {
        for (K key : keys) {
            mark(key);
        }
    }

}
