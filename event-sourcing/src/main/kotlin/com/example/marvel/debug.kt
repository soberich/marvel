package com.example.marvel

import com.example.marvel.sourcing.EventSourcing
import io.vertx.reactivex.core.Vertx
import org.slf4j.LoggerFactory

/**
 * Convenience method so you can run it in your IDE
 */
fun main() {

    val begin = System.currentTimeMillis()
    val vertx = Vertx.vertx()
    val log = LoggerFactory.getLogger("main")

    vertx.deployVerticle(EventSourcing().apply { httpPort = 8081; configFile = "./event-sourcing.env" }) {
        if (it.succeeded()) log.info("Application started in ${System.currentTimeMillis() - begin} ms")
        else it.cause().printStackTrace()
    }
}
