import versioning.Platforms

rootProject.name = "marvel"

pluginManagement {
    plugins {
        id("io.quarkus") version Platforms.Versions.QUARKUS
    }
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        maven("http://dl.bintray.com/kotlin/kotlin-eap") {
            content {
                includeGroup("org.jetbrains.kotlin")
            }
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "io.quarkus") {
                useModule("io.quarkus:quarkus-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
            }
        }
    }
}

include(
        ":api",
        ":business",
        ":convention",
//        ":event-sourcing",
        ":openapi",
//        ":runtime-blocking-sql-jdbc",
        ":runtime-blocking-sql-jpa",
        ":runtime-jakarta",
//        ":runtime-nosql",
        ":spi",
        ":spring-boot-runner",
        ":javaee-runner"
)
