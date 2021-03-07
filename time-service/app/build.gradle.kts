import org.hibernate.orm.tooling.gradle.EnhanceExtension
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.createAptConfigurationIfNeeded
import versioning.Deps

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `testing-convention-helper`
    `maven-publish`
    //org.hibernate.orm
}

repositories.mavenCentral()

dependencies {
    kotlin.sourceSets.configureEach {
        dependencies {

            //FIXME: Kotlin plugin doen't have this new api
            val kapt = createAptConfigurationIfNeeded(project, name).name

            kapt          (platform(project(":convention")))
            compileOnly   (platform(project(":convention")))
            api           (platform(project(":convention")))
            runtimeOnly   (platform(project(":convention")))

            kapt          ("com.blazebit:blaze-persistence-entity-view-processor")
            kapt          ("com.blazebit:blaze-persistence-core-api")
            kapt          (Deps.Jakarta.PERSISTENCE)
            kapt          (Deps.Libs.HIBERNATE_JPAMODELGEN)
            kapt          (Deps.Libs.MAPSTRUCT_AP)
            kapt          (Deps.Libs.VALIDATOR_AP)

            compileOnly   (Deps.Jakarta.INJECT)
            compileOnly   (Deps.Jakarta.PERSISTENCE)
            compileOnly   (Deps.Libs.MAPSTRUCT)

            api           ("com.blazebit:blaze-persistence-core-api") //105 Kb
            api           ("com.blazebit:blaze-persistence-entity-view-api") // 136 Kb

            implementation(project(":shared"))
            implementation(project(":time-service.api"))
            implementation(project(":time-service.spi"))
            implementation("com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3")
            implementation("org.springframework:spring-tx:+")     //TODO: Extract to `Deps.Libs`
            implementation("org.springframework:spring-web:+")    //TODO: Extract to `Deps.Libs`
            implementation("org.springframework:spring-context:+") //TODO: Extract to `Deps.Libs`
        }
    }
}

idea.module.isDownloadSources = true
// FIXME
//hibernate {
//    enhance(closureOf<EnhanceExtension> {
//        enableLazyInitialization    = true
//        enableDirtyTracking         = true
//        enableAssociationManagement = true
//        enableExtendedEnhancement   = false
//    })
//}
//tasks.register("enhance", org.hibernate.orm.tooling.gradle.EnhanceTask::class) {
//    setSourceSets(project.sourceSets.main.get())
//    options(closureOf<EnhanceExtension> {
//        enableLazyInitialization    = true
//        enableDirtyTracking         = true
//        enableAssociationManagement = true
//        enableExtendedEnhancement   = false
//    })
//}

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
