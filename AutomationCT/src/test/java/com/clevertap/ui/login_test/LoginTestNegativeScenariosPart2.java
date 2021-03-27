package com.clevertap.ui.login_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.login_page.LoginPageNegativeScenarios;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTestNegativeScenariosPart2 extends BaseTest{

    private static final String LOGIN_SECURITY_CHECK = "Login_Security_Check_Part2";
    private static final String LOGIN_SECURITY_NEGATIVE_SCENARIOS = "Login Security/Negative Scenarios";
    private static Logger logger;
    private static SweetAlert sweetAlert = null;
    private static WebDriver driver;
    private LoginPageNegativeScenarios loginPageNegativeScenarios;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver=getDriver();
        loginPageNegativeScenarios = new LoginPageNegativeScenarios(driver);
        PropertyConfigurator.configure("log4j.properties");
        logger = configureLogger(getClass());
        sweetAlert = new SweetAlert(driver);
        driver.get(Mocha.getUrl(getValue("environment")));

    }

    /*Raised Jira as it takes to log out mode after successfull login. It is good to have scenario so this test is failing*/
    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART2CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Verify that clicking on browser back button after successful login should not take User to log out mode ")
    public void verifyBrowserBackButtonNotTakeToLogoutAfterLogin() throws InterruptedException {
        Reporter.log("******verifyBrowserBackButtonNotTakeToLogoutAfterLogin Started******",true);
        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), BaseTest.getValue("Password"), true);
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//div[text()='Boards']")));
        Assert.assertTrue(loginPageNegativeScenarios.getHeaderString().contains("Boards"), "Logged In Successfull");
        driver.navigate().back();
        SeleniumUtils.pause(3);
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isEnabled(), "Login button is enabled");
        Reporter.log("******verifyBrowserBackButtonNotTakeToLogoutAfterLogin Finished******",true);

    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART2CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Verify that clicking on browser back button after successful logout should not take User to logged in mode ", dependsOnMethods = {"verifyBrowserBackButtonNotTakeToLogoutAfterLogin"})
    public void verifyBrowserBackButtonNotTakeToLoginAfterLogout() throws InterruptedException {
        Reporter.log("******verifyBrowserBackButtonNotTakeToLoginAfterLogout Started******");
        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), BaseTest.getValue("Password"), true);
        SeleniumUtils.pause(1);
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.id("ellipsis_icon")));
        loginPageNegativeScenarios.logOutApplication();
        SeleniumUtils.waitForElementToLoad(driver,loginPageNegativeScenarios.getLoginElement());
        driver.navigate().back();
        SeleniumUtils.waitForElementToLoad(driver,loginPageNegativeScenarios.getLoginElement());
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "after clicking back user was not on login page");
        Reporter.log("******verifyBrowserBackButtonNotTakeToLoginAfterLogout Finished******");
    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART2CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Verify that the password is in encrypted form when entered", dependsOnMethods = {"verifyBrowserBackButtonNotTakeToLoginAfterLogout"})
    public void verifyPasswordEncryptedWhenEntered() throws InterruptedException {
        Reporter.log("******verifyPasswordEncryptedWhenEntered Started******");
        Assert.assertTrue(loginPageNegativeScenarios.checkPasswordEncrypted().equalsIgnoreCase("password"), "Password is encrypted");
        Reporter.log("******verifyPasswordEncryptedWhenEntered Finished******");

    }

    @Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART2CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Verify that encrypted characters in “Password” field should not allow deciphering if copied", dependsOnMethods = {"verifyPasswordEncryptedWhenEntered"})
    public void verifyPasswordEncryptedDecipherNotAllowedWhenCopied() throws InterruptedException {
        Reporter.log("******verifyPasswordEncryptedDecipherNotAllowedWhenCopied Started******");
        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), BaseTest.getValue("Password"), false);
        Assert.assertTrue(loginPageNegativeScenarios.getPasswordElement().getText().equalsIgnoreCase(""), "Password can not be deciphered upon copy encrypted password");
        Reporter.log("******verifyPasswordEncryptedDecipherNotAllowedWhenCopied Finished******");

    }


    // commented because this scenario is covered in 'verifyBrowserBackButtonNotTakeToLoginAfterLogout' this test case
    //@Test(groups = {TestCaseGroups.LOGINNEGATIVESCENARIOPART2CRITICAL, TestCaseGroups.ALL, TestCaseGroups.NEGATIVETESTSCENARIO}, description = "Verify that the logout link is redirected to login/home page", dependsOnMethods = {"verifyPasswordEncryptedDecipherNotAllowedWhenCopied"})
    public void verifyLogoutRedirectToLoginPage() throws InterruptedException {
        Reporter.log("******verifyLogoutRedirectToLoginPage Started******");
        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), BaseTest.getValue("Password"), true);
        SeleniumUtils.waitForPageLoaded(driver);
        try {
            driver.findElement(By.xpath("//a[@class='close-experience-banner']")).click();
            SeleniumUtils.pause(1);
        } catch (NoSuchElementException e) {
            Reporter.log("no info bar found " + e.getMessage());
        }catch (ElementNotInteractableException e){
            Reporter.log("Element not interactable: "+e.getMessage());
        }
        loginPageNegativeScenarios.logOutApplication();
        SeleniumUtils.waitForPageLoaded(driver);
        Assert.assertTrue(SeleniumUtils.isClickable(loginPageNegativeScenarios.getLoginElement(), driver), "");
        Reporter.log("******verifyLogoutRedirectToLoginPage Finished******");

    }
}
