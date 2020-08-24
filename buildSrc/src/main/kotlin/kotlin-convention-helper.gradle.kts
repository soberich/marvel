import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.internal.KaptTask
import versioning.Deps
import java.nio.file.Files
import java.nio.file.Paths
import java.util.spi.ToolProvider
import kotlin.streams.asSequence

plugins {
    java //if not applied `spring-boot-runner` reports missing `annotationProcessor` configuration
    kotlin
    //org.jetbrains.kotlin.jvm
    `kotlin-kapt`
    `kotlin-allopen`
    `kotlin-noarg`
    `kotlin-jpa`
    `kotlin-spring`
    `kotlin-sam-with-receiver`
    //org.jetbrains.dokka
}

public val mrjar9: Configuration by configurations.creating


dependencies {
    implementation(enforcedPlatform(kotlin("bom")))
    /*
     * need to explicitly have it here
     * 'buildSrc:compileKotlin' prints "w: Consider providing an explicit dependency on kotlin-reflect 1.4 to prevent strange errors"
     */
    //implementation(kotlin("reflect"))
    //implementation(kotlin("stdlib"))
    //implementation(kotlin("stdlib-common"))
    //implementation(kotlin("stdlib-jdk7"))
    //implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:atomicfu:0.14.4")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-io-jvm:0.2.0")
    //implementation(Deps.Libs.COROUTINES_REACTOR)
}

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions {
            val args = Files.readAllLines(Paths.get("$rootDir", "buildSrc", "kotlincArgs"))
            val kotlinVersion: String? by project
            languageVersion =
                if (true == kotlinVersion?.startsWith("1.4")) {
                    //override few args
                    arrayOf(
                        "-Xjvm-default=all",
                        "-Xuse-ir"
                    ).forEach(args::plusAssign)
                    "1.4"
                } else "${KotlinVersion.CURRENT.major}.${KotlinVersion.CURRENT.minor}"

            freeCompilerArgs = args.filterNot(String::isNullOrBlank)
        }
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = JavaVersion.current().coerceAtMost(JavaVersion.VERSION_13).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            isFork = true
            forkOptions.jvmArgs = listOf("--enable-preview", "--illegal-access=warn")
            release.set(JavaVersion.current().coerceAtMost(JavaVersion.VERSION_14).toString().toInt())
            Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).asSequence().filterNot(String::isNullOrBlank).forEach(compilerArgs::plusAssign)
        }
    }
    withType<Test>().configureEach {
        jvmArgs("--enable-preview", "--illegal-access=warn")
    }
    withType<JavaExec>().configureEach {
        jvmArgs("--enable-preview", "--illegal-access=warn")
    }

    afterEvaluate {
        withType<KaptTask>().configureEach {
            val mrjar9 by configurations
            mrjar9.files.forEach { mrjar9Jar ->
                val destDir = file("$buildDir/mrjar9")
                delete(destDir)
                mkdir(destDir)
                copy {
                    from(zipTree(mrjar9Jar))
                    into(destDir)
                }
                val v9Dir = destDir.toPath().resolve("META-INF/versions/9").toFile()
                copy {
                    from(v9Dir)
                    into(destDir)
                }
                delete(v9Dir)

                delete(mrjar9Jar)
                val jarExec = ToolProvider.findFirst( "jar" ).get()
                val result = jarExec.run(System.out, System.err,
                    "-cf", "$mrjar9Jar", "-C", destDir.path, ".")
                println("Fixed $mrjar9Jar")
            }
        }
    }

}

kapt.javacOptions {
    //option("--module-path", sourceSets.main.get().compileClasspath.asPath)
    option("-target", JavaVersion.current().coerceAtMost(JavaVersion.VERSION_11).toString())
    option("--release", JavaVersion.current().coerceAtMost(JavaVersion.VERSION_11).toString())
    Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).asSequence().filterNot(String::isNullOrBlank).forEach(::option)
}

allOpen.annotations(
    "arrow.optics.optics",
    "io.micronaut.aop.Around",
    "io.micronaut.core.annotation.Introspected",
    "io.micronaut.validation.Validated",
    "io.quarkus.test.junit.QuarkusTest",
    "javax.enterprise.context.ApplicationScoped",
    "javax.enterprise.context.RequestScoped",
    "javax.inject.Named",
    "javax.ws.rs.Path",
    "org.springframework.boot.autoconfigure.SpringBootApplication",
    "org.springframework.web.bind.annotation.RestController"
)

noArg {
    invokeInitializers = true // run `init {...}` blocks
    annotations(
        "io.micronaut.core.annotation.Introspected",
        "javax.inject.Named",
        "javax.ws.rs.Path"
    )
}
