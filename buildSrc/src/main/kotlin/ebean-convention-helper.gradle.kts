import versioning.Deps

plugins {
    java
    idea
    io.ebean
}

ebean.debugLevel = 2

tasks.compileJava {
    options.annotationProcessorGeneratedSourcesDirectory = file("src/generated/java")
}

dependencies {
    "kapt"(Deps.Libs.EBEAN_ANNOTATION)
    "kapt"(Deps.Libs.EBEAN_QUERY)
    "kapt"(Deps.Libs.EBEAN_QUERY_GEN)
    "kapt"(Deps.Libs.EBEAN_PERSISTENCE)

    annotationProcessor(Deps.Libs.EBEAN_PERSISTENCE)

    annotationProcessor(Deps.Javax.JAXB) {
        because("MetaModel and some other components require this to be on annotationProcessor classpath")
    }
    annotationProcessor(Deps.Javax.JAXB_RUNTIME) {
        because("BeanValidation annotations on QueryBeans source classes require this to be on annotationProcessor classpath")
    }
    annotationProcessor(Deps.Javax.ANNOTATION) {
        because("Annotation Processors require this to be on annotationProcessor classpath")
    }

    annotationProcessor(Deps.Libs.VALIDATOR_AP)

    implementation(Deps.Javax.JAXB) {
        because("Java 9+")
    }
    implementation(Deps.Javax.JAXB_RUNTIME) {
        because("Java 9+")
    }
    implementation(Deps.Javax.ANNOTATION) {
        because("Java 9+")
    }

    implementation(Deps.Libs.VALIDATOR)

    implementation(Deps.Libs.EBEAN)
    implementation(Deps.Libs.EBEAN_QUERY)
    implementation(Deps.Libs.EBEAN_ANNOTATION)

    testImplementation(Deps.Libs.EBEAN_TEST)
}
