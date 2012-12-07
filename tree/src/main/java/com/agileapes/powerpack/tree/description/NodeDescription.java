package com.agileapes.powerpack.tree.description;

import com.agileapes.powerpack.string.reader.DocumentReader;
import com.agileapes.powerpack.string.reader.SnippetParser;
import com.agileapes.powerpack.string.reader.TokenDescriptor;
import com.agileapes.powerpack.string.reader.TokenDesignator;
import com.agileapes.powerpack.string.reader.impl.ContainedTextParser;
import com.agileapes.powerpack.string.reader.impl.NonWhitespaceTokenDesignator;
import com.agileapes.powerpack.string.reader.impl.PositionAwareDocumentReader;
import com.agileapes.powerpack.string.reader.impl.SimpleTokenDescriptor;
import com.agileapes.powerpack.tree.Node;
import com.agileapes.powerpack.tree.description.evaluator.NodeEvaluatorFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 16:42)
 */
public class NodeDescription {

    private static class ParameterDesignator implements TokenDesignator {

        @Override
        public TokenDescriptor getToken(String string) {
            int offset = 0;
            while (offset < string.length() && Character.isWhitespace(string.charAt(offset))) {
                offset ++;
            }
            int pos = offset;
            while (pos < string.length() && !",)".contains(String.valueOf(string.charAt(pos)))) {
                pos ++;
            }
            return new SimpleTokenDescriptor(pos - offset, offset);
        }

    }

    public static final String DEFAULT_NAME = ".*";
    public static final int DEFAULT_INDEX = -1;
    public static final String QUOTATION = "`\"'";
    private int index = DEFAULT_INDEX;
    private String name = DEFAULT_NAME;
    private Set<AttributeDescriptor> attributes = new HashSet<AttributeDescriptor>();
    private Set<EvaluatorDescriptor> evaluators = new HashSet<EvaluatorDescriptor>();

    /**
     * This method will compile a {@link NodeDescription} from the given description text.
     * <p/>
     * The text description is of the following format:
     * <p/>
     * <code>name index attributes evaluators</code>
     * <ul>
     * <li><strong>name</strong> the name of the node. This is a regular expression. If no name
     * is provided, <code>.*</code> is selected as default, which will match all nodes regardless
     * of their actual name.</li>
     * <il><strong>index</strong> the index, which must be prefixed by the character <code>#</code>
     * is a positive integer indicating the index of the target node. Note that the indices
     * are numbered from <code>0</code> onwards. The index, is the numerical identifier of any
     * XML node at its level in the XML tree hierarchy</il>
     * <li><strong>attributes</strong> the attributes selector must be enclosed in a pair of square
     * brackets (<code>[]</code>) and will indicate the list of attributes separated by commas. Each
     * attribute selector must have a name, and can optionally have a value. The value can be quoted
     * using any of the quotation characters <code>'</code>, <code>"</code>, or <code>`</code>. The
     * name and the value must be separated using a <code>=</code> sign and are both regular
     * expressions, and as such allow wildcard selections</li>
     * <li><strong>evaluators</strong> evaluators are functions that can be plugged into the matching
     * process. They are subclasses of {@link com.agileapes.powerpack.tree.description.evaluator.NodeDescriptionEvaluator}. Each evaluator must start
     * with the name of that evaluator. After the evaluator's name a pair of round brackets must follow,
     * which can enclose a list of arguments intended for the target evaluator. Like attribute values,
     * parameters <em>can</em> be quoted using one of <code>'</code>, <code>"</code>, or <code>`</code>.
     * If more than one evaluator is presented for the current description, they have to be separated by
     * a single semicolon. A trailing semicolon for the last evaluator is optional.<br/>
     * The list of evaluators must be enclosed within a pair of curly brackets (<code>{}</code>).
     * </li>
     * </ul>
     * <p/>
     * <strong>NB</strong>&nbsp; All of the above four sections of the text description are
     * optional, and can thus be dropped from the description. However, note that an empty
     * description will be <em>all-inclusive</em>, i.e. it will prove true for all nodes in
     * any given XML structure.
     * <p/>
     * <p><strong>A Few Examples</strong></p>
     * <p/>
     * <ol>
     * <li><code>c.* {hasChild('d.*'); hasChild('e.*')}</code> will describe a node whose name starts
     * with the letter 'c' and has two children whose names start with the letters 'd'
     * and 'e', respectively</li>
     * <li><code>.* {next('form')}</code> will describe a node whose next sibling at the
     * same level in the XML structure's hierarchy is <code>&lt;form/&gt;</code> element</li>
     * <li><code>[.*='plane.*']</code> will describe a node which has at least one attribute
     * with a value starting with the word 'plane'</li>
     * <li><code>form {child('element[name=username]');}</code> will describe a node whose name
     * is 'form', and has at least one child with the name 'element', and an attribute 'name'
     * which has the value 'username'</li>
     * </ol>
     *
     * @param description the description
     * @return the compiled metadata
     * @see com.agileapes.powerpack.tree.description.evaluator.NodeDescriptionEvaluator
     */
    public static NodeDescription compile(String description) {
        final NodeDescription result = new NodeDescription();
        final DocumentReader parser = new PositionAwareDocumentReader(description, new NonWhitespaceTokenDesignator());
        parseName(result, parser);
        parseIndex(result, parser);
        parseAttributes(result, parser);
        parseEvaluators(result, parser);
        if (parser.hasMore()) {
            throw new IllegalArgumentException("Unexpected token " + parser.nextToken() + " in node description");
        }
        return result;
    }

    private static void parseEvaluators(final NodeDescription result, DocumentReader parser) {
        parser.parse(new SnippetParser() {

            @Override
            public String parse(DocumentReader parser) {
                if (!parser.has("\\{")) {
                    return null;
                }
                parser.expect("\\{", true);
                while (!parser.has("\\}")) {
                    final String name = parser.nextToken("\\(");
                    final List<String> parameters = new ArrayList<String>();
                    parser.expect("\\(", true);
                    while (parser.hasMore()) {
                        final String parameter = parser.parse(new ContainedTextParser(QUOTATION, QUOTATION, '\\', true, true, new ParameterDesignator()));
                        if (parameter == null) {
                            parser.expect("\\)", true);
                            break;
                        }
                        if (parameter.isEmpty()) {
                            throw new IllegalArgumentException("Expected parameter while found " + parser.nextChar());
                        }
                        parameters.add(parameter);
                        if (parser.read(",", true) == null) {
                            parser.expect("\\)", true);
                            break;
                        } else if (parser.has("\\)")) {
                            throw new IllegalArgumentException("Expected parameter while found ')'");
                        }
                    }
                    if (!parser.has(";")) {
                        if (!parser.has("\\}")) {
                            throw new IllegalArgumentException("Expected '}' or ';' while found '" + parser.nextToken() + "'");
                        }
                    } else {
                        parser.expect(";", true);
                    }
                    result.addEvaluator(name, parameters);
                }
                parser.expect("\\}", true);
                return null;
            }
        });
    }

    private static void parseAttributes(final NodeDescription result, DocumentReader parser) {
        parser.parse(new SnippetParser() {
            @Override
            public String parse(DocumentReader parser) {
                if (!parser.has("\\[")) {
                    return null;
                }
                parser.expect("\\[", true);
                while (parser.hasMore() && !parser.has("]")) {
                    String name = parser.nextToken(",", "]", "=");
                    if (name == null) {
                        parser.expect("]", true);
                        break;
                    }
                    if (name.isEmpty()) {
                        throw new IllegalArgumentException("Expected attribute name, but found: " + parser.nextChar());
                    }
                    String value = AttributeDescriptor.DEFAULT_VALUE;
                    if (parser.read("=", true) != null) {
                        value = parser.parse(new ContainedTextParser(QUOTATION, QUOTATION, '\\', true, true, new ParameterDesignator()));
                    }
                    result.setAttribute(name, value);
                    if (parser.read(",", true) == null) {
                        break;
                    } else if (parser.has("\\]")) {
                        throw new IllegalArgumentException("Expected attribute while found ']'");
                    }
                }
                parser.expect("\\]", true);
                return null;
            }
        });
    }

    private static void parseIndex(final NodeDescription result, DocumentReader parser) {
        parser.parse(new SnippetParser() {
            @Override
            public String parse(DocumentReader parser) {
                if (!parser.has("#\\d+")) {
                    return null;
                }
                parser.expect("#", true);
                final String value = parser.nextToken("\\[", "\\{");
                result.setIndex(Integer.parseInt(value));
                return value;
            }
        });
    }

    private static void parseName(final NodeDescription result, DocumentReader parser) {
        parser.parse(new SnippetParser() {
            @Override
            public String parse(DocumentReader parser) {
                if (!parser.hasMore()) {
                    return null;
                }
                if (parser.has("#\\d+") || parser.has("\\[") || parser.has("\\{")) {
                    return null;
                }
                final String name = parser.nextToken("#", "\\[", "\\{");
                result.setName(name);
                return name;
            }

        });
    }

    private NodeDescription() {
    }

    private void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void addEvaluator(String name, List<String> parameters) {
        evaluators.add(new EvaluatorDescriptor(name, parameters));
    }

    private void setAttribute(String name, String value) {
        attributes.add(new AttributeDescriptor(name, value));
    }

    /**
     * This will return the attribute descriptors for the current
     * node description
     *
     * @return attributes
     */
    public Set<AttributeDescriptor> getAttributes() {
        return attributes;
    }

    /**
     * This will return the evaluator descriptors for the current
     * node description
     *
     * @return evaluators
     */
    public Set<EvaluatorDescriptor> getEvaluators() {
        return evaluators;
    }

    /**
     * This will return the {@link NodeMatcher} instance for this description with
     * the default node evaluator factory
     *
     * @return the matcher
     * @see #matcher(com.agileapes.powerpack.tree.description.evaluator.NodeEvaluatorFactory)
     * @see com.agileapes.powerpack.tree.description.evaluator.NodeEvaluatorFactory
     */
    public NodeMatcher matcher() {
        return new NodeMatcher(this, null);
    }

    /**
     * This will return the {@link NodeMatcher} instance for this description with
     * the given node evaluator factory
     *
     * @param factory the evaluator factory
     * @return the matcher
     * @see com.agileapes.powerpack.tree.description.evaluator.NodeEvaluatorFactory
     */
    public NodeMatcher matcher(NodeEvaluatorFactory factory) {
        return new NodeMatcher(this, factory);
    }

    /**
     * This will instantiate a matcher and use it to test the given node, using the default
     * {@link NodeEvaluatorFactory} instance associated with the matcher
     *
     * @param node the node to be tested
     * @return <code>true</code> if the given node can be described using this description
     * @see #matcher()
     * @see NodeMatcher
     */
    public boolean test(Node node) {
        return matcher().test(node);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getIndex() > 0) {
            builder.append("#").append(getIndex());
        }
        builder.append("[");
        int i = 0;
        for (AttributeDescriptor attribute : attributes) {
            builder.append(attribute.getName()).append("=").append("`");
            builder.append(attribute.getValue().replace("`", "\\`")).append("`");
            if (i++ < attributes.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]").append("{");
        for (EvaluatorDescriptor evaluator : evaluators) {
            builder.append(evaluator.getName()).append("(");
            for (int j = 0; j < evaluator.getParameters().size(); j++) {
                builder.append("`");
                builder.append(evaluator.getParameters().get(j).replace("`", "\\`"));
                builder.append("`");
                if (j < evaluator.getParameters().size() - 1) {
                    builder.append(",");
                }
            }
            builder.append(")");
            builder.append(";");
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeDescription that = (NodeDescription) o;
        return index == that.index &&
                !(attributes != null ? !attributes.equals(that.attributes) : that.attributes != null) &&
                !(evaluators != null ? !evaluators.equals(that.evaluators) : that.evaluators != null) &&
                !(name != null ? !name.matches(that.name) && (that.name != null && !that.name.matches(name)) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (evaluators != null ? evaluators.hashCode() : 0);
        return result;
    }

}
