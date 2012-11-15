package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;
import com.agileapes.powerpack.reflection.tools.PropertyFilter;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 1:55)
 */
public class FieldBeanAccessor<B> extends GetterBeanAccessor<B> {

    private final Map<String, Field> fields = new HashMap<String, Field>();

    public FieldBeanAccessor(B bean) {
        super(bean);
    }

    @Override
    protected void initialize() {
        final Field[] properties = ReflectionUtils.getFields(getBeanType(), PropertyFilter.ALL);
        for (Field property : properties) {
            if (!Modifier.isFinal(property.getModifiers()) && !Modifier.isStatic(property.getModifiers())) {
                fields.put(property.getName(), property);
            }
        }
    }

    @Override
    public Collection<String> getProperties() {
        return Collections.unmodifiableCollection(fields.keySet());
    }

    @Override
    protected <T> T readProperty(String propertyName) {
        final Field field = fields.get(propertyName);
        field.setAccessible(true);
        try {
            //noinspection unchecked
            return (T) field.get(getBean());
        } catch (IllegalAccessException e) {
            throw new PropertyReadAccessException(propertyName, e);
        }
    }

    @Override
    protected Class<?> determinePropertyType(String propertyName) {
        return fields.get(propertyName).getType();
    }

    @Override
    protected boolean checkCanWrite(String propertyName) {
        return true;
    }

    @Override
    protected PropertyAccessMethod determineAccessMethod(String propertyName) {
        return new PropertyAccessMethod(propertyName, AccessType.FIELD, propertyName);
    }

}
