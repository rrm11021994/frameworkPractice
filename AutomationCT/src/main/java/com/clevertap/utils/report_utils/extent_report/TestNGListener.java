package com.clevertap.utils.report_utils.extent_report;
import com.clevertap.BaseTest;
import com.clevertap.utils.SeleniumUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.util.Objects;

import static com.clevertap.utils.SeleniumUtils.deleteOlderFiles;

public class TestNGListener extends TestListenerAdapter {

    public static CTReports report = new CTReports();

    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
        if (result.getThrowable() instanceof Exception) {
            Reporter.log("Test is skipped due to failed configuration" +result.getName(),true);
            report.startTest(result);
            report.getResult(result);
            String testClass = result.getTestClass().getName();
            testClass = testClass.substring(testClass.lastIndexOf(".")+1,testClass.length());
            String testCase=result.getName();

            if(Objects.nonNull(BaseTest.webDriverThreadSafe.get())){
                String base64 = SeleniumUtils.getScreenshot(BaseTest.webDriverThreadSafe.get(), testCase);
                CTReports.extenNodeMap.get(testClass+testCase).addScreenCaptureFromBase64String(base64);
            }

        } else if (result.getThrowable().getMessage().contains("depends on not successfully finished methods")) {
            Reporter.log("Test is skipped " + result.getName(), true);
            report.startTest(result);
            report.getResult(result);
        } else {
            Reporter.log("Test is skipped with exception " + result.getName(), true);
            report.startTest(result);
            report.getResult(result);
        }
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        deleteOlderFiles(5,"/output/reports/","/output/FailedTestsScreenshots/");
        report.getResult(tr);
        super.onTestFailure(tr);
        Reporter.log("Test Case Ended "+tr.getName(),true);
        String testClass = tr.getTestClass().getName();
        testClass = testClass.substring(testClass.lastIndexOf(".")+1,testClass.length());
        String testCase=tr.getName();

        if(Objects.nonNull(BaseTest.webDriverThreadSafe.get())){
            String base64 = SeleniumUtils.getScreenshot(BaseTest.webDriverThreadSafe.get(), tr.getName());
            CTReports.extenNodeMap.get(testClass+testCase).addScreenCaptureFromBase64String(base64);
        }
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        report.getResult(tr);
        super.onTestSuccess(tr);
        Reporter.log("All Steps of Test are completed", true);
        Reporter.log("Test Case Ended "+tr.getName(),true);
    }

    @Override()
    public void onTestStart(ITestResult tr) {
        report.startTest(tr);
        report.getResult(tr);
        Reporter.log("Test Case started " + tr.getName(), true);
        super.onTestStart(tr);
    }

    @Override
    public void onFinish(ITestContext context) {
        report.saveReport();
    }
}
