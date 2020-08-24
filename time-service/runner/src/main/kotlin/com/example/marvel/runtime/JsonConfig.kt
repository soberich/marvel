package com.example.marvel.runtime

import com.example.marvel.convention.jpa.naming.PhysicalNamingStrategyImpl
import com.example.marvel.convention.serial.Json
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.metrics.micrometer.*
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import io.micronaut.context.annotation.Replaces
import io.micronaut.data.hibernate.naming.DefaultPhysicalNamingStrategy
import io.micronaut.ktor.KtorApplicationBuilder
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

//@Introspected(classes = [
//    EmployeeCommand::class,
//    EmployeeCommand.EmployeeCreateCommand::class,
//    EmployeeCommand.EmployeeUpdateCommand::class
//])

//@ApplicationScoped
@Configuration(proxyBeanMethods = false)
class JsonConfig : KtorApplicationBuilder({
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter(Json.CONFIGURED_MAPPER))
    }
    install(MicrometerMetrics) {
        registry = SimpleMeterRegistry()
        meterBinders = listOf(
            ClassLoaderMetrics(),
            JvmMemoryMetrics(),
            JvmGcMetrics(),
            ProcessorMetrics(),
            JvmThreadMetrics(),
            FileDescriptorMetrics()
        )
    }
}) {

    @get:
    [Bean
    Primary
    Replaces(ObjectMapper::class)]
    val objectMapper: ObjectMapper get() = Json.CONFIGURED_MAPPER

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
