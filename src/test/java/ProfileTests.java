import pageobjects.LoginPage;
import pageobjects.MainPage;
import pageobjects.ProfilePage;
import api.*;
import data.*;
import generators.UserGenerators;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.WebDriverFactory;

public class ProfileTests extends BaseTest {
    private User user;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ProfilePage profilePage;

    static final String HOME_URL = "https://stellarburgers.nomoreparties.site/";
    static final String PROFILE_URL = "https://stellarburgers.nomoreparties.site/account/profile";

    @Before
    public void setUp() {
        String browserName = System.getProperty("browser", "chrome");
        driver = WebDriverFactory.createDriver(browserName);

        driver.manage().window().maximize();


        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        profilePage = new ProfilePage(driver);

        loginApi = new LoginApi();
        user = UserGenerators.createValidUser();

        ValidatableResponse responseCreate = loginApi.createUser(user);
        String bearerToken = responseCreate.extract().path("accessToken");
        token = bearerToken.substring(7);

        loginApi.loginUser(Credentials.from(user));
    }

    @Test
    public void goToProfileFromMainPage() {
        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getProfileLink()));

        mainPage.goToProfile();
        wait.until(ExpectedConditions.urlToBe(PROFILE_URL));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", PROFILE_URL, currentUrl);

    }

    @Test
    public void goToMainPageByLogoFromProfile() {
        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getProfileLink()));

        mainPage.goToProfile();

        wait.until(ExpectedConditions.visibilityOfElementLocated(profilePage.getLogo()));

        profilePage.clickLogo();
        wait.until(ExpectedConditions.urlToBe(HOME_URL));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", HOME_URL, currentUrl);
    }

    @Test
    public void goToMainPageByButtonFromProfile() {
        loginPage.open();
        loginPage.login(user.getEmail(), user.getPassword());

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(mainPage.getProfileLink()));

        mainPage.goToProfile();

        wait.until(ExpectedConditions.visibilityOfElementLocated(profilePage.getConstructorButton()));

        profilePage.goToMainByConstructorButton();
        wait.until(ExpectedConditions.urlToBe(HOME_URL));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", HOME_URL, currentUrl);
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
