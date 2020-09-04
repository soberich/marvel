package com.example.marvel

//import io.ktor.server.netty.NettyApplicationEngine
//import io.micronaut.ktor.*
//import io.micronaut.ktor.runApplication        as runKtor
import io.micronaut.runtime.mnRun              as runMicronautHttp
import org.springframework.boot.runApplication as runSpringApplication

//import io.micronaut.spring.context.MicronautApplicationContext // PARENT_CONTEXT
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import javax.inject.Named
import javax.inject.Singleton


@OpenAPIDefinition(
    info = Info(
        title = "Hello World",
        version = "0.0",
        description = "My API",
        license = License(name = "Apache 2.0", url = "http://foo.bar"),
        contact = Contact(
            url = "http://gigantic-server.com",
            name = "soberich",
            email = "soberich@gigagantic-server.com")))
@Named
@Singleton
@SpringBootApplication(proxyBeanMethods = false)
//@EnableLoadTimeWeaving
class Application /*: KtorApplication<NettyApplicationEngine.Configuration>({
    applicationEngineEnvironment {
        log = LoggerFactory.getLogger(Application::class.java)
    }

    applicationEngine {
        workerGroupSize = 10
    }
})*/

fun main(vararg args: String) {
    if (args.isNotEmpty() && "spring" in args[0])
    runSpringApplication<Application>(*args)/* {
        addListeners(ApplicationListener<ApplicationPreparedEvent?> {
            it.applicationContext.beanFactory.registerSingleton(LOAD_TIME_WEAVER_BEAN_NAME, InstrumentationLoadTimeWeaver())
        })
    }*/
    else runMicronautHttp<Application>(*args)
}
