@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

import org.jetbrains.gradle.ext.IdeaCompilerConfiguration
import org.jetbrains.gradle.ext.ProjectSettings
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence
import org.gradle.api.plugins.ExtensionAware as EA

plugins {
    java
    `kotlin-dsl`
    `build-dashboard`                             // optional
    `help-tasks`                                  // optional
    `project-report`                              // optional
    alias(libs.plugins.idea.ext)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://repo.spring.io/milestone") {
        content {
            includeGroupByRegex("org\\.springframework.*")
            includeGroup("io.projectreactor")
        }
    }
    maven("https://oss.sonatype.org/content/repositories/snapshots") {
        mavenContent {
            snapshotsOnly()
        }
    }
}

gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS_FULL

val CI = System.getenv("CI") != null

/*plugins'*/ dependencies {
    //noinspection DifferentKotlinGradleVersion
    implementation(enforcedPlatform(kotlin("bom", "latest.release")))
    implementation(platform(libs.guava)) //idea-ext uses it - it seems to stay safe to upgrade
    /*
     * need to explicitly have it here
     * 'buildSrc:compileKotlin' prints "w: Consider providing an explicit dependency on kotlin-reflect 1.4 to prevent strange errors"
     */
    //implementation(kotlin("reflect"))
    //implementation(kotlin("stdlib"))
    //implementation(kotlin("stdlib-common"))
    //implementation(kotlin("stdlib-jdk7"))
    //implementation(kotlin("stdlib-jdk8"))
    /*
     * N.B. Kotlin BOM does NOT contain kapt and compiler-plugins. There is another "special" BOM
     * e.g. "org.jetbrains.kotlin.kapt:org.jetbrains.kotlin.kapt.gradle.plugin", but it has longer name
     */
    implementation(kotlin("gradle-plugin"                , "latest.release"))
    implementation(kotlin("allopen"                      , "latest.release"))
    implementation(kotlin("noarg"                        , "latest.release"))
    implementation(kotlin("sam-with-receiver"            , "latest.release"))
    //implementation(kotlin("serialization", "latest.release"))
    implementation(gradleKotlinDsl()) //FOR JPS compilation  TODO: Remove
    //implementation("org.apache.logging.log4j"                          ,"log4j-core"                ,"latest.integration") //FOR JPS compilation  TODO: Remove
    //implementation("org.jetbrains.kotlin.kapt:org.jetbrains.kotlin.kapt.gradle.plugin:$"latest.release"") //FOR JPS compilation  TODO: Remove
    // implementation("com.vaadin"                                        , "vaadin-gradle-plugin"     , "+")

//    implementation(libs.bundles.gradle.plugins)
}

kotlinDslPluginOptions {
    jvmTarget.set(JavaVersion.current().coerceAtMost(JavaVersion.VERSION_16).toString())
}

afterEvaluate {
    kotlinDslPluginOptions {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.current().coerceAtMost(JavaVersion.VERSION_16).toString()
                languageVersion = "${KotlinVersion.CURRENT.major}.${KotlinVersion.CURRENT.minor}"
                apiVersion = languageVersion
                freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "kotlincArgs")).filterNot(String::isNullOrBlank)
            }
        }
    }
}

((idea.project as EA).the<ProjectSettings>() as EA).configure<IdeaCompilerConfiguration> {
    additionalVmOptions = "${project.property("org.gradle.jvmargs")}"
    //addNotNullAssertions = true TODO
    parallelCompilation = true
    useReleaseOption = true
    javac {
        javacAdditionalOptions = Files.readString(Paths.get("$rootDir", "javacArgs"))
        preferTargetJDKCompiler = true
    }
}

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions {
            languageVersion = "${KotlinVersion.CURRENT.major}.${KotlinVersion.CURRENT.minor}"
            apiVersion = languageVersion
            freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "kotlincArgs")).filterNot(String::isNullOrBlank)
        }
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = JavaVersion.current().coerceAtMost(JavaVersion.VERSION_16).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            isFork = true
            release.set(JavaVersion.current().coerceAtMost(JavaVersion.VERSION_16).toString().toInt())
            targetCompatibility = release.get().toString() //FOR JPS compilation  TODO: Remove

            Files.lines(Paths.get("$rootDir", "javacArgs")).asSequence().filterNot(String::isNullOrBlank).forEach(compilerArgs::plusAssign)
        }
    }
    withType<Test>().configureEach {
        // jvmArgs("")
    }
    withType<JavaExec>().configureEach {
        // jvmArgs("")
    }
}
