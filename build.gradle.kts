plugins {
    id("java")
    id("io.github.krakowski.jextract").version("0.5.0")
}

group = "me.tobiasliese"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jextract {
    header("/usr/include/systemd/sd-bus.h") {
        // The library name
        libraries = listOf("systemd")

        // The package under which all source files will be generated
        targetPackage = "org.systemd"

        // The generated class name
        className = "SystemD"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}
