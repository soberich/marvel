import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronstadt
    `project-report`
}

version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = VERSION_1_8

repositories.jcenter {
    content {
        includeGroup("io.reactivex.rxjava2")
        includeGroup("org.slf4j")
        includeGroup("io.vertx")
        includeGroupByRegex("(jakarta|sun|org\\.glassfish).+")
    }
}

dependencies {
    api(project(":api", "default"))
    api(project(":spi", "default"))

    arrayOf(
            Deps.Jakarta.JAX_RS,
            Deps.Jakarta.INJECT,
            Deps.Libs.RX2,
            Deps.Libs.SLF4J_API,
            "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT"
    ).forEach(::api)

    runtimeOnly("org.webjars:bootstrap:3.1.0")
    runtimeOnly("org.webjars:swagger-ui:3.20.9")
}

tasks.test {
    useJUnitPlatform()
}
