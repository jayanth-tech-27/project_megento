package StepDef;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
public class Hooks {
    protected static WebDriver driver;

    @Before
    public void setup() {
               driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

