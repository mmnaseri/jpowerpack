package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.beans.impl.SerializableBeanWrapper;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public class SerializableBeanConverter extends AbstractTypedBeanConverter<SerializableBeanWrapper> {

    @Override
    public SerializableBeanWrapper convert(Object source) throws ConversionFailureException {
        return convert(source, new SerializableBeanConversionFilter());
    }

    @Override
    public SerializableBeanWrapper convert(Object source, ConversionFilter conversionFilter) throws ConversionFailureException {
        return getConverter().convert(source, SerializableBeanWrapper.class, conversionFilter);
    }

}
