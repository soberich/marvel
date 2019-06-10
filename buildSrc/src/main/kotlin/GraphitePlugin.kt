
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec


class GraphitePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        dependencies {
            "implementation"(group = "io.dropwizard.metrics", name = "metrics-graphite") //comes ONLY from Spring
        }
    }
}

val PluginDependenciesSpec.`graphite`: PluginDependencySpec
    get() = id("graphite")
