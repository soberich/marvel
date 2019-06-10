
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import versioning.Deps


class SecurityPlugin : Plugin<Project> {

    override fun apply(project: Project) = project.run {
        dependencies {
            "implementation"(Deps.Libs.JJWT)
//            "implementation"("org.apache.shiro:shiro-core:${project.extra["shiroVersion"]}")
//            "implementation"("org.apache.shiro:shiro-jaxrs:${project.extra["shiroVersion"]}") { isTransitive = false }
//            "implementation"("org.apache.shiro:shiro-servlet-plugin:${project.extra["shiroVersion"]}")
//            "implementation"("org.apache.shiro:shiro-ehcache:${project.extra["shiroVersion"]}")
            //string interpolation in shiro
//            "runtimeOnly"("org.apache.commons:commons-configuration2:${extra["apacheConfigurationVersion"]}")
        }
    }
}

val PluginDependenciesSpec.`security`: PluginDependencySpec
    get() = id("security")
