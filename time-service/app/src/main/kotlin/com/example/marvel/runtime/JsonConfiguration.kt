package com.example.marvel.runtime

import com.example.marvel.convention.serial.Json
import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.jackson.ObjectMapperCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.enterprise.inject.Instance

@Configuration
class JsonConfiguration {

    @Bean
    fun objectMapper(cstzrs: Instance<ObjectMapperCustomizer>): ObjectMapper = Json().apply {
        cstzrs.takeUnless(Instance<ObjectMapperCustomizer>::isUnsatisfied)?.forEach { it.customize(this) }
    }
}
