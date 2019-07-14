package com.example.marvel.web.rest.jakarta

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import javax.json.bind.Jsonb
import javax.json.bind.JsonbBuilder
import javax.json.bind.JsonbConfig
import javax.json.bind.config.PropertyVisibilityStrategy
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider

@Provider
@Produces(APPLICATION_JSON)
class JaxRsContextResolver : ContextResolver<Jsonb> {
    override fun getContext(type: Class<*>): Jsonb =
            JsonbBuilder.newBuilder().withConfig(JsonbConfig().withPropertyVisibilityStrategy(object : PropertyVisibilityStrategy {
                override fun isVisible(field: Field): Boolean = Modifier.isPublic(field.modifiers)
                override fun isVisible(method: Method): Boolean = Modifier.isPublic(method.modifiers)
            }).withNullValues(false)
            ).build()
}
