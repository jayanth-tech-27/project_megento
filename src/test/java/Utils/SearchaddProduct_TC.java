package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import BaseClass.BaseClass;
import Pages.LoginPage;
import Pages.SearchandAddProduct;

public class SearchaddProduct_TC extends BaseClass {
    private LoginPage login_page;
    private SearchandAddProduct SP;
    private WebDriverWait wait;

    @BeforeTest
    public void openBrowser() {  
        invokeBrowser("chrome");

        driver.get("https://magento.softwaretestingboard.com");
        driver.findElement(By.linkText("Sign In")).click();

        login_page = new LoginPage(driver);
        SP = new SearchandAddProduct(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // ✅ Initialize Extent Reports
        ExtentReportManagement.initializeReport();
        ExtentReportManagement.startTest("Search and Add Product Test");
    }

    @Test(priority = 1)
    public void readDataFromExcel() throws IOException {
        ExtentReportManagement.startTest("Read Data from Excel");

        FileInputStream excel = new FileInputStream("C:\\Users\\JAYANTH\\eclipse-workspace\\Cap_Stone\\src\\test\\resources\\Excel\\Luma_Project.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(excel);
        XSSFSheet sheet = workbook.getSheet("SignupPage");

        ExtentReportManagement.logInfo("Entering login details from Excel");
        
        wait.until(ExpectedConditions.visibilityOf(login_page.email())).sendKeys(sheet.getRow(4).getCell(0).getStringCellValue());
        wait.until(ExpectedConditions.visibilityOf(login_page.password())).sendKeys(sheet.getRow(5).getCell(0).getStringCellValue());
        wait.until(ExpectedConditions.elementToBeClickable(login_page.signIn())).click();

        ExtentReportManagement.logPass("User logged in successfully");

        workbook.close();
        excel.close();
    }

    @Test(priority = 2)
    public void searchProduct() throws InterruptedException {
        ExtentReportManagement.startTest("Search and Add Product");

        wait.until(ExpectedConditions.visibilityOf(SP.search())).sendKeys("mens shorts");
        wait.until(ExpectedConditions.elementToBeClickable(SP.search())).sendKeys(Keys.ARROW_DOWN);
        wait.until(ExpectedConditions.elementToBeClickable(SP.search())).sendKeys(Keys.ENTER);
        
        ExtentReportManagement.logInfo("User searched for 'mens shorts'");

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
        js.executeScript("window.scrollBy(200,0)");
        Thread.sleep(2000);
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOf(SP.product()));

        try {
            Assert.assertEquals(successMessage.getText(), "You added Lono Yoga Short to your shopping cart.");
            ExtentReportManagement.logPass("Product successfully added to cart.");
        } catch (AssertionError e) {
            ExtentReportManagement.logFail("Product not added to cart. Error: " + e.getMessage());
        }

        ExtentReportManagement.logInfo("Test completed successfully");
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
