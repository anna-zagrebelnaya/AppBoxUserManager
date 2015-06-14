package com.anka.rest.model;

import com.box.sdk.BoxUser;

public class AppBoxUser {

    private String name;
    private String login;

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public AppBoxUser(BoxUser.Info userInfo) {
        name = userInfo.getName();
        login = userInfo.getLogin();
    }
}
