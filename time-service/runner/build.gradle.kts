import versioning.Deps

plugins {
    idea
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

    /*
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
    listOf(
        platform(Deps.Platforms.MICRONAUT),
        platform(Deps.Platforms.MICRONAUT_DATA)
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::testAnnotationProcessor)
    .onEach(::kapt)
    .onEach(::kaptTest)
    .onEach(::compileOnly)
    .onEach { developmentOnly(it) }
    .onEach(::implementation)
    .onEach(::runtimeOnly)
    .onEach(::testImplementation)
    .forEach(::testRuntimeOnly) // we have to fixup deps coming from plugins as well for `testRuntimeOnly`

    /*
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
    listOf(
        project(":shared-convention"),
        project(":time-service.api"),
        project(":time-service.app"),
        "io.micronaut.configuration:micronaut-openapi",
        "io.micronaut.data:micronaut-data-processor",
        "io.micronaut.spring:micronaut-spring-annotation",
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-boot-annotation",
        "io.micronaut.spring:micronaut-spring-web-annotation",
        "io.micronaut:micronaut-graal",
        "io.micronaut:micronaut-inject-java"                             , /*TODO:drop version for 2.x*/
        "io.micronaut:micronaut-management",
        "io.micronaut:micronaut-validation"                              , /*TODO:drop version for 2.x*/
        "org.springframework:spring-core",                               /*TODO: Extract to `Deps.Libs`*/
        Deps.Libs.VALIDATOR_AP
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::testAnnotationProcessor)
    .onEach(::kapt)
    .forEach(::kaptTest)

    implementation("org.springframework.boot:spring-boot-starter")/*TODO: uncomment for 2.x*/ /*Didn't work for /routes*/

    arrayOf(
        "org.graalvm.nativeimage:svm",
        Deps.Jakarta.CDI,
        Deps.Jakarta.SERVLET,
        Deps.Libs.SLF4J_API
    ).forEach(::compileOnly)

    arrayOf(
        "net.java.dev.jna:jna",
        "io.micronaut:micronaut-runtime-osx",
        "io.methvin:directory-watcher"
    ).forEach { developmentOnly(it) }

    arrayOf(
        project(":shared-convention"),
        project(":time-service.api"),
        project(":time-service.app"),
        "com.fasterxml.jackson.module:jackson-module-kotlin",
        "io.micronaut.micrometer:micronaut-micrometer-core",
        "io.micronaut.data:micronaut-data-hibernate-jpa",
        "io.micronaut.kotlin:micronaut-kotlin-extension-functions", /*FIXME: 2.0.1.BUILD-SNAPSHOT"*/
        "io.micronaut:micronaut-inject",
        "io.micronaut:micronaut-management",
        "io.micronaut:micronaut-runtime",
        "io.micronaut:micronaut-validation"                       , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.swagger.core.v3:swagger-annotations",
        "org.springframework.boot:spring-boot-starter-web",
        "org.springframework:spring-context",                              /*TODO: Extract to `Deps.Libs`*/
        "org.springframework:spring-core"                               /*TODO: Extract to `Deps.Libs`*/
        //"io.ktor:ktor-server-netty:+"                                  , /*TODO:Not sure*/
        //"io.micronaut.kotlin:micronaut-ktor:2.0.0"/*2.0.1.BUILD-SNAPSHOT"*/ /*TODO: uncomment for 2.x */
    ).forEach(::implementation)

    arrayOf(
        project(":time-service.app"),
        "ch.qos.logback:logback-classic",
        "com.h2database:h2",
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "io.micronaut.beanvalidation:micronaut-hibernate-validator", /*TODO: Replace `io.micronaut.configuration` one for 2.x*//* This switches to "full" Hib Valid-or https://docs.micronaut.io/2.0.0.M2/guide/index.html#beanValidation */
        "io.micronaut.cache:micronaut-cache-caffeine",
        "io.micronaut.data:micronaut-data-spring",
        "io.micronaut.kotlin:micronaut-kotlin-runtime"             , /*2.0.1.BUILD-SNAPSHOT"*/
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-web",
        "io.micronaut.sql:micronaut-hibernate-jpa"                 , /*TODO: Replace `io.micronaut.configuration` one for 2.x*/
        "io.micronaut.sql:micronaut-hibernate-jpa-spring"          , /*FIXME: `io.micronaut.configuration` is a pre-2.x!*/
        "io.micronaut.sql:micronaut-jdbc-hikari"                   , /*TODO: Replace `io.micronaut.configuration` one for 2.x*/
        "io.micronaut:micronaut-http-client",
        "io.micronaut:micronaut-http-server"                       , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.micronaut:micronaut-http-server-netty"                 , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.micronaut:micronaut-inject"                            , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.micronaut:micronaut-spring"                            , /*TODO:drop version for 2.x*/
        "io.micronaut:micronaut-validation"                        , /*TODO:Specify 1.3.7 for 2.x with Spring*/
        "io.swagger.core.v3:swagger-annotations",
        "ognl:ognl:3.2.14",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.3",
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.webjars:bootstrap:+",
        "org.webjars:swagger-ui:+"
    ).forEach(::runtimeOnly)

    arrayOf(
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.3"
    ).asSequence()
    .onEach(::testAnnotationProcessor)
    .forEach(::kaptTest)

    arrayOf(
        "io.micronaut.test:micronaut-test-kotlintest",
        "io.micronaut.test:micronaut-test-junit5"
    ).forEach(::testImplementation)
}

//rootProject.idea {
//    project {
//        this as ExtensionAware
//        configure<ProjectSettings> {
//            doNotDetectFrameworks("spring")
//        }
//    }
//}

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
    //apply(plugin = "io.micronaut.application")
    dependencies {
        listOf(
            /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
            platform(Deps.Platforms.MICRONAUT)
        ).asSequence()
        .onEach(::annotationProcessor)
        .onEach(::testAnnotationProcessor)
        .onEach(::kapt)
        .onEach(::kaptTest)
        .onEach(::compileOnly)
        .onEach(::implementation)
        .onEach(::runtimeOnly)
        .onEach(::testImplementation)
        .forEach(::testRuntimeOnly) // we have to fixup deps coming from plugins as well for `testRuntimeOnly`

        arrayOf(
            "io.micronaut:micronaut-inject-java"
        ).asSequence()
        .onEach(::annotationProcessor)
        .onEach(::testAnnotationProcessor)
        .onEach(::kapt)
        .onEach(::kaptTest)

        arrayOf(
            "io.micronaut:micronaut-inject"
        ).forEach(::implementation)
    }
}
