import versioning.Deps

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `testing-convention-helper`
}

repositories.jcenter()

dependencies {
    /*
     * ORDER MATTERS!!
     * JPAMODELGEN better go first!
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
    listOf(
        Deps.Libs.HIBERNATE_JPAMODELGEN,
        Deps.Libs.MAPSTRUCT_AP,
        Deps.Libs.VALIDATOR_AP
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::testAnnotationProcessor)
    .onEach(::kapt)
    .forEach(::kaptTest)

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
