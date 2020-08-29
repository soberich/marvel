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

    /*
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
    listOf(
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
