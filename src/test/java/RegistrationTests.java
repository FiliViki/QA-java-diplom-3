import api.*;
import data.*;
import generators.UserGenerators;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import pageobjects.RegistrationPage;
import pageobjects.WebDriverFactory;

import static org.junit.Assert.assertTrue;

public class RegistrationTests extends BaseTest {
    private RegistrationPage registrationPage;
    private User user;
    private Credentials login;

    @Before
    public void setUp() {
        String browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);

        driver.manage().window().maximize();
        registrationPage = new RegistrationPage(driver);
        registrationPage.open();

        loginApi = new LoginApi();
        user = UserGenerators.createValidUser();
        login = new Credentials(user.getEmail(), user.getPassword());
    }

    @Description("Регистрация прошла успешно")
    @Test
    public void testSuccessfulRegistration() {
        registrationPage.register(user.getName(), user.getEmail(), user.getPassword());

        assertTrue("Пользоватлеь не заргистрирован", registrationPage.isSuccessfulRegistration());

        ValidatableResponse responseCreate = loginApi.loginUser(login);

        String bearerToken = responseCreate.extract().path("accessToken");
        token = bearerToken.substring(7);
    }

    @Description("Длина пароля должна быть не менее 6 символов")
    @Test
    public void testRegistrationWithShortPassword() {
        registrationPage.register("TestUser", "testuser@example.com", "pass");

        assertTrue("Короткий пароль", registrationPage.isPasswordErrorDisplayed());
    }
}