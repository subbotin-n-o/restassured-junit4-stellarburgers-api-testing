package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import org.junit.runners.MethodSorters;
import ru.yandex.practicum.diplom2.model.user.*;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.diplom2.model.user.UserClient.createUser;
import static ru.yandex.practicum.diplom2.model.user.UserClient.deleteUser;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.*;
import static ru.yandex.practicum.diplom2.model.user.UserType.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateUserTest {

    private UserClient userClient;
    private ValidatableResponse response;

    private static String accessToken;

    private static final String USER_EXISTS = "User already exists";
    private static final String REQUIRED_FIELDS = "Email, password and name are required fields";

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Success create User")
    @Description("Expected response: StatusCode 200")
    public void a_succesCreateUserTest() {
        response = createUser(getUser(VALID_USER));

        accessToken = new StringBuilder(response
                .extract()
                .path("accessToken"))
                .substring(7);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);

    }

    @Test
    @DisplayName("Create duplicate User")
    @Description("Expected response: StatusCode 403")
    public void b_createDuplicateUserTest() {
        User user = getUser(VALID_USER);
        response = createUser(user);

        accessToken = new StringBuilder(response
                .extract()
                .path("accessToken"))
                .substring(7);

        response = createUser(user);

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
    public void c_createUserNoNameTest() {
        response = createUser(getUser(NO_NAME_USER));

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
    public void d_createUserNoEmailTest() {
        response = createUser(getUser(NO_EMAIL_USER));

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
    public void e_createUserNoPasswordTest() {
        response = createUser(getUser(NO_PASSWORD_USER));

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_FORBIDDEN, actualStatusCode);
        assertEquals(REQUIRED_FIELDS, actualMessage);
        assertFalse(actualSuccess);

    }

    @After
    public void clearDate() {
        if(accessToken != null) {
            deleteUser(accessToken);
        }
    }

}