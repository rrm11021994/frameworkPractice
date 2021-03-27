package com.clevertap.ui.login_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.Login;
import com.clevertap.utils.Gmail;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ForgotPasswordTest extends BaseTest{

    private static final String FORGOT_PASSWORD = "Forgot_Password";
    private static final String CHECK_IF_FORGOT_PASSWORD_COMPONENT_IS_WORKING_CORRECTLY = "Check If Forgot Password component is working correctly";
    private static final String WWW_GMAIL_COM = "www.gmail.com";
    private Logger logger;
    private WebDriver driver;
    private static final String CTAutomationUserName = "manmohan@clevertap.com" ;//"clevertapmumbaitest@gmail.com"
    private static final String CTAutomationPassword = "Asdfg@123";

    @BeforeClass(alwaysRun=true)
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        PropertyConfigurator.configure("log4j.properties");
        logger = baseTest.configureLogger(getClass());
    }

    @Test(groups = {TestCaseGroups.ALL,TestCaseGroups.CRITICAL,TestCaseGroups.REGRESSION})
    public void verifyForgotPassword(){
        logger.info(">>>verifyForgotPassword");
//        driver.get(BaseTest.getValue("URL")+"/login.html");
        driver.get(BaseTest.getValue("sk1-1_ui"));
        SeleniumUtils.waitForPageLoaded(driver);
        Login login = new Login(driver);
        login.forgotPassword(CTAutomationUserName);
        driver.get(WWW_GMAIL_COM);
        Gmail gmail = new Gmail(driver);
        gmail.logInToGMail(CTAutomationUserName,CTAutomationPassword);
        gmail.resetPassword(CTAutomationPassword);
        Login.login(CTAutomationUserName,CTAutomationPassword);
        SeleniumUtils.waitForPageLoaded(driver);
        Assert.assertTrue(driver.findElement(By.xpath("//a[@class='accountName']/span")).getText().contains("Automation Tester"));
        logger.info("<<<verifyForgotPassword");
    }
}