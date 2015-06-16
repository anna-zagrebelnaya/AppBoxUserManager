package com.anka.appboxconnector.model;

public class AppBoxUser {

    private String id;
    private String name;
    private String login;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public AppBoxUser() {
    }

    public AppBoxUser(String name, String login) {
        this.name = name;
        this.login = login;
    }

    public AppBoxUser(String id, String name, String login) {
        this(name, login);
        this.id = id;
    }
}
