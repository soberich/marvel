@file:Suppress("ConvertToStringTemplate")

import versioning.Deps

plugins {
    `java-platform`
    org.springframework.boot apply false
    //io.micronaut.library
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
    api(platform("com.blazebit"                     + ':' +"blaze-persistence-bom"   + ':' + blazeJpaVersion))
    api(platform("org.jetbrains.kotlinx"            + ':' +"kotlinx-coroutines-bom"  + ':' + coroutinesVersion))
    api(platform("com.google.guava"                 + ':' +"guava-bom"               + ':' + guavaVersion))
    api(platform("org.immutables"                   + ':' +"bom"                     + ':' + immutablesVersion))
    api(platform("com.fasterxml.jackson"            + ':' +"jackson-bom"             + ':' + jacksonVersion))
    api(platform("io.ktor"                          + ':' +"ktor-bom"                + ':' + ktorVersion))
    api(platform("io.micronaut"                     + ':' +"micronaut-bom"           + ':' + micronautVersion))
    api(platform("io.micronaut.data"                + ':' +"micronaut-data-bom"      + ':' + micronautDataVersion))
    api(platform("io.quarkus"                       + ':' +"quarkus-universe-bom"    + ':' + quarkusVersion))
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
    publications {
        create<MavenPublication>("myPlatform") {
            from(components["javaPlatform"])
        }
    }
}
