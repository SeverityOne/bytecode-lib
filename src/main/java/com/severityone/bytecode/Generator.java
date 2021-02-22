package com.severityone.bytecode;

@FunctionalInterface
interface Generator {

    Object generate(ConstantPool constantPool);
}
