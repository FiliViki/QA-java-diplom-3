package Generators;

import Data.User;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;


public class UserGenerators {
    public static Faker faker = new Faker();

    public static User createValidUser() {
        String email = faker.internet().emailAddress();
        String password = RandomStringUtils.randomAlphabetic(12);
        String name = faker.name().firstName();

        return new User(email, password, name);
    }
}