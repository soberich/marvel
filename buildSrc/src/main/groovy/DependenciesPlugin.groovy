import com.github.benmanes.gradle.versions.reporter.result.Result
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorExtension.Generator
import groovy.xml.MarkupBuilder
import guru.nidi.graphviz.attribute.Color
import guru.nidi.graphviz.attribute.Style
import guru.nidi.graphviz.model.MutableNode
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ComponentSelection
import org.gradle.api.artifacts.ComponentSelectionRules
import org.gradle.api.artifacts.ResolvedDependency

class DependenciesPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.with {
            apply(plugin: "com.vanniktech.dependency.graph.generator")
            apply(plugin: "com.github.ben-manes.versions")

            def springGenerator = new Generator(
                "spring", // Suffix for our Gradle task.
                { ResolvedDependency dependency -> dependency.getModuleGroup().startsWith("org.springframework") },
                { true }, // Include transitive dependencies.
                { MutableNode node, ResolvedDependency dependency -> node.add(Style.FILLED, Color.rgb("#6CB23F")) },
            )

            def javaxGenerator = new Generator(
                "javax", // Suffix for our Gradle task.
                { ResolvedDependency dependency -> dependency.getModuleGroup().contains("javax") },
                { true }, // Include transitive dependencies.
                { MutableNode node, ResolvedDependency dependency -> node.add(Style.FILLED, Color.rgb("#f35d45")) },
            )

            def jakartaGenerator = new Generator(
                    "jakarta", // Suffix for our Gradle task.
                    { ResolvedDependency dependency -> dependency.getModuleGroup().contains("jakarta") },
                    { true }, // Include transitive dependencies.
                    { MutableNode node, ResolvedDependency dependency -> node.add(Style.FILLED, Color.rgb("#f35d45")) },
            )

            def javaxOrJakarta = ["javax", "jakarta", "glassfish", "sun"]
            def javaxOrJakartaGenerator = new Generator(
                    "javaxOrJakarta", // Suffix for our Gradle task.
                    { ResolvedDependency dependency -> javaxOrJakarta.any {  dependency.getModuleGroup().contains(it) || dependency.getModuleName().contains(it) } },
                    { true }, // Include transitive dependencies.
                    { MutableNode node, ResolvedDependency dependency -> node.add(Style.FILLED, Color.rgb("#f35d45")) },
            )

            def ebeanGenerator = new Generator(
                "ebean", // Suffix for our Gradle task.
                { ResolvedDependency dependency -> dependency.getModuleGroup().contains("ebean") || dependency.getModuleGroup().contains("avaje") },
                { true }, // Include transitive dependencies.
                { MutableNode node, ResolvedDependency dependency -> node.add(Style.FILLED, Color.rgb("#f57a1b")) },
            )

            def metrics = ["metrics", "micrometer", "actuator", "dropwizzard"]
            def metricsGenerator = new Generator(
                "metrics", // Suffix for our Gradle task.
                { ResolvedDependency dependency -> metrics.any {  dependency.getModuleGroup().contains(it) || dependency.getModuleName().contains(it) } }, // Only want Metrics.
                { true }, // Include transitive dependencies.
                { MutableNode node, ResolvedDependency dependency -> node.add(Style.FILLED, Color.rgb("#f57a1b")) },
            )

            dependencyGraphGenerator {
                generators = [ Generator.ALL, springGenerator, javaxGenerator, jakartaGenerator, javaxOrJakartaGenerator, ebeanGenerator ]
            }

            tasks.withType(DependencyUpdatesTask).configureEach {
                it.group = "reporting"
                it.gradleReleaseChannel = "release-candidate"
                it.resolutionStrategy {
                    it.componentSelection { ComponentSelectionRules rules ->
                        rules.all { ComponentSelection selection ->
                            boolean rejected = false
                            if (rejected) {
                                selection.reject('We want all candidates -> none rejected')
                            }
                        }
                    }
                }
                it.outputFormatter = { Result result ->
                    def updatable = result.outdated.dependencies
                    if (!updatable.isEmpty()) {
                        def writer = new StringWriter()
                        def html = new MarkupBuilder(writer)

                        html.html {
                            body {
                                table {
                                    thead {
                                        tr {
                                            td("Group")
                                            td("Module")
                                            td("Current version")
                                            td("Latest version")
                                        }
                                    }
                                    tbody {
                                        updatable.each { dependency->
                                            tr {
                                                td(dependency.group)
                                                td(dependency.name)
                                                td(dependency.version)
                                                td(dependency.available.release ?: dependency.available.milestone)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        new File("${project.buildDir}/dependencyUpdates").mkdirs()
                        def file = new File("${project.buildDir}/dependencyUpdates/report.html")
                        file.createNewFile()
                        file.write(writer.toString())
                    }
                }
            }
        }
    }
}
