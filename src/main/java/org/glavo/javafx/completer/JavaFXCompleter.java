package org.glavo.javafx.completer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Objects;

public class JavaFXCompleter {

    private final EnumSet<JavaFXModule> modules = EnumSet.noneOf(JavaFXModule.class);
    private boolean allowFallback = false;

    private JavaFXVersion version;
    private int majorVersion = -1;

    private JavaFXVersion minVersion;
    private int minMajorVersion = -1;

    private JavaFXVersion maxVersion;
    private int maxMajorVersion = -1;

    private LinkedHashMap<String, String> repos;
    private Path storageDir;

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

    public JavaFXCompleter addMavenRepository(String url, String description) {
        if (repos == null) {
            repos = new LinkedHashMap<>();
        }

        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        repos.put(url, description);
        return this;
    }

    public JavaFXCompleter addMavenCentralRepository(String description) {
        addMavenRepository("https://repo1.maven.org/maven2", description);
        return this;
    }

    public JavaFXCompleter addMavenCentralAliyunMirrorRepository(String description) {
        addMavenRepository("https://maven.aliyun.com/repository/public", description);
        return this;
    }

    public JavaFXCompleter addMavenLocalRepository(String description) {
        addMavenRepository(Paths.get(System.getProperty("user.home"), ".m2", "repository").toUri().toString(), description);
        return this;
    }

    public JavaFXCompleter appName(String name) {
        Objects.requireNonNull(name);
        if ("".equals(name) || ".".equals(name) || "..".equals(name) || "~".equals(name)
            || name.indexOf('/') >= 0
            || name.indexOf('\\') >= 0
            || name.indexOf(':') >= 0
            || name.indexOf(';') >= 0
        ) {
            throw new IllegalArgumentException();
        }

        Path dir = null;
        try {
            switch (JavaFXPlatform.getCurrent().getOperatingSystem()) {
                case WINDOWS: {
                    String appdata = System.getenv("APPDATA");
                    if (appdata != null) {
                        if (!Files.isRegularFile(Paths.get(appdata))) {
                            dir = Paths.get(appdata, name, ".openjfx", "cache");
                        }
                    }
                    break;
                }
                case LINUX: {
                    String dataHome = System.getenv("XDG_DATA_HOME");
                    if (dataHome != null) {
                        if (!Files.isRegularFile(Paths.get(dataHome))) {
                            dir = Paths.get(dataHome, ".openjfx", "cache");
                        }
                    }
                    break;
                }
                case MACOS: {
                    dir = Paths.get(System.getProperty("user.home"), "Library", "Application Support", name, ".openjfx", "cache");
                    break;
                }

            }
        } catch (Throwable ignored) {
        }

        if (dir == null) {
            dir = Paths.get(System.getProperty("user.home"), "." + name, ".openjfx", "cache");
        }

        this.storageDir = dir;
        return this;
    }

    public JavaFXCompleter storageDir(Path dir) {
        this.storageDir = dir;
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
