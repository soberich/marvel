import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import versioning.Deps
import java.nio.file.Files
import java.nio.file.Paths

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

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions.freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "buildSrc", "kotlincArgs"))
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = (JavaVersion.current().takeUnless { it.isCompatibleWith(JavaVersion.VERSION_13) } ?: JavaVersion.VERSION_13).toString()
    }
    withType<JavaCompile>().configureEach {
        //modularity.inferModulePath.set(true)
        options.apply {
            isFork = true
            forkOptions.jvmArgs = listOf("--enable-preview", "--illegal-access=warn")
            Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).forEach(compilerArgs::add)
        }
    }
    withType<Test>().configureEach {
        jvmArgs("--enable-preview", "--illegal-access=warn")
    }
    withType<JavaExec>().configureEach {
        jvmArgs("--enable-preview", "--illegal-access=warn")
    }
}

kapt.javacOptions {
    Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).forEach(::option)
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
    "org.springframework.web.bind.annotation.RestController"
)

noArg.annotations(
    "io.micronaut.core.annotation.Introspected",
    "javax.inject.Named",
    "javax.ws.rs.Path"
)

dependencies {
    implementation(enforcedPlatform(kotlin("bom")))
    implementation(platform(Deps.Platforms.JACKSON))
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.2")
//    implementation(Deps.Libs.COROUTINES_JDK8)
//    implementation(Deps.Libs.COROUTINES_REACTOR)

    implementation(Deps.Libs.JACKSON_KOTLIN)
}
