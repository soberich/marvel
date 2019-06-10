package com.example.marvel.openapi;

//import com.example.marvel.web.JaxRsApp;
import io.swagger.v3.jaxrs2.Reader;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


public class ApplicationPathReader extends Reader {
    @Override
    protected String resolveApplicationPath() {
        return /*JaxRsApp*/Application.class.getAnnotation(ApplicationPath.class).value();
    }
}
