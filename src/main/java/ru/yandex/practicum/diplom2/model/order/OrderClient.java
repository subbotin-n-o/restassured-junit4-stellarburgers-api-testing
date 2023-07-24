package ru.yandex.practicum.diplom2.model.order;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.diplom2.Client;
import ru.yandex.practicum.diplom2.model.order.ingredients.Ingredients;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    private static final String PATH_GET_INGREDIENTS = "api/ingredients";
    private static final String PATH_CREATE_ORDER = "api/orders";
    private static final String PATH_GET_LIST_ORDERS = "api/orders";

    public static Ingredients getIngredients() {
        return given()
                .spec(getSpec())
                .get(PATH_GET_INGREDIENTS)
                .body().as(Ingredients.class);
    }

    public static ValidatableResponse createOder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH_CREATE_ORDER)
                .then();
    }

    public static ValidatableResponse createOder(Order order, String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .body(order)
                .when()
                .post(PATH_CREATE_ORDER)
                .then();
    }

    public static ValidatableResponse getListOrdersUser(String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .get(PATH_GET_LIST_ORDERS)
                .then();
    }

    public static ValidatableResponse getListOrdersUser() {
        return given()
                .spec(getSpec())
                .get(PATH_GET_LIST_ORDERS)
                .then();
    }

}