plugins {
    java
    `java-gradle-plugin`
    `kotlin-dsl`
    `build-dashboard`                                         // optional
    `help-tasks`                                              // optional
    `project-report`                                          // optional
    id("com.github.ben-manes.versions")      version "0.27.0" // optional
    id("se.patrikerdes.use-latest-versions") version "0.2.12" // optional
}

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

kotlinDslPluginOptions.experimentalWarning.set(false)

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.current()

tasks {
    compileKotlin {
        kotlinOptions {
            suppressWarnings = false
            verbose          = true
            freeCompilerArgs = file("../kotlincArgs", PathValidation.FILE).readLines()
            jvmTarget        = JavaVersion.current().toString()
        }
    }
    compileJava {
        options.apply {
            file("../javacArgs", PathValidation.FILE).forEachLine(action = compilerArgs::add)
        }
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
    implementation("com.github.ben-manes",                              "gradle-versions-plugin",                   "0.27.0")
    implementation("com.vanniktech",                                    "gradle-dependency-graph-generator-plugin", "0.5.0")
    implementation("gradle.plugin.com.gorylenko.gradle-git-properties", "gradle-git-properties",                    "+")
    implementation("io.ebean",                                          "ebean-gradle-plugin",                      "+")
    implementation("io.swagger.core.v3",                                "swagger-gradle-plugin",                    "2.0.10")
    implementation("org.sonarsource.scanner.gradle",                    "sonarqube-gradle-plugin",                  "2.8")
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
