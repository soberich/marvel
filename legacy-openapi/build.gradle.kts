import io.swagger.v3.plugins.gradle.tasks.ResolveTask.Format.YAML

plugins {
    idea
    `documentation-convention-helper`
    `jackson-convention-helper`
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":time-service.app"))
}

tasks.resolve {
    description                = "At build-time (on demand) task. Creates an openapi.yaml definition file in `app`"
    group                      = JavaBasePlugin.DOCUMENTATION_GROUP
    classpath                  = project.sourceSets.main.get().runtimeClasspath
    modelConverterClasses      = linkedSetOf("com.example.marvel.openapi.OpenApiConfig")
    objectMapperProcessorClass = "com.example.marvel.openapi.OpenApiConfig"
    openApiFile                = file("$rootDir/time-service/app/src/main/resources/META-INF/resources/api.yaml", PathValidation.FILE)
    outputFormat               = YAML
    outputDir                 =  file("$rootDir/time-service/app/src/main/resources/META-INF/resources", PathValidation.DIRECTORY)
    prettyPrint                = true
    //Don't need it in jaxrs as Quarkus don't need Application class
//    readerClass                = "com.example.marvel.openapi.ApplicationPathReader"
    resourcePackages           = setOf("com.example.marvel.domain.employee", "com.example.marvel.api")
}
