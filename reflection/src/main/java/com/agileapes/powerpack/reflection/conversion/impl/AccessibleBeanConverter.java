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

import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.beans.BeanFactory;
import com.agileapes.powerpack.reflection.beans.BeanWrapper;
import com.agileapes.powerpack.reflection.beans.BeanWrapperFactory;
import com.agileapes.powerpack.reflection.beans.impl.GetterBeanWrapperFactory;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;
import com.agileapes.powerpack.reflection.conversion.TypeMapper;
import com.agileapes.powerpack.reflection.conversion.impl.wrapper.ListWrapper;
import com.agileapes.powerpack.reflection.conversion.impl.wrapper.MapWrapper;
import com.agileapes.powerpack.reflection.conversion.impl.wrapper.SetWrapper;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

import java.util.*;

/**
 * This class will take a {@link BeanAccessor} and then convert it to an instance of the
 * specified type. If you provide a {@link BeanWrapper} as the target type, then the engine
 * will have an easier time of converting things.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (8/1/12)
 */
@SuppressWarnings("UnusedDeclaration")
public class AccessibleBeanConverter {

    public static final ConversionFilter DEFAULT_CONVERSION_FILTER = new AccessibleConversionFilter();
    public static final BeanWrapperFactory DEFAULT_ACCESSOR_FACTORY = new GetterBeanWrapperFactory();
    private final BeanWrapperFactory accessorFactory;
    private final BeanFactory beanFactory;
    private final Map<Object, BeanWrapper<Object>> cache = new HashMap<Object, BeanWrapper<Object>>();
    private final TypeMapper typeMapper = new DefaultTypeMapper();

    public AccessibleBeanConverter() {
        this(null, DEFAULT_ACCESSOR_FACTORY);
    }

    public AccessibleBeanConverter(BeanWrapperFactory accessorFactory) {
        this(null, accessorFactory);
    }

    public AccessibleBeanConverter(BeanFactory beanFactory) {
        this(beanFactory, DEFAULT_ACCESSOR_FACTORY);
    }

    public AccessibleBeanConverter(BeanFactory beanFactory, BeanWrapperFactory accessorFactory) {
        this.beanFactory = beanFactory;
        this.accessorFactory = accessorFactory;
    }

    protected boolean isObjectCached(Object source) {
        return cache.containsKey(source);
    }

    protected BeanWrapper<Object> loadFromCache(Object source) {
        return cache.get(source);
    }

    protected void cacheObject(Object source, BeanWrapper<Object> accessor) {
        cache.put(source, accessor);
    }

    private <T> T instantiate(Class<T> targetType) throws InstantiationException, IllegalAccessException {
        return beanFactory != null ? beanFactory.getBean(targetType) : targetType.newInstance();
    }

    public <T> T convert(BeanAccessor<Object> source, Class<T> targetType) throws ConversionFailureException {
        return convert(source, targetType, DEFAULT_CONVERSION_FILTER);
    }

    public <T> T convert(BeanAccessor<Object> source, Class<T> targetType, ConversionFilter conversionFilter) throws ConversionFailureException {
        if (isObjectCached(source)) {
            //noinspection unchecked
            return (T) loadFromCache(source).getBean();
        }
        T bean;
        try {
            bean = instantiate(targetType);
        } catch (Throwable e) {
            throw new ConversionFailureException("Failed to instantiate target bean", e);
        }
        BeanWrapper<Object> targetWrapper = null;
        try {
            //noinspection unchecked
            targetWrapper = (BeanWrapper<Object>) accessorFactory.getWrapper(bean);
        } catch (Exception ignored) {
        }
        cacheObject(source, targetWrapper);
        try {
            doConvert(source, targetWrapper, conversionFilter);
        } catch (Exception e) {
            throw new ConversionFailureException("Failed to convert object", e);
        }
        assert targetWrapper != null;
        //noinspection unchecked
        return (T) targetWrapper.getBean();
    }

    private void doConvert(BeanAccessor<Object> source, BeanWrapper<Object> target, ConversionFilter conversionFilter) throws Exception {
        for (String propertyName : source.getProperties()) {
            if (!target.hasProperty(propertyName) || !target.canWrite(propertyName)) {
                continue;
            }
            final Class<?> propertyType = source.getPropertyType(propertyName);
            if (!conversionFilter.include(target.getClass(), propertyType, propertyName)) {
                continue;
            }
            Object propertyValue = source.getPropertyValue(propertyName);
            Object result;
            if (propertyValue == null) {
                result = null;
            } else if (!conversionFilter.convert(target.getClass(), propertyValue)) {
                result = propertyValue;
            } else {
                result = convertItem(propertyValue, typeMapper.getType(target.getPropertyType(propertyName)), conversionFilter);
            }
            target.setPropertyValue(propertyName, result);
        }
    }

    private Object convertItem(Object propertyValue, Class<?> targetType, ConversionFilter conversionFilter) throws Exception {
        Object result;
        if (propertyValue == null) {
            return null;
        }
        if (propertyValue instanceof ListWrapper && List.class.isAssignableFrom(targetType)) {
            final ListWrapper listWrapper = (ListWrapper) propertyValue;
            final List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < listWrapper.size(); i++) {
                Object item = listWrapper.get(i);
                final Class type = listWrapper.getType(i);
                list.add(convertItem(item, type, conversionFilter));
            }
            result = list;
        } else if (propertyValue instanceof MapWrapper && Map.class.isAssignableFrom(targetType)) {
            final MapWrapper mapWrapper = (MapWrapper) propertyValue;
            final Map<Object, Object> map = new HashMap<Object, Object>();
            for (Object key : mapWrapper.keySet()) {
                //noinspection unchecked
                map.put(convertItem(key, mapWrapper.getKeyType(key), conversionFilter), convertItem(mapWrapper.get(key), mapWrapper.getValueType(key), conversionFilter));
            }
            result = map;
        } else if (propertyValue instanceof SetWrapper && Set.class.isAssignableFrom(targetType)) {
            final SetWrapper setWrapper = (SetWrapper) propertyValue;
            final HashSet<Object> set = new HashSet<Object>();
            for (Object item : setWrapper) {
                //noinspection unchecked
                set.add(convertItem(item, setWrapper.getType(item), conversionFilter));
            }
            result = set;
        } else if (targetType.isInstance(propertyValue)) {
                result = propertyValue;
        } else {
            BeanAccessor<Object> beanWrapper;
            if (propertyValue instanceof BeanAccessor) {
                //noinspection unchecked
                beanWrapper = (BeanAccessor<Object>) propertyValue;
            } else {
                beanWrapper = accessorFactory.getWrapper(propertyValue);
            }
            result = convert(beanWrapper, targetType, conversionFilter);
        }
        return result;
    }

}
