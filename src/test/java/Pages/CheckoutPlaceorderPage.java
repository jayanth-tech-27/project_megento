package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPlaceorderPage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public CheckoutPlaceorderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);  
    }

    @FindBy(linkText="Sign In")
    private WebElement signInButton;

    @FindBy(css = "a.action.showcart")
    private WebElement cart;

    @FindBy(xpath="//button[contains(text(),'Proceed to Checkout')]")
    private WebElement proceedToCheckoutBtn;

    @FindBy(xpath="//button[@class='action action-show-popup']")
    private WebElement addressBtn;

    @FindBy(name="firstname")
    private WebElement firstName;

    @FindBy(name="lastname")
    private WebElement lastName;

    @FindBy(name="street[0]")
    private WebElement streetAddress;

    @FindBy(name="city")
    private WebElement city;

    @FindBy(name="region_id")
    private WebElement state;

    @FindBy(name="postcode")
    private WebElement zipCode;

    @FindBy(name="country_id")
    private WebElement country;

    @FindBy(name="telephone")
    private WebElement phone;

    @FindBy(xpath="//span[normalize-space()='Ship here']")
    private WebElement shipHereButton;

//    @FindBy(xpath="//td[contains(text(),'Free Shipping')]")
//    private WebElement freeShipping;
    @FindBy(xpath="//input[@class='radio']")
    private WebElement button;

    @FindBy(xpath="//button[@class='button action continue primary']")
    private WebElement nextButton;

    @FindBy(xpath="//button[@title='Place Order']")

    private WebElement placeOrderButton;

    @FindBy(css="a[class='order-number'] strong")
    private WebElement orderNumber;

    @FindBy(linkText="Sign Out")
    private WebElement logoutButton;

    // ✅ Open Cart
    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
    }

    // ✅ Click "Proceed to Checkout"
    public void clickProceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutBtn)).click();
    }

    // ✅ Fill Shipping Details
    public void enterShippingDetails(String fName, String lName, String address, String cityName, String stateName, String zip, String countryName, String phoneNum) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("checkout-loader")));
        wait.until(ExpectedConditions.elementToBeClickable(addressBtn)).click();
        firstName.clear();
        firstName.sendKeys(fName);
        lastName.clear();
        lastName.sendKeys(lName);
        streetAddress.sendKeys(address);
        city.sendKeys(cityName);
        selectFromDropdown(state, stateName);
        zipCode.sendKeys(zip);
        selectFromDropdown(country, countryName);

        // Scroll down after selecting the country
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500);");
        phone.sendKeys(phoneNum);


    }
    // ✅ Click "Ship Here"
    public void clickShipHere() {
        wait.until(ExpectedConditions.elementToBeClickable(shipHereButton)).click();
    }
//    price
    public void buttonr() {
    	wait.until(ExpectedConditions.elementToBeClickable(button)).click(); 
    	}

    // ✅ Click Next Button
    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
    }

    // ✅ Place Order
    public void placeOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
    }

    // ✅ Get Order Number
    public String getOrderNumber() {
        return wait.until(ExpectedConditions.visibilityOf(orderNumber)).getText();
    }

    // ✅ Logout
    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    // ✅ Helper: Select from Dropdown
    private void selectFromDropdown(WebElement element, String value) {
        new Select(element).selectByVisibleText(value);
    }
}
