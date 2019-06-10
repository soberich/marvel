
import com.bmuschko.gradle.docker.DockerExtension
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
import nu.studer.gradle.credentials.domain.CredentialsContainer
import org.gradle.api.PathValidation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy.EXCLUDE
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.*
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec


class DockerPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {

        apply(plugin = "com.bmuschko.docker-remote-api")
        apply(plugin = "nu.studer.credentials")

        configure<DockerExtension> {
            registryCredentials {
                val credentials: CredentialsContainer by project
                url      = "registry.gitlab.com"
                username = credentials.propertyMissing("GITLAB_ACC")?.toString()
                password = credentials.propertyMissing("GITLAB_SECRET")?.toString()
                email    = "user@example.com"
            }
        }

        tasks {
            val copyDockerFiles by registering(Copy::class) {
                description = "Copy Dockerfile and required data to build directory"
                group       = "docker"
                duplicatesStrategy = EXCLUDE
                from(file("src/main/docker", PathValidation.DIRECTORY))
                from(file("${project.buildDir}/libs", PathValidation.DIRECTORY))
                include("*")
                exclude("**/*.yml")
                into(file("${project.buildDir}/docker", PathValidation.DIRECTORY))
            }

            val imageNameGitLab = "registry.gitlab.com/codenomads/timesheet-es-backend/${project.name.splitToSequence(' ', '-', '.', '_').joinToString(separator = "") { it.substring(0, 1).toLowerCase() }}"
            val tag         = "latest"

            register<DockerBuildImage>("buildDocker") {
                description = "Package application as Docker image"
                group       = "docker"
                dependsOn  += copyDockerFiles
                inputDir    = project.file("${project.buildDir}/docker")
                tags        = setOf("$imageNameGitLab:$tag", "$imageNameGitLab:${project.version.toString().substringBefore("-SNAPSHOT")}")
            }

            register<DockerPushImage>("pushDocker") {
                description = "Push application as Docker image"
                group       = "docker"
                imageName   = imageNameGitLab
                this.tag    = tag
            }
        }
    }
}

val PluginDependenciesSpec.`docker`: PluginDependencySpec
    get() = id("docker")
