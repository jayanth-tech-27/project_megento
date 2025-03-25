package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchandAddProduct {
	WebDriver driver;
	public SearchandAddProduct(WebDriver driver) {
		this.driver=driver;
		
	}
     By email=By.id("email");
	
	By password = By.id("pass");
	By signin=By.xpath("//button[@class='action login primary']");
	By search=By.id("search");
	By Short=By.linkText("Lono Yoga Short");
	By size=By.id("option-label-size-143-item-177");
	By color=By.id("option-label-color-93-item-50");
	By quantity=By.id("qty");
	By addCart=By.id("product-addtocart-button");
	By jacket=By.linkText("Jupiter All-Weather Trainer");
	By size_j=By.id("option-label-size-143-item-170");
	By cart=By.xpath("//a[@class='action showcart']");
	By product=By.cssSelector(".message-success");
	By product_1=By.xpath("//[@data-bind='html: $parent.prepareMessageForHtml(message.text)']");
	By amount=By.xpath("//span[@class='price']");


//	user defined methods
	public WebElement search() {
		return driver.findElement(search);
		
	}
	public WebElement Short() {
		return driver.findElement(Short);
	}
	public WebElement size() {
		return driver.findElement(size);
	}
	public WebElement color() {
		return driver.findElement(color);
	}
	public WebElement quantity() {
		return driver.findElement(quantity);
	}
	public WebElement addCart() {
		return driver.findElement(addCart);
	}
	public WebElement jacket() {
		return driver.findElement(jacket);
	}
	public WebElement size_j() {
		return driver.findElement(size_j);
	}
	public WebElement cart() {
		return driver.findElement(cart);
	}
	public WebElement  product() {
		return driver.findElement(product);
				
	}
	public WebElement amount() {	
		return driver.findElement(amount);
				
	}
		
}
