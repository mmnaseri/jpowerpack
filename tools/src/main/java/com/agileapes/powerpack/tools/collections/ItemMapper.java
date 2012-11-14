package com.agileapes.powerpack.tools.collections;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 1:37)
 */
public interface ItemMapper<E, T> {

    T map(E e);

}
