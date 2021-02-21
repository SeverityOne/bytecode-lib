package com.severityone.bytecode;

public class ClassReference extends IdentifierReference {

    public ClassReference(final String className) {
        super(className);
    }

    public String getName() {
        return getIdentifier().replace('/', '.');
    }

    @Override
    public String toString() {
        return getName();
    }
}
