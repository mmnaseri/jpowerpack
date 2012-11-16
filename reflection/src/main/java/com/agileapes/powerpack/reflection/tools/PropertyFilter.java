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

package com.agileapes.powerpack.reflection.tools;

import com.agileapes.powerpack.reflection.tools.impl.PatternPropertyFilter;

import java.lang.reflect.Field;

/**
 * This filter will allow you to cherry-pick fields from a list of them.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 0:07)
 */
public interface PropertyFilter {

    public final static PropertyFilter ALL = new PatternPropertyFilter(".*");

    /**
     * @param field    the field to be examined
     * @return {@code true} if the given field must be included in the query results
     */
    boolean accept(Field field);

}
