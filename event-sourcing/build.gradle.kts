//import com.google.protobuf.gradle.*
import versioning.Deps

plugins {
    `java-library`
    kronstadt
}
repositories.jcenter {
    content {
        includeGroupByRegex("(jakarta|sun|org\\.glassfish).+")
    }
}

dependencies {

    api(project(":business"))

    arrayOf(
            Deps.Jakarta.CDI,
            Deps.Libs.REACTIVE_STREAMS,
            Deps.Libs.SLF4J_API
    ).forEach(::api)

    arrayOf(
            "io.reactiverse:reactive-pg-client:+",
            "io.reactivex.rxjava2:rxjava:2.2.9",
            "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT",
            "io.vertx:vertx-rx-java2:4.0.0-SNAPSHOT",
            "io.vertx:vertx-config:4.0.0-SNAPSHOT",
            "io.github.microutils:kotlin-logging:1.6.26"
    ).forEach(::implementation)
}
