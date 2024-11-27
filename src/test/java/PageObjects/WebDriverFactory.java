package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
    public static WebDriver createDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
            case "yandex":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            default:
                throw new RuntimeException("Браузер не поддерживается: " + browserName);
        }
    }
}
