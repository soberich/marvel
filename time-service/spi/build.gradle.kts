plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `maven-publish`
}

dependencies {
    api(project(":time-service.api"))
}

idea.module.isDownloadSources = true

publishing {
    repositories.maven("https://maven.pkg.github.com/soberich/marvel")
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
