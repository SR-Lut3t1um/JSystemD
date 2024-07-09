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
        className = "SdBus"
    }
    header("systemd/src/libsystemd/sd-bus/bus-internal.h") {
        // The library name
        libraries = listOf("systemd")

        // whatever
        includes = listOf( "systemd/src/systemd/", "systemd/src/core/", "systemd/src/basic/", "systemd/src/fundamental/")

        definedMacros = listOf(
            "SIZEOF_TIME_T=8",
            "RELATIVE_SOURCE_PATH=\"${file("systemd/src").relativeTo(file("build/"))}\"",
        )
        // The package under which all source files will be generated
        targetPackage = "org.systemd"
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}
