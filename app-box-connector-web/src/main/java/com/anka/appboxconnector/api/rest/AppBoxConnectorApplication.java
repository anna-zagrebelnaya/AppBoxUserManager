package com.anka.appboxconnector.api.rest;

import com.anka.appboxconnector.api.rest.resources.AppBoxConnectorExceptionMapper;
import com.anka.appboxconnector.api.rest.resources.JacksonJsonProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

@ApplicationPath("/*")
public class AppBoxConnectorApplication extends Application {

    private static final String SERVICES_PACKAGE =
            "com.anka.appboxconnector.api.rest.services";

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(AppBoxConnectorExceptionMapper.class);
        resources.add(org.glassfish.jersey.jackson.JacksonFeature.class);
        resources.add(JacksonJsonProvider.class);
        return resources;
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.wadl.disableWadl", true);
        properties.put("jersey.config.server.provider.packages", SERVICES_PACKAGE);
        return properties;
    }

}
