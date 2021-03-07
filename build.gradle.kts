import org.jetbrains.gradle.ext.IdeaCompilerConfiguration
import org.jetbrains.gradle.ext.ProjectSettings
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import org.gradle.api.plugins.ExtensionAware as EA
import org.gradle.api.plugins.jvm.internal.DefaultJvmPluginExtension
import org.gradle.api.plugins.jvm.internal.JvmPluginExtension

plugins {
    `jvm-ecosystem`
    org.jetbrains.gradle.plugin.`idea-ext`
}

gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS_FULL

val props = FileInputStream("$rootDir/buildSrc/gradle.properties").use {
    Properties().apply { load(it) }
}

props.stringPropertyNames()
    .associateWith { props.getProperty(it) }
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
//    apply(plugin = "dependencies-reporting-helper") //FIXME: groovy won't run on java 16
    apply(plugin = "help-tasks")

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
        gradlePluginPortal()
        exclusiveContent {
            forRepository {
                maven("file://$rootDir/repo")
            }
            filter {
                includeModule("org.jetbrains.kotlinx", "kotlinx-io-jvm")
            }
        }
        mavenCentral()
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

//val jvm = extensions.getByType<JvmPluginExtension>()
//
//// A resolvable configuration to collect test report data
//val testReportData = jvm.createResolvableConfiguration("testReportData") {
//    requiresAttributes {
//        documentation("test-report-data")
//    }
//}
//
//// A resolvable configuration to collect JaCoCo coverage data
//val jacocoCoverageData = jvm.createResolvableConfiguration("jacocoCoverageData") {
//    requiresAttributes {
//        documentation("jacoco-coverage-data")
//    }
//}
//
//dependencies {
//    subprojects {
//        plugins.withId("testing-convention-helper") {
//            plugins.withType<JavaLibraryPlugin>().configureEach {
//                testReportData(this@dependencies.project(this@subprojects.path))
//                tasks.withType<Test>().configureEach {
//                    extensions.findByType<JacocoTaskExtension>()?.run {
//                        jacocoCoverageData(this@dependencies.project(this@subprojects.path))
//                    }
//                }
//            }
//            plugins.withType<JavaPlugin>().configureEach {
//                testReportData(this@dependencies.project(this@subprojects.path))
//                tasks.withType<Test>().configureEach {
//                    extensions.findByType<JacocoTaskExtension>()?.run {
//                        jacocoCoverageData(this@dependencies.project(this@subprojects.path))
//                    }
//                }
//            }
//        }
//    }
//}
//
//tasks.register<TestReport>("${project.name}TestReport") {
//    group = JavaBasePlugin.VERIFICATION_GROUP
//    destinationDir = file("$buildDir/reports")
//    // Use test results from testReportData configuration
//    (testResultDirs as ConfigurableFileCollection).from(testReportData)
//}
