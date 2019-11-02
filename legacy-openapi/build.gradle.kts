import io.swagger.v3.plugins.gradle.tasks.ResolveTask.Format.YAML
import org.gradle.api.PathValidation.FILE

plugins {
    `documentation-convention-helper`
    `jackson-convention-helper`
}

tasks.resolve {
    description                = "At build-time (on demand) task. Creates an openapi.yaml definition file in `app`"
    group                      = "documentation"
    classpath                  = project.sourceSets.main.get().runtimeClasspath
    modelConverterClasses      = linkedSetOf("com.example.marvel.openapi.OpenApiConfig")
    objectMapperProcessorClass = "com.example.marvel.openapi.OpenApiConfig"
    openApiFile                = file("$rootDir/time-service/app/src/main/resources/META-INF/resources/api.yaml", FILE)
    outputFormat               = YAML
    outputPath                 =  "$rootDir/app/src/main/resources/META-INF/resources"
    prettyPrint                = true
    //Don't need it in jaxrs as Quarkus don't need Application class
//    readerClass                = "com.example.marvel.openapi.ApplicationPathReader"
    resourcePackages           = setOf("com.example.marvel.domain.employee", "com.example.marvel.api")
}

dependencies {
    implementation(project(":time-service:app"))
}
