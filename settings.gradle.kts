import java.nio.file.Files
import java.nio.file.Paths

enableFeaturePreview("VERSION_ORDERING_V2")

rootProject.name = "marvel"

pluginManagement {
    val ideaExtPluginVersion  : String by settings
    val jlinkPluginVersion    : String by settings
    val micronautPluginVersion: String by settings
    val shadowPluginVersion   : String by settings
    plugins {
        id("com.github.johnrengelman.shadow")      version shadowPluginVersion
        id("io.micronaut.application")             version micronautPluginVersion
        id("io.micronaut.library")                 version micronautPluginVersion
        id("org.beryx.jlink")                      version jlinkPluginVersion
        id("org.jetbrains.gradle.plugin.idea-ext") version ideaExtPluginVersion
    }
    repositories {
        gradlePluginPortal()
        jcenter()
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
