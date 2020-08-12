package com.example.marvel.runtime

import io.micronaut.runtime.mnRun
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
    info = Info(
        title = "Hello World",
        version = "0.0",
        description = "My API",
        license = License(name = "Apache 2.0", url = "http://foo.bar"),
        contact = Contact(url = "http://gigantic-server.com", name = "soberich", email = "soberich@gigagantic-server.com")))
@SpringBootApplication(proxyBeanMethods = false)
object Application

fun main(vararg args: String) {
    mnRun<Application>(*args)
//    runApplication<Application>(*args)
}
