import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
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

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.current()
}

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions {
            suppressWarnings = false
            verbose          = true
            freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "buildSrc", "kotlincArgs"))
        }
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = (JavaVersion.current().takeUnless(JavaVersion::isJava12Compatible) ?: JavaVersion.VERSION_12).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            isFork = true
            Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).forEach(compilerArgs::add)
        }
    }
}

kapt.javacOptions {
    Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).forEach(::option)
}

allOpen.annotations(
    "io.micronaut.aop.Around",
    "io.quarkus.test.junit.QuarkusTest",
    "javax.enterprise.context.ApplicationScoped",
    "javax.enterprise.context.RequestScoped",
    "javax.inject.Named",
    "javax.ws.rs.Path",
    "org.springframework.web.bind.annotation.RestController"
)

noArg.annotations(
    "javax.inject.Named",
    "javax.ws.rs.Path"
)

dependencies {
    //BOM
    implementation(enforcedPlatform(kotlin("bom")))
//    implementation(platform(Deps.Platforms.JACKSON))
    //BOM
    implementation(kotlin("stdlib-jdk8"))
//    implementation(Deps.Libs.COROUTINES_JDK8)
//    implementation(Deps.Libs.COROUTINES_REACTOR)

//    implementation(Deps.Libs.JACKSON_KOTLIN)
}
