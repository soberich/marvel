import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.createAptConfigurationIfNeeded

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `maven-publish`
    //org.hibernate.orm
    alias(libs.plugins.idea.ext)
}

repositories.mavenCentral()

dependencies {
    kotlin.sourceSets.configureEach {
        dependencies {

            //FIXME: Kotlin plugin doen't have this new api
            val kapt = createAptConfigurationIfNeeded(project, name).name
            val compileOnlyApiName = project.sourceSets[name].compileOnlyApiConfigurationName
            val compileOnlyApi     = configurations.findByName(compileOnlyApiName)

            kapt          (platform(project(":convention")))
            compileOnly   (platform(project(":convention")))
            api           (platform(project(":convention")))
            runtimeOnly   (platform(project(":convention")))

            kapt          ("com.blazebit:blaze-persistence-entity-view-processor")
            kapt          ("com.blazebit:blaze-persistence-core-api")
            kapt          (libs.persistence)
            kapt          (libs.hibernate.jpamodelgen)
            kapt          (libs.mapstruct.ap)
            kapt          (libs.validator.ap)

            compileOnlyApi?.invoke(libs.inject)
            compileOnlyApi?.invoke(libs.persistence)
            compileOnly   (libs.mapstruct)

            api           ("com.blazebit:blaze-persistence-core-api") //105 Kb
            api           ("com.blazebit:blaze-persistence-entity-view-api") // 136 Kb

            implementation(project(":shared"))
            implementation(project(":time-service.api"))
            implementation(project(":time-service.spi"))
            implementation("com.kumuluz.ee.rest:kumuluzee-rest-core:1.5.1")
            implementation("org.springframework:spring-tx:[5.3,6)")
            implementation("org.springframework:spring-web:[5.3,6)")
            implementation("org.springframework:spring-context:[5.3,6)")
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
