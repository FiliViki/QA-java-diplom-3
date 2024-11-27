import Api.*;
import Data.*;
import Generators.UserGenerators;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import PageObjects.RegistrationPage;
import PageObjects.WebDriverFactory;

import static org.junit.Assert.assertTrue;

public class RegistrationTests {
    private WebDriver driver;
    private RegistrationPage registrationPage;
    private String token;
    private User user;
    private Credentials login;
    private LoginApi authApi;

    @Before
    public void setUp() {
        String browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);

        driver.manage().window().maximize();
        registrationPage = new RegistrationPage(driver);
        registrationPage.open();

        authApi = new LoginApi();
        user = UserGenerators.createValidUser();
        login = new Credentials(user.getEmail(), user.getPassword());
    }

    @Description("Регистрация прошла успешно")
    @Test
    public void testSuccessfulRegistration() {
        registrationPage.register(user.getName(), user.getEmail(), user.getPassword());

        assertTrue("Пользоватлеь не заргистрирован", registrationPage.isSuccessfulRegistration());

        ValidatableResponse responseCreate = authApi.loginUser(login);

        String bearerToken = responseCreate.extract().path("accessToken");
        token = bearerToken.substring(7);
    }

    @Description("Длина пароля должна быть не менее 6 символов")
    @Test
    public void testRegistrationWithShortPassword() {
        registrationPage.register("TestUser", "testuser@example.com", "pass");

        assertTrue("Короткий пароль", registrationPage.isPasswordErrorDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        if(token != null){
            authApi.deleteUser(token);
        }
    }
}