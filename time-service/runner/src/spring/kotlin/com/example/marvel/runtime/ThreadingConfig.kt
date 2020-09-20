package com.example.marvel.runtime

import org.eclipse.jetty.util.thread.ThreadPool
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.task.TaskExecutor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS


@Configuration(proxyBeanMethods = false)
class ThreadingConfig {

    @Primary
    @Bean(name = ["applicationTaskExecutor", "taskExecutor"])
    fun taskExecutor() : TaskExecutor = TaskExecutor {
        Executors.newThreadExecutor(
            Thread.builder().virtual().name("task-vt#", 0).factory()
        )
    }

    @Primary
    @Bean
    fun JettyServletWebServerFactory(): JettyServletWebServerFactory =
        object : JettyServletWebServerFactory() {
            override fun setThreadPool(threadPool: ThreadPool) {
                super.setThreadPool(object : ThreadPool {
                    private val executor = Executors.newThreadExecutor(Thread.builder().virtual().name("jetty-vt#", 0).factory())
                    override fun execute(command: Runnable) = executor.execute(command)
                    @Throws(InterruptedException::class)
                    override fun join() : Unit = with(executor) { shutdown(); awaitTermination(3, SECONDS) }
                    override fun getThreads() = -1
                    override fun getIdleThreads() = -1
                    override fun isLowOnThreads() = false
                })
            }
        }
}
