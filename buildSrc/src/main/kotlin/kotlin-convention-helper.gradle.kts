import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import java.nio.file.Files
import java.nio.file.Paths
import versioning.Deps

plugins {
    idea
    java //if not applied `spring-boot-runner` reports missing `annotationProcessor` configuration
    kotlin
    `kotlin-kapt`
    `kotlin-allopen`
    `kotlin-noarg`
    `kotlin-jpa`
    `kotlin-spring`
    org.jetbrains.gradle.plugin.`idea-ext`
}

java {
    sourceCompatibility = JavaVersion.VERSION_14
    targetCompatibility = JavaVersion.current()
}

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions.freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "buildSrc", "kotlincArgs"))
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = (JavaVersion.current().takeUnless { it.isCompatibleWith(JavaVersion.VERSION_14) } ?: JavaVersion.VERSION_14).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            isFork = true
            forkOptions.jvmArgs = listOf("--enable-preview")
            Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).forEach(compilerArgs::add)
        }
    }
    withType<Test>().configureEach {
        jvmArgs("--enable-preview")
    }
    withType<JavaExec>().configureEach {
        jvmArgs("--enable-preview")
    }
}

kapt.javacOptions {
    Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).forEach(::option)
}

allOpen.annotations(
    "arrow.optics.optics",
    "io.micronaut.aop.Around",
    "io.micronaut.core.annotation.Introspected",
    "io.quarkus.test.junit.QuarkusTest",
    "javax.enterprise.context.ApplicationScoped",
    "javax.enterprise.context.RequestScoped",
    "javax.inject.Named",
    "javax.ws.rs.Path",
    "org.springframework.web.bind.annotation.RestController"
)

noArg.annotations(
    "javax.inject.Named",
    "javax.ws.rs.Path",
    "io.micronaut.core.annotation.Introspected"
)

//gnag {
//    isEnabled = true
//    setFailOnError(true)
//
//    ktlint { isEnabled = true }
//
//    github {
//        repoName("btkelly/android-svsu-acm-20131120")
//        authToken("0000000000000")
//        issueNumber("1")
//        setCommentInline(true)
//        setCommentOnSuccess(true)
//    }
//}

dependencies {
    implementation(enforcedPlatform(kotlin("bom")))
    implementation(platform(Deps.Platforms.JACKSON))
//    implementation(Deps.Libs.COROUTINES_JDK8)
//    implementation(Deps.Libs.COROUTINES_REACTOR)

    implementation(Deps.Libs.JACKSON_KOTLIN)
}
