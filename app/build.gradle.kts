import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronstadt
    jackson
    id("io.quarkus") version "0.26.1"
}

repositories.jcenter()

java.sourceCompatibility = VERSION_1_8

/**
 * ORDER MATTERS!!
 * JPAMODELGEN Should go first!
 * TODO: Remove Quarkus form convention default configuration to not to leak here.
 */
dependencies {
    arrayOf(
        Deps.Libs.HIBERNATE_JPAMODELGEN,
        Deps.Libs.MAPSTRUCT_AP,
        Deps.Libs.VALIDATOR_AP
    ).forEach(::kapt)

    arrayOf(
        Deps.Libs.IMMUTABLES_BUILDER,
        Deps.Libs.IMMUTABLES_VALUE + ":annotations",
        Deps.Libs.MAPSTRUCT
    ).forEach(::compileOnly)

    arrayOf(
        /*even though Deps.Libs may contain strict version this enforces proper platform recomendations*/
        enforcedPlatform(Deps.Platforms.QUARKUS),
        platform(Deps.Platforms.RESTEASY),
        platform(Deps.Platforms.JACKSON),
        project(":api"),
        project(":spi"),
        Deps.Libs.ARROW_OPTICS,
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "io.quarkus:quarkus-hibernate-orm",
        "io.quarkus:quarkus-jdbc-h2",
        "io.quarkus:quarkus-kotlin",
        "io.quarkus:quarkus-rest-client",
        "io.quarkus:quarkus-resteasy-jackson",
        "io.quarkus:quarkus-smallrye-context-propagation",
        "io.quarkus:quarkus-spring-data-jpa",
        "io.quarkus:quarkus-spring-web",
        "io.quarkus:quarkus-vertx",
        "io.smallrye.reactive:smallrye-reactive-converter-rxjava2:1.0.10",
        "io.smallrye:smallrye-context-propagation-jta",
        "io.smallrye:smallrye-context-propagation-propagators-rxjava2",
        "io.vertx:vertx-lang-kotlin:4.0.0-milestone3",
        "org.jboss.logmanager:jboss-logmanager-embedded",
        "org.jboss.resteasy:resteasy-rxjava2",
        /*such libs should stay in colpile classpath unfortunately, polluting it, this started between 0.23.1 and 0.26.1*/
        "org.webjars:bootstrap",
        "org.webjars:swagger-ui",
        "org.wildfly.common:wildfly-common"
    ).forEach(::implementation)

    arrayOf(
        kotlin("test-junit5"),
        "io.quarkus:quarkus-junit5",
        "io.rest-assured:rest-assured"
    ).forEach(::testImplementation)
}

quarkus {
    setSourceDir("$projectDir/src/main/kotlin")
//    setSourceDir("$buildDir/generated/source/kapt/main")
//    resourcesDir() += file("$projectDir/src/main/resources")
    setOutputDirectory("$buildDir/classes/kotlin/main")
}

tasks.test {
    useJUnitPlatform()
}
