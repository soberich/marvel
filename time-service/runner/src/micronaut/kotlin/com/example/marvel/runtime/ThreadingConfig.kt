package com.example.marvel.runtime

import edu.umd.cs.findbugs.annotations.Nullable
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.codec.MediaTypeCodecRegistry
import io.micronaut.http.netty.channel.NettyThreadFactory
import io.micronaut.http.server.binding.RequestArgumentSatisfier
import io.micronaut.http.server.netty.EventLoopGroupFactory
import io.micronaut.http.server.netty.HttpCompressionStrategy
import io.micronaut.http.server.netty.HttpContentProcessorResolver
import io.micronaut.http.server.netty.NettyHttpServer
import io.micronaut.http.server.netty.configuration.NettyHttpServerConfiguration
import io.micronaut.http.server.netty.ssl.ServerSslBuilder
import io.micronaut.http.server.netty.types.NettyCustomizableResponseTypeHandlerRegistry
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.executor.ExecutorSelector
import io.micronaut.web.router.Router
import io.micronaut.web.router.resource.StaticResourceResolver
import io.netty.channel.ChannelOutboundHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadFactory
import javax.inject.Named

@Configuration(proxyBeanMethods = false)
class ThreadingConfig {


    /**
     * FIXME: Needed to isKeepAlive = true
     * TODO: Report to Micronaut.
     */
    @Primary
    @Bean
    @Replaces(NettyHttpServer::class)
    fun NettyHttpServer(
        serverConfiguration: NettyHttpServerConfiguration,
        applicationContext: ApplicationContext,
        router: Router,
        requestArgumentSatisfier: RequestArgumentSatisfier,
        mediaTypeCodecRegistry: MediaTypeCodecRegistry,
        customizableResponseTypeHandlerRegistry: NettyCustomizableResponseTypeHandlerRegistry,
        resourceResolver: StaticResourceResolver,
        @Named(TaskExecutors.IO) ioExecutor: ExecutorService,
        @Named(NettyThreadFactory.NAME) threadFactory: ThreadFactory,
        executorSelector: ExecutorSelector,
        @Nullable serverSslBuilder: ServerSslBuilder?,
        outboundHandlers: List<ChannelOutboundHandler>,
        eventLoopGroupFactory: EventLoopGroupFactory,
        httpCompressionStrategy: HttpCompressionStrategy,
        httpContentProcessorResolve: HttpContentProcessorResolver
    ): NettyHttpServer {
        return object : NettyHttpServer(
            serverConfiguration,
            applicationContext,
            router,
            requestArgumentSatisfier,
            mediaTypeCodecRegistry,
            customizableResponseTypeHandlerRegistry,
            resourceResolver,
            ioExecutor,
            threadFactory,
            executorSelector,
            serverSslBuilder,
            outboundHandlers,
            eventLoopGroupFactory,
            httpCompressionStrategy,
            httpContentProcessorResolve
        ) {
            override fun isKeepAlive(): Boolean {
                return true;
            }
        }
    }

    @Primary
    @Bean("netty")
    @Replaces(ThreadFactory::class)
    fun threadFactory(): ThreadFactory =
        Thread.builder().virtual().name("task-vt#", 0).factory()
}
