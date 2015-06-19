package com.anka.appboxconnector.api.rest.services;

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
public class BoxUsersRestService {
    private static final String ID = "id";
    @Context
    private HttpHeaders headers;

    @EJB
    private AppBoxConnectorService appBoxConnectorService;

    @GET
    @Path("/current")
    public Response getCurrentUser(@HeaderParam("Authorization") String authToken) {
        AppBoxUser user = appBoxConnectorService.getCurrentUser(authToken);
        return Response.ok(user).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@HeaderParam("Authorization") String authToken,
                                AppBoxUser appBoxUser) {
        AppBoxUser user = appBoxConnectorService.createUser(appBoxUser, authToken);
        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{" + ID + "}")
    public Response deleteUser(@HeaderParam("Authorization") String authToken,
                               @PathParam(ID) String id) {
        appBoxConnectorService.deleteUser(id, authToken);
        return Response.ok("User with id " + id + " is deleted").build();
    }
}
