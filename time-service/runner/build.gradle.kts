import org.jetbrains.gradle.ext.ProjectSettings
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import versioning.Deps
import org.gradle.api.plugins.ExtensionAware as EA

plugins {
    idea
    application
    `kotlin-convention-helper`
    `jackson-convention-helper`
    com.webcohesion.enunciate
    `dependencies-reporting-helper`
    org.springframework.boot
    id("com.github.johnrengelman.shadow")
    // FIXME: Micronaut Gradle plugin fails by now with Kotlin 1.4.0
//    id("io.micronaut.application")
}

repositories.jcenter()

val developmentOnly by configurations.getting
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
        platform(Deps.Platforms.MICRONAUT_DATA),
        platform(SpringBootPlugin.BOM_COORDINATES)
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::testAnnotationProcessor)
    .onEach(::kapt)
    .onEach(::kaptTest)
    .onEach(::compileOnly)
    .onEach(::developmentOnly)
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
        "io.micronaut.configuration:micronaut-openapi",
        "io.micronaut.data:micronaut-data-processor",
        "io.micronaut.spring:micronaut-spring-annotation",
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-boot-annotation",
        "io.micronaut.spring:micronaut-spring-web-annotation",
        "io.micronaut:micronaut-graal",
        "io.micronaut:micronaut-inject-java",
        "io.micronaut:micronaut-management",
        "io.micronaut:micronaut-validation",
        "org.springframework:spring-core",
        Deps.Libs.VALIDATOR_AP
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::testAnnotationProcessor)
    .onEach(::kapt)
    .forEach(::kaptTest)

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
    ).forEach(::developmentOnly)

    constraints {
        implementation("org.springframework.boot:spring-boot-starter") {
            because("The only way to make use of Micronaut 2.x")
        }
        implementation("org.springframework:spring-context:5.3.0-M2") {
            because("The only way to make use of Micronaut 2.x")
        }
        implementation("org.springframework:spring-core:5.3.0-M2") {
            because("The only way to make use of Micronaut 2.x")
        }
    }

    arrayOf(
        project(":shared-convention"),
        project(":time-service.api"),
        "com.fasterxml.jackson.module:jackson-module-kotlin",
        "io.micronaut.data:micronaut-data-hibernate-jpa",
        "io.micronaut.kotlin:micronaut-kotlin-extension-functions",
        "io.micronaut.micrometer:micronaut-micrometer-core",
        "io.micronaut:micronaut-inject",
        "io.micronaut:micronaut-management",
        "io.micronaut:micronaut-runtime",
        "io.micronaut:micronaut-validation",
        "io.swagger.core.v3:swagger-annotations",
        "org.springframework.boot:spring-boot-autoconfigure"
        //"io.ktor:ktor-server-netty
        //"io.micronaut.kotlin:micronaut-ktor:2.0.0"/*2.0.1.BUILD-
    ).forEach(::implementation)

    arrayOf(
        project(":time-service.app"),
        "ch.qos.logback:logback-classic",
        "com.h2database:h2",
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "io.micronaut.beanvalidation:micronaut-hibernate-validator",
        "io.micronaut.cache:micronaut-cache-caffeine",
        "io.micronaut.data:micronaut-data-spring",
        "io.micronaut.kotlin:micronaut-kotlin-runtime",
        "io.micronaut.spring:micronaut-spring",
        "io.micronaut.spring:micronaut-spring-boot",
        "io.micronaut.spring:micronaut-spring-web",
        "io.micronaut.sql:micronaut-hibernate-jpa",
        "io.micronaut.sql:micronaut-hibernate-jpa-spring",
        "io.micronaut.sql:micronaut-jdbc-hikari",
        "io.micronaut:micronaut-http-client",
        "io.micronaut:micronaut-http-server",
        "io.micronaut:micronaut-http-server-netty",
        "io.micronaut:micronaut-inject",
        "io.micronaut:micronaut-spring",
        "io.swagger.core.v3:swagger-annotations",
        "net.bytebuddy:byte-buddy:1.10.14",
        "ognl:ognl:3.2.14",
        "org.apache.logging.log4j:log4j-to-slf4j:2.13.3",
        "org.slf4j:jul-to-slf4j:1.7.30",
        "org.springframework.boot:spring-boot-starter-web",
        "org.springframework:spring-webmvc",
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

(rootProject.idea.project as EA).the<ProjectSettings>().doNotDetectFrameworks("spring")

idea.module.isDownloadSources = true

//// FIXME: Micronaut Gradle plugin fails by now with Kotlin 1.4.0-rc.
//micronaut {
//    version("1.3.7")
//    enableNativeImage.set(true)
//}

application {
    mainModule.convention("com.example.marvel.runtime")
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
