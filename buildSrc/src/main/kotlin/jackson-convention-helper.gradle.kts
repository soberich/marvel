import versioning.Deps

plugins {
    java
}

dependencies {
    arrayOf(
        platform(Deps.Platforms.JACKSON),
        "com.fasterxml.jackson.core:jackson-annotations",
        "com.fasterxml.jackson.core:jackson-core",
        "com.fasterxml.jackson.core:jackson-databind",
//        "com.fasterxml.jackson.datatype:jackson-datatype-hppc",
        "com.fasterxml.jackson.datatype:jackson-datatype-jdk8",
//        "com.fasterxml.jackson.datatype:jackson-datatype-json-org",
        "com.fasterxml.jackson.datatype:jackson-datatype-jsr310",
        "com.fasterxml.jackson.jaxrs:jackson-jaxrs-base",
        "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider",
        "org.jdbi.jackson-contrib:jackson-module-blackbird:0.0.1",
//        "com.fasterxml.jackson.module:jackson-module-afterburner",
//        "com.fasterxml.jackson.module:jackson-module-jaxb-annotations",
        "com.fasterxml.jackson.module:jackson-module-kotlin",
//        "com.fasterxml.jackson.module:jackson-module-mrbean",
        "com.fasterxml.jackson.module:jackson-module-parameter-names"
//        "com.github.fge:jackson-coreutils",
//        "org.zalando:jackson-datatype-money",
//        "org.zalando:jackson-datatype-problem",
//        Deps.Libs.MONEY
    ).forEach { "implementation"(it) }
}
