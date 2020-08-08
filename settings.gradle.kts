import java.nio.file.Files
import java.nio.file.Paths

enableFeaturePreview("VERSION_ORDERING_V2")

rootProject.name = "marvel"

pluginManagement {
    val micronautPluginVersion: String by settings
    plugins {
        id("io.micronaut.application") version micronautPluginVersion
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://dl.bintray.com/kotlin/kotlin-eap") {
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
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
            }
        }
    }
}

include(
    ":gatling",
    ":legacy-openapi",
    ":shared-convention"
)

for (serviceDir in setOf("time-service")) {
    Files.list(Paths.get("$rootDir", serviceDir)).use {
        it.filter(Files::isDirectory)
            .forEach {
                ":${it.fileName}"
                    .also { include(it) }
                    .run(::project)
                    .apply {
                        name       = "$serviceDir.${it.fileName}"
                        projectDir = it.toFile()
                    }
            }
    }
}
