package pageobjects;

import config.Environment;
import org.openqa.selenium.By;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;
    private final By loginLink = By.cssSelector("button.button_button__33qZ0.button_button_type_primary__1O7Bx.button_button_size_large__G21Vg");
    private final By profileLink = By.xpath("//a[@href='/account']");
    private final By bunTab = By.xpath("//span[text()='Булки']");
    private final By sauceTab = By.xpath("//span[text()='Соусы']");
    private final By fillingTab = By.xpath("//span[text()='Начинки']");
    private final By activeBunTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]/span[text()='Булки']");
    private final By activeSauceTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]/span[text()='Соусы']");
    private final By activeFillingTab = By.xpath("//div[contains(@class, 'tab_tab_type_current')]/span[text()='Начинки']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step ("Переход в профиль пользователя")
    public void goToProfile() {
        driver.findElement(profileLink).click();
    }

    public By getProfileLink() {
        return profileLink;
    }

    @Step("Переход на главную страницу")
    public void open() {
        driver.get(Environment.HOST);
    }

    @Step ("Переход на страницу входа")
    public void navigateToLogin() {
        driver.findElement(loginLink).click();
    }

    @Step ("Клик по вкладке 'Булки'")
    public void clickBunTab() {
        clickTab(bunTab);
    }

    @Step ("Клик по вкладке 'Соусы'")
    public void clickSauceTab() {
        clickTab(sauceTab);
    }

    @Step ("Клик по вкладке 'Начинки'")
    public void clickFillingTab() {
        clickTab(fillingTab);
    }

    @Step ("Клик по вкладке")
    private void clickTab(By tabLocator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(tabLocator));
        tab.click();
    }

    public boolean isBunTabActive() {
        return !driver.findElements(activeBunTab).isEmpty();
    }

    public boolean isSauceTabActive() {
        return !driver.findElements(activeSauceTab).isEmpty();
    }

    public boolean isFillingTabActive() {
        return !driver.findElements(activeFillingTab).isEmpty();
    }
}