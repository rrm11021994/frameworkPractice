package com.clevertap.utils.report_utils.listeners;


import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Retry implements IRetryAnalyzer {

    private int retryCount = 0;
    private int maxRetryCount = 0;  //no of time test will be retried once failed
    private static Logger logger = Logger.getLogger("Retry");

    // Below method returns 'true' if the test method has to be retried else 'false'
    //and it takes the 'Result' as parameter of the test method that just ran
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            logger.info("Retrying test " + result.getName() + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).");
            Reporter.log("Retrying test " + result.getName() + " with status "
                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount + 1) + " time(s).",true);
            retryCount++;
            result.setStatus(ITestResult.FAILURE);  //Mark test as failed

            /* Ensure this work and then commit */
            //extendReportsFailOperations(result);

            return true;
        }
        return false;
    }

    public String getResultStatusName(int status) {
        String resultName = null;
        if(status==1)
            resultName = "SUCCESS";
        if(status==2)
            resultName = "FAILURE";
        if(status==3)
            resultName = "SKIP";
        return resultName;
    }

//    public void extendReportsFailOperations (ITestResult iTestResult) {
//        Object testClass = iTestResult.getInstance();
//        WebDriver webDriver = BaseTest.getInstance().getDriver();
//        String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)webDriver).getScreenshotAs(OutputType.BASE64);
//        ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
//    }
}
