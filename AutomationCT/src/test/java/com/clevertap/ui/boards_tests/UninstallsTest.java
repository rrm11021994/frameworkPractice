package com.clevertap.ui.boards_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.boards.UninstallsPage;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class UninstallsTest extends BaseTest{

    private Logger logger;
    private WebDriver driver;
    String pointsOnGraph = "//*[contains(@class,'highcharts-point') and (@fill='#02c664')]";
    String tooltipXpath = "//*[@class='highcharts-label highcharts-tooltip highcharts-color-undefined']";


    @BeforeClass
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.Unistall.toString(), "");
        logger = baseTest.configureLogger(getClass());
    }

    @Test(priority = 1, description = "verifying the activations and uninstalls count on daily graph and \"In the last 7 days\"")
    public void verifyActivationUninstallEventsDailyGraph() throws InterruptedException {

        logger.info("******verifyActivationUninstallEventsDailyGraph Test Started*****");
        UninstallsPage uninstallsPage = new UninstallsPage(driver);
        uninstallsPage.selectCalendarDateInterval("In the last 30 days");
        Map<String, Integer> eventsCountMap = uninstallsPage.getEventsCount();
        uninstallsPage.selectTrendTypeDropdown("Daily");
        Map<String, Integer> tootipTextEvenetsMap = SeleniumUtils.getTooltipText(driver, pointsOnGraph, tooltipXpath);

        Assert.assertTrue(eventsCountMap.get("TotalNewActivtions").equals(tootipTextEvenetsMap.get("New activations")), "New activations count matches");
        Assert.assertTrue(eventsCountMap.get("TotalUninstalls").equals(tootipTextEvenetsMap.get("Uninstalls")), "New activations count matches");
        logger.info("******verifyActivationUninstallEventsDailyGraph Test Finished*****");

    }

    @Test(priority = 2, description = "verifying the activations and uninstalls count on Weekly graph and \"In the last 7 days\"")
    public void verifyActivationUninstallEventsWeeklyGraph() throws InterruptedException {

        logger.info("******verifyActivationUninstallEventsWeeklyGraph Test Started*****");
        UninstallsPage uninstallsPage = new UninstallsPage(driver);
        Map<String, Integer> eventsCountMap = uninstallsPage.getEventsCount();
        uninstallsPage.selectTrendTypeDropdown("Weekly");
        Map<String, Integer> tootipTextEvenetsMap = SeleniumUtils.getTooltipText(driver, pointsOnGraph, tooltipXpath);

        Assert.assertTrue(eventsCountMap.get("TotalNewActivtions").equals(tootipTextEvenetsMap.get("New activations")), "New activations count matches");
        Assert.assertTrue(eventsCountMap.get("TotalUninstalls").equals(tootipTextEvenetsMap.get("Uninstalls")), "New activations count matches");

        logger.info("******verifyActivationUninstallEventsWeeklyGraph Test Finished*****");

    }

    @Test(priority = 3, description = "verifying the activations and uninstalls count on Monthly graph and \"In the last 7 days\"")
    public void verifyActivationUninstallEventsMonthlyGraph() throws InterruptedException {
        logger.info("******verifyActivationUninstallEventsMonthlyGraph Test Started*****");

        UninstallsPage uninstallsPage = new UninstallsPage(driver);
        Map<String, Integer> eventsCountMap = uninstallsPage.getEventsCount();
        uninstallsPage.selectTrendTypeDropdown("Monthly");
        Map<String, Integer> tootipTextEvenetsMap = SeleniumUtils.getTooltipText(driver, pointsOnGraph, tooltipXpath);
        Assert.assertTrue(eventsCountMap.get("TotalNewActivtions").equals(tootipTextEvenetsMap.get("New activations")), "New activations count matches");
        Assert.assertTrue(eventsCountMap.get("TotalUninstalls").equals(tootipTextEvenetsMap.get("Uninstalls")), "New activations count matches");

        logger.info("******verifyActivationUninstallEventsMonthlyGraph Test Finished*****");

    }


    @Test(priority = 4, description = "verifying the total lost users against anderoid lost user and iOS lost users and \"In the last 7 days\"")
    public void verifyLostUsers() throws InterruptedException {

        logger.info("******verifyLostUsers Test Started*****");

        UninstallsPage uninstallsPage = new UninstallsPage(driver);
        SeleniumUtils.scrollDown(driver);
        int toalLostUsers = uninstallsPage.getTotalLostUsers();
        int androidLostUsers = uninstallsPage.getAndroidLostUsers();
        int iOSLostUsers = uninstallsPage.getIOSLostUsers();
        Assert.assertTrue(toalLostUsers == androidLostUsers + iOSLostUsers, "Users count is correct");

        logger.info("******verifyLostUsers Test Finished*****");

    }

    @Test(priority = 5, description = "Switch to flows tab and wait for page to load")
    public void verifySwitchingToFLowsTab() throws InterruptedException {

        logger.info("******verifySwitchingToFLowsTab Test Started*****");

        UninstallsPage uninstallsPage = new UninstallsPage(driver);
        uninstallsPage.switchMode();
        String flowsGridTitle = uninstallsPage.getFlowsGridTitle();

        Assert.assertTrue(flowsGridTitle.contains("Event flows- Analyze what users did before performing an event"), "flows trab launched");

        logger.info("******verifySwitchingToFLowsTab Test Finished*****");
    }

}
