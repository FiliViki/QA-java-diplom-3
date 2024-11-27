package PageObjects;

import Config.Environment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    private final WebDriver driver;
    private final By passwordError = By.xpath("//*[contains(@class,'input__error')]");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Переход на страницу регистрации")
    public void open() {
        driver.get(Environment.HOST + Environment.REGISTER);
    }

    @Step ("Поиск поля ввода имени")
    public WebElement getNameInput() {
        return driver.findElement(By.xpath("//input[@name='name']"));
    }

    @Step ("Поиск поля ввода электронной почты")
    public WebElement getEmailInput() {
        return driver.findElements(By.cssSelector("input.input__textfield")).get(1);
    }

    @Step ("Поиск поля ввода пароля")
    public WebElement getPasswordInput() {
        return driver.findElement(By.xpath("//input[@type='password']"));
    }

    @Step ("Поиск кнопки регистрации")
    public WebElement getRegisterButton() {
        return driver.findElement(By.cssSelector("button.button_button_type_primary__1O7Bx"));
    }

    @Step ("Регистрация пользователя")
    public void register(String name, String email, String password) {
        this.getNameInput().clear();
        this.getNameInput().sendKeys(name);

        this.getEmailInput().clear();
        this.getEmailInput().sendKeys(email);

        this.getPasswordInput().clear();
        this.getPasswordInput().sendKeys(password);

        this.getRegisterButton().click();
    }

    @Step ("Проверка успешной регистрации")
    public boolean isSuccessfulRegistration() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.urlToBe(Environment.HOST));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step ("Проверка отображения ошибки ввода пароля")
    public boolean isPasswordErrorDisplayed() {
        try {
            return driver.findElement(passwordError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step ("Переход на страницу входа")
    public void navigateToLogin() {
        By loginLink = By.xpath("//a[contains(text(),'Войти') or contains(@href, '/login')]");

        driver.findElement(loginLink).click();
    }
}