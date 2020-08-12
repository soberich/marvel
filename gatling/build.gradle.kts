import org.gradle.plugins.ide.idea.model.FilePath
import org.gradle.plugins.ide.idea.model.Module
import org.gradle.plugins.ide.idea.model.SingleEntryModuleLibrary
import org.jetbrains.gradle.ext.IdeaCompilerConfiguration
import org.jetbrains.gradle.ext.ModuleSettings
import java.nio.file.Files
import java.nio.file.Paths

plugins {
    application
    scala
    org.jetbrains.gradle.plugin.`idea-ext`
}

repositories.jcenter()

idea {
    targetVersion = JavaVersion.current().toString()
    module {
        this as ExtensionAware
        configure<ModuleSettings> {
            this as ExtensionAware
            configure<IdeaCompilerConfiguration> {
                additionalVmOptions = Files.readString(Paths.get("javacArgs"))
                //addNotNullAssertions = true TODO
                parallelCompilation = true
                useReleaseOption = true
                javac.preferTargetJDKCompiler = true
            }

        }
    }
    module {
        inheritOutputDirs = false
        iml {
            whenMerged { m: Module ->
                m.isInheritOutputDirs = false
                configurations
                    .implementation
                    .map { it.single { it.name.startsWith("scala-library") } }
                    .map { FilePath(it, it.toURI().toURL().toExternalForm(), it.canonicalPath, it.relativeTo(project.buildFile).path) }
                    .map { SingleEntryModuleLibrary(it, "COMPILE") }
                    .map { m.dependencies.add(it) }
            }
        }
    }
}

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
