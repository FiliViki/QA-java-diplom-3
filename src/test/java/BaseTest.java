import api.LoginApi;
import org.junit.After;
import org.openqa.selenium.WebDriver;

abstract public class BaseTest {
    public WebDriver driver;
    public String token;
    public LoginApi loginApi;

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
