package com.agileapes.powerpack.reflection.beans.impl;

import com.agileapes.powerpack.test.model.Book;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 16:41)
 */
public class AccessorBeanWrapperTest {

    @Test
    public void testSetPropertyValue() throws Exception {
        final Book book = new Book();
        book.setTitle("first");
        final AccessorBeanWrapper<Book> wrapper = new AccessorBeanWrapper<Book>(book);
        wrapper.setPropertyValue("title", "second");
        Assert.assertEquals(book.getTitle(), "second");
    }

}
