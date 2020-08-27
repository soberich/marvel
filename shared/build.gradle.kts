import versioning.Deps

plugins {
    idea
    `java-library`
    `jackson-convention-helper`
    `kotlin-convention-helper`
    io.micronaut.library
}

repositories.jcenter()

val hibernateVersion: String by project

dependencies {
    listOf(
        platform(project(":convention"))
    ).asSequence()
    .forEach(::api)

    api("com.vladmihalcea:hibernate-types-52:2.9.+")
    api("io.github.classgraph:classgraph:+")
    api("io.reactivex.rxjava2:rxjava:2.2.19")
    api("org.hibernate:hibernate-core:$hibernateVersion")

    api("com.google.guava:guava") {
        exclude("com.google.code.findbugs", "jsr305")
    }
}

java.modularity.inferModulePath.set(true)
idea.module.isDownloadSources = true
