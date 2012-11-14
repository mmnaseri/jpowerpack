package com.agileapes.powerpack.reflection.tools.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:44)
 */
public class ReadAccessMethodFilter extends PatternMethodFilter {

    public ReadAccessMethodFilter() {
        super("get[A-Z].*|is[A-Z].*");
    }

    @Override
    public boolean accept(Method method) {
        return super.accept(method) && Modifier.isPublic(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers())
                && method.getParameterTypes().length == 0 && ((method.getName().startsWith("is") &&
                method.getReturnType().isPrimitive() && method.getReturnType().getName().equals("boolean"))
                || (method.getName().startsWith("get")));
    }
}
