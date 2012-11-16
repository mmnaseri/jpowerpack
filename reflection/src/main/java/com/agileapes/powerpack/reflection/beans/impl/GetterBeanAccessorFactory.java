package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.beans.BeanAccessorFactory;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/16, 18:36)
 */
public class GetterBeanAccessorFactory implements BeanAccessorFactory {

    @Override
    public <B> BeanAccessor<B> getBeanAccessor(B object) {
        return new GetterBeanAccessor<B>(object);
    }

}
