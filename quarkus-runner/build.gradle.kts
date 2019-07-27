import versioning.Deps

plugins {
    `spring-boot`
    kronstadt
}

dependencies {
    implementation(project(":runtime-jaxrs", "default")) //FIXME: WHY `api` configuration is available here!??
    runtimeOnly(project(":runtime-blocking-sql-jpa", "default"))
    implementation(platform(Deps.Platforms.BLAZE_JPA))

    compileOnly("com.blazebit:blaze-persistence-core-api")

    arrayOf(
            Deps.Libs.HIBERNATE,
            Deps.Libs.RESTEASY_BOOT,
            "org.springframework.boot:spring-boot-starter-actuator",
            "org.springframework.boot:spring-boot-starter-aop",
            "org.springframework.boot:spring-boot-starter-logging"
    ).forEach(::implementation)
    api("com.blazebit:blaze-persistence-integration-entity-view-cdi")
}
