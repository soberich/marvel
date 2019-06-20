package versioning;

/**
 * Project targets all maximum developer experience
 * and tries to hit as close to "how it should be done" (a.k.a. "standard") as possible.
 * For now this file is in Java as IntelliJ IDEA gives nice pop-ups for Java and but not Kotlin/Groovy
 * allowing to see what this string with dependency declaration actually contains.
 * e.g.
 * <pre>{@code
 *     Deps.Libs
 *     String JACKSON   = "com.fasterxml.jackson:jackson-databind" + ':' + Versions.JACKSON = "com.fasterxml.jackson:jackson-databind:2.9.9"
 * }</pre>
 */
public interface Deps {

    interface Versions {
        String ACTIVATION        = "[1.1,2)";
        String ANNOTATION        = "[1.3,2)";
        String CDI               = "[2,3)";
        String CONCURRENT        = "[1.1,2)";
        String INJECT            = "[,3)";
        String JAX_RS            = "[2.1,2.2)";
        String JAXB              = "[2.3,2.5)";
        String JAXB_RUNTIME      = "[2.4,2.5)";
        String PERSISTENCE       = "[2.2,3)";
        String REACTIVE_STREAMS  = "[1,2)";
        String SERVLET           = "[4,5)";
        String TRANSACTION       = "[1.3,2)";
        String VALIDATION        = "[2,2.1)";

        String ARROW             = "+";
        String COROUTINES        = "+";
        String EBEAN             = "11.39.1";
        String EBEAN_ANNOTATION  = "4.10";
        String EBEAN_PERSISTENCE = "2.2.2";
        String EBEAN_QUERY       = EBEAN;
        String EBEAN_QUERY_GEN   = EBEAN;
        String EBEAN_TEST        = EBEAN;
        String HIKARI            = "+";
        String JACKSON           = "2.9.9";
        String JACKSON_COREUTILS = "1.8";
        String JACKSON_MONEY     = "1.0.2";
        String JBOSS_LOG         = "2.1.4.Final";
        String JJWT              = "0.9.1";
        String JSON_PATCH        = "1.9";
        String JSR_305           = "3.0.2";
        //val KOTLIN: Nothing = TODO("Scripts are PRE-compiled, can't these lines from script to apply")
        String LIQUIBASE         = "3.6.2";
        String LIQUIBASE_GRADLE  = "2.0.1";
        String LIQUIBASE_HIB5    = "3.6";
        String LOMBOK            = "1.18.2";
        String MONEY             = "1.3";
        String OKHTTP            = "4.0.0-rc1";
        String OPENAPI           = "2.0.8";
        String RESTEASY          = "4.0.0.Final";
        String RESTEASY_BOOT     = "3.0.0.Final";
        String RETROFIT2         = "2.4.0";
        String SLF4J_API         = "1.7.25";
        String SLF4J_JBOSS       = "1.0.4.GA";
        String STATEMACHINE      = "2.1.3.RELEASE";
        String SWAGGER           = "2.0.7";
        String VALIDATOR         = "6.0.12.Final";
        String ZALANDO_PROBLEM   = "0.22.0";

    }

    interface Platforms {
        //val KOTLIN: Nothing = TODO("Scripts are PRE-compiled, can't these lines from script to apply '" + "org.jetbrains.kotlin:kotlin-bom"   + ':' + Versions.KOTLIN + '\'')
        String JACKSON      = "com.fasterxml.jackson:jackson-bom"                        + ':' + versioning.Platforms.Versions.JACKSON;
        String RESTEASY     = "org.jboss.resteasy:resteasy-bom"                          + ':' + Versions.RESTEASY;
        String QUARKUS      = "io.quarkus:quarkus-bom"                                   + ':' + versioning.Platforms.Versions.QUARKUS;
        String STATEMACHINE = "org.springframework.statemachine:spring-statemachine-bom" + ':' + Versions.STATEMACHINE;
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
        String JSR_305      = "com.google.code.findbugs:jsr305"                                 + ':' + Versions.JSR_305;
        String PERSISTENCE  = "javax.persistence:javax.persistence-api"                         + ':' + Versions.PERSISTENCE;
        String SERVLET      = "javax.servlet:javax.servlet-api"                                 + ':' + Versions.SERVLET;
        String TRANSACTION  = "javax.transaction:javax.transaction-api"                         + ':' + Versions.TRANSACTION;
        String VALIDATION   = "javax.validation:validation-api"                                 + ':' + Versions.VALIDATION;
    }

    interface Libs {
        //Meta `apt`
        String ARROW_META                                  = "io.arrow-kt:arrow-meta"                                  + ':' + Versions.ARROW;
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
        String ARROW_OPTICS                                = "io.arrow-kt:arrow-optics"                                + ':' + Versions.ARROW; //optional
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
        String EBEAN_TEST                  = "io.ebean.test:ebean-test-config"                                  + ':' + Versions.EBEAN_TEST;
        String HIKARI                      = "com.zaxxer:HikariCP"                                              + ':' + Versions.HIKARI;
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
        String JACKSON_PARAMETER           = "com.fasterxml.jackson.module:jackson-module-parameter-names"      + ':' + versioning.Platforms.Versions.JACKSON;
        String JBOSS_LOG                   = "org.jboss.logmanager:jboss-logmanager"                            + ':' + Versions.JBOSS_LOG;
        String JJWT                        = "io.jsonwebtoken:jjwt"                                             + ':' + Versions.JJWT;
        String JSON_PATCH                  = "com.github.fge:json-patch"                                        + ':' + Versions.JSON_PATCH;
        String LIQUIBASE                   = "org.liquibase:liquibase-core"                                     + ':' + Versions.LIQUIBASE;
        String LIQUIBASE_GRADLE            = "org.liquibase:liquibase-gradle-plugin"                            + ':' + Versions.LIQUIBASE_GRADLE;
        String LIQUIBASE_HIB5              = "org.liquibase.ext:liquibase-hibernate5"                           + ':' + Versions.LIQUIBASE_HIB5;
        String LOMBOK                      = "org.projectlombok:lombok"                                         + ':' + Versions.LOMBOK;
        String MONEY                       = "org.javamoney:moneta"                                             + ':' + Versions.MONEY;
        String OKHTTP                      = "com.squareup.okhttp3:okhttp-urlconnection"                        + ':' + Versions.OKHTTP;
        String OKHTTP_LOG                  = "com.squareup.okhttp3:logging-interceptor"                         + ':' + Versions.OKHTTP;
        String OKHTTP_URL                  = "com.squareup.okhttp3:okhttp-urlconnection"                        + ':' + Versions.OKHTTP;
        String OPENAPI_JAXRS2              = "io.swagger.core.v3:swagger-jaxrs2"                                + ':' + Versions.OPENAPI;
        String OPENAPI_MODELS              = "io.swagger.core.v3:swagger-models"                                + ':' + Versions.OPENAPI;
        String REACTIVE_STREAMS            = "org.reactivestreams:reactive-streams"                             + ':' + Versions.REACTIVE_STREAMS;
        String RESTEASY_BOOT               = "org.jboss.resteasy:resteasy-spring-boot-starter"                  + ':' + Versions.RESTEASY_BOOT;
        String RETROFIT2                   = "com.squareup.retrofit2:retrofit"                                  + ':' + Versions.RETROFIT2;
        String RETROFIT2_JACK              = "com.squareup.retrofit2:converter-jackson"                         + ':' + Versions.RETROFIT2;
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


