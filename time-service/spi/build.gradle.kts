plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    id("io.micronaut.library")
}

dependencies {
    api(project(":time-service.api"))
}

idea.module.isDownloadSources = true
