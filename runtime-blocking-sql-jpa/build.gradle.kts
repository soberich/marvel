import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronstadt
//    dependencies
//    id("org.galaxx.gradle.jandex") version "1.0.2"
    `project-report`
}

version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":business", "default"))
//    implementation(project(":convention", "default"))

    implementation(enforcedPlatform(Deps.Platforms.QUARKUS))

    arrayOf(
            Deps.Javax.PERSISTENCE,
            "io.quarkus:quarkus-jdbc-h2",
            "io.quarkus:quarkus-hibernate-orm-panache"
    ).forEach(::implementation)

}

java.sourceCompatibility = VERSION_1_8

