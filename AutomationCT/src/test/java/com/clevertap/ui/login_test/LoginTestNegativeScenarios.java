package com.clevertap.ui.login_test;//package org.CleverTap.tests.LoginTest;
//
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;
//import org.CleverTap.pages.login_page.LoginPageNegativeScenarios;
//import org.CleverTap.pages.widget.SweetAlert;
//import com.clevertap.utils.report_utils.extent_report.ExtentTestManager;
//import org.CleverTap.utils.BaseTest;
//import com.clevertap.utils.SeleniumUtils;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//public class LoginTestNegativeScenarios {
//
//
//    private static final String LOGIN_SECURITY_CHECK = "Login_Security_Check";
//    private static final String LOGIN_SECURITY_NEGATIVE_SCENARIOS = "Login Security/Negative Scenarios";
//    private static ExtentTest test;
//    private static ExtentTest parentTest;
//    private static Logger logger;
//    private WebDriver driver;
//
//    /*CleverTap0104*/
//
//    @BeforeClass
//    public void initialize() {
//        BaseTest baseTest=BaseTest.getInstance();
//        driver=baseTest.getDriver();
////        parentTest=ExtentTestManager.startTest(LOGIN_SECURITY_CHECK, LOGIN_SECURITY_NEGATIVE_SCENARIOS);
//        PropertyConfigurator.configure("log4j.properties");
//        //parentTest=baseTest.configureExtentReport(getClass());
//        logger = baseTest.configureLogger(getClass());
//
//    }
//
//    @Test(priority = 1,description = "Try to login with Empty password")
//    public void verifyLoginWithEmptyPassword() throws InterruptedException{
//        logger.info("******verifyLoginWithEmptyPassword Started******");
//        test = ExtentTestManager.startTest(getClass().getSimpleName()+" :->Login With Empty Password", "Login should not happen with Empty Password");
////        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        driver.get(BaseTest.getValue("URL"));
//        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), "",true);
//        SweetAlert sweetAlert=new SweetAlert(driver);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        logger.info("******verifyLoginWithEmptyPassword Finished******");
//
//    }
//
//    @Test(priority = 1,description = "Try to login with Explicit Empty password")
//    public void verifyLoginWithExplicitEmptyPassword() throws InterruptedException{
//        logger.info("******verifyLoginWithEmptyPassword Started******");
//        test = ExtentTestManager.startTest(getClass().getSimpleName()+" :->Login With Empty Password", "Login should not happen with Empty Password");
////        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        driver.get(BaseTest.getValue("URL"));
//        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), "     ",true);
//        SweetAlert sweetAlert=new SweetAlert(driver);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        logger.info("******verifyLoginWithEmptyPassword Finished******");
//
//    }
//
//
//    @Test(priority = 2,description = "Try to login with Empty UserName")
//    public void verifyLoginWithEmptyUserName() throws InterruptedException{
//        logger.info("******verifyLoginWithEmptyUserName Started******");
//        test = ExtentTestManager.startTest(getClass().getSimpleName()+" :->Login With Empty Username", "Login should not happen with Empty Username");
////        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        loginPageNegativeScenarios.login("", prop.getProperty("Password"),true);
//        SweetAlert sweetAlert=new SweetAlert(driver);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        logger.info("******verifyLoginWithEmptyUserName Finished******");
//
//    }
//
//    @Test(priority = 3,description = "Try to login with Valid UserName and Valid Password, but username and password belongs to two different person")
//    public void verifyLoginWithDifferentUserAndDifferentPassword() throws InterruptedException{
//        logger.info("******verifyLoginWithDifferentUserAndDifferentPassword Started******");
//        test = ExtentTestManager.startTest(getClass().getSimpleName()+" :->Login With Valid UserName and Valid Password, but username and password belongs to two different person", "Valid UserName and Valid Password, but username and password belongs to two different person");
////        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        loginPageNegativeScenarios.login("manmohan+10@clevertap.com", "CleverTap0104",true);
//        SweetAlert sweetAlert=new SweetAlert(driver);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        logger.info("******verifyLoginWithDifferentUserAndDifferentPassword Finished******");
//
//    }
//
//
////    /*Tested and raise a bug: */
//    @Test(priority = 4,description = "Try to login with Valid UserName with extra space in between it")
//    public void verifyLoginWithValidUSerWithExtraSpace() throws InterruptedException{
//        logger.info("******verifyLoginWithDifferentUserAndDifferentPassword Started******");
//        test = ExtentTestManager.startTest(getClass().getSimpleName()+" :->Login With Valid UserName with extra space as prefix ", "");
////        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        loginPageNegativeScenarios.login("    manmo  han+10@clevertap.com", "Manmohan@1",true);
//        SweetAlert sweetAlert=new SweetAlert(driver);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        Thread.sleep(61000);
//        logger.info("******verifyLoginWithDifferentUserAndDifferentPassword Finished******");
//
//    }
//
//    @Test(priority = 5,description = "Try to login with Invalid UserName and Valid Password")
//    public void verifyLoginWithInvalidUserNameAndValidPAssword() throws InterruptedException{
//        logger.info("******verifyLoginWithInvalidUserNameAndValidPAssword Started******");
//        test = ExtentTestManager.startTest(getClass().getSimpleName()+" :->Login With Invalid UserName and valid password ", "");
////        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        loginPageNegativeScenarios.login("manmohan1001@clevertap.com", "Manmohan@1",true);
//        SweetAlert sweetAlert=new SweetAlert(driver);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        Thread.sleep(61000);
//        logger.info("******verifyLoginWithInvalidUserNameAndValidPAssword Finished******");
//
//    }
//
//
//    @Test(priority = 6,description = "Try to login with Valid UserName and Invalid Password")
//    public void verifyLoginWithValidUserNameAndInvalidPAssword() throws InterruptedException{
//        logger.info("******verifyLoginWithValidUserNameAndInvalidPAssword Started******");
//        test = ExtentTestManager.startTest(getClass().getSimpleName()+" :->Login With Valid UserName and Invalid password ", "");
////        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        loginPageNegativeScenarios.login("manmohan+10@clevertap.com", "Manmohan@1234",true);
//        SweetAlert sweetAlert=new SweetAlert(driver);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        Thread.sleep(61000);
//        logger.info("******verifyLoginWithValidUserNameAndInvalidPAssword Finished******");
//
//    }
//
//    @Test(priority = 7,description = "Try to login with Invalid UserName and Invalid Password")
//    public void verifyLoginWithInvalidUserNameAndInvalidPAssword() throws InterruptedException{
//        logger.info("******verifyLoginWithInvalidUserNameAndInvalidPAssword Started******");
//        test = ExtentTestManager.startTest(getClass().getSimpleName()+" :->Login With Valid UserName and Invalid password ", "");
////        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        loginPageNegativeScenarios.login("manmohan2000@clevertap.com", "Manmohan@123456",true);
//        SweetAlert sweetAlert=new SweetAlert(driver);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        Thread.sleep(61000);
//        logger.info("******verifyLoginWithInvalidUserNameAndInvalidPAssword Finished******");
//
//    }
//
//
////////    @Test(priority = 8,description = "Try to login with Inactive UserName and Password")
////////    public void verifyLoginWithInactiveUsernameAndPassword() throws InterruptedException{
////////        logger.info("******verifyLoginWithInactiveUsernameAndPassword Started******");
////////        test = ExtentTestManager.startTest("Login With inactive UserName and password ", "");
////////        parentTest.appendChild(test);
////////        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
////////        loginPageNegativeScenarios.login("    manmohan+21@clevertap.com", "Manmohan@1",true);
////////        Assert.assertTrue(loginPageNegativeScenarios.getSweetAlertString().contains("Invalid"), "");
////////        loginPageNegativeScenarios.hitOk();
////////        test.log(LogStatus.PASS, "");
////////        ExtentTestManager.endTest();
////////        logger.info("******verifyLoginWithInactiveUsernameAndPassword Finished******");
////////
////////    }
//////
//    @Test(priority = 9,description = "Verify that clicking on browser back button after successful login should not take User to log out mode ")
//    public void verifyBrowserBackButtonNotTakeToLogoutAfterLogin() throws InterruptedException{
//        logger.info("******verifyBrowserBackButtonNotTakeToLogoutAfterLogin Started******");
//        test = ExtentTestManager.startTest("verifyBrowserBackButtonNotTakeToLogoutAfterLogin ", "");
//        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        loginPageNegativeScenarios.login("manmohan+10@clevertap.com", "Manmohan@1",true);
//        Assert.assertTrue(loginPageNegativeScenarios.getHeaderString().contains("boards"), "Logged In Successfull");
//
//        driver.navigate().back();
////        SeleniumUtils.waitForPageLoaded(driver);
//        Assert.assertTrue(!SeleniumUtils.isClickable(loginPageNegativeScenarios.getLoginElement(),driver));
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        Thread.sleep(101000);
//        logger.info("******verifyBrowserBackButtonNotTakeToLogoutAfterLogin Finished******");
//
//    }
//
//    @Test(priority = 10,description = "Verify that clicking on browser back button after successful logout should not take User to logged in mode ")
//    public void verifyBrowserBackButtonNotTakeToLoginAfterLogout() throws InterruptedException{
//        logger.info("******verifyBrowserBackButtonNotTakeToLoginAfterLogout Started******");
//        test = ExtentTestManager.startTest("verifyBrowserBackButtonNotTakeToLoginAfterLogout ", "");
//        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//
//        loginPageNegativeScenarios.login("manmohan+10@clevertap.com", "Manmohan@1",true);
//        //put the code to logout
//        loginPageNegativeScenarios.logOutApplication();
////        SeleniumUtils.waitForPageLoaded(driver);
//
//        driver.navigate().back();
//        SeleniumUtils.waitForPageLoaded(driver);
//
//        Assert.assertTrue(!loginPageNegativeScenarios.getHeaderString().contains("boards"), "");
//
//
//
////        Assert.assertTrue(SeleniumUtils.isClickable(loginPageNegativeScenarios.getLoginElement(),driver));
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        logger.info("******verifyBrowserBackButtonNotTakeToLoginAfterLogout Finished******");
//
//    }
//
////    @Test(priority = 11,description = "Verify that the password is in encrypted form when entered")
////    public void verifyPasswordEncryptedWhenEntered() throws InterruptedException{
////        logger.info("******verifyPasswordEncryptedWhenEntered Started******");
////        test = ExtentTestManager.startTest("verifyPasswordEncryptedWhenEntered ", "");
////        parentTest.appendChild(test);
////        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
////
////        Assert.assertTrue(loginPageNegativeScenarios.checkPasswordEncrypted().equalsIgnoreCase("password"), "Password is encrypted");
////        test.log(LogStatus.PASS, "");
////        ExtentTestManager.endTest();
////        logger.info("******verifyPasswordEncryptedWhenEntered Finished******");
////
////    }
////
////    @Test(priority = 12,description = "Verify that encrypted characters in “Password” field should not allow deciphering if copied")
////    public void verifyPasswordEncryptedDecipherNotAllowedWhenCopied() throws InterruptedException{
////        logger.info("******verifyPasswordEncryptedDecipherNotAllowedWhenCopied Started******");
////        test = ExtentTestManager.startTest("verifyPasswordEncryptedDecipherNotAllowedWhenCopied ", "");
////        parentTest.appendChild(test);
////        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
////        loginPageNegativeScenarios.login("manmohan+10@clevertap.com", "Manmohan@1",false);
////
////        Assert.assertTrue(loginPageNegativeScenarios.getPasswordElement().getText().equalsIgnoreCase(""), "Password can not be deciphered upon copy encrypted password");
////        test.log(LogStatus.PASS, "");
////        ExtentTestManager.endTest();
////        logger.info("******verifyPasswordEncryptedDecipherNotAllowedWhenCopied Finished******");
////
////    }
//
//
////    @Test(priority = 14,description = "Verify that the logout link is redirected to login/home page")
////    public void verifyLogoutRedirectToLoginPage() throws InterruptedException{
////        logger.info("******verifyLogoutRedirectToLoginPage Started******");
////        test = ExtentTestManager.startTest("verifyLogoutRedirectToLoginPage ", "");
////        parentTest.appendChild(test);
////
////        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
////        loginPageNegativeScenarios.login("manmohan+10@clevertap.com", "Manmohan@1",true);
////        Thread.sleep(10000);
////        SeleniumUtils.waitForPageLoaded(driver);
////
////        //put code for logout
////
////        loginPageNegativeScenarios.logOutApplication();
////        SeleniumUtils.waitForPageLoaded(driver);
////
////        Assert.assertTrue(SeleniumUtils.isClickable(loginPageNegativeScenarios.getLoginElement(),driver), "");
////        test.log(LogStatus.PASS, "");
////
////        ExtentTestManager.endTest();
////        logger.info("******verifyLogoutRedirectToLoginPage Finished******");
////
////    }
//
//
//
//
//
//
//    @AfterMethod
//
//    public static void setResult(ITestResult result) throws Exception {
//        if (result.getStatus() == ITestResult.FAILURE) {
//            test.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
//            test.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
//            String screenshotPath = SeleniumUtils.getScreenshot(driver, result.getName());
//            test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
//            logger.info("Test failed !!!!!!!!!!");
//            parentTest.log(LogStatus.FAIL, "Test failed due to " + result.getName() + " test case");
//        } else if (result.getStatus() == ITestResult.SKIP) {
//            test.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
//        } else if (result.getStatus() == ITestResult.SUCCESS) {
//            parentTest.log(LogStatus.PASS, "");
//        }
//        ExtentTestManager.endTest();
//    }
//
//
//    @AfterClass
//    private void tearDown() {
//        logger.info("closing the chrome browser and driver");
//        driver.close();
//        driver.quit();
//    }
//}
