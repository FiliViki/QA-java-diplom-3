import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import PageObjects.MainPage;
import PageObjects.WebDriverFactory;

import static org.junit.Assert.*;

public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        String browserName = System.getProperty("browser", "chrome");

        driver = WebDriverFactory.createDriver(browserName);
        driver.manage().window().maximize();

        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    public void testBunTabOpen() {
        assertTrue("Вкладка булок не активна", mainPage.isBunTabActive());
    }

    @Test
    public void testSauceTabOpen() {
        mainPage.clickSauceTab();

        new WebDriverWait(driver, 10);

        assertTrue("Вкладка соусов не активна", mainPage.isSauceTabActive());
    }

    @Test
    public void testFillingTabOpen() {
        mainPage.clickFillingTab();

        new WebDriverWait(driver, 10);

        assertTrue("Вкладка начинок не активна", mainPage.isFillingTabActive());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
