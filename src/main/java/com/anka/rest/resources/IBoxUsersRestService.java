package com.anka.rest.resources;

import com.anka.rest.model.AppBoxUser;

import javax.ws.rs.core.Response;

public interface IBoxUsersRestService {
    Response getCurrentBoxUserInJSON();
    Response createUser(AppBoxUser appBoxUser);
}
