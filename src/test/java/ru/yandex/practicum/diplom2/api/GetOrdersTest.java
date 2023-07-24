package ru.yandex.practicum.diplom2.api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.yandex.practicum.diplom2.model.user.User;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import static ru.yandex.practicum.diplom2.model.order.OrderClient.createOder;
import static ru.yandex.practicum.diplom2.model.order.OrderClient.getListOrdersUser;
import static ru.yandex.practicum.diplom2.model.order.OrderGenerator.*;
import static ru.yandex.practicum.diplom2.model.user.UserClient.*;
import static ru.yandex.practicum.diplom2.model.user.UserCredentials.from;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getUser;
import static ru.yandex.practicum.diplom2.model.user.UserType.VALID_USER;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GetOrdersTest {

    private User user;
    private ValidatableResponse response;

    private static String accessToken;

    private static final String SHOULD_BE_AUTHORIZED = "You should be authorised";

    @Test
    @DisplayName("Get List Orders authorized User")
    @Description("Expected response: StatusCode 200")
    public void a_getListOrdersTest() {
        user = getUser(VALID_USER);

        accessToken = new StringBuilder(createUser(user)
                .extract()
                .path("accessToken"))
                .substring(7);

        loginUser(from(user));

        createOder(getListIngredients(), accessToken);

        response = getListOrdersUser(accessToken);

        int actualStatusCode = response.extract().statusCode();
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_OK, actualStatusCode);
        assertTrue(actualSuccess);

        deleteUser(accessToken);
    }

    @Test
    @DisplayName("Get List Orders unauthorized User")
    @Description("Expected response: StatusCode 401")
    public void c_getListOrdersTest() {

        response = getListOrdersUser();

        int actualStatusCode = response.extract().statusCode();
        String actualMessage = response.extract().path("message");
        boolean actualSuccess = response.extract().path("success");

        assertEquals(SC_UNAUTHORIZED, actualStatusCode);
        assertEquals(SHOULD_BE_AUTHORIZED, actualMessage);
        assertFalse(actualSuccess);

    }

}