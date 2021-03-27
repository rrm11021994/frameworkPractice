package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.RestApiUtil.RestApi;
import com.clevertap.ui.pages.settings_menu_page.Account;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class ResetPasscodeTest extends BaseTest{

    private Logger logger;
    private static WebDriver driver;
    public static String accountID;
    public static String passcode;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
       BaseTest baseTest= BaseTest.getInstance();
       driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), "", "");
        logger = baseTest.configureLogger(getClass());
    }

    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.RESETPASSCODECRITICAL, TestCaseGroups.ALL}, description = "Check if user is able to perform action with current passcode")
    public void ResetPasscodePositiveScenario() throws IOException, ParseException, InterruptedException, ConfigurationException {
        logger.info(">>>ResetPasscodePositiveScenario");
        Account acc = new Account(driver);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ACCOUNT.toString(), "");

        RestApi api = new RestApi();
        accountID = acc.getAccountId();
        logger.info("Account ID for the account is: " + accountID);
        passcode = acc.getPasscode();
        logger.info("Passcode for the account is: " + passcode);
        Boolean eventUpload = api.pushAction("EventPushPasscodeReset.json", "https://api.clevertap.com/1/upload", "POST");
        Assert.assertTrue(eventUpload, "Event Upload");
        logger.info("<<<ResetPasscodePositiveScenario");
    }

    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.RESETPASSCODECRITICAL, TestCaseGroups.ALL},dependsOnMethods ={"ResetPasscodePositiveScenario"},description = "Check user should not be able to perform action after reseting the passcode")
    public void ResetPasscodeNegativeScenario() throws IOException, ParseException, InterruptedException, ConfigurationException {
        logger.info(">>>ResetPasscodeNegativeScenario");
        Account acc = new Account(driver);
        RestApi api = new RestApi();
        acc.resetPasscode();
        driver.navigate().refresh();
        SeleniumUtils.pause(200);
        Boolean eventUploadAfterReset = api.pushAction("EventPushPasscodeReset.json", "https://api.clevertap.com/1/upload", "POST");
        Assert.assertFalse(eventUploadAfterReset, "Event Upload after ");
        logger.info(passcode);
        logger.info("<<<ResetPasscodeNegativeScenario");
    }
}
