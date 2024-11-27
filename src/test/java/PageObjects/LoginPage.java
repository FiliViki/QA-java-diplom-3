package PageObjects;

import Config.Environment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;
    private final By emailInput = By.xpath("//input[@name='name']");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By loginButton = By.xpath("//button[text()='Войти']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    @Step ("Переход на страницу входа")
    public void open() {
        driver.get(Environment.HOST+Environment.ENDPOINT_LOGIN);
    }

    @Step ("Ввод почты")
    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step ("Ввод пароля")
    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step ("Клик по кнопке 'Войти'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step ("Авторизация")
    public void login(String email, String password) {
        this.enterEmail(email);
        this.enterPassword(password);
        this.clickLoginButton();
    }

    @Step ("Проверка успешного входа в систему")
    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains(Environment.HOST);
    }
}
