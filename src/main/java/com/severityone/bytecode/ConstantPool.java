package com.severityone.bytecode;

import com.severityone.bytecode.ConstantPoolTag.Generator;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ConstantPool {

    private final ConstantPoolTag[] tags;
    private final Generator[] generators;
    private final Map<Integer, ConstantPoolEntry> entries = new HashMap<>();

    public ConstantPool(final ByteBuffer buffer) {
        final int constantPoolCount = buffer.getShort();
        tags = new ConstantPoolTag[constantPoolCount];
        generators = new Generator[constantPoolCount];
        for (int index = 1; index < constantPoolCount; ) {
            final ConstantPoolTag tag = ConstantPoolTag.from(buffer.get());
            tags[index] = tag;
            generators[index] = tag.getReader().read(buffer);
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
        if (index < 1 || index >= tags.length) {
            throw new ArrayIndexOutOfBoundsException(index + " is out of bounds");
        }
        return entries.computeIfAbsent(
                index,
                i -> new ConstantPoolEntry(tags[i], generators[i].generate(this)));
    }

    public int size() {
        return entries.size();
    }

    private Object get(final int index, final ConstantPoolTag expectedTag) {
        final ConstantPoolEntry entry = get(index);
        if (entry.getTag() != expectedTag) {
            throw new IllegalArgumentException("Expected tag " + expectedTag + " but found " + entry.getTag());
        }
        return entry.getValue();
    }
}
