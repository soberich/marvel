import versioning.Deps

plugins {
    `java-library`
    `kotlin-convention-helper`
}

repositories.jcenter()

/**
 * ORDER MATTERS!!
 * JPAMODELGEN Should go first!
 * TODO: Remove Quarkus form convention default configuration to not to leak here.
 */
dependencies {
    arrayOf(
        Deps.Libs.HIBERNATE_JPAMODELGEN,
        Deps.Libs.MAPSTRUCT_AP,
        Deps.Libs.VALIDATOR_AP
    ).forEach(::kapt)

    arrayOf(
        Deps.Jakarta.INJECT,
        Deps.Jakarta.PERSISTENCE,
        Deps.Libs.IMMUTABLES_BUILDER,
        Deps.Libs.IMMUTABLES_VALUE + ":annotations",
        Deps.Libs.MAPSTRUCT
    ).forEach(::compileOnly)

    arrayOf(
        project(":shared-convention"),
        project(":time-service.api"),
        project(":time-service.spi"),
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "org.springframework:spring-tx:+",     //TODO: Extract to `Deps.Libs`
        "org.springframework:spring-web:+",    //TODO: Extract to `Deps.Libs`
        "org.springframework:spring-context:+" //TODO: Extract to `Deps.Libs`
    ).forEach(::implementation)
}
