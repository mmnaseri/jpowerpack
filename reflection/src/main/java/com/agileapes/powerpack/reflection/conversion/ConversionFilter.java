package com.agileapes.powerpack.reflection.conversion;

import com.agileapes.powerpack.reflection.beans.ConfigurableBean;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public interface ConversionFilter {

    boolean include(Class<? extends ConfigurableBean> type, Class<?> propertyType, String property);

    boolean convert(Class<? extends ConfigurableBean> type, Object source);

}
