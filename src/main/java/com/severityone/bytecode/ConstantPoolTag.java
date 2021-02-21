package com.severityone.bytecode;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

enum ConstantPoolTag {

    STRING(1, readString()),
    INT(3, readObject(ByteBuffer::getInt)),
    FLOAT(4, readObject(ByteBuffer::getFloat)),
    LONG(5, 2, readObject(ByteBuffer::getLong)),
    DOUBLE(6, 2, readObject(ByteBuffer::getDouble)),
    CLASS_REF(7, readClassRef()),
    STRING_REF(8, readRef()),
    FIELD_REF(9, readMemberRef()),
    METHOD_REF(10, readMemberRef()),
    INTERFACE_METHOD_REF(11, readMemberRef()),
    NAME_AND_TYPE(12, readNameAndTypeDescriptor()),
    METHOD_HANDLE(15, readMethodHandle()),
    METHOD_TYPE(16, readRef()),
    DYNAMIC(17, readDynamic()),
    INVOKE_DYNAMIC(18, readDynamic()),
    MODULE(19, readRef()),
    PACKAGE(20, readRef());

    private final int tag;
    private final int size;
    private final Reader reader;

    private static final Map<Integer, ConstantPoolTag> FROM_INT =
            Arrays.stream(values()).collect(Collectors.toMap(ConstantPoolTag::getTag, Function.identity()));

    ConstantPoolTag(final int tag, final int size, final Reader reader) {
        this.tag = tag;
        this.size = size;
        this.reader = reader;
    }

    ConstantPoolTag(final int tag, final Reader reader) {
        this(tag, 1, reader);
    }

    int getTag() {
        return tag;
    }

    int getSize() { return size;}

    Reader getReader() {
        return reader;
    }

    static ConstantPoolTag from(final int tag) {
        return Optional.ofNullable(FROM_INT.get(tag))
                       .orElseThrow(() -> new IllegalArgumentException("Unknown tag " + tag));
    }

    @FunctionalInterface
    interface Generator {

        Object generate(ConstantPool constantPool);
    }

    @FunctionalInterface
    interface Reader {

        Generator read(ByteBuffer buffer);
    }

    private static Reader readObject(final Function<ByteBuffer, Object> function) {
        return buffer -> classFile -> function.apply(buffer);
    }

    // TODO: The constant pool uses a modified UTF-8, but this will do for now
    private static Reader readString() {
        return buffer -> {
            final int length = buffer.getShort();
            final byte[] bytes = new byte[length];
            buffer.get(bytes);
            return constantPool -> new String(bytes, StandardCharsets.UTF_8);
        };
    }

    private static Reader readRef() {
        return buffer -> {
            final int index = buffer.getShort();
            return constantPool -> constantPool.getString(index);
        };
    }

    private static Reader readClassRef() {
        return buffer -> {
            final int index = buffer.getShort();
            return constantPool -> new ClassReference(constantPool.getString(index));
        };
    }

    private static Reader readMemberRef() {
        return buffer -> {
            final int classIndex = buffer.getShort();
            final int nameAndDescriptorIndex = buffer.getShort();
            return constantPool -> new MemberReference(
                    constantPool.getClassRef(classIndex),
                    constantPool.getNameAndTypeDescriptor(nameAndDescriptorIndex)
            );
        };
    }

    private static Reader readNameAndTypeDescriptor() {
        return buffer -> {
            final int nameIndex = buffer.getShort();
            final int typeIndex = buffer.getShort();
            return constantPool -> new NameAndTypeDescriptor(
                    constantPool.getString(nameIndex),
                    constantPool.getString(typeIndex)
            );
        };
    }

    private static Reader readMethodHandle() {
        return buffer -> {
            final TypeDescriptor typeDescriptor = TypeDescriptor.from(buffer.get());
            final int methodRefIndex = buffer.getShort();
            return constantPool -> new MethodHandleReference(
                    typeDescriptor,
                    constantPool.getMethodRef(methodRefIndex)
            );
        };
    }

    private static Reader readDynamic() {
        return buffer -> {
            final int bootstrapMethodIndex = buffer.getShort();
            final int nameAndDescriptorIndex = buffer.getShort();
            return constantPool -> new DynamicReference(
                    bootstrapMethodIndex,
                    constantPool.getNameAndTypeDescriptor(nameAndDescriptorIndex)
            );
        };
    }
}
