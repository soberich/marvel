import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
    `java-library`
    kronstadt
//    dependencies
    `project-report`

}
java {
    val main by sourceSets
    main.output.setResourcesDir("$buildDir/classes/java/main")
}
version = "0.0.1-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":business", "default"))
//    implementation(project(":convention", "default"))

    kapt("org.hibernate:hibernate-jpamodelgen:5.4.3.Final")

    arrayOf(
            "javax.json.bind:javax.json.bind-api:1.0",
            Deps.Javax.PERSISTENCE,
            Deps.Javax.CDI,
            "org.hibernate:hibernate-core:[5.3,6)"
    ).forEach(::api)

}

java.sourceCompatibility = VERSION_1_8

