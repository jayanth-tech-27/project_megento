package BaseClass;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {

    public static WebDriver driver= null;

    public static void invokeBrowser(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();  //Correctly initializing chrome driver
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver(); //Correctly initializing Firefox driver
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();  // Correctly initializing Edge driver
        } else {
            System.out.println("Not a valid browser!");
 
        }
        
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    public void takeScreenshot() {
        String fileName = "screenshots/screenshot_" + System.currentTimeMillis() + ".png"; // Fixed fileName
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(fileName));
            System.out.println("Screenshot saved: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }}

    public void handleAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert Message: " + alert.getText());
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert present: " + e.getMessage());
        }
    }}