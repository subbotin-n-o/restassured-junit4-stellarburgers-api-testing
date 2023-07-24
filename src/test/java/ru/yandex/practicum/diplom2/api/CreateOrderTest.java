package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.yandex.practicum.diplom2.model.user.User;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.diplom2.model.order.OrderClient.createOder;
import static ru.yandex.practicum.diplom2.model.order.OrderGenerator.*;
import static ru.yandex.practicum.diplom2.model.user.UserClient.*;
import static ru.yandex.practicum.diplom2.model.user.UserCredentials.from;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getUser;
import static ru.yandex.practicum.diplom2.model.user.UserType.VALID_USER;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateOrderTest {

    private User user;
    private ValidatableResponse response;

    private static String accessToken;

    private static final String INGREDIENT_MUST_BE_PROVIDED = "Ingredient ids must be provided";

    @Test
    @DisplayName("Create Order with ingredients and authorization")
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
    @DisplayName("Create Order no ingredients with authorization")
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
    @DisplayName("Create Order with ingredients no authorization")
    @Description("Expected response: StatusCode 200")
    public void c_createOrderTest() {

        response = createOder(getListIngredients());

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);

    }

    @Test
    @DisplayName("Create Order no ingredients no authorization")
    @Description("Expected response: StatusCode 400")
    public void d_createOrderTest() {

        response = createOder(getEmptyListIngredients());

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(INGREDIENT_MUST_BE_PROVIDED, actualMessage);
        assertFalse(actualSuccess);

    }

    @Test
    @DisplayName("Create Order with invalid hash of ingredients with authorization")
    @Description("Expected response: StatusCode 500")
    public void e_createOrderTest() {
        user = getUser(VALID_USER);

        accessToken = new StringBuilder(createUser(user)
                .extract()
                .path("accessToken"))
                .substring(7);

        loginUser(from(user));

        response = createOder(getListIngredientsInvalidHash());

        int actualStatusCode = response.extract().statusCode();

        assertEquals(SC_INTERNAL_SERVER_ERROR, actualStatusCode);

    }

    @Test
    @DisplayName("Create Order with invalid hash of ingredients no authorization")
    @Description("Expected response: StatusCode 500")
    public void f_createOrderTest() {

        response = createOder(getListIngredientsInvalidHash());

        int actualStatusCode = response.extract().statusCode();

        assertEquals(SC_INTERNAL_SERVER_ERROR, actualStatusCode);

    }

    @After
    public void clearDate() {
        if(accessToken != null) {
            deleteUser(accessToken);
        }
    }
}