package com.agileapes.powerpack.reflection.beans;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/16, 18:42)
 */
public interface BeanFactory {

    <T> T getBean(Class<T> targetType);

}
