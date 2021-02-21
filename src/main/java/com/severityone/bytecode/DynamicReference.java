package com.severityone.bytecode;

public class DynamicReference {

    private final int bootstreapMethodIndex;
    private final NameAndTypeDescriptor nameAndTypeDescriptor;

    public DynamicReference(final int bootstreapMethodIndex, final NameAndTypeDescriptor nameAndTypeDescriptor) {
        this.bootstreapMethodIndex = bootstreapMethodIndex;
        this.nameAndTypeDescriptor = nameAndTypeDescriptor;
    }

    public int getBootstreapMethodIndex() {
        return bootstreapMethodIndex;
    }

    public NameAndTypeDescriptor getNameAndTypeDescriptor() {
        return nameAndTypeDescriptor;
    }

    @Override
    public String toString() {
        return String.valueOf(bootstreapMethodIndex) + ' ' + nameAndTypeDescriptor.toString();
    }
}
