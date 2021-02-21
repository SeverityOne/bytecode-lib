package com.severityone.bytecode;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public final class Main implements Serializable {

    public static void main(final String[] args) throws IOException {

        final ClassFile classFile = ClassReader.readClass(Main.class);
        System.out.printf("Major version: %s%n", classFile.getMajorVersion());
        System.out.printf(
                "%s class %s extends %s%n",
                classFile.getModifiersString(),
                classFile.getClassName(),
                classFile.getSuperClassName());
        final List<ClassReference> interfaces = classFile.getInterfaceTable();
        if (!interfaces.isEmpty()) {
            System.out.printf("implements %s%n", interfaces.stream()
                                                           .map(ClassReference::getName)
                                                           .collect(Collectors.joining(", ")));
        }

        System.out.println("Fields:");
        classFile.getFieldTable()
                 .forEach(f -> System.out.printf("  %s %s%n", Modifier.toString(f.getAccessFlags()), f.getName()));

        System.out.println("Methods:");
        classFile.getMethodTable()
                 .forEach(n -> System.out.printf("  %s %s%n", Modifier.toString(n.getAccessFlags()), n.getName()));

        System.out.println("Attributes:");
        classFile.getAttributeTable()
                 .forEach(a -> System.out.printf("  %s; length: %d%n", a.getAttributeName(), a.getAttributeLength()));

//        System.out.println("Constant pool:");
//        for (int index = 1; index < classFile.getConstantPool().size(); index++) {
//            System.out.printf("  %3d: %s%n", index, classFile.getConstantPool().get(index));
//        }
    }
}
