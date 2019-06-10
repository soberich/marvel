
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.invoke
import versioning.Deps

plugins {
    java
}

tasks.test {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
    maxParallelForks                = if (Runtime.getRuntime().availableProcessors() / 2 < 1) 1 else Runtime.getRuntime().availableProcessors() / 2
    setForkEvery(100)
    reports.html.isEnabled          = true
    reports.junitXml.isEnabled      = true
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    systemProperty("log4j2.debug", "")
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events("passed", "skipped", "failed")
    }
}

dependencies {
//    testRuntimeOnly(versioning.Deps.Libs.SLF4J_JBOSS)
    testRuntimeOnly(Deps.Libs.JBOSS_LOG)
}