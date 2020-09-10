package com.example.marvel.runtime

import java.lang.reflect.Type
import java.time.YearMonth
import javax.annotation.Priority
import javax.inject.Singleton
import javax.ws.rs.Priorities
import javax.ws.rs.ext.ParamConverter
import javax.ws.rs.ext.ParamConverterProvider
import javax.ws.rs.ext.Provider

@Singleton
@Provider
@Priority(Priorities.USER + 1)
class JacksonJsonParamConverterProvider : ParamConverterProvider {

    @Suppress("UNCHECKED_CAST")
    override fun <T> getConverter(
        rawType: Class<T>,
        genericType: Type,
        annotations: Array<Annotation>
    ): ParamConverter<T>? =
        if (YearMonth::class.java.isAssignableFrom(rawType)) object : ParamConverter<YearMonth> {
            override fun fromString(value: String): YearMonth = YearMonth.parse(value)
            override fun toString(value: YearMonth): String = value.toString()
        } as ParamConverter<T>
        else null
}
