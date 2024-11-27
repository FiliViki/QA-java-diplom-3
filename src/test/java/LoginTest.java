import Api.*;
import Data.*;
import Generators.UserGenerators;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import PageObjects.*;

import static org.junit.Assert.assertTrue;

public class LoginTest {
    private LoginApi loginApi;
    private User user;
    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private PasswordRecoveryPage passwordRecoveryPage;
    private RegistrationPage registrationPage;
    private ProfilePage profilePage;
    private String token;

    @Before
    public void setUp() {
        String browserName = System.getProperty("browser", "chrome");

        driver = WebDriverFactory.createDriver(browserName);

        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);
        registrationPage = new RegistrationPage(driver);
        profilePage = new ProfilePage(driver);

        driver.manage()
                .window()
                .maximize();

        loginApi = new LoginApi();
        user = UserGenerators.createValidUser();

        ValidatableResponse responseCreate = loginApi.createUser(user);

        String bearerToken = responseCreate.extract().path("accessToken");
        token = bearerToken.substring(7);

        loginApi.loginUser(Credentials.from(user));
    }

    @Description("Вход по кнопке «Войти в аккаунт» на главной")
    @Test
    public void testLoginFromMainPage() {
        mainPage.open();
        mainPage.navigateToLogin();

        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue("Неуспешный вход", loginPage.isLoginSuccessful());
    }

    @Description("Вход через кнопку в форме регистрации")
    @Test
    public void testLoginFromRegistrationPage() {
        registrationPage.open();
        registrationPage.navigateToLogin();

        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue("Неуспешный вход", loginPage.isLoginSuccessful());
    }

    @Description("Вход после смены пароля")
    @Test
    public void testLoginAfterPasswordRecovery() {
        passwordRecoveryPage.open();
        passwordRecoveryPage.navigateToLogin();

        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue("Неуспешный вход", loginPage.isLoginSuccessful());

    }

    @Test
    public void testLogout() {
        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getProfileLink()));

        mainPage.goToProfile();

        wait.until(ExpectedConditions.visibilityOfElementLocated(profilePage.getLogoutButttonElement()));
        wait.until(ExpectedConditions.elementToBeClickable(profilePage.getLogoutButton())).click();
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/login"));

        String currentUrl = driver.getCurrentUrl();

        Assert.assertEquals("https://stellarburgers.nomoreparties.site/login", currentUrl);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if(token != null){
            loginApi.deleteUser(token);
        }
    }
}
