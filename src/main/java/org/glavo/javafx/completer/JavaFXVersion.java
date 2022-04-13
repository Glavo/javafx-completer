package org.glavo.javafx.completer;

import java.util.Objects;

public final class JavaFXVersion implements Comparable<JavaFXVersion> {
    private final int major;
    private final int minor;
    private final int patch;
    private final int additional;


    public JavaFXVersion(int major, int minor, int patch, int additional) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.additional = additional;
    }

    @Override
    public int compareTo(JavaFXVersion version) {
        int c;

        c = Integer.compare(major, version.major);
        if (c != 0) {
            return c;
        }

        c = Integer.compare(minor, version.minor);
        if (c != 0) {
            return c;
        }

        c = Integer.compare(patch, version.patch);
        if (c != 0) {
            return c;
        }

        c = Integer.compare(additional, version.additional);
        return c;
    }


}
