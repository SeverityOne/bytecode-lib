package com.severityone.bytecode;

public class MemberReference implements ConstantPoolValue {

    private final ClassReference classReference;
    private final NameAndTypeDescriptor nameAndTypeDescriptor;

    public MemberReference(final ClassReference classReference, final NameAndTypeDescriptor nameAndTypeDescriptor) {
        this.classReference = classReference;
        this.nameAndTypeDescriptor = nameAndTypeDescriptor;
    }

    public ClassReference getClassReference() {
        return classReference;
    }

    public NameAndTypeDescriptor getNameAndTypeDescriptor() {
        return nameAndTypeDescriptor;
    }

    @Override
    public String toString() {
        return getClassReference().toString() + '.' +
               getNameAndTypeDescriptor().getName() + ' ' +
               getNameAndTypeDescriptor().getType();
    }
}
