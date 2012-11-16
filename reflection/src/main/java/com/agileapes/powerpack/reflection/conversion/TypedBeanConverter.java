package com.agileapes.powerpack.reflection.conversion;

import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/31/12)
 */
public interface TypedBeanConverter<B> {

    B convert(Object source) throws ConversionFailureException;

    B convert(Object source, ConversionFilter conversionFilter) throws ConversionFailureException;

}
