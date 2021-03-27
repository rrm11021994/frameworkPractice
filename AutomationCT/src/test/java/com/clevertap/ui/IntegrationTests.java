package com.clevertap.ui;//package org.CleverTap.tests;
//
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;
//import org.CleverTap.pages.Integration;
//import org.CleverTap.utils.BaseTest;
//import com.clevertap.utils.SeleniumUtils;
//import com.clevertap.utils.report_utils.extent_report.ExtentTestManager;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.Test;
//
//import java.io.FileInputStream;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//public class IntegrationTests {
//
//    private WebDriver driver;
//    private ExtentTest test;
//    private ExtentTest ParentTest;
//    private BaseTest baseTest_obj = new BaseTest();
//
//
//    private Properties prop = new Properties();
//
//    {
//        try {
//            FileInputStream fis = new FileInputStream("./config.properties");
//            prop.load(fis);
//
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test(priority = 1, description = "Launch cleverTap URL")
//    public void verifyCleverTapLaunch() {
////        test = ExtentTestManager.startTest("INTEGRATION TEST CASE","");
//        ParentTest = ExtentTestManager.startTest("INTEGRATION TEST CASE","");
//
//        test = ExtentTestManager.startTest("verifyLCleverTapLaunch", "Verifying the URL launch");
//        ParentTest.appendChild(test);
//        this.driver=baseTest_obj.setup();
////        this.driver = baseTest_obj.getDriver();
//        this.driver.get(prop.getProperty("URL"));
//        Assert.assertTrue(this.driver.findElement(By.xpath("//*[@id = 'submitBtn']")).isEnabled(), "CleverTap page is launched !!!!");
//        test.log(LogStatus.PASS, "Passed");
//
//        ExtentTestManager.endTest();
//
//    }
//
//
//    @Test(priority = 3, description = "verify Integration page")
//    public void verifyIntegrationPage() {
//        test = ExtentTestManager.startTest("verifyIntegrationPage", "verify Integration page");
//        ParentTest.appendChild(test);
//        Integration integration = new Integration(this.driver);
//        integration.launchIntegrationPage();
//        List<String> actualAppList = new ArrayList<>();
//        actualAppList.add("Android");
//        actualAppList.add("iOS");
//        actualAppList.add("Windows");
//        actualAppList.add("Website");
//        actualAppList.add("Magento");
//        actualAppList.add("Shopify");
//        actualAppList.add("WooCommerce");
//
//        List<String> expectedAppList = integration.getAppsFromDropdown();
//        System.out.println(actualAppList);
//        System.out.println(expectedAppList);
//        Assert.assertTrue(actualAppList.equals(expectedAppList), "All expected OS are present");
//        test.log(LogStatus.PASS, "All integration OS are correct");
//        ExtentTestManager.endTest();
//
//    }
//
//    @Test(priority = 4, description = "Verify push notification")
//    public void verifyTestPushNotification() throws InterruptedException {
//        test = ExtentTestManager.startTest("verifyTestPushNotification", "verify push notification with negative scenario");
//        ParentTest.appendChild(test);
//        Integration integration = new Integration(this.driver);
//        String pushNotificationResult = integration.testPushNotifiucation("AutomationPush", "AutomationBody", "");
//        Assert.assertTrue(pushNotificationResult.contains("Android: Error: No registration IDs/Profiles specified"), "Need to enter a valid registration ID");
//        test.log(LogStatus.PASS, "Appropriate message populated");
//        ExtentTestManager.endTest();
//
//
//    }
//    @Test(priority = 5,description = "checking individual app message")
//    public void verifyAppMessage(){
//        test = ExtentTestManager.startTest("verifyAppMessage", "Verify the text in the page");
//        ParentTest.appendChild(test);
//        Integration integration = new Integration(this.driver);
//        List status=integration.selectIndividualApp();
//        System.out.println(status);
//        // Assert.assertTrue(!status.contains(false),"test passed");
//        Assert.assertTrue(!status.contains(false),"Verified message");
//        test.log(LogStatus.PASS, "Verify Text");
//        ExtentTestManager.endTest();
//
//    }
//
//
//    @Test(priority = 6,description ="Send test push notification")
//    public void verifySendTestPush() throws InterruptedException {
//        test = ExtentTestManager.startTest("VerifySendTestPush", "verify push notification");
//        ParentTest.appendChild(test);
//        Integration integration = new Integration(this.driver);
//        integration.selectSpecificItemFromDropdown("iOS");
//        Thread.sleep(2000);
//        String pushNotificationResult = integration.sendTestPush("AutomationPush1", "AutomationBody2", "");
//        Assert.assertTrue(pushNotificationResult.contains("Error: No registration IDs/Profiles specified"), "Need to enter a valid registration ID");
//        test.log(LogStatus.PASS, "Appropriate message populated");
//        ExtentTestManager.endTest();
//    }
//
//    @Test(priority = 7,description="Send test push notification for Windows")
//    public void verifyWindows()throws InterruptedException {
//        test = ExtentTestManager.startTest("VerifyWindows", "verify windows notification");
//        ParentTest.appendChild(test);
//        Integration integration = new Integration(this.driver);
//        Thread.sleep(2000);
//        integration.selectWindowsFromDropdown("Windows");
//        Thread.sleep(2000);
//        String pushNotificationResult = integration.sendTestPushWindows("AutomationPush3", "AutomationBody3", "");
//        Assert.assertTrue(pushNotificationResult.contains("Error: No registration IDs/Profiles specified"), "Need to enter a valid registration ID");
//        test.log(LogStatus.PASS, "Appropriate message populated");
//        ExtentTestManager.endTest();
//    }
//
//
//
//    @AfterMethod
//    private  void getResult(ITestResult result) throws Exception {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            test.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
//            test.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
//            ParentTest.log(LogStatus.FAIL,"");
//            //To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
//            //We do pass the path captured by this method in to the extent reports using "test.addScreenCapture" method.
//            String screenshotPath = SeleniumUtils.getScreenshot(this.driver, result.getName());
//            //To add it in the extent report
//            test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
//        } else if (result.getStatus() == ITestResult.SKIP) {
//            test.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
//        }else if (result.getStatus()==ITestResult.SUCCESS){
//            ParentTest.log(LogStatus.PASS,"");
//        }
//        // ending test
//        //endTest(logger) : It ends the current test and prepares to create HTML report
//        ExtentTestManager.endTest();
//    }
//
//    @AfterClass
//    private void tearDown() {
//        this.driver.close();
//        this.driver.quit();
//    }
//}