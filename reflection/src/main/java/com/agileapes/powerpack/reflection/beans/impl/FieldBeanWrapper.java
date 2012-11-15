package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.reflection.exceptions.NoSuchPropertyException;
import com.agileapes.powerpack.reflection.exceptions.PropertyWriteAccessException;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;
import com.agileapes.powerpack.reflection.tools.impl.NamedPropertyFilter;

import java.lang.reflect.Field;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 2:05)
 */
public class FieldBeanWrapper<B> extends AccessorBeanWrapper<B> {

    public FieldBeanWrapper(B bean) {
        super(bean);
    }

    @Override
    protected GetterBeanAccessor<B> getAccessor(B bean) {
        return new FieldBeanAccessor<B>(bean);
    }

    @Override
    public void setPropertyValue(String propertyName, Object propertyValue) throws NoSuchPropertyException, PropertyWriteAccessException {
        final Field[] fields = ReflectionUtils.getFields(getBeanType(), new NamedPropertyFilter(propertyName));
        Throwable exception = null;
        for (Field field : fields) {
            if (propertyValue == null || field.getType().isInstance(propertyValue)) {
                field.setAccessible(true);
                try {
                    field.set(getBean(), propertyValue);
                } catch (IllegalAccessException e) {
                    exception = e;
                    continue;
                }
                return;
            }
        }
        throw new PropertyWriteAccessException(propertyName, exception);
    }

}
