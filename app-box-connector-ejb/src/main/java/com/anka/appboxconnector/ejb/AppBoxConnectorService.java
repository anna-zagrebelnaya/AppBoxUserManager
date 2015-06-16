package com.anka.appboxconnector.ejb;

import com.anka.appboxconnector.model.AppBoxUser;

import javax.ejb.Local;

@Local
public interface AppBoxConnectorService {

    AppBoxUser getCurrentUser(String authToken);

    AppBoxUser createUser(AppBoxUser user, String authToken);

    void deleteUser(String id, String authToken);
}
