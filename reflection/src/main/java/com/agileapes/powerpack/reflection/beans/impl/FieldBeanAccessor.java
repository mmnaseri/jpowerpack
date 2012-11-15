package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.exceptions.PropertyReadAccessException;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;
import com.agileapes.powerpack.reflection.tools.impl.PatternPropertyFilter;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import com.agileapes.powerpack.tools.collections.ItemMapper;

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

    private Map<String, Field> fields;

    public FieldBeanAccessor(B bean) {
        super(bean);
    }

    @Override
    protected void initialize() {
        fields = CollectionUtils.makeMap(new ItemMapper<Field, String>() {
            @Override
            public String map(Field field) {
                return field.getName();
            }
        }, ReflectionUtils.getFields(getBeanType(), new PatternPropertyFilter(".*") {
            @Override
            public boolean accept(Field field) {
                return super.accept(field) && !Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers());
            }
        }));
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
