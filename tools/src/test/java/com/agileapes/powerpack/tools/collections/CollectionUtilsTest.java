package com.agileapes.powerpack.tools.collections;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
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
}
