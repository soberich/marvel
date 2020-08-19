package com.example.marvel.convention.serial

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.PUBLIC_ONLY
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT
import com.fasterxml.jackson.annotation.PropertyAccessor.ALL
import com.fasterxml.jackson.databind.DeserializationFeature.*
import com.fasterxml.jackson.databind.MapperFeature.*
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.databind.json.JsonMapper
import java.time.ZoneOffset.UTC
import java.util.*

object Json {

    internal val DEFAULT_TIMEZONE = TimeZone.getTimeZone(UTC)

    @JvmStatic
    val configuredBuilder: JsonMapper.Builder
        get() =
            JsonMapper.builder()
                .findAndAddModules()
                .defaultTimeZone(DEFAULT_TIMEZONE)
                .serializationInclusion(NON_ABSENT)
                .visibility(ALL, PUBLIC_ONLY)
                /*.setDefaultSetterInfo(empty().withValueNulls(SKIP, SKIP))*/
                //.disable(WRITE_DATES_AS_TIMESTAMPS)
                .enable(
                    ACCEPT_CASE_INSENSITIVE_ENUMS,
                    ACCEPT_CASE_INSENSITIVE_PROPERTIES,
                    ACCEPT_CASE_INSENSITIVE_VALUES,
                    ALLOW_EXPLICIT_PROPERTY_RENAMING
                ).enable(
                    ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,
                    ACCEPT_SINGLE_VALUE_AS_ARRAY,
                    FAIL_ON_IGNORED_PROPERTIES,
                    FAIL_ON_NULL_FOR_PRIMITIVES,
                    FAIL_ON_NUMBERS_FOR_ENUMS,
                    FAIL_ON_READING_DUP_TREE_KEY,
                    FAIL_ON_UNKNOWN_PROPERTIES,
                    READ_ENUMS_USING_TO_STRING
                ).enable(
                    WRITE_ENUMS_USING_TO_STRING
                ).disable(
                    ALLOW_FINAL_FIELDS_AS_MUTATORS,
                    DEFAULT_VIEW_INCLUSION
                ).disable(FAIL_ON_UNKNOWN_PROPERTIES)
}
