package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.test.model.Printable;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 17:30)
 */
public class FieldBeanWrapperTest {

    @Test
    public void testSetPropertyValue() throws Exception {
        final Book book = new Book();
        final FieldBeanWrapper<Book> wrapper = new FieldBeanWrapper<Book>(book);
        Assert.assertEquals(wrapper.getPropertyValue("identifier"), Printable.DEFAULT_ID);
        wrapper.setPropertyValue("identifier", null);
        Assert.assertNull(wrapper.getPropertyValue("identifier"));
    }

}
