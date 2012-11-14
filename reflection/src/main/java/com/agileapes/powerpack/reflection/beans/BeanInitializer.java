package com.agileapes.powerpack.reflection.beans;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:32)
 */
public interface BeanInitializer<B> {

    B initialize(Object ... arguments);

}
