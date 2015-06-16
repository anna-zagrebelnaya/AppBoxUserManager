package com.anka.appboxconnector.api.rest.services.impl;

import com.anka.appboxconnector.api.rest.services.BoxUsersRestService;
import com.anka.appboxconnector.ejb.AppBoxConnectorService;
import com.anka.appboxconnector.model.AppBoxUser;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
public class BoxUsersRestServiceImpl implements BoxUsersRestService {
    private static final String ID = "id";
    @Context
    private HttpHeaders headers;

    @EJB
    private AppBoxConnectorService appBoxConnectorService;

    @GET
    @Path("/current")
    @Override
    public Response getCurrentUser(@HeaderParam("Authorization") String authToken) {
        AppBoxUser user = appBoxConnectorService.getCurrentUser(authToken);
        return Response.ok(user).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response createUser(@HeaderParam("Authorization") String authToken,
                                AppBoxUser appBoxUser) {
        AppBoxUser user = appBoxConnectorService.createUser(appBoxUser, authToken);
        return Response.ok(user).build();
    }

    @DELETE
    @Path("/delete/{" + ID + "}")
    @Override
    public Response deleteUser(@HeaderParam("Authorization") String authToken,
                               @PathParam(ID) String id) {
        appBoxConnectorService.deleteUser(id, authToken);
        return Response.ok("User with id " + id + " is deleted").build();
    }
}
