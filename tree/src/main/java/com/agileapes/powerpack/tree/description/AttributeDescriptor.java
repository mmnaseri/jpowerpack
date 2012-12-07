package com.agileapes.powerpack.tree.description;

public class AttributeDescriptor {

    private String name;
    private String value;
    public static final String DEFAULT_VALUE = ".*";

    public AttributeDescriptor(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeDescriptor that = (AttributeDescriptor) o;
        return !(name != null ? (!name.matches(that.name) && that.name != null && !that.name.matches(name)) : that.name != null) &&
                !(value != null ? !value.matches(that.value) && that.name != null && !that.name.matches(name) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
