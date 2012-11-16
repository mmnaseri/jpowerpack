package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.beans.BeanWrapper;
import com.agileapes.powerpack.reflection.beans.BeanWrapperFactory;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 23:26)
 */
public class GetterBeanWrapperFactory implements BeanWrapperFactory {
    
    @Override
    public <B> BeanWrapper<B> getWrapper(B object) {
        return new AccessorBeanWrapper<B>(object);
    }
    
}
