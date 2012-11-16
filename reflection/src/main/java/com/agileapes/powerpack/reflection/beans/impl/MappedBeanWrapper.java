package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.beans.BeanWrapper;
import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyAccessException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public class MappedBeanWrapper implements BeanWrapper<Map> {

    protected Map<String, Object> map = new ConcurrentHashMap<String, Object>();

    public MappedBeanWrapper() {
    }

    public MappedBeanWrapper(Object bean) throws PropertyAccessException, NoSuchPropertyException {
        final BeanAccessor<Object> accessor = new GetterBeanAccessor<Object>(bean);
        for (String property : accessor.getProperties()) {
            setPropertyValue(property, accessor.getPropertyValue(property));
        }
    }

    @Override
    public void setPropertyValue(String propertyName, Object propertyValue) throws NoSuchPropertyException, PropertyAccessException {
        try {
            map.put(propertyName, propertyValue);
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public boolean canWrite(String propertyName) throws NoSuchPropertyException {
        return true;
    }

    @Override
    public <T> T getPropertyValue(String propertyName) throws NoSuchPropertyException, PropertyAccessException {
        if (!map.containsKey(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        //noinspection unchecked
        return (T) map.get(propertyName);
    }

    @Override
    public <T> T getPropertyValue(String propertyName, Class<T> type) throws NoSuchPropertyException, PropertyAccessException {
        if (!map.containsKey(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        final Object value = map.get(propertyName);
        if (!type.isInstance(value)) {
            throw new NoSuchPropertyException(propertyName, type);
        }
        //noinspection unchecked
        return (T) value;
    }

    @Override
    public boolean hasProperty(String propertyName) {
        return map.containsKey(propertyName);
    }

    @Override
    public Class<?> getPropertyType(String propertyName) throws NoSuchPropertyException {
        if (!map.containsKey(propertyName)) {
            throw new NoSuchPropertyException(propertyName);
        }
        return map.get(propertyName).getClass();
    }

    @Override
    public Class<Map> getBeanType() {
        return Map.class;
    }

    @Override
    public Map<String, Object> getBean() {
        return map;
    }

    @Override
    public Set<String> getProperties() {
        return map.keySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MappedBeanWrapper that = (MappedBeanWrapper) o;
        return !(map != null ? !map.equals(that.map) : that.map != null);
    }

}
