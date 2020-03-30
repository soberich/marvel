package com.example.marvel.runtime

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

@OpenAPIDefinition(
    info = Info(
        title = "Hello World",
        version = "0.0",
        description = "My API",
        license = License(name = "Apache 2.0", url = "http://foo.bar"),
        contact = Contact(url = "http://gigantic-server.com", name = "Fred", email = "Fred@gigagantic-server.com")
    )
)
object Application {

    @JvmStatic
    fun main(vararg args: String) {
        Micronaut.run(Application::class.java, *args)
    }
}
