package Pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import BaseClass.BaseClass;
import Utils.ExtentReportManagement;

public class RegisterPage extends BaseClass {
    private String first_name, last_name, email, password, confirm_password, url, browser;
    private WebDriverWait wait;

    @BeforeTest(alwaysRun = true)
    public void setup() throws IOException {
        // Start Extent Report
        ExtentReportManagement.startTest("SignupPage");
        ExtentReportManagement.logInfo("Opening the Sign up process");

        // Load properties file
        FileInputStream fis = new FileInputStream("C:\\Users\\JAYANTH\\eclipse-workspace\\Cap_Stone\\src\\main\\java\\data\\SignupPage.properties");
        Properties prop = new Properties();
        prop.load(fis);

        first_name = prop.getProperty("first_name");
        last_name = prop.getProperty("last_name");
        email = prop.getProperty("email");
        password = prop.getProperty("password");
        confirm_password = prop.getProperty("confirm_password");
        url = prop.getProperty("url");
        browser = prop.getProperty("browser");

        fis.close();

        // Initialize WebDriver
        invokeBrowser(browser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(priority = 1)
    public void verifyLoadedData() {
        System.out.println("First Name: " + first_name);
        System.out.println("Last Name: " + last_name);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Confirm Password: " + confirm_password);
        System.out.println("URL: " + url);
        System.out.println("Browser: " + browser);
    }

    @Test(priority = 2, dependsOnMethods = "verifyLoadedData")
    public void login() throws IOException, InterruptedException {
        // Validate and Click "Create an Account"
        try {
            WebElement createAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Create an Account")));
            createAccountLink.click();
            ExtentReportManagement.logPass("Create an Account button clicked.");
        } catch (TimeoutException e) {
            ExtentReportManagement.logFail("Create an Account link is not available. Test cannot proceed.");
            return;
        }

        // Fill signup form with invalid data
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='input-text required-entry']"))).sendKeys(first_name);
        driver.findElement(By.id("lastname")).sendKeys(last_name);
        driver.findElement(By.id("email_address")).sendKeys(""); // Invalid email scenario
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("password-confirmation")).sendKeys(confirm_password);

        WebElement register = driver.findElement(By.xpath("//button[@class='action submit primary']"));
        register.click();

        // Verify error message for empty email
        try {
            WebElement emailError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_address-error")));
            Assert.assertEquals(emailError.getText(), "This is a required field.");
            ExtentReportManagement.logPass("Validation for empty email field passed.");
        } catch (Exception e) {
            ExtentReportManagement.logFail("Mismatch in actual and expected email error message: " + e);
        }

        // Retry with valid email
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_address"))).sendKeys(email);
        register.click();

        // Verify successful registration
        try {
            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@data-bind='html: $parent.prepareMessageForHtml(message.text)']")));
            Assert.assertEquals(successMsg.getText(), "Thank you for registering with Main Website Store.");
            ExtentReportManagement.logPass("Signup successful.");
        } catch (Exception e) {
            ExtentReportManagement.logFail("Signup failed. Error: " + e);
        }
    }

    @AfterTest(alwaysRun = true)
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            ExtentReportManagement.logInfo("Browser closed.");
            ExtentReportManagement.endTest();
        }
    }
}
