package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public class DefaultConversionFilter implements ConversionFilter {

    @Override
    public boolean include(Class<? extends ConfigurableBean> type, Class<?> propertyType, String property) {
        return true;
    }

    @Override
    public boolean convert(Class<? extends ConfigurableBean> type, Object source) {
        return true;
    }

}
