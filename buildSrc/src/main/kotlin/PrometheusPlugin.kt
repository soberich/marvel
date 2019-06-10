
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec


class PrometheusPlugin : Plugin<Project> {

    override fun apply(project: Project) = project.run {
        dependencies {
            "implementation"(group = "io.prometheus", name = "simpleclient")            //comes ONLY from Spring
            "implementation"(group = "io.prometheus", name = "simpleclient_servlet")    //comes ONLY from Spring
            "implementation"(group = "io.prometheus", name = "simpleclient_dropwizard") //comes ONLY from Spring
        }
    }
}

val PluginDependenciesSpec.`prometheus`: PluginDependencySpec
    get() = id("prometheus")
