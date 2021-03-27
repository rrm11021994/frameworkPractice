package org.example;

import com.aventstack.extentreports.MediaEntityBuilder;
import lombok.SneakyThrows;
import org.example.utils.Utilities;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class customListeners extends BaseClass implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("System is started");
        test = extent.createTest(result.getName());

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extent.flush();
    }
    @Override
    public void onTestFailure(ITestResult result) {
        test.fail(MediaEntityBuilder.createScreenCaptureFromPath(Utilities.getScreenShot()).build());
        extent.flush();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("test cases is skipped since runMode is No "+result.getThrowable());
        extent.flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        extent.flush();
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        extent.flush();
    }


    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
