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

import com.agileapes.powerpack.reflection.beans.BeanAccessor;
import com.agileapes.powerpack.reflection.beans.BeanAccessorFactory;
import com.agileapes.powerpack.reflection.beans.impl.GetterBeanAccessorFactory;
import com.agileapes.powerpack.reflection.compare.BeanComparator;
import com.agileapes.powerpack.reflection.compare.ComparisonStrategy;
import com.agileapes.powerpack.reflection.compare.result.*;
import com.agileapes.powerpack.reflection.exceptions.BeanComparisonException;
import com.agileapes.powerpack.reflection.exceptions.CausalityViolationException;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This comparator will cache the results of each comparison, so that if two items are compared more than once,
 * the whole process is not repeated
 */
public class CachedBeanComparator implements BeanComparator {

    private static final HashMap<Class<?>, ComparisonStrategy> builtIns = new HashMap<Class<?>, ComparisonStrategy>();
    private final Map<ComparisonItem, Set<ComparisonResult>> cache = new HashMap<ComparisonItem, Set<ComparisonResult>>();
    private final BeanAccessorFactory accessorFactory;

    /**
     * This will initialize the comparator using the {@link GetterBeanAccessorFactory} factory
     */
    public CachedBeanComparator() {
        this(new GetterBeanAccessorFactory());
    }

    /**
     * A bean accessor factory can be passed to this constructor to manipulate the way in which it accesses the
     * properties of any given objects
     * @param accessorFactory    the factory
     */
    public CachedBeanComparator(BeanAccessorFactory accessorFactory) {
        this.accessorFactory = accessorFactory;
    }

    static {
        builtIns.put(Integer.class, ComparisonStrategy.DEFAULT);
        builtIns.put(Short.class, ComparisonStrategy.DEFAULT);
        builtIns.put(Long.class, ComparisonStrategy.DEFAULT);
        builtIns.put(String.class, ComparisonStrategy.DEFAULT);
        builtIns.put(Character.class, ComparisonStrategy.DEFAULT);
        builtIns.put(Double.class, ComparisonStrategy.DEFAULT);
        builtIns.put(Float.class, ComparisonStrategy.DEFAULT);
        builtIns.put(Boolean.class, ComparisonStrategy.DEFAULT);
        builtIns.put(BigDecimal.class, ComparisonStrategy.DEFAULT);
        builtIns.put(Date.class, ComparisonStrategy.DEFAULT);
        builtIns.put(java.sql.Date.class, ComparisonStrategy.DEFAULT);
        builtIns.put(Class.class, ComparisonStrategy.DEFAULT);
    }

    @Override
    public <E> Set<ComparisonResult> compare(E first, E second) throws BeanComparisonException {
        return compare(first, second, ComparisonStrategy.DEFAULT);
    }

    @Override
    public <E> Set<ComparisonResult> compare(E first, E second, ComparisonStrategy<Object> comparisonStrategy) throws BeanComparisonException {
        final Set<ComparisonResult> result = new HashSet<ComparisonResult>();
        if (first == null && second == null) {
            return result;
        }
        if (first == null || second == null) {
            return null;
        }
        //After making sure that the NULL factor is dealt with, we look at the previously cached result
        final ComparisonItem comparisonItem = new ComparisonItem(first, second);
        if (cache.containsKey(comparisonItem)) {
            final Set<ComparisonResult> results = cache.get(comparisonItem);
            if (results == null) {
                throw new CausalityViolationException();
            }
            return results;
        }
        cache.put(comparisonItem, null);
        @SuppressWarnings("unchecked") final BeanAccessor<?> firstAccessor = accessorFactory.getBeanAccessor(first);
        @SuppressWarnings("unchecked") final BeanAccessor<?> secondAccessor = accessorFactory.getBeanAccessor(second);
        //We first pool together all available properties
        final Set<String> properties = new HashSet<String>();
        properties.addAll(firstAccessor.getProperties());
        properties.addAll(secondAccessor.getProperties());
        //Then we determine which properties are not in common
        for (String property : properties) {
            if (secondAccessor.hasProperty(property) && firstAccessor.hasProperty(property)) {
                continue;
            }
            if (firstAccessor.hasProperty(property) && !secondAccessor.hasProperty(property)) {
                result.add(new PropertyRemovalComparisonResult(property));
                properties.remove(property); //eliminating removed property
            }
            if (!firstAccessor.hasProperty(property) && secondAccessor.hasProperty(property)) {
                result.add(new PropertyAdditionComparisonResult(property));
                properties.remove(property); //eliminating added property
            }
        }
        //Afterwards, we have only the common properties. So, the difference(s), if any, must lie in their values.
        for (String property : properties) {
            final Object firstValue;
            final Object secondValue;
            try {
                firstValue = firstAccessor.getPropertyValue(property);
                secondValue = secondAccessor.getPropertyValue(property);
            } catch (Throwable e) {
                result.add(new FailedComparisonResult(property, e));
                continue;
            }
            final Set<ComparisonResult> comparisonResults;
            try {
                comparisonResults = compareItems(comparisonStrategy, property, firstValue, secondValue);
            } catch (Throwable e) {
                if (e instanceof BeanComparisonException) {
                    throw (BeanComparisonException) e;
                } else {
                    throw new BeanComparisonException(e);
                }
            }
            result.addAll(comparisonResults);
        }
        //We will cache our result for further reference before returning it
        cache.put(comparisonItem, result);
        return result;
    }

    private Set<ComparisonResult> compareItems(ComparisonStrategy<Object> comparisonStrategy, String property, Object firstValue, Object secondValue) throws BeanComparisonException {
        final Set<ComparisonResult> result = new HashSet<ComparisonResult>();
        //If both values are NULLs then they are considered equals
        if (firstValue == null && secondValue == null) {
            return result;
        }
        //If only one of them is NULL, they are different
        if (firstValue == null || secondValue == null) {
            result.add(new SimpleValueModificationComparisonResult(property, firstValue, secondValue));
            return result;
        }
        //If their types are different, then they are immediately considered as different
        if (!firstValue.getClass().equals(secondValue.getClass())) {
            result.add(new TypeModificationComparisonResult(property, firstValue.getClass(), secondValue.getClass()));
        }
        //If, by definition, the values are equal, we will not inspect them any further.
        if (comparisonStrategy.equals(firstValue, secondValue)) {
            //They are the same :-)
            return result;
        }
        //For primitive types reaching this stage of the comparison means they are definitely different.
        if (firstValue.getClass().isPrimitive()) {
            result.add(new SimpleValueModificationComparisonResult(property, firstValue, secondValue));
            return result;
        }
        //If we know already how to deal with the values, we won't go into details with them
        if (builtIns.containsKey(firstValue.getClass())) {
            //noinspection unchecked
            final ComparisonStrategy<Object> strategy = builtIns.get(firstValue.getClass());
            //noinspection unchecked
            if (!strategy.equals(firstValue, secondValue)) {
                result.add(new SimpleValueModificationComparisonResult(property, firstValue, secondValue));
                return result;
            } else {
                //They are considered to be the same
                return result;
            }
        }
        //Now, it is time to deal with the collection type of properties
        if (firstValue instanceof Set) {
            final Set firstSet = (Set) firstValue;
            final Set secondSet = (Set) secondValue;
            if (compareSets(firstSet, secondSet, comparisonStrategy)) {
                result.add(new SimpleValueModificationComparisonResult(property, firstValue, secondValue));
                return result;
            }
        } else if (firstValue instanceof List) {
            final List firstList = (List) firstValue;
            final List secondList = (List) secondValue;
            if (compareLists(comparisonStrategy, firstList, secondList)) {
                result.add(new SimpleValueModificationComparisonResult(property, firstValue, secondValue));
                return result;
            }
        } else if (firstValue instanceof Map) {
            final Map firstMap = (Map) firstValue;
            final Map secondMap = (Map) secondValue;
            if (compareMaps(comparisonStrategy, firstMap, secondMap)) {
                result.add(new SimpleValueModificationComparisonResult(property, firstValue, secondValue));
                return result;
            }
        } else if (firstValue.getClass().isArray()) {
            final Object[] firstArray = (Object[]) firstValue;
            final Object[] secondArray = (Object[]) secondValue;
            if (compareArrays(comparisonStrategy, firstArray, secondArray)) {
                result.add(new SimpleValueModificationComparisonResult(property, firstArray, secondValue));
                return result;
            }
        }
        //Reaching here means that we need to examine the details of each value to know if they are different
        final Set<ComparisonResult> comparisonResults = compare(firstValue, secondValue, comparisonStrategy);
        if (comparisonResults.isEmpty()) {
            //if no differences could be found, we can consider them as equals
            return result;
        }
        result.add(new ComplexValueModificationComparisonResult(property, comparisonResults));
        return result;
    }

    private boolean compareMaps(ComparisonStrategy<Object> comparisonStrategy, Map firstMap, Map secondMap) throws BeanComparisonException {
        if (compareSets(firstMap.keySet(), secondMap.keySet(), comparisonStrategy)) {
            return true;
        }
        for (Object key : firstMap.keySet()) {
            final Object firstItem = firstMap.get(key);
            final Object secondItem = secondMap.get(key);
            if (firstItem == null && secondItem == null) {
                continue;
            }
            if (firstItem == null || secondItem == null) {
                return true;
            }
            final Set<ComparisonResult> results = compareItems(comparisonStrategy, "", firstItem, secondItem);
            if (!results.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean compareArrays(ComparisonStrategy<Object> comparisonStrategy, Object[] firstArray, Object[] secondArray) {
        final ArrayList<Object> firstList = new ArrayList<Object>();
        final ArrayList<Object> secondList = new ArrayList<Object>();
        Collections.addAll(firstList, firstArray);
        Collections.addAll(secondList, secondArray);
        return compareLists(comparisonStrategy, firstList, secondList);
    }

    private boolean compareLists(ComparisonStrategy<Object> comparisonStrategy, List firstList, List secondList) throws BeanComparisonException {
        if (firstList.size() != secondList.size()) {
            return true;
        }
        for (int i = 0; i < firstList.size(); i++) {
            final Object firstItem = firstList.get(i);
            final Object secondItem = secondList.get(i);
            if (firstItem == null && secondItem == null) {
                continue;
            }
            if (firstItem == null || secondItem == null) {
                return true;
            }
            final Set<ComparisonResult> results = compareItems(comparisonStrategy, Integer.toString(i), firstItem, secondItem);
            if (!results.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean compareSets(Set firstSet, Set secondSet, ComparisonStrategy<Object> comparisonStrategy) throws BeanComparisonException {
        if (firstSet.size() != secondSet.size()) {
            return true;
        }
        int i = 0;
        for (final Object firstItem : firstSet) {
            boolean found = false;
            for (final Object secondItem : secondSet) {
                final Set<ComparisonResult> results = compareItems(comparisonStrategy, Integer.toString(i), firstItem, secondItem);
                if (results.isEmpty()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return true;
            }
            i ++;
        }
        return false;
    }

    private static class ComparisonItem {

        private Object first;
        private Object second;

        private ComparisonItem(Object first, Object second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof ComparisonItem) {
                ComparisonItem item = (ComparisonItem) object;
                if (item.first.equals(this.first) && item.second.equals(this.second) ||
                        item.first.equals(this.second) && item.second.equals(this.first)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 1;
            result *= (second != null ? second.hashCode() : 1);
            return result;
        }
    }

}
