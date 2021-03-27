package com.clevertap.ui.boards_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.boards.MobileApp;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;


public class MobileAppTest extends BaseTest{


    private static final String PASSED = "Passed";
    private static final String ID_TRENDGRAPH_DPU_DIV_CLASS_CT_CHART_DROP_DOWN_A = "//*[@id='trendgraphDpu']//div[@class='ctChart__dropDown']//a";
    private Logger logger;
    private WebDriver driver;
    private static final String last7Days = "In the last 7 days";
    private static final String last30Days = "In the last 30 days";
    private static final String last60Days = "In the last 60 days";
    private static final String mobileAppTrendDaily = "Daily";
    private static final String mobileAppTrendWeekly = "Weekly";
    private static final String mobileAppTrendMonthly = "Monthly";
    private final static String convertingUserTooltip = "//*[contains(@class,'highcharts-point') and (@fill='#02c664')]";
    private final static String activeUserTooltip = "//*[name()='path' and contains(@class,'highcharts-point') and (@fill='#009cf2')]";
    private final static String tooltipXpath = "//p[@class='chartTooltipLegend']";
    private final static String histogramUser = "//*[name()='rect' and contains(@class,'highcharts-point')]";
    private final static String appLaunchHistogram = "//*[name()='rect' and contains(@class,'highcharts-point')]";
    private static float totalActiveUser, totalDailyUsers, totalWeeklyUsers;
    private static float totalConvertingUser, totalDailyConverting, totalWeeklyConverting;


    @BeforeClass
    public void initialize() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.MOBILE_APP.toString(), "");
        logger = baseTest.configureLogger(getClass());

    }

    @Test(priority = 1, description = "Validate the Mobile Users for last 7 days")
    public void verifyLast7DaysMobileAppBoard() throws InterruptedException {
        logger.info("verifyLast7DaysMobileAppBoard");
        MobileApp mobileApp = new MobileApp(driver);
        mobileApp.selectCalendarDateInterval(MobileAppTest.last7Days);
        boolean _7DaysMobileAppBoard = mobileApp.verifyTotalActiveUsers(7);
        Assert.assertTrue(_7DaysMobileAppBoard, "Total MobileApp Board for 7 days is validated.");
        logger.info("verifyLast7DaysMobileAppBoard");
    }
    @Test(priority = 2, description = "verifying the MobileApp Trend on daily graph and \"In the last 7 days\"")
    public void verifyMobileAppTrendDailyGraph1()  {
        logger.info("verifyMobileAppDailyGraph");
        MobileApp mobileApp = new MobileApp(driver);
        mobileApp.selectTrendTypeDropdown1(MobileAppTest.mobileAppTrendDaily);
        SeleniumUtils.scrollUp(driver);
        Map<String, Integer> tootipTextEvenetsMap = SeleniumUtils.getTooltipText(driver, activeUserTooltip, tooltipXpath);
        totalActiveUser = mobileApp.getTotalActiveUsers();
        totalDailyUsers = mobileApp.getTotalDailyUsersValue();
        totalWeeklyUsers = mobileApp.getTotalWeeklyUsers();
        int toolTipTotalMobileApp = tootipTextEvenetsMap.get("Active Users Trend");
        Assert.assertTrue(toolTipTotalMobileApp == (int) totalActiveUser, "Active Users Trend for Mobile App");
        logger.info("verifyMobileAppTrendDailyGraphFor7Days");
    }

    @Test(priority = 3,description = "Validate the Mobile App board for last 30 days.")
    public void verifyLast30DaysMobileAppBoard()throws InterruptedException{
        logger.info("verifyLast30DaysRevenueBoard");
        MobileApp mobileApp = new MobileApp(driver);
        mobileApp.selectCalendarDateInterval(MobileAppTest.last30Days);
        SeleniumUtils.waitForElementToLoad(driver, mobileApp.getMobileAppMonthlyConvertingUsers());
        boolean _30DaysMobileAppBoard = mobileApp.verifyTotalConvertingUsers(30);
        Assert.assertTrue(_30DaysMobileAppBoard, "Total MobileApp Board for 30 days is validated.");
        logger.info("verifyLast30DaysMobileAppBoard");
    }

    @Test(priority = 4, description = "verifying the MobileApp Trend on daily graph and \"In the last 30 days\"")
    public void verifyMobileAppTrendDailyGraph2()  {
        logger.info("verifyMobileAppDailyGraph");
        MobileApp mobileApp = new MobileApp(driver);
        if (SeleniumUtils.getVisibility(By.xpath(ID_TRENDGRAPH_DPU_DIV_CLASS_CT_CHART_DROP_DOWN_A), 10, driver) != null) {
            driver.findElement(By.xpath(ID_TRENDGRAPH_DPU_DIV_CLASS_CT_CHART_DROP_DOWN_A)).click();
        } else {
            SeleniumUtils.scrollDown(driver);
            driver.findElement(By.xpath(ID_TRENDGRAPH_DPU_DIV_CLASS_CT_CHART_DROP_DOWN_A)).click();
        }
        mobileApp.selectTrendTypeDropdown2(MobileAppTest.mobileAppTrendWeekly);
        Map<String, Integer> tootipTextEvenetsMap = SeleniumUtils.getTooltipText(driver, convertingUserTooltip, tooltipXpath);
        totalConvertingUser = mobileApp.getTotalActiveConvertingUser();
        System.out.println(totalConvertingUser);
        totalDailyConverting = mobileApp.getTotalDailyConvertingUser();
        totalWeeklyConverting = mobileApp.getTotalWeeklyConvertingUser();
        int toolTipTotalMobileApp = tootipTextEvenetsMap.get("Converting Users Trend");
        System.out.println(toolTipTotalMobileApp);
        Assert.assertTrue(toolTipTotalMobileApp == (int) totalConvertingUser, "Converting Users Trend");
        logger.info("verifyMobileAppTrendDailyGraph");
    }
    @Test(priority = 5,description = "Validate the MobileApp board for last 60 days.")
    public void verifyLast60DaysMobileAppBoard()throws InterruptedException {
        logger.info(">>>verifyLast60DaysRevenueBoard");
        MobileApp mobileApp = new MobileApp(driver);
        SeleniumUtils.scrollUp(driver);
        mobileApp.selectCalendarDateInterval(MobileAppTest.last60Days);
        SeleniumUtils.waitForElementToLoad(driver, mobileApp.getMobileAppMonthlyActiveUsers());
        boolean _60DaysMobileAppBoard = mobileApp.verifyTotalActiveUsers(60);
        Assert.assertTrue(_60DaysMobileAppBoard, "Total MobileApp Board for 60 days is validated.");
        logger.info("verifyLast60DaysMobileAppBoard");
    }

    @Test(priority = 6, description = "verifying the MobileApp Trend on daily graph and \"In the last 30 days\"")
    public void verifyMobileAppTrendGraphForUsers(){
        logger.info("verifyMobileAppDailyGraphForUsers");
        SeleniumUtils.getTooltipText(driver,histogramUser,tooltipXpath);
        logger.info("verifyMobileAppTrendDailyGraphFor30Days");

    }
    @Test(priority = 7, description = "verifying the MobileApp Trend on daily graph and \"In the last 30 days\"")
    public void verifyMobileAppTrendGraphForApplaunchedUser(){
        logger.info("verifyMobileAppDailyGraphForAppLaunchedUser ");
        MobileApp mobileApp = new MobileApp(driver);
        mobileApp.selectRadioButton();
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.getTooltipText(driver,appLaunchHistogram,tooltipXpath);
        logger.info("verifyMobileAppTrendDailyGraphFor30Days");

    }
}