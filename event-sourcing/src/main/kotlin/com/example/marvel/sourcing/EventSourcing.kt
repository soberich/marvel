@file:Suppress("NOTHING_TO_INLINE")
package com.example.marvel.sourcing

import io.reactiverse.kotlin.pgclient.pgPoolOptionsOf
import io.reactiverse.reactivex.pgclient.PgClient
import io.reactiverse.reactivex.pgclient.PgPool
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.config.configRetrieverOptionsOf
import io.vertx.kotlin.config.configStoreOptionsOf
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.reactivex.config.ConfigRetriever
import io.vertx.reactivex.core.Vertx
import mu.KLogging
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Named

/**
 * Right now we use PgPool directly. This is a limitation on `reactiverse` implementation.
 * Currently `reactiverse` was transferred to vertx under Eclipse and more generic `SqlClient` already available
 * @see `[this commit](`https://github.com/eclipse-vertx/vertx-sql-client/pull/318/commits/18eee1ed8ed32b6f6ee3a840232374f08763f25a)
 */
@Named
class EventSourcing /*: AbstractVerticle()*/ {

    internal var httpPort   = 8081
    internal var configFile = "event-sourcing.env"

    @set:
    [Inject]
    protected lateinit var vertx: Vertx

    @PostConstruct
    fun init() {
//        vertx = if (::vertx.isInitialized) vertx else Vertx.vertx()
        val envOptions =
                configStoreOptionsOf(jsonObjectOf("path" to configFile), "properties", null, "file")
        ConfigRetriever.create(vertx, configRetrieverOptionsOf(stores = listOf(envOptions)))
                .getConfig {
                    if (it.failed()) return@getConfig
                    val config = it.result()
                    logger.trace(config.encodePrettily()) // initVertx(vertx)
                    readModelDb  = config.toPgPoolFor("READ")
                    writeModelDb = config.toPgPoolFor("WRITE")
                }
    }

    private inline fun JsonObject.toPgPoolFor(purpose: String): PgPool =
            PgClient.pool(vertx, pgPoolOptionsOf(
                    port     = 5432,
                    host     = getString ("${purpose}_DATABASE_HOST"),
                    database = getString ("${purpose}_DATABASE_NAME"),
                    user     = getString ("${purpose}_DATABASE_USER"),
                    password = getString ("${purpose}_DATABASE_PASSWORD"),
                    maxSize  = getInteger("${purpose}_DATABASE_POOL_MAX_SIZE")
            ))

    companion object : KLogging() {
        internal lateinit var writeModelDb: PgPool
        internal lateinit var readModelDb : PgPool
    }
}
