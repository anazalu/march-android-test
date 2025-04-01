package screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductsScreen {
    private AndroidDriver driver;
    private WebDriverWait wait;

    //android.view.ViewGroup[following-sibling::android.view.ViewGroup[@content-desc="test-ABOUT"]and preceding-sibling::android.view.ViewGroup[@content-desc="test-Close"]]
//android.view.ViewGroup[preceding-sibling::android.view.ViewGroup[@content-desc="test-Close"]]/*[1]
//android.view.ViewGroup[preceding-sibling::android.view.ViewGroup[@content-desc="test-Close"]]
//android.view.ViewGroup[following-sibling::android.view.ViewGroup[@content-desc="test-ABOUT"]]
// new UiScrollable(new UiSelector().description("Carousel")).setAsHorizontalList().scrollForward()
// new UiScrollable(new UiSelector().description("test-Item")).setAsHorizontalList().scrollForward()



    //    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PRODUCTS\")")
    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"PRODUCTS\"]")
    private WebElement productsTextView;

    //    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"test-ADD TO CART\")")
//    new UiSelector().description("test-ADD TO CART")
//    @AndroidFindBy(accessibility = "test-ADD TO CART")
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Item\"]//android.view.ViewGroup[@clickable='true' and preceding-sibling::android.widget.TextView[@content-desc=\"test-Price\"]]")
    private List<WebElement> addToCardButtons;

    @AndroidFindBy(accessibility = "test-Cart")
    private WebElement cartButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[ancestor::android.view.ViewGroup[@content-desc=\"test-Cart\"]]")
    private WebElement cartBadgeTextView;

    public ProductsScreen(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(5));
    }

    public void isDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(productsTextView));
    }

    public void tapAllVisibleAddButtons() {
/*        AppiumBy.ByAndroidUIAutomator uiselector = new AppiumBy.ByAndroidUIAutomator("new UiSelector().description(\\\"test-ADD TO CART\\\")");
        driver.findElement(uiselector).click(); // selects first from the list
        driver.findElement(uiselector).click();
*/
        for (WebElement addButton : addToCardButtons) {
//            ExpectedConditions.refreshed();
            wait.until(ExpectedConditions.visibilityOf(addButton)).click();
        }
    }

    public void isCardEmpty() {
        wait.withTimeout(Duration.ofSeconds(1)).until(ExpectedConditions.invisibilityOf(cartBadgeTextView));
    }

    public void isCardNotEmpty() {
        wait.withTimeout(Duration.ofSeconds(1)).until(ExpectedConditions.visibilityOf(cartBadgeTextView));
    }

    public void tapCartButton() {
        wait.until(ExpectedConditions.visibilityOf(cartButton)).click();
    }
}
