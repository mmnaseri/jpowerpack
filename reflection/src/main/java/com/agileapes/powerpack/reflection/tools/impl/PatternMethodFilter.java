package com.agileapes.powerpack.reflection.tools.impl;

import com.agileapes.powerpack.reflection.tools.MethodFilter;

import java.lang.reflect.Method;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:43)
 */
public class PatternMethodFilter implements MethodFilter {

    private final String[] patterns;

    public PatternMethodFilter(String ... patterns) {
        this.patterns = patterns;
    }

    @Override
    public boolean accept(Method method) {
        for (String pattern : patterns) {
            if (method.getName().matches(pattern)) {
                return true;
            }
        }
        return false;
    }
}
