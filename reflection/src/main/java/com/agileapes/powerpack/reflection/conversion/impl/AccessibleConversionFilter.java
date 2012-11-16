/*
 * Copyright (c) 2012 M. M. Naseri <m.m.naseri@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 */

package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;

/**
 * This filter will leave out {@link Class} type properties and prevent the conversion of primitives
 *
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
