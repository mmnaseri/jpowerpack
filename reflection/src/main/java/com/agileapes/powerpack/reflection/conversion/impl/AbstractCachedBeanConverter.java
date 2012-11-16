package com.agileapes.powerpack.reflection.conversion.impl;

import com.agileapes.powerpack.reflection.beans.BeanFactory;
import com.agileapes.powerpack.reflection.beans.ConfigurableBean;
import com.agileapes.powerpack.reflection.conversion.BeanConverter;
import com.agileapes.powerpack.reflection.conversion.ConversionFilter;
import com.agileapes.powerpack.reflection.conversion.impl.wrapper.ListWrapper;
import com.agileapes.powerpack.reflection.conversion.impl.wrapper.MapWrapper;
import com.agileapes.powerpack.reflection.conversion.impl.wrapper.SetWrapper;
import com.agileapes.powerpack.reflection.exceptions.ConversionFailureException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This abstract class provides basic caching for the objects so that while this instance stands (of which a single one
 * should usually suffice) the conversion can be carried out only once.
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class AbstractCachedBeanConverter implements BeanConverter {

    private Map<Class<? extends ConfigurableBean>, Map<Object, ConfigurableBean>> cache = new HashMap<Class<? extends ConfigurableBean>, Map<Object, ConfigurableBean>>();
    private boolean cacheEnabled = true;
    private static final ConversionFilter DEFAULT_CONVERSION_FILTER = new DefaultConversionFilter();
    private BeanFactory beanFactory = null;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Determines whether this object has a previously cached value. Will always return {@code false} if
     * caching has been disabled
     * @param targetType    the desired object type
     * @param source        the source object
     * @param <T>           the type binder
     * @return {@code true} if caching has been enabled and an existing value is present
     * @see #disableCaching()
     * @see #enableCaching()
     * @see #cacheObject(Class, Object, ConfigurableBean)
     * @see #loadFromCache(Class, Object)
     */
    protected <T extends ConfigurableBean> boolean isObjectCached(Class<T> targetType, Object source) {
        return cacheEnabled && cache.containsKey(targetType) && cache.get(targetType).containsKey(source);
    }

    /**
     * Will store a reference to this object if caching is enabled
     * @param targetType    the desired object type
     * @param source        the source object
     * @param object        the converted object
     * @param <T>           the type binder for converted object
     * @see #isObjectCached(Class, Object)
     * @see  #loadFromCache(Class, Object)
     */
    protected <T extends ConfigurableBean> void cacheObject(Class<T> targetType, Object source, T object) {
        if (!cacheEnabled) {
            return;
        }
        Map<Object, ConfigurableBean> beanMap = cache.get(targetType);
        if (beanMap == null) {
            beanMap = new HashMap<Object, ConfigurableBean>();
        }
        beanMap.put(source, object);
        cache.put(targetType, beanMap);
    }

    /**
     * This will load a copy of the object from the cache (if one exists and caching is enabled)
     * @param targetType    the desired target type
     * @param source        the source object
     * @param <T>           the binding of type to object
     * @return the cached reference
     * @see #isObjectCached(Class, Object)
     * @see #cacheObject(Class, Object, ConfigurableBean)
     */
    protected <T extends ConfigurableBean> T loadFromCache(Class<T> targetType, Object source) {
        if (!cacheEnabled) {
            return null;
        }
        final Map<Object, ConfigurableBean> beanMap = cache.get(targetType);
        if (beanMap != null && beanMap.containsKey(source)) {
            //noinspection unchecked
            return (T) beanMap.get(source);
        }
        return null;
    }

    @Override
    public <T extends ConfigurableBean> T convert(Object source, Class<T> targetType, ConversionFilter conversionFilter) throws ConversionFailureException {
        if (source == null) {
            return null;
        }
        if (isObjectCached(targetType, source)) {
            return loadFromCache(targetType, source);
        }
        final T target;
        try {
            if (beanFactory == null) {
                target = targetType.newInstance();
            } else {
                target = beanFactory.getBean(targetType);
            }
        } catch (Throwable e) {
            throw new ConversionFailureException("Failed to instantiate the target configurable object", e);
        }
        cacheObject(targetType, source, target);
        return convertObject(source, targetType, conversionFilter, target);
    }

    @Override
    public <T extends ConfigurableBean> T convert(Object source, Class<T> targetType) throws ConversionFailureException {
        return convert(source, targetType, DEFAULT_CONVERSION_FILTER);
    }

    /**
     * This method will invalidate the object cache. Should be called in case new copies of the cached object(s) are needed
     */
    public void invalidate() {
        cache.clear();
    }

    /**
     * This will disable caching and invalidate the current cache
     */
    public void disableCaching() {
        if (cacheEnabled) {
            cacheEnabled = false;
            invalidate();
        }
    }

    /**
     * This will turn caching on, removing all currently cached items
     */
    public void enableCaching() {
        if (!cacheEnabled) {
            cacheEnabled = true;
            invalidate();
        }
    }

    /**
     * This method does the actual conversion
     *
     *
     * @param source            the source object
     * @param targetType        the target type
     * @param conversionFilter    this filter decides whether a property is included in conversion or not
     * @param target            this is the converted instance that is supposed to be filled in by the converter implementation
     * @return an instance of the converted object
     * @see BeanConverter#convert(Object, Class)
     */
    protected abstract <T extends ConfigurableBean> T convertObject(Object source, Class<T> targetType, ConversionFilter conversionFilter, T target) throws ConversionFailureException;

    protected <T extends ConfigurableBean> Object convertItem(Object source, Class<T> targetType, ConversionFilter conversionFilter) throws ConversionFailureException {
        Object result;
        if (source instanceof Set) {
            final Set set = (Set) source;
            final Set<Object> target = new SetWrapper<Object>();
            for (Object item : set) {
                ((SetWrapper<Object>) target).put(convertItem(item, targetType, conversionFilter), item == null ? null : item.getClass());
            }
            result = target;
        } else if (source instanceof List) {
            final List list = (List) source;
            final List<Object> target = new ListWrapper<Object>();
            for (Object item : list) {
                ((ListWrapper<Object>) target).add(convertItem(item, targetType, conversionFilter), item == null ? null : item.getClass());
            }
            result = target;
        } else if (source instanceof Map) {
            final Map map = (Map) source;
            final Map<Object, Object> target = new MapWrapper<Object, Object>();
            for (Object key : map.keySet()) {
                Object value = map.get(key);
                key = convertItem(key, targetType, conversionFilter);
                value = convertItem(value, targetType, conversionFilter);
                ((MapWrapper<Object, Object>) target).put(key, value, key == null ? null : key.getClass(), value == null ? null : value.getClass());
            }
            result = target;
        } else if (!conversionFilter.convert(targetType, source)) {
            result = source;
        } else {
            result = convert(source, targetType, conversionFilter);
        }
        return result;
    }

}
