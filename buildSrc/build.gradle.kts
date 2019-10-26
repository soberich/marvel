
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    java
    `java-gradle-plugin`
    `kotlin-dsl`
    `build-dashboard`                                         // optional
    `help-tasks`                                              // optional
    `project-report`                                          // optional
    id("com.github.ben-manes.versions")      version "0.22.0" // optional
    id("se.patrikerdes.use-latest-versions") version "0.2.12" // optional
}

kotlinDslPluginOptions.experimentalWarning.set(false)

repositories {
    gradlePluginPortal()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-eap") {
        content {
            includeGroup("org.jetbrains.kotlin")
        }
    }
    maven(url = "https://repo.spring.io/milestone") {
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
    maven("https://jitpack.io") {
        content {
            includeGroup("com.github.JetBrains")
        }
    }
}

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions {
            suppressWarnings = false
            verbose          = true
            freeCompilerArgs = file("../kotlincArgs", PathValidation.FILE).readLines()
        }
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
        //kotlinOptions.jvmTarget = maxOf(JavaVersion.current().toString().toBigDecimal(), 12.toBigDecimal()).toString()
    }
}

//val kotlinVersion = "1.3.5+"
val kotlinVersion = KotlinVersion(1, 3, 50).toString()

/*plugins'*/ dependencies {
    //noinspection DifferentKotlinGradleVersion
    implementation(enforcedPlatform(kotlin("bom", kotlinVersion)))
    implementation(kotlin("stdlib-jdk8"))
    /**
     * N.B. Kotlin BOM does NOT contain kapt and compiler-plugins. There is another "special" BOM
     * e.g. "org.jetbrains.kotlin.kapt:org.jetbrains.kotlin.kapt.gradle.plugin", but it has longer name
     */
    implementation(kotlin("gradle-plugin"    , kotlinVersion))
    implementation(kotlin("allopen"          , kotlinVersion))
    implementation(kotlin("noarg"            , kotlinVersion))
    implementation(kotlin("sam-with-receiver", kotlinVersion))
//    implementation(kotlin("serialization", kotlinVersion))
    implementation("com.bmuschko",                                      "gradle-docker-plugin",                     "5.0.0")
    implementation("com.github.ben-manes",                              "gradle-versions-plugin",                   "0.22.0")
    implementation("com.moowork.gradle",                                "gradle-node-plugin",                       "+")
    implementation("com.vanniktech",                                    "gradle-dependency-graph-generator-plugin", "0.5.0")
    implementation("gradle.plugin.com.gorylenko.gradle-git-properties", "gradle-git-properties",                    "+")
    implementation("io.ebean",                                          "ebean-gradle-plugin",                      "+")
    implementation("io.swagger.core.v3",                                "swagger-gradle-plugin",                    "2.0.8")
    implementation("nu.studer",                                         "gradle-credentials-plugin",                "1.0.7")
    implementation("org.sonarsource.scanner.gradle",                    "sonarqube-gradle-plugin",                  "2.7.1")
    implementation("org.springframework.boot",                          "spring-boot-gradle-plugin",                "2.2.0.M5")
    implementation("se.patrikerdes",                                    "gradle-use-latest-versions-plugin",        "0.2.12")
    //TODO:
//    implementation("com.github.JetBrains",                              "gradle-idea-ext-plugin",                   "master-SNAPSHOT")
}

gradlePlugin {
    plugins {
        for (plugin in file("./src/main", PathValidation.DIRECTORY).walk().filter { it.isFile && it.name.contains("Plugin") }) {
            val name = plugin.nameWithoutExtension
                .replace(Regex("\\$"), "")
                .replace(Regex("((?!^)[^_])([A-Z0-9]+)"), "$1-$2").toLowerCase()
            register(name) {
                id = name.substringBeforeLast("-plugin")
                implementationClass = plugin.nameWithoutExtension
            }
        }
    }
}
