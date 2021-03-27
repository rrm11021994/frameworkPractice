package com.clevertap.ui.campaign_tests;


import com.clevertap.BaseTest;
import com.clevertap.ui.pages.campaigns_page.MobilePushPage;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.TestCaseGroups;
import com.clevertap.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


public class MobilePushTests extends BaseTest{

    private static final String MOBILE_PUSH = "Mobile Push";
    private static final String STATUS = "Status";
    private static final String STOPPED = "Stopped";
    private static final String AUTOMATION = "Automation";
    private static final String RUNNING = "Running";
    private static final String CAMPAIGN_NAME = "Campaign name";
    private Logger logger;
    private WebDriver driver;
    private MobilePushPage mobilePushPage ;

    public enum campaignMeta {
        CAMPAIGNTITLE(Data.randomAlphaNumeric("AutoTitleMessage",4)),
        CAMPAIGNTEXT("Mobile push campaign created"),
        CAMPAIGNNAME(Data.randomAlphaNumeric("AutoCampaignMobilePushSingleMessage",4));
        String text;
        campaignMeta(String s) {
            text = s;
        }
        public String toString() {
            return this.text;
        }
    }

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Reporter.log("campaign name use for creation is : "+campaignMeta.CAMPAIGNNAME,true);
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
//        if (driver.findElement(By.id("android-o-settings-check")).isSelected()){
//            driver.findElement(By.xpath("//*[@for='android-o-settings-check']")).click();
//            driver.findElement(By.xpath("//*[@class='android-o-settings']/..//input[@type='submit']")).click();
//            SweetAlert sweetAlert=new SweetAlert(driver);
//            sweetAlert.sweetAlertConfirmOK();
//        }
        mobilePushPage=new MobilePushPage(driver);
        mobilePushPage.turnOffNotificationchannel();
        logger = configureLogger(getClass());
    }

    @Test(description = "Verify header of page")
    public void verifyHeader() {
        logger.info(">>>verifyHeader");
        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        Assert.assertTrue(mobilePushPage.getBreadcumbText().contains("Campaigns"));
        logger.info("<<<verifyHeader");
    }

    @Test(groups = {TestCaseGroups.MOBILEPUSHCRITICAL}, description = "Campaign MobilePush OneTime OneTime StartNow SingleMessage")
    public void createPushCampaigns() throws InterruptedException {
        logger.info(">>>createPushCampaigns");
        Mocha.openLeftNavMenu(driver,false,NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        mobilePushPage.createCampaign();
        mobilePushPage.createMobilePush();
        mobilePushPage.selectOneTimePBS();
        mobilePushPage.selectCampaignStartDate();
        mobilePushPage.selectMessageType();
        mobilePushPage.enterCampaignMeta(campaignMeta.CAMPAIGNTITLE.toString(), campaignMeta.CAMPAIGNTEXT.toString(), campaignMeta.CAMPAIGNNAME.toString());
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");

        List<String> campaignsList = mobilePushPage.getTableCellData();
        Assert.assertTrue(campaignsList.contains(campaignMeta.CAMPAIGNNAME.toString()), "created campaigin was not present on listing page");
        logger.info("<<<createPushCampaigns");
    }

    @Test(groups = {TestCaseGroups.MOBILEPUSHCRITICAL}, dependsOnMethods = {"createPushCampaigns"}, description = "Check if push campaigns can be stopped")
    public void stopPushCampaigns() {
        logger.info(">>>stopPushCampaigns");
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
//        mobilePushPage.clickShowCampaignAction(campaignMeta.CAMPAIGNNAME.toString() + "-edited");
        mobilePushPage.stopCampaign(campaignMeta.CAMPAIGNNAME.toString());
        mobilePushPage.filter(STATUS, STOPPED);
        Assert.assertTrue(mobilePushPage.getTableCellData().contains(campaignMeta.CAMPAIGNNAME.toString()+"-edited"));
        logger.info("<<<stopPushCampaigns");
    }

    //@Test(description = "Check if push campaigns can be stopped in bulk")
    public void stopAllAutomationCampaigns() throws InterruptedException{
        try {
            logger.info(">>>stopAllAutomationCampaigns");
            Mocha.forceNavigate = true;
            Mocha.openLeftNavMenu(driver, false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
            mobilePushPage.filter(STATUS, RUNNING);
            mobilePushPage.stopAllAutomationCampaign(AUTOMATION);
            mobilePushPage.filter(STATUS, STOPPED);
            List<String> stoppedCampaigns = mobilePushPage.getTableCellData();
            Assert.assertTrue(stoppedCampaigns.contains(CAMPAIGN_NAME));
            logger.info("<<<stopAllAutomationCampaigns");
        }finally {
            Mocha.forceNavigate = false;
        }
    }

    @Test(groups = {TestCaseGroups.MOBILEPUSHCRITICAL}, dependsOnMethods = {"createPushCampaigns"}, description = "Check if push campaigns can be cloned")
    public void clonePushCampaigns() {
        logger.info(">>>clonePushCampaigns");
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        mobilePushPage.clickShowCampaignAction(campaignMeta.CAMPAIGNNAME.toString());
        mobilePushPage.cloneCampaign(campaignMeta.CAMPAIGNNAME.toString());
//        Assert.assertTrue(mobilePushPage.getClonedBreadcrumb().contains(campaignMeta.CAMPAIGNNAME.toString() + "-cloned"));
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        Assert.assertTrue(mobilePushPage.getTableCellData().contains(campaignMeta.CAMPAIGNNAME.toString()));
        logger.info("<<<clonePushCampaigns");
    }

    @Test(groups = {TestCaseGroups.MOBILEPUSHCRITICAL}, dependsOnMethods = {"createPushCampaigns"}, description = "Check if push campaigns can be edited")
    public void editPushCampaigns() {
        logger.info(">>>editPushCampaigns");
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        mobilePushPage.clickShowCampaignAction(campaignMeta.CAMPAIGNNAME.toString());
        mobilePushPage.editCampaign(campaignMeta.CAMPAIGNNAME.toString());
//        Assert.assertTrue(mobilePushPage.getClonedBreadcrumb().contains(campaignMeta.CAMPAIGNNAME.toString() + "-edited"));
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        Assert.assertTrue(mobilePushPage.getTableCellData().contains(campaignMeta.CAMPAIGNNAME.toString() + "-edited"));
        logger.info("<<<editPushCampaigns");
    }

    @Test(groups = {TestCaseGroups.MOBILEPUSHCRITICAL},description = "Check if push campaigns can be archived",dependsOnMethods = {"stopPushCampaigns"})
    public void archivePushCampaigns() throws InterruptedException {
        logger.info(">>>archivePushCampaigns");
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        mobilePushPage.filter(STATUS, STOPPED);
//        mobilePushPage.stopCampaign(campaignMeta.CAMPAIGNNAME.toString());
        mobilePushPage.archiveAutomationCampaigns(campaignMeta.CAMPAIGNNAME.toString());
        Assert.assertFalse(mobilePushPage.getTableCellData().contains(campaignMeta.CAMPAIGNNAME.toString()));
        logger.info("<<<archivePushCampaigns");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        Reporter.log("inside after class of Mobile Push test.",true);
        driver.close();
        driver.quit();

    }

}