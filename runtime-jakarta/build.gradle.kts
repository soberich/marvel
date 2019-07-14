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
    compile("com.blazebit:blaze-persistence-integration-entity-view-cdi:1.3.2")
    compile("com.blazebit:blaze-persistence-core-api:1.3.2")
    compile("com.blazebit:blaze-persistence-core-impl:1.3.2")
    compile("com.blazebit:blaze-persistence-integration-hibernate-5.4:1.3.2")
    compile("com.blazebit:blaze-persistence-entity-view-api:1.3.2")
    compile("com.blazebit:blaze-persistence-entity-view-impl:1.3.2")
    compile("com.blazebit:blaze-persistence-jpa-criteria-api:1.3.2")
    compile("com.blazebit:blaze-persistence-jpa-criteria-impl:1.3.2")
    compile("com.blazebit:blaze-persistence-jpa-criteria-jpa-2-compatibility:1.3.2")
    // https://mvnrepository.com/artifact/com.blazebit/blaze-apt-utils
    compile("com.blazebit:blaze-apt-utils:0.1.16")
    annotationProcessor("com.blazebit:blaze-apt-utils:0.1.16")

}

tasks.test {
    useJUnitPlatform()
}
