package com.example.marvel.javaee

import io.vertx.core.Vertx
import io.vertx.core.eventbus.EventBus
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class EventSourcingConfiguration {

    @get:
    [Produces
    ApplicationScoped]
    val eventBus: EventBus = Vertx.vertx().eventBus()
}
