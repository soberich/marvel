import java.nio.file.Files
import java.nio.file.Paths

plugins {
    application
    scala
}

repositories.jcenter()

application {
    mainClassName = "io.gatling.app.Gatling"
    applicationDefaultJvmArgs = Files.readAllLines(Paths.get("$projectDir/jvmArgs"))
}

(tasks.run) {
    file("$buildDir/reports/gatling").mkdirs()
    args = listOf(
        "-s", "com.example.marvel.gatling.AddressGatlingTest",
        "-rf", "$buildDir/reports/gatling"
    )
}

dependencies {
    arrayOf(
        "org.scala-lang:scala-library:2.13.3",
        "io.gatling.highcharts:gatling-charts-highcharts:3.4.0-M1"
    ).forEach(::implementation)
}
