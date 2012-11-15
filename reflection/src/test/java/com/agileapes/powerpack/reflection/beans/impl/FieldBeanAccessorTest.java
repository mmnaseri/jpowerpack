package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.test.model.Printable;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 16:20)
 */
public class FieldBeanAccessorTest {

    public static final String TITLE = "Killer UX Design";
    public static final String AUTHOR = "Jodie Moule";

    private FieldBeanAccessor<Book> getBeanAccessor() {
        final Book book = new Book();
        book.setTitle(TITLE);
        book.setAuthor(AUTHOR);
        return new FieldBeanAccessor<Book>(book);
    }

    private Set<String> getProperties() {
        return CollectionUtils.asSet("identifier", "title", "author");
    }

    @Test
    public void testGetProperties() throws Exception {
        final Collection<String> properties = getBeanAccessor().getProperties();
        final Set<String> expected = getProperties();
        Assert.assertEquals(properties.size(), expected.size());
        for (String property : properties) {
            Assert.assertTrue(expected.contains(property));
        }
    }

    @Test
    public void testGetPropertyValue() throws Exception {
        final FieldBeanAccessor<Book> beanAccessor = getBeanAccessor();
        Assert.assertEquals(beanAccessor.getPropertyValue("author"), AUTHOR);
        Assert.assertEquals(beanAccessor.getPropertyValue("title"), TITLE);
        Assert.assertEquals(beanAccessor.getPropertyValue("identifier"), Printable.DEFAULT_ID);
    }

    @Test
    public void testCanWrite() throws Exception {
        for (String propertyName : getProperties()) {
            Assert.assertTrue(getBeanAccessor().canWrite(propertyName));
        }
    }
}
