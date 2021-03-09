// this file prevents gradle to search for it
//in Gradle 7.x this property is deprecated.
//enableFeaturePreview("VERSION_ORDERING_V2")

pluginManagement {
    val ideaExtPluginVersion  : String by settings
    val useLatestPluginVersion: String by settings
    val versionsPluginVersion : String by settings
    plugins {
        id("com.github.ben-manes.versions")        version versionsPluginVersion  // optional
        id("org.jetbrains.gradle.plugin.idea-ext") version ideaExtPluginVersion
        id("se.patrikerdes.use-latest-versions")   version useLatestPluginVersion // optional
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}
