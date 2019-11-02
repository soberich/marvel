import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorExtension.Generator
import guru.nidi.graphviz.attribute.Color
import guru.nidi.graphviz.attribute.Style

plugins {
    com.vanniktech.dependency.graph.generator
    com.github.`ben-manes`.versions
    se.patrikerdes.`use-latest-versions`
    `project-report`
}

dependencyGraphGenerator {
    generators = listOf(
        Generator.ALL,
        Generator(
            "spring", // Suffix for our Gradle task.
            { arrayOf("org.springframework").any { tag -> it.moduleGroup.contains(tag) || it.moduleName.contains(tag) } },
            dependencyNode = { node, _ -> node.add(Style.FILLED, Color.rgb("#6CB23F")) }
        ),
        Generator(
            "javax", // Suffix for our Gradle task.
            { arrayOf("javax").any { tag -> it.moduleGroup.contains(tag) || it.moduleName.contains(tag) } },
            dependencyNode = { node, _ -> node.add(Style.FILLED, Color.rgb("#f35d45")) }
        ),
        Generator(
            "jakarta", // Suffix for our Gradle task.
            { arrayOf("jakarta").any { tag -> it.moduleGroup.contains(tag) || it.moduleName.contains(tag) } },
            dependencyNode = { node, _ -> node.add(Style.FILLED, Color.rgb("#f35d45")) }
        ),
        Generator(
            "javaxOrJakarta", // Suffix for our Gradle task.
            { arrayOf("javax", "jakarta", "glassfish", "com.sun", "jvnet").any { tag -> it.moduleGroup.contains(tag) || it.moduleName.contains(tag) } },
            dependencyNode = { node, _ -> node.add(Style.FILLED, Color.rgb("#f35d45")) }
        ),
        Generator(
            "ebean", // Suffix for our Gradle task.
            { arrayOf("ebean", "avaje").any { tag -> it.moduleGroup.contains(tag) || it.moduleName.contains(tag) } },
            dependencyNode = { node, _ -> node.add(Style.FILLED, Color.rgb("#f57a1b")) }
        ),
        Generator(
            "metrics", // Suffix for our Gradle task.
            { arrayOf("metrics", "micrometer", "actuator", "dropwizzard").any { tag -> it.moduleGroup.contains(tag) || it.moduleName.contains(tag) } }, // Only want Metrics.
            dependencyNode = { node, _ -> node.add(Style.FILLED, Color.rgb("#f57a1b")) }
        )
    )
}

tasks.dependencyUpdates {
    group = "reporting"
    resolutionStrategy {
        componentSelection {
            all {
                val rejected = false
                if (rejected) {
                    reject("We want all candidates -> none rejected")
                }
            }
        }
    }
//    outputFormatter = { result: Result ->
//        val updatable = result.outdated.dependencies
//            if (!updatable.isEmpty()) {
//                val writer = StringWriter()
//                val html = MarkupBuilder(writer)
//
//                html.html {
//                    body {
//                        table {
//                            thead {
//                                tr {
//                                    td("Group")
//                                    td("Module")
//                                    td("Current version")
//                                    td("Latest version")
//                                }
//                            }
//                            tbody {
//                                updatable.each { dependency->
//                                    tr {
//                                        td(dependency.group)
//                                        td(dependency.name)
//                                        td(dependency.version)
//                                        td(dependency.available.release ?: dependency.available.milestone)
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                file("${project.buildDir}/dependencyUpdates").mkdirs()
//                val file = file("${project.buildDir}/dependencyUpdates/report.html")
//                file.createNewFile()
//                file.write(writer.toString())
//            }
//    }
}

