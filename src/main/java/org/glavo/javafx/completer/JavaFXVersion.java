package org.glavo.javafx.completer;

public final class JavaFXVersion implements Comparable<JavaFXVersion> {
    private final int major;
    private final int minor;
    private final int patch;
    private final int additional;

    private String str;

    public JavaFXVersion(int major, int minor, int patch, int additional) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.additional = additional;
    }

    public JavaFXVersion(int major, int minor, int patch, int additional, String str) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.additional = additional;
        this.str = str;
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

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JavaFXVersion)) {
            return false;
        }

        JavaFXVersion other = (JavaFXVersion) obj;
        return this.major == other.major
               && this.minor == other.minor
               && this.patch == other.patch
               && this.additional == other.additional;
    }

    @Override
    public String toString() {
        if (str == null) {
            StringBuilder builder = new StringBuilder();
            builder.append(major);

            if (minor >= 0) {
                builder.append('.');
                builder.append(minor);

                if (patch >= 0) {
                    builder.append('.');
                    builder.append(patch);

                    if (additional >= 0) {
                        builder.append('.');
                        builder.append(patch);
                    }
                }
            }
            str = builder.toString();
        }

        return str;
    }
}
