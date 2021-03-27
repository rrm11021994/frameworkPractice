package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.settings_menu_page.WhatsAppIntegration;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WhatsAppIntegrationTest extends BaseTest{

    private static final String PASSED = "Passed";
    private static Logger logger;
    private static WebDriver driver;

    @BeforeClass(alwaysRun=true)
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WHATSAPP.toString());
        logger = baseTest.configureLogger(getClass());

    }


    @Test(groups = {TestCaseGroups.ALL , TestCaseGroups.WHATSAPPCRITICAL}, description = "Verify if user is able to create a Template for WhatsApp")
    public void validateCreateTemplatesTab() {
        logger.info("******validateCreateTemplatesTab Test started*****");
        WhatsAppIntegration obj = new WhatsAppIntegration(driver);

        boolean accessCheck = obj.createTemplate("whatsapp:hsm:technology:nexmo:verifytest",
                "Welcome{{1}}. We look forward to serving you better with WhatsApp");

        logger.info("Result: User is able to create a template.");
        Assert.assertTrue(accessCheck, "User is able to create a template.");
        logger.info("******validateCreateTemplatesTab Test Finished*****");


    }

    @Test(groups = {TestCaseGroups.ALL,"WhatsApp", TestCaseGroups.CRITICAL}, description = "Verify if user is able to create a Template for WhatsApp")
    public void validatePBSCampaignCreation()  {

        logger.info("******validatePBSCampaignCreation Test started*****");
        WhatsAppIntegration obj = new WhatsAppIntegration(driver);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        boolean whatsAppCampaignCreation = obj.validatePBSCampaigns("WA_Campaign_PBS_Automation");

        logger.info("Result: User is able to create a Past Behaviour based campaign..");
        Assert.assertTrue(whatsAppCampaignCreation, "User is able to create a Past Behaviour based campaign..");
        logger.info("******validatePBSCampaignCreation Test Finished*****");
    }

    @Test(groups = {TestCaseGroups.ALL,"WhatsApp"}, description = "Verify if user is able to create a single action trigger campaign for WhatsApp")
    public void validateSingleActionTriggerCampaignCreation()  {

        logger.info("******validateSingleActionTriggerCampaignCreation Test started*****");
        WhatsAppIntegration obj = new WhatsAppIntegration(driver);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        boolean whatsAppCampaignCreation = obj.validateSingleActionTriggerCampaigns("SingleActionAddedToCart");


        logger.info("Result: User is able to create a single action trigger based campaign..");
        Assert.assertTrue(whatsAppCampaignCreation, "User is able to create a single action trigger based campaign..");
        logger.info("******validateSingleActionTriggerCampaignCreation Test Finished*****");
    }

    @Test(groups = {TestCaseGroups.ALL,"WhatsApp"}, description = "Verify if user is able to create a single action trigger campaign for WhatsApp")
    public void validateInactionWithinTimeTriggerCampaignCreation() {

        logger.info("******validateInactionWithinTimeTriggerCampaignCreation Test started*****");
        WhatsAppIntegration obj = new WhatsAppIntegration(driver);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        boolean whatsAppCampaignCreation = obj.validateInactionWithinTimeCampaigns("InactionInTimeWACampaign");

        logger.info("Result: User is able to create a single action trigger based campaign..");
        Assert.assertTrue(whatsAppCampaignCreation, "User is able to create a single action trigger based campaign..");
        logger.info("******validateInactionWithinTimeTriggerCampaignCreation Test Finished*****");
    }


    @Test(groups = {TestCaseGroups.ALL,"WhatsApp"}, description = "Verify if user is able to create a single action trigger campaign for WhatsApp")
    public void validateOnDateTimeTriggerCampaignCreation() {

        logger.info("******validateInactionWithinTimeTriggerCampaignCreation Test started*****");
        WhatsAppIntegration obj = new WhatsAppIntegration(driver);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        boolean whatsAppCampaignCreation = obj.validateOnDateTimeCampaigns("OnDateTimeSegmentWA_Automation");


        logger.info("Result: User is able to create a single action trigger based campaign..");
        Assert.assertTrue(whatsAppCampaignCreation, "User is able to create a single action trigger based campaign..");
        logger.info("******validateInactionWithinTimeTriggerCampaignCreation Test Finished*****");
    }

}
