package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import ru.yandex.practicum.diplom2.model.user.*;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.*;
import static ru.yandex.practicum.diplom2.model.user.UserType.*;

public class CreateUserTest {

    private UserClient userClient;
    private ValidatableResponse response;

    private static final String USER_EXISTS = "User already exists";
    public static final String REQUIRED_FIELDS = "Email, password and name are required fields";

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Success create User")
    @Description("Expected response: StatusCode 200")
    public void succesCreateUserTest() {
        response = userClient.createUser(getUser(VALID_USER));

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);

    }

    @Test
    @DisplayName("Create duplicate User")
    @Description("Expected response: StatusCode 403")
    public void createDuplicateUserTest() {
        User user = getUser(VALID_USER);
        userClient.createUser(user);

        response = userClient.createUser(user);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_FORBIDDEN, actualStatusCode);
        assertEquals(USER_EXISTS, actualMessage);
        assertFalse(actualSuccess);

    }

    @Test
    @DisplayName("Create User no name")
    @Description("Expected response: StatusCode 403")
    public void createUserNoNameTest() {
        response = userClient.createUser(getUser(NO_NAME_USER));

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_FORBIDDEN, actualStatusCode);
        assertEquals(REQUIRED_FIELDS, actualMessage);
        assertFalse(actualSuccess);

    }

    @Test
    @DisplayName("Create User no email")
    @Description("Expected response: StatusCode 403")
    public void createUserNoEmailTest() {
        response = userClient.createUser(getUser(NO_EMAIL_USER));

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_FORBIDDEN, actualStatusCode);
        assertEquals(REQUIRED_FIELDS, actualMessage);
        assertFalse(actualSuccess);

    }

    @Test
    @DisplayName("Create User no password")
    @Description("Expected response: StatusCode 403")
    public void createUserNoPasswordTest() {
        response = userClient.createUser(getUser(NO_PASSWORD_USER));

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_FORBIDDEN, actualStatusCode);
        assertEquals(REQUIRED_FIELDS, actualMessage);
        assertFalse(actualSuccess);

    }
}