package com.clevertap.ui.ab_test;//package org.CleverTap.tests.ABTest;
//
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;
//import org.CleverTap.pages.ab_test_page.VisualEditorPage;
//import com.clevertap.utils.report_utils.extent_report.ExtentTestManager;
//import org.CleverTap.utils.BaseTest;
//import org.CleverTap.utils.Mocha;
//import org.CleverTap.utils.NavigateCTMenuEnums;
//import org.CleverTap.utils.TestCaseGroups;
//import org.apache.log4j.Logger;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//public class VisualEditorTest {
//
//    private static final String ANDROID = "android";
//    private static final String AUTOMATION_EXPERIMENT = "Automation Visual Editor Experiment";
//    private ExtentTest test;
//    private ExtentTest parentTest;
//    private Logger logger;
//    private WebDriver driver;
//
//    @BeforeClass(alwaysRun=true)
//    public void initialize(){
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(true, NavigateCTMenuEnums.Menu.AB_TEST.toString(), NavigateCTMenuEnums.Submenu.VISUAL_EDITOR.toString(), "");
//        BaseTest bt = new BaseTest();
//        driver= bt.getDriver();
//        parentTest = mocha.configureExtentReport("Test cases related to all actions on Event Page","");
//        logger = mocha.configureLogger();
//    }
//
//    @Test(groups = {TestCaseGroups.ALL,TestCaseGroups.AB_TEST},description = "Verify Header of the Page")
//    public void verifyHeader(){
//        logger.info(">>>verifyHeader");
//        test = ExtentTestManager.startTest("verifyHeader", "Verifying if header text is correct");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false, NavigateCTMenuEnums.Menu.AB_TEST.toString(), NavigateCTMenuEnums.Submenu.VISUAL_EDITOR.toString(), "");
//        VisualEditorPage visualEditor = new VisualEditorPage(driver);
//        Assert.assertEquals(visualEditor.getHeader(),"AB Tests/Visual Editor/");
//        test.log(LogStatus.PASS, "Header Verified successfully");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyHeader");
//    }
//
//    @Test(groups = {TestCaseGroups.ALL,TestCaseGroups.AB_TEST},description = "Verifying if save as draft works or not")
//    public void verifySaveAsDraft(){
//        logger.info(">>>verifySaveAsDraft");
//        test = ExtentTestManager.startTest("verifySaveAsDraft", "Verifying if save as draft works or not");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false, NavigateCTMenuEnums.Menu.AB_TEST.toString(), NavigateCTMenuEnums.Submenu.VISUAL_EDITOR.toString(), "");
//        VisualEditorPage visualEditor = new VisualEditorPage(driver);
//        visualEditor.createTest(true, ANDROID,"");
//        visualEditor.nameTheExperiment(AUTOMATION_EXPERIMENT);
//        visualEditor.saveAsDraft();
//        Assert.assertTrue(visualEditor.checkIfExperimentExist(AUTOMATION_EXPERIMENT));
//        test.log(LogStatus.PASS, "Save as Draft Verified successfully");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifySaveAsDraft");
//    }
//
//    @AfterMethod(alwaysRun=true)
//    private void testResult(ITestResult result) throws Exception {
//        Mocha.setResult(result, test);
//    }
//
//    @AfterClass(alwaysRun=true)
//    private void tearDown(){
//        logger.info("closing the chrome browser and driver");
//        driver.close();
//        driver.quit();
//    }
//}
