package com.clevertap.ui.analyze_test;//package org.CleverTap.tests.AnalyzeTest;
//
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;
//import org.CleverTap.pages.analyze_page.EventsPage;
//import com.clevertap.utils.report_utils.extent_report.ExtentTestManager;
//import org.CleverTap.utils.BaseTest;
//import org.CleverTap.utils.Mocha;
//import com.clevertap.utils.SeleniumUtils;
//import org.apache.log4j.Logger;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.ITestResult;
//import org.testng.annotations.*;
//import org.testng.asserts.SoftAssert;
//
//import java.util.Map;
//
//public class EventsTest{
//
//    private static final String PASSED = "Passed";
//    private static final String ALL_USERS = "All Users";
//    private static final String TODAY = "Today";
//    private static final String CHARGED = "Charged";
//    private static final String TREND_PROPERTIES = "Trend & Properties";
//    private static final String EVENTS = "Events";
//    private static final String PEOPLE = "People";
//    private static final String DAILY = "Daily";
//    private static final String WEEKLY = "Weekly";
//    private static final String MONTHLY = "Monthly";
//    private static final String APP_LAUNCHED = "App Launched";
//    private static final String THIS_MONTH = "This month";
//    private static final String NAME_PATH_AND_FILL_009_CF_2 = "//*[name()='path' and @fill='#009cf2']";
//    private static final String P_CLASS_CHART_TOOLTIP_LEGEND = "//p[@class='chartTooltipLegend']";
//    private static final String NAME_RECT_AND_STROKE_FFFFFF = "//*[name()='rect' and @stroke='#ffffff']";
//    private static final String USERS = "Users";
//    private static final String DIV_ID_EVENT_PROPERTY_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='eventProperty']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_CLEVER_TAP_CHART_2_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='CleverTap-Chart2']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String SESSION = "Session";
//    private static final String DIV_ID_HOUR_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='hour']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_TIME_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='time']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_PAGES_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='pages']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String GEOGRAPHY = "Geography";
//    private static final String DIV_ID_CITY_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='city']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_REGION_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='region']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_COUNTRY_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='country']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String TECHNOGRAPHICS = "Technographics";
//    private static final String DIV_ID_TECHNO_APP_FIELD_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='technoAppField']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_BROW_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='brow']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_OS_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='os']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_DEV_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='dev']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String DIV_ID_PROP_USER_FIELD_NAME_G_NAME_RECT_AND_STROKE_FFFFFF = "//div[@id='propUserField']//*[name()='g']//*[name()='rect' and @stroke='#ffffff']";
//    private static final String ANALYZE = "Analyze";
//    private static final String IN_THE_LAST_7_DAYS = "In the last 7 days";
//    private static final String TEST_ANKUSH = "Test - ankush";
//    private static final String ADDED_TO_CART = "Added To Cart";
//    private ExtentTest test;
//    private ExtentTest parentTest;
//    private Logger logger;
//    private WebDriver driver;
//    private static SoftAssert softAssert= new SoftAssert();
//
//
//        @BeforeClass(alwaysRun=true)
//        public void initialize(){
//            System.out.println("Initialize");
//            Mocha mocha = new Mocha();
//            mocha.openLeftNavMenu(true, ANALYZE, EVENTS, "");
//            BaseTest bt = new BaseTest();
//            driver= bt.getDriver();
//            parentTest = mocha.configureExtentReport("Test cases related to all actions on Event Page","");
//            logger = mocha.configureLogger();
//        }
//
//        @Test(description = "Verify Header of the Page", groups = {"Regression"})
//        public void verifyHeader(){
//            logger.info(">>>verifyHeader");
//            test = ExtentTestManager.startTest("verifyHeader", "Verifying if header text is correct");
//            parentTest.appendChild(test);
//            Mocha mocha = new Mocha();
//            mocha.openLeftNavMenu(false, ANALYZE, EVENTS, "");
//            EventsPage events = new EventsPage(driver);
//            Assert.assertEquals(events.getHeader(),ANALYZE+"/"+EVENTS+"/");
//            test.log(LogStatus.PASS, "Header Verified successfully");
//            ExtentTestManager.endTest();
//            logger.info("<<<verifyHeader");
//        }
//
//    @Test(groups = {"Regression","All"},dependsOnMethods = {"verifyHeader"},description = "Verify if events found match with the Detail page",dataProvider = "inputSegmentAndDuration")
//    public void verifyEventsFound(String segment, String duration,String event)throws InterruptedException{
//        logger.info(">>>verifyEventsFound");
//        test = ExtentTestManager.startTest("verifyEventsFound", "Verify if events found match with the Detail page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.choseSegment(segment);
//        events.choseDuration(duration);
//        events.choseEvent(event);
//        SeleniumUtils.waitForPageLoaded(driver);
//        int eventsFound = events.getEventsFound();
//        events.clickViewDetailsBtn();
//        SeleniumUtils.waitForPageLoaded(driver);
//        Assert.assertEquals(eventsFound,events.getDetailEventsFound());
//        test.log(LogStatus.PASS, "Header Verified successfully");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyEventsFound");
//    }
//
//    @DataProvider(name = "inputSegmentAndDuration")
//    public Object[][] inputSegmentAndDuration(){
//        return new String[][]{
//                {ALL_USERS,TODAY,APP_LAUNCHED},
//                {TEST_ANKUSH,IN_THE_LAST_7_DAYS, ADDED_TO_CART}
//        };
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if users are correctly divided into device types")
//    public void verifyUsersDividedAsPerDevices()throws InterruptedException{
//        logger.info(">>>verifyUsersDividedAsPerDevices");
//        test = ExtentTestManager.startTest("verifyUsersDividedAsPerDevices", "Verify if users are correctly divided into device types");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.choseSegment(ALL_USERS);
//        events.choseDuration(IN_THE_LAST_7_DAYS);
//        events.clickViewDetailsBtn();
//        Assert.assertEquals(events.getNumberOfUsers(),events.getTotalUsersFromSegregation());
//        logger.info(">>>verifyCentPercent");
//        softAssert.assertEquals(events.getTotalPercentage(),100);
//        logger.info("<<<verifyCentPercent");
//        logger.info(">>>verifyCentPercentOfGender");
//        softAssert.assertEquals(events.getMalePercent() + events.getFemalePercent(),100);
//        logger.info("<<<verifyCentPercentOfGender");
//        logger.info(">>>verifyProfileLink");
//        events.clickProfile();
//        softAssert.assertEquals(events.getProfileHeader() , "Profiles");
//        logger.info("<<<verifyProfileLink");
//        test.log(LogStatus.PASS, "Device type division Verified successfully");
//        ExtentTestManager.endTest();
//        softAssert.assertAll();
//        logger.info("<<<verifyUsersDividedAsPerDevices");
//
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if graph gives correct numbers", dataProvider = "provideDataToVerifyGraphForEvents")
//    public void verifyGraphForEvents(String segment, String duration, String event){
//        logger.info(">>>verifyGraphForEvents");
//        test = ExtentTestManager.startTest("verifyGraphForEvents", "Verify if graph gives correct numbers");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(segment, duration, event);
//        SeleniumUtils.waitForPageLoaded(driver);
//        events.clickEventDetailsBtn(TREND_PROPERTIES);
//        SeleniumUtils.scrollDown(driver,"550");
//        events.clickRadioBtn(EVENTS);
//        logger.info(">>>verifyDailyGraphForEvents");
//        events.selectGraphDuration(DAILY);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> dailyEvent = SeleniumUtils.getTooltipText(driver, NAME_PATH_AND_FILL_009_CF_2, P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = dailyEvent.get(event);
//        logger.info("Calculated events from graph = " + totalEvents);
//        int eventListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventListedOnPage);
//        softAssert.assertEquals(totalEvents,eventListedOnPage);
//        logger.info("<<<verifyDailyGraphForEvents");
//        logger.info(">>>verifyWeeklyGraphForEvents");
//        events.selectGraphDuration(WEEKLY);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> WeeklyEvent = SeleniumUtils.getTooltipText(driver, NAME_PATH_AND_FILL_009_CF_2, P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalWeeklyEvents = WeeklyEvent.get(event);
//        logger.info("Calculated events from graph = " + totalWeeklyEvents);
//        softAssert.assertEquals(totalWeeklyEvents,eventListedOnPage);
//        logger.info("<<<verifyWeeklyGraphForEvents");
//        logger.info(">>>verifyMonthlyGraphForEvents");
//        events.selectGraphDuration(MONTHLY);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> monthlyEvent = SeleniumUtils.getTooltipText(driver, NAME_PATH_AND_FILL_009_CF_2, P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalMonthlyEvents = monthlyEvent.get(event);
//        logger.info("Calculated events from graph = " + totalMonthlyEvents);
//        softAssert.assertEquals(totalMonthlyEvents,eventListedOnPage);
//        logger.info("<<<verifyMonthlyGraphForEvents");
//        test.log(LogStatus.PASS, "Graph verified successfully for Events");
//        ExtentTestManager.endTest();
//        softAssert.assertAll();
//        logger.info("<<<verifyGraphForEvents");
//    }
//
//    @DataProvider(name = "provideDataToVerifyGraphForEvents")
//    public Object[][] inputSegmentAndDurationWithGraphDuration(){
//        return new String[][]{
//                {ALL_USERS,THIS_MONTH,APP_LAUNCHED},
//                {ALL_USERS,IN_THE_LAST_7_DAYS,APP_LAUNCHED},
//                {ALL_USERS,THIS_MONTH,CHARGED}
//        };
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if graph gives correct numbers", dataProvider = "provideDataToVerifyGraphForPeople")
//    public void verifyGraphForPeople(String segment, String duration, String event)throws InterruptedException{
//        logger.info(">>>verifyGraphForPeople");
//        test = ExtentTestManager.startTest("verifyGraphForPeople", "Verify if graph gives correct numbers for People");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(segment, duration, event);
//        events.clickEventDetailsBtn(TREND_PROPERTIES);
//        SeleniumUtils.scrollDown(driver,"550");
//        events.clickRadioBtn(PEOPLE);
//        logger.info(">>>verifyDailyGraphForEvents");
//        SeleniumUtils.waitForPageLoaded(driver);
//        events.selectGraphDuration(DAILY);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> dailyEvent = SeleniumUtils.getTooltipText(driver, NAME_PATH_AND_FILL_009_CF_2, P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalUsers = dailyEvent.get(event);
//        logger.info("Calculated users from graph = " + totalUsers);
//        int usersListedOnPage = events.getNumberOfUsers();
//        logger.info("Users listed on page = " + usersListedOnPage);
//        softAssert.assertTrue(totalUsers>=usersListedOnPage);
//        logger.info("<<<verifyDailyGraphForEvents");
//        logger.info(">>>verifyWeeklyGraphForEvents");
//        SeleniumUtils.waitForPageLoaded(driver);
//        events.selectGraphDuration(WEEKLY);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> weeklyEvent = SeleniumUtils.getTooltipText(driver, NAME_PATH_AND_FILL_009_CF_2, P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalUsersWeekly = weeklyEvent.get(event);
//        logger.info("Calculated users from graph = " + totalUsersWeekly);
//        softAssert.assertTrue(totalUsersWeekly>=usersListedOnPage);
//        logger.info("<<<verifyWeeklyGraphForEvents");
//        logger.info(">>>verifyMonthlyGraphForEvents");
//        SeleniumUtils.waitForPageLoaded(driver);
//        events.selectGraphDuration(MONTHLY);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> monthlyEvent = SeleniumUtils.getTooltipText(driver, NAME_PATH_AND_FILL_009_CF_2, P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalMonthlyUsers = monthlyEvent.get(event);
//        logger.info("Calculated users from graph = " + totalMonthlyUsers);
//        softAssert.assertTrue(totalMonthlyUsers>=usersListedOnPage);
//        logger.info("<<<verifyMonthlyGraphForEvents");
//        test.log(LogStatus.PASS, "Graph verified successfully for People");
//        ExtentTestManager.endTest();
//        softAssert.assertAll();
//        logger.info("<<<verifyGraphForPeople");
//    }
//
//    @DataProvider(name = "provideDataToVerifyGraphForPeople")
//    public Object[][] inputs(){
//        return new String[][]{
//                {ALL_USERS,THIS_MONTH,APP_LAUNCHED},
//                {ALL_USERS,IN_THE_LAST_7_DAYS,APP_LAUNCHED},
//                {ALL_USERS,THIS_MONTH,CHARGED}
//        };
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Histogram gives correct numbers")
//    public void verifyHistogram()throws InterruptedException{
//        logger.info(">>>verifyHistogram");
//        test = ExtentTestManager.startTest("verifyHistogram", "Verify if graph gives correct numbers for People");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, APP_LAUNCHED);
//        events.clickEventDetailsBtn(TREND_PROPERTIES);
//        SeleniumUtils.scrollToBottom(driver);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver,DIV_ID_CLEVER_TAP_CHART_2_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalUsers = map.get(USERS);
//        logger.info("Calculated users from graph = " + totalUsers);
//        SeleniumUtils.scrollUp(driver);
//        int usersListedOnPage = events.getNumberOfUsers();
//        logger.info("Users listed on page = " + usersListedOnPage);
//        Assert.assertTrue(totalUsers>=usersListedOnPage);
//        test.log(LogStatus.PASS, "Histogram verified successfully");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyHistogram");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph gives correct numbers")
//    public void verifyBarGraph(){
//        logger.info(">>>verifyBarGraph");
//        test = ExtentTestManager.startTest("verifyBarGraph", "Verify if Bar graph gives correct numbers for People");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, APP_LAUNCHED);
//        events.clickEventDetailsBtn(TREND_PROPERTIES);
//        SeleniumUtils.scrollToBottom(driver);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver,DIV_ID_EVENT_PROPERTY_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents<=eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph verified successfully");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraph");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph gives correct numbers on session page")
//    public void verifyBarGraphOnSessionPage(){
//        logger.info(">>>verifyBarGraphOnSessionPage");
//        test = ExtentTestManager.startTest("verifyBarGraphOnSessionPage", "Verify if Bar graph gives correct numbers for Events on session page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, APP_LAUNCHED);
//        events.clickEventDetailsBtn(SESSION);
//        SeleniumUtils.scrollDown(driver,"550");
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_HOUR_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertEquals(totalEvents,eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph verified successfully on session page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphOnSessionPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by minutes gives correct numbers on session page")
//    public void verifyBarGraphByMinutesOnSessionPage(){
//        logger.info(">>>verifyBarGraphOnSessionPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByMinutesOnSessionPage", "Verify if Bar graph by minutes gives correct numbers for Events on session page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, CHARGED);
//        events.clickEventDetailsBtn(SESSION);
//        SeleniumUtils.scrollDown(driver,"1000");
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_TIME_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertEquals(totalEvents,eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by minutes verified successfully on session page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByMinutesOnSessionPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by Pages gives correct numbers on session page")
//    public void verifyBarGraphByPagesOnSessionPage() throws InterruptedException{
//        logger.info(">>>verifyBarGraphByPagesOnSessionPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByPagesOnSessionPage", "Verify if Bar graph by Pages gives correct numbers for Events on session page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, CHARGED);
//        events.clickEventDetailsBtn(SESSION);
//        SeleniumUtils.scrollToBottom(driver);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver,DIV_ID_PAGES_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        SeleniumUtils.waitForPageLoaded(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertEquals(totalEvents,eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by Pages verified successfully on session page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByPagesOnSessionPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by cities gives correct numbers on Geography page")
//    public void verifyBarGraphByCitiesOnGeographyPage(){
//        logger.info(">>>verifyBarGraphByCitiesOnGeographyPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByCitiesOnGeographyPage", "Verify if Bar Graph by cities gives correct numbers on Geography page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, CHARGED);
//        SeleniumUtils.waitForPageLoaded(driver);
//        events.clickEventDetailsBtn(GEOGRAPHY);
//        SeleniumUtils.scrollDown(driver,"550");
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_CITY_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents <= eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by cities verified successfully on geography page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByCitiesOnGeographyPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by Region gives correct numbers on Geography page")
//    public void verifyBarGraphByRegionOnGeographyPage()throws InterruptedException{
//        logger.info(">>>verifyBarGraphByRegionOnGeographyPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByRegionOnGeographyPage", "Verify if Bar Graph by Region gives correct numbers on Geography page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, CHARGED);
//        events.clickEventDetailsBtn(GEOGRAPHY);
//        SeleniumUtils.scrollDown(driver,"1100");
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_REGION_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents <= eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by Region verified successfully on geography page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByRegionOnGeographyPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by Country gives correct numbers on Geography page")
//    public void verifyBarGraphByCountryOnGeographyPage() throws InterruptedException{
//        logger.info(">>>verifyBarGraphByCountryOnGeographyPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByCountryOnGeographyPage", "Verify if Bar Graph by Country gives correct numbers on Geography page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, APP_LAUNCHED);
//        events.clickEventDetailsBtn(GEOGRAPHY);
//        SeleniumUtils.scrollToBottom(driver);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_COUNTRY_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents <= eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by Country verified successfully on geography page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByCountryOnGeographyPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by App Field gives correct numbers on Techno Graphics page")
//    public void verifyBarGraphByAppFieldOnTechnoGraphicsPage()throws InterruptedException{
//        logger.info(">>>verifyBarGraphByAppFieldOnTechnoGraphicsPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByAppFieldOnTechnoGraphicsPage", "Verify if Bar Graph by App Field gives correct numbers on Techno Graphics page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, CHARGED);
//        events.clickEventDetailsBtn(TECHNOGRAPHICS);
//        SeleniumUtils.scrollDown(driver,"550");
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_TECHNO_APP_FIELD_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents <= eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by App Field verified successfully on geography page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByAppFieldOnTechnoGraphicsPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by App Field gives correct numbers on Techno Graphics page")
//    public void verifyBarGraphByBrowserOnTechnoGraphicsPage()throws InterruptedException{
//        logger.info(">>>verifyBarGraphByBrowserOnTechnoGraphicsPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByBrowserOnTechnoGraphicsPage", "Verify if Bar Graph by App Field gives correct numbers on Techno Graphics page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, CHARGED);
//        events.clickEventDetailsBtn(TECHNOGRAPHICS);
//        SeleniumUtils.scrollDown(driver,"1100");
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_BROW_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents <= eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by Browser verified successfully on Techno Graphics page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByBrowserOnTechnoGraphicsPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by OS gives correct numbers on Techno Graphics page")
//    public void verifyBarGraphByOSOnTechnoGraphicsPage()throws InterruptedException{
//        logger.info(">>>verifyBarGraphByOSOnTechnoGraphicsPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByOSOnTechnoGraphicsPage", "Verify if Bar Graph by OS gives correct numbers on Techno Graphics page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, CHARGED);
//        events.clickEventDetailsBtn(TECHNOGRAPHICS);
//        SeleniumUtils.scrollDown(driver,"550");
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_OS_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents <= eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by OS verified successfully on Techno Graphics page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByOSOnTechnoGraphicsPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by Devices gives correct numbers on Techno Graphics page")
//    public void verifyBarGraphByDevicesOnTechnoGraphicsPage()throws InterruptedException{
//        logger.info(">>>verifyBarGraphByDevicesOnTechnoGraphicsPage");
//        test = ExtentTestManager.startTest("verifyBarGraphByDevicesOnTechnoGraphicsPage", "Verify if Bar Graph by Devices gives correct numbers on Techno Graphics page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, APP_LAUNCHED);
//        events.clickEventDetailsBtn(TECHNOGRAPHICS);
//        SeleniumUtils.scrollToBottom(driver);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_DEV_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents <= eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by Devices verified successfully on Techno Graphics page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByDevicesOnTechnoGraphicsPage");
//    }
//
//    @Test(groups = {"Regression","All"},description = "Verify if Bar Graph by Devices gives correct numbers on People page")
//    public void verifyBarGraphByUserPropOnPeoplePage()throws InterruptedException{
//        logger.info(">>>verifyBarGraphByUserPropOnPeoplePage");
//        test = ExtentTestManager.startTest("verifyBarGraphByUserPropOnPeoplePage", "Verify if Bar Graph by User Prop gives correct numbers on People page");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false,ANALYZE,EVENTS,"");
//        EventsPage events = new EventsPage(driver);
//        events.viewDetailsOf(ALL_USERS, THIS_MONTH, APP_LAUNCHED);
//        events.clickEventDetailsBtn(PEOPLE);
//        SeleniumUtils.scrollToBottom(driver);
//        SeleniumUtils.waitForPageLoaded(driver);
//        Map<String, Integer> map = SeleniumUtils.getTooltipText(driver, DIV_ID_PROP_USER_FIELD_NAME_G_NAME_RECT_AND_STROKE_FFFFFF,P_CLASS_CHART_TOOLTIP_LEGEND);
//        int totalEvents = map.get(EVENTS);
//        logger.info("Calculated Events from Bar graph = " + totalEvents);
//        SeleniumUtils.scrollUp(driver);
//        int eventsListedOnPage = events.getDetailEventsFound();
//        logger.info("Events listed on page = " + eventsListedOnPage);
//        Assert.assertTrue(totalEvents <= eventsListedOnPage);
//        test.log(LogStatus.PASS, "BarGraph by User Prop verified successfully on People page");
//        ExtentTestManager.endTest();
//        logger.info("<<<verifyBarGraphByUserPropOnPeoplePage");
//    }
//
//
//    @AfterMethod(alwaysRun=true)
//    private void testResult(ITestResult result) throws Exception {
//            Mocha.setResult(result, test);
//        }
//
//        @AfterClass(alwaysRun=true)
//        private void tearDown(){
//            logger.info("closing the chrome browser and driver");
//            driver.close();
//            driver.quit();
//        }
//    }
//
//
