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

package com.agileapes.powerpack.tools.general;

/**
 * This interface provides a general purpose way for defining a filter of any sort
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/17, 3:00)
 */
public interface Filter<E> {

    /**
     * This method will be called whenever a decision needs to be made in regards to
     * keeping an item or throwing it away
     * @param item    the item to be examined.
     * @return {@code true}, should mean that the item must remain
     */
    boolean accept(E item);

}
