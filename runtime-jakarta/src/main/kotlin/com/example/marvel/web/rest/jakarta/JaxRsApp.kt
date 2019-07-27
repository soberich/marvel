package com.example.marvel.web.rest.jakarta

import javax.inject.Named
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@Named
@ApplicationPath("/api")
class JaxRsApp : Application()
