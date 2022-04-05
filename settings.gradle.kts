import java.nio.file.Files
import java.nio.file.Paths

//in Gradle 7.x this property is deprecated.
//enableFeaturePreview("VERSION_ORDERING_V2")

rootProject.name = "marvel"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
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

plugins {
    `gradle-enterprise`
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

include(
    ":convention",
    //":gatling",
//    ":legacy-openapi",
    ":shared"
)

for (serviceDir in setOf("time-service")) {
    Files.list(Paths.get("$rootDir", serviceDir)).use {
        it.filter { Files.isDirectory(it) }
            .forEach {
                val projectFolderName = ":${it.fileName}"
                include(projectFolderName)
                val projectDescriptor = project(projectFolderName)
                projectDescriptor.name = "$serviceDir.${it.fileName}"
                projectDescriptor.projectDir = it.toFile()
            }
    }
}
