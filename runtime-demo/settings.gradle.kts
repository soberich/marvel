// this file prevents gradle to search for it

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("http://dl.bintray.com/kotlin/kotlin-eap")
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "io.quarkus") {
                useModule("io.quarkus:quarkus-gradle-plugin:${requested.version}")
            }
            if (requested.id.id == "kotlinx-serialization") {
                useModule("org.jetbrains.kotlin:kotlin-serialization:${requested.version}")
            }
        }
    }
}

include(
        ":business",
        ":convention",
//        ":runtime-blocking-sql-jdbc",
        ":runtime-blocking-sql-jpa",
//        ":runtime-nosql",
        ":runtime-jakarta"

)

//includeFlat(
//        ":business",
//        ":runtime-blocking-sql-jpa"
//)
