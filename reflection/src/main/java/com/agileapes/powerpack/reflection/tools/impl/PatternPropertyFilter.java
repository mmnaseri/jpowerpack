package com.agileapes.powerpack.reflection.tools.impl;

import com.agileapes.powerpack.reflection.tools.PropertyFilter;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 0:08)
 */
public class PatternPropertyFilter implements PropertyFilter {

    private final String[] patterns;

    public PatternPropertyFilter(String ... patterns) {
        this.patterns = patterns;
    }

    @Override
    public boolean accept(String propertyName, Class<?> propertyType) {
        for (String pattern : patterns) {
            if (propertyName.matches(pattern)) {
                return true;
            }
        }
        return false;
    }
}
