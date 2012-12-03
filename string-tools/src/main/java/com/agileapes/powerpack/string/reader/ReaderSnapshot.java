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

package com.agileapes.powerpack.string.reader;

/**
 * This interface is designed to signify a state in the {@link DocumentReader}'s activity.
 * This will enable this state to be <em>remembered</em> and later restored.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 17:04)
 */
public interface ReaderSnapshot {

    /**
     * @return the text of the document itself
     */
    String getDocument();

    /**
     * @return the additive cursor position in relation to the beginning of the document.
     */
    int getCursor();

}
