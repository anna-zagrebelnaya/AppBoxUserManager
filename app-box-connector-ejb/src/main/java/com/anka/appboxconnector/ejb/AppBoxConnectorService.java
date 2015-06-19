package com.anka.appboxconnector.ejb;

import com.anka.appboxconnector.model.AppBoxUser;

import javax.ejb.Local;

/**
 * This service provides access to <a href="https://app.box.com">Box</a> using a developer token
 *  for authentication into it. Please read <a href="https://developers.box.com/docs/">API docs</a>
 *  to know how to get a developer token.
 */
@Local
public interface AppBoxConnectorService {

    /**
     * Returns information about the user for whom specified auth token was generated.
     *
     * @param authToken  the auth token was generated on <a href="https://app.box.com">Box</a>
     * @return retrieved user
     * @throws com.anka.appboxconnector.exceptions.AppBoxConnectorException if token is invalid
     */
    AppBoxUser getCurrentUser(String authToken);

    /**
     * Returns information about the newly created user.
     *
     * @param user       information for creating new user
     * @param authToken  the auth token was generated on <a href="https://app.box.com">Box</a>
     * @return newly created user
     * @throws com.anka.appboxconnector.exceptions.AppBoxConnectorException if the fields are
     * invalid or token is invalid or token is generated for non-admin account.
     */
    AppBoxUser createUser(AppBoxUser user, String authToken);

    /**
     * Deletes a user in an enterprise account.
     *
     * @param id         id of user to delete
     * @param authToken  the auth token was generated on <a href="https://app.box.com">Box</a>
     * @throws com.anka.appboxconnector.exceptions.AppBoxConnectorException if token is invalid
     * or the user still has files in their account.
     */
    void deleteUser(String id, String authToken);
}
