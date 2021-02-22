package com.severityone.bytecode;

import java.nio.ByteBuffer;

public class ConstantPool {

    private final ConstantPoolEntryInitialiser[] entries;

    public ConstantPool(final ByteBuffer buffer) {
        final int constantPoolCount = buffer.getShort();
        entries = new ConstantPoolEntryInitialiser[constantPoolCount];
        for (int index = 1; index < constantPoolCount; ) {
            final ConstantPoolTag tag = ConstantPoolTag.from(buffer.get());
            entries[index] = new ConstantPoolEntryInitialiser(tag, tag.getReader().read(buffer));
            index += tag.getSize();
        }
    }

    public String getString(final int index) {
        return (String) get(index, ConstantPoolTag.STRING);
    }

    public ClassReference getClassRef(final int index) {
        return (ClassReference) get(index, ConstantPoolTag.CLASS_REF);
    }

    public NameAndTypeDescriptor getNameAndTypeDescriptor(final int index) {
        return (NameAndTypeDescriptor) get(index, ConstantPoolTag.NAME_AND_TYPE);
    }

    public MemberReference getMethodRef(final int index) {
        return (MemberReference) get(index, ConstantPoolTag.METHOD_REF);
    }

    public ConstantPoolEntry get(final int index) {
        if (index < 1 || index >= entries.length) {
            throw new ArrayIndexOutOfBoundsException(index + " is out of bounds");
        }
        return entries[index].getEntry(this);
    }

    public int size() {
        return entries.length;
    }

    private Object get(final int index, final ConstantPoolTag expectedTag) {
        final ConstantPoolEntry entry = get(index);
        if (entry.getTag() != expectedTag) {
            throw new IllegalArgumentException("Expected tag " + expectedTag + " but found " + entry.getTag());
        }
        return entry.getValue();
    }
}
