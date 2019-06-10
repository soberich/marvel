import org.gradle.api.JavaVersion.VERSION_1_8
import versioning.Deps

plugins {
//    java
    kronshtadt
//    dependencies
    `jdk9plus-convention-helper`
    id("io.quarkus") version "0.16.1"
    `project-report`
//    id("org.galaxx.gradle.jandex") version "1.0.2"
}

//./mvnw GOAL
quarkus {
    setSourceDir("$projectDir/src/main/kotlin")
    resourcesDir() += file("$projectDir/src/main/resources")
    setOutputDirectory("$buildDir/classes/kotlin/main")
}

version = "0.0.1-SNAPSHOT"

val quarkusVersion = "0.16.1"

dependencies {
    implementation(project(":business", "default"))
    implementation(project(":runtime-blocking-sql-jpa", "default"))
    implementation(enforcedPlatform("io.quarkus:quarkus-bom:${quarkusVersion}"))
    implementation("io.quarkus:quarkus-resteasy")
    arrayOf(
            Deps.Libs.ARROW_SYNTAX,
            Deps.Libs.ARROW_EFFECTS_REACTOR_DATA,
            Deps.Libs.ARROW_EFFECTS_REACTOR_EXTENSIONS,
            Deps.Libs.ARROW_EFFECTS_IO_EXTENSIONS,
            Deps.Libs.COROUTINES_RXJAVA2,
            "io.quarkus:quarkus-hibernate-orm-panache:${quarkusVersion}",
            Deps.Libs.ARROW_OPTICS,
            "io.quarkus:quarkus-resteasy:${quarkusVersion}",
            "io.quarkus:quarkus-resteasy-jsonb:${quarkusVersion}",
            "io.quarkus:quarkus-vertx:${quarkusVersion}",
            "org.litote.kmongo:kmongo:3.10.2-SNAPSHOT",
            "org.litote.kmongo:kmongo-coroutine:3.10.2-SNAPSHOT",
            "io.vertx:vertx-lang-kotlin:4.0.0-SNAPSHOT",
            "io.vertx:vertx-lang-kotlin-coroutines:4.0.0-SNAPSHOT",
            Deps.Libs.SLF4J_JBOSS,
//            "io.quarkus:quarkus-smallrye-openapi", FIXME: see README.md
            "io.quarkus:quarkus-smallrye-context-propagation",
            "io.smallrye:smallrye-context-propagation-propagators-rxjava2",
            "io.smallrye.reactive:smallrye-reactive-converter-rxjava2:1.0.5",
            "io.quarkus:quarkus-reactive-pg-client",
            "org.jboss.resteasy:resteasy-rxjava2",
"org.jboss.logmanager:jboss-logmanager-embedded:1.0.3",
"org.wildfly.common:wildfly-common:1.5.0.Final",
            "io.quarkus:quarkus-kotlin:0.16.1"
            //            "org.jboss.resteasy:resteasy-jackson2-provider:4.0.0.Final",
    ).forEach(::implementation)
    arrayOf(
//            "nl.topicus:spanner-hibernate:0.10-SNAPSHOT",
//            "nl.topicus:spanner-jdbc:1.1.5"
            "io.quarkus:quarkus-jdbc-h2"
    ).forEach(::runtimeOnly)

    runtimeOnly("io.quarkus:quarkus-smallrye-context-propagation:${quarkusVersion}")
    implementation("org.jboss.resteasy:resteasy-rxjava2:4.0.0.Final")
    runtimeOnly("io.smallrye:smallrye-context-propagation-jta:+")
    runtimeOnly("org.jboss.resteasy:resteasy-context-propagation:+")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.quarkus", "quarkus-junit5", "${quarkusVersion}")
    testImplementation("io.rest-assured", "rest-assured", "3.3.0")
//    testImplementation("org.jetbrains.kotlin:kotlin-test")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
//    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testRuntimeOnly("io.quarkus:quarkus-jdbc-h2")
    //<dependency>
    //    <groupId>io.quarkus</groupId>
    //    <artifactId>quarkus-junit5</artifactId>
    //    <version>${quarkus.version}</version>
    //    <scope>test</scope>
    //</dependency>
    //<dependency>
    //    <groupId>io.rest-assured</groupId>
    //    <artifactId>rest-assured</artifactId>
    //    <version>3.3.0</version>
    //    <scope>test</scope>
    //</dependency>
}

java.sourceCompatibility = VERSION_1_8

tasks.test {
    useJUnitPlatform()
}
