import org.gradle.api.tasks.SourceSet.MAIN_SOURCE_SET_NAME
import org.gradle.internal.os.OperatingSystem
import versioning.Deps
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.jar.Attributes.Name.CLASS_PATH
import kotlin.collections.set

val liquibase: Configuration by configurations.creating

dependencies {
    liquibase(Deps.Libs.LIQUIBASE_HIB5)
    /*
     * TODO: This is just a quick fix to enable logging with [org.liquibase.gradle.OutputEnablingLiquibaseRunner] while making changelog. Consider use the plugin
     *              Consider use the plugin
     * FIXME: BUT with [org.liquibase.gradle.OutputEnablingLiquibaseRunner] there is this constant error...
     *  switching back to [liquibase.integration.commandline.Main]
     *  @since 17/10/2018
     */
    liquibase(Deps.Libs.LIQUIBASE_GRADLE)
}

val pathingLiquibaseJar by tasks.registering(Jar::class)

tasks {
    val initPaths by registering {
        group      = "liquibase"
        dependsOn += pathingLiquibaseJar
        if (OperatingSystem.current().isWindows) {
            pathingLiquibaseJar {
                group = "liquibase"
                dependsOn(liquibase)
                archiveAppendix.set("pathingLiquibase")
                doFirst {
                    manifest.attributes[CLASS_PATH.toString()] = project
                        .convention.getPlugin<JavaPluginConvention>()
                        .sourceSets[MAIN_SOURCE_SET_NAME].runtimeClasspath.plus(liquibase)
                        .joinToString(separator = " ") { it.toURI().toURL().toString().replaceFirst("file:/", "/") }
                }
            }
        }
    }

    fun liquibaseCommand(command: String) {
        javaexec {
            if (OperatingSystem.current().isWindows) {
                classpath = files(pathingLiquibaseJar.get().archiveFile)
            } else {
                classpath = convention.getPlugin<JavaPluginConvention>().sourceSets[MAIN_SOURCE_SET_NAME].runtimeClasspath
                classpath += liquibase
            }
            main = "org.liquibase.gradle.OutputEnablingLiquibaseRunner"

            args(
                "--changeLogFile=$projectDir/src/main/resources/config/liquibase/changelog/${ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}_changelog.xml",
                "--referenceUrl=hibernate:spring:com.example.marvel?dialect=org.hibernate.dialect.H2Dialect&hibernate.physical_naming_strategy=com.example.marvel.convention.jpa.naming.PhysicalNamingStrategyImpl&hibernate.implicit_naming_strategy=com.example.marvel.convention.jpa.naming.ImplicitNamingStrategyImpl",
                "--username=example",
                "--password=",
                "--url=jdbc:h2:retry:${project.buildDir}/h2db/db/example",
                "--driver=org.h2.Driver",
                command
            )
        }
    }

    register("liquibaseDiffChangeLog") {
        group = "liquibase"
        dependsOn += initPaths
        doLast {
            liquibaseCommand("diffChangeLog")
        }
    }
    register("liquibaseClearChecksum") {
        group = "liquibase"
        dependsOn += initPaths
        doLast {
            liquibaseCommand("clearChecksums")
        }
    }
}
