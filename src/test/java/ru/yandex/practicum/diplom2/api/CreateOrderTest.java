package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.diplom2.model.user.User;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.yandex.practicum.diplom2.model.order.OrderClient.createOder;
import static ru.yandex.practicum.diplom2.model.order.OrderGenerator.getListIngredients;
import static ru.yandex.practicum.diplom2.model.user.UserClient.*;
import static ru.yandex.practicum.diplom2.model.user.UserCredentials.from;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getUser;
import static ru.yandex.practicum.diplom2.model.user.UserType.VALID_USER;

public class CreateOrderTest {

    private User user;
    private ValidatableResponse response;

    private static String accessToken;

    @Before
    public void setUp() {
        user = getUser(VALID_USER);

        accessToken = new StringBuilder(createUser(user)
                .extract()
                .path("accessToken"))
                .substring(7);
    }

    @Test
    @DisplayName("Create an order with ingredients with authorization")
    @Description("Expected response: StatusCode 200")
    public void createOrderWithIngredientsAuthTest() {

        loginUser(from(user));

        response = createOder(getListIngredients(), accessToken);

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