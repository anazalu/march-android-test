package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Reporter;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class DriverMethods {
    private static AndroidDriver driver;

    public static void setDriver(AndroidDriver driver) {
        DriverMethods.driver = driver;
    }

    public static void logScreenShot() {
        String src = driver.getScreenshotAs(OutputType.BASE64);
        String path = "<img src=\"data:image/png;base64, " + src + "\" width=\"*\" height=\"350\"";
        Reporter.log(path);
    }

    public static void tapOnCoordinates(int x, int y) {
        driver.executeScript("mobile: clickGesture", Map.ofEntries(
                        Map.entry("x", x),
                        Map.entry("y", y)
                ));

//                ImmutableMap.of(
//                "x", ((RemoteWebElement) element).getId()));
    }

    public static void tapOnElem(WebElement element) {
        driver.executeScript("mobile: clickGesture", Map.ofEntries(
                Map.entry("elementId", ((RemoteWebElement) element).getId())));

//                ImmutableMap.of(
//                "x", ((RemoteWebElement) element).getId()));
    }

    public static void swipeByCoord(int left, int top, int width,
                                    int height, String direction, Double percent, int speed) {
        driver.executeScript("mobile: swipeGesture", Map.ofEntries(
                Map.entry("left", left),
                Map.entry("top", top),
                Map.entry("width", width),
                Map.entry("height", height),
                Map.entry("direction", direction),
                Map.entry("percent", percent),
                Map.entry("speed", speed)
        ));
    }

    public static void swipeByElem(WebElement element, String direction, Double percent) {
        driver.executeScript("mobile: swipeGesture", Map.ofEntries(
                Map.entry("elementId", ((RemoteWebElement) element).getId()),
                Map.entry("direction", "up"),
                Map.entry("percent", 0.75),
                Map.entry("speed", 500)
        ));
    }

    public static void dragByCoord(int startX, int startY, int endX,
                                    int endY, int speed) {
        driver.executeScript("mobile: dragGesture", Map.ofEntries(
                Map.entry("startX", startX),
                Map.entry("startY", startY),
                Map.entry("endX", endX),
                Map.entry("endY", endY),
                Map.entry("speed", speed)
        ));
    }

    public static void dragElemToCoord(WebElement element, int endX,
                                   int endY, int speed) {
        driver.executeScript("mobile: dragGesture", Map.ofEntries(
                Map.entry("elementId", ((RemoteWebElement) element).getId()),
                Map.entry("endX", endX),
                Map.entry("endY", endY),
                Map.entry("speed", speed)
        ));
    }

    public static void tapOnCoordWithPointerInput(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
    }

}
