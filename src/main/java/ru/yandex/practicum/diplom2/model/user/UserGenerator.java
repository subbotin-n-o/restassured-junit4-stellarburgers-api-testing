package ru.yandex.practicum.diplom2.model.user;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {

    private static String name;
    private static String email;
    private static String validPassword;
//    private final String notValidPassword;

    public UserGenerator() {
        Faker faker = new Faker(new Locale("en"));

        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        validPassword = faker.internet().password(6, 10);
//        notValidPassword = faker.internet().password(4,5);
    }

    public static User getRandomUser() {
        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(validPassword);

        return user;
    }

//    public String getName() {
//        return name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public String getValidPassword() {
//        return validPassword;
//    }
//
//    public String getNotValidPassword() {
//        return notValidPassword;
//    }
}