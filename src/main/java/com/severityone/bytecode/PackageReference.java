package com.severityone.bytecode;

public class PackageReference extends IdentifierReference {

    public PackageReference(final String packageName) {
        super(packageName);
    }

    public String getName() {
        return getIdentifier()/*.replace('/', '.')*/;
    }

    @Override
    public String toString() {
        return getName();
    }
}
