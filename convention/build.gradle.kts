import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronstadt
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
}

java.sourceCompatibility = VERSION_1_8

dependencies {
    api(Deps.Libs.HIBERNATE)

    "implementation"(enforcedPlatform(Deps.Platforms.QUARKUS))
    "implementation"(platform(Deps.Platforms.RESTEASY))

    arrayOf(
//            "io.quarkus:quarkus-smallrye-openapi", //FIXME: see README.md (EDIT: Generated models are wrong/empty)
            "io.quarkus:quarkus-hibernate-orm-panache",
            "io.quarkus:quarkus-kotlin",
            "io.quarkus:quarkus-narayana-jta",
            "io.quarkus:quarkus-reactive-pg-client",
            "io.quarkus:quarkus-resteasy",
            "io.quarkus:quarkus-resteasy-jsonb",
            "io.quarkus:quarkus-smallrye-context-propagation",
            "io.quarkus:quarkus-vertx",
            "io.smallrye.reactive:smallrye-reactive-converter-rxjava2:1.0.5",
            "io.smallrye:smallrye-context-propagation-propagators-rxjava2",
            "io.vertx:vertx-lang-kotlin-coroutines:4.0.0-SNAPSHOT",
            "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT",
            "org.jboss.logmanager:jboss-logmanager-embedded",
            "org.jboss.resteasy:resteasy-rxjava2",
            "org.wildfly.common:wildfly-common"
    ).forEach { "implementation"(it) }

    /**
     * FIXME: Should be `runtimeOnly` but Quarkus breaks to work with Gradle with finer grained dependency configurations
     */
    arrayOf(
            Deps.Libs.SLF4J_JBOSS,
            "io.quarkus:quarkus-jdbc-h2",
            "io.quarkus:quarkus-smallrye-context-propagation",
            "io.smallrye:smallrye-context-propagation-jta",
            //FIXME: Does not work.
            "org.webjars:bootstrap",
            "org.webjars:swagger-ui"
    ).forEach { "implementation"(it) }

//    arrayOf(
//            "io.quarkus:quarkus-junit5",
//            "io.rest-assured:rest-assured"
////            kotlin("test"),
////            kotlin("test-junit")
////            "io.kotlintest:kotlintest-runner-junit5:3.3.2"
//    ).forEach { "quarkusTestImplementation"(it) }

//    "quarkusTestRuntimeOnly"("io.quarkus:quarkus-jdbc-h2")
}
