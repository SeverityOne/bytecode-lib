package com.severityone.bytecode;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class AttributeInfo {

    private final int attributeNameIndex;
    private final int attributeLength;
    private final byte[] info;
    private final ConstantPool constantPool;

    public AttributeInfo(final ByteBuffer buffer, final ConstantPool constantPool) {
        attributeNameIndex = buffer.getShort();
        attributeLength = buffer.getInt();
        info = new byte[attributeLength];
        buffer.get(info);
        this.constantPool = constantPool;
    }

    public int getAttributeNameIndex() {
        return attributeNameIndex;
    }

    public String getAttributeName() {
        return constantPool.getString(attributeNameIndex);
    }

    public int getAttributeLength() {
        return attributeLength;
    }

    public byte[] getInfo() {
        return Arrays.copyOf(info, info.length);
    }
}
