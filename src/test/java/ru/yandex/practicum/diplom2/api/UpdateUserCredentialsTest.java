package ru.yandex.practicum.diplom2.api;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.diplom2.model.user.User;
import ru.yandex.practicum.diplom2.model.user.UserClient;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.diplom2.model.user.UserClient.*;
import static ru.yandex.practicum.diplom2.model.user.UserCredentials.*;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getUser;
import static ru.yandex.practicum.diplom2.model.user.UserType.VALID_USER;

public class UpdateUserCredentialsTest {

    private User user;
    private UserClient userClient;
    private ValidatableResponse response;

    private static final String EMAIL_ALREADY_EXISTS = "User with such email already exists";
    private static final String SHOULD_BE_AUTH = "You should be authorised";
    private static String accessToken;

    @Before
    public void setUp() {
        user = getUser(VALID_USER);
        userClient = new UserClient();

        accessToken = new StringBuilder(createUser(user)
                .extract()
                .path("accessToken"))
                .substring(7);
    }

    @Test
    public void updateUserNameAuthorized() {
        loginUser(from(user));
        response = updateUser(updateUserName(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);
    }

    @Test
    public void updateUserEmailAuthorized() {
        loginUser(from(user));
        response = updateUser(updateUserEmail(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);
    }

    @Test
    public void updateUserEmailToDuplicateAuthorized() {
        loginUser(from(user));
        response = updateUser(getUserEmail(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_FORBIDDEN, actualStatusCode);
        assertEquals(EMAIL_ALREADY_EXISTS, actualMessage);
        assertFalse(actualSuccess);
    }

    @Test
    public void updateUserPasswordAuthorized() {
        loginUser(from(user));
        response = updateUser(updateUserEmail(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);
    }

    @Test
    public void updateUserNameUnauthorized() {

        response = updateUser(updateUserName(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(SHOULD_BE_AUTH, actualMessage);
        assertFalse(actualSuccess);
    }

    @Test
    public void updateUserEmailUnauthorized() {

        response = updateUser(updateUserEmail(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(SHOULD_BE_AUTH, actualMessage);
        assertFalse(actualSuccess);
    }

    @Test
    public void updateUserPasswordUnauthorized() {

        response = updateUser(updateUserPassword(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(SHOULD_BE_AUTH, actualMessage);
        assertFalse(actualSuccess);
    }

    @After
    public void clearDate() {
        deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }

}