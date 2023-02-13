package ru.yandex.practicum.diplom2.model.user;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.diplom2.Client;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    private static final String PATH_REGISTER = "api/auth/register";
    private static final String PATH_LOGIN = "api/auth/login";

    public static ValidatableResponse createUser(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH_REGISTER)
                .then();
    }

    public static ValidatableResponse loginUser(UserCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

}