import io.swagger.v3.plugins.gradle.tasks.ResolveTask
import io.swagger.v3.plugins.gradle.tasks.ResolveTask.Format.YAML
import org.gradle.api.PathValidation.FILE

plugins {
    openapi
    jackson
}

tasks.withType<ResolveTask>().configureEach {
    description                = "At build-time (on demand) task. Creates an openapi.yaml definition file in `runtime-jaxrs`"
    group                      = "documentation"
    classpath                  = project.sourceSets.main.get().runtimeClasspath
    modelConverterClasses      = linkedSetOf("com.example.marvel.openapi.OpenApiConfig")
    objectMapperProcessorClass = "com.example.marvel.openapi.OpenApiConfig"
    openApiFile                = file("$rootDir/runtime-jaxrs/src/main/resources/META-INF/resources/api.yaml", FILE)
    outputFormat               = YAML
    outputPath                 =  "$rootDir/runtime-jaxrs/src/main/resources/META-INF/resources"
    prettyPrint                = true
    //Don't need it in jaxrs as Quarkus don't need Application class
    readerClass                = "com.example.marvel.openapi.ApplicationPathReader"
    resourcePackages           = setOf("com.example.marvel.web.rest.jaxrs", "com.example.marvel.web.rest")
}

dependencies {
    implementation(project(":runtime-jaxrs"))
    implementation(project(":convention"))
}
