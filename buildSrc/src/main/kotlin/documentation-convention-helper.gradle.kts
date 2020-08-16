import versioning.Deps

plugins {
    java
    io.swagger.core.v3.`swagger-gradle-plugin`
    //com.webcohesion.enunciate
}

dependencies {
    compileOnly(Deps.Javax.JAX_RS)

    implementation(Deps.Libs.OPENAPI_MODELS)
    implementation(Deps.Libs.OPENAPI_JAXRS2)
}
