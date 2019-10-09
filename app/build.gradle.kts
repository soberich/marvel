
import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("io.quarkus:quarkus-gradle-plugin:0.23.2")
    }
}
//val main = ((this as ExtensionAware).extensions.findByName("sourceSets") as? SourceSetContainer?)?.findByName("main")
//if (extensions.findByType<JavaPluginExtension>() != null) {
//    configure<JavaPluginExtension> {
//        main?.output?.setResourcesDir("$buildDir/classes/java/main")
//    }
//}
plugins {
    kronstadt
    id("io.quarkus")
}

quarkus {
    setSourceDir("$projectDir/src/main/kotlin")
//    setSourceDir("$buildDir/generated/source/kapt/main")
//        resourcesDir() += file("$projectDir/src/main/resources")
    setOutputDirectory("$buildDir/classes/kotlin/main")
}

version = "0.0.1-SNAPSHOT"

repositories.jcenter()

java.sourceCompatibility = VERSION_1_8

/**
 * ORDER MATTERS!!
 * JPAMODELGEN Should go first!
 * TODO: Remove Quarkus form convention default configuration to not to leak here.
 */
dependencies {
    kapt("org.immutables:value:2.8.0-CriteriaPreview1") // for annotation processor
    compileOnly("org.immutables:value:2.8.0-CriteriaPreview1:annotations") // annotation-only artifact
    compileOnly("org.immutables:builder:2.8.0-CriteriaPreview1") // there are only annotations anyway

    kapt(Deps.Libs.ARROW_META)
    kapt(Deps.Libs.VALIDATOR_AP)

    compileOnly(Deps.Libs.ARROW_ANNOTATIONS)

    kapt("org.hibernate:hibernate-jpamodelgen:5.4.3.Final")

    kapt("org.mapstruct:mapstruct-processor:1.3.1.Final")
    compileOnly("org.mapstruct:mapstruct:1.3.1.Final")

    implementation(enforcedPlatform(Deps.Platforms.QUARKUS))
    implementation(platform(Deps.Platforms.RESTEASY))
    implementation(platform(Deps.Platforms.JACKSON))
//    implementation(project(":api"))
    implementation(project(":spi"))

    arrayOf(
        Deps.Jakarta.INJECT,
        Deps.Jakarta.SERVLET,
        Deps.Libs.ARROW_OPTICS,
        Deps.Libs.HIBERNATE,
        Deps.Libs.RESTEASY_BOOT,
        Deps.Libs.RX2,
        Deps.Libs.SLF4J_API,
        "com.kumuluz.ee.rest:kumuluzee-rest-core:1.2.3",
        "io.quarkus:quarkus-kotlin",
        "io.quarkus:quarkus-rest-client",
        "io.quarkus:quarkus-resteasy-jackson",
        "io.quarkus:quarkus-smallrye-context-propagation",
        "io.quarkus:quarkus-spring-data-jpa",
        "io.quarkus:quarkus-spring-web",
        "io.quarkus:quarkus-vertx",
        "io.smallrye.reactive:smallrye-reactive-converter-rxjava2:1.0.7",
        "io.smallrye:smallrye-context-propagation-propagators-rxjava2",
        "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT",
        "org.jboss.logmanager:jboss-logmanager-embedded",
        "org.jboss.resteasy:resteasy-rxjava2",
        "org.springframework:spring-web:5.+",
        "org.wildfly.common:wildfly-common"
    ).forEach(::implementation)

    arrayOf(
        "io.quarkus:quarkus-jdbc-h2",
        "io.quarkus:quarkus-smallrye-context-propagation",
        "io.smallrye:smallrye-context-propagation-jta",
        "org.webjars:bootstrap:3.1.0",
        "org.webjars:swagger-ui:3.20.9"
    ).forEach(::runtimeOnly)

    arrayOf(
        kotlin("test-junit5"),
        "io.quarkus:quarkus-junit5",
        "io.rest-assured:rest-assured"
    ).forEach(::testImplementation)
}

tasks.test {
    useJUnitPlatform()
}
