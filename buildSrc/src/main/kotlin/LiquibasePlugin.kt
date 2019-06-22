
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet.MAIN_SOURCE_SET_NAME
import org.gradle.api.tasks.bundling.Jar
import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getPlugin
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import versioning.Deps
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class LiquibasePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        val liquibase by configurations.creating

        dependencies {
            liquibase(Deps.Libs.LIQUIBASE_HIB5)
            /**
             * TODO: This is just a quick fix to enable logging with [org.liquibase.gradle.OutputEnablingLiquibaseRunner] while making changelog. Consider use the plugin
             *              Consider use the plugin
             * FIXME: BUT with [org.liquibase.gradle.OutputEnablingLiquibaseRunner] there is this constant error...
             *  switching back to [iquibase.integration.commandline.Main]
             *  @since 17/10/2018
             */
            liquibase(Deps.Libs.LIQUIBASE_GRADLE)
        }

        val pathingLiquibaseJar by tasks.registering(Jar::class)

        fun liquibaseCommand(command: String) {
            javaexec {
                if (OperatingSystem.current().isWindows) {
                    classpath = files(pathingLiquibaseJar.get().archivePath)
                } else {
                    classpath = convention.getPlugin(JavaPluginConvention::class).sourceSets[MAIN_SOURCE_SET_NAME].runtimeClasspath
                    classpath += liquibase
                }
                main = "org.liquibase.gradle.OutputEnablingLiquibaseRunner"

                args(
                    "--changeLogFile=$projectDir/src/main/resources/config/liquibase/changelog/${ZonedDateTime.now(ZoneId.of("Z")).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))}_changelog.xml",
                    "--referenceUrl=hibernate:spring:com.example.marvel?dialect=org.hibernate.dialect.H2Dialect&hibernate.physical_naming_strategy=com.example.marvel.PhysicalNamingStrategyImpl&hibernate.implicit_naming_strategy=com.example.marvel.ImplicitNamingStrategyImpl",
                    "--username=example",
                    "--password=",
                    "--url=jdbc:h2:file:${project.buildDir}/h2db/db/example",
                    "--driver=org.h2.Driver",
                    command
                )
            }
        }

        tasks {
            val initPaths by registering {
                group      = "liquibase"
                dependsOn += pathingLiquibaseJar
                if (OperatingSystem.current().isWindows) {
                    pathingLiquibaseJar {
                        group = "liquibase"
                        dependsOn(liquibase)
                        appendix = "pathingLiquibase"
                        doFirst {
                            manifest.attributes["Class-Path"] = project
                                .convention.getPlugin(JavaPluginConvention::class)
                                .sourceSets[MAIN_SOURCE_SET_NAME].runtimeClasspath.plus(liquibase)
                                .joinToString(separator = " ") { it.toURI().toURL().toString().replaceFirst("file:/", "/") }
                        }
                    }
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
    }
}

val PluginDependenciesSpec.`liquibase`: PluginDependencySpec
    get() = id("liquibase")
