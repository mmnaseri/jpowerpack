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

import java.util.regex.Pattern;

/**
 * The DocumentReader will expose tools for parsing a document.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/3, 16:49)
 */
public interface DocumentReader {

    /**
     * This should give report as to where the reader stands within the document at this moment
     * @return the part of document currently read through
     */
    String taken();

    /**
     * This will return the parts of document not yet read through by the reader
     * @return the part of the document not processed by the reader
     */
    String rest();

    /**
     * This method will skip all the characters matching the pattern from this point in the document
     * onwards (if any)
     */
    void skip(Pattern pattern);

    /**
     * This method will determine whether the indicated pattern can be found at this point in the document or not
     * @param pattern    the lookup pattern
     * @return <code>true</code> if the pattern can be found
     */
    boolean has(Pattern pattern);

    /**
     * This method will determine if another token can be found in the document
     * @return <code>true</code> if anything other than whitespace characters exist within the rest of the document
     */
    boolean hasNext();

    /**
     * Determines whether we have hit the end of the document or not
     * @return <code>true</code> if we have no more to go
     */
    boolean hasMore();

    /**
     * Will determine whether the rest of the document matches the given regular expression
     * @param pattern    the query regular expression
     * @return <code>true</code> if the document can be probed for the given regular expression
     */
    boolean matches(Pattern pattern);

    /**
     * Will return the next character
     * @return next character in the document stream
     */
    char nextChar();

    /**
     * Will give the next token. If no tokens can be found, the method will either take further action
     * to fix this, or throw an Exception. You can confer {@link #hasNext()} to see if another token exists
     * in the document.<br/>
     * <strong>NB</strong> This method will attempt to read the <em>first</em> identifiable token, meaning
     * that if one token is the prefix of another, then the first will be discovered.
     * @return next token (if any can be found)
     */
    String nextToken();

    /**
     * Will attempt to read the string matching the given parameter. If the string matched with this pattern
     * does not start at the current point in the document, the result will be considered to be negative.
     * @param pattern            the compiled pattern to be matched against
     * @param skipWhitespaces    will cause a call to {@link #skip(java.util.regex.Pattern)} with whitespace pattern
     *                           before going forth
     * @return the string read by the method, or <code>null</code> if it cannot be found
     * @see Pattern#compile(String)
     * @see Pattern#compile(String, int)
     */
    String read(Pattern pattern, boolean skipWhitespaces);

    /**
     * This will attempt to read string matching the given pattern from the document at the current point
     * indicated by the cursor. If failed to do so, the method will be expected to throw an exception or take corrective measures.
     * @param pattern            the regular to query for
     * @param skipWhitespaces    will cause a call to {@link #skip(java.util.regex.Pattern)} with whitespace pattern
     *                           before going forth
     * @return the read string
     */
    String expect(Pattern pattern, boolean skipWhitespaces);

    /**
     * This will cause the state of the reading to be reset. The cursor will be set back to the beginning of the document,
     * and the line/column positioning data will be reset to their initial value.
     */
    void reset();

    /**
     * This will insert the given string into the point indicated by the cursor for the document
     * @param string    the string to be inserted
     */
    void put(String string);

    /**
     * This method will give the control momentarily to the given {@link SnippetParser} instance.
     * @param parser    the parser to give over the flow to
     */
    String parse(SnippetParser parser);

    /**
     * This should return a snapshot of current position/processing data about the reader's
     * status at the moment
     * @return snapshot of the reader's positioning status
     * @see #restore(ReaderSnapshot)
     */
    ReaderSnapshot snapshot();

    /**
     * Given a snapshot, this method should restore the state of the reader
     * @param snapshot    the snapshot to be used
     * @see #snapshot()
     */
    void restore(ReaderSnapshot snapshot);

    /**
     * Takes out (removes) the specified number of characters from the document from the
     * position currently indicated by the cursor.
     * @param offset    number of characters to be skipped.
     */
    void take(int offset);

    /**
     * Peeks ahead for the specified length of characters
     * @param length    the number of characters to read
     * @return the string with the specified length
     */
    String peek(int length);

}
