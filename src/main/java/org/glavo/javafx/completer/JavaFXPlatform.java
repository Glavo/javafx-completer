package org.glavo.javafx.completer;

import java.util.Locale;

public class JavaFXPlatform {

    private final OperatingSystem operatingSystem;
    private final Architecture architecture;

    private String str;

    private JavaFXPlatform(OperatingSystem operatingSystem, Architecture architecture) {
        this.operatingSystem = operatingSystem;
        this.architecture = architecture;
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JavaFXPlatform)) {
            return false;
        }
        JavaFXPlatform other = (JavaFXPlatform) o;
        return operatingSystem == other.operatingSystem && architecture == other.architecture;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        if (str == null) {
            str = operatingSystem.toString().toLowerCase(Locale.ROOT) + "-" + architecture.toString().toLowerCase(Locale.ROOT);
        }
        return str;
    }


    private static final JavaFXPlatform current;

    static {
        OperatingSystem os;
        Architecture arch;

        String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT).trim();
        if (osName.startsWith("windows")) {
            os = OperatingSystem.WINDOWS;
        } else if (osName.startsWith("linux") || osName.equals("gnu")) {
            os = OperatingSystem.LINUX;
        } else if (osName.startsWith("mac") || osName.startsWith("darwin")) {
            os = OperatingSystem.MACOS;
        } else {
            os = null;
        }

        String archName = System.getProperty("os.arch").trim().toLowerCase(Locale.ROOT);
        switch (archName) {
            case "x86":
            case "x86-32":
            case "x86_32":
            case "x8632":
            case "i86pc":
            case "i386":
            case "i486":
            case "i586":
            case "i686":
            case "ia32":
            case "x32":
                arch = Architecture.X86;
                break;
            case "x8664":
            case "x86-64":
            case "x86_64":
            case "amd64":
            case "x64":
            case "ia32e":
            case "em64t":
                arch = Architecture.AMD64;
                break;
            case "arm":
            case "arm32":
            case "aarch32":
                arch = Architecture.ARM32;
                break;
            case "arm64":
            case "aarch64":
                arch = Architecture.AARCH64;
                break;
            default:
                arch = null;
                break;
        }


        if (os == null || arch == null) {
            current = null;
        } else {
            current = new JavaFXPlatform(os, arch);
        }
    }

    public static JavaFXPlatform getCurrent() {
        return current;
    }

    public enum OperatingSystem {
        WINDOWS,
        LINUX,
        MACOS
    }

    public enum Architecture {
        X86,
        AMD64,
        ARM32,
        AARCH64
    }
}
