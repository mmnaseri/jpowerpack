package com.agileapes.powerpack.tools.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 15:54)
 */
public class CachedMap<K, V> implements Map<K, V> {

    private final Map<K, V> map = new ConcurrentHashMap<K, V>();
    private final CacheMissHandler<K, V> missHandler;

    public CachedMap(CacheMissHandler<K, V> missHandler) {
        this.missHandler = missHandler;
    }

    protected CacheMissHandler<K, V> getMissHandler() {
        return missHandler;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        if (!containsKey(key)) {
            @SuppressWarnings("unchecked") final K convertedKey = (K) key;
            final V value = missHandler.handle(convertedKey);
            if (value != null) {
                put(convertedKey, value);
                return value;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
