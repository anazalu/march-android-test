package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class MyCustomListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.log(result.getTestName());
        Reporter.log(result.getName());
        DriverMethods.logScreenShot();
    }
}
