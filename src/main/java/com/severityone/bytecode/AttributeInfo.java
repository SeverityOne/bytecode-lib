package com.severityone.bytecode;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class AttributeInfo {

    private final String attributeName;
    private final Object[] values;

    public AttributeInfo(final String attributeName, final Object[] values) {
        this.attributeName = attributeName;
        this.values = Arrays.copyOf(values, values.length);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public Object[] getValues() {
        return Arrays.copyOf(values, values.length);
    }
}
