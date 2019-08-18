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
    kapt("org.immutables:value:2.7.4") // for annotation processor
    compileOnly("org.immutables:value:2.7.4:annotations") // annotation-only artifact
    compileOnly("org.immutables:builder:2.7.4") // there are only annotations anyway
    kapt("org.hibernate:hibernate-jpamodelgen:5.4.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.3.0.Final")

    compileOnly("org.mapstruct:mapstruct:1.3.0.Final")

    api(project(":business", "default"))
    api(project(":spi", "default"))
    implementation(project(":convention", "default"))

    implementation(platform(Deps.Platforms.BLAZE_JPA))

    arrayOf(
            Deps.Jakarta.CDI,
            Deps.Jakarta.PERSISTENCE,
            Deps.Libs.HIBERNATE,
            "com.blazebit:blaze-persistence-core-api",
            "com.blazebit:blaze-persistence-entity-view-api",
            "com.blazebit:blaze-persistence-jpa-criteria-api",
            "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3"
    ).forEach(::api)
}
