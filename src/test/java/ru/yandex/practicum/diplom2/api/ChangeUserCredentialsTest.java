package ru.yandex.practicum.diplom2.api;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.diplom2.model.user.User;
import ru.yandex.practicum.diplom2.model.user.UserClient;
import ru.yandex.practicum.diplom2.model.user.UserCredentials;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.diplom2.model.user.UserClient.*;
import static ru.yandex.practicum.diplom2.model.user.UserCredentials.from;
import static ru.yandex.practicum.diplom2.model.user.UserCredentials.updateUserName;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getUser;
import static ru.yandex.practicum.diplom2.model.user.UserType.VALID_USER;

public class ChangeUserCredentialsTest {

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
        loginUser(from(user));
    }

    @Test
    public void updateUserNameWithAuth() {

        response = updateUser(updateUserName(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);
    }

    @After
    public void clearDate() {
        deleteUser(accessToken)
                .statusCode(SC_ACCEPTED);
    }

}