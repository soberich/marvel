import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
//    java
    kronstadt
//    dependencies
    `jdk9plus-convention-helper`
    id("io.quarkus") version "999-SNAPSHOT" //Deps.Versions.QUARKUS FIXME: Doesn't work because of Deps are in Java ?
    `project-report`
//    id("org.galaxx.gradle.jandex") version "1.0.2"
}

quarkus {
    setSourceDir("$projectDir/src/main/kotlin")
    resourcesDir() += file("$projectDir/src/main/resources")
    setOutputDirectory("$buildDir/classes/kotlin/main")
}

version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":business", "default"))
    implementation(project(":runtime-blocking-sql-jpa", "default"))

    implementation(enforcedPlatform(Deps.Platforms.QUARKUS))
    implementation(platform(Deps.Platforms.RESTEASY))

    arrayOf(
            Deps.Libs.ARROW_EFFECTS_IO_EXTENSIONS,
            Deps.Libs.ARROW_EFFECTS_REACTOR_DATA,
            Deps.Libs.ARROW_EFFECTS_REACTOR_EXTENSIONS,
            Deps.Libs.ARROW_OPTICS,
            Deps.Libs.ARROW_SYNTAX,
            Deps.Libs.COROUTINES_RXJAVA2,
            Deps.Libs.SLF4J_JBOSS,
//            "io.quarkus:quarkus-smallrye-openapi", FIXME: see README.md
            "io.quarkus:quarkus-hibernate-orm-panache",
            "io.quarkus:quarkus-kotlin",
            "io.quarkus:quarkus-narayana-jta",
            "io.quarkus:quarkus-reactive-pg-client",
            "io.quarkus:quarkus-resteasy-jsonb",
            "io.quarkus:quarkus-resteasy",
            "io.quarkus:quarkus-smallrye-context-propagation",
            "io.quarkus:quarkus-vertx",
            "io.smallrye.reactive:smallrye-reactive-converter-rxjava2:1.0.5",
            "io.smallrye:smallrye-context-propagation-propagators-rxjava2",
            "io.vertx:vertx-lang-kotlin-coroutines:4.0.0-SNAPSHOT",
            "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT",
            "org.jboss.logmanager:jboss-logmanager-embedded",
            "org.jboss.resteasy:resteasy-rxjava2",
            "org.wildfly.common:wildfly-common"
    ).forEach(::implementation)

    arrayOf(
            "io.quarkus:quarkus-jdbc-h2",
            "io.quarkus:quarkus-smallrye-context-propagation",
            "io.smallrye:smallrye-context-propagation-jta",
            "org.jboss.resteasy:resteasy-context-propagation"
    ).forEach(::runtimeOnly)

    arrayOf(
            "io.quarkus:quarkus-junit5",
            "io.rest-assured:rest-assured"
//            , "org.jetbrains.kotlin:kotlin-test",
//            "org.jetbrains.kotlin:kotlin-test-junit",
//            "io.kotlintest:kotlintest-runner-junit5:3.3.2"
    ).forEach(::testImplementation)

    testRuntimeOnly("io.quarkus:quarkus-jdbc-h2")
}

java.sourceCompatibility = VERSION_1_8

tasks.test {
    useJUnitPlatform()
}
