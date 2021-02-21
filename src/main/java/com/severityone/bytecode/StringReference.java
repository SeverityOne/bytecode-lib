package com.severityone.bytecode;

public class StringReference implements ConstantPoolValue {

    private final int value;
    private final ConstantPool constantPool;

    public StringReference(final int nameIndex, final ConstantPool constantPool) {
        this.value = nameIndex;
        this.constantPool = constantPool;
    }

    public String getValue() {
        return constantPool.getString(value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
