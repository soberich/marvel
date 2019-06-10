rootProject.name = "marvel"

pluginManagement {
    repositories {
        mavenLocal()
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
        ":openapi",
//        ":convention",
//        ":runtime-blocking-sql-jdbc",
        ":runtime-blocking-sql-jpa",
//        ":runtime-nosql",
        ":runtime-jakarta"

)
