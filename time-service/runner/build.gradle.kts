import versioning.Deps

plugins {
    application
    `kotlin-convention-helper`
    `jackson-convention-helper`
    com.webcohesion.enunciate
    `dependencies-reporting-helper`
    id("com.github.johnrengelman.shadow") version "6.0.0"
    // FIXME: Micronaut Gradle plugin fails by now with Kotlin 1.4.0-rc.
//    id("io.micronaut.application")
}

repositories.jcenter()

val developmentOnly by configurations.creating
/**
 * ORDER MATTERS!!
 * JPAMODELGEN Should go first!
 */
dependencies {
    enunciate("dk.jyskebank.tooling.enunciate:enunciate-openapi:1.1.+")
    arrayOf(
        platform(Deps.Platforms.MICRONAUT)                               , /*TODO: uncomment for 2.x*/
        "io.micronaut.configuration:micronaut-openapi",
        "io.micronaut.data:micronaut-data-processor:1.1.3",
        "io.micronaut.spring:micronaut-spring-annotation",
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-boot-annotation",
        "io.micronaut.spring:micronaut-spring-web-annotation:2.1.0",
        "io.micronaut:micronaut-graal",
        "io.micronaut:micronaut-inject-java"                             , /*TODO:drop version for 2.x*/
        "io.micronaut:micronaut-management",
        "io.micronaut:micronaut-validation"                              , /*TODO:drop version for 2.x*/
        Deps.Libs.VALIDATOR_AP
    ).forEach(::kapt)

    //implementation("org.springframework.boot:spring-boot-starter:2.1.0.RELEASE")/*TODO: uncomment for 2.x*/ /*Didn't work for /routes*/

    arrayOf(
        platform(Deps.Platforms.MICRONAUT)                               , /*TODO: uncomment for 2.x*/
        "org.graalvm.nativeimage:svm",
        Deps.Jakarta.CDI,
        Deps.Jakarta.SERVLET,
        Deps.Libs.SLF4J_API
    ).forEach(::compileOnly)

    arrayOf(
        platform(Deps.Platforms.MICRONAUT)                               , /*TODO: uncomment for 2.x*/
        "net.java.dev.jna:jna",
        "io.micronaut:micronaut-runtime-osx",
        "io.methvin:directory-watcher"
    ).forEach { developmentOnly(it) }

    arrayOf(
        platform(Deps.Platforms.MICRONAUT)                               , /*TODO: uncomment for 2.x*/
        //platform(Deps.Platforms.MICRONAUT_DATA)                        , /*TODO: uncomment for 2.x*/
        project(":shared-convention"),
        project(":time-service.api"),
        //"io.micronaut.configuration:micronaut-micrometer-core:1.3.0"   , /*FIXME: `io.micronaut.configuration` is a pre-2.x!*/
        "io.micronaut.data:micronaut-data-hibernate-jpa",
        "io.micronaut.kotlin:micronaut-kotlin-extension-functions:2.0.0" , /*2.0.1.BUILD-SNAPSHOT"*/
        "io.micronaut:micronaut-inject",
        "io.micronaut:micronaut-management",
        "io.micronaut:micronaut-runtime",
        "io.micronaut:micronaut-validation"                              , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.swagger.core.v3:swagger-annotations",
        "io.swagger.core.v3:swagger-annotations",
        //"org.springframework.boot:spring-boot-starter-web:2.1.0.RELEASE",/*Didn't work for /routes*/
        "org.springframework:spring-context"                              /*TODO: Extract to `Deps.Libs`*/
        //"io.ktor:ktor-server-netty:+"                                  , /*TODO:Not sure*/
        //"io.micronaut.kotlin:micronaut-ktor:2.0.0"/*2.0.1.BUILD-SNAPSHOT"*/ /*TODO: uncomment for 2.x */
    ).forEach(::implementation)

    arrayOf(
        platform(Deps.Platforms.MICRONAUT)                               , /*TODO: uncomment for 2.x*/
        //platform(Deps.Platforms.MICRONAUT_DATA)                        , /*TODO: uncomment for 2.x*/
        project(":time-service.app"),
        "ch.qos.logback:logback-classic:1.2.3",
        "com.h2database:h2",
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "io.micronaut.cache:micronaut-cache-caffeine",
        "io.micronaut.configuration:micronaut-hibernate-jpa-spring:1.4.1", /*FIXME: `io.micronaut.configuration` is a pre-2.x!*/
        "io.micronaut.configuration:micronaut-hibernate-jpa:1.4.1"       , /*FIXME: `io.micronaut.configuration` is a pre-2.x!*/
        "io.micronaut.configuration:micronaut-hibernate-validator:1.2.0" , /*FIXME: `configuration` is missing in group for 2.x */ /*This switches to "full" Hib Valid-or https://docs.micronaut.io/2.0.0.M2/guide/index.html#beanValidation*/
        "io.micronaut.configuration:micronaut-jdbc-hikari:1.4.1"         , /*FIXME: `io.micronaut.configuration` is a pre-2.x!*/
        "io.micronaut.data:micronaut-data-spring",
        "io.micronaut.kotlin:micronaut-kotlin-runtime:2.0.0"             , /*2.0.1.BUILD-SNAPSHOT"*/
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-web",
        "io.micronaut:micronaut-http-client",
        "io.micronaut:micronaut-http-server"                             , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.micronaut:micronaut-http-server-netty"                       , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.micronaut:micronaut-inject"                                  , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.micronaut:micronaut-spring"                                  , /*TODO:drop version for 2.x*/
        "io.micronaut:micronaut-validation"                              , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.swagger.core.v3:swagger-annotations",
        "ognl:ognl:3.2.14",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.3",
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.webjars:bootstrap:+",
        "org.webjars:swagger-ui:+"
        //"io.micronaut.beanvalidation:micronaut-hibernate-validator"    , /*TODO: Replace `io.micronaut.configuration` one for 2.x*//* This switches to "full" Hib Valid-or https://docs.micronaut.io/2.0.0.M2/guide/index.html#beanValidation */
        //"io.micronaut.sql:micronaut-hibernate-jpa"                     , /*TODO: Replace `io.micronaut.configuration` one for 2.x*/
        //"io.micronaut.sql:micronaut-jdbc-hikari"                       , /*TODO: Replace `io.micronaut.configuration` one for 2.x*/
    ).forEach(::runtimeOnly)

    arrayOf(
        platform(Deps.Platforms.MICRONAUT)                               , /*TODO: uncomment for 2.x*/
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.3",
        "io.micronaut:micronaut-inject-java",
        "io.micronaut.spring:micronaut-spring-web-annotation:2.1.0",
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-boot-annotation"
    ).forEach(::kaptTest)

    arrayOf(
        platform(Deps.Platforms.MICRONAUT)                               , /*TODO: uncomment for 2.x*/
        kotlin("test-junit5"),
        "org.spekframework.spek2:spek-dsl-jvm:2.0.12",
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.junit.jupiter:junit-jupiter-api",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.3",
        "io.mockk:mockk:1.9.3",
        "io.micronaut.test:micronaut-test-kotlintest",
        "io.micronaut.test:micronaut-test-junit5",
        "io.kotlintest:kotlintest-runner-junit5:3.3.2"
    ).forEach(::testImplementation)

    arrayOf(
        platform(Deps.Platforms.MICRONAUT)                               , /*TODO: uncomment for 2.x*/
        "org.spekframework.spek2:spek-runner-junit5:2.0.12",
        "org.junit.jupiter:junit-jupiter-engine"
    ).forEach(::testRuntimeOnly)
}

rootProject.idea.project {
    this as ExtensionAware
    configure<ProjectSettings> {
        doNotDetectFrameworks("spring")
    }
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

//// FIXME: Micronaut Gradle plugin fails by now with Kotlin 1.4.0-rc.
//micronaut {
//    version("1.3.7")
//    enableNativeImage.set(true)
//}

application {
    mainClass.convention("com.example.marvel.runtime.ApplicationKt")
    mainClassName = "com.example.marvel.runtime.ApplicationKt"
    applicationDefaultJvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
}

tasks {
    withType<ProcessResources>().configureEach {
        filesMatching("openapi.properties") {
            System.setProperty("micronaut.openapi.config.file", path) //FIXME
            expand(project.properties)
        }
    }
    shadowJar {
        mergeServiceFiles()
    }
    (run) {
        jvmArgs("-Dmicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop")
    }
//    val developmentOnly by configurations.getting
    withType<JavaExec>().configureEach {
        classpath += developmentOnly
    }
    withType<Test>().configureEach {
        classpath += developmentOnly
        useJUnitPlatform()
        exclude("**/Native*")
    }
}

configure(listOf(
    project(":time-service.app"),
    project(":time-service.api")
)) {
    apply(plugin = "kotlin-convention-helper")
//    apply(plugin = "io.micronaut.gradle.MicronautApplicationPlugin")
    dependencies {
        arrayOf(
            /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
            platform(Deps.Platforms.MICRONAUT),
            "io.micronaut:micronaut-inject-java"
        ).forEach(::kapt)

        arrayOf(
            /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
            platform(Deps.Platforms.MICRONAUT),
            "io.micronaut:micronaut-inject"
        ).forEach(::implementation)
    }
}
