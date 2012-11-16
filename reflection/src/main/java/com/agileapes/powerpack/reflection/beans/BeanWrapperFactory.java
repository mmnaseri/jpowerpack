package com.agileapes.powerpack.reflection.beans;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 23:25)
 */
public interface BeanWrapperFactory {

    <B> BeanWrapper<B> getWrapper(B object);

}
