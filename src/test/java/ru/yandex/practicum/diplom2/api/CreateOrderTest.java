package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.diplom2.model.user.User;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.diplom2.model.order.OrderClient.createOder;
import static ru.yandex.practicum.diplom2.model.order.OrderGenerator.getEmptyListIngredients;
import static ru.yandex.practicum.diplom2.model.order.OrderGenerator.getListIngredients;
import static ru.yandex.practicum.diplom2.model.user.UserClient.*;
import static ru.yandex.practicum.diplom2.model.user.UserCredentials.from;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getUser;
import static ru.yandex.practicum.diplom2.model.user.UserType.VALID_USER;

public class CreateOrderTest {

    private User user;
    private ValidatableResponse response;

    private static String accessToken;

    private static final String INGREDIENT_MUST_BE_PROVIDED = "Ingredient ids must be provided";

    @Test
    @DisplayName("Create an order with ingredients with authorization")
    @Description("Expected response: StatusCode 200")
    public void a_createOrderTest() {
        user = getUser(VALID_USER);

        accessToken = new StringBuilder(createUser(user)
                .extract()
                .path("accessToken"))
                .substring(7);

        loginUser(from(user));

        response = createOder(getListIngredients(), accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);

    }

    @Test
    @DisplayName("Create an order without ingredients with authorization")
    @Description("Expected response: StatusCode 400")
    public void b_createOrderTest() {
        user = getUser(VALID_USER);

        accessToken = new StringBuilder(createUser(user)
                .extract()
                .path("accessToken"))
                .substring(7);

        loginUser(from(user));

        response = createOder(getEmptyListIngredients(), accessToken);

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(INGREDIENT_MUST_BE_PROVIDED, actualMessage);
        assertFalse(actualSuccess);

    }

    @Test
    @DisplayName("Create an order with ingredients without authorization")
    @Description("Expected response: StatusCode 200")
    public void c_createOrderTest() {

        response = createOder(getListIngredients());

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);

    }

    @After
    public void clearDate() {
        if(accessToken != null) {
            deleteUser(accessToken)
                    .statusCode(SC_ACCEPTED);
        }
    }
}