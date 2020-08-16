import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import versioning.Deps
import java.nio.file.Files
import java.nio.file.Paths

plugins {
    java //if not applied `spring-boot-runner` reports missing `annotationProcessor` configuration
    kotlin
    `kotlin-kapt`
    `kotlin-allopen`
    `kotlin-noarg`
    `kotlin-jpa`
    `kotlin-spring`
    //org.jetbrains.dokka
}

dependencies {
    implementation(enforcedPlatform(kotlin("bom")))
    /*
     * need to explicitly have it here
     * 'buildSrc:compileKotlin' prints "w: Consider providing an explicit dependency on kotlin-reflect 1.4 to prevent strange errors"
     */
    //implementation(kotlin("reflect"))
    //implementation(kotlin("stdlib"))
    //implementation(kotlin("stdlib-jdk7"))
    //implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.2")
    implementation("org.jetbrains.kotlinx:atomicfu:0.14.4")
    //implementation("com.github.Kotlin:kotlinx-io:master-SNAPSHOT")
    //implementation(Deps.Libs.COROUTINES_REACTOR)
}

tasks {
    withType<KotlinCompile<*>>().configureEach {
        kotlinOptions.freeCompilerArgs = Files.readAllLines(Paths.get("$rootDir", "buildSrc", "kotlincArgs"))
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = JavaVersion.current().coerceAtMost(JavaVersion.VERSION_14).toString()
    }
    withType<JavaCompile>().configureEach {
        //modularity.inferModulePath.set(true)
        options.apply {
            isFork = true
            forkOptions.jvmArgs = listOf("--enable-preview", "--illegal-access=warn")
            Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).forEach { compilerArgs.add(it) } //Ant (e.i. Ittellij driven build) can't compile ambiguous function reference
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
    option("--release", JavaVersion.current().coerceAtMost(JavaVersion.VERSION_14).toString())
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
    "org.springframework.boot.autoconfigure.SpringBootApplication",
    "org.springframework.web.bind.annotation.RestController"
)

noArg.annotations(
    "io.micronaut.core.annotation.Introspected",
    "javax.inject.Named",
    "javax.ws.rs.Path"
)
