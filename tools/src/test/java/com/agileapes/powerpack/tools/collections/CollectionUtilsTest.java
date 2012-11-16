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

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/15, 14:54)
 */
public class CollectionUtilsTest {

    @Test
    public void testFilter() throws Exception {
        final Set<Integer> original = CollectionUtils.asSet(1, 2, 3, 4, 5);
        final Collection<? extends Integer> filtered = CollectionUtils.filter(original, new ItemSelector<Integer>() {
            @Override
            public boolean select(Integer item) {
                return item > 3;
            }
        });
        Assert.assertEquals(filtered.size(), 2);
        for (Integer integer : filtered) {
            Assert.assertTrue(integer > 3);
        }
    }

    @Test
    public void testMap() throws Exception {
        final Set<Integer> integers = CollectionUtils.asSet(1, 2, 3);
        final Collection<String> strings = CollectionUtils.map(new ItemMapper<Integer, String>() {
            @Override
            public String map(Integer integer) {
                return integer.toString();
            }
        }, integers);
        Assert.assertEquals(strings.size(), integers.size());
        for (Integer integer : integers) {
            Assert.assertTrue(strings.contains(integer.toString()));
        }
    }

    @Test
    public void testAsSet() throws Exception {
        final Integer[] array = {1, 2, 3};
        final Set<Integer> set = CollectionUtils.asSet(array);
        Assert.assertEquals(set.size(), array.length);
        for (Integer integer : array) {
            Assert.assertTrue(set.contains(integer));
        }
    }

    @Test
    public void testMakeMap() throws Exception {
        final Set<Integer> integers = CollectionUtils.asSet(100, 400, 900);
        Map<Integer, Integer> squares = CollectionUtils.makeMap(new ItemMapper<Integer, Integer>() {
            @Override
            public Integer map(Integer integer) {
                return (int) Math.floor(Math.sqrt(integer));
            }
        }, integers);
        Assert.assertEquals(squares.size(), integers.size());
        for (Integer integer : integers) {
            Assert.assertTrue(squares.containsValue(integer));
        }
        for (Integer integer : squares.keySet()) {
            Assert.assertTrue(integers.contains(squares.get(integer)));
        }
    }

    @Test
    public void testKeyRelocation() throws Exception {
        final Map<Integer, String> map = CollectionUtils.mapOf(new HashMap<Integer, String>()).keys(1, 2, 3, 4)
                .values("2", "4", "6", "8");
        CollectionUtils.changeKeys(map, new ItemRelocationCallback<Integer, String>() {
            @Override
            public Integer relocate(Integer key, String value) {
                return key * 2;
            }
        });
        for (Integer integer : map.keySet()) {
            Assert.assertEquals(map.get(integer), integer.toString());
        }
    }

    @Test
    public void testMapBuilder() throws Exception {
        final String[] keys = {"a", "b", "c"};
        final Integer[] values = {1, 2, 3};
        final Map<String, Integer> map = CollectionUtils.mapOf(String.class, Integer.class).keys(keys).values(values);
        Assert.assertEquals(map.size(), keys.length);
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            Assert.assertEquals(map.get(key), values[i]);
        }
    }
}
