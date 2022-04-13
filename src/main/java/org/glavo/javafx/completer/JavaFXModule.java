package org.glavo.javafx.completer;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum JavaFXModule {
    BASE(Collections.emptySet()),
    GRAPHICS(EnumSet.of(BASE)),
    CONTROLS(EnumSet.of(BASE, GRAPHICS)),
    FXML(EnumSet.of(BASE, GRAPHICS)),
    MEDIA(EnumSet.of(BASE, GRAPHICS)),
    SWING(EnumSet.of(BASE, GRAPHICS)),
    WEB(EnumSet.of(BASE, CONTROLS, GRAPHICS, MEDIA));

    private final Set<JavaFXModule> dependentModules;

    JavaFXModule(Set<JavaFXModule> dependentModules) {
        this.dependentModules = dependentModules;
    }

    public Set<JavaFXModule> getDependentModules() {
        return dependentModules;
    }
}
