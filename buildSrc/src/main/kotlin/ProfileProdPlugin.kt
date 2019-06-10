
import com.gorylenko.GenerateGitPropertiesTask
import com.gorylenko.GitPropertiesPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.WarPluginConvention
import org.gradle.kotlin.dsl.*
import org.gradle.language.jvm.tasks.ProcessResources
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import org.springframework.boot.gradle.tasks.run.BootRun


class ProfileProdPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {

        apply(plugin = "org.springframework.boot")
        apply(plugin = "com.gorylenko.gradle-git-properties")
        apply(plugin = "com.moowork.node")

        dependencies {
            "testImplementation"(group = "com.h2database", name = "h2")
        }

        var profiles = "prod"
        if (project.hasProperty("no-liquibase")) {
            profiles += ",no-liquibase"
        }

        if (project.hasProperty("swagger")) {
            profiles += ",swagger"
        }

        configure<WarPluginConvention> {
            webAppDirName = "build/www/"
        }

        configure<GitPropertiesPluginExtension> {
            keys += arrayOf("git.branch", "git.commit.id.abbrev", "git.commit.id.describe")
        }

        tasks {
            withType<BootRun>().configureEach {
                args = mutableListOf<String>()
            }
            withType<ProcessResources>().configureEach {
                filesMatching("**/application.yml") {
                    filter {
                        it.replace("#project.version#", version.toString())
                    }
                }
                filesMatching("**/bootstrap.yml") {
                    filter {
                        it.replace("#spring.profiles.active#", profiles)
                    }
                }
            }

            withType<GenerateGitPropertiesTask>().configureEach {
                onlyIf { ! source.isEmpty }
            }
        }
    }
}

val PluginDependenciesSpec.`profile-prod`: PluginDependencySpec
    get() = id("profile-prod")
