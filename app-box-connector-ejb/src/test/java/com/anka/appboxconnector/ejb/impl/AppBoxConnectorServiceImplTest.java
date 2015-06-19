package com.anka.appboxconnector.ejb.impl;

import com.anka.appboxconnector.ejb.AppBoxConnectorService;
import com.anka.appboxconnector.exceptions.AppBoxConnectorException;
import com.anka.appboxconnector.model.AppBoxUser;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxAPIException;
import com.box.sdk.BoxUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.interceptor.InvocationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BoxUser.class)
public class AppBoxConnectorServiceImplTest {

    private static final String TOKEN = "token";
    private static final String METHOD_NAME = "getCurrentUser";

    private static final String EXPECTED_USER_ID = "111";
    private static final String EXPECTED_USER_NAME = "John Doe";
    private static final String EXPECTED_USER_LOGIN = "john.doe@gmail.com";

    private static final String EXPECTED_NEW_USER_ID = "222";
    private static final String EXPECTED_NEW_USER_NAME = "Jane Roe";
    private static final String EXPECTED_NEW_USER_LOGIN = "jane.roe@gmail.com";

    @InjectMocks
    private final AppBoxConnectorService appBoxConnectorService = new AppBoxConnectorServiceImpl();

    @Mock BoxAPIConnection apiMock;
    @Mock BoxUser boxUserMock;
    @Mock BoxUser.Info infoMock;
    @Mock BoxUser.Info newInfoMock;
    @Mock InvocationContext invocationContext;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Before
    public void init() throws Exception {
        mockStatic(BoxUser.class);
    }

    @Test
    public void shouldGetCurrentUser() throws Exception {
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
        assertEquals("Wrong user received", actualUser, expectedUser);
    }

    @Test
    public void shouldCreateUser() throws Exception {
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
        assertEquals("Wrong user created", actualUser, expectedUser);
    }

    @Test
    public void shouldThrowException() throws Exception {
        expectedEx.expect(AppBoxConnectorException.class);
        expectedEx.expectMessage("Box API exception while executing " + METHOD_NAME);

        when(invocationContext.proceed()).thenThrow(new BoxAPIException(""));
        when(invocationContext.getMethod()).thenReturn(
                AppBoxConnectorService.class.getMethod(METHOD_NAME, String.class));

        invokeExceptionFilter(invocationContext);
    }

    private void invokeExceptionFilter(InvocationContext invocationContext)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method retrieveItems = AppBoxConnectorServiceImpl.class.getDeclaredMethod("exceptionFilter",
                InvocationContext.class);
        retrieveItems.invoke(appBoxConnectorService, invocationContext);
    }

}
