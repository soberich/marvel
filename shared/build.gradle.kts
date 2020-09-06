import versioning.Deps

plugins {
    idea
    `java-library`
    `jackson-convention-helper`
    `kotlin-convention-helper`
    `maven-publish`
}

repositories.jcenter()

val hibernateVersion: String by project

dependencies {
    listOf(
        platform(project(":convention"))
    ).asSequence()
    .forEach(::api)

    api("com.vladmihalcea:hibernate-types-52:[2.9,2.10)")
    api("io.github.classgraph:classgraph:+")
    api("io.reactivex.rxjava2:rxjava:2.2.19")
    //trying to avoid possible bug in 5.5.0-SNAPSHOT for integrating via SPI for `org.hibernate.integrator.spi.IntegratorService`
    api("org.hibernate:hibernate-core:$hibernateVersion")

    api("com.google.guava:guava") {
        exclude("com.google.code.findbugs", "jsr305")
        because("FindBugs is legacy. SpotBugs preferred.")
    }
}

java.modularity.inferModulePath.set(true)
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
