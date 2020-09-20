import versioning.Deps

dependencies {
    project.the<SourceSetContainer>().configureEach {
        if ("test" !in name && "Test" !in name) {
            arrayOf(
                platform(Deps.Platforms.JACKSON),
                "com.fasterxml.jackson.core:jackson-annotations",
                "com.fasterxml.jackson.core:jackson-core",
                "com.fasterxml.jackson.core:jackson-databind",
                //"com.fasterxml.jackson.datatype:jackson-datatype-hppc",
                "com.fasterxml.jackson.datatype:jackson-datatype-jdk8",
                //"com.fasterxml.jackson.datatype:jackson-datatype-json-org",
                "com.fasterxml.jackson.datatype:jackson-datatype-jsr310",
                "com.fasterxml.jackson.jaxrs:jackson-jaxrs-base",
                "com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider",
                "com.fasterxml.jackson.module:jackson-module-afterburner"
                //"com.fasterxml.jackson.module:jackson-module-jaxb-annotations",
                //"com.fasterxml.jackson.module:jackson-module-mrbean",
                //"com.fasterxml.jackson.module:jackson-module-parameter-names"
                //"com.github.fge:jackson-coreutils",
                //"org.jdbi.jackson-contrib:jackson-module-blackbird:0.0.1"
                //"org.zalando:jackson-datatype-money",
                //"org.zalando:jackson-datatype-problem"
                //Deps.Libs.MONEY
            ).forEach { implementationConfigurationName(it) }
        }
    }
}
