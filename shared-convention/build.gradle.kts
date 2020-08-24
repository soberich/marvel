import versioning.Deps

plugins {
    idea
    `java-library`
    `jackson-convention-helper`
    `kotlin-convention-helper`
    id("io.micronaut.library")
}

repositories.jcenter()

dependencies {
    arrayOf(
        platform(Deps.Platforms.GUAVA),
        Deps.Libs.HIBERNATE,
        Deps.Libs.HIBERNATE_TYPES,
        Deps.Libs.RX2,
        "io.github.classgraph:classgraph:+"
    ).forEach(::api)

    api("com.google.guava:guava") {
        exclude("com.google.code.findbugs", "jsr305")
    }
}

java.modularity.inferModulePath.set(true)
idea.module.isDownloadSources = true
