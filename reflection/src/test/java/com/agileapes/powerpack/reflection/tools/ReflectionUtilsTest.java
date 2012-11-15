package com.agileapes.powerpack.reflection.tools;

import com.agileapes.powerpack.reflection.tools.impl.PatternMethodFilter;
import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 14:00)
 */
public class ReflectionUtilsTest {

    @Test
    public void testGetMethods() throws Exception {
        final Method[] methods = ReflectionUtils.getMethods(Book.class, new PatternMethodFilter("(get|set).*"));
        final Set<String> names = CollectionUtils.asSet("setTitle", "getTitle", "setAuthor", "getAuthor", "getClass");
        Assert.assertEquals(methods.length, names.size());
        for (Method method : methods) {
            Assert.assertTrue(names.contains(method.getName()));
        }
    }

    @Test
    public void testGetFields() throws Exception {
        final Field[] fields = ReflectionUtils.getFields(Book.class, PropertyFilter.ALL);
        final Set<String> names = CollectionUtils.asSet("title", "author");
        Assert.assertEquals(fields.length, names.size());
        for (Field field : fields) {
            Assert.assertTrue(names.contains(field.getName()));
        }
    }

    @Test
    public void testGetPropertyNameFromGetter() throws Exception {
        Assert.assertEquals(ReflectionUtils.getPropertyName("getPropertyName"), "propertyName");
    }

    @Test
    public void testGetPropertyNameFromSetter() throws Exception {
        Assert.assertEquals(ReflectionUtils.getPropertyName("setPropertyName"), "propertyName");
    }

    @Test
    public void testGetPropertyNameFromBooleanGetter() throws Exception {
        Assert.assertEquals(ReflectionUtils.getPropertyName("isPropertyName"), "propertyName");
    }

    @Test
    public void testGetSetter() throws Exception {
        final Book book = new Book();
        book.setTitle("test");
        final Method setter = ReflectionUtils.getSetter(Book.class, "title");
        Assert.assertNotNull(setter);
        Assert.assertEquals(book.getTitle(), "test");
        setter.invoke(book, "something else");
        Assert.assertEquals(book.getTitle(), "something else");
    }

}
