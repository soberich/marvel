package com.example.marvel.runtime

import com.example.marvel.api.EmployeeCommand
import com.example.marvel.convention.jpa.naming.PhysicalNamingStrategyImpl
import com.example.marvel.convention.serial.Json
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Replaces
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.hibernate.naming.DefaultPhysicalNamingStrategy
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

//import com.example.marvel.convention.serial.Json
//import com.fasterxml.jackson.databind.ObjectMapper
//import io.quarkus.jackson.ObjectMapperCustomizer
//import javax.enterprise.context.ApplicationScoped
//import javax.enterprise.inject.Instance
//import javax.enterprise.inject.Produces
//import javax.inject.Singleton

@Introspected(classes = [
    EmployeeCommand::class,
    EmployeeCommand.EmployeeCreateCommand::class,
    EmployeeCommand.EmployeeUpdateCommand::class
])

//@ApplicationScoped
@Configuration(proxyBeanMethods = false)
class JsonConfig {

    @get:
    [Bean
    Primary
    Replaces(ObjectMapper::class)]
    val objectMapper: ObjectMapper get() = Json.configuredBuilder.build()

    @get:
    [Bean
    Primary
    Replaces(DefaultPhysicalNamingStrategy::class)]
    val physicalNamingStrategy: PhysicalNamingStrategy get() = PhysicalNamingStrategyImpl.INSTANCE

//    @Produces
//    @Singleton
//    fun objectMapper(cstzrs: Instance<ObjectMapperCustomizer>): ObjectMapper = Json().apply {
//        cstzrs.takeUnless(Instance<ObjectMapperCustomizer>::isUnsatisfied)?.forEach { it.customize(this) }
//    }
}
