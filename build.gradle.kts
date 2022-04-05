import org.jetbrains.gradle.ext.IdeaCompilerConfiguration
import org.jetbrains.gradle.ext.ProjectSettings
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import org.gradle.api.plugins.ExtensionAware as EA

plugins {
    `jvm-ecosystem`
    alias(libs.plugins.idea.ext)
    alias(libs.plugins.versions)        apply false
    alias(libs.plugins.latest.versions) apply false
}

val ideaActive = java.lang.Boolean.getBoolean("idea.active")

gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS_FULL

val props = FileInputStream("$rootDir/buildSrc/gradle.properties").use {
    Properties().apply { load(it) }
}

props.stringPropertyNames()
    .associateWith(props::getProperty)
    .forEach { project.ext.set(it.key, it.value) }

((idea.project as EA).the<ProjectSettings>() as EA).configure<IdeaCompilerConfiguration> {
    additionalVmOptions = "${project.property("org.gradle.jvmargs")}"
    //addNotNullAssertions = true TODO
    parallelCompilation = true
    useReleaseOption = true
    javac {
        javacAdditionalOptions = Files.readString(Paths.get("$rootDir", "buildSrc", "javacArgs"))
        preferTargetJDKCompiler = true
    }
}

subprojects {
    apply(plugin = "build-dashboard")
    apply(plugin = "help-tasks")
    apply(plugin = "se.patrikerdes.use-latest-versions")
    apply(plugin = "com.github.ben-manes.versions")

    description = "${name.replace('-', ' ').toUpperCase()} of Native Quarkus/Micronaut Arrow-Kt Vert.x Coroutines GRPC Kotlin-DSL app"

    plugins.withType<JavaLibraryPlugin>().configureEach {
        configure<JavaPluginExtension> {
            modularity.inferModulePath.set(true)
        }
    }
    plugins.withType<JavaPlugin>().configureEach {
        configure<JavaPluginExtension> {
            modularity.inferModulePath.set(true)
        }
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repository.jboss.org/nexus/content/repositories/public")
        maven("https://repo.spring.io/milestone")
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/micronaut/core-releases-local") {
            content {
                includeGroupByRegex("io\\.micronaut.*")
            }
        }
        maven("https://dl.bintray.com/arrow-kt/arrow-kt") {
            content {
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.jfrog.org/oss-snapshot-local") {
            content {
                includeGroupByRegex("io\\.micronaut.*")
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots") {
            mavenContent {
                snapshotsOnly()
            }
        }
        maven("https://jitpack.io") {
            content {
                includeGroup("com.github.Kotlin")
                includeGroup("io.smallrye")
            }
        }
    }
}
