package com.anka.appboxconnector.ejb.impl;

import com.anka.appboxconnector.ejb.AppBoxConnectorService;
import com.anka.appboxconnector.exceptions.AppBoxConnectorException;
import com.anka.appboxconnector.model.AppBoxUser;
import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxAPIException;
import com.box.sdk.BoxUser;

import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

@Stateless
public class AppBoxConnectorServiceImpl implements AppBoxConnectorService {

    @Override
    public AppBoxUser getCurrentUser(String authToken) throws AppBoxConnectorException {
        BoxAPIConnection api = new BoxAPIConnection(authToken);
        BoxUser.Info info = BoxUser.getCurrentUser(api).getInfo();
        return new AppBoxUser(info.getID(), info.getName(), info.getLogin());
    }

    @Override
    public AppBoxUser createUser(AppBoxUser user, String authToken) {
        BoxAPIConnection api = new BoxAPIConnection(authToken);
        BoxUser.Info info = BoxUser.createEnterpriseUser(api, user.getLogin(), user.getName());
        return new AppBoxUser(info.getID(), info.getName(), info.getLogin());
    }

    @Override
    public void deleteUser(String id, String authToken) {
        BoxAPIConnection api = new BoxAPIConnection(authToken);
        BoxUser user = new BoxUser(api, id);
        user.delete(false, false);
    }

    @AroundInvoke
    protected Object exceptionFilter(InvocationContext ctx) throws Exception {
        try {
            return ctx.proceed();
        } catch (BoxAPIException ex) {
            throw new AppBoxConnectorException(
                    ex.getResponseCode(),
                    ex.getResponse(),
                    "Box API exception while executing " + ctx.getMethod().getName());
        }
    }
}
