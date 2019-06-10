
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.sonarqube.gradle.SonarQubeExtension


class SonarPlugin : Plugin<Project> {

    override fun apply(project: Project) = project.run {

        apply(plugin = "jacoco")
        apply(plugin = "org.sonarqube")

        configure<JacocoPluginExtension> {
            toolVersion = "0.8.1"
        }

        tasks.withType<JacocoReport>().configureEach {
            reports {
                xml.isEnabled = true
            }
        }

        configure<SonarQubeExtension> {
            properties {
                property("sonar.host.url", "http://localhost:9001")
                property("sonar.exclusions", "src/main/webapp/content/**/*.*,src/main/webapp/i18n/*.js, build/www/**/*.*")

                property("sonar.issue.ignore.multicriteria", "S3437,S4502,S4684,UndocumentedApi,BoldAndItalicTagsCheck")

                // Rule https://sonarcloud.io/coding_rules?open=Web%3ABoldAndItalicTagsCheck&rule_key=Web%3ABoldAndItalicTagsCheck is ignored. Even if we agree that using the "i" tag is an awful practice, this is what is recommended by http://fontawesome.io/examples/
                property("sonar.issue.ignore.multicriteria.BoldAndItalicTagsCheck.resourceKey", ">src/main/webapp/app/**/*.*")
                property("sonar.issue.ignore.multicriteria.BoldAndItalicTagsCheck.ruleKey", "Web:BoldAndItalicTagsCheck")

                // Rule https://sonarcloud.io/coding_rules?open=squid%3AS3437&rule_key=squid%3AS3437 is ignored, as a JPA-managed field cannot be transient
                property("sonar.issue.ignore.multicriteria.S3437.resourceKey", "src/main/java/**/*")
                property("sonar.issue.ignore.multicriteria.S3437.ruleKey", "squid:S3437")

                // Rule https://sonarcloud.io/coding_rules?open=squid%3AUndocumentedApi&rule_key=squid%3AUndocumentedApi is ignored, as we want to follow "clean code" guidelines and classes, methods and arguments names should be self-explanatory
                property("sonar.issue.ignore.multicriteria.UndocumentedApi.resourceKey", "src/main/java/**/*")
                property("sonar.issue.ignore.multicriteria.UndocumentedApi.ruleKey", "squid:UndocumentedApi")
                // Rule https://sonarcloud.io/coding_rules?open=squid%3AS4502&rule_key=squid%3AS4502 is ignored, as for JWT tokens we are not subject to CSRF attack
                property("sonar.issue.ignore.multicriteria.S4502.resourceKey", "src/main/java/**/*")
                property("sonar.issue.ignore.multicriteria.S4502.ruleKey", "squid:S4502")

                // Rule https://sonarcloud.io/coding_rules?open=squid%3AS4684&rule_key=squid%3AS4684
                property("sonar.issue.ignore.multicriteria.S4684.resourceKey", "src/main/java/**/*")
                property("sonar.issue.ignore.multicriteria.S4684.ruleKey", "squid:S4684")

                property("sonar.jacoco.reportPaths", "${project.buildDir}/jacoco/test.exec")
                property("sonar.java.codeCoveragePlugin", "jacoco")
                property("sonar.typescript.lcov.reportPaths", "${project.buildDir}/test-results/lcov.info")
                property("sonar.junit.reportsPath", "${project.buildDir}/test-results/test/")
                property("sonar.sources", "${project.projectDir}/src/main/")
                property("sonar.tests", "${project.projectDir}/src/test/")
            }
        }
    }
}

val PluginDependenciesSpec.`sonar`: PluginDependencySpec
    get() = id("sonar")
