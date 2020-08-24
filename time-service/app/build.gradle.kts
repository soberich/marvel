import versioning.Deps

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `testing-convention-helper`
    id("io.micronaut.library")
}

repositories.jcenter()

dependencies {
    /*
     * ORDER MATTERS!!
     * JPAMODELGEN better go first!
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
//    annotationProcessor("io.micronaut.data:micronaut-data-processor")
//    kapt("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.sql:micronaut-hibernate-jpa")
    implementation("io.micronaut.spring:micronaut-spring")
    listOf(
        Deps.Libs.HIBERNATE_JPAMODELGEN,
        Deps.Libs.MAPSTRUCT_AP,
        Deps.Libs.VALIDATOR_AP,
        Deps.Javax.PERSISTENCE
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

    arrayOf(
        "io.micronaut:micronaut-inject-java"
    ).asSequence()
        .onEach(::annotationProcessor)
        .onEach(::testAnnotationProcessor)
        .onEach(::kapt)
        .onEach(::kaptTest)


    arrayOf(
        "io.micronaut:micronaut-inject"
    ).forEach(::implementation)

        listOf(
            /*even though Deps.Libs may contain strict version this enforces proper platform recommendations*/
            platform(Deps.Platforms.MICRONAUT)
        ).asSequence()
        .onEach(::annotationProcessor)
        .onEach(::testAnnotationProcessor)
        .onEach(::kapt)
        .onEach(::kaptTest)
//        .onEach(::compileOnly)
        .onEach(::implementation)
        .onEach(::runtimeOnly)
        .onEach(::testImplementation)
        .forEach(::testRuntimeOnly) // we have : fixup deps coming from plugins as well for `testRuntimeOnly`

        arrayOf(
            "io.micronaut:micronaut-inject-java"
        ).asSequence()
        .onEach(::annotationProcessor)
        .onEach(::testAnnotationProcessor)
        .onEach(::kapt)
        .onEach(::kaptTest)

        arrayOf(
            "io.micronaut:micronaut-inject"
        ).forEach(::implementation)
}

idea.module.isDownloadSources = true
