package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.diplom2.model.user.User;
import ru.yandex.practicum.diplom2.model.user.UserClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getRandomUser;

public class CreateUserTest {

    private User user;
    private UserClient userClient;

    private ValidatableResponse response;

    @Before
    public void setUp() {
        user = getRandomUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Success create User")
    @Description("Expected response: StatusCode 200")
    public void succesCreateCourierTest() {
        response = userClient.createUser(user);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);

    }
}