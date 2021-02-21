package com.severityone.bytecode;

public class MethodTypeReference extends IdentifierReference {

    public MethodTypeReference(final String name) {
        super(name);
    }

    public String getName() {
        return getIdentifier();
    }
}
