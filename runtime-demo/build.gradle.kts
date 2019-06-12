
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.JavaVersion.current
import versioning.Deps
import java.nio.charset.StandardCharsets.UTF_8

check(current().isJava8Compatible) { "At least Java 8 is required, current JVM is ${current()}" }

plugins {
    kronstadt
    jpa
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

val quarkusVersion = "999-SNAPSHOT"

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

    implementation(enforcedPlatform("io.quarkus:quarkus-bom:${quarkusVersion}"))

    implementation(project(":business", "default"))
//    implementation(project(":runtime-blocking-sql-jdbc", "default"))
//    implementation(project(":runtime-nosql", "default"))
    implementation(project(":runtime-jakarta", "default"))

    arrayOf(
            Deps.Libs.ARROW_SYNTAX,
            Deps.Libs.ARROW_EFFECTS_REACTOR_DATA,
            Deps.Libs.ARROW_EFFECTS_REACTOR_EXTENSIONS,
            Deps.Libs.ARROW_EFFECTS_IO_EXTENSIONS,
            Deps.Libs.ARROW_OPTICS,
            "io.quarkus:quarkus-resteasy:${quarkusVersion}",
            "io.quarkus:quarkus-resteasy:${quarkusVersion}",
            "io.quarkus:quarkus-vertx:${quarkusVersion}",
            "org.litote.kmongo:kmongo:3.10.2-SNAPSHOT",
            "org.litote.kmongo:kmongo-coroutine:3.10.2-SNAPSHOT",
            "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT",
            "io.vertx:vertx-lang-kotlin-coroutines:4.0.0-SNAPSHOT",
            kotlin("stdlib-jdk8")
    ).forEach(::implementation)
//<dependency>
//            <groupId>io.smallrye</groupId>
//            <artifactId>smallrye-config</artifactId>
//            <scope>runtime</scope>
//        </dependency>
    arrayOf(
            "org.jboss.resteasy:resteasy-json-binding-provider:4.0.0.Final",
            "org.eclipse.microprofile.config:microprofile-config-api:1.3",
            "io.smallrye:smallrye-config:1.3.5"
    ).forEach(::runtimeOnly)
    //<dependency>
    //    <groupId>org.jboss.resteasy</groupId>
    //    <artifactId>resteasy-json-binding-provider</artifactId>
    //    <version>4.0.0.Final</version>
    //</dependency>
    arrayOf(
            "io.quarkus:quarkus-junit5:${quarkusVersion}",
            "io.rest-assured:rest-assured:3.3.0",
            Deps.Libs.COROUTINES_TEST,
            kotlin("test"),
            kotlin("test-junit")
    ).forEach(::testImplementation)
    testCompile("org.assertj:assertj-core:3.11.1")
}

tasks.test {
    useJUnitPlatform()
}


//import org.gradle.api.JavaVersion.*
//import java.nio.charset.Charset
//import java.nio.charset.StandardCharsets.UTF_8
//
//check(current().isJava8Compatible) { "At least Java 8 is required, current JVM is ${current()}" }
//
//plugins {
//	base
////    id("com.moowork.node")              version "1.2.0"
//    // ./gradlew dependencyUpdates
//    id("com.github.ben-manes.versions") version "0.20.0"
//}
//

