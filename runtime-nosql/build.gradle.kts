import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronshtadt
}

version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    kapt("org.litote.kmongo:kmongo-annotation-processor:3.10.2-SNAPSHOT")
    implementation(project(":business", "default"))


    arrayOf(
            Deps.Jakarta.VALIDATION,
            Deps.Jakarta.CDI,
            "org.litote.kmongo:kmongo:3.10.2-SNAPSHOT",
            "org.litote.kmongo:kmongo-coroutine:3.10.2-SNAPSHOT"
    ).forEach(::implementation)

//    implementation(kotlin("stdlib-jdk8"))
//    testCompile("junit", "junit", "4.12")
}

java.sourceCompatibility = VERSION_1_8

