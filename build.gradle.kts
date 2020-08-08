import org.jetbrains.gradle.ext.*

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_14)) { "At least Java 14 is required, current JVM is ${JavaVersion.current()}" }

plugins {
    idea
    org.jetbrains.gradle.plugin.`idea-ext`
}

idea.project {
    this as ExtensionAware
    configure<ProjectSettings> {
        doNotDetectFrameworks("spring")
    }
}

subprojects {
    apply(plugin = "build-dashboard")
    apply(plugin = "help-tasks")
    apply(plugin = "dependencies-reporting-helper")

    group       = "com.example.marvel"
    version     = "0.0.1-SNAPSHOT"
    description = "${name.replace('-', ' ').toUpperCase()} of Native Quarkus/Micronaut Arrow-Kt Vert.x Coroutines GRPC Kotlin-DSL app"

    repositories {
        jcenter()
        maven("https://repository.jboss.org/nexus/content/repositories/public")
        maven("https://repo.spring.io/milestone")
        maven("https://dl.bintray.com/micronaut/core-releases-local") {
            content {
                includeGroupByRegex("io\\.micronaut.*")
            }
        }
        maven("https://dl.bintray.com/kotlin/kotlin-eap") {
            content {
                includeGroup("org.jetbrains.kotlin")
            }
        }
        maven("https://dl.bintray.com/arrow-kt/arrow-kt") {
            content {
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local") {
            content {
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}
