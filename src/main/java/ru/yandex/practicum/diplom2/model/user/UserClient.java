package ru.yandex.practicum.diplom2.model.user;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.diplom2.Client;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    private static final String PATH_CREATE = "api/auth/register";

    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(PATH_CREATE)
                .then();
    }

}