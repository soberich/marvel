package com.example.marvel.web.rest.jakarta

import com.fasterxml.jackson.databind.ObjectMapper
import javax.inject.Inject
import javax.inject.Named
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider

@Named
@Provider
@Produces(APPLICATION_JSON)
class JaxRsJacksonContextResolver @Inject constructor(private val mapper: ObjectMapper) : ContextResolver<ObjectMapper> {
    override fun getContext(type: Class<*>?): ObjectMapper = mapper
}
