
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import com.bmuschko.gradle.docker.tasks.image.DockerPushImage
import nu.studer.gradle.credentials.domain.CredentialsContainer
import org.gradle.api.file.DuplicatesStrategy.EXCLUDE

plugins {
    com.bmuschko.`docker-remote-api`
    nu.studer.credentials
}

docker {
    registryCredentials {
        val credentials: CredentialsContainer by project
        url     .set("registry.gitlab.com")
        username.set(credentials.propertyMissing("GITLAB_ACC")?.toString())
        password.set(credentials.propertyMissing("GITLAB_SECRET")?.toString())
        email   .set("user@example.com")
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
        exclude("**/*.y*ml")
        into(file("${project.buildDir}/docker", PathValidation.DIRECTORY))
    }

    val imageNameGitLab = "registry.gitlab.com/example/marvel/${project.name.splitToSequence(' ', '-', '.', '_').joinToString(separator = "") { it.substring(0, 1).toLowerCase() }}"
    val tag             = "latest"

    register<DockerBuildImage>("buildDocker") {
        description = "Package application as Docker image"
        group       = "docker"
        dependsOn  += copyDockerFiles
        inputDir  .set(project.file("${project.buildDir}/docker"))
        tags   .addAll(setOf("$imageNameGitLab:$tag", "$imageNameGitLab:${project.version.toString().substringBefore("-SNAPSHOT")}"))
    }

    register<DockerPushImage>("pushDocker") {
        description = "Push application as Docker image"
        group       = "docker"
        imageName .set(imageNameGitLab)
        this.tag  .set(tag)
    }
}
