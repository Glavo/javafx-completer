package org.glavo.javafx.completer;

import java.util.EnumSet;

public class JavaFXCompleter {

    private final EnumSet<JavaFXModule> modules = EnumSet.noneOf(JavaFXModule.class);
    private boolean allowFallback = false;

    private JavaFXVersion version;
    private int majorVersion = -1;

    private JavaFXVersion minVersion;
    private int minMajorVersion = -1;

    private JavaFXVersion maxVersion;
    private int maxMajorVersion = -1;


    public JavaFXCompleter addModule(JavaFXModule module) {
        if (!this.modules.contains(module)) {
            this.modules.addAll(module.getDependentModules());
            this.modules.add(module);
        }
        return this;
    }

    public JavaFXCompleter addModules(JavaFXModule... modules) {
        for (JavaFXModule module : modules) {
            if (!this.modules.contains(module)) {
                this.modules.addAll(module.getDependentModules());
                this.modules.add(module);
            }
        }
        return this;
    }

    public JavaFXCompleter version(JavaFXVersion version) {
        this.version = version;
        return this;
    }

    public JavaFXCompleter majorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
        return this;
    }

    public JavaFXCompleter allowFallback() {
        allowFallback = true;
        return this;
    }

    public JavaFXCompleter minVersion(JavaFXVersion version) {
        this.minVersion = version;
        return this;
    }

    public JavaFXCompleter minMajorVersion(int majorVersion) {
        this.minMajorVersion = majorVersion;
        return this;
    }

    public JavaFXCompleter maxVersion(JavaFXVersion version) {
        this.maxVersion = version;
        return this;
    }

    public JavaFXCompleter maxMajorVersion(int majorVersion) {
        this.maxMajorVersion = majorVersion;
        return this;
    }

    public void complete() {
        if (modules.isEmpty()) {
            return;
        }

        try {
            // Skip completion when JavaFX is present at runtime. (Whether to detect all modules in the future, not just javafx.base?)
            Class.forName("javafx.beans.Observable");
            return;
        } catch (ClassNotFoundException ignored) {
        }

        try {
            //noinspection Since15
            if (Runtime.version().feature() < 11) {
                throw new Exception();
            }
        } catch (Throwable ex) {
            throw new UnsupportedOperationException("Completing JavaFX is currently only supported on Java 11+");
        }


        if (modules.contains(JavaFXModule.SWING)) {
            try {
                Class.forName("jdk.swing.interop.DispatcherWrapper");
            } catch (ClassNotFoundException ignored) {
                throw new UnsupportedOperationException(
                        "Because runtime does not include the module jdk.unsupported.desktop, it cannot be completed by the module javafx.swing."
                );
            }
        }


        throw new AssertionError(); // TODO
    }
}
