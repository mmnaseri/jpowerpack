package com.agileapes.powerpack.reflection.tools.impl;

import com.agileapes.powerpack.reflection.tools.PropertyFilter;

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
    public boolean accept(String propertyName, Class<?> propertyType) {
        return propertyName.equals(this.propertyName);
    }
}
