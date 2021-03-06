import org.gradle.api.plugins.jvm.internal.DefaultJvmPluginExtension
import org.gradle.api.plugins.jvm.internal.JvmPluginExtension
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import versioning.Deps

plugins {
    java
    jacoco
    id("kotlin-convention-helper")
}

dependencies {

    testImplementation(platform(Deps.Platforms.JUNIT5))
    testRuntimeOnly(platform(Deps.Platforms.JUNIT5))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))

    testCompileOnly("junit:junit")
    /**
     * @sample [https://github.com/spotbugs/spotbugs-gradle-plugin/blob/master/src/test/java/com/github/spotbugs/snom/SpotBugsPluginTest.java]
     */
    testImplementation("com.tngtech.archunit:archunit-junit5:0.14.1")
//    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testImplementation("io.mockk:mockk:1.10.0")
    testImplementation("org.apache.logging.log4j:log4j-to-slf4j:2.13.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.slf4j:jul-to-slf4j:1.7.30")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.12")
    testImplementation("io.rest-assured:kotlin-extensions:[4.3,5)")

    testRuntimeOnly(Deps.Libs.JBOSS_LOG)
    testRuntimeOnly(Deps.Libs.SLF4J_JBOSS)
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.12")
}

//val jvm = extensions.create<JvmPluginExtension>("jvm", DefaultJvmPluginExtension::class)
//
//// Share the test report data to be aggregated for the whole project
//jvm.createOutgoingElements("binaryTestRuntimeElements") {
//    providesAttributes {
//        documentation("test-report-data")
//    }
//    tasks.withType<Test>().configureEach {
//        artifact(binaryResultsDirectory)
//    }
//}
//// Share the test report data to be aggregated for the whole project
//jvm.createOutgoingElements("coverageDataElements") {
//    providesAttributes {
//        documentation("jacoco-coverage-data")
//    }
//    tasks.withType<Test>().configureEach {
//        extensions.findByType<JacocoTaskExtension>()?.destinationFile?.run(::artifact)
//    }
//}

tasks.withType<Test>().configureEach {
    //@formatter:off
    useJUnitPlatform()
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
    setForkEvery(100)
    reports {
        html    .isEnabled = true
        junitXml.isEnabled = true
        junitXml.mergeReruns.set(true)
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
