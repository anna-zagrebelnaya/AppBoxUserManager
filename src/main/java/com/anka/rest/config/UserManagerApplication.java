package com.anka.rest.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

@ApplicationPath("/*")
public class UserManagerApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.anka.rest.errorhandling.AppExceptionMapper.class);
        resources.add(org.glassfish.jersey.jackson.JacksonFeature.class);
        resources.add(com.anka.rest.config.JacksonJsonProvider.class);
        return resources;
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();
        //in Jersey WADL generation is enabled by default, but we don't
        //want to expose too much information about our apis.
        //therefore we want to disable wadl (http://localhost:8080/service/application.wadl should return http 404)
        //see https://jersey.java.net/nonav/documentation/latest/user-guide.html#d0e9020 for details
        properties.put("jersey.config.server.wadl.disableWadl", true);
        properties.put("jersey.config.server.provider.packages", "com.anka.rest.resources");
        return properties;
    }

}
