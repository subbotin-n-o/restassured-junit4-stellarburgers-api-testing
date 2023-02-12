package ru.yandex.practicum.diplom2.model.user;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {

    public static User getRandomUser() {
        Faker faker = new Faker(new Locale("en"));
        User user = new User();

        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password(6, 10));
        user.setName(faker.name().firstName());

        return user;
    }
}