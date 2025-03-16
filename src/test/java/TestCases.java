import dataObjects.User;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.service.local.flags.ServerArgument;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;
import screens.CartScreen;
import screens.CheckoutScreen;
import screens.LoginScreen;
import screens.ProductsScreen;
import utils.DriverMethods;
import utils.MyCustomListener;
import utils.TestProperties;

import java.io.File;
import java.time.Duration;
import java.util.Map;

import static reports.ExtentTestManager.getTest;

@Listeners(MyCustomListener.class)
public class TestCases {

    public AppiumDriverLocalService server;
    public AndroidDriver driver;
    public WebDriverWait wait;

    public LoginScreen loginScreen;
    public ProductsScreen productsScreen;
    public CartScreen cartScreen;
    public CheckoutScreen checkoutScreen;

    @DataProvider(name = "valid-user-data")
    public static Object[][] getUserData() {
        return new Object[][]{
                {"standard_user", "secret_sauceXXXX"},
                {"standard_user", "secret_sauce"}
        };
    }

    @DataProvider(name = "valid-user-data3")
    public static Object[][] getUserData3() {
        return new Object[][]{
                {"standard_user", "secret_sauce"}
        };
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClassSetup() {
//        server = AppiumDriverLocalService.buildDefaultService();
        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder();
        appiumServiceBuilder.usingPort(4724);
        appiumServiceBuilder.withLogFile(new File("appium-server-logs.log"));
        appiumServiceBuilder.withArgument(GeneralServerFlag.RELAXED_SECURITY);
        server = appiumServiceBuilder.build();
        server.clearOutPutStreams();
        server.start();

//        URL appiumServerURL = new URL("http://0.0.0.0:4724");
        TestProperties.loadProperties();
        DesiredCapabilities desiredCapabilities = TestProperties.getDesiredCapabilities();

//        driver = new AndroidDriver(appiumServerURL, desiredCapabilities);
        driver = new AndroidDriver(server.getUrl(), desiredCapabilities);
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        DriverMethods.setDriver(driver);

        loginScreen = new LoginScreen(driver);
        productsScreen = new ProductsScreen(driver);
        cartScreen = new CartScreen(driver);
        checkoutScreen = new CheckoutScreen(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethodSetup() {
//        driver.activateApp("com.swaglabsmobileapp");
//        driver.getBatteryInfo();
//        driver.executeScript("mobile: activateApp", Map.ofEntries(
//                Map.entry("appId", "com.swaglabsmobileapp")
//        ));
        DriverMethods.activateApp();
    }

    @Parameters("username")
//    @Test(testName = "testOne", description = "Test without POM", groups = {"TC1", "regression"})
    @Test(groups = {"TC1", "regression", "smoke", "loginTest"})
    public void testOne(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(new AppiumBy.ByAccessibilityId("test-Login")));
        driver.findElement(new AppiumBy.ByAccessibilityId("test-Login"));
        driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"Username\")"))
                .sendKeys(username);
        driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"test-Password\"]"))
                .sendKeys("secret_sauce");
        driver.findElement(new AppiumBy.ByAccessibilityId("test-LOGIN")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"PRODUCTS\")")));

    }

    @Test(testName = "testTwo", description = "Makeover of testOne with POM")
    public void testTwo() {
        loginScreen.isDisplayed();
        loginScreen.loginUser("standard_user", "secret_sauce");
        getTest().info("User logged in ; Username: standard_user ; Password: secret_sauce");
        productsScreen.isDisplayed();
    }

    @Test(testName = "testThree",
            description = "Makeover of testTwo with Data Provider",
            dataProvider = "valid-user-data")
    public void testThree(String userName, String password) {
        Reporter.log("Username: " + userName + "; password: " + password);
        loginScreen.isDisplayed();
        loginScreen.loginUser(userName, password);
        productsScreen.isDisplayed();
    }

    @Test(testName = "testFour",
            description = "Makeover of testThree with Data Provider",
            dataProvider = "valid-user-data2",
            dataProviderClass = UserData.class)
    public void testFour(User user) {
        Reporter.log("username: " + user.getUserName() + "; password: " + user.getPassword());
        loginScreen.isDisplayed();
        loginScreen.loginUser(user.getUserName(), user.getPassword());
        productsScreen.isDisplayed();
//        DriverMethods.logScreenShot();
    }

    @Test(testName = "testFiveHomeWork",
            dataProvider = "valid-user-data3",
            description = "add products")
    public void testFiveHomeWork(String userName, String password) {
        Reporter.log("Test Five Homework");
        loginScreen.isDisplayed();
        loginScreen.loginUser(userName, password);
        productsScreen.isDisplayed();
        productsScreen.isCardEmpty();
        DriverMethods.logScreenShot();

        productsScreen.tapAllVisibleAddButtons();
        DriverMethods.logScreenShot();
        productsScreen.isCardNotEmpty();
        productsScreen.tapCartButton();
        DriverMethods.logScreenShot();
        cartScreen.isDisplayed();
        cartScreen.tapCheckoutButton();
        checkoutScreen.isInfoDisplayed();
        DriverMethods.logScreenShot();
        checkoutScreen.enterDetails("Martin", "Jones", "33607");
        checkoutScreen.tapContinueButton();
        checkoutScreen.isOverviewDisplayed();
        DriverMethods.logScreenShot();

        checkoutScreen.tapFinishButton();
        checkoutScreen.isCheckoutCompleteDisplayed();
        checkoutScreen.tapHomeButton();
        productsScreen.isDisplayed();
    }

    @Test(testName = "testFive", description = "purchase flow")
    public void testFive() {
        loginScreen.isDisplayed();
        loginScreen.loginUser("standard_user", "secret_sauce");
        productsScreen.isDisplayed();

        productsScreen.tapAllVisibleAddButtons();
        productsScreen.tapCartButton();

        cartScreen.isDisplayed();
        cartScreen.tapCheckoutButton();

        checkoutScreen.isInfoDisplayed();
        checkoutScreen.enterDetails("Joe", "Doe", "33607");
        checkoutScreen.tapContinueButton();
        checkoutScreen.isOverviewDisplayed();
        checkoutScreen.tapFinishButton();
        checkoutScreen.isCheckoutCompleteDisplayed();
        checkoutScreen.tapHomeButton();

        productsScreen.isDisplayed();
        productsScreen.isCardEmpty();

    }
/*
    @Test(testName = "testAssert")
    public void testAssert() {
        Assert.assertEquals(4, 5, "4 not equal to 5");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(4, 5, "soft assert that 4 != 5");
        softAssert.assertAll();
    }
*/
    @Test(testName = "tapOnCoord")
    public void testTapOnCoord() throws InterruptedException {
        loginScreen.isDisplayed();
        DriverMethods.tapOnCoordinates(430, 950);
        Thread.sleep(3000);
    }

    @Test(testName = "tapOnElem")
    public void testTapOnElem() throws InterruptedException {
        loginScreen.isDisplayed();
        DriverMethods.tapOnElem(driver.findElement(new AppiumBy.ByAccessibilityId("test-LOGIN")));
        Thread.sleep(3000);
    }

    @Test(testName = "swipeByCoord")
    public void testSwipeByCoord() throws InterruptedException {
        loginScreen.isDisplayed();
        loginScreen.loginStandardUser();
        productsScreen.isDisplayed();
        Dimension screenSize = driver.manage().window().getSize();
        int screenWid = screenSize.getWidth();
        int screenHei = screenSize.getHeight();
        // TODO
        DriverMethods.swipeByCoord(screenHei / 4, screenWid /4, screenWid, screenHei, "up", 0.75, 500);
        Thread.sleep(5000);
    }

    @Test(testName = "swipeOnElem")
    public void testSwipeOnElem() throws InterruptedException {
        loginScreen.isDisplayed();
        DriverMethods.swipeByElem(driver.findElement(new AppiumBy.ByAccessibilityId("test-LOGIN")),
                "up", 0.75, 500);
        Thread.sleep(3000);
    }

    @Test(testName = "openMenuBySwipe")
    public void testOpenMenuBySwipe() throws InterruptedException {
        Dimension screensize = driver.manage().window().getSize();
        int screenWid = screensize.getWidth();
        int screenHei = screensize.getHeight();
        int top = 60;
        int left = 60;
        loginScreen.isDisplayed();
        loginScreen.loginStandardUser();
        productsScreen.isDisplayed();
        DriverMethods.swipeByCoord(top, left, screenWid / 20, screenHei / 40, "up", 0.75, 500);
        Thread.sleep(3000);
    }

    @Test(testName = "dragByCoord")
    public void testDragByCoord() throws InterruptedException {
        loginScreen.isDisplayed();
        loginScreen.loginUser("standard_user", "secret_sauce");
        productsScreen.isDisplayed();
        DriverMethods.dragByCoord(600, 2000, 600, 20, 500);
        Thread.sleep(500);
    }

    @Test(testName = "dragElemToCoord")
    public void testDragElemToCoord() throws InterruptedException {
        loginScreen.isDisplayed();
        loginScreen.loginStandardUser();
        productsScreen.isDisplayed();
        DriverMethods.dragElemToCoord(driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-Drag Handle\"])[1]")),
                60, 60, 500);
        Thread.sleep(500);
    }

    @Test(testName = "tapOnCoordWithPointerInput")
    public void tapOnCoordWithPointerInput() throws InterruptedException {
        loginScreen.isDisplayed();
        DriverMethods.tapOnCoordWithPointerInput(430, 920);
        Thread.sleep(5000);
    }

    @Test(testName = "Get device info")
    public void getDeviceInfo() {
        System.out.println(DriverMethods.getDeviceInfo());
    }


//android.view.ViewGroup[following-sibling::android.view.ViewGroup[@content-desc="test-ABOUT"]and preceding-sibling::android.view.ViewGroup[@content-desc="test-Close"]]
//android.view.ViewGroup[preceding-sibling::android.view.ViewGroup[@content-desc="test-Close"]]/*[1]
//android.view.ViewGroup[preceding-sibling::android.view.ViewGroup[@content-desc="test-Close"]]
//android.view.ViewGroup[following-sibling::android.view.ViewGroup[@content-desc="test-ABOUT"]]
// new UiScrollable(new UiSelector().description("Carousel")).setAsHorizontalList().scrollForward()
// new UiScrollable(new UiSelector().description("test-Item")).setAsHorizontalList().scrollForward()


    @AfterMethod(alwaysRun = true)
    public void afterMethodCleanup() {
//        driver.terminateApp("com.swaglabsmobileapp");
        driver.executeScript("mobile: terminateApp", Map.ofEntries(
           Map.entry("appId", "com.swaglabsmobileapp"),
           Map.entry("timeout", 1000)
        ));
    }

    @AfterClass(alwaysRun = true)
    public void afterClassCleanup() {
        driver.quit();
        server.stop();
    }
}
