package com.clevertap.ui.login_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.login_page.LoginPageNegativeScenarios;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    private LoginPageNegativeScenarios loginPageNegativeScenarios;
    private WebDriver driver;
    private SweetAlert sweetAlert;
    private final String user1="gaurav.yadav+loginnegative1@clevertap.com";
    private final String user2="gaurav.yadav+loginnegative2@clevertap.com";
    private final String password="Alliswell@123";

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        driver=getDriver();
        loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
        sweetAlert=new SweetAlert(driver);
        driver.get(Mocha.url);
    }

    @Test(priority = 0,description = "user should be able to login with correct username and password",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyUserShouldBeAbleToLoginWithCorrectCreditianls(){

        Reporter.log("verify user should be able to login : started",true);
        loginPageNegativeScenarios.login(getValue("UserName"),getValue("Password"),true);
        Assert.assertTrue(loginPageNegativeScenarios.verifyUserLoggedIn(),"user was not able to login");
        Assert.assertTrue(driver.getCurrentUrl().contains("my-board.html"),"User was not logged in sucessfully");
        loginPageNegativeScenarios.logOutApplication();
        Reporter.log("verify user should be able to login : ended",true);

    }

    @Test(priority = 1,description = "user should not be able to login with incorrect password",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyUserIsNotAbleToLoginWithWrongPassword(){

        Reporter.log("verify user should not be able to login with incorrect password : Started",true);
        loginPageNegativeScenarios.login(user1,password+"asd",true);
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "user should be present on login page");
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Mocha.url+"/login.html#/"),"user was not present on login page");
        SeleniumUtils.pause(1);//this wait required so for invalid text to be visible
        Assert.assertTrue(loginPageNegativeScenarios.invalidCredentialsLoc.isDisplayed(), "Error message alert was not present");
        Reporter.log("verify user should not be able to login with incorrect password : ended",true);
    }

    @Test(priority = 2,description = "user should not be able to login with incorrect email",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyUserIsNotAbleToLoginWithWrongEmail(){

        Reporter.log("verify user should not be able to login with incorrect email : Started",true);
        driver.navigate().refresh();
        loginPageNegativeScenarios.login("gauravv.yadav+loginnegative1@clevertap.com",getValue("Password"),true);
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "user should be present on login page");
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Mocha.url+"/login.html#/"),"user was not present on login page");
        SeleniumUtils.pause(1);//this wait required so for invalid text to be visible
        Assert.assertTrue(loginPageNegativeScenarios.invalidCredentialsLoc.isDisplayed(), "Error message alert was not present");
        Reporter.log("verify user should not be able to login with incorrect email : ended",true);

    }

    @Test(priority = 3,description = "user should not be able to login with empty password",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyLoginWithEmptyPassword(){

        Reporter.log("verifyLoginWithEmptyPassword : Started",true);
        driver.navigate().refresh();
        loginPageNegativeScenarios.login(user1,"",true);
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "user should be present on login page");
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Mocha.url+"/login.html#/"),"user was not present on login page");
        SeleniumUtils.pause(1);//this wait required so for invalid text to be visible
        Assert.assertTrue(loginPageNegativeScenarios.pleaseEnterValidCredLoc.isDisplayed(), "Error message alert was not present");
        Reporter.log("verifyLoginWithEmptyPassword : Ended",true);

    }

    @Test(priority = 4,description = "user should not be able to login with space as password",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyLoginWithSpaceAsPassword(){

        Reporter.log("verifyLoginWithSpaceAsPassword : Started",true);
        driver.navigate().refresh();
        loginPageNegativeScenarios.login(user2,"             ",true);
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "user should be present on login page");
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Mocha.url+"/login.html#/"),"user was not present on login page");
        SeleniumUtils.pause(1);//this wait required so for invalid text to be visible
        Assert.assertTrue(loginPageNegativeScenarios.invalidCredentialsLoc.isDisplayed(), "Error message alert was not present");
        Reporter.log("verifyLoginWithSpaceAsPassword : Ended",true);

    }

    @Test(priority = 5,description = "user should not be able to login with empty username",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyLoginWithEmptyUserName(){

        Reporter.log("verifyLoginWithEmptyUserName : Started",true);
        driver.navigate().refresh();
        loginPageNegativeScenarios.login("",getValue("Password"),true);
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "user should be present on login page");
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Mocha.url+"/login.html#/"),"user was not present on login page");
        SeleniumUtils.pause(1);//this wait required so for invalid text to be visible
        Assert.assertTrue(loginPageNegativeScenarios.pleaseEnterValidCredLoc.isDisplayed(), "Error message alert was not present");
        Reporter.log("verifyLoginWithEmptyUserName : Ended",true);

    }

    @Test(priority = 6,description = "user should not be able to login with space As username",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyLoginWithSpaceAsUserName(){

        Reporter.log("verifyLoginWithSpaceAsUserName : Started",true);
        driver.navigate().refresh();
        loginPageNegativeScenarios.login("               ",getValue("Password"),true);
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "user should be present on login page");
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Mocha.url+"/login.html#/"),"user was not present on login page");
        SeleniumUtils.pause(1);//this wait required so for invalid text to be visible
        Assert.assertTrue(loginPageNegativeScenarios.pleaseEnterValidCredLoc.isDisplayed(), "Error message alert was not present");
        Reporter.log("verifyLoginWithSpaceAsUserName : Ended",true);

    }

    @Test(priority = 7,description = "user should not be able to login with username of one user and password of another user",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyLoginWithDifferentUserAndDifferentPassword(){

        Reporter.log("verifyLoginWithDifferentUserAndDifferentPassword : Started",true);
        driver.navigate().refresh();
        loginPageNegativeScenarios.login(user2,getValue("GooglePassword"),true);
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "user should be present on login page");
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(Mocha.url+"/login.html#/"),"user was not present on login page");
        SeleniumUtils.pause(1);//this wait required so for invalid text to be visible
        Assert.assertTrue(loginPageNegativeScenarios.invalidCredentialsLoc.isDisplayed(), "Error message alert was not present");
        Reporter.log("verifyLoginWithDifferentUserAndDifferentPassword : Ended",true);

    }

    @Test(priority = 0,description = "after successful login if user tries to go back using browser navigate he should be taken to login page and if user refresh then he should be taken to homepage of dashboard",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyBrowserBackButtonNotTakeToLogoutAfterLogin(){
        Reporter.log("verifyBrowserBackButtonNotTakeToLogoutAfterLogin : Started",true);
        loginPageNegativeScenarios.login(getValue("UserName"),getValue("Password"),true);
        Assert.assertTrue(loginPageNegativeScenarios.verifyUserLoggedIn(),"user was not able to login");
        Assert.assertTrue(driver.getCurrentUrl().contains("my-board.html"),"User was not logged in sucessfully");
        driver.navigate().back();
        SeleniumUtils.waitForElementToLoad(driver,loginPageNegativeScenarios.getLoginElement());
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "after clicking back user was not on login page");
        Assert.assertTrue(driver.getCurrentUrl().contains("login.html"),"User was not logged in sucessfully");
        driver.navigate().refresh();
        Assert.assertTrue(driver.getCurrentUrl().contains("my-board.html"),"User was not logged in sucessfully");
        Assert.assertTrue(loginPageNegativeScenarios.verifyUserLoggedIn(),"user was not able to login");
        loginPageNegativeScenarios.logOutApplication();
        Reporter.log("verifyBrowserBackButtonNotTakeToLogoutAfterLogin : Ended",true);
    }

    @Test(priority = 0,description = "after user logout from dashboard and tries to navigate back using browser he should not been taken to login page",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyBrowserBackButtonNotTakeToLoginAfterLogout(){

        Reporter.log("verifyBrowserBackButtonNotTakeToLoginAfterLogout : Started",true);
        loginPageNegativeScenarios.login(getValue("UserName"),getValue("Password"),true);
        Assert.assertTrue(loginPageNegativeScenarios.verifyUserLoggedIn(),"user was not able to login");
        Assert.assertTrue(driver.getCurrentUrl().contains("my-board.html"),"User was not logged in sucessfully");
        loginPageNegativeScenarios.logOutApplication();
        SeleniumUtils.waitForElementToLoad(driver,loginPageNegativeScenarios.getLoginElement());
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "after clicking back user was not on login page");
        driver.navigate().back();
        SeleniumUtils.waitForElementToLoad(driver,loginPageNegativeScenarios.getLoginElement());
        Assert.assertTrue(loginPageNegativeScenarios.getLoginElement().isDisplayed(), "after clicking back user was not on login page");
        Assert.assertTrue(driver.getCurrentUrl().contains("login.html"),"User was not logged in sucessfully");
        Reporter.log("verifyBrowserBackButtonNotTakeToLoginAfterLogout : Ended",true);

    }

    @Test(priority = 0,description = "Verify that the password is in encrypted form when entered",groups = TestCaseGroups.LOGINCRITICAL)
    public void verifyPasswordEncryptedWhenEntered(){
        Reporter.log("verifyPasswordEncryptedWhenEntered : Started",true);
        Assert.assertTrue(loginPageNegativeScenarios.checkPasswordEncrypted().equalsIgnoreCase("password"), "Password is encrypted");
        Reporter.log("verifyPasswordEncryptedWhenEntered : Ended",true);
    }

    // pending need to ask that getattribute value can give password of user
    //@Test(description = "Verify that encrypted characters in “Password” field should not allow deciphering if copied")
    public void verifyPasswordEncryptedDecipherNotAllowedWhenCopied(){

        Reporter.log("verifyPasswordEncryptedDecipherNotAllowedWhenCopied : Started",true);
        loginPageNegativeScenarios.login(BaseTest.getValue("UserName"), BaseTest.getValue("Password"), false);
        Assert.assertTrue(loginPageNegativeScenarios.getPasswordElement().getText().equalsIgnoreCase(""), "Password can not be deciphered upon copy encrypted password");
        Reporter.log("verifyPasswordEncryptedDecipherNotAllowedWhenCopied : Ended",true);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        driver.close();
        driver.quit();
    }

}
