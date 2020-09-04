import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.asSequence

plugins {
    idea
    scala
    application
}

repositories.jcenter()

dependencies {
    arrayOf(
        "org.scala-lang:scala-library:2.12.10",
        "org.scala-lang:scala-compiler:2.12.10",
        "io.gatling.highcharts:gatling-charts-highcharts:3.4.0-M1"
    ).forEach(::implementation)
}

scala {
    zincVersion.set("1.3.5")
}

tasks {
    withType<ScalaCompile>().configureEach {
        options.apply {
            isFork = true
            forkOptions.jvmArgs = listOf("--enable-preview", "--illegal-access=warn")
            //FIXME: Stopped working, no idea why.
            //release.set(JavaVersion.current().coerceAtMost(JavaVersion.VERSION_12).toString().toInt())
            /*
             * For IDEA based build (Ant) this `targetCompatibility` shas to be set to the desired value,
             * otherwise will be inherited from project regardless `release` set
             */
            //targetCompatibility = JavaVersion.current().coerceAtMost(JavaVersion.VERSION_12).toString() //Not affecting compilation. For IDEA integration only.  TODO: Remove
            Files.lines(Paths.get("$rootDir", "buildSrc", "javacArgs")).asSequence().filterNot(String::isNullOrBlank).forEach(compilerArgs::plusAssign)
        }
        scalaCompileOptions.apply {
            isForce = true
            forkOptions.apply {
                jvmArgs = Files.readAllLines(Paths.get("$projectDir", "jvmArgs"))
            }
        }
    }

    (run) {
        file("$buildDir/reports/gatling").mkdirs()
        args = listOf(
            "-s", "org.openapitools.client.api.DefaultApiSimulation",
            "-rf", "$buildDir/reports/gatling"
        )
    }
}

application {
    mainClass.set("io.gatling.app.Gatling")
    mainClassName = mainClass.get()
    applicationDefaultJvmArgs = Files.readAllLines(Paths.get("$projectDir", "jvmArgs"))
}

java.modularity.inferModulePath.set(false)
