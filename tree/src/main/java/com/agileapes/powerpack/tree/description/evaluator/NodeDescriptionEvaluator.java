package com.agileapes.powerpack.tree.description.evaluator;

import com.agileapes.powerpack.reflection.tools.MethodFilter;
import com.agileapes.powerpack.reflection.tools.ReflectionUtils;
import com.agileapes.powerpack.tree.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2012/12/4, 16:51)
 */
public abstract class NodeDescriptionEvaluator {

    private Node node;

    public abstract String getName();

    public void setNode(Node node) {
        this.node = node;
    }

    protected Node getNode() {
        return node;
    }

    /**
     * Will evaluate the given node using the specified parameters.
     *
     * The method will look for a <em>public</em> method named <code>matches</code> that
     * returns either <code>boolean</code> or <code>Boolean</code> and attempts to invoke it
     * for the specified criteria
     *
     * This method will automatically convert the given String parameters into the types
     * required by the evaluator. The process uses the list of arguments provided for the
     * <code>public boolean matches( ... )</code> method in the current instance of the
     * subclass.
     *
     * @param parameters    the parameters being passed to the target evaluator
     * @return <code>true</code> if the evaluation succeeds for this node
     */
    public boolean evaluate(List<String> parameters) {
        final Method[] methods = ReflectionUtils.getMethods(getClass(), new MethodFilter() {
            @Override
            public boolean accept(Method item) {
                return item.getName().equals("matches") && (item.getReturnType().equals(Boolean.class) ||
                        (item.getReturnType().isPrimitive() && item.getReturnType().getName().equals("boolean")))
                        && Modifier.isPublic(item.getModifiers()) && !Modifier.isAbstract(item.getModifiers()) &&
                        !Modifier.isStatic(item.getModifiers());
            }
        });
        if (methods.length != 1) {
            throw new IllegalStateException("Invalid definition for evaluator: " + getClass().getName());
        }
        Method method = methods[0];
        if (!method.getReturnType().isPrimitive() && !method.getReturnType().equals(Boolean.class)) {
            throw new IllegalStateException(getClass().getName() + ".matches( ... ) must return boolean");
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalStateException(getClass().getName() + ".matches( ... ) must be declared public");
        }
        if (method.getReturnType().isPrimitive() && !method.getReturnType().getName().equals("boolean")) {
            throw new IllegalStateException(getClass().getName() + ".matches( ... ) must return boolean");
        }
        if (parameters.size() != method.getParameterTypes().length) {
            throw new IllegalArgumentException("Expected " + method.getParameterTypes().length + " parameters while got " + parameters.size());
        }
        Object[] arguments = new Object[parameters.size()];
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            final Class<?> type = method.getParameterTypes()[i];
            Object value = null;
            String item = parameters.get(i);
            if ((type.isPrimitive() && type.getName().equals("int")) || type.equals(Integer.class)) {
                try {
                    value = Integer.parseInt(item);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(item + " is not a valid integer");
                }
            } else if ((type.isPrimitive() && type.getName().equals("double")) || type.equals(Double.class)) {
                try {
                    value = Double.parseDouble(item);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(item + " is not a valid double");
                }
            } else if ((type.isPrimitive() && type.getName().equals("float")) || type.equals(Float.class)) {
                try {
                    value = Float.parseFloat(item);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(item + " is not a valid float");
                }
            } else if ((type.isPrimitive() && type.getName().equals("boolean")) || type.equals(Boolean.class)) {
                try {
                    value = Boolean.parseBoolean(item);
                } catch (Exception e) {
                    throw new IllegalArgumentException(item + " is not a valid boolean");
                }
            } else if ((type.isPrimitive() && type.getName().equals("short")) || type.equals(Short.class)) {
                try {
                    value = Short.parseShort(item);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(item + " is not a valid short integer");
                }
            } else if ((type.isPrimitive() && type.getName().equals("long")) || type.equals(Long.class)) {
                try {
                    value = Long.parseLong(item);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(item + " is not a valid long number");
                }
            } else if (type.isEnum()) {
                item = item.toUpperCase();
                for (Object constant : type.getEnumConstants()) {
                    if (constant.toString().equals(item)) {
                        value = constant;
                    }
                }
                if (value == null) {
                    throw new IllegalArgumentException("Invalid enum constant: " + item);
                }
            } else if (type.equals(String.class)) {
                value = item;
            } else {
                throw new IllegalStateException("Unhandled type: " + type.getName());
            }
            arguments[i] = value;
        }
        try {
            return (Boolean) method.invoke(this, arguments);
        } catch (IllegalAccessException ignored) {
        } catch (InvocationTargetException e) {
            throw new Error("Action aborted due to an error", e.getCause());
        }
        return false;
    }


}
