package com.example.marvel.runtime

import com.example.marvel.convention.serial.Json
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonConfiguration {

    @get:Bean
    val objectMapper: ObjectMapper = Json()
}
