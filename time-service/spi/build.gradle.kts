plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `maven-publish`
    //io.micronaut.library
}

dependencies {
    api(project(":time-service.api"))
}

idea.module.isDownloadSources = true

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}
