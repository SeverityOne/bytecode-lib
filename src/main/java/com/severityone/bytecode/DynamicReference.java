package com.severityone.bytecode;

public class DynamicReference {

    private final int bootstrapMethodIndex;
    private final NameAndTypeDescriptor nameAndTypeDescriptor;

    public DynamicReference(final int bootstrapMethodIndex, final NameAndTypeDescriptor nameAndTypeDescriptor) {
        this.bootstrapMethodIndex = bootstrapMethodIndex;
        this.nameAndTypeDescriptor = nameAndTypeDescriptor;
    }

    public int getBootstrapMethodIndex() {
        return bootstrapMethodIndex;
    }

    public NameAndTypeDescriptor getNameAndTypeDescriptor() {
        return nameAndTypeDescriptor;
    }

    @Override
    public String toString() {
        return String.valueOf(bootstrapMethodIndex) + ' ' + nameAndTypeDescriptor.toString();
    }
}
