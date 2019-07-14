import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronstadt
    `project-report`
}

version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = VERSION_1_8

dependencies {
    api(project(":api", "default"))
    api(project(":spi", "default"))
    implementation(project(":runtime-blocking-sql-jpa", "default"))

    arrayOf(
            Deps.Jakarta.JAX_RS,
            Deps.Libs.RX2,
            Deps.Libs.SLF4J_API,
            "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT"
    ).forEach(::api)
}

tasks.test {
    useJUnitPlatform()
}
