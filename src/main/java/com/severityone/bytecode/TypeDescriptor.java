package com.severityone.bytecode;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TypeDescriptor {

    GET_FIELD(1, "getField"),
    GET_STATIC(2, "getStatic"),
    PUT_FIELD(3, "putField"),
    PUT_STATIC(4, "putStatic"),
    INVOKE_VIRTUAL(5, "invokeVirtual"),
    INVOKE_STATIC(6, "invokeStatic"),
    INVOKE_SPECIAL(7, "invokeSpecial"),
    NEW_INVOKE_SPECIAL(8, "newInvokeSpecial"),
    INVOKE_INTERFACE(9, "invokeInterface");

    private final int type;
    private final String descriptor;

    private final static Map<Integer, TypeDescriptor> FROM_INT =
            Arrays.stream(values())
                  .collect(Collectors.toMap(TypeDescriptor::getType, Function.identity()));

    TypeDescriptor(final int type, final String descriptor) {
        this.type = type;
        this.descriptor = descriptor;
    }

    public static TypeDescriptor from(final int type) {
        return Optional.ofNullable(FROM_INT.get(type))
                       .orElseThrow(() -> new IllegalArgumentException("Unknown type " + type));
    }

    public int getType() {
        return type;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
