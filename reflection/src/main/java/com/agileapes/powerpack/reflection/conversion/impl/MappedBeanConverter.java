package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.beans.impl.MappedBeanWrapper;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public class MappedBeanConverter extends AbstractTypedBeanConverter<MappedBeanWrapper> {

    @Override
    public MappedBeanWrapper convert(Object source) throws ConversionFailureException {
        return convert(source, new ConversionFilter() {
            @Override
            public boolean include(Class<? extends ConfigurableBean> type, Class<?> propertyType, String property) {
                return true;
            }

            @Override
            public boolean convert(Class<? extends ConfigurableBean> type, Object source) {
                return (!source.getClass().isPrimitive() && !source.getClass().getName().startsWith("java.lang."));
            }
        });
    }

    @Override
    public MappedBeanWrapper convert(Object source, ConversionFilter conversionFilter) throws ConversionFailureException {
        return getConverter().convert(source, MappedBeanWrapper.class, conversionFilter);
    }

}
