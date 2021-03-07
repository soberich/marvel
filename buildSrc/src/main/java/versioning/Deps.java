package versioning;

/**
 * Project targets all maximum developer experience
 * and tries to hit as close to "how it should be done" (a.k.a. "standard") as possible.
 * For now this file is in Java as IntelliJ IDEA gives a nice pop-up for Java, but not Kotlin/Groovy
 * allowing to see the actual contents of variable with dependency declaration.
 * e.g.
 * <pre>{@code
 *     Deps.Libs
 *     String JACKSON   = "com.fasterxml.jackson:jackson-databind" + ':' + Versions.JACKSON = "com.fasterxml.jackson:jackson-databind:{@value versioning.Platforms.Versions#JACKSON}"
 * }</pre>
 */
@SuppressWarnings("squid:S1214")
public interface Deps {

    interface Versions {
        String ACTIVATION            = "[1.1,2)";
        String ANNOTATION            = "[1.3,2)";
        String CDI                   = "[2,3)";
        String CONCURRENT            = "[1.1,2)";
        String HIBERNATE_JPAMODELGEN = "6.0.0.Alpha5";
        String INJECT                = "(0,3)";
        String JAX_RS                = "[2.1,2.2)";
        String JAXB                  = "[2.3,2.5)";
        String JAXB_RUNTIME          = "[2.3,2.5)";
        String JSR_305               = "[3,5)";
        String JSONB                 = "[1,2)";
        String MONEY                 = "[1,2)";
        String PERSISTENCE           = "[2.2,3)";
        String REACTIVE_STREAMS      = "[1,2)";
        String SERVLET               = "[4,5)";
        String TRANSACTION           = "[1.3,2.1)";
        String VALIDATION            = "[2,3.1)";

        String COROUTINES            = "+";
        String EBEAN                 = "12.2.1";
        String EBEAN_ANNOTATION      = "6.11";
        String EBEAN_PERSISTENCE     = "2.2.4";
        String EBEAN_QUERY           = EBEAN;
        String EBEAN_QUERY_GEN       = EBEAN;
        String EBEAN_TEST            = EBEAN;
        String HIBERNATE_TYPES       = "2.9.+";
        String IMMUTABLES            = "2.8.9-SNAPSHOT";
        String JBOSS_LOG             = "2.1.4.Final";
        String LIQUIBASE_GRADLE      = "2.0.1";
        String LIQUIBASE_HIB5        = "3.6";
        String MAPSTRUCT             = "1.4.0.Beta3";
        //String MAPSTRUCT             = "1.4.0.CR1";
        String OPENAPI               = "2.1.3";
        String SLF4J_API             = "1.7.25";
        String SLF4J_JBOSS           = "1.0.4.GA";
        String VALIDATOR             = "[6,7)";
    }

    interface Platforms {
        //val KOTLIN: Nothing = TODO("Scripts are PRE-compiled, can't these lines from script to apply '" + "org.jetbrains.kotlin:kotlin-bom"   + ':' + Versions.KOTLIN + '\'')
        String ARROW          = "io.arrow-kt:arrow-stack"                                  + ':' + versioning.Platforms.Versions.ARROW;
        String BLAZE_JPA      = "com.blazebit:blaze-persistence-bom"                       + ':' + versioning.Platforms.Versions.BLAZE_JPA;
        String COROUTINES     = "org.jetbrains.kotlinx:kotlinx-coroutines-bom"             + ':' + versioning.Platforms.Versions.COROUTINES;
        String GUAVA          = "com.google.guava:guava-bom"                               + ':' + versioning.Platforms.Versions.GUAVA;
        String IMMUTABLES     = "org.immutables:bom"                                       + ':' + versioning.Platforms.Versions.IMMUTABLES;
        String JACKSON        = "com.fasterxml.jackson:jackson-bom"                        + ':' + versioning.Platforms.Versions.JACKSON;
        String JAXB           = "org.glassfish.jaxb:jaxb-bom"                              + ':' + versioning.Platforms.Versions.JAXB;
        String JAXB_RUNTIME   = "com.sun.xml.bind:jaxb-bom"                                + ':' + versioning.Platforms.Versions.JAXB;
        String KTOR           = "io.ktor:ktor-bom"                                         + ':' + versioning.Platforms.Versions.KTOR;
        String MICRONAUT      = "io.micronaut:micronaut-bom"                               + ':' + versioning.Platforms.Versions.MICRONAUT;
        String MICRONAUT_DATA = "io.micronaut.data:micronaut-data-bom"                     + ':' + versioning.Platforms.Versions.MICRONAUT_DATA;
        String QUARKUS        = "io.quarkus:quarkus-universe-bom"                          + ':' + versioning.Platforms.Versions.QUARKUS;
        String RESTEASY       = "org.jboss.resteasy:resteasy-bom"                          + ':' + versioning.Platforms.Versions.RESTEASY;

        String JUNIT5         = "org.junit:junit-bom"                                      + ':' + versioning.Platforms.Versions.JUNIT5;
    }

    interface Jakarta {
        String ACTIVATION   = "jakarta.activation:jakarta.activation-api"                       + ':' + Versions.ACTIVATION;
        String ANNOTATION   = "jakarta.annotation:jakarta.annotation-api"                       + ':' + Versions.ANNOTATION;
        String CDI          = "jakarta.enterprise:jakarta.enterprise.cdi-api"                   + ':' + Versions.CDI;
        String CONCURRENT   = "jakarta.enterprise.concurrent:jakarta.enterprise.concurrent-api" + ':' + Versions.CONCURRENT;
        String INJECT       = "org.glassfish.hk2.external:jakarta.inject"                       + ':' + Versions.INJECT;
        String JAX_RS       = "jakarta.ws.rs:jakarta.ws.rs-api"                                 + ':' + Versions.JAX_RS;
        String JAXB         = "jakarta.xml.bind:jakarta.xml.bind-api"                           + ':' + Versions.JAXB;
        String JAXB_RUNTIME = "org.glassfish.jaxb:jaxb-runtime"                                 + ':' + Versions.JAXB;
        String JSR_305      = "com.github.spotbugs:spotbugs-annotations"                        + ':' + Versions.JSR_305;
        String PERSISTENCE  = "jakarta.persistence:jakarta.persistence-api"                     + ':' + Versions.PERSISTENCE;
        String SERVLET      = "jakarta.servlet:jakarta.servlet-api"                             + ':' + Versions.SERVLET;
        String TRANSACTION  = "jakarta.transaction:jakarta.transaction-api"                     + ':' + Versions.TRANSACTION;
        String VALIDATION   = "jakarta.validation:jakarta.validation-api"                       + ':' + Versions.VALIDATION;
    }

    interface Javax {
        String ACTIVATION   = "javax.activation:activation"                                     + ':' + Versions.ACTIVATION;
        String ANNOTATION   = "javax.annotation:javax.annotation-api"                           + ':' + Versions.ANNOTATION;
        String CDI          = "javax.enterprise:cdi-api"                                        + ':' + Versions.CDI;
        String CONCURRENT   = "javax.enterprise.concurrent:javax.enterprise.concurrent-api"     + ':' + Versions.CONCURRENT;
        String INJECT       = "javax.inject:javax.inject"                                       + ':' + Versions.INJECT;
        String JAX_RS       = "javax.ws.rs:javax.ws.rs-api"                                     + ':' + Versions.JAX_RS;
        String JAXB         = "javax.xml.bind:jaxb-api"                                         + ':' + Versions.JAXB;
        String JAXB_RUNTIME = "org.glassfish.jaxb:jaxb-runtime"                                 + ':' + Versions.JAXB_RUNTIME;
        String JSONB        = "javax.json.bind:javax.json.bind-api"                             + ':' + Versions.JSONB;
        String JSR_305      = "com.google.code.findbugs:jsr305"                                 + ':' + Versions.JSR_305;
        String MONEY        = "javax.money:money-api"                                           + ':' + Versions.MONEY;
        String PERSISTENCE  = "javax.persistence:javax.persistence-api"                         + ':' + Versions.PERSISTENCE;
        String SERVLET      = "javax.servlet:javax.servlet-api"                                 + ':' + Versions.SERVLET;
        String TRANSACTION  = "javax.transaction:javax.transaction-api"                         + ':' + Versions.TRANSACTION;
        String VALIDATION   = "javax.validation:validation-api"                                 + ':' + Versions.VALIDATION;
    }

    interface Libs {
        String COROUTINES_JDK8             = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8"                    + ':' + Versions.COROUTINES;
        String COROUTINES_RXJAVA2          = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2"                     + ':' + Versions.COROUTINES;
        String EBEAN                       = "io.ebean:ebean"                                                   + ':' + Versions.EBEAN;
        String EBEAN_ANNOTATION            = "io.ebean:ebean-annotation"                                        + ':' + Versions.EBEAN_ANNOTATION;
        String EBEAN_PERSISTENCE           = "io.ebean:persistence-api"                                         + ':' + Versions.EBEAN_PERSISTENCE;
        String EBEAN_QUERY                 = "io.ebean:ebean-querybean"                                         + ':' + Versions.EBEAN_QUERY;
        String EBEAN_QUERY_GEN             = "io.ebean:kotlin-querybean-generator"                              + ':' + Versions.EBEAN_QUERY_GEN;
        String EBEAN_TEST                  = "io.ebean:ebean-test"                                              + ':' + Versions.EBEAN_TEST;
        String HIBERNATE_JPAMODELGEN       = "org.hibernate.orm:hibernate-jpamodelgen"                          + ':' + Versions.HIBERNATE_JPAMODELGEN;
        String HIBERNATE_TYPES             = "com.vladmihalcea:hibernate-types-52"                              + ':' + Versions.HIBERNATE_TYPES;
        String IMMUTABLES_VALUE            = "org.immutables:value"                                             + ':' + Versions.IMMUTABLES;
        String IMMUTABLES_BUILDER          = "org.immutables:builder"                                           + ':' + Versions.IMMUTABLES;
        String JBOSS_LOG                   = "org.jboss.logmanager:jboss-logmanager"                            + ':' + Versions.JBOSS_LOG;
        String LIQUIBASE_GRADLE            = "org.liquibase:liquibase-gradle-plugin"                            + ':' + Versions.LIQUIBASE_GRADLE;
        String LIQUIBASE_HIB5              = "org.liquibase.ext:liquibase-hibernate5"                           + ':' + Versions.LIQUIBASE_HIB5;
        String MAPSTRUCT                   = "org.mapstruct:mapstruct"                                          + ':' + Versions.MAPSTRUCT;
        String MAPSTRUCT_AP                = "org.mapstruct:mapstruct-processor"                                + ':' + Versions.MAPSTRUCT;
        String OPENAPI_JAXRS2              = "io.swagger.core.v3:swagger-jaxrs2"                                + ':' + Versions.OPENAPI;
        String OPENAPI_MODELS              = "io.swagger.core.v3:swagger-models"                                + ':' + Versions.OPENAPI;
        String REACTIVE_STREAMS            = "org.reactivestreams:reactive-streams"                             + ':' + Versions.REACTIVE_STREAMS;
        String SLF4J_API                   = "org.slf4j:slf4j-api"                                              + ':' + Versions.SLF4J_API;
        String SLF4J_JBOSS                 = "org.jboss.slf4j:slf4j-jboss-logmanager"                           + ':' + Versions.SLF4J_JBOSS;
        String VALIDATOR                   = "org.hibernate.validator:hibernate-validator"                      + ':' + Versions.VALIDATOR;
        String VALIDATOR_AP                = "org.hibernate.validator:hibernate-validator-annotation-processor" + ':' + Versions.VALIDATOR;
    }
}
