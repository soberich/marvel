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
 * TODO: Remove Quarkus form convention default configuration to not to leak here.
 */
dependencies {
    kapt("org.hibernate:hibernate-jpamodelgen:5.4.3.Final")

    implementation(platform(Deps.Platforms.BLAZE_JPA))

    api(project(":business", "default"))
    api(project(":spi", "default"))

    arrayOf(
            Deps.Javax.CDI,
            Deps.Javax.JSONB,
            Deps.Javax.PERSISTENCE,
            Deps.Libs.HIBERNATE,
            "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3"
    ).forEach(::api)

    compile("com.blazebit:blaze-persistence-integration-entity-view-cdi")
    compile("com.blazebit:blaze-persistence-core-api")
    compile("com.blazebit:blaze-persistence-core-impl")
    compile("com.blazebit:blaze-persistence-integration-hibernate-5.4")
    compile("com.blazebit:blaze-persistence-entity-view-api")
    compile("com.blazebit:blaze-persistence-entity-view-impl")
    compile("com.blazebit:blaze-persistence-jpa-criteria-api")
    compile("com.blazebit:blaze-persistence-jpa-criteria-impl")
    compile("com.blazebit:blaze-persistence-jpa-criteria-jpa-2-compatibility")

    implementation(project(":convention", "default"))
}
