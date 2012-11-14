package com.agileapes.powerpack.reflection.tools;

import com.agileapes.powerpack.reflection.tools.impl.PatternMethodFilter;

import java.lang.reflect.Method;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:42)
 */
public interface MethodFilter {

    public final static MethodFilter ALL = new PatternMethodFilter(".*");

    boolean accept(Method method);

}
