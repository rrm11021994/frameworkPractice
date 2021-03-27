package com.clevertap.ui.login_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.login_page.LoginPageNegativeScenarios;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTestNegativeScenariosPart1 extends BaseTest{

    private static final String LOGIN_SECURITY_CHECK = "Login_Security_Check_Part1";
    private static final String LOGIN_SECURITY_NEGATIVE_SCENARIOS = "Login Security/Negative Scenarios";
    private static SweetAlert sweetAlert = null;
    private WebDriver driver;
    private LoginPageNegativeScenarios loginPageNegativeScenarios;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver=getDriver();
        loginPageNegativeScenarios = new LoginPageNegativeScenarios(driver);
        sweetAlert = new SweetAlert(driver);
        driver.get(Mocha.url);

    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART1CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Try to login with Empty password")
    public void verifyLoginWithEmptyPassword(){
        Reporter.log("******verifyLoginWithEmptyPassword Started******",true);
        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), "", true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        Reporter.log("******verifyLoginWithEmptyPassword Finished******",true);

    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART1CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Try to login with Explicit Empty password")
    public void verifyLoginWithExplicitEmptyPassword() {
        Reporter.log("******verifyLoginWithEmptyPassword Started******",true);
        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), "     ", true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        Reporter.log("******verifyLoginWithEmptyPassword Finished******",true);

    }


    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART1CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Try to login with Empty UserName")
    public void verifyLoginWithEmptyUserName(){
        Reporter.log("******verifyLoginWithEmptyUserName Started******",true);
        loginPageNegativeScenarios.login("", BaseTest.getValue("Password"), true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        Reporter.log("******verifyLoginWithEmptyUserName Finished******",true);

    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART1CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Try to login with Empty UserName")
    public void verifyLoginWithExplicitlyEmptyUserName(){
        Reporter.log("******verifyLoginWithExplicitlyEmptyUserName Started******",true);
        loginPageNegativeScenarios.login("   ", BaseTest.getValue("Password"), true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        Reporter.log("******verifyLoginWithExplicitlyEmptyUserName Finished******",true);

    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART1CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Try to login with Valid UserName and Valid Password, but username and password belongs to two different person")
    public void verifyLoginWithDifferentUserAndDifferentPassword(){
        Reporter.log("******verifyLoginWithDifferentUserAndDifferentPassword Started******",true);
        loginPageNegativeScenarios.login("manmohan+10@clevertap.com", "CleverTap0104", true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        Reporter.log("******verifyLoginWithDifferentUserAndDifferentPassword Finished******",true);

    }


////    /*Tested and raise a bug: */
//    @Test(priority = 4,description = "Try to login with Valid UserName with extra space in between it")
//    public void verifyLoginWithValidUSerWithExtraSpace() throws InterruptedException{
//        Reporter.log("******verifyLoginWithDifferentUserAndDifferentPassword Started******");
//        test = ExtentTestManager.startTest("Login With Valid UserName with extra space as prefix ", "");
//        parentTest.appendChild(test);
//        LoginPageNegativeScenarios loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
//        loginPageNegativeScenarios.login("manmo  han+10@clevertap.com", "Manmohan@1",true);
//        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
//        test.log(LogStatus.PASS, "");
//        ExtentTestManager.endTest();
//        Thread.sleep(61000);
//        Reporter.log("******verifyLoginWithDifferentUserAndDifferentPassword Finished******");
//
//    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART1CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Try to login with Invalid UserName and Valid Password")
    public void verifyLoginWithInvalidUserNameAndValidPAssword() {
        Reporter.log("******verifyLoginWithInvalidUserNameAndValidPAssword Started******",true);
        loginPageNegativeScenarios.login("manmohan1001@clevertap.com", "Manmohan@1", true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        SeleniumUtils.pause(61);
        Reporter.log("******verifyLoginWithInvalidUserNameAndValidPAssword Finished******",true);

    }


    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART1CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Try to login with Valid UserName and Invalid Password")
    public void verifyLoginWithValidUserNameAndInvalidPAssword() {
        Reporter.log("******verifyLoginWithValidUserNameAndInvalidPAssword Started******",true);
        loginPageNegativeScenarios.login("manmohan+10@clevertap.com", "Manmohan@1234", true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        SeleniumUtils.pause(61);
        Reporter.log("******verifyLoginWithValidUserNameAndInvalidPAssword Finished******",true);

    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART1CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Try to login with Invalid UserName and Invalid Password")
    public void verifyLoginWithInvalidUserNameAndInvalidPAssword() {
        Reporter.log("******verifyLoginWithInvalidUserNameAndInvalidPAssword Started******",true);
        loginPageNegativeScenarios.login("manmohan2000@clevertap.com", "Manmohan@123456", true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        Reporter.log("******verifyLoginWithInvalidUserNameAndInvalidPAssword Finished******",true);

    }

    /*Login should not be allowed to inactive user, already discussed with prodcut team
    @Test(groups = {TestCaseGroups.CRITICAL,TestCaseGroups.ALL,TestCaseGroups.NEGATIVETESTSCENARIO},description = "Try to login with Inactive UserName and Password")
    public void verifyLoginWithInactiveUsernameAndPassword() throws InterruptedException {
        Reporter.log("******verifyLoginWithInactiveUsernameAndPassword Started******");
        test = ExtentTestManager.startTest("Login With inactive UserName and password ", "");
        parentTest.appendChild(test);
        LoginPageNegativeScenarios loginPageNegativeScenarios = new LoginPageNegativeScenarios(driver);
        loginPageNegativeScenarios.login("manmohan+21@clevertap.com", "Manmohan@1", true);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "");
        loginPageNegativeScenarios.hitOk();
        test.log(LogStatus.PASS, "");
        ExtentTestManager.endTest();
        Reporter.log("******verifyLoginWithInactiveUsernameAndPassword Finished******");

    }*/

}
