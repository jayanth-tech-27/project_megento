package StepDef;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Pages.LoginPage;
import Pages.LogoutPage;
import Utils.ExtentReportManagement;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LogoutStepDef {
    private LoginPage login;
    private LogoutPage logout;
    private WebDriver driver;
    private WebDriverWait wait;

    public LogoutStepDef() {
        this.driver = Hooks.driver;  
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.login = new LoginPage(driver);  
        this.logout = new LogoutPage(driver);
    }

    @Given("User opens the browser")
    public void user_opens_the_browser() throws InterruptedException {
        System.out.println("Browser is opened successfully.");
        ExtentReportManagement.startTest("Login/logout");
        Thread.sleep(3000);
    }

    @And("User navigates to the URL {string}")
    public void user_navigates_to_the_application_URL(String url) {
        driver.get(url);
        ExtentReportManagement.logInfo("User navigated to the URL");
    }

    @When("User clicks the link {string}")
    public void user_clicks_the_link(String linkText) {
        WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signIn.click();
    }

    @And("User enters valid email {string} and password {string}")
    public void user_enters_valid_email_and_password(String email, String password) {
        login.email().sendKeys(email);
        login.password().sendKeys(password);
    }

    @And("User clicks the {string} button")
    public void user_clicks_the_button(String buttonText) {
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'" + buttonText + "')]")));
        signInButton.click();
    }

    @And("User clicks {string} from {string} page")
    public void user_clicks_logout(String logoutText, String page) {
        logout.logout();
    }

    @Then("User verifies that the session ends and the user is redirected to the homepage")
    public void user_verifies_that_the_session_ends() {
        wait.until(ExpectedConditions.urlToBe("https://magento.softwaretestingboard.com/"));
        String currentURL = driver.getCurrentUrl();
        ExtentReportManagement.logPass("Successfully redirected to home page");
        assert currentURL.equals("https://magento.softwaretestingboard.com/") : "Session did not end properly";
    }

    @And("User verifies the {string} is displayed")
    public void user_verifies_the_home_page_is_displayed(String pageTitle) {
        wait.until(ExpectedConditions.titleContains(pageTitle));
        assert driver.getTitle().contains(pageTitle) : "Home Page is not displayed.";
        ExtentReportManagement.endTest();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully.");
        }
    }
}
