package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (8/2/12)
 */
public class AccessibleConversionFilter implements ConversionFilter {
    @Override
    public boolean include(Class<? extends ConfigurableBean> type, Class<?> propertyType, String property) {
        return !propertyType.equals(Class.class) && !"class".equals(property);
    }

    @Override
    public boolean convert(Class<? extends ConfigurableBean> type, Object source) {
        return !source.getClass().isPrimitive();
    }
}
