import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.kotlin.dsl.invoke
import versioning.Deps

plugins {
    java
}

tasks.test {
    //@formatter:off
    useJUnitPlatform()
    maxParallelForks = if (Runtime.getRuntime().availableProcessors() / 2 < 1) 1 else Runtime.getRuntime().availableProcessors() / 2
    setForkEvery(100)
    reports {
        html    .isEnabled = true
        junitXml.isEnabled = true
    }
    systemProperties(
        "java.util.logging.manager" to "org.jboss.logmanager.LogManager",
        "log4j2.debug" to ""
    )
    testLogging {
        events              = setOf(PASSED, SKIPPED, FAILED)
        exceptionFormat     = FULL
        showStandardStreams = true
    }
    //@formatter:on
}

dependencies {
    testImplementation(platform(Deps.Platforms.JUNIT5))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("org.apache.logging.log4j:log4j-to-slf4j:2.13.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.slf4j:jul-to-slf4j:1.7.30")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.12")

    testRuntimeOnly(Deps.Libs.JBOSS_LOG)
    testRuntimeOnly(Deps.Libs.SLF4J_JBOSS)
    testRuntimeOnly(platform(Deps.Platforms.JUNIT5))
    //testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine") TODO
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.12")
}
