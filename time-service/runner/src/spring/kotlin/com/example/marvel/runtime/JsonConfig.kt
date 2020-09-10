package com.example.marvel.runtime

import com.example.marvel.convention.jpa.naming.PhysicalNamingStrategyImpl
import com.example.marvel.convention.serial.Json
import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.boot.model.naming.PhysicalNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration(proxyBeanMethods = false)
class JsonConfig {

    @get:
    [Bean
    Primary]
    val objectMapper: ObjectMapper get() = Json.CONFIGURED_MAPPER

    @get:
    [Bean
    Primary]
    val physicalNamingStrategy: PhysicalNamingStrategy get() = PhysicalNamingStrategyImpl.INSTANCE
}
