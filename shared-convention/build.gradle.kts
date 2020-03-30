import versioning.Deps

plugins {
    `java-library`
    `jackson-convention-helper`
    `kotlin-convention-helper`
}

repositories.jcenter()

dependencies {
    arrayOf(
        Deps.Libs.HIBERNATE,
        Deps.Libs.RX2
    ).forEach(::api)
}
