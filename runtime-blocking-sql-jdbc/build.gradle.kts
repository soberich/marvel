import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronshtadt
    jpa
    dependencies
}

version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":business", "default"))


    arrayOf(
            Deps.Javax.VALIDATION,
            Deps.Libs.EBEAN,
            Deps.Libs.EBEAN_ANNOTATION,
            Deps.Libs.EBEAN_QUERY
    ).forEach(::implementation)

//    implementation(kotlin("stdlib-jdk8"))
//    testCompile("junit", "junit", "4.12")
}

java.sourceCompatibility = VERSION_1_8

