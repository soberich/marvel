//import com.google.protobuf.gradle.*
import versioning.Deps

plugins {
    `java-library`
    kronstadt
//    id("com.google.protobuf") version "0.8.8"
}
repositories.jcenter {
    content {
        includeGroup("io.arrow-kt")
        includeGroupByRegex("(jakarta|sun|org\\.glassfish).+")
    }
}

//protobuf {
//    protoc {
//        artifact = "com.google.protobuf:protoc:3.0.0"
//    }
//
//    plugins {
//        id("kroto") {
//            artifact = "com.github.marcoferrer.krotoplus:protoc-gen-kroto-plus:0.3.0:jvm8@jar"
//        }
//    }
//    generateProtoTasks {
//        val krotoConfig = file("krotoPlusConfig.json")
//        all().forEach {
//            // Adding the config file to the task inputs lets UP-TO-DATE checks
//            // include changes to configuration
//            it.inputs.files + krotoConfig
//
//            it.plugins {
//                id("kroto") {
//                    outputSubDir = "java"
//                    option("ConfigPath=$krotoConfig")
//                }
//            }
//        }
//    }
//}
dependencies {
    kapt(Deps.Libs.ARROW_META)

    compileOnly(Deps.Libs.ARROW_ANNOTATIONS)

    arrayOf(
            "javax.json.bind:javax.json.bind-api:1.0",
            Deps.Jakarta.VALIDATION,
            Deps.Libs.REACTIVE_STREAMS
    ).forEach(::api)

    arrayOf(
            Deps.Libs.ARROW_EXTRAS_DATA,
            Deps.Libs.ARROW_OPTICS,
            Deps.Libs.REACTIVE_STREAMS
    ).forEach(::implementation)




}
