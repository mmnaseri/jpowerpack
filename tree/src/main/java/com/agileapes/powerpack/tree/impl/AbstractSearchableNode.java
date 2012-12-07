package com.agileapes.powerpack.tree.impl;

import com.agileapes.powerpack.string.reader.DocumentReader;
import com.agileapes.powerpack.string.reader.TokenDescriptor;
import com.agileapes.powerpack.string.reader.TokenDesignator;
import com.agileapes.powerpack.string.reader.impl.ContainedTextParser;
import com.agileapes.powerpack.string.reader.impl.PositionAwareDocumentReader;
import com.agileapes.powerpack.string.reader.impl.SimpleTokenDescriptor;
import com.agileapes.powerpack.tree.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 13:02)
 */
public abstract class AbstractSearchableNode implements Node {

    private static final Character SECTION_SEPARATOR = '/';
    private static final String QUOTATION = "'\"`";
    private static final Character ESCAPE = '\\';

    private static class QuerySectionDesignator implements TokenDesignator {

        @Override
        public TokenDescriptor getToken(String string) {
            int pos = 0;
            Character quote = null;
            while (pos < string.length()) {
                if (pos > 0 && string.charAt(pos) == SECTION_SEPARATOR && quote == null) {
                    break;
                }
                if (quote == null) {
                    if (QUOTATION.contains(Character.toString(string.charAt(pos)))) {
                        quote = string.charAt(pos);
                    }
                } else {
                    if (string.substring(pos).startsWith(ESCAPE.toString() + quote)) {
                        pos += 2;
                        continue;
                    }
                    if (string.charAt(pos) == quote) {
                        quote = null;
                    }
                }
                pos++;
            }
            return new SimpleTokenDescriptor(pos);
        }

    }

    @Override
    public List<Node> find(String query) {
        final DocumentReader reader = new PositionAwareDocumentReader(query, new QuerySectionDesignator());
        reader.skip(PositionAwareDocumentReader.WHITESPACE);
        String current = reader.parse(new ContainedTextParser(QUOTATION, QUOTATION, ESCAPE, true, true));
        final String rest = reader.rest();
        boolean absolute = false;
        if (current.charAt(0) == '/') {
            absolute = true;
            current = current.substring(1);
        }
        List<Node> agenda = new ArrayList<Node>();
        if (absolute) {
            if (!this.matches(current)) {
                return Collections.emptyList();
            }
            agenda.add(this);
        } else {
            if (this.matches(current)) {
                agenda.add(this);
            }
            for (Node child : this.getChildren()) {
                agenda.addAll(child.find(current));
            }
        }
        if (rest.isEmpty()) {
            return agenda;
        } else {
            reader.expect(Pattern.compile(SECTION_SEPARATOR.toString()), false);
        }
        List<Node> result = new ArrayList<Node>();
        for (Node item : agenda) {
            for (Node node : item.getChildren()) {
                final List<Node> exploration = node.find(rest);
                result.addAll(exploration);
            }
        }
        return result;
    }

}
