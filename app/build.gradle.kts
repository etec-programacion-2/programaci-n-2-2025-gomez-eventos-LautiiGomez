plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    kotlin("jvm") version "2.2.10"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation(libs.guava)

    // Gson para serialización/deserialización JSON
    implementation("com.google.code.gson:gson:2.10.1")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.12.1")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    // Por defecto usa la CLI, pero puedes cambiarlo a MainSwingKt para GUI
    mainClass = "org.example.gui.MainSwingKt"
    // Para usar CLI, comenta la línea anterior y descomenta esta:
    // mainClass = "org.example.AppKt"
}

// Configurar para permitir entrada interactiva
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

// Tarea para ejecutar la versión GUI
tasks.register<JavaExec>("runGui") {
    group = "application"
    mainClass.set("org.example.gui.MainSwingKt")
    classpath = sourceSets["main"].runtimeClasspath
}

// Tarea para ejecutar la versión CLI
tasks.register<JavaExec>("runCli") {
    group = "application"
    mainClass.set("org.example.AppKt")
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`
}