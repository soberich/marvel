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
    api(project(":business", "default"))
    arrayOf(
            Deps.Jakarta.VALIDATION,
            Deps.Libs.REACTIVE_STREAMS
    ).forEach(::api)
}
