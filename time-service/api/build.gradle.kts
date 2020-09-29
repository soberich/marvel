import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.createAptConfigurationIfNeeded
import versioning.Deps

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `maven-publish`
}

//FIXME: This project supposed to be multiplatform

repositories.jcenter()

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
            kapt                  (Deps.Libs.VALIDATOR_AP)

            //compileOnlyApi?.invoke(Deps.Libs.ARROW_ANNOTATIONS) //FIXME
            compileOnlyApi?.invoke("org.immutables:builder")
            compileOnlyApi?.invoke("org.immutables:value:annotations")

            api                   (Deps.Jakarta.VALIDATION)
            //api                   (Deps.Libs.ARROW_OPTICS) //FIXME
            api                   (Deps.Libs.REACTIVE_STREAMS)
            api                   (Deps.Libs.VALIDATOR)
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
