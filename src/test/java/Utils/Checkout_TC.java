package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import BaseClass.BaseClass;
import Pages.LoginPage;
import Pages.CheckoutPlaceorderPage;

public class Checkout_TC {
    WebDriver driver;
    LoginPage loginPage;
    CheckoutPlaceorderPage checkoutPage;

    @BeforeClass
    public void setUp() {
    	
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://magento.softwaretestingboard.com/");
        driver.findElement(By.linkText("Sign In")).click();
        loginPage = new LoginPage(driver);
        checkoutPage = new CheckoutPlaceorderPage(driver);
    }

    @Test(priority = 1)
    public void readDataFromExcel() throws IOException, InterruptedException {
    	 ExtentReportManagement.startTest("checkout");
        FileInputStream excel = new FileInputStream("C:\\Users\\JAYANTH\\eclipse-workspace\\Cap_Stone\\src\\test\\resources\\Excel\\Luma_Project.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(excel);
        XSSFSheet sheet = workbook.getSheet("SignupPage");
//      valid data
        loginPage.email().clear();
        loginPage.email().sendKeys(sheet.getRow(4).getCell(0).getStringCellValue());
        Thread.sleep(2000);
        loginPage.password().clear();
        loginPage.password().sendKeys(sheet.getRow(5).getCell(0).getStringCellValue());
        Thread.sleep(2000);
        loginPage.signIn().click(); 
        ExtentReportManagement.logInfo("Login_Successfull WithExcel data");
        workbook.close();
        excel.close();
        ExtentReportManagement.logInfo("Data taken from excel");
    }
    @Test(priority = 2)
    public void testCheckoutProcess() throws InterruptedException, IOException {
    	ExtentReportManagement.startTest("Checkout page info");
       
        	Thread.sleep(5000);
            checkoutPage.openCart();
            checkoutPage.clickProceedToCheckout();
            checkoutPage.enterShippingDetails("jay", "kumar", "#1233 Main St", "New York", "New York", "10011", "United States", "1234430003");
            checkoutPage.clickShipHere();
//            checkoutPage.selectFreeShipping();
            Thread.sleep(3000);
            checkoutPage.buttonr();
            Thread.sleep(2000);
            checkoutPage.clickNext();
            Thread.sleep(3000);
            checkoutPage.placeOrder();
            Thread.sleep(3000);
      
        ExtentReportManagement.endTest();
        ExtentReportManagement.logPass("Extent report generated successfully.");


        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File("./screenshots/screenshot.png"));

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