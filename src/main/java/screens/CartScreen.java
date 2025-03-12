package screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartScreen {
    private AndroidDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"YOUR CART\"]")
    private WebElement cartTextView;

    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().description(\"test-CHECKOUT\"))")
    private WebElement checkoutButton;

    public CartScreen(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(5));
    }

    public void isDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(cartTextView));
    }

    public void tapCheckoutButton() {
//        wait.until(ExpectedConditions.visibilityOf(checkoutButton)).click();
        wait.until(ExpectedConditions.visibilityOf(checkoutButton)).click();
    }
}
