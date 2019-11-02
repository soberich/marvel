import java.io.FileFilter

rootProject.name = "marvel"

pluginManagement {
    repositories {
        mavenLocal()
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
    ":gatling",
    ":legacy-openapi"
)

for (serviceDir in setOf("time-service")) {
    for (`sub-project` in file(serviceDir, PathValidation.DIRECTORY).listFiles(FileFilter(File::isDirectory))!!) {
        ":${`sub-project`.name}"
            .also(::include)
            .run(::project)
            .apply {
                name       = "$serviceDir.${`sub-project`.name}"
                projectDir = `sub-project`
            }
    }
}
