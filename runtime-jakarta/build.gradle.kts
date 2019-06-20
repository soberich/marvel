import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.JavaVersion.current
import versioning.Deps

plugins {
    kronstadt
//    dependencies
    id("io.quarkus") version versioning.Platforms.Versions.QUARKUS
    `project-report`
}

java {
    val main by sourceSets
    main.output.setResourcesDir("$buildDir/classes/java/main")
}

quarkus {
    setSourceDir("$projectDir/src/main/kotlin")
    resourcesDir() += file("$projectDir/src/main/resources")
    setOutputDirectory("$buildDir/classes/kotlin/main")
}

version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(project(":business", "default"))
    runtimeOnly(project(":runtime-blocking-sql-jpa", "default"))

    implementation(enforcedPlatform(Deps.Platforms.QUARKUS))
    implementation(platform(Deps.Platforms.RESTEASY))

    arrayOf(
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
            "org.jboss.resteasy:resteasy-rxjava2:${Deps.Versions.RESTEASY}",
            "org.wildfly.common:wildfly-common"
    ).forEach(::implementation)

    arrayOf(
            Deps.Libs.SLF4J_JBOSS,
            "io.quarkus:quarkus-jdbc-h2",
            "io.quarkus:quarkus-smallrye-context-propagation",
            "io.smallrye:smallrye-context-propagation-jta"
          //FIXME: Does not work.
          //   "org.webjars:bootstrap:4.3.1",
          //   "org.webjars:swagger-ui:3.22.2",
          //   "org.webjars:webjars-locator-core:0.37",
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
java.targetCompatibility = current()

tasks.test {
    useJUnitPlatform()
}
