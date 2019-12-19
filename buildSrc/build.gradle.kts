import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

plugins {
    java
    `java-gradle-plugin`
    `kotlin-dsl`
    `build-dashboard`                                         // optional
    `help-tasks`                                              // optional
    `project-report`                                          // optional
    id("com.github.ben-manes.versions")      version "0.27.0" // optional
    id("se.patrikerdes.use-latest-versions") version "0.2.13" // optional
}

repositories {
    gradlePluginPortal()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-eap") {
        content {
            includeGroup("org.jetbrains.kotlin")
        }
    }
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

kotlinDslPluginOptions {
    experimentalWarning.set(false)
    jvmTarget.set((JavaVersion.current().takeUnless(JavaVersion::isJava12Compatible) ?: JavaVersion.VERSION_12).toString())
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.current()
}

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions {
            suppressWarnings = false
            verbose          = true
            freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "kotlincArgs"))
        }
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = (JavaVersion.current().takeUnless(JavaVersion::isJava12Compatible) ?: JavaVersion.VERSION_12).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            Files.lines(Paths.get("$rootDir", "javacArgs")).forEach(compilerArgs::add)
        }
    }
}

//val kotlinVersion = "1.3.6+"
val kotlinVersion = KotlinVersion(1, 3, 61).toString()

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
    implementation("gradle.plugin.com.webcohesion.enunciate",           "enunciate-gradle",                         "+")
    implementation("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext","gradle-idea-ext",                          "0.7")
    implementation("io.ebean",                                          "ebean-gradle-plugin",                      "+")
    implementation("io.swagger.core.v3",                                "swagger-gradle-plugin",                    "+")
    implementation("org.sonarsource.scanner.gradle",                    "sonarqube-gradle-plugin",                  "+")
    implementation("se.patrikerdes",                                    "gradle-use-latest-versions-plugin",        "+")
}

/**
 * It is not used as all plugins converted to precompiled scripts, however this is a neat way to get on board other plugins if needed.
 */
gradlePlugin {
    plugins {
        Files.walk(Paths.get("$rootDir", "src", "main")).use {
            it.filter(Files::isRegularFile)
                .filter { it.fileName.toString().substringBeforeLast(".").endsWith("Plugin") }
                .map(Path::toAbsolutePath)
                .forEach { absolutePluginPath ->
                    val name = absolutePluginPath.fileName.toString().substringBeforeLast(".")
                        .replace('$', Char.MIN_VALUE) //FIXME: `Char.MIN_VALUE` is probably incorrect
                        .replace("""((?!^)[^_])([A-Z0-9]+)""".toRegex(), "$1-$2").toLowerCase()
                    register(name) {
                        id = name.substringBeforeLast("-plugin")
                        implementationClass = project.sourceSets
                            .map(SourceSet::allSource)
                            .flatMap(SourceDirectorySet::getSrcDirs)
                            .asSequence()
                            .map(File::absolutePath)
                            .filter { absolutePluginPath.toString().contains(it) }
                            .map { absolutePluginPath.toString().substringAfterLast(it + File.separator) }
                            .map { it.substringBeforeLast('.') }
                            .map { it.replace('/', '.') }
                            .single()
                    }
                }
        }
    }
}
