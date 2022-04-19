package org.glavo.javafx.completer;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

public class JavaFXCompleter {

    private final EnumSet<JavaFXModule> modules = EnumSet.noneOf(JavaFXModule.class);

    public JavaFXCompleter addModule(JavaFXModule module) {
        this.modules.add(module);
        return this;
    }

    public JavaFXCompleter addModules(JavaFXModule... modules) {
        Collections.addAll(this.modules, modules);
        return this;
    }

    public JavaFXCompleter addModules(Collection<JavaFXModule> modules) {
        this.modules.addAll(modules);
        return this;
    }
}
