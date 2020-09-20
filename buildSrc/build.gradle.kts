@file:Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")

import org.jetbrains.gradle.ext.IdeaCompilerConfiguration
import org.jetbrains.gradle.ext.ProjectSettings
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import java.lang.Boolean
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence
import org.gradle.api.plugins.ExtensionAware as EA

plugins {
    java
    `java-gradle-plugin`
    //`groovy-gradle-plugin`
    `kotlin-dsl`//                                   version "1.4.0"
    `build-dashboard`                              // optional
    `help-tasks`                                   // optional
    `project-report`                               // optional
    id("com.github.ben-manes.versions")
    id("org.jetbrains.gradle.plugin.idea-ext")
}

repositories {
    gradlePluginPortal()
    jcenter()
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

val ideaActive = Boolean.getBoolean("idea.active")

val CI = System.getenv("CI") != null

val byteBuddyVersion      : String by project
val guavaVersion          : String by project
val hibernateVersion      : String by project
val ideaExtPluginVersion  : String by project
val jlinkPluginVersion    : String by project
val kotlinVersion         : String by project
val micronautPluginVersion: String by project
val shadowPluginVersion   : String by project
val spotBugsPluginVersion : String by project
val springBootVersion     : String by project
val versionsPluginVersion : String by project

/*plugins'*/ dependencies {
    //noinspection DifferentKotlinGradleVersion
    implementation(enforcedPlatform(kotlin("bom", kotlinVersion)))
    implementation(platform("com.google.guava:guava-bom:$guavaVersion")) //idea-ext uses it - it seems to stay safe to upgrade
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
    implementation(kotlin("gradle-plugin"                , kotlinVersion))
    implementation(kotlin("allopen"                      , kotlinVersion))
    implementation(kotlin("noarg"                        , kotlinVersion))
    implementation(kotlin("sam-with-receiver"            , kotlinVersion))
    //implementation(kotlin("serialization", kotlinVersion))
    implementation(gradleKotlinDsl()) //FOR IDEA compilation
    implementation("org.apache.logging.log4j:log4j-core:2.11.0") //FOR JPS compilation
    //implementation("org.jetbrains.kotlin.kapt:org.jetbrains.kotlin.kapt.gradle.plugin:$kotlinVersion") //FOR IDEA compilation
    implementation("com.github.ben-manes"                              , "gradle-versions-plugin"                  , versionsPluginVersion)
    implementation("com.github.jengelman.gradle.plugins"               , "shadow"                                  , shadowPluginVersion)
    implementation("com.vaadin"                                        , "vaadin-gradle-plugin"                    , "+")
    implementation("com.vanniktech"                                    , "gradle-dependency-graph-generator-plugin", "0.5.0")
    /*TODO: Apparently, these plugins needs PRs to remove `gradle.plugin` from group. Looks amateur*/
    implementation("gradle.plugin.com.github.spotbugs.snom"            , "spotbugs-gradle-plugin"                  , spotBugsPluginVersion)
    implementation("gradle.plugin.com.gorylenko.gradle-git-properties" , "gradle-git-properties"                   , "+")
    implementation("gradle.plugin.com.webcohesion.enunciate"           , "enunciate-gradle"                        , "+")
    //implementation("gradle.plugin.io.quarkus"                          , "quarkus-gradle-plugin"                   , quarkusVersion)
    implementation("gradle.plugin.net.bytebuddy"                       , "byte-buddy-gradle-plugin"                , byteBuddyVersion)
    implementation("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext", "gradle-idea-ext"                         , ideaExtPluginVersion)
    implementation("io.ebean"                                          , "ebean-gradle-plugin"                     , "+")
    implementation("io.micronaut.gradle"                               , "micronaut-gradle-plugin"                 , micronautPluginVersion)
    implementation("org.kordamp.gradle"                                , "jandex-gradle-plugin"                    , "+")
    implementation("io.swagger.core.v3"                                , "swagger-gradle-plugin"                   , "+")
    implementation("org.beryx"                                         , "badass-jlink-plugin"                     , jlinkPluginVersion)
    implementation("org.hibernate"                                     , "hibernate-gradle-plugin"                 , hibernateVersion)
    implementation("org.jetbrains.dokka"                               , "dokka-gradle-plugin"                     , "1.4.0")
    implementation("org.sonarsource.scanner.gradle"                    , "sonarqube-gradle-plugin"                 , "+")
    implementation("org.springframework.boot"                          , "spring-boot-gradle-plugin"               , springBootVersion)
    //implementation("se.patrikerdes"                                    , "gradle-use-latest-versions-plugin"       , "+")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
    jvmTarget.set(JavaVersion.current().coerceAtMost(JavaVersion.VERSION_13).toString())
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
        kotlinOptions.jvmTarget = JavaVersion.current().coerceAtMost(JavaVersion.VERSION_13).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            isFork = true
            forkOptions.jvmArgs = listOf("--illegal-access=warn")
            release.set(JavaVersion.current().coerceAtMost(JavaVersion.VERSION_14).toString().toInt())
            targetCompatibility = release.get().toString() //Not affecting compilation. For IDEA integration only.  TODO: Remove
            Files.lines(Paths.get("$rootDir", "javacArgs")).asSequence().filterNot(String::isNullOrBlank).forEach(compilerArgs::plusAssign)
        }
    }
    withType<Test>().configureEach {
        jvmArgs("--illegal-access=warn")
    }
    withType<JavaExec>().configureEach {
        jvmArgs("--illegal-access=warn")
    }
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
                        .replace("$", "") //FIXME: `Char.MIN_VALUE` is probably incorrect
                        .replace("""((?!^)[^_])([A-Z0-9]+)""".toRegex(), "$1-$2").toLowerCase()
                    register(name) {
                        id = name.substringBeforeLast("-plugin")
                        implementationClass = project.sourceSets
                            .map(SourceSet::allSource)
                            .flatMap(SourceDirectorySet::getSrcDirs)
                            .asSequence()
                            .map(File::absolutePath)
                            .filter { it in absolutePluginPath.toString() }
                            .map { absolutePluginPath.toString().substringAfterLast(it + File.separator) }
                            .map { it.substringBeforeLast('.') }
                            .map { it.replace('/', '.') }
                            .single()
                    }
                }
        }
    }
}
