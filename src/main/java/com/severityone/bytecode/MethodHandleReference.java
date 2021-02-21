package com.severityone.bytecode;

public class MethodHandleReference implements ConstantPoolValue {

    private final TypeDescriptor typeDescriptor;
    private final MemberReference methodReference;

    public MethodHandleReference(final TypeDescriptor typeDescriptor, final MemberReference methodReference) {
        this.typeDescriptor = typeDescriptor;
        this.methodReference = methodReference;
    }

    public TypeDescriptor getTypeDescriptor() {
        return typeDescriptor;
    }

    public MemberReference getMethodReference() {
        return methodReference;
    }

    @Override
    public String toString() {
        return getTypeDescriptor().toString() + ' ' + getMethodReference().toString();
    }
}
