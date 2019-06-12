
import com.moowork.gradle.node.yarn.YarnTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.WarPluginConvention
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import org.springframework.boot.gradle.tasks.run.BootRun


class ProfileDevPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {

        apply(plugin = "org.springframework.boot")
        apply(plugin = "com.moowork.node")

        dependencies {
            "implementation"(group = "org.springframework.boot", name = "spring-boot-devtools") //comes ONLY from Spring
            "implementation"(group = "com.h2database", name = "h2") //comes ONLY from Spring TODO("really?")
        }

        var profiles = "dev"
        if (project.hasProperty("no-liquibase")) {
            profiles += ",no-liquibase"
        }

        configure<WarPluginConvention> {
            webAppDirName = "src/main/webapp/"
        }

        tasks {
            withType<BootRun>().configureEach {
                args = mutableListOf<String>()
            }

            register<YarnTask>("webpackBuildDev") {
                group = "node"
                args += arrayOf("run", "webpack:build")
            }


            withType<ProcessResources>().configureEach {
                filesMatching("**/application.y*ml") {
                    filter {
                        it.replace("#project.version#", version.toString())
                    }
                    //new
                    filter {
                        it.replace("#spring.profiles.active#", profiles)
                    }
                    //new
                }
            }
        }
    }
}

val PluginDependenciesSpec.`profile-dev`: PluginDependencySpec
    get() = id("profile-dev")
