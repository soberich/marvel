rootProject.name = "marvel"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        maven("http://dl.bintray.com/kotlin/kotlin-eap")
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
        ":runtime-demo",
        ":business",
        ":openapi",
//        ":event-sourcing",w
//        ":convention",
//        ":runtime-blocking-sql-jdbc",
        ":runtime-blocking-sql-jpa",
//        ":runtime-nosql",
        ":runtime-jakarta"

)
