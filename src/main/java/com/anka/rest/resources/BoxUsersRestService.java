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
public class BoxUsersRestService implements IBoxUsersRestService {

    @Context
    private HttpHeaders headers;

    private String getAuthorizationToken() {
        try {
            return headers.getRequestHeader("Authorization").get(0);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @GET
    @Path("/get-current-user")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Override
    public Response getCurrentBoxUserInJSON() {
        String token = getAuthorizationToken();
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        BoxUser user;
        BoxAPIConnection api = new BoxAPIConnection(token);
        user = BoxUser.getCurrentUser(api);
        return Response.ok(new AppBoxUser(user.getInfo()))
                .build();
    }

    @POST
    @Path("/create-user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response createUser(AppBoxUser appBoxUser) {
        String token = getAuthorizationToken();
        if (token == null || token.isEmpty()) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .build();
        }
        BoxUser.Info info;
        BoxAPIConnection api = new BoxAPIConnection(token);
        info = BoxUser.createEnterpriseUser(api, appBoxUser.getLogin(), appBoxUser.getName());
        return Response.ok(info)
                .build();
    }

}
