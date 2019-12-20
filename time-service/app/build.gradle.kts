import versioning.Deps

plugins {
    application
    `kotlin-convention-helper`
    `jackson-convention-helper`
    com.webcohesion.enunciate
    `dependencies-reporting-helper`
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

repositories.jcenter()

val developmentOnly  by configurations.creating
/**
 * ORDER MATTERS!!
 * JPAMODELGEN Should go first!
 * TODO: Remove Quarkus form convention default configuration to not to leak here.
 */
dependencies {
    enunciate("dk.jyskebank.tooling.enunciate:enunciate-openapi:1.1.+")
    annotationProcessor("io.micronaut.data:micronaut-data-processor:1.0.0.BUILD-SNAPSHOT")
    arrayOf(
        enforcedPlatform("io.micronaut:micronaut-bom:1.3.0.M2"),
        "io.micronaut.spring:micronaut-spring-annotation",
        "io.micronaut.spring:micronaut-spring-boot-annotation",
        "io.micronaut.spring:micronaut-spring-web-annotation",
        "io.micronaut.data:micronaut-data-processor",
        "io.micronaut:micronaut-graal",
        "io.micronaut:micronaut-inject-java",
//        "io.micronaut:micronaut-validation",
        Deps.Libs.HIBERNATE_JPAMODELGEN,
        Deps.Libs.MAPSTRUCT_AP,
        Deps.Libs.VALIDATOR_AP
    ).forEach(::kapt)

    arrayOf(
        "com.oracle.substratevm:svm",
        Deps.Jakarta.PERSISTENCE,
        Deps.Jakarta.SERVLET,
        Deps.Libs.IMMUTABLES_BUILDER,
        Deps.Libs.IMMUTABLES_VALUE + ":annotations",
        Deps.Libs.MAPSTRUCT
    ).forEach(::compileOnly)

    developmentOnly("io.methvin:directory-watcher")
    developmentOnly("io.micronaut:micronaut-runtime-osx")
    developmentOnly("net.java.dev.jna:jna")

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        enforcedPlatform("io.micronaut:micronaut-bom:1.3.0.M2"),
        project(":time-service.api"),
        project(":time-service.spi"),
        Deps.Libs.ARROW_OPTICS,
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "io.micronaut.configuration:micronaut-hibernate-jpa",
        "io.micronaut.configuration:micronaut-jdbc-hikari",
        "io.micronaut.data:micronaut-data-hibernate-jpa",
        "io.micronaut.data:micronaut-data-spring",
        "io.micronaut.kotlin:micronaut-kotlin-runtime:+",
        "io.micronaut:micronaut-http-client",
        "io.micronaut:micronaut-http-server",
        "io.micronaut:micronaut-http-server-netty",
        "io.micronaut:micronaut-runtime",
        "io.micronaut:micronaut-spring",
        "io.micronaut:micronaut-validation", //io.micronaut.configuration:micronaut-hibernate-validator
        "org.springframework.boot:spring-boot-starter-web:+",
        "org.springframework:spring-orm",
        "org.springframework:spring-tx:5.2.2.RELEASE"
    ).forEach(::implementation)

    arrayOf(
        enforcedPlatform("io.micronaut:micronaut-bom:1.3.0.M2"),
        "io.micronaut:micronaut-inject-java",
        "io.micronaut.spring:micronaut-spring-boot-annotation",
        "io.micronaut.spring:micronaut-spring-web-annotation",
        "io.micronaut:micronaut-graal"
    ).forEach(::kaptTest)

    arrayOf(
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-web",
        "io.micronaut:micronaut-graal",
        "ognl:ognl:3.1.12",
        "com.h2database:h2"
    ).forEach(::runtimeOnly)

    arrayOf(
        kotlin("test-junit5"),
        "io.micronaut.test:micronaut-test-junit5",
        "io.rest-assured:rest-assured:+"
    ).forEach(::testImplementation)
}

tasks {
    test {
        useJUnitPlatform()
        exclude("**/Native*")
    }
    (run) {
        jvmArgs("-noverify", "-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
    }
//    shadowJar {
//
//        mainClassName = "com.example.marvel.Application"
//    }
}
