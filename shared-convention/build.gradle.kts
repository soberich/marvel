import versioning.Deps

plugins {
    `java-library`
    `jackson-convention-helper`
    `kotlin-convention-helper`
}

repositories.jcenter()

dependencies {
    arrayOf(
        platform(Deps.Platforms.GUAVA),
        Deps.Libs.HIBERNATE,
        Deps.Libs.HIBERNATE_TYPES,
        Deps.Libs.RX2,
        "com.google.guava:guava",
        "io.github.classgraph:classgraph:+"
    ).forEach(::api)
}
