package Utils;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import BaseClass.BaseClass;
import Pages.LoginPage;

public class LoginExcelTC extends BaseClass {
    private LoginPage login_page;
    private Wait<WebDriver> wait;

    @BeforeMethod
    @Parameters({"browser"})
    public void openBrowser(@Optional("chrome") String browser) {
        System.out.println("Opening browser: " + browser);
        ExtentReportManagement.startTest("login_page");
            invokeBrowser(browser);
        driver.get("https://magento.softwaretestingboard.com");

        // Fluent Wait Initialization
        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);

        // Wait for "Sign In" link to be clickable and click
        WebElement signInLink = wait.until(d -> d.findElement(By.linkText("Sign In")));
        signInLink.click();

        login_page = new LoginPage(driver);
    }

    @Test
    public void readDataFromExcel() throws Exception {

        try (FileInputStream excel = new FileInputStream("C:\\Users\\JAYANTH\\eclipse-workspace\\Cap_Stone\\src\\test\\resources\\Excel\\Luma_Project.xlsx");
             XSSFWorkbook workbook = new XSSFWorkbook(excel)) {

            System.out.println("Reading data from Excel...");
            XSSFSheet sheet = workbook.getSheet("SignupPage");
            Thread.sleep(3000);

            // Invalid login attempt
            enterCredentials(sheet.getRow(2).getCell(0).getStringCellValue(), sheet.getRow(3).getCell(0).getStringCellValue());
            login_page.signIn().click();

            // Valid login attempt
            enterCredentials(sheet.getRow(0).getCell(0).getStringCellValue(), sheet.getRow(1).getCell(0).getStringCellValue());
            login_page.signIn().click();

            ExtentReportManagement.logInfo("Login successful with Excel data");

            // Verify successful login
            WebElement homePageElement = wait.until(d -> d.findElement(By.xpath("//span[text()='Home Page']")));
            Assert.assertEquals(homePageElement.getText(), "Home Page");

            takeScreenshot();
            System.out.println("Login test passed.");

        } catch (Exception e) {
            System.out.println("Error during login or reading Excel file: " + e.getMessage());
          throw e;
        }
    }

    private void enterCredentials(String email, String password) {
        WebElement emailField = wait.until(d -> d.findElement(By.id("email")));
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(d -> d.findElement(By.id("pass")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    @AfterTest
    public void closeReport() {
        ExtentReportManagement.logInfo("Closing browser...");
        driver.quit();
        ExtentReportManagement.endTest();
        ExtentReportManagement.logPass("Extent report generated successfully.");
    }
}
