package com.anka.appboxconnector.ejb.impl;

import com.anka.appboxconnector.ejb.AppBoxConnectorService;
import com.anka.appboxconnector.model.AppBoxUser;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BoxUser.class)
public class AppBoxConnectorServiceImplTest {

    private static final String TOKEN = "token";

    private static final String EXPECTED_USER_ID = "111";
    private static final String EXPECTED_USER_NAME = "John Doe";
    private static final String EXPECTED_USER_LOGIN = "john.doe@gmail.com";

    private static final String EXPECTED_NEW_USER_ID = "222";
    private static final String EXPECTED_NEW_USER_NAME = "Jane Roe";
    private static final String EXPECTED_NEW_USER_LOGIN = "jane.roe@gmail.com";

    private final AppBoxConnectorService appBoxConnectorService = new AppBoxConnectorServiceImpl();

    BoxAPIConnection apiMock;
    BoxUser boxUserMock;
    BoxUser.Info infoMock;
    BoxUser.Info newInfoMock;

    @Before
    public void init() throws Exception {
        mockStatic(BoxUser.class);
        apiMock = mock(BoxAPIConnection.class);
        boxUserMock = mock(BoxUser.class);
        infoMock = mock(BoxUser.Info.class);
        newInfoMock = mock(BoxUser.Info.class);
    }

    @Test
    public void testGetCurrentUser() throws Exception {
        whenNew(BoxAPIConnection.class).withArguments(TOKEN)
                .thenReturn(apiMock);
        when(BoxUser.getCurrentUser(any(BoxAPIConnection.class)))
                .thenReturn(boxUserMock);
        when(boxUserMock.getInfo()).thenReturn(infoMock);
        when(infoMock.getID()).thenReturn(EXPECTED_USER_ID);
        when(infoMock.getName()).thenReturn(EXPECTED_USER_NAME);
        when(infoMock.getLogin()).thenReturn(EXPECTED_USER_LOGIN);

        AppBoxUser expectedUser = new AppBoxUser(
                EXPECTED_USER_ID,
                EXPECTED_USER_NAME,
                EXPECTED_USER_LOGIN);
        AppBoxUser actualUser = appBoxConnectorService.getCurrentUser(TOKEN);
        assertEquals(actualUser, expectedUser);
    }

    @Test
    public void testCreateUser() throws Exception {
        whenNew(BoxAPIConnection.class).withArguments(TOKEN)
                .thenReturn(apiMock);
        when(BoxUser.createEnterpriseUser(any(BoxAPIConnection.class), anyString(), anyString()))
                .thenReturn(newInfoMock);
        when(newInfoMock.getID()).thenReturn(EXPECTED_NEW_USER_ID);
        when(newInfoMock.getName()).thenReturn(EXPECTED_NEW_USER_NAME);
        when(newInfoMock.getLogin()).thenReturn(EXPECTED_NEW_USER_LOGIN);

        AppBoxUser expectedUser = new AppBoxUser(
                EXPECTED_NEW_USER_ID,
                EXPECTED_NEW_USER_NAME,
                EXPECTED_NEW_USER_LOGIN);
        AppBoxUser actualUser = appBoxConnectorService.createUser(expectedUser, TOKEN);
        assertEquals(actualUser, expectedUser);
    }

}
