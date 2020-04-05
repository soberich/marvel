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

val developmentOnly by configurations.creating
/**
 * ORDER MATTERS!!
 * JPAMODELGEN Should go first!
 * TODO: Remove Quarkus form convention default configuration to not to leak here.
 */
dependencies {
    enunciate("dk.jyskebank.tooling.enunciate:enunciate-openapi:1.1.+")
    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        enforcedPlatform(Deps.Platforms.MICRONAUT),
        "io.micronaut.configuration:micronaut-openapi",
        "io.micronaut.data:micronaut-data-processor:1.0.2.BUILD-SNAPSHOT",
        "io.micronaut.spring:micronaut-spring-annotation",
        "io.micronaut.spring:micronaut-spring-boot-annotation",
        "io.micronaut.spring:micronaut-spring-web-annotation",
        "io.micronaut:micronaut-graal",
        "io.micronaut:micronaut-inject-java",
        "io.micronaut:micronaut-validation",
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
        enforcedPlatform(Deps.Platforms.MICRONAUT),
        "io.methvin:directory-watcher",
        "io.micronaut:micronaut-runtime-osx",
        "net.java.dev.jna:jna"
    ).forEach { developmentOnly(it) }

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        enforcedPlatform(Deps.Platforms.MICRONAUT),
        project(":shared-convention"),
        project(":time-service.api"),
        "io.micronaut.data:micronaut-data-hibernate-jpa",
        "io.micronaut:micronaut-inject",
        "io.micronaut:micronaut-runtime",
        "io.micronaut:micronaut-validation",
        "io.swagger.core.v3:swagger-annotations",
        "org.springframework:spring-context" //TODO: Extract to `Deps.Libs`
    ).forEach(::implementation)

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        enforcedPlatform(Deps.Platforms.MICRONAUT),
        project(":time-service.app"),
        "ch.qos.logback:logback-classic:1.2.3",
        "com.h2database:h2",
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "io.micronaut.configuration:micronaut-hibernate-jpa",
        "io.micronaut.configuration:micronaut-hibernate-validator", //This switches to "full" Hib Valid-or https://docs.micronaut.io/2.0.0.M2/guide/index.html#beanValidation
        "io.micronaut.configuration:micronaut-jdbc-hikari",
        "io.micronaut.data:micronaut-data-spring",
//        "io.micronaut.kotlin:micronaut-kotlin-runtime:+" //FIXME: 1.1.0.BUILD-SNAPSHOT is not compatible with 2.0.0.M2
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-web",
        "io.micronaut:micronaut-http-client",
        "io.micronaut:micronaut-http-server",
        "io.micronaut:micronaut-http-server-netty",
        "io.micronaut:micronaut-inject",
        "io.micronaut:micronaut-spring",
        "io.micronaut:micronaut-validation",
        "io.swagger.core.v3:swagger-annotations",
        "ognl:ognl:3.1.12",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.1",
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.webjars:bootstrap:+",
        "org.webjars:swagger-ui:3.20.9"
    ).forEach(::runtimeOnly)

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
        enforcedPlatform(Deps.Platforms.MICRONAUT),
        "io.micronaut:micronaut-inject-java",
        "io.micronaut.spring:micronaut-spring-boot-annotation",
        "io.micronaut.spring:micronaut-spring-web-annotation",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.1",
        "org.slf4j:jul-to-slf4j:1.7.30"
    ).forEach(::kaptTest)

    arrayOf(
        kotlin("test-junit5"),
        "io.micronaut.test:micronaut-test-junit5",
        "io.micronaut.test:micronaut-test-kotlintest",
        "io.mockk:mockk:1.9.3",
        "org.junit.jupiter:junit-jupiter-api",
        "org.spekframework.spek2:spek-dsl-jvm:2.0.8",
        "io.kotlintest:kotlintest-runner-junit5:3.3.2",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.1",
        "org.slf4j:jul-to-slf4j:1.7.30"
    ).forEach(::testImplementation)

    arrayOf(
        "org.junit.jupiter:junit-jupiter-engine",
        "org.spekframework.spek2:spek-runner-junit5:2.0.8"
    ).forEach(::testRuntimeOnly)
}

application {
    mainClassName = "com.example.marvel.runtime.Application"
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
            enforcedPlatform(Deps.Platforms.MICRONAUT),
            "io.micronaut:micronaut-inject-java"
        ).forEach(::kapt)

        arrayOf(
            /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
            enforcedPlatform(Deps.Platforms.MICRONAUT),
            "io.micronaut:micronaut-inject"
        ).forEach(::implementation)
    }
}
