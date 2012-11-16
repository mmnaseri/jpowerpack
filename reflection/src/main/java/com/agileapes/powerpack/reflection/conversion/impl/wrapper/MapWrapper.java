package com.agileapes.powerpack.reflection.conversion.impl.wrapper;

import java.io.Serializable;
import java.util.*;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (8/1/12)
 *
 * This wrapper class will enable the developer to query the actual type of both keys and values for
 * any given entry within the map
 */
public class MapWrapper<K, V> implements Map<K, V>, Serializable {
   
    private Map<K, MapEntry<V>> map = new HashMap<K, MapEntry<V>>();
   
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
        final MapEntry<V> entry = map.get(key);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, new MapEntry<V>(value, null, null)).getValue();
    }

    @Override
    public V remove(Object key) {
        final MapEntry<V> remove = map.remove(key);
        if (remove == null) {
            return null;
        }
        return remove.getValue();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K k : m.keySet()) {
            map.put(k, new MapEntry<V>(m.get(k), null, null));
        }
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
        final Set<V> set = new HashSet<V>();
        for (MapEntry<V> entry : map.values()) {
            set.add(entry.getValue());
        }
        return set;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        final Set<Entry<K, V>> set = new HashSet<Entry<K, V>>();
        for (Entry<K, MapEntry<V>> entry : map.entrySet()) {
            set.add(new AbstractMap.SimpleEntry<K, V>(entry.getKey(), entry.getValue().getValue()));
        }
        return set;
    }

    public void put(K key, V value, Class<?> keyType, Class<?> valueType) {
        map.put(key, new MapEntry<V>(value, keyType, valueType));
    }

    public Class<?> getKeyType(K key) {
        final MapEntry<V> entry = map.get(key);
        if (entry != null) {
            return entry.getKeyType();
        }
        return null;
    }

    public Class<?> getValueType(K key) {
        final MapEntry<V> entry = map.get(key);
        if (entry != null) {
            return entry.getValueType();
        }
        return null;
    }

    public static class MapEntry<V> {
        
        private Class<?> keyType;
        private Class<?> valueType;
        private V value;

        private MapEntry(V value, Class<?> keyType, Class<?> valueType) {
            this.value = value;
            this.keyType = keyType;
            this.valueType = valueType;
        }

        public Class<?> getKeyType() {
            return keyType;
        }

        public Class<?> getValueType() {
            return valueType;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MapEntry mapEntry = (MapEntry) o;
            return !(value != null ? !value.equals(mapEntry.value) : mapEntry.value != null);

        }

    }
    
}
