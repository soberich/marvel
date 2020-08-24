package com.example.marvel.runtime

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationPreparedEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext.*
import org.springframework.context.annotation.EnableLoadTimeWeaving
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver
import javax.inject.Named
import javax.inject.Singleton
import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.ktor.*
import io.micronaut.ktor.runApplication        as runKtor
//import io.micronaut.runtime.mnRun              as runMicronautHttp
//import org.springframework.boot.runApplication as runSpringApplication
import org.slf4j.LoggerFactory

@OpenAPIDefinition(
    info = Info(
        title = "Hello World",
        version = "0.0",
        description = "My API",
        license = License(name = "Apache 2.0", url = "http://foo.bar"),
        contact = Contact(
            url = "http://gigantic-server.com",
            name = "soberich",
            email = "soberich@gigagantic-server.com"
        )
    )
)
@Named
@Singleton
@SpringBootApplication(proxyBeanMethods = false)
@EnableLoadTimeWeaving
class Application : KtorApplication<NettyApplicationEngine.Configuration>({
    applicationEngineEnvironment {
        log = LoggerFactory.getLogger(Application::class.java)
    }

    applicationEngine {
        workerGroupSize = 10
    }
})

fun main(vararg args: String) {
    runKtor(arrayOf(*args))
//    runMicronautHttp<Application>(*args)

//    runSpringApplication<Application>(*args) {
//        addListeners(ApplicationListener<ApplicationPreparedEvent?> {
//            it.applicationContext.beanFactory.registerSingleton(LOAD_TIME_WEAVER_BEAN_NAME, InstrumentationLoadTimeWeaver())
//        })
//    }
}
