
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec


class ZipkinPlugin : Plugin<Project> {

    override fun apply(project: Project) = project.run {
        dependencies {
            "implementation"(group = "org.springframework.cloud", name = "spring-cloud-starter-zipkin") //comes ONLY from Spring
        }
    }
}

val PluginDependenciesSpec.`zipkin`: PluginDependencySpec
    get() = id("zipkin")
