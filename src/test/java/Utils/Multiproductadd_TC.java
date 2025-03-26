package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import BaseClass.BaseClass;
import Pages.LoginPage;
import Pages.SearchandAddProduct;

public class Multiproductadd_TC extends BaseClass {
    private LoginPage login_page;
    private SearchandAddProduct SP;
    private WebDriverWait wait; // Explicit wait instance

    @BeforeTest
    public void openBrowser() {  
        ExtentReportManagement.startTest("MultiProduct Add to Cart");

        invokeBrowser("chrome");

        driver.get("https://magento.softwaretestingboard.com");
        driver.findElement(By.linkText("Sign In")).click();

        login_page = new LoginPage(driver);
        SP = new SearchandAddProduct(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Explicit Wait (10 seconds)

        ExtentReportManagement.logInfo("Browser launched and navigated to Magento website");
    }

    @Test(priority = 1)
    public void readDataFromExcel() throws IOException {
        ExtentReportManagement.startTest("Read Data from Excel");

        FileInputStream excel = new FileInputStream("C:\\Users\\JAYANTH\\eclipse-workspace\\Cap_Stone\\src\\test\\resources\\Excel\\Luma_Project.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(excel);
        XSSFSheet sheet = workbook.getSheet("SignupPage");

        // Login Process
        ExtentReportManagement.logInfo("Entering login details from Excel");
        
        wait.until(ExpectedConditions.visibilityOf(login_page.email())).sendKeys(sheet.getRow(4).getCell(0).getStringCellValue());
        wait.until(ExpectedConditions.visibilityOf(login_page.password())).sendKeys(sheet.getRow(5).getCell(0).getStringCellValue());
        wait.until(ExpectedConditions.elementToBeClickable(login_page.signIn())).click();

        ExtentReportManagement.logPass("User logged in successfully");

        workbook.close();
        excel.close();
    }

    @Test(priority = 2)
    public void searchAndAddMultipleProducts() throws InterruptedException {
        ExtentReportManagement.startTest("Search and Add Multiple Products");

        // **Add First Product - Men's Shorts**
        ExtentReportManagement.logInfo("Searching for 'mens shorts'");
        
        wait.until(ExpectedConditions.visibilityOf(SP.search())).sendKeys("mens shorts");
        wait.until(ExpectedConditions.elementToBeClickable(SP.search())).sendKeys(Keys.ARROW_DOWN);
        wait.until(ExpectedConditions.elementToBeClickable(SP.search())).sendKeys(Keys.ENTER);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        wait.until(ExpectedConditions.elementToBeClickable(SP.Short())).click();
        wait.until(ExpectedConditions.elementToBeClickable(SP.size())).click();
        wait.until(ExpectedConditions.elementToBeClickable(SP.color())).click();

        WebElement quantity = wait.until(ExpectedConditions.visibilityOf(SP.quantity()));
        quantity.clear();
        quantity.sendKeys("3");

        js.executeScript("window.scrollBy(0,200)");
        wait.until(ExpectedConditions.elementToBeClickable(SP.addCart())).click();
        Thread.sleep(2000);

        try {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOf(SP.product()));
            Assert.assertEquals(successMessage.getText(), "You added Lono Yoga Short to your shopping cart.");
            ExtentReportManagement.logPass("Successfully added 'Lono Yoga Short' to cart.");
        } catch (Exception e) {
            ExtentReportManagement.logFail("Failed to add 'Lono Yoga Short' to cart. Error: " + e.getMessage());
        }

        // **Add Second Product - Men's Jacket**
        ExtentReportManagement.logInfo("Searching for 'jackets for men'");

        wait.until(ExpectedConditions.visibilityOf(SP.search())).sendKeys("jackets for men");
        wait.until(ExpectedConditions.elementToBeClickable(SP.search())).sendKeys(Keys.ARROW_DOWN);
        wait.until(ExpectedConditions.elementToBeClickable(SP.search())).sendKeys(Keys.ENTER);

        js.executeScript("window.scrollBy(0,500)");

        wait.until(ExpectedConditions.elementToBeClickable(SP.jacket())).click();
        wait.until(ExpectedConditions.elementToBeClickable(SP.size_j())).click();
        wait.until(ExpectedConditions.elementToBeClickable(SP.color())).click();

        WebElement quantity_1 = wait.until(ExpectedConditions.visibilityOf(SP.quantity()));
        quantity_1.clear();
        quantity_1.sendKeys("3");

        js.executeScript("window.scrollBy(0,200)");
        wait.until(ExpectedConditions.elementToBeClickable(SP.addCart())).click();
        Thread.sleep(2000);

        try {
            WebElement successMessage2 = wait.until(ExpectedConditions.visibilityOf(SP.product()));
            Assert.assertEquals(successMessage2.getText(), "You added Jupiter All-Weather Trainer to your shopping cart.");
            ExtentReportManagement.logPass("Successfully added 'Jupiter All-Weather Trainer' to cart.");
        } catch (Exception e) {
            ExtentReportManagement.logFail("Failed to add 'Jupiter All-Weather Trainer' to cart. Error: " + e.getMessage());
        }

        // **Open Cart**
        wait.until(ExpectedConditions.elementToBeClickable(SP.cart())).click();
        ExtentReportManagement.logInfo("Opened shopping cart.");
    }

    @AfterTest
    public void closeReport() {
        ExtentReportManagement.logInfo("Closing browser...");
        if (driver != null) {
            driver.quit();
            driver = null;  // Ensure the session is reset properly
        }
        ExtentReportManagement.endTest();
        ExtentReportManagement.logPass("Extent report generated successfully.");
    }
}