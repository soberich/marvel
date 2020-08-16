import org.jetbrains.gradle.ext.IdeaCompilerConfiguration
import org.jetbrains.gradle.ext.ProjectSettings
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import org.gradle.api.plugins.ExtensionAware as EA

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_14)) { "At least Java 14 is required, current JVM is ${JavaVersion.current()}" }

plugins {
    java
    `java-gradle-plugin`
    `groovy-gradle-plugin`
    `kotlin-dsl`
    `build-dashboard`                                         // optional
    `help-tasks`                                              // optional
    `project-report`                                          // optional
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

val guavaVersion         : String by project
val ideaExtPluginVersion : String by project
val kotlinVersion        : String by project
val springBootVersion    : String by project
val versionsPluginVersion: String by project

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
    //testImplementation(kotlin("test"         , kotlinVersion))
    //testImplementation(kotlin("test-junit5"   , kotlinVersion))
    implementation("com.github.ben-manes"                              , "gradle-versions-plugin"                  , versionsPluginVersion)
    implementation("com.vaadin"                                        , "vaadin-gradle-plugin"                    , "+")
    implementation("com.vanniktech"                                    , "gradle-dependency-graph-generator-plugin", "0.5.0")
    implementation("gradle.plugin.com.gorylenko.gradle-git-properties" , "gradle-git-properties"                   , "+")
    implementation("gradle.plugin.com.webcohesion.enunciate"           , "enunciate-gradle"                        , "+")
    implementation("gradle.plugin.org.jetbrains.gradle.plugin.idea-ext", "gradle-idea-ext"                         , ideaExtPluginVersion)
    implementation("io.ebean"                                          , "ebean-gradle-plugin"                     , "+")
    implementation("io.swagger.core.v3"                                , "swagger-gradle-plugin"                   , "+")
    implementation("org.jetbrains.dokka"                               , "dokka-gradle-plugin"                     , "$kotlinVersion-rc")
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
        kotlinOptions.freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "kotlincArgs"))
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = JavaVersion.current().coerceAtMost(JavaVersion.VERSION_14).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            Files.lines(Paths.get("$rootDir", "javacArgs")).forEach(compilerArgs::add)
        }
    }
    withType<Test>().configureEach {
        jvmArgs("--enable-preview", "--illegal-access=warn")
    }
    withType<JavaExec>().configureEach {
        jvmArgs("--enable-preview", "--illegal-access=warn")
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
