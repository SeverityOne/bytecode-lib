package com.severityone.bytecode;

import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ClassFile {

    private final ByteBuffer buffer;

    private final int minorVersion;
    private final ClassFileVersion majorVersion;
    private final int accessFlags;
    private final int thisIndex;
    private final int superIndex;

    private final ConstantPool constantPool;
    private final List<ClassReference> interfaceTable;
    private final List<MemberInfo> fieldTable;
    private final List<MemberInfo> methodTable;
    private final List<AttributeInfo> attributeTable;

    public ClassFile(final byte[] bytes) {

        buffer = ByteBuffer.wrap(bytes).duplicate().asReadOnlyBuffer();

        final int magicNumber = buffer.getInt();
        if (magicNumber != 0xCAFEBABE) {
            throw new IllegalArgumentException("Not a Java class file (magic number mismatch)");
        }

        minorVersion = buffer.getShort();
        majorVersion = ClassFileVersion.fromMajorVersion(buffer.getShort());

        constantPool = new ConstantPool(buffer);

        accessFlags = buffer.getShort();
        thisIndex = buffer.getShort();
        superIndex = buffer.getShort();

        final int interfaceTableCount = buffer.getShort();
        interfaceTable = getList(interfaceTableCount, () -> constantPool.getClassRef(buffer.getShort()));

        final int fieldTableCount = buffer.getShort();
        fieldTable = getList(fieldTableCount, MemberInfo::new);

        final int methodTableCount = buffer.getShort();
        methodTable = getList(methodTableCount, MemberInfo::new);

        final int attributeTableCount = buffer.getShort();
        attributeTable = getList(attributeTableCount, AttributeInfo::new);
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public ClassFileVersion getMajorVersion() {
        return majorVersion;
    }

    public int getModifiers() {
        return accessFlags;
    }

    public String getModifiersString() {
        return Modifier.toString(accessFlags);
    }

    public String getClassName() {
        return constantPool.getClassRef(thisIndex).getName();
    }

    public String getSuperClassName() {
        return constantPool.getClassRef(superIndex).getName();
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public List<ClassReference> getInterfaceTable() {
        return interfaceTable;
    }

    public List<MemberInfo> getFieldTable() {
        return fieldTable;
    }

    public List<MemberInfo> getMethodTable() {
        return methodTable;
    }

    public List<AttributeInfo> getAttributeTable() {
        return attributeTable;
    }

    private <T> List<T> getList(final int count, final BiFunction<ByteBuffer, ConstantPool, T> constructor) {
        return IntStream.range(0, count)
                        .mapToObj(i -> constructor.apply(buffer, constantPool))
                        .collect(Collectors.toList());
    }

    private <T> List<T> getList(final int count, final Supplier<T> supplier) {
        return IntStream.range(0, count)
                        .mapToObj(i -> supplier.get())
                        .collect(Collectors.toList());
    }
}
