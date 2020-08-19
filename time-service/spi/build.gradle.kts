plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
}

dependencies {
    api(project(":time-service.api"))
}

idea.module.isDownloadSources = true
