package ru.yandex.practicum.diplom2.model.user;

import lombok.*;

import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getRandomEmail;
import static ru.yandex.practicum.diplom2.model.user.UserGenerator.getRandomPassword;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

    private String email;
    private String password;

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

    public static UserCredentials notValidUserEmail(User user) {
        user.setEmail(getRandomEmail());
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

    public static UserCredentials notValidUserPassword(User user) {
        user.setPassword(getRandomPassword());
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

}