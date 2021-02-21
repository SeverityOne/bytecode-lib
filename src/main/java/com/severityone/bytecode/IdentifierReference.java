package com.severityone.bytecode;

public class IdentifierReference implements ConstantPoolValue {

    private final String identifier;

    protected IdentifierReference(final String identifier) {
        this.identifier = identifier;
    }

    protected String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
