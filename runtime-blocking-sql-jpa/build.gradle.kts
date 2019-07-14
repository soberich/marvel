import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    `java-library`
    kronstadt
}

version = "0.0.1-SNAPSHOT"

repositories.jcenter()

java.sourceCompatibility = VERSION_1_8

/**
 * ORDER MATTERS!!
 * JPAMODELGEN Should go first!
 */
dependencies {
    kapt("org.hibernate:hibernate-jpamodelgen:5.4.3.Final")

    api(project(":business", "default"))
    api(project(":spi", "default"))

    arrayOf(
            Deps.Javax.CDI,
            Deps.Javax.JSONB,
            Deps.Javax.PERSISTENCE,
            Deps.Libs.HIBERNATE,
            "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3"
    ).forEach(::api)

    implementation(project(":convention", "default"))
}
