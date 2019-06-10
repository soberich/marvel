import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    kronshtadt
//    dependencies
//    id("org.galaxx.gradle.jandex") version "1.0.2"
    `project-report`
}

version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":business", "default"))
//    implementation(project(":convention", "default"))


    arrayOf(
            Deps.Javax.PERSISTENCE,
            "io.quarkus:quarkus-jdbc-h2:0.16.1",
            "io.quarkus:quarkus-hibernate-orm-panache:0.16.1"
    ).forEach(::implementation)

//    implementation(kotlin("stdlib-jdk8"))
//    testCompile("junit", "junit", "4.12")
}

java.sourceCompatibility = VERSION_1_8

