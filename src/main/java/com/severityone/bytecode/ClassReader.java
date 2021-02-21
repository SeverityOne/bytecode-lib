package com.severityone.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ClassReader {

    private ClassReader() {}

    public static ClassFile readClass(final Class<?> type) throws IOException {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final URL url = new URL(type.getProtectionDomain().getCodeSource().getLocation().toExternalForm() +
                                type.getName().replace('.', '/')
                                + ".class");
        try (InputStream inputStream = url.openStream()) {
            for (; ; ) {
                final int available = inputStream.available();
                if (available == 0) {
                    break;
                }
                final byte[] bytes = new byte[available];
                inputStream.read(bytes, 0, available);
                outputStream.write(bytes);
            }
            return new ClassFile(outputStream.toByteArray());
        }
    }
}
