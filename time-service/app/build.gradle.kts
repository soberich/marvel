import org.hibernate.orm.tooling.gradle.EnhanceExtension
import versioning.Deps

plugins {
    idea
    `java-library`
    `kotlin-convention-helper`
    `testing-convention-helper`
    `maven-publish`
    //org.hibernate.orm
}

repositories.jcenter()

dependencies {
    listOf(
        platform(project(":convention"))
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::kapt)
    .onEach(::compileOnly)
    .onEach(::api)
    .onEach(::runtimeOnly)
    .onEach(::testAnnotationProcessor)
    .forEach(::kaptTest)

    /*
     * ORDER MATTERS!!
     * JPAMODELGEN better go first!
     * For IDEA based build (Ant) this has to be in `annotationProcessor`
     */
    listOf(
        "com.blazebit:blaze-persistence-entity-view-processor",
        "com.blazebit:blaze-persistence-core-api",
        Deps.Jakarta.PERSISTENCE,
        Deps.Libs.HIBERNATE_JPAMODELGEN,
        Deps.Libs.MAPSTRUCT_AP,
        Deps.Libs.VALIDATOR_AP
    ).asSequence()
    .onEach(::annotationProcessor)
    .onEach(::testAnnotationProcessor)
    .onEach(::kapt)
    .forEach(::kaptTest)

    arrayOf(
        Deps.Jakarta.INJECT,
        Deps.Jakarta.PERSISTENCE,
        Deps.Libs.MAPSTRUCT
    ).forEach(::compileOnly)

    api("com.blazebit:blaze-persistence-core-api") //105 Kb
    api("com.blazebit:blaze-persistence-entity-view-api") // 136 Kb

    arrayOf(
        project(":shared"),
        project(":time-service.api"),
        project(":time-service.spi"),
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "org.springframework:spring-tx:+",     //TODO: Extract to `Deps.Libs`
        "org.springframework:spring-web:+",    //TODO: Extract to `Deps.Libs`
        "org.springframework:spring-context:+" //TODO: Extract to `Deps.Libs`
    ).forEach(::implementation)
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
