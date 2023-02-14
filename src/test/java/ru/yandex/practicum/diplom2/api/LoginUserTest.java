package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
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

public class LoginUserTest {

    private User user;
    private UserClient userClient;
    private ValidatableResponse response;

    private static final String INCORRECT = "email or password are incorrect";
    private static String accessToken;

    @Before
    public void setUp() {
        user = getUser(VALID_USER);
        userClient = new UserClient();

        accessToken = createUser(user)
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Success login User")
    @Description("Expected response: StatusCode 200")
    public void succesLoginUserTest() {
        response = loginUser(from(user));

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);

    }

    @Test
    @DisplayName("Login User not valid email")
    @Description("Expected response: StatusCode 401")
    public void loginUserNotValidEmailTest() {
        response = loginUser(changeUserEmail(user));

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(INCORRECT, actualMessage);
        assertFalse(actualSuccess);

    }

    @Test
    @DisplayName("Login User not valid password")
    @Description("Expected response: StatusCode 401")
    public void loginUserNotValidPasswordTest() {
        response = loginUser(changeUserPassword(user));

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(INCORRECT, actualMessage);
        assertFalse(actualSuccess);

    }

    @After
    public void clearDate() {
        deleteUser(new StringBuilder(accessToken)
                .substring(7))
                .statusCode(SC_ACCEPTED);
    }
}