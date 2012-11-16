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

package com.agileapes.powerpack.tools.collections;

/**
 * This interface will provide a means for handling cases in which an item is requested that does
 * not currently reside inside the cache
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 15:56)
 */
public interface CacheMissHandler<K, V> {

    /**
     * This method is called whenever a miss has occurred for the provided key
     * @param key    the key for which a miss has occurred.
     * @return the value to be stored inside the cache for future use
     */
    V handle(K key);

}
