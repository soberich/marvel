
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.JavaVersion.current
import versioning.Deps
import java.nio.charset.StandardCharsets.UTF_8

check(current().isJava8Compatible) { "At least Java 8 is required, current JVM is ${current()}" }

plugins {
    kronstadt
    jdbc
    `project-report`
}

repositories {
    mavenLocal {
        content {
            includeGroup("io.quarkus")
            includeGroup("nl.topicus")
        }
    }
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://dl.bintray.com/arrow-kt/arrow-kt")
    maven("https://oss.jfrog.org/artifactory/oss-snapshot-local")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

java.sourceCompatibility = VERSION_1_8
java.targetCompatibility = current()


val quarkusVersion = "0.19.1"

subprojects {
    apply(plugin = "build-dashboard")
    apply(plugin = "help-tasks")
    apply(plugin = "project-report")

    group       = "com.example.marvel"
    version     = "0.0.1-SNAPSHOT"
    description = "${name.replace('-', ' ').toUpperCase()} of Native Quarkus/Micronaut Arrow-Kt Vert.x Coroutines GRPC Kotlin-DSL app"

    repositories {
        jcenter()
        maven("https://dl.bintray.com/arrow-kt/arrow-kt") {
            content {
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.jfrog.org/artifactory/oss-snapshot-local") {
            content {
                includeGroup("io.arrow-kt")
            }
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }

    tasks {
        withType<JavaCompile>().configureEach {
            options.apply {
                encoding      = UTF_8.name()
                isIncremental = true
                isFork        = true
                // compiler deamon JVM options
                forkOptions.jvmArgs = project.extra["org.gradle.jvmargs"].toString().split(" ")
                // javac tasks args
                file("$rootDir/javacArgs", PathValidation.FILE).forEachLine(action = compilerArgs::add)
            }
        }
    }
}

dependencies {

//    kapt(Deps.Jakarta.ANNOTATION)
    annotationProcessor(Deps.Jakarta.ANNOTATION)
    compileOnly(Deps.Jakarta.ANNOTATION)

    implementation(enforcedPlatform(Deps.Platforms.QUARKUS))
    implementation(platform(Deps.Platforms.RESTEASY))
    implementation(platform(Deps.Platforms.JACKSON))

    implementation(project(":api", "default"))
//    implementation(project(":runtime-blocking-sql-jdbc", "default"))
//    implementation(project(":runtime-nosql", "default"))
    implementation(project(":runtime-jakarta", "default"))

    arrayOf(
            Deps.Libs.ARROW_EFFECTS_IO_EXTENSIONS,
            Deps.Libs.ARROW_EFFECTS_REACTOR_DATA,
            Deps.Libs.ARROW_EFFECTS_REACTOR_EXTENSIONS,
            Deps.Libs.ARROW_OPTICS,
            Deps.Libs.ARROW_SYNTAX,
            Deps.Libs.COROUTINES_RXJAVA2,
            Deps.Libs.SLF4J_JBOSS,
//            "io.quarkus:quarkus-smallrye-openapi", FIXME: see README.md
            "io.quarkus:quarkus-hibernate-orm-panache",
            "io.quarkus:quarkus-kotlin",
            "io.quarkus:quarkus-narayana-jta",
            "io.quarkus:quarkus-reactive-pg-client",
            "io.quarkus:quarkus-resteasy-jsonb",
            "io.quarkus:quarkus-resteasy",
            "io.quarkus:quarkus-smallrye-context-propagation",
            "io.quarkus:quarkus-vertx",
            "io.smallrye.reactive:smallrye-reactive-converter-rxjava2:1.0.5",
            "io.smallrye:smallrye-context-propagation-propagators-rxjava2",
            "io.vertx:vertx-lang-kotlin-coroutines:4.0.0-SNAPSHOT",
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
            "io.quarkus:quarkus-junit5:${quarkusVersion}",
            "io.rest-assured:rest-assured",
            Deps.Libs.COROUTINES_TEST,
            kotlin("test"),
            kotlin("test-junit")
    ).forEach(::testImplementation)
    testCompile("org.assertj:assertj-core:3.11.1")
}

tasks.test {
    useJUnitPlatform()
}
