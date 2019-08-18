import io.quarkus.gradle.QuarkusPluginExtension
import versioning.Deps
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("io.quarkus:quarkus-gradle-plugin:0.21.1")
    }
}
val main = ((this as ExtensionAware).extensions.findByName("sourceSets") as? SourceSetContainer?)?.findByName("main")
if (extensions.findByType<JavaPluginExtension>() != null) {
    configure<JavaPluginExtension> {
        main?.output?.setResourcesDir("$buildDir/classes/java/main")
    }
}
plugins {
    kronstadt
    id("io.quarkus")
}
configure<QuarkusPluginExtension> {
    setSourceDir("$projectDir/src/main/kotlin")
//        resourcesDir() += file("$projectDir/src/main/resources")
    setOutputDirectory("$buildDir/classes/kotlin/main")
}
dependencies {
    implementation(enforcedPlatform(Deps.Platforms.QUARKUS))
    implementation(platform(Deps.Platforms.RESTEASY))
    implementation(platform(Deps.Platforms.JACKSON))

    compile(project(":runtime-jaxrs", "default")) //FIXME: WHY `api` configuration is available here!??
    runtimeOnly(project(":runtime-blocking-sql-jpa", "default"))

    arrayOf(
        Deps.Libs.HIBERNATE,
        Deps.Libs.RESTEASY_BOOT,
        "io.quarkus:quarkus-rest-client",
        "io.quarkus:quarkus-resteasy-jackson",
        "io.quarkus:quarkus-smallrye-context-propagation",
        "io.quarkus:quarkus-spring-web",
        "io.quarkus:quarkus-vertx",
        "io.smallrye.reactive:smallrye-reactive-converter-rxjava2:1.0.7",
        "io.smallrye:smallrye-context-propagation-propagators-rxjava2",
        "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT",
        "org.jboss.logmanager:jboss-logmanager-embedded",
        "org.jboss.resteasy:resteasy-rxjava2",
        "org.wildfly.common:wildfly-common"
    ).forEach(::implementation)

    arrayOf(
        "io.quarkus:quarkus-jdbc-h2",
        "io.quarkus:quarkus-smallrye-context-propagation",
        "io.smallrye:smallrye-context-propagation-jta"
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
