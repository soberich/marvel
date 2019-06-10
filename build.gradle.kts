
import org.gradle.api.JavaVersion.current

check(current().isJava8Compatible) { "At least Java 8 is required, current JVM is ${current()}" }

plugins {
    base
    `project-report`
}

subprojects {
    apply(plugin = "build-dashboard")
    apply(plugin = "help-tasks")
    apply(plugin = "project-report")
    apply(plugin = "com.github.ben-manes.versions")

    group       = "com.example.marvel"
    version     = "0.0.1-SNAPSHOT"
    description = "${name.replace('-', ' ').toUpperCase()} of Native Quarkus/Micronaut Arrow-Kt Vert.x Coroutines GRPC Kotlin-DSL app"
    
    repositories {
        mavenLocal {
            content {
                includeGroup("io.quarkus")
                includeGroup("nl.topicus") //0.10-SNAPSHOT
            }
        }
        maven("http://dl.bintray.com/kotlin/kotlin-eap")
        jcenter()
        maven("https://dl.bintray.com/arrow-kt/arrow-kt/") {
            content {
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/") {
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
