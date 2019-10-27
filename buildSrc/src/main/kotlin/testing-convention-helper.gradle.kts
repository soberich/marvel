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
}

dependencies {
//    testRuntimeOnly(Deps.Libs.SLF4J_JBOSS)
    testRuntimeOnly(Deps.Libs.JBOSS_LOG)
}
