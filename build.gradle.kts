import io.quarkus.gradle.QuarkusPluginExtension
import org.gradle.api.JavaVersion.current
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("io.quarkus:quarkus-gradle-plugin:0.19.1")
    }
}
check(current().isJava8Compatible) { "At least Java 8 is required, current JVM is ${current()}" }

plugins {
    base
    `project-report`
}

project(":runtime-jakarta") {
    buildscript {
        repositories {
            mavenLocal()
        }
        dependencies {
            classpath("io.quarkus:quarkus-gradle-plugin:0.19.1")
        }
    }
    apply(plugin = "java")
    apply(plugin = "io.quarkus")
    configure<QuarkusPluginExtension> {
        setSourceDir("$projectDir/src/main/kotlin")
//        resourcesDir() += file("$projectDir/src/main/resources")
        setOutputDirectory("$buildDir/classes/kotlin/main")
    }
    val main = ((this as ExtensionAware).extensions.findByName("sourceSets") as? SourceSetContainer?)?.findByName("main")
    if (extensions.findByType<JavaPluginExtension>() != null) {
        configure<JavaPluginExtension> {
            main?.output?.setResourcesDir("$buildDir/classes/java/main")
        }
    }
    dependencies {
        "implementation"(project(":convention"))
//        "implementation"(project(":convention")) {
//            capabilities {
//                requireCapability("com.example.marvel:convention-quarkus")
//            }
//        }
    }
}

subprojects {
    apply(plugin = "build-dashboard")
    apply(plugin = "help-tasks")
    apply(plugin = "dependencies")

    group       = "com.example.marvel"
    version     = "0.0.1-SNAPSHOT"
    description = "${name.replace('-', ' ').toUpperCase()} of Native Quarkus/Micronaut Arrow-Kt Vert.x Coroutines GRPC Kotlin-DSL app"
    
    repositories {
        mavenLocal()
        jcenter()
        maven("http://dl.bintray.com/kotlin/kotlin-eap") {
            content {
                includeGroup("org.jetbrains.kotlin")
            }
        }
        maven("https://dl.bintray.com/arrow-kt/arrow-kt") {
            content {
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local") {
            content {
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}
