package com.severityone.bytecode;

public class ConstantPoolEntry {

    private final ConstantPoolTag tag;
    private final Object value;

    public ConstantPoolEntry(final ConstantPoolTag tag, final Object value) {
        this.tag = tag;
        this.value = value;
    }

    public ConstantPoolTag getTag() {
        return tag;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        final StringBuilder string = new StringBuilder(tag.name()).append(": ");
        if (value == null) {
            string.append("<null>");
        } else {
            string.append(value.toString());
        }
        return string.toString();
    }
}
