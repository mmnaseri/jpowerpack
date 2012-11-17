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

package com.agileapes.powerpack.reflection.compare.impl;

import com.agileapes.powerpack.reflection.beans.BeanAccessorFactory;
import com.agileapes.powerpack.reflection.beans.impl.FieldBeanAccessorFactory;
import com.agileapes.powerpack.reflection.beans.impl.GetterBeanAccessorFactory;
import com.agileapes.powerpack.reflection.compare.result.ComparisonResult;
import com.agileapes.powerpack.reflection.compare.result.ComplexValueModificationComparisonResult;
import com.agileapes.powerpack.reflection.compare.result.SimpleValueModificationComparisonResult;
import com.agileapes.powerpack.test.model.Artist;
import com.agileapes.powerpack.test.model.Book;
import com.agileapes.powerpack.test.model.HidingRabbit;
import com.agileapes.powerpack.test.model.Song;
import com.agileapes.powerpack.tools.collections.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/11/16, 19:09)
 */
public class CachedBeanComparatorTest {

    private Set<ComparisonResult> compare(Object first, Object second, BeanAccessorFactory accessorFactory) {
        final CachedBeanComparator comparator = new CachedBeanComparator(accessorFactory);
        return comparator.compare(first, second);
    }

    private Set<ComparisonResult> compare(Object first, Object second) {
        return compare(first, second, new GetterBeanAccessorFactory());
    }

    @Test
    public void testComparisonOfEqualPrimitives() throws Exception {
        Assert.assertTrue(compare(1, 1).isEmpty());
    }

    @Test
    public void testComparisonOfPrimitivesOfDifferentTypes() throws Exception {
        final Set<ComparisonResult> comparisonResults = compare(1, 2L);
        Assert.assertFalse(comparisonResults.isEmpty());
        Assert.assertEquals(comparisonResults.size(), 1);
        final ComparisonResult item = comparisonResults.iterator().next();
        Assert.assertTrue(item instanceof SimpleValueModificationComparisonResult);
        final SimpleValueModificationComparisonResult difference = (SimpleValueModificationComparisonResult) item;
        Assert.assertEquals(difference.getProperty(), "class");
        Assert.assertEquals(difference.getFirst(), Integer.class);
        Assert.assertEquals(difference.getSecond(), Long.class);
    }

    @Test
    public void testComparisonOfCollections() throws Exception {
        List<Object> first = new ArrayList<Object>();
        first.add(1);
        first.add(2);
        List<Object> second = new ArrayList<Object>();
        second.add(1);
        second.add(3);
        final Set<ComparisonResult> comparisonResults = compare(first, second);
        Assert.assertTrue(comparisonResults.isEmpty());
        //at this time, we do not support compound comparisons
        //so, these collections will look the same to the engine
    }

    @Test
    public void testSimilarSimpleObjects() throws Exception {
        final Book first = new Book();
        final Book second = new Book();
        //right after initialization the objects should still appear the same
        //even though they are not identically referenced
        Set<ComparisonResult> comparisonResults = compare(first, second);
        Assert.assertFalse(first.equals(second));
        Assert.assertTrue(comparisonResults.isEmpty());
        first.setTitle("Test Book");
        first.setAuthor("Test Author");
        second.setTitle(first.getTitle());
        second.setAuthor(first.getAuthor());
        comparisonResults = compare(first, second);
        Assert.assertTrue(comparisonResults.isEmpty());
    }

    @Test
    public void testNonSimilarSimpleObjects() throws Exception {
        final Book first = new Book();
        final Book second = new Book();
        first.setTitle("First Title");
        first.setAuthor("First Author");
        second.setTitle("Second Title");
        second.setAuthor("Second Author");
        final Set<ComparisonResult> comparisonResults = compare(first, second);
        final Set<String> properties = CollectionUtils.asSet("title", "author");
        Assert.assertFalse(comparisonResults.isEmpty());
        Assert.assertEquals(comparisonResults.size(), properties.size());
        for (ComparisonResult result : comparisonResults) {
            Assert.assertTrue(result instanceof SimpleValueModificationComparisonResult);
            Assert.assertTrue(properties.contains(result.getProperty()));
        }
    }

    @Test
    public void testIdenticalObjects() throws Exception {
        final Book book = new Book();
        Assert.assertTrue(compare(book, book).isEmpty());
    }

    @Test
    public void testNulls() throws Exception {
        Assert.assertTrue(compare(null, null).isEmpty());
    }

    @Test
    public void testNullAndOther() throws Exception {
        final Book book = new Book();
        Assert.assertNull(compare(book, null));
        Assert.assertNull(compare(null, book));
    }

    @Test
    public void testSimilarFacadeAccessor() throws Exception {
        final HidingRabbit first = new HidingRabbit();
        final HidingRabbit second = new HidingRabbit();
        first.setLocation("closet");
        second.setLocation("kitchen");
        //even though the fields are affected by setters, the value returned by
        //the getter is always the same, thus, the comparison fails to realize
        //any differences. This is, of course, the expected behaviour.
        Assert.assertTrue(compare(first, second).isEmpty());
    }

    @Test
    public void testNonSimilarFieldAccessor() throws Exception {
        final String closet = "closet";
        final String kitchen = "kitchen";
        final HidingRabbit first = new HidingRabbit();
        final HidingRabbit second = new HidingRabbit();
        first.setLocation(closet);
        second.setLocation(kitchen);
        //this time, since we are reading the values from the properties themselves, the effect
        //of the setters should be seen by the comparison engine.
        final Set<ComparisonResult> comparisonResults = compare(first, second, new FieldBeanAccessorFactory());
        Assert.assertFalse(comparisonResults.isEmpty());
        Assert.assertEquals(comparisonResults.size(), 1);
        final ComparisonResult item = comparisonResults.iterator().next();
        Assert.assertTrue(item instanceof SimpleValueModificationComparisonResult);
        final SimpleValueModificationComparisonResult difference = (SimpleValueModificationComparisonResult) item;
        Assert.assertEquals(difference.getProperty(), "location");
        Assert.assertEquals(difference.getFirst(), closet);
        Assert.assertEquals(difference.getSecond(), kitchen);
    }

    @Test
    public void testComplexSimilarObjects() throws Exception {
        final Song first = new Song();
        first.setTitle("Tougher Than The Rest");
        final Artist firstArtist = new Artist();
        firstArtist.setName("Bruce Springsteen");
        first.setArtists(firstArtist);
        final Song second = new Song();
        second.setTitle("Tougher Than The Rest");
        final Artist secondArtist = new Artist();
        secondArtist.setName("Bruce Springsteen");
        second.setArtists(secondArtist);
        final Set<ComparisonResult> comparisonResults = compare(first, second);
        Assert.assertTrue(comparisonResults.isEmpty());
    }

    @Test
    public void testComplexNonSimilarObjects() throws Exception {
        final Song first = new Song();
        first.setTitle("Tougher Than The Rest");
        final Artist firstArtist = new Artist();
        final String bruceSpringsteen = "Bruce Springsteen";
        firstArtist.setName(bruceSpringsteen);
        first.setArtists(firstArtist);
        final Song second = new Song();
        second.setTitle("Tougher Than The Rest");
        final Artist secondArtist = new Artist();
        final String cameraObscura = "Camera Obscura";
        secondArtist.setName(cameraObscura);
        second.setArtists(secondArtist);
        final Set<ComparisonResult> comparisonResults = compare(first, second);
        Assert.assertFalse(comparisonResults.isEmpty());
        Assert.assertEquals(comparisonResults.size(), 1);
        final ComparisonResult item = comparisonResults.iterator().next();
        Assert.assertTrue(item instanceof ComplexValueModificationComparisonResult);
        Assert.assertEquals(item.getProperty(), "artists");
        final Set<ComparisonResult> details = ((ComplexValueModificationComparisonResult) item).getComparisonDetails();
        Assert.assertFalse(details.isEmpty());
        Assert.assertEquals(details.size(), 1);
        final ComparisonResult detail = details.iterator().next();
        Assert.assertTrue(detail instanceof SimpleValueModificationComparisonResult);
        Assert.assertEquals(detail.getProperty(), "name");
        final SimpleValueModificationComparisonResult difference = (SimpleValueModificationComparisonResult) detail;
        Assert.assertEquals(difference.getFirst(), bruceSpringsteen);
        Assert.assertEquals(difference.getSecond(), cameraObscura);
    }

    @Test
    public void testComplexNearSimilarObjects() throws Exception {
        final Song first = new Song();
        first.setTitle("Tougher Than The Rest");
        final Artist firstArtist = new Artist();
        final String bruceSpringsteen = "Bruce Springsteen";
        firstArtist.setName(bruceSpringsteen);
        first.setArtists(firstArtist);
        final Artist firstAffiliation = new Artist();
        firstAffiliation.setName("First");
        firstArtist.setAffiliations(CollectionUtils.asSet(firstAffiliation));
        final Song second = new Song();
        second.setTitle("Tougher Than The Rest");
        final Artist secondArtist = new Artist();
        secondArtist.setName(bruceSpringsteen);
        secondArtist.setAffiliations(new HashSet<Artist>());
        second.setArtists(secondArtist);
        final Set<ComparisonResult> comparisonResults = compare(first, second);
        Assert.assertFalse(comparisonResults.isEmpty());
        Assert.assertEquals(comparisonResults.size(), 1);
        final ComparisonResult item = comparisonResults.iterator().next();
        Assert.assertTrue(item instanceof ComplexValueModificationComparisonResult);
        Assert.assertEquals(item.getProperty(), "artists");
        final Set<ComparisonResult> details = ((ComplexValueModificationComparisonResult) item).getComparisonDetails();
        Assert.assertFalse(details.isEmpty());
        Assert.assertEquals(details.size(), 1);
        final ComparisonResult difference = details.iterator().next();
        Assert.assertTrue(difference instanceof SimpleValueModificationComparisonResult);
        Assert.assertEquals(difference.getProperty(), "affiliations");
        Assert.assertTrue(((SimpleValueModificationComparisonResult) difference).getFirst() instanceof Set);
        Assert.assertTrue(((SimpleValueModificationComparisonResult) difference).getSecond() instanceof Set);
        Assert.assertTrue(((Set) ((SimpleValueModificationComparisonResult) difference).getFirst()).contains(firstAffiliation));
        Assert.assertTrue(((Set) ((SimpleValueModificationComparisonResult) difference).getSecond()).isEmpty());
    }

    @Test
    public void testComparisonOfSimilarArrays() throws Exception {
        final Song first = new Song();
        final Song second = new Song();
        first.setNotes(new String[]{"1", "2", "3"});
        second.setNotes(new String[]{"1", "2", "3"});
        Assert.assertTrue(compare(first, second).isEmpty());
    }

    @Test
    public void testComparisonOfNonSimilarArrays() throws Exception {
        final Song first = new Song();
        final Song second = new Song();
        first.setNotes(new String[]{"2", "3"});
        second.setNotes(new String[]{"1", "2", "3"});
        final Set<ComparisonResult> comparisonResults = compare(first, second);
        Assert.assertFalse(comparisonResults.isEmpty());
        Assert.assertEquals(comparisonResults.size(), 1);
        final ComparisonResult item = comparisonResults.iterator().next();
        Assert.assertTrue(item instanceof SimpleValueModificationComparisonResult);
        Assert.assertEquals(item.getProperty(), "notes");
        Assert.assertEquals(((Object[]) ((SimpleValueModificationComparisonResult) item).getFirst()).length, 2);
        Assert.assertEquals(((Object[]) ((SimpleValueModificationComparisonResult) item).getSecond()).length, 3);
    }

}
