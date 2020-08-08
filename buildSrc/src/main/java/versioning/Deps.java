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
public interface Deps {

    interface Versions {
        String ACTIVATION            = "[1.1,2)";
        String ANNOTATION            = "[1.3,2)";
        String CDI                   = "[2,3)";
        String CONCURRENT            = "[1.1,2)";
        String HIBERNATE             = "5.4.19.Final"; //trying to avoid possible bug in 5.5.0-SNAPSHOT for integrating via SPI for `org.hibernate.integrator.spi.IntegratorService`
        String HIBERNATE_JPAMODELGEN = "6.0.0.Alpha5";
        String INJECT                = "(0,3)";
        String JAX_RS                = "[2.1,2.2)";
        String JAXB                  = "[2.3,2.5)";
        String JAXB_RUNTIME          = "[2.4,2.5)";
        String JSONB                 = "[1,2)";
        String MONEY                 = "[1,2)";
        String PERSISTENCE           = "[2.2,3)";
        String REACTIVE_STREAMS      = "[1,2)";
        String SERVLET               = "[4,5)";
        String TRANSACTION           = "[1.3,2)";
        String VALIDATION            = "[2,3.1)";

        String ARROW                 = "0.10.5";
        String COROUTINES            = "+";
        String EBEAN                 = "12.2.1";
        String EBEAN_ANNOTATION      = "6.11";
        String EBEAN_PERSISTENCE     = "2.2.4";
        String EBEAN_QUERY           = EBEAN;
        String EBEAN_QUERY_GEN       = EBEAN;
        String EBEAN_TEST            = EBEAN;
        String HIBERNATE_TYPES       = "2.9.+";
        String HIKARI                = "+";
        String IMMUTABLES            = "2.8.9-SNAPSHOT";
        String JACKSON_COREUTILS     = "1.8";
        String JACKSON_MONEY         = "1.1.1";
        String JBOSS_LOG             = "2.1.4.Final";
        String JJWT                  = "0.9.1";
        String JSON_PATCH            = "1.9";
        String JSR_305               = "3.0.2";
        //val KOTLIN: Nothing = TODO("Scripts are PRE-compiled, can't these lines from script to apply")
        String LIQUIBASE             = "3.6.2";
        String LIQUIBASE_GRADLE      = "2.0.1";
        String LIQUIBASE_HIB5        = "3.6";
        String LOMBOK                = "1.18.2";
        String MAPSTRUCT             = "1.4.0.Beta3";
        String MONETA                = "1.3";
        String OKHTTP                = "4.0.0-rc1";
        String OPENAPI               = "2.1.3";
        String RESTEASY_BOOT         = "3.0.0.Final";
        String RETROFIT2             = "2.4.0";
        String RX2                   = "2.2.19";
        String SLF4J_API             = "1.7.25";
        String SLF4J_JBOSS           = "1.0.4.GA";
        String SWAGGER               = "2.0.7";
        String VALIDATOR             = "6.+";
        String ZALANDO_PROBLEM       = "0.23.0";
    }

    interface Platforms {
        //val KOTLIN: Nothing = TODO("Scripts are PRE-compiled, can't these lines from script to apply '" + "org.jetbrains.kotlin:kotlin-bom"   + ':' + Versions.KOTLIN + '\'')
        String BLAZE_JPA      = "com.blazebit:blaze-persistence-bom"                       + ':' + versioning.Platforms.Versions.BLAZE_JPA;
        String COROUTINES     = "org.jetbrains.kotlinx:kotlinx-coroutines-bom"             + ':' + versioning.Platforms.Versions.COROUTINES;
        String GUAVA          = "com.google.guava:guava-bom"                               + ':' + versioning.Platforms.Versions.GUAVA;
        String JACKSON        = "com.fasterxml.jackson:jackson-bom"                        + ':' + versioning.Platforms.Versions.JACKSON;
        String MICRONAUT      = "io.micronaut:micronaut-bom"                               + ':' + versioning.Platforms.Versions.MICRONAUT;
        String MICRONAUT_DATA = "io.micronaut.data:micronaut-data-bom"                     + ':' + versioning.Platforms.Versions.MICRONAUT_DATA;
        String QUARKUS        = "io.quarkus:quarkus-bom"                                   + ':' + versioning.Platforms.Versions.QUARKUS;
        String RESTEASY       = "org.jboss.resteasy:resteasy-bom"                          + ':' + versioning.Platforms.Versions.RESTEASY;
        String STATEMACHINE   = "org.springframework.statemachine:spring-statemachine-bom" + ':' + versioning.Platforms.Versions.STATEMACHINE;
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
        String PERSISTENCE  = "jakarta.persistence:jakarta.persistence-api"                     + ':' + Versions.PERSISTENCE;
        String SERVLET      = "jakarta.servlet:jakarta.servlet-api"                             + ':' + Versions.SERVLET;
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
        //Meta `apt`
        String ARROW_META                                  = "io.arrow-kt:arrow-meta"                                  + ':' + Versions.ARROW;
        String ARROW_ANNOTATIONS                           = "io.arrow-kt:arrow-annotations"                           + ':' + Versions.ARROW;
        //Very core
        String ARROW_SYNTAX                                = "io.arrow-kt:arrow-syntax"                                + ':' + Versions.ARROW;
        String ARROW_TYPECLASSES                           = "io.arrow-kt:arrow-typeclasses"                           + ':' + Versions.ARROW;
        //Aggregation of `core` + `extensions`
        String ARROW_CORE                                  = "io.arrow-kt:arrow-core"                                  + ':' + Versions.ARROW;
        String ARROW_EXTRAS                                = "io.arrow-kt:arrow-extras"                                + ':' + Versions.ARROW;
        String ARROW_EFFECTS                               = "io.arrow-kt:arrow-effects"                               + ':' + Versions.ARROW;
        String ARROW_EFFECTS_KOTLINX_COROUTINES            = "io.arrow-kt:arrow-effects-kotlinx-coroutines"            + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_REACTOR                       = "io.arrow-kt:arrow-effects-reactor"                       + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_RX2                           = "io.arrow-kt:arrow-effects-rx2"                           + ':' + Versions.ARROW; //optional
        String ARROW_GENERIC                               = "io.arrow-kt:arrow-generic"                               + ':' + Versions.ARROW;
        String ARROW_OPTICS                                = "io.arrow-kt:arrow-optics"                                + ':' + Versions.ARROW;
        //these are same but broken down to two artifacts
        String ARROW_CORE_DATA                             = "io.arrow-kt:arrow-core-data"                             + ':' + Versions.ARROW;
        String ARROW_CORE_EXTENSIONS                       = "io.arrow-kt:arrow-core-extensions"                       + ':' + Versions.ARROW;
        String ARROW_EXTRAS_DATA                           = "io.arrow-kt:arrow-extras-data"                           + ':' + Versions.ARROW;
        String ARROW_EXTRAS_EXTENSIONS                     = "io.arrow-kt:arrow-extras-extensions"                     + ':' + Versions.ARROW;
        String ARROW_EFFECTS_DATA                          = "io.arrow-kt:arrow-effects-data"                          + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_EXTENSIONS                    = "io.arrow-kt:arrow-effects-extensions"                    + ':' + Versions.ARROW; //optional
        //these are same but more broken down to two artifacts
        String ARROW_EFFECTS_IO_EXTENSIONS                 = "io.arrow-kt:arrow-effects-io-extensions"                 + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_KOTLINX_COROUTINES_DATA       = "io.arrow-kt:arrow-effects-kotlinx-coroutines-data"       + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_KOTLINX_COROUTINES_EXTENSIONS = "io.arrow-kt:arrow-effects-kotlinx-coroutines-extensions" + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_REACTOR_DATA                  = "io.arrow-kt:arrow-effects-reactor-data"                  + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_REACTOR_EXTENSIONS            = "io.arrow-kt:arrow-effects-reactor-extensions"            + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_RX2_DATA                      = "io.arrow-kt:arrow-effects-rx2-data"                      + ':' + Versions.ARROW; //optional
        String ARROW_EFFECTS_RX2_EXTENSIONS                = "io.arrow-kt:arrow-effects-rx2-extensions"                + ':' + Versions.ARROW; //optional
        String ARROW_OPTICS_CORE                           = "io.arrow-kt:arrow-optics-core"                           + ':' + Versions.ARROW; //optional
        String ARROW_OPTICS_MTL                            = "io.arrow-kt:arrow-optics-mtl"                            + ':' + Versions.ARROW; //optional
        //Other
        String COROUTINES_JDK8             = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8"                    + ':' + Versions.COROUTINES;
        String COROUTINES_REACTOR          = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor"                 + ':' + Versions.COROUTINES;
        String COROUTINES_RXJAVA2          = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2"                     + ':' + Versions.COROUTINES;
        String COROUTINES_TEST             = "org.jetbrains.kotlinx:kotlinx-coroutines-test"                    + ':' + Versions.COROUTINES;
        String EBEAN                       = "io.ebean:ebean"                                                   + ':' + Versions.EBEAN;
        String EBEAN_ANNOTATION            = "io.ebean:ebean-annotation"                                        + ':' + Versions.EBEAN_ANNOTATION;
        String EBEAN_PERSISTENCE           = "io.ebean:persistence-api"                                         + ':' + Versions.EBEAN_PERSISTENCE;
        String EBEAN_QUERY                 = "io.ebean:ebean-querybean"                                         + ':' + Versions.EBEAN_QUERY;
        String EBEAN_QUERY_GEN             = "io.ebean:kotlin-querybean-generator"                              + ':' + Versions.EBEAN_QUERY_GEN;
        String EBEAN_TEST                  = "io.ebean:ebean-test"                                              + ':' + Versions.EBEAN_TEST;
        String GUAVA                       = "com.google.guava:guava"                                           + ':' + versioning.Platforms.Versions.GUAVA;
        String HIBERNATE                   = "org.hibernate:hibernate-core"                                     + ':' + Versions.HIBERNATE;
        String HIBERNATE_JPAMODELGEN       = "org.hibernate.orm:hibernate-jpamodelgen"                          + ':' + Versions.HIBERNATE_JPAMODELGEN;
        String HIBERNATE_TYPES             = "com.vladmihalcea:hibernate-types-52"                              + ':' + Versions.HIBERNATE_TYPES;
        String HIKARI                      = "com.zaxxer:HikariCP"                                              + ':' + Versions.HIKARI;
        String IMMUTABLES_VALUE            = "org.immutables:value"                                             + ':' + Versions.IMMUTABLES;
        String IMMUTABLES_BUILDER          = "org.immutables:builder"                                           + ':' + Versions.IMMUTABLES;
        String JACKSON_AFTERBURNER         = "com.fasterxml.jackson.module:jackson-module-afterburner"          + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_ANNOTATIONS         = "com.fasterxml.jackson.core:jackson-annotations"                   + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_CORE                = "com.fasterxml.jackson.core:jackson-core"                          + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_COREUTILS           = "com.github.fge:jackson-coreutils"                                 + ':' + Versions.JACKSON_COREUTILS;
        String JACKSON_DATABIND            = "com.fasterxml.jackson.core:jackson-databind"                      + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_HPPC                = "com.fasterxml.jackson.datatype:jackson-datatype-hppc"             + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_JAXB_ANNOTATIONS    = "com.fasterxml.jackson.module:jackson-module-jaxb-annotations"     + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_JAXRS_BASE          = "com.fasterxml.jackson.jaxrs:jackson-jaxrs-base"                   + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_JAXRS_JSON_PROVIDER = "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider"          + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_JDK8                = "com.fasterxml.jackson.datatype:jackson-datatype-jdk8"             + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_JSON_ORG            = "com.fasterxml.jackson.datatype:jackson-datatype-json-org"         + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_JSR310              = "com.fasterxml.jackson.datatype:jackson-datatype-jsr310"           + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_KOTLIN              = "com.fasterxml.jackson.module:jackson-module-kotlin"               + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_MONEY               = "org.zalando:jackson-datatype-money"                               + ':' + Versions.JACKSON_MONEY;
        String JACKSON_MR_BEAN             = "com.fasterxml.jackson.module:jackson-module-mrbean"               + ':' + versioning.Platforms.Versions.JACKSON;
        String JACKSON_PARAMETER           = "com.fasterxml.jackson.module:jackson-module-parameter-names"      + ':' + versioning.Platforms.Versions.JACKSON;
        String JBOSS_LOG                   = "org.jboss.logmanager:jboss-logmanager"                            + ':' + Versions.JBOSS_LOG;
        String JJWT                        = "io.jsonwebtoken:jjwt"                                             + ':' + Versions.JJWT;
        String JSON_PATCH                  = "com.github.fge:json-patch"                                        + ':' + Versions.JSON_PATCH;
        String LIQUIBASE                   = "org.liquibase:liquibase-core"                                     + ':' + Versions.LIQUIBASE;
        String LIQUIBASE_GRADLE            = "org.liquibase:liquibase-gradle-plugin"                            + ':' + Versions.LIQUIBASE_GRADLE;
        String LIQUIBASE_HIB5              = "org.liquibase.ext:liquibase-hibernate5"                           + ':' + Versions.LIQUIBASE_HIB5;
        String LOMBOK                      = "org.projectlombok:lombok"                                         + ':' + Versions.LOMBOK;
        String MAPSTRUCT                   = "org.mapstruct:mapstruct"                                          + ':' + Versions.MAPSTRUCT;
        String MAPSTRUCT_AP                = "org.mapstruct:mapstruct-processor"                                + ':' + Versions.MAPSTRUCT;
        String MONEY                       = "org.javamoney:moneta"                                             + ':' + Versions.MONETA;
        String OKHTTP                      = "com.squareup.okhttp3:okhttp-urlconnection"                        + ':' + Versions.OKHTTP;
        String OKHTTP_LOG                  = "com.squareup.okhttp3:logging-interceptor"                         + ':' + Versions.OKHTTP;
        String OKHTTP_URL                  = "com.squareup.okhttp3:okhttp-urlconnection"                        + ':' + Versions.OKHTTP;
        String OPENAPI_JAXRS2              = "io.swagger.core.v3:swagger-jaxrs2"                                + ':' + Versions.OPENAPI;
        String OPENAPI_MODELS              = "io.swagger.core.v3:swagger-models"                                + ':' + Versions.OPENAPI;
        String REACTIVE_STREAMS            = "org.reactivestreams:reactive-streams"                             + ':' + Versions.REACTIVE_STREAMS;
        String RESTEASY_BOOT               = "org.jboss.resteasy:resteasy-spring-boot-starter"                  + ':' + Versions.RESTEASY_BOOT;
        String RETROFIT2                   = "com.squareup.retrofit2:retrofit"                                  + ':' + Versions.RETROFIT2;
        String RETROFIT2_JACK              = "com.squareup.retrofit2:converter-jackson"                         + ':' + Versions.RETROFIT2;
        String RX2                         = "io.reactivex.rxjava2:rxjava"                                      + ':' + Versions.RX2;
        String SLF4J_API                   = "org.slf4j:slf4j-api"                                              + ':' + Versions.SLF4J_API;
        String SLF4J_JBOSS                 = "org.jboss.slf4j:slf4j-jboss-logmanager"                           + ':' + Versions.SLF4J_JBOSS;
        String SWAGGER_JAXRS2              = "io.swagger.core.v3:swagger-jaxrs2"                                + ':' + Versions.SWAGGER;
        String SWAGGER_JAXRS2_INIT         = "io.swagger.core.v3:swagger-jaxrs2-servlet-initializer"            + ':' + Versions.SWAGGER;
        String SWAGGER_MODELS              = "io.swagger.core.v3:swagger-models"                                + ':' + Versions.SWAGGER;
        String VALIDATOR                   = "org.hibernate.validator:hibernate-validator"                      + ':' + Versions.VALIDATOR;
        String VALIDATOR_AP                = "org.hibernate.validator:hibernate-validator-annotation-processor" + ':' + Versions.VALIDATOR;
        String ZALANDO_PROBLEM             = "org.zalando:problem"                                              + ':' + Versions.ZALANDO_PROBLEM;
        String ZALANDO_PROBLEM_JACK        = "org.zalando:jackson-datatype-problem"                             + ':' + Versions.ZALANDO_PROBLEM;
    }
}
