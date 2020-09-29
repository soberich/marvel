@file:Suppress("ConvertToStringTemplate")

import versioning.Deps

plugins {
    `java-platform`
    org.springframework.boot apply false
    `maven-publish`
}

repositories.jcenter()

val arrowVersion        : String by project
val blazeJpaVersion     : String by project
val coroutinesVersion   : String by project
val guavaVersion        : String by project
val hibernateVersion    : String by project
val immutablesVersion   : String by project
val jacksonVersion      : String by project
val ktorVersion         : String by project
val micronautVersion    : String by project
val micronautDataVersion: String by project
val quarkusVersion      : String by project
val reactorVersion      : String by project
val resteasyVersion     : String by project

dependencies {
    api(platform("io.arrow-kt"                      + ':' +"arrow-stack"             + ':' + arrowVersion))
    //FIXME: Report Gradle team that without `enforced...`  1.5.0-Alpha5 is chosen over 1.5.0-SNAPSHOT (
    api(enforcedPlatform("com.blazebit"                     + ':' +"blaze-persistence-bom"   + ':' + blazeJpaVersion))
    api(platform("org.jetbrains.kotlinx"            + ':' +"kotlinx-coroutines-bom"  + ':' + coroutinesVersion))
    api(platform("com.google.guava"                 + ':' +"guava-bom"               + ':' + guavaVersion))
    api(platform("org.immutables"                   + ':' +"bom"                     + ':' + immutablesVersion))
    api(platform("com.fasterxml.jackson"            + ':' +"jackson-bom"             + ':' + jacksonVersion))
    api(platform("io.ktor"                          + ':' +"ktor-bom"                + ':' + ktorVersion))
    api(platform("io.micronaut"                     + ':' +"micronaut-bom"           + ':' + micronautVersion))
    //api(platform("io.micronaut.data"                + ':' +"micronaut-data-bom"      + ':' + micronautDataVersion))
    api(platform("io.quarkus"                       + ':' +"quarkus-bom"             + ':' + quarkusVersion))
    //api(platform("io.quarkus"                       + ':' +"quarkus-universe-bom"    + ':' + quarkusVersion))
    api(platform("org.jboss.resteasy"               + ':' +"resteasy-bom"            + ':' + resteasyVersion))
    api(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))

    constraints {
        api(Deps.Jakarta.ACTIVATION)
        api(Deps.Jakarta.ANNOTATION)
        api(Deps.Jakarta.CDI)
        api(Deps.Jakarta.CONCURRENT)
        api(Deps.Jakarta.INJECT)
        api(Deps.Jakarta.JAX_RS)
        api(Deps.Jakarta.JAXB)
        api(Deps.Jakarta.JAXB_RUNTIME)
        api(Deps.Jakarta.JSR_305)
        api(Deps.Jakarta.PERSISTENCE)
        api(Deps.Jakarta.SERVLET)
        api(Deps.Jakarta.TRANSACTION)
        api(Deps.Jakarta.VALIDATION)

        api(Deps.Javax.ACTIVATION)
        api(Deps.Javax.ANNOTATION)
        api(Deps.Javax.CDI)
        api(Deps.Javax.CONCURRENT)
        api(Deps.Javax.INJECT)
        api(Deps.Javax.JAX_RS)
        api(Deps.Javax.JAXB)
        api(Deps.Javax.JAXB_RUNTIME)
        api(Deps.Javax.JSONB)
        api(Deps.Javax.JSR_305)
        api(Deps.Javax.MONEY)
        api(Deps.Javax.PERSISTENCE)
        api(Deps.Javax.SERVLET)
        api(Deps.Javax.TRANSACTION)
        api(Deps.Javax.VALIDATION)
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
        maven("https://api.bintray.com/content/soberich/maven/com.github.soberich.marvel:$name/$version;publish=1;override=1;").credentials {
            username = System.getenv("BINTRAY_USER")
            password = System.getenv("BINTRAY_API_KEY")
        }
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
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
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
    val odlValue: String? = System.getProperty("org.gradle.internal.publish.checksums.insecure")
    doFirst {
        if ("SNAPSHOT" in version.toString())
             System.setProperty("org.gradle.internal.publish.checksums.insecure", "true")
        else System.setProperty("org.gradle.internal.publish.checksums.insecure", "false")
    }
    doLast {
        if (odlValue != System.getProperty("org.gradle.internal.publish.checksums.insecure")) {
            if (odlValue != null)
                 System.setProperty("org.gradle.internal.publish.checksums.insecure", odlValue)
            else System.clearProperty("org.gradle.internal.publish.checksums.insecure")
        }
    }
}
