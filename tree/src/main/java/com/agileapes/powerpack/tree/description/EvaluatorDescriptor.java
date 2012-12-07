package com.agileapes.powerpack.tree.description;

import java.util.List;

public class EvaluatorDescriptor {

    private String name;
    private List<String> parameters;

    public EvaluatorDescriptor(String name, List<String> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluatorDescriptor that = (EvaluatorDescriptor) o;

        return !(name != null ? !name.equals(that.name) : that.name != null) &&
                !(parameters != null ? !parameters.equals(that.parameters) : that.parameters != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }
}
