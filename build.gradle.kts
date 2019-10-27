import org.gradle.api.JavaVersion.current

check(current().isJava8Compatible) { "At least Java 8 is required, current JVM is ${current()}" }

plugins {
    base
    `project-report`
}

subprojects {
    apply(plugin = "build-dashboard")
    apply(plugin = "help-tasks")
    apply(plugin = "dependencies")

    group       = "com.example.marvel"
    version     = "0.0.1-SNAPSHOT"
    description = "${name.replace('-', ' ').toUpperCase()} of Native Quarkus/Micronaut Arrow-Kt Vert.x Coroutines GRPC Kotlin-DSL app"

    repositories {
        jcenter()
        maven("https://repository.jboss.org/nexus/content/repositories/public")
        maven("https://repo.spring.io/milestone")
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
