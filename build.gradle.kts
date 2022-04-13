buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.glavo:module-info-compiler:1.2")
    }
}

plugins {
    id("java")
}

group = "org.glavo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.compileJava {
    options.release.set(8)
}

val compileModuleInfo = tasks.create<org.glavo.mic.tasks.CompileModuleInfo>("compileModuleInfo") {
    sourceFile.set(file("src/main/module-info.java"))
    targetFile.set(buildDir.resolve("classes/java/module-info/module-info.class"))
}

tasks.jar {
    dependsOn(compileModuleInfo)
    from(compileModuleInfo.targetFile)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
