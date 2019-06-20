import versioning.Deps

plugins {
    java
}

dependencies {
    annotationProcessor(Deps.Javax.JAXB) {
        because("MetaModel and some other components require this to be on annotationProcessor classpath")
    }
    annotationProcessor(Deps.Javax.JAXB_RUNTIME) {
        because("BeanValidation annotations on QueryBeans source classes require this to be on annotationProcessor classpath")
    }
    annotationProcessor(Deps.Jakarta.ANNOTATION) {
        because("Annotation Processors require this to be on annotationProcessor classpath")
    }

    annotationProcessor(Deps.Libs.VALIDATOR_AP)

    compileOnly(Deps.Jakarta.JAXB) {
        because("Java 9+")
    }
    implementation(Deps.Jakarta.JAXB_RUNTIME) {
        because("Java 9+")
    }
    compileOnly(Deps.Javax.ANNOTATION) {
        because("Java 9+")
    }
}
