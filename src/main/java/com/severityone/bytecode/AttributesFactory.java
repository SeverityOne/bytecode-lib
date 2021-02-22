package com.severityone.bytecode;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import com.severityone.bytecode.attrbutes.AnnotationDefault;
import com.severityone.bytecode.attrbutes.BootstrapMethods;
import com.severityone.bytecode.attrbutes.Code;
import com.severityone.bytecode.attrbutes.ConstantValue;
import com.severityone.bytecode.attrbutes.Deprecated;
import com.severityone.bytecode.attrbutes.EnclosingMethod;
import com.severityone.bytecode.attrbutes.Exceptions;
import com.severityone.bytecode.attrbutes.InnerClasses;
import com.severityone.bytecode.attrbutes.LineNumberTable;
import com.severityone.bytecode.attrbutes.LocalVariableTable;
import com.severityone.bytecode.attrbutes.LocalVariableTypeTable;
import com.severityone.bytecode.attrbutes.MethodParameters;
import com.severityone.bytecode.attrbutes.Module;
import com.severityone.bytecode.attrbutes.ModuleMainClass;
import com.severityone.bytecode.attrbutes.ModulePackages;
import com.severityone.bytecode.attrbutes.NestHost;
import com.severityone.bytecode.attrbutes.NestMembers;
import com.severityone.bytecode.attrbutes.RuntimeInvisibleAnnotations;
import com.severityone.bytecode.attrbutes.RuntimeInvisibleParameterAnnotations;
import com.severityone.bytecode.attrbutes.RuntimeInvisibleTypeAnnotations;
import com.severityone.bytecode.attrbutes.RuntimeVisibleAnnotations;
import com.severityone.bytecode.attrbutes.RuntimeVisibleParameterAnnotations;
import com.severityone.bytecode.attrbutes.RuntimeVisibleTypeAnnotations;
import com.severityone.bytecode.attrbutes.Signature;
import com.severityone.bytecode.attrbutes.SourceDebugExtension;
import com.severityone.bytecode.attrbutes.SourceFile;
import com.severityone.bytecode.attrbutes.StackMapTable;
import com.severityone.bytecode.attrbutes.Synthetic;

public final class AttributesFactory {

    private static final Map<String, BiFunction<ByteBuffer, ConstantPool, Object>> CONSTRUCTORS = new HashMap<>();

    static {
        /* Critical */
        CONSTRUCTORS.put("ConstantValue", ConstantValue::new);
        CONSTRUCTORS.put("Code", Code::new);
        CONSTRUCTORS.put("StackMapTable", StackMapTable::new);
        CONSTRUCTORS.put("BootstrapMethods", BootstrapMethods::new);
        CONSTRUCTORS.put("NestHost", NestHost::new);
        CONSTRUCTORS.put("NestMembers", NestMembers::new);

        /* Non-critical */
        CONSTRUCTORS.put("Exceptions", Exceptions::new);
        CONSTRUCTORS.put("InnerClasses", InnerClasses::new);
        CONSTRUCTORS.put("EnclosingMethod", EnclosingMethod::new);
        CONSTRUCTORS.put("Synthetic", Synthetic::new);
        CONSTRUCTORS.put("Signature", Signature::new);
        CONSTRUCTORS.put("SourceFile", SourceFile::new);
        CONSTRUCTORS.put("LineNumberTable", LineNumberTable::new);
        CONSTRUCTORS.put("LocalVariableTable", LocalVariableTable::new);
        CONSTRUCTORS.put("LocalVariableTypeTable", LocalVariableTypeTable::new);

        /* Optional */
        CONSTRUCTORS.put("SourceDebugExtension", SourceDebugExtension::new);
        CONSTRUCTORS.put("Deprecated", Deprecated::new);
        CONSTRUCTORS.put("RuntimeVisibleAnnotations", RuntimeVisibleAnnotations::new);
        CONSTRUCTORS.put("RuntimeInvisibleAnnotations", RuntimeInvisibleAnnotations::new);
        CONSTRUCTORS.put("RuntimeVisibleParameterAnnotations", RuntimeVisibleParameterAnnotations::new);
        CONSTRUCTORS.put("RuntimeInvisibleParameterAnnotations", RuntimeInvisibleParameterAnnotations::new);
        CONSTRUCTORS.put("RuntimeVisibleTypeAnnotations", RuntimeVisibleTypeAnnotations::new);
        CONSTRUCTORS.put("RuntimeInvisibleTypeAnnotations", RuntimeInvisibleTypeAnnotations::new);
        CONSTRUCTORS.put("AnnotationDefault", AnnotationDefault::new);
        CONSTRUCTORS.put("MethodParameters", MethodParameters::new);
        CONSTRUCTORS.put("Module", Module::new);
        CONSTRUCTORS.put("ModulePackages", ModulePackages::new);
        CONSTRUCTORS.put("ModuleMainClass", ModuleMainClass::new);
    }

    private AttributesFactory() {}

    public static AttributeInfo readAttributes(final ByteBuffer buffer, final ConstantPool constantPool) {

        final int attributeNameIndex = buffer.getShort();
        final int attributeLength = buffer.getInt();
        final ByteBuffer subBuffer = buffer.slice().limit(attributeLength);

        final String attributeName = constantPool.getString(attributeNameIndex);
        final Object[] values = new Object[numAttributes];
        for (int index = 0; index < numAttributes; index++) {
            values[index] = CONSTRUCTORS.get(attributeName).apply(buffer, constantPool);
        }

        return new AttributeInfo(attributeName, values);
    }
}
