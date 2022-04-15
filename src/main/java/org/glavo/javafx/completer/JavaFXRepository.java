package org.glavo.javafx.completer;

import java.nio.file.Path;
import java.util.List;

public interface JavaFXRepository {
    List<JavaFXVersion> fetchVersions(JavaFXPlatform platform) throws Exception;

    boolean isSupport(JavaFXPlatform platform, JavaFXVersion version, JavaFXModule module);

    void fetchModule(JavaFXPlatform platform, JavaFXVersion version, JavaFXModule module, Path targetFile) throws Exception;
}
