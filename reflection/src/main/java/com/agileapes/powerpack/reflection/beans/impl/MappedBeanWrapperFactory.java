package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.beans.BeanWrapper;
import com.agileapes.powerpack.reflection.beans.BeanWrapperFactory;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (8/1/12)
 */
public class MappedBeanWrapperFactory implements BeanWrapperFactory {

    @Override
    public <B> BeanWrapper<B> getWrapper(B object) {
        //noinspection unchecked
        return (BeanWrapper<B>) new MappedBeanWrapper();
    }
}
