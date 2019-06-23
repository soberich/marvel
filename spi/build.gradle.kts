//import com.google.protobuf.gradle.*
import versioning.Deps

plugins {
    `java-library`
    kronstadt
//    `arrow-meta-convention-helper`
//    id("com.google.protobuf") version "0.8.8"
}
repositories.jcenter {
    content {
        includeGroup("io.projectreactor")
        includeGroupByRegex("(jakarta|sun|org\\.glassfish).+")
    }
}
java {
    val main by sourceSets
    main.output.setResourcesDir("$buildDir/classes/java/main")
}

dependencies {
    api(project(":business", "default"))

    arrayOf(
            Deps.Jakarta.VALIDATION,
            Deps.Libs.REACTIVE_STREAMS
    ).forEach(::api)



}
