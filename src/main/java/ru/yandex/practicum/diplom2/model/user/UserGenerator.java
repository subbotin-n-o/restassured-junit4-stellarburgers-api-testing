package ru.yandex.practicum.diplom2.model.user;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {

    public static User getUser(UserType userType) {
        Faker faker = new Faker(new Locale("en"));
        User user = new User();

        switch (userType) {
            case VALID_USER:
                user.setEmail(faker.internet().emailAddress());
                user.setPassword(faker.internet().password(6, 10));
                user.setName(faker.name().firstName());
                break;
            case NO_NAME_USER:
                user.setEmail(faker.internet().emailAddress());
                user.setPassword(faker.internet().password(6, 10));
                break;
            case NO_EMAIL_USER:
                user.setPassword(faker.internet().password(6, 10));
                user.setName(faker.name().firstName());
                break;
            case NO_PASSWORD_USER:
                user.setEmail(faker.internet().emailAddress());
                user.setName(faker.name().firstName());
                break;
        }
        return user;
    }

}