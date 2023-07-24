package ru.yandex.practicum.diplom2.model.user;

import lombok.*;

import static ru.yandex.practicum.diplom2.model.user.UserGenerator.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentials {

    private String email;
    private String password;
    private String name;

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword(), null);
    }

    public static UserCredentials replaceUserEmail(User user) {
        user.setEmail(getRandomEmail());
        return new UserCredentials(user.getEmail(), user.getPassword(), null);
    }

    public static UserCredentials replaceUserPassword(User user) {
        user.setPassword(getRandomPassword());
        return new UserCredentials(user.getEmail(), user.getPassword(), null);
    }

    public static UserCredentials updateUserEmail(User user) {
        user.setEmail(getRandomEmail());
        return new UserCredentials(user.getEmail(), null, null);
    }

    public static UserCredentials updateUserPassword(User user) {
        user.setPassword(getRandomPassword());
        return new UserCredentials(null, user.getPassword(), null);
    }

    public static UserCredentials updateUserName(User user) {
        user.setName(getRandomName());
        return new UserCredentials(null, null, user.getName());
    }

    public static UserCredentials getUserEmail(User user) {
        return new UserCredentials(user.getEmail(), null, null);
    }

}