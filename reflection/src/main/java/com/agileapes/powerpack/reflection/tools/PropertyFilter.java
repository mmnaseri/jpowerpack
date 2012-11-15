package com.agileapes.powerpack.reflection.tools;

import com.agileapes.powerpack.reflection.tools.impl.PatternPropertyFilter;

import java.lang.reflect.Field;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 0:07)
 */
public interface PropertyFilter {

    public final static PropertyFilter ALL = new PatternPropertyFilter(".*");

    boolean accept(Field field);

}
