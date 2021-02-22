package com.severityone.bytecode;

import java.nio.ByteBuffer;

@FunctionalInterface
interface Reader {

    Generator read(ByteBuffer buffer);
}
