import versioning.Deps

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
}

repositories.jcenter()

dependencies {

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

    api("com.blazebit:blaze-persistence-core-api")
    api("com.blazebit:blaze-persistence-integration-jackson")
    api("com.blazebit:blaze-persistence-jpa-criteria-api")
    runtimeOnly("com.blazebit:blaze-persistence-core-impl")
    runtimeOnly("com.blazebit:blaze-persistence-integration-hibernate-5.4")
    runtimeOnly("com.blazebit:blaze-persistence-jpa-criteria-impl")

    /*
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
    listOf(
        "com.blazebit:blaze-persistence-entity-view-processor",
        "org.immutables:value",
        Deps.Libs.VALIDATOR_AP
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::testAnnotationProcessor)
    .onEach(::kapt)
    .forEach(::kaptTest)

    arrayOf(
        //Deps.Libs.ARROW_ANNOTATIONS, //FIXME
        "org.immutables:builder",
        "org.immutables:value:annotations"
    ).forEach(::compileOnly)

    arrayOf(
        Deps.Jakarta.VALIDATION,
        //Deps.Libs.ARROW_OPTICS, //FIXME
        Deps.Libs.REACTIVE_STREAMS,
        Deps.Libs.VALIDATOR
    ).forEach(::api)
}
