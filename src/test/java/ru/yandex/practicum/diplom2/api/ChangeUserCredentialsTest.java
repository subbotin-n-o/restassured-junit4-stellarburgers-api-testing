package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.yandex.practicum.diplom2.model.user.User;
import ru.yandex.practicum.diplom2.model.user.UserClient;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.diplom2.model.user.UserClient.*;
import static ru.yandex.practicum.diplom2.model.user.UserCredentials.*;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getUser;
import static ru.yandex.practicum.diplom2.model.user.UserType.VALID_USER;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
    }

    @Test
    @DisplayName("Change username with authorization")
    @Description("Expected response: StatusCode 200")
    public void a_changeUserNameAuthTest() {
        loginUser(from(user));
        response = updateUser(updateUserName(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);
    }

    @Test
    @DisplayName("Change user email with authorization")
    @Description("Expected response: StatusCode 200")
    public void b_changeUserEmailAuthTest() {
        loginUser(from(user));
        response = updateUser(updateUserEmail(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);
    }

    @Test
    @DisplayName("Change user email to duplicate with authorization")
    @Description("Expected response: StatusCode 403")
    public void c_changeUserEmailToDuplicateAuthTest() {
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
    @DisplayName("Change user password with authorization")
    @Description("Expected response: StatusCode 200")
    public void d_changeUserPasswordAuthTest() {
        loginUser(from(user));
        response = updateUser(updateUserEmail(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);
    }

    @Test
    @DisplayName("Change username unauthorized")
    @Description("Expected response: StatusCode 401")
    public void e_changeUserNameUnAuthTest() {

        response = updateUser(updateUserName(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(SHOULD_BE_AUTH, actualMessage);
        assertFalse(actualSuccess);
    }

    @Test
    @DisplayName("Change user email unauthorized")
    @Description("Expected response: StatusCode 401")
    public void f_changeUserEmailUnAuthTest() {

        response = updateUser(updateUserEmail(user), accessToken);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(SHOULD_BE_AUTH, actualMessage);
        assertFalse(actualSuccess);
    }

    @Test
    @DisplayName("Change user password unauthorized")
    @Description("Expected response: StatusCode 401")
    public void g_changeUserPasswordUnAuthTest() {

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
        if(accessToken != null) {
            deleteUser(accessToken);
        }
    }

}