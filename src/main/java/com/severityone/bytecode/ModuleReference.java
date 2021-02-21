package com.severityone.bytecode;

public class ModuleReference extends IdentifierReference {

    public ModuleReference(final String noduleName) {
        super(noduleName);
    }

    public String getName() {
        return getIdentifier()/*.replace('/', '.')*/;
    }

    @Override
    public String toString() {
        return getName();
    }
}
