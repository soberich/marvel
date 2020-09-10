package com.example.marvel.runtime

import com.example.marvel.convention.serial.Json
import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.jackson.ObjectMapperCustomizer
import io.quarkus.runtime.Startup
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct
import javax.enterprise.inject.Instance
import javax.enterprise.inject.Produces
import javax.enterprise.inject.spi.BeanManager
import javax.inject.Inject
import javax.inject.Singleton
import javax.enterprise.inject.Any
import javax.enterprise.util.AnnotationLiteral


@Startup
@Singleton
@Configuration(proxyBeanMethods = false)
class JsonConfig {

    @set:Inject
    lateinit var beanManager: BeanManager

    @PostConstruct
    fun init() {
        val beans = beanManager.getBeans(Object::class.java, object : AnnotationLiteral<Any>() {})
        println("BEANS!!!")
        for (bean in beans) {
            println(bean.beanClass.name)
        }
    }

    /**
     * @see [https://quarkus.io/guides/rest-json#jackson]
     */
    @Produces
    @Singleton
    fun objectMapper(cstzrs: Instance<ObjectMapperCustomizer>): ObjectMapper = Json.CONFIGURED_MAPPER.apply {
        cstzrs.takeUnless(Instance<ObjectMapperCustomizer>::isUnsatisfied)?.forEach { it.customize(this) }
    }
}
