plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `maven-publish`
}

repositories.mavenCentral()

dependencies {

    api(platform(project(":convention")))

    api(libs.hibernate.types)
    api("io.github.classgraph:classgraph:+")
    api("io.reactivex.rxjava2:rxjava:2.+")
    //trying to avoid possible bug in 5.5.0-SNAPSHOT for integrating via SPI for `org.hibernate.integrator.spi.IntegratorService`
    api(libs.hibernate)

    api("com.google.guava:guava") {
        exclude("com.google.code.findbugs", "jsr305")
        because("FindBugs is legacy. SpotBugs preferred.")
    }
}

java.modularity.inferModulePath.set(true)
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
