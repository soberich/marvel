package com.example.marvel.openapi;

import com.example.marvel.web.rest.jakarta.JaxRsApp;
import io.swagger.v3.jaxrs2.Reader;

import javax.ws.rs.ApplicationPath;


public class ApplicationPathReader extends Reader {
    @Override
    protected String resolveApplicationPath() {
        return JaxRsApp.class.getAnnotation(ApplicationPath.class).value();
    }
}
