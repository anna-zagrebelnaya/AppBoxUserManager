package com.anka.rest.resources;

import com.anka.rest.model.AppBoxUser;
import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxUser;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class BoxUsersRestService {

    private static final String[] USER_FIELDS = {"name", "login"};

    @GET
    @Path("/get-current-user")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getCurrentBoxUserInJSON(@Context HttpHeaders headers) {
        String token;
        try {
            token = headers.getRequestHeader("Authorization").get(0);
        } catch (NullPointerException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        BoxAPIConnection api = new BoxAPIConnection(token); //TODO: add try catch
        BoxUser user = BoxUser.getCurrentUser(api);
        BoxUser.Info info = user.getInfo(USER_FIELDS);

        return Response.ok(new AppBoxUser(info)).build();
    }
}
