// this file prevents gradle to search for it
enableFeaturePreview("VERSION_ORDERING_V2")

pluginManagement {
    val ideaExtPluginVersion : String by settings
    val versionsPluginVersion: String by settings
    plugins {
        id("com.github.ben-manes.versions")        version versionsPluginVersion // optional
        id("org.jetbrains.gradle.plugin.idea-ext") version ideaExtPluginVersion
        //id("se.patrikerdes.use-latest-versions") version "0.2.14" // optional
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
}
