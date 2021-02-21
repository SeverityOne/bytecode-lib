package com.severityone.bytecode;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MethodHandle {

    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final int attributesCount;
    private final List<AttributeInfo> attributes;
    private final ConstantPool constantPool;

    public MethodHandle(final ByteBuffer buffer, final ConstantPool constantPool) {
        accessFlags = buffer.getShort();
        nameIndex = buffer.getShort();
        descriptorIndex = buffer.getShort();
        attributesCount = buffer.getShort();
        attributes = IntStream.range(0, attributesCount)
                              .mapToObj(i -> new AttributeInfo(buffer, constantPool))
                              .collect(Collectors.toList());
        this.constantPool = constantPool;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public String getName() {
        return constantPool.getString(nameIndex);
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public int getAttributesCount() {
        return attributesCount;
    }

    public List<AttributeInfo> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }
}
