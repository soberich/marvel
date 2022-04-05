import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.createAptConfigurationIfNeeded

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `maven-publish`
}

//FIXME: This project supposed to be multiplatform

repositories.mavenCentral()

dependencies {
    kotlin.sourceSets.configureEach {
        dependencies {

            //FIXME: Kotlin plugin doen't have this new api
            val compileOnlyApiName = project.sourceSets[name].compileOnlyApiConfigurationName
            val compileOnlyApi     = configurations.findByName(compileOnlyApiName)
            val kapt               = createAptConfigurationIfNeeded(project, name).name

            kapt                  (platform(project(":convention")))
            compileOnly           (platform(project(":convention")))
            compileOnlyApi?.invoke(platform(project(":convention")))
            api                   (platform(project(":convention")))
            runtimeOnly           (platform(project(":convention")))

            kapt                  ("org.immutables:value")
            kapt                  (libs.validator.ap)

            //compileOnlyApi?.invoke(libs.arrow-annotations) //FIXME
            compileOnlyApi?.invoke("org.immutables:builder")
            compileOnlyApi?.invoke("org.immutables:value:annotations")

            api                   (libs.validation)
            //api                   (libs.arrow.optics) //FIXME
            api                   (libs.reactive.streams)
            api                   (libs.validator)
        }
    }
}

publishing {
    repositories.maven("https://maven.pkg.github.com/soberich/marvel")
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
        }
    }
}
