package com.anka.appboxconnector.api.rest.services;

import com.anka.appboxconnector.model.AppBoxUser;

import javax.ws.rs.core.Response;

public interface BoxUsersRestService {

    Response getCurrentUser(String authToken);

    Response createUser(String authToken, AppBoxUser appBoxUser);

    Response deleteUser(String authToken, String id);
}
