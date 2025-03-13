package screens;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutScreen {
    private AndroidDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"CHECKOUT: INFORMATION\"]")
    private WebElement checkoutInfoTextView;

    @AndroidFindBy(accessibility = "test-First Name")
    private WebElement firstNameField;

    @AndroidFindBy(accessibility = "test-Last Name")
    private WebElement lastNameField;

    @AndroidFindBy(accessibility = "test-Zip/Postal Code")
    private WebElement zipField;

    @AndroidFindBy(accessibility = "test-CONTINUE")
    private WebElement continueButton;

    //    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"CHECKOUT: OVERVIEW\"]")
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"CHECKOUT: OVERVIEW\")")
    private WebElement checkoutOverviewTextView;

    @AndroidFindBy(uiAutomator =
            "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().description(\"test-FINISH\"))")
    private WebElement finishButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"CHECKOUT: COMPLETE!\"]")
    private WebElement checkoutCompleteTextView;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"THANK YOU FOR YOU ORDER\"]")
    private WebElement thankYouMessage;

    @AndroidFindBy(accessibility = "test-BACK HOME")
    private WebElement backHomeButton;

    public CheckoutScreen(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(5));
    }

    public void isInfoDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(checkoutInfoTextView));
    }

    public void isOverviewDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(checkoutOverviewTextView));
    }

    public void isCheckoutCompleteDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(checkoutCompleteTextView));
        wait.until(ExpectedConditions.visibilityOf(thankYouMessage));
    }

    public void enterDetails(String firstName, String lastName, String zipCode) {
        wait.until(ExpectedConditions.visibilityOf(firstNameField)).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOf(lastNameField)).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOf(zipField)).sendKeys(zipCode);
    }

    public void tapContinueButton() {
        wait.until(ExpectedConditions.visibilityOf(continueButton)).click();
    }

    public void tapFinishButton() {
        wait.until(ExpectedConditions.visibilityOf(finishButton)).click();
    }

    public void tapHomeButton() {
        wait.until(ExpectedConditions.visibilityOf(backHomeButton)).click();
    }
}
