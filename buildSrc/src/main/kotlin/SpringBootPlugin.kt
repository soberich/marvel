
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.existing
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
import org.gradle.language.jvm.tasks.ProcessResources
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import org.springframework.boot.gradle.dsl.SpringBootExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.buildinfo.BuildInfo
import org.springframework.boot.gradle.tasks.bundling.BootWar


class SpringBootPlugin : Plugin<Project> {



    override fun apply(project: Project) = project.run {

        apply(plugin = "org.springframework.boot")
        apply(plugin = "io.spring.dependency-management")

        configure<SpringBootExtension> {
                mainClassName = "com.example.marvel.Application"
                buildInfo()
        }

        tasks {
            withType<BootWar>().configureEach {
                mainClassName = "com.example.marvel.Application"
            }
            val cleanResources by registering(Jar::class) {
                delete("build/resources")
            }
            val bootBuildInfo by existing(BuildInfo::class)
            withType<JavaCompile>().configureEach {
                dependsOn += named(JavaPlugin.PROCESS_RESOURCES_TASK_NAME)
                dependsOn += bootBuildInfo
            }
            withType<ProcessResources>().configureEach {
                dependsOn += cleanResources
            }
            bootBuildInfo {
                mustRunAfter(cleanResources)
            }
        }

        dependencies {
            "annotationProcessor"(platform(SpringBootPlugin.BOM_COORDINATES))
            "implementation"(platform(SpringBootPlugin.BOM_COORDINATES))
        }
    }
}

val PluginDependenciesSpec.`spring-boot`: PluginDependencySpec
    get() = id("spring-boot")
