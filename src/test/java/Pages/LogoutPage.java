package Pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private LogoutObjects obj;

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.obj = new LogoutObjects(driver); 
    }

    public void logout() {
        try {
            Actions actions = new Actions(driver);
            
            // Wait until dropdown is visible and click
            wait.until(ExpectedConditions.visibilityOf(obj.accountDropdownElement));
            actions.moveToElement(obj.accountDropdownElement).click().perform();
            
            // Wait until logout button is visible and click
            wait.until(ExpectedConditions.visibilityOf(obj.logoutElement));
            actions.moveToElement(obj.logoutElement).click().perform();

            System.out.println(" User successfully logged out.");
        } catch (Exception e) {
            System.out.println(" Exception in logout: " + e.getMessage());
        }
    }
}
