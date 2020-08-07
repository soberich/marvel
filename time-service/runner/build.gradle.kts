import versioning.Deps

plugins {
    application
    `kotlin-convention-helper`
    `jackson-convention-helper`
    com.webcohesion.enunciate
    `dependencies-reporting-helper`
    id("com.github.johnrengelman.shadow") version "6.0.0"
    // FIXME: Micronaut Gradle plugin fails by now at Java 14.
//    id("io.micronaut.application") version "1.0.0.M9"
}

repositories.jcenter()

val developmentOnly by configurations.creating
/**
 * ORDER MATTERS!!
 * JPAMODELGEN Should go first!
 * TODO: Remove Quarkus form convention default configuration to not to leak here.
 */
// FIXME: Micronaut Gradle plugin fails by now at Java 14.
//micronaut {
//    version("2.0.1")
//}
dependencies {
    enunciate("dk.jyskebank.tooling.enunciate:enunciate-openapi:1.1.+")
    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        platform(Deps.Platforms.MICRONAUT),
        "io.micronaut:micronaut-validation",
        "io.micronaut:micronaut-management",
        "io.micronaut:micronaut-inject-java",
        "io.micronaut:micronaut-graal",
        "io.micronaut.spring:micronaut-spring-web-annotation",
        "io.micronaut.spring:micronaut-spring-boot-annotation",
        "io.micronaut.spring:micronaut-spring-annotation",
        "io.micronaut.data:micronaut-data-processor:1.1.3",
        "io.micronaut.configuration:micronaut-openapi",
        Deps.Libs.VALIDATOR_AP
    ).forEach(::kapt)

    arrayOf(
        "org.graalvm.nativeimage:svm",
        Deps.Jakarta.CDI,
        Deps.Jakarta.SERVLET,
        Deps.Libs.SLF4J_API
    ).forEach(::compileOnly)

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        platform(Deps.Platforms.MICRONAUT),
        "net.java.dev.jna:jna",
        "io.micronaut:micronaut-runtime-osx",
        "io.methvin:directory-watcher"
    ).forEach { developmentOnly(it) }

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        platform(Deps.Platforms.MICRONAUT),
        platform(Deps.Platforms.MICRONAUT_DATA),
        project(":shared-convention"),
        project(":time-service.api"),
        "org.springframework:spring-context",
        "io.swagger.core.v3:swagger-annotations",
        "io.micronaut:micronaut-validation",
        "io.micronaut:micronaut-runtime",
        "io.micronaut:micronaut-management",
        "io.micronaut.kotlin:micronaut-kotlin-extension-functions:2.0.0"/*2.0.1.BUILD-SNAPSHOT"*/,
//        "io.micronaut.kotlin:micronaut-ktor:2.0.0"/*2.0.1.BUILD-SNAPSHOT"*/,
//        "io.ktor:ktor-server-netty:+",
        "io.micronaut:micronaut-inject",
        "io.micronaut.data:micronaut-data-hibernate-jpa"
    ).forEach(::implementation)

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        platform(Deps.Platforms.MICRONAUT),
        platform(Deps.Platforms.MICRONAUT_DATA),
        project(":time-service.app"),
        "org.webjars:swagger-ui:3.30.0",
        "org.webjars:bootstrap:+",
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.3",
        "ognl:ognl:3.2.14",
        "io.swagger.core.v3:swagger-annotations",
        "io.micronaut:micronaut-validation",
        "io.micronaut:micronaut-spring",
        "io.micronaut:micronaut-inject",
        "io.micronaut:micronaut-http-server-netty",
        "io.micronaut:micronaut-http-server",
        "io.micronaut:micronaut-http-client",
        "io.micronaut.sql:micronaut-jdbc-hikari",
        "io.micronaut.sql:micronaut-hibernate-jpa",
        "io.micronaut.spring:micronaut-spring-web",
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.kotlin:micronaut-kotlin-runtime:2.0.0"/*2.0.1.BUILD-SNAPSHOT"*/,
        "io.micronaut.data:micronaut-data-spring",
        "io.micronaut.beanvalidation:micronaut-hibernate-validator", //This switches to "full" Hib Valid-or https://docs.micronaut.io/2.0.0.M2/guide/index.html#beanValidation
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "com.h2database:h2",
        "ch.qos.logback:logback-classic:1.2.3"
    ).forEach(::runtimeOnly)

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        platform(Deps.Platforms.MICRONAUT),
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.3",
        "io.micronaut:micronaut-inject-java",
        "io.micronaut.spring:micronaut-spring-web-annotation",
        "io.micronaut.spring:micronaut-spring-boot-annotation"
    ).forEach(::kaptTest)

    arrayOf(
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
        "org.spekframework.spek2:spek-runner-junit5:2.0.12",
        "org.junit.jupiter:junit-jupiter-engine"
    ).forEach(::testRuntimeOnly)
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

application {
    mainClass.set("com.example.marvel.runtime.ApplicationKt")
    mainClassName = "com.example.marvel.runtime.ApplicationKt"
    applicationDefaultJvmArgs = listOf("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
}

tasks {
    shadowJar {
        mergeServiceFiles()
    }
    (run) {
        jvmArgs("-Dmicronaut.openapi.views.spec=rapidoc.enabled=true,swagger-ui.enabled=true,swagger-ui.theme=flattop")
    }
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
