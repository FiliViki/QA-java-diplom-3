import PageObjects.LoginPage;
import PageObjects.MainPage;
import PageObjects.ProfilePage;
import Api.*;
import Data.*;
import Generators.UserGenerators;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import PageObjects.WebDriverFactory;

public class ProfileTests {
    private LoginApi loginApi;
    private User user;
    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private String token;

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
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/account/profile"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", "https://stellarburgers.nomoreparties.site/account/profile", currentUrl);

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
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", "https://stellarburgers.nomoreparties.site/", currentUrl);
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
        wait.until(ExpectedConditions.urlToBe("https://stellarburgers.nomoreparties.site/"));

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("URL не соответствует ожидаемому", "https://stellarburgers.nomoreparties.site/", currentUrl);
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
