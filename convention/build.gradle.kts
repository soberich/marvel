
import io.quarkus.gradle.QuarkusPluginExtension
import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronstadt
    id("io.quarkus")
}

version = "0.0.1-SNAPSHOT"

repositories.jcenter()

java {
    registerFeature("quarkus") {
        usingSourceSet(sourceSets.main.get())
    }
    registerFeature("quarkusTest") {
        usingSourceSet(sourceSets.test.get())
    }
    registerFeature("spring") {
        usingSourceSet(sourceSets.main.get())
    }
}
repositories {
    gradlePluginPortal()
    jcenter()
    maven("http://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://repo.spring.io/milestone")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://jitpack.io")
}
java.sourceCompatibility = VERSION_1_8

configure<QuarkusPluginExtension> {
    setSourceDir("$projectDir/src/main/kotlin")
//        resourcesDir() += file("$projectDir/src/main/resources")
    setOutputDirectory("$buildDir/classes/kotlin/main")
}

/**
 * TODO: Remove Quarkus form convention default configuration to not to leak it there.
 */
dependencies {
    api(Deps.Libs.HIBERNATE)

//    "implementation"(enforcedPlatform(Deps.Platforms.QUARKUS))
//    "implementation"(platform(Deps.Platforms.RESTEASY))
//
//    arrayOf(
////            "io.quarkus:quarkus-smallrye-openapi", //FIXME: see README.md (EDIT: Generated models are wrong/empty)
//            "io.quarkus:quarkus-hibernate-orm-panache",
//            "io.quarkus:quarkus-kotlin",
//            "io.quarkus:quarkus-narayana-jta",
//            "io.quarkus:quarkus-reactive-pg-client",
//            "io.quarkus:quarkus-resteasy",
//            "io.quarkus:quarkus-resteasy-jsonb",
//            "io.quarkus:quarkus-smallrye-context-propagation",
//            "io.quarkus:quarkus-vertx",
//            "io.smallrye.reactive:smallrye-reactive-converter-rxjava2:1.0.5",
//            "io.smallrye:smallrye-context-propagation-propagators-rxjava2",
//            "io.vertx:vertx-lang-kotlin-coroutines:4.0.0-SNAPSHOT",
//            "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT",
//            "org.jboss.logmanager:jboss-logmanager-embedded",
//            "org.jboss.resteasy:resteasy-rxjava2",
//            "org.wildfly.common:wildfly-common"
//    ).forEach { "implementation"(it) }
//
//    /**
//     * FIXME: Should be `runtimeOnly` but Quarkus breaks to work with Gradle with finer grained dependency configurations
//     */
//    arrayOf(
//            "io.quarkus:quarkus-jdbc-h2",
//            "io.quarkus:quarkus-smallrye-context-propagation",
//            "io.smallrye:smallrye-context-propagation-jta",
//            //FIXME: Does not work.
//            "org.webjars:bootstrap",
//            "org.webjars:swagger-ui"
//    ).forEach { "implementation"(it) }

//    implementation(platform(BOM_COORDINATES))
    implementation(Deps.Libs.JACKSON_AFTERBURNER)
    implementation(Deps.Libs.JACKSON_JDK8)
    implementation(Deps.Libs.JACKSON_JSR310)
    implementation(Deps.Libs.JACKSON_PARAMETER)
//    "springImplementation"(platform(BOM_COORDINATES))
//
//    arrayOf(
//            Deps.Libs.RESTEASY_BOOT,
//            "org.springframework.boot:spring-boot-starter-actuator:${Versions.SPRING_BOOT}",
//            "org.springframework.boot:spring-boot-starter-aop:${Versions.SPRING_BOOT}",
//            "org.springframework.boot:spring-boot-starter-logging:${Versions.SPRING_BOOT}",
//            "org.springframework.boot:spring-boot-starter-mail:${Versions.SPRING_BOOT}",
////            "org.springframework.boot:spring-boot-starter-security:${Versions.SPRING_BOOT}",
//            "org.springframework.boot:spring-boot-starter-thymeleaf:${Versions.SPRING_BOOT}",
//            "org.springframework.boot:spring-boot-starter-web:${Versions.SPRING_BOOT}"
//    ).forEach { "springImplementation"(it) }
//    implementation("org.springframework:spring-tx")


//    arrayOf(
//            "io.quarkus:quarkus-junit5",
//            "io.rest-assured:rest-assured"
////            kotlin("test"),
////            kotlin("test-junit")
////            "io.kotlintest:kotlintest-runner-junit5:3.3.2"
//    ).forEach { "quarkusTestImplementation"(it) }

//    "quarkusTestRuntimeOnly"("io.quarkus:quarkus-jdbc-h2")
}
