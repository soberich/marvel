import versioning.Deps

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `testing-convention-helper`
    //io.micronaut.library
}

repositories.jcenter()

dependencies {
    /*
     * ORDER MATTERS!!
     * JPAMODELGEN better go first!
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
    //annotationProcessor("io.micronaut.data:micronaut-data-processor")
    //kapt("io.micronaut.data:micronaut-data-processor")
//    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
//    implementation("io.micronaut.sql:micronaut-hibernate-jpa")
    //FIXME: this is tiny artifact, yet there is bug which prevents using `spring-tx`
    implementation("io.micronaut.spring:micronaut-spring")

    listOf(
        platform(project(":convention"))
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::kapt)
    .onEach(::compileOnly)
    .onEach(::api)
    .onEach(::runtimeOnly)
    .onEach(::testAnnotationProcessor)
    .forEach(::kaptTest)

    listOf(
        "com.blazebit:blaze-persistence-entity-view-processor",
        "com.blazebit:blaze-persistence-core-api",
        Deps.Jakarta.PERSISTENCE,
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

    api("com.blazebit:blaze-persistence-core-api")
    api("com.blazebit:blaze-persistence-integration-jackson")
    api("com.blazebit:blaze-persistence-jpa-criteria-api")
    api("com.blazebit:blaze-persistence-entity-view-api")
    api("com.blazebit:blaze-persistence-integration-hibernate-base")

    api("com.blazebit:blaze-persistence-entity-view-impl")
    api("com.blazebit:blaze-persistence-core-impl")
    api("com.blazebit:blaze-persistence-integration-hibernate-5.4")
    api("com.blazebit:blaze-persistence-jpa-criteria-impl")

    arrayOf(
        project(":shared"),
        project(":time-service.api"),
        project(":time-service.spi"),
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "org.springframework:spring-tx:+",     //TODO: Extract to `Deps.Libs`
        "org.springframework:spring-web:+",    //TODO: Extract to `Deps.Libs`
        "org.springframework:spring-context:+" //TODO: Extract to `Deps.Libs`
    ).forEach(::implementation)
}

idea.module.isDownloadSources = true
