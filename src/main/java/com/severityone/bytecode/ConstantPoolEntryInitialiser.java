package com.severityone.bytecode;

public class ConstantPoolEntryInitialiser {

    private final ConstantPoolTag tag;
    private final Generator generator;
    private Object value;

    public ConstantPoolEntryInitialiser(final ConstantPoolTag tag, final Generator generator) {
        this.tag = tag;
        this.generator = generator;
    }

    public ConstantPoolEntry getEntry(final ConstantPool constantPool) {
        if (value == null) {
            value = generator.generate(constantPool);
        }
        return new ConstantPoolEntry(tag, value);
    }
}
