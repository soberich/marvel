plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
}

dependencies {
    api(project(":time-service.api"))
}
