import versioning.Deps

plugins {
    `java-library`
    kronstadt
}
repositories.jcenter {
    content {
        includeGroup("io.projectreactor")
        includeGroupByRegex("(jakarta|sun|org\\.glassfish).+")
    }
}

dependencies {
    //    api(project(":business", "default"))
    kapt("org.immutables:value:2.8.0") // for annotation processor
    compileOnly("org.immutables:value:2.8.0:annotations") // annotation-only artifact
    compileOnly("org.immutables:builder:2.8.0") // there are only annotations anyway

    kapt(Deps.Libs.ARROW_META)
    kapt(Deps.Libs.VALIDATOR_AP)

    compileOnly(Deps.Libs.ARROW_ANNOTATIONS)

    arrayOf(
        Deps.Jakarta.VALIDATION,
        Deps.Libs.REACTIVE_STREAMS,
        Deps.Libs.ARROW_OPTICS
    ).forEach(::api)
}
