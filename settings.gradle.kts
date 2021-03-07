import java.nio.file.Files
import java.nio.file.Paths

//in Gradle 7.x this property is deprecated.
//enableFeaturePreview("VERSION_ORDERING_V2")

rootProject.name = "marvel"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
    plugins {
        val quarkusVersion: String by settings
        id("io.quarkus") version quarkusVersion
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
    ":convention",
    //":gatling",
    ":legacy-openapi",
    ":shared"
)

for (serviceDir in setOf("time-service")) {
    Files.list(Paths.get("$rootDir", serviceDir)).use {
        it.filter { Files.isDirectory(it) }
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
