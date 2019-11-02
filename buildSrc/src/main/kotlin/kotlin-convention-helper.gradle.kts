import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

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
            freeCompilerArgs = file("$rootDir/kotlincArgs", PathValidation.FILE).readLines()
        }
    }
    withType<KotlinJvmCompile>().configureEach {
        kotlinOptions.jvmTarget = JavaVersion.current().toString()
        //kotlinOptions.jvmTarget = maxOf(JavaVersion.current().toString().toBigDecimal(), 12.toBigDecimal()).toString()
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            isFork = true
            file("$rootDir/javacArgs", PathValidation.FILE).forEachLine(action = compilerArgs::add)
            annotationProcessorGeneratedSourcesDirectory = file("src/generated/java")
        }
    }
}

kapt.javacOptions {
    file("$rootDir/javacArgs", PathValidation.FILE).forEachLine(action = ::option)
}

idea.module {
    val variants = arrayOf("/main"/*, "/debug", "/release"*/)
    val paths =
            variants.map { "$buildDir/generated/source/kaptKotlin$it" } +
            variants.map { "$buildDir/generated/source/kapt$it" } +
            variants.map { "$projectDir/src/generated/kotlin$it" } +
            variants.map { "$projectDir/src/generated/java$it" } +
            "$buildDir/tmp/kapt/main/kotlinGenerated" +
            "$projectDir/src/generated/kotlin" +
            "$projectDir/src/generated/java" +
            "$projectDir/generated"

    files(paths) {
        files.also(::setSourceDirs)
             .also(::setGeneratedSourceDirs)
    }
}

allOpen.annotations(
    "io.micronaut.aop.Around",
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
