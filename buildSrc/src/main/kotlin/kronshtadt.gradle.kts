
import org.gradle.api.JavaVersion.VERSION_1_8
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.internal.KaptTask
import versioning.Deps
import java.nio.charset.StandardCharsets.UTF_8

plugins {
    idea
    kotlin
//    `kotlin-kapt`
    `kotlin-allopen`
    `kotlin-noarg`
    `kotlin-jpa`
    `kotlin-spring`
}

repositories {
    maven("http://dl.bintray.com/kotlin/kotlin-eap")
}

idea.module {
    val variants = arrayOf("/main"/*, "/debug", "/release"*/)
    val paths =
            variants.map { "$buildDir/generated/source/kaptKotlin$it" } +
            variants.map { "$buildDir/generated/source/kapt$it" } +
            variants.map { "$projectDir/src/generated/kotlin$it" } +
            variants.map { "$projectDir/src/generated/java$it" } +
            "$buildDir/tmp/kapt/main/kotlinGenerated" +
            "$projectDir/generated"

    files(paths) {
        files.also(::setSourceDirs)
             .also(::setGeneratedSourceDirs)
    }
}

//kapt {
//    javacOptions {
//        file("$rootDir/javacArgs", PathValidation.FILE).forEachLine(action = ::option)
//    }
//    arguments {
//        arg("kapt.kotlin.generated", "$projectDir/generated")
//    }
//}

allOpen {
    annotations(
        "io.micronaut.aop.Around",
        "javax.enterprise.context.ApplicationScoped",
        "javax.enterprise.context.RequestScoped",
        "javax.inject.Named",
        "javax.ws.rs.Path"
    )
}

noArg {
    annotations(
        "javax.inject.Named",
        "javax.ws.rs.Path"
    )
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
        kotlinOptions.jvmTarget = VERSION_1_8.toString()
    }
    withType<KaptTask>().configureEach {
        destinationDir = file("$projectDir/generated")
        kotlinSourcesDestinationDir = file("$projectDir/generated")
    }
    withType<JavaCompile>().configureEach {
        options.apply {
            encoding      = UTF_8.name()
            isIncremental = true
            isFork        = true
            // compiler deamon JVM options
            forkOptions.jvmArgs = project.extra["org.gradle.jvmargs"].toString().split(" ")
            // javac tasks args
            file("$rootDir/javacArgs", PathValidation.FILE).forEachLine(action = compilerArgs::add)
        }
    }
}

dependencies {
    //BOM
    implementation(platform(Deps.Platforms.JACKSON))
    implementation(enforcedPlatform(kotlin("bom")))
    //BOM
    implementation(kotlin("stdlib-jdk8"))
    implementation(Deps.Libs.COROUTINES_JDK8)
    implementation(Deps.Libs.COROUTINES_REACTOR)

    implementation(Deps.Libs.JACKSON_KOTLIN)
}
