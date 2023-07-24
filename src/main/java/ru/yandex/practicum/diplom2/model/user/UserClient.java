package ru.yandex.practicum.diplom2.model.user;

import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.diplom2.Client;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;

public class UserClient extends Client {

    private static final String PATH_REGISTER = "api/auth/register";
    private static final String PATH_LOGIN = "api/auth/login";
    private static final String PATH_UPDATE = "api/auth/user";
    private static final String PATH_DELETE = "api/auth/user";

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

    public static ValidatableResponse updateUser(UserCredentials credentials, String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .body(credentials)
                .when()
                .patch(PATH_UPDATE)
                .then();
    }

    public static ValidatableResponse deleteUser(String token) {
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .delete(PATH_DELETE)
                .then();
    }

}