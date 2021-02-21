package com.severityone.bytecode;

public enum ClassFileVersion {

    JDK_1_1((short) 0x2D, "JDK 1.1"),
    JDK_1_2((short) 0x2E, "JDK 1.2"),
    JDK_1_3((short) 0x2F, "JDK 1.3"),
    JDK_1_4((short) 0x30, "JDK 1.4"),
    JAVA_SE_5_0((short) 0x31, "Java SE 5.0"),
    JAVA_SE_6_0((short) 0x32, "Java SE 6.0"),
    JAVA_SE_7((short) 0x33, "Java SE 7"),
    JAVA_SE_8((short) 0x34, "Java SE 8"),
    JAVA_SE_9((short) 0x35, "Java SE 9"),
    JAVA_SE_10((short) 0x36, "Java SE 10"),
    JAVA_SE_11((short) 0x37, "Java SE 11"),
    JAVA_SE_12((short) 0x38, "Java SE 12"),
    JAVA_SE_13((short) 0x39, "Java SE 13"),
    JAVA_SE_14((short) 0x3A, "Java SE 14"),
    JAVA_SE_15((short) 0x3B, "Java SE 15"),
    JAVA_SE_16((short) 0x3C, "Java SE 16"),
    JAVA_SE_17((short) 0x3D, "Java SE 17");

    private final short majorVersion;
    private final String description;

    ClassFileVersion(final short majorVersion, final String description) {
        this.majorVersion = majorVersion;
        this.description = description;
    }

    public short getMajorVersion() {
        return majorVersion;
    }

    public String getDescription() {
        return description;
    }

    public static ClassFileVersion fromMajorVersion(final short majorVersion) {
        final int index = (majorVersion - JDK_1_1.getMajorVersion());
        if (index < 0 || index >= values().length) {
            throw new IllegalArgumentException("Unsupported major version: " + majorVersion);
        }
        return values()[index];
    }
}
