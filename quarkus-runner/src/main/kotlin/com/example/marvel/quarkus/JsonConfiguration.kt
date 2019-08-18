package com.example.marvel.quarkus

import com.fasterxml.jackson.annotation.JsonAutoDetect.Value.construct
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY
import com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT
import com.fasterxml.jackson.annotation.PropertyAccessor.ALL
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES
import com.fasterxml.jackson.databind.MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING
import com.fasterxml.jackson.databind.MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS
import com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class JsonConfiguration {
    internal val DEFAULT_TIMEZONE = TimeZone.getTimeZone("UTC")

    @get:Bean
    val objectMapper: ObjectMapper = ObjectMapper()
    .setTimeZone(DEFAULT_TIMEZONE).registerModules(
    JavaTimeModule(),
    ParameterNamesModule(PROPERTIES)
    ).setSerializationInclusion(NON_ABSENT)
//            .disable(WRITE_DATES_AS_TIMESTAMPS)
    /*.setDefaultSetterInfo(empty().withValueNulls(SKIP, SKIP))*/
    .setDefaultVisibility(construct(ALL, PUBLIC_ONLY))
    .enable(ALLOW_EXPLICIT_PROPERTY_RENAMING, ACCEPT_CASE_INSENSITIVE_ENUMS, ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    .enable(FAIL_ON_NUMBERS_FOR_ENUMS, READ_ENUMS_USING_TO_STRING)
    .enable(WRITE_ENUMS_USING_TO_STRING)
    .disable(ALLOW_FINAL_FIELDS_AS_MUTATORS, DEFAULT_VIEW_INCLUSION)
    .disable(FAIL_ON_UNKNOWN_PROPERTIES)
}
