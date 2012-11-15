package com.agileapes.powerpack.reflection.tools.impl;

import com.agileapes.powerpack.reflection.tools.PropertyFilter;

import java.lang.reflect.Field;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 0:11)
 */
public class NamedPropertyFilter implements PropertyFilter {

    private final String propertyName;

    public NamedPropertyFilter(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public boolean accept(Field field) {
        return field.getName().equals(this.propertyName);
    }

}
