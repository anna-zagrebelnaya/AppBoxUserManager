package com.anka.rest.errorhandling;

import com.box.sdk.BoxAPIException;
import com.eclipsesource.json.JsonObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AppExceptionMapper implements ExceptionMapper<BoxAPIException> {
    @Override
    public Response toResponse(BoxAPIException e) {
        if (e.getResponse() == null) {
            return Response.status(e.getResponseCode())
                    .build();
        }
        JsonObject response = JsonObject.readFrom(e.getResponse());
        int status = response.get("status").asInt();
        String code = response.get("code").asString();
        String message = response.get("message").asString();

        return Response.status(e.getResponseCode())
                .entity(new ErrorMessage(status, code, message))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
