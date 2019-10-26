plugins {
    idea
    java //if not applied `spring-boot-runner` reports missing `annotationProcessor` configuration
    kotlin
    `kotlin-kapt`
    `kotlin-allopen`
    `kotlin-noarg`
    `kotlin-jpa`
    `kotlin-spring`
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.current()

tasks {
    compileKotlin {
        kotlinOptions {
            suppressWarnings = false
            verbose          = true
            freeCompilerArgs = file("$rootDir/kotlincArgs", PathValidation.FILE).readLines()
            jvmTarget        = JavaVersion.current().toString()
        }
    }
    compileJava {
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
