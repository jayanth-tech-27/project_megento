package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	WebDriver driver ;
	
	
	public LoginPage(WebDriver driver){
		this.driver=driver;
		
		
	}

	By email=By.id("email");
	By password = By.id("pass");
	By signin=By.xpath("//button[@class='action login primary']");
	
	
	
//	user defined methods
	public WebElement email() {
		
		return driver.findElement(email);
	}
	

	public WebElement password() {
		
		
		return driver.findElement(password);
	}
	
	
    public WebElement signIn() {
        return driver.findElement(signin);
    }


	public boolean isLoginSuccessful() {
		// TODO Auto-generated method stub
		return false;
	}
    
}
