import versioning.Deps

plugins {
    `java-library`
    kronstadt
}
repositories.jcenter()

dependencies {
    arrayOf(
        Deps.Libs.ARROW_META,
        Deps.Libs.IMMUTABLES_VALUE,
        Deps.Libs.VALIDATOR_AP
    ).forEach(::kapt)

    arrayOf(
        Deps.Libs.ARROW_ANNOTATIONS,
        Deps.Libs.IMMUTABLES_BUILDER,
        Deps.Libs.IMMUTABLES_VALUE + ":annotations"
    ).forEach(::compileOnly)

    arrayOf(
        Deps.Jakarta.VALIDATION,
        Deps.Libs.REACTIVE_STREAMS,
        Deps.Libs.ARROW_OPTICS
    ).forEach(::api)
}
