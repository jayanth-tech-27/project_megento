package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogoutObjects {
    WebDriver driver;

    public LogoutObjects(WebDriver driver) {  // ✅ Fix: Pass driver in constructor
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public By accountDropdown = By.xpath("//button[@data-action='customer-menu-toggle']");
    public By logout = By.xpath("//a[contains(text(),'Sign Out')]");

    @FindBy(xpath = "//button[@data-action='customer-menu-toggle']")
    public WebElement accountDropdownElement;

    @FindBy(xpath = "//a[contains(text(),'Sign Out')]")
    public WebElement logoutElement;
}
