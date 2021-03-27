package com.clevertap.ui;//package org.CleverTap.tests;

//import org.CleverTap.pages.RealImpact;
//import org.CleverTap.utils.BaseTest;
//import org.CleverTap.utils.Mocha;
//import com.clevertap.utils.SeleniumUtils;
//import org.apache.log4j.Logger;
//import org.testng.Assert;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//
//public class RealImpactTest extends BaseTest {
//    public static final String PASSED = "Passed";
//    private ExtentTest test;
//    private ExtentTest parentTest;
//    private Logger logger;
//
//
//    @BeforeClass
//    public void initialize() {
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(true, "Real Impact", "", "");
//        parentTest = mocha.configureExtentReport("Real Impact Test Scenarios","");
//        logger = mocha.configureLogger();
//
//    }
//
//
//    public enum textList {
//        DAUMAU("Real Impact on Sticky Quotient (DAU/MAU)"),
//        RETENTION("Real Impact on Retention Cohort of App Launched"),
//        CONVERSION("Real Impact on Conversion App Launched"),
//        REVENUE("Real Impact on average Revenue per User"),
//        RFMSEGEMENT("Real Impact on RFM segments"),
//        RECENCY("Real Impact on Recency"),
//        FREQUENCY("Real Impact on Frequency");
//
//        String text;
//
//        textList (String s){
//            text=s;
//        }
//
//        public String toString() {
//            return this.text;
//        }
//    }
//
//    @Test(priority = 1,description = "Verify DAU/MAU sticky nav item to load")
//    public void verifyRealDAUMAU() throws InterruptedException {
//        logger.info("verifyRealDAUMAU Test Started");
//        test = ExtentTestManager.startTest("verifyRealDAUMAU", "Verify Real Impact feature");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
////        mocha.openLeftNavMenu(true,"Real Impact","","");
//        Thread.sleep(2000); // remove the hard wait and write a logic
//        logger.info("Successfully launched the Real Impact page'");
//        RealImpact obj=new RealImpact(driver);  //create a single object
//        obj.getSpeedometerForRealImp("Sticky Quotient (DAU/MAU)"); // test this method
//        String cardHeaderText=obj.getRealImpactCardHeaderText();
//        System.out.println("********ENUM*********"+textList.DAUMAU.toString());
//        Assert.assertTrue(cardHeaderText.contains(textList.DAUMAU.toString()),"");
//        test.log(LogStatus.PASS,PASSED);
//        ExtentTestManager.endTest();
//        logger.info("verifyRealDAUMAU Test Finished");
//    }
//
//    @Test(priority = 2,description = "verify Read access to Real Impact")
//    public void verifyRealRetention() {
//        logger.info("verifyRealRetention Test Started");
//        test = ExtentTestManager.startTest("verifyRealRetention", "");
//        parentTest.appendChild(test);
//        SeleniumUtils.scrollUp(driver);
//        RealImpact obj=new RealImpact(driver);
//        obj.getSpeedometerForRealImp("Retention");
//        String cardHeaderText=obj.getRealImpactCardHeaderText();
//        System.out.println("********ENUM*********"+textList.RETENTION.toString());
//        Assert.assertTrue(cardHeaderText.contains(textList.RETENTION.toString()),"");
//        test.log(LogStatus.PASS,PASSED);
//        ExtentTestManager.endTest();
//        logger.info("verifyRealRetention Test Finished");
//    }
//
//    @Test(priority = 3,description = "verify Read access to Real Impact")
//    public void verifyRealConversion() {
//        logger.info("verifyRealConversion Test Started");
//        test = ExtentTestManager.startTest("verifyRealConversion", "");
//        parentTest.appendChild(test);
//        SeleniumUtils.scrollUp(driver);
//        RealImpact obj=new RealImpact(driver);
//        obj.getSpeedometerForRealImp("Conversion");
//        String cardHeaderText=obj.getRealImpactCardHeaderText();
//        System.out.println("********ENUM*********"+textList.CONVERSION.toString());
//        Assert.assertTrue(cardHeaderText.contains(textList.CONVERSION.toString()),"");
//        test.log(LogStatus.PASS,PASSED);
//        ExtentTestManager.endTest();
//        logger.info("verifyRealConversion Test Finished");
//    }
//    @Test(priority = 4,description = "verify Read access to Real Impact")
//    public void verifyRealRevenue() {
//        logger.info("verifyRealRevenue Test Started");
//        test = ExtentTestManager.startTest("verifyRealRevenue", "");
//        parentTest.appendChild(test);
//        SeleniumUtils.scrollUp(driver);
//        RealImpact obj=new RealImpact(driver);
//        obj.getSpeedometerForRealImp("Revenue Per User");
//        String cardHeaderText=obj.getRealImpactCardHeaderText();
//        System.out.println("********ENUM*********"+textList.REVENUE.toString());
//        Assert.assertTrue(cardHeaderText.contains(textList.REVENUE.toString()),"");
//        test.log(LogStatus.PASS,PASSED);
//        ExtentTestManager.endTest();
//        logger.info("verifyRealRevenue Test Finished");
//    }
//
//    @Test(priority = 5,description = "verify Read access to Real Impact")
//    public void verifyRealRFMSegments() throws InterruptedException{
//        logger.info("verifyRealRetention Test Started");
//        test = ExtentTestManager.startTest("verifyRealRFM Segments", "");
//        parentTest.appendChild(test);
//        SeleniumUtils.scrollUp(driver);
//        RealImpact obj=new RealImpact(driver);
//        obj.getSpeedometerForRealImp("RFM Segments");
//        String cardHeaderText=obj.getRealImpactCardHeaderText();
//        System.out.println("********ENUM*********"+textList.RFMSEGEMENT.toString());
//        Assert.assertTrue(cardHeaderText.contains(textList.RFMSEGEMENT.toString()),"");
//        SeleniumUtils.scrollDown(driver);
//        Thread.sleep(2000);
//        String graphHeader = obj.getHeaderText();
//        Assert.assertTrue(graphHeader.contains("Change in the RFM segment size of Target group - In the last 30 days"), "");
//        SeleniumUtils.scrollUp(driver);
//        Thread.sleep(5000);
//        obj.getChartOfEachComponent("Cannot Lose Them");
//        String currentWindow=driver.getWindowHandle();
//        driver.switchTo().frame(0);
//        SeleniumUtils.elementHighlighter(driver,obj.getRFMItemsHeaderElement());
//        String rfmMainPageHeader=obj.getRFMItemsHeaderText();
//        System.out.println("************** "+rfmMainPageHeader);
//        Assert.assertTrue(rfmMainPageHeader.contains("Cannot Lose Them"));
////      driver.switchTo().defaultContent();
//        driver.switchTo().window(currentWindow);
//        Thread.sleep(2000);
//        obj.closeTheCurrentPage();
//        System.out.println("Text checked");
//        // obj.clickCancel();
//        test.log(LogStatus.PASS,PASSED);
//        ExtentTestManager.endTest();
//        logger.info("verifyRealRFM Segments Test Finished");
//    }
//    @Test(priority = 6,description = "verify Read access to Real Impact")
//    public void verifyRealRecency() {
//        logger.info("verifyRealRecency Test Started");
//        test = ExtentTestManager.startTest("verifyRealRecency", "");
//        parentTest.appendChild(test);
//        SeleniumUtils.scrollUp(driver);
//        RealImpact obj=new RealImpact(driver);
//        obj.getSpeedometerForRealImp("Recency");
//        String cardHeaderText=obj.getRealImpactCardHeaderText();
//        System.out.println("********ENUM*********"+textList.RECENCY.toString());
//        Assert.assertTrue(cardHeaderText.contains(textList.RECENCY.toString()),"");
//        test.log(LogStatus.PASS,PASSED);
//        ExtentTestManager.endTest();
//        logger.info("verifyRealRecencyTest Finished");
//    }
//
//    @Test(priority = 7,description = "verify Read access to Real Impact")
//    public void verifyRealFrequency() {
//        logger.info("verifyRealFrequency Test Started");
//        test = ExtentTestManager.startTest("verifyRealFrequency", "");
//        parentTest.appendChild(test);
//        SeleniumUtils.scrollUp(driver);
//        RealImpact obj=new RealImpact(driver);
//        obj.getSpeedometerForRealImp("Frequency");
//        String cardHeaderText=obj.getRealImpactCardHeaderText();
//        System.out.println("********ENUM*********"+textList.FREQUENCY.toString());
//        Assert.assertTrue(cardHeaderText.contains(textList.FREQUENCY.toString()),"");
//        test.log(LogStatus.PASS,PASSED);
//        ExtentTestManager.endTest();
//        logger.info("verifyRealFrequency Test Finished");
//    }
//
//
//
//
//    @AfterMethod
//
//    private void testResult(ITestResult result) throws Exception {
//        Mocha.setResult(result, test);
//
//    }
//
//
//    @AfterClass
//    private void tearDown() throws InterruptedException {
//        logger.info("closing the chrome broswer and driver");
//        driver.close();
//        driver.quit();
//    }
//
//}
//
