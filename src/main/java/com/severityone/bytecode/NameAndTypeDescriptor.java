package com.severityone.bytecode;

public class NameAndTypeDescriptor implements ConstantPoolValue {

    private final String name;
    private final String type;

    public NameAndTypeDescriptor(final String name, final String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return getType() + ' ' + getName();
    }
}
