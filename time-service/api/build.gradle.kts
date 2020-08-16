import versioning.Deps

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
}

repositories.jcenter()

dependencies {
    /*
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
    listOf(
        Deps.Libs.ARROW_META,
        Deps.Libs.IMMUTABLES_VALUE,
        Deps.Libs.VALIDATOR_AP
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::testAnnotationProcessor)
    .onEach(::kapt)
    .forEach(::kaptTest)

    arrayOf(
        Deps.Libs.ARROW_ANNOTATIONS,
        Deps.Libs.IMMUTABLES_BUILDER,
        Deps.Libs.IMMUTABLES_VALUE + ":annotations"
    ).forEach(::compileOnly)

    arrayOf(
        Deps.Javax.VALIDATION,
        Deps.Jakarta.VALIDATION,
        Deps.Libs.ARROW_OPTICS,
        Deps.Libs.REACTIVE_STREAMS,
        Deps.Libs.VALIDATOR
    ).forEach(::api)
}
