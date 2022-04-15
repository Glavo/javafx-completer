package org.glavo.javafx.completer;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class JavaFXCompleter {

    private final Set<JavaFXModule> modules;

    public JavaFXCompleter(Set<JavaFXModule> modules) {
        this.modules = modules;
    }

    public JavaFXCompleter.Builder builder() {
        return new Builder();
    }



    public static final class Builder {
        private EnumSet<JavaFXModule> modules = EnumSet.noneOf(JavaFXModule.class);

        public Builder addModule(JavaFXModule module) {
            this.modules.add(module);
            return this;
        }

        public Builder addModules(JavaFXModule... modules) {
            Collections.addAll(this.modules, modules);
            return this;
        }

        public JavaFXCompleter build() {
            modules = null;

            return null; // TODO
        }
    }
}
