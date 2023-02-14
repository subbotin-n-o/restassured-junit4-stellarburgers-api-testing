package ru.yandex.practicum.diplom2.model.user;

import com.github.javafaker.Faker;

import java.util.Locale;

public class UserGenerator {

    private static final Faker faker = new Faker(new Locale("en"));
    private static final User user = new User();

    public static User getUser(UserType userType) {

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

    public static String getRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String getRandomPassword() {
        return faker.internet().password(6, 10);
    }

    public static String getRandomName() {
        return faker.name().firstName();
    }

}