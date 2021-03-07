import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import versioning.Deps
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.asSequence
import kotlin.KotlinVersion

plugins {
    java //if not applied `spring-boot-runner` reports missing `annotationProcessor` configuration
    kotlin
    `kotlin-kapt`
    `kotlin-allopen`
    `kotlin-noarg`
    `kotlin-jpa`
    `kotlin-spring`
    `kotlin-sam-with-receiver`
    //org.jetbrains.dokka
    com.github.`ben-manes`.versions
    org.kordamp.gradle.jandex
}

dependencies {
    kotlin.sourceSets.configureEach {
        dependencies {
            implementation(enforcedPlatform(kotlin("bom")))
            implementation("org.jetbrains.kotlinx:atomicfu:0.15.1")
            implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.3")
            //implementation("com.github.Kotlin:kotlinx.collections.immutable:master-SNAPSHOT")
            implementation("org.jetbrains.kotlinx:kotlinx-io-jvm:0.2.0")
            //implementation("com.github.Kotlin:kotlinx-io:master-SNAPSHOT")
            //implementation(Deps.Libs.COROUTINES_REACTOR)
        }
    }
}

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions {
            languageVersion = "${KotlinVersion.CURRENT.major}.${KotlinVersion.CURRENT.minor}"
            apiVersion = languageVersion
            freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "buildSrc", "kotlincArgs")).filterNot(String::isNullOrBlank)
        }
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = JavaVersion.current().coerceAtMost(if (KotlinVersion.CURRENT.isAtLeast(1, 4)) JavaVersion.VERSION_14 else JavaVersion.VERSION_13).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            isFork = true
            forkOptions.jvmArgs = listOf("--illegal-access=warn")
            release.set(JavaVersion.current().coerceAtMost(JavaVersion.VERSION_14).toString().toInt())
            targetCompatibility = release.get().toString() //FOR JPS compilation  TODO: Remove
            Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).asSequence().filterNot(String::isNullOrBlank).forEach(compilerArgs::plusAssign)
        }
    }
    withType<Test>().configureEach {
        jvmArgs("--illegal-access=warn")
    }
    withType<JavaExec>().configureEach {
        jvmArgs("--illegal-access=warn")
    }
}

kapt {
    correctErrorTypes = true
    javacOptions {
        //option("--processor-module-path", sourceSets.main.get().compileClasspath.asPath)
        /**
         * `--source` (N.B. `-source` with one dash does not work!) option to prevent warning from KotlinPluginWrapper about "source version `VERSION_14` been higher than max `SOURCE_11`"
         * `--target` (N.B. `-target` with one dash does not work!) for consistency with `--source` so it does not cause doubts on why is one set and other is not.
         * `--release` is the latest introduced and supported by Gradle version, yet, `kotlinc` seems to ignore it, again to avoid ambiguity.
         */
        option("--source",  JavaVersion.current().coerceAtMost(JavaVersion.VERSION_11).toString())
        option("--target",  JavaVersion.current().coerceAtMost(JavaVersion.VERSION_11).toString())
        option("--release", JavaVersion.current().coerceAtMost(JavaVersion.VERSION_11).toString())
        Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).asSequence().filterNot(String::isNullOrBlank).forEach(::option)
    }
}

noArg {
    invokeInitializers = true // run `init {...}` blocks
    annotations(
        "io.micronaut.core.annotation.Introspected",
        "javax.inject.Named",
        //`kotlin-jpa`
        "javax.persistence.Embeddable",
        "javax.persistence.Entity",
        "javax.persistence.MappedSuperclass",
        //`kotlin-jpa`
        "javax.ws.rs.Path",
        "org.springframework.web.bind.annotation.RestController"
    )
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
    "javax.ws.rs.ext.Provider",
    "javax.ws.rs.Path",
    //`kotlin-jpa`
    "javax.persistence.Entity",
    "javax.persistence.MappedSuperclass",
    "javax.persistence.Embeddable",
    //`kotlin-jpa`
    //`kotlin-spring`
    "org.springframework.boot.autoconfigure.SpringBootApplication",
    "org.springframework.boot.test.context.SpringBootTest",
    "org.springframework.cache.annotation.Cacheable",
    "org.springframework.scheduling.annotation.Async",
    "org.springframework.stereotype.Component",
    "org.springframework.transaction.annotation.Transactional"
    //`kotlin-spring`
)
