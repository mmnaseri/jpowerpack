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

package com.agileapes.powerpack.reflection.beans.impl;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/14, 23:39)
 */
public class PropertyAccessMethod {

    private final AccessType accessType;
    private final String accessorName;
    private final String propertyName;

    public PropertyAccessMethod(String propertyName, AccessType accessType, String accessorName) {
        this.accessType = accessType;
        this.accessorName = accessorName;
        this.propertyName = propertyName;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public String getAccessorName() {
        return accessorName;
    }

    public String getPropertyName() {
        return propertyName;
    }

}
