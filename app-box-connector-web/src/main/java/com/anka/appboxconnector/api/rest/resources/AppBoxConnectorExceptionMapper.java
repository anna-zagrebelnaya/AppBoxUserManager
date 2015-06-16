package com.anka.appboxconnector.api.rest.resources;

import com.anka.appboxconnector.exceptions.AppBoxConnectorException;
import com.anka.appboxconnector.model.ErrorMessage;
import com.eclipsesource.json.JsonObject;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AppBoxConnectorExceptionMapper implements ExceptionMapper<AppBoxConnectorException> {
    @Override
    public Response toResponse(AppBoxConnectorException e) {
        ErrorMessage errorMessage;
        if (e.getResponse() == null) {
            errorMessage = new ErrorMessage(e.getResponseCode(), e.getMessage());
        } else {
            JsonObject response = JsonObject.readFrom(e.getResponse());
            int status = response.get("status").asInt();
            String code = response.get("code").asString();
            String message = response.get("message").asString();
            errorMessage = new ErrorMessage(status, code, message);
        }

        return Response.status(e.getResponseCode())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
