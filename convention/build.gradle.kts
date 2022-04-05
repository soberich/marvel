@file:Suppress("ConvertToStringTemplate")

plugins {
    `java-platform`
    alias(libs.plugins.spring.boot) apply false
    `maven-publish`
}

repositories.mavenCentral()

dependencies {
    //FIXME: Report Gradle team that without `enforced...`  1.5.0-Alpha5 is chosen over 1.5.0-SNAPSHOT (
    api(enforcedPlatform(libs.blaze.jpa))
    api(platform(libs.coroutines))
    api(platform(libs.guava))
    api(platform(libs.immutables))
    api(platform(libs.jackson))
    api(platform(libs.ktor))
    api(platform(libs.micronaut))
    api(platform(libs.quarkus))
    api(platform(libs.quarkus.blaze))
    api(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))

    constraints {
        api(libs.bundles.ee)
    }
}

javaPlatform.allowDependencies()

publishing {
    repositories {
        mavenLocal()
        maven("https://maven.pkg.github.com/soberich/marvel").credentials{
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key")  as String? ?: System.getenv("TOKEN")
        }
//        maven("https://api.bintray.com/content/soberich/maven/com.github.soberich.marvel:$name/$version;publish=1;override=1;").credentials {
//            username = System.getenv("BINTRAY_USER")
//            password = System.getenv("BINTRAY_API_KEY")
//        }
    }
    publications {
        create<MavenPublication>("marvelPlatform") {
            groupId = "com.github.soberich.marvel"
            from(components["javaPlatform"])
            if ("SNAPSHOT" in version.toString())
                 withoutBuildIdentifier()
            else withBuildIdentifier()
            versionMapping {
                usage("java-api") {
                    fromResolutionResult()
                }
            }
            pom {
                withXml {
                    asNode().apply {
                        appendNode("repositories").appendNode("repository").apply {
                            appendNode("id", "spring-milestone")
                            appendNode("url", "https://repo.spring.io/milestone")
                        }
                    }
                }
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("soberich")
                        email.set("25544967+soberich@users.noreply.github.com")
                    }
                }
                scm {
                    connection.set("scm:git:[fetch=]https://github.com/soberich/marvel.git[push=]git://github.com/soberich/marvel.git")
                    developerConnection.set("scm:git:[fetch=]git://github.com/soberich/marvel.git[push=]ssh://github.com/soberich/marvel.git")
                    url.set("https://github.com/soberich/marvel")
                }
            }
        }
    }
}

tasks.withType<PublishToMavenRepository>().configureEach {
    val oldValue: String? = System.getProperty("org.gradle.internal.publish.checksums.insecure")
    doFirst {
        if ("SNAPSHOT" in version.toString())
             System.setProperty("org.gradle.internal.publish.checksums.insecure", "true")
        else System.setProperty("org.gradle.internal.publish.checksums.insecure", "false")
    }
    doLast {
        if (oldValue != System.getProperty("org.gradle.internal.publish.checksums.insecure")) {
            if (oldValue != null)
                 System.setProperty("org.gradle.internal.publish.checksums.insecure", oldValue)
            else System.clearProperty("org.gradle.internal.publish.checksums.insecure")
        }
    }
}
