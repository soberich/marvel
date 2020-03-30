package com.example.marvel.convention.serial

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT
import com.fasterxml.jackson.annotation.PropertyAccessor.ALL
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_VALUES
import com.fasterxml.jackson.databind.MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING
import com.fasterxml.jackson.databind.MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS
import com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.afterburner.AfterburnerModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import java.time.ZoneOffset.UTC
import java.util.TimeZone

object Json {

    internal val DEFAULT_TIMEZONE = TimeZone.getTimeZone(UTC)

    @JvmStatic
    val configuredBuilder: JsonMapper.Builder
        get() =
            JsonMapper.builder()
                .addModules(
                    AfterburnerModule(),
                    JavaTimeModule(),
                    ParameterNamesModule()
                ).defaultTimeZone(DEFAULT_TIMEZONE)
                .serializationInclusion(NON_ABSENT)
                .visibility(ALL, PUBLIC_ONLY)
                /*.setDefaultSetterInfo(empty().withValueNulls(SKIP, SKIP))*/
                //.disable(WRITE_DATES_AS_TIMESTAMPS)
                .enable(ALLOW_EXPLICIT_PROPERTY_RENAMING, ACCEPT_CASE_INSENSITIVE_PROPERTIES, ACCEPT_CASE_INSENSITIVE_ENUMS, ACCEPT_CASE_INSENSITIVE_VALUES)
                .enable(FAIL_ON_NUMBERS_FOR_ENUMS, READ_ENUMS_USING_TO_STRING)
                .enable(WRITE_ENUMS_USING_TO_STRING)
                .disable(ALLOW_FINAL_FIELDS_AS_MUTATORS, DEFAULT_VIEW_INCLUSION)
                .disable(FAIL_ON_UNKNOWN_PROPERTIES)
}
