package com.agileapes.powerpack.reflection.beans;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 17:45)
 */
public interface BeanAccessorFactory {

    <B> BeanAccessor<B> getBeanAccessor(B object);

}
