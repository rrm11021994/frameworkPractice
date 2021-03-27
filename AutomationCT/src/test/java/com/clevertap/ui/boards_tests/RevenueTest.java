package com.clevertap.ui.boards_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.boards.Revenue;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TableUtility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class RevenueTest extends BaseTest{

    private Logger logger;
    private WebDriver driver;
    private static final String PASSED = "Passed";
    private static final String last30Days = "In the last 30 days";
    private static final String last7Days = "In the last 7 days";
    private static final String revenueTrendDaily = "Daily";
    private static final String revenueTrendWeekly = "Weekly";
    private static final String revenueTrendMonthly = "Monthly";

    String pointsOnGraph = "//*[contains(@class,'highcharts-point') and (@fill='#02c664')]";
    //String tooltipXpath = "//*[contains(@class,'highcharts-tooltip')]";
    String tooltipXpath = "//p[@class='chartTooltipLegend']";

    float totalRevenue, revenuePerPayingUser, avgDailyRevenueUI;
    int totalPayingUsers;

    @BeforeClass
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.REVENUE.toString(), "");
        logger = baseTest.configureLogger(getClass());
    }

    @Test(priority = 1,description = "Validate the Revenue board for last 7 days")
    public void verifyLast7DaysRevenueBoard() throws InterruptedException{
        logger.info(">>>verifyLast7DaysRevenueBoard");
        Revenue revenue = new Revenue(driver);
        revenue.selectCalendarDateInterval(RevenueTest.last7Days);
        SeleniumUtils.waitForElementToLoad(driver, revenue.getRevenuePerPayingUser());
        boolean _7DaysRevnueBoard = revenue.verifyTotalRevenueBoard(7);
        Assert.assertTrue(_7DaysRevnueBoard, "Total Revenue Board for 7 days is validated.");
        logger.info(">>>verifyLast7DaysRevenueBoard");
    }

    @Test(priority = 2,description = "Validate the Revenue board for last 30 days.")
    public void verifyLast30DaysRevenueBoard(){
        logger.info(">>>verifyLast30DaysRevenueBoard");
        Revenue revenue = new Revenue(driver);
        revenue.selectCalendarDateInterval(RevenueTest.last30Days);
        SeleniumUtils.waitForElementToLoad(driver, revenue.getRevenuePerPayingUser());
        boolean _30DaysRevenueBoard = revenue.verifyTotalRevenueBoard(30);
        Assert.assertTrue(_30DaysRevenueBoard, "Total Revenue Board for 30 days is validated.");
    }

    @Test(priority = 3, description = "verifying the Revenue Trend on daily graph and \"In the last 30 days\"")
    public void verifyRevenueTrendDailyGraph() throws InterruptedException {
        logger.info(">>>verifyRevenueTrendDailyGraph");
        Revenue revenue = new Revenue(driver);

        revenue.selectTrendTypeDropdown(RevenueTest.revenueTrendDaily);

        Map<String, Integer> tootipTextEvenetsMap = SeleniumUtils.getTooltipText(driver, pointsOnGraph, tooltipXpath);
        totalRevenue = revenue.getTotalRevenuevalue();
        totalPayingUsers = revenue.getTotalPayingUsersValue();
        revenuePerPayingUser = revenue.getRevenuePerPayingUserValue();
        avgDailyRevenueUI = revenue.getAvgDailyRevenueValue();
        int toolTipTotalRevenue = tootipTextEvenetsMap.get("Total revenue");
        Assert.assertTrue(toolTipTotalRevenue == (int) totalRevenue, "Total revenue matches with the graph revenue.");
        logger.info(">>>verifyRevenueTrendDailyGraph");
    }

    @Test(priority = 4, description = "verifying the Revenue Trend on Weekly graph and \"In the last 30 days\"")
    public void verifyRevenueTrendWeeklyGraph() throws InterruptedException {

        logger.info("******verifyRevenueTrendWeeklyGraph Test Started*****");
        Revenue revenue = new Revenue(driver);
        revenue.selectTrendTypeDropdown(RevenueTest.revenueTrendWeekly);


        Map<String, Integer> tootipTextEvenetsMap = SeleniumUtils.getTooltipText(driver, pointsOnGraph, tooltipXpath);

        int toolTipTotalRevenue = tootipTextEvenetsMap.get("Total revenue");
        Assert.assertTrue(toolTipTotalRevenue == (int) totalRevenue, "Total revenue matches with the graph revenue.");
        logger.info("******verifyRevenueTrendWeeklyGraph Test Started*****");
    }

    @Test(priority = 5, description = "verifying the Revenue Trend on Monthly graph and \"In the last 30 days\"")
    public void verifyRevenueTrendMonthlyGraph() throws InterruptedException {

        logger.info("******verifyRevenueTrendMonthlyGraph Test Started*****");
        Revenue revenue = new Revenue(driver);
        revenue.selectTrendTypeDropdown(RevenueTest.revenueTrendMonthly);
        Map<String, Integer> tootipTextEvenetsMap = SeleniumUtils.getTooltipText(driver, pointsOnGraph, tooltipXpath);

        int toolTipTotalRevenue = tootipTextEvenetsMap.get("Total revenue");
        Assert.assertTrue(toolTipTotalRevenue == (int) totalRevenue, "Total revenue matches with the graph revenue.");
        logger.info("******verifyRevenueTrendMonthlyGraph Test Started*****");
    }

    @Test(priority = 6, description = "verifying the details of More tab with Quick view.")
    public void verifyMoreDetailsTab() throws InterruptedException {

        logger.info("******verifyMoreDetailsTab Test Started*****");
        Revenue revenue = new Revenue(driver);
        SeleniumUtils.scrollUpAndClickElement(driver, revenue.getMoreDetailsView());
        TableUtility.InitializeTable(driver, revenue.moreDetailsTableXpath);
        SeleniumUtils.waitForElementToLoad(driver, driver.findElement(By.xpath(revenue.moreDetailsTableXpath)));
        List<Map<String, Object>> moreDetailaTableData = revenue.getSingleTableData(revenue.moreDetailsTableXpath);
        float totalRevenueDetail = 0, totalAvgDailyRevenueDetail = 0, totalRevenuePerUser = 0;
        int totalPayingUsersDetail = 0;
        for(int i = 0; i < moreDetailaTableData.size(); i++){
            Map<String, Object> map = moreDetailaTableData.get(i);
            totalRevenueDetail = totalRevenueDetail + Float.parseFloat(map.get("Total revenue").toString().replace(",", ""));
            totalAvgDailyRevenueDetail = totalAvgDailyRevenueDetail + Float.parseFloat(map.get("Average daily revenue").toString().replace(",", ""));
            totalRevenuePerUser = totalRevenuePerUser + Float.parseFloat(map.get("Revenue/Paying User").toString().replace(",", ""));
            totalPayingUsersDetail = totalPayingUsersDetail + Integer.parseInt(map.get("Total paying users").toString().replace(",", ""));
        }

        Assert.assertTrue((int) totalRevenueDetail == (int) totalRevenue, "Total revenue matches with the more detailed revenue.");
        Assert.assertTrue((int) totalAvgDailyRevenueDetail == (int) avgDailyRevenueUI, "Total revenue matches with the more detailed revenue.");
        Assert.assertTrue((int) totalRevenuePerUser == (int) revenuePerPayingUser, "Total revenue matches with the more detailed revenue.");
        Assert.assertTrue(totalPayingUsersDetail ==  totalPayingUsers, "Total revenue matches with the more detailed revenue.");

        logger.info("******verifyMoreDetailsTab Test Finished*****");

    }

}
