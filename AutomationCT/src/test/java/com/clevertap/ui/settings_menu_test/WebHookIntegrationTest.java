package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.settings_menu_page.WebHookIntegration;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WebHookIntegrationTest extends BaseTest{
    private static Logger logger;
    private static WebDriver driver;
    private SweetAlert sweetAlert;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver = getDriver();
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBHOOKS.toString());
        logger = configureLogger(getClass());
        sweetAlert = new SweetAlert(driver);

    }


    @Test(groups = {TestCaseGroups.WEBHOOKREGRESSION}, description = "Verify if user is able to add a WebHook")
    public void validateCreateWebHookTabNegativeCase() {
        logger.info("******validateCreateWebHookTab Test started with wrong url*****");
        WebHookIntegration webHookIntegration = new WebHookIntegration(driver);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBHOOKS.toString());
        webHookIntegration.addNewWebHook("automation", "test");
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), " webhook failed to add because of invalid credentials");
        logger.info("verify webhook data Finished");
    }

    @Test(groups = {TestCaseGroups.WEBHOOKREGRESSION}, description = "Verify if user is able to add a WebHook")
    public void validateCreateWebHookTabPositiveCase() {
        logger.info("******validateCreateWebHookTab Test started with correct url*****");
        WebHookIntegration webHookIntegration = new WebHookIntegration(driver);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBHOOKS.toString());
        webHookIntegration.addNewWebHook("automation", "https://eng0dq9zn37bt.x.pipedream.net/");
        Assert.assertTrue(sweetAlert.getSweetAlertSuccessSignal(), "New WebHook added successfully");
        logger.info("verify webhook data Finished");
    }

}