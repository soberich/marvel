import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    kronshtadt
}

version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    //    implementation(kotlin("stdlib-jdk8"))
//    testCompile("junit", "junit", "4.12")
}


java.sourceCompatibility = VERSION_1_8

