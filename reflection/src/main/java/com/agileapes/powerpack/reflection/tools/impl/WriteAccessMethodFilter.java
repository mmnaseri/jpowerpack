package com.agileapes.powerpack.reflection.tools.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:47)
 */
public class WriteAccessMethodFilter extends PatternMethodFilter {

    public WriteAccessMethodFilter() {
        super("set[A-Z].*");
    }

    @Override
    public boolean accept(Method method) {
        return super.accept(method) && Modifier.isPublic(method.getModifiers()) && !Modifier.isAbstract(method.getModifiers())
                && method.getParameterTypes().length == 1 && method.getReturnType().equals(Void.TYPE);
    }

}
