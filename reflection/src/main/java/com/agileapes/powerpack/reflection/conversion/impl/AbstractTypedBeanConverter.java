package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.beans.BeanAccessorFactory;
import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.conversion.BeanConverter;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;
import com.agileapes.powerpack.reflection.conversion.TypedBeanConverter;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public abstract class AbstractTypedBeanConverter<B extends ConfigurableBean> implements TypedBeanConverter<B> {

    private static final ConversionFilter DEFAULT_CONVERSION_FILTER = new DefaultConversionFilter();
    private final BeanConverter converter;

    public AbstractTypedBeanConverter() {
        converter = new DefaultBeanConverter();
    }

    public AbstractTypedBeanConverter(BeanAccessorFactory factory) {
        converter = new DefaultBeanConverter(factory);
    }

    protected BeanConverter getConverter() {
        return converter;
    }

    @Override
    public B convert(Object source) throws ConversionFailureException {
        return convert(source, DEFAULT_CONVERSION_FILTER);
    }

    @Override
    public abstract B convert(Object source, ConversionFilter conversionFilter) throws ConversionFailureException;

}
