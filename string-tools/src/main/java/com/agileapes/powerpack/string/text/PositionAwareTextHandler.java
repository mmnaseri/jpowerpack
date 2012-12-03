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

package com.agileapes.powerpack.string.text;

/**
 * This interface is designed to signify which classes will be able to tell you
 * about the position they are in at this moment.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 16:51)
 */
public interface PositionAwareTextHandler {

    /**
     * @return the character-distance of the cursor position from the beginning of the line.
     */
    int getColumn();

    /**
     * @return The line distance of the cursor from the beginning of the document. This should
     * be {@code 1} if no other action has taken place.
     */
    int getLine();

}
