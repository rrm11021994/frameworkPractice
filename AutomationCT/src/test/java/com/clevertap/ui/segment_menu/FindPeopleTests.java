package com.clevertap.ui.segment_menu;

import com.clevertap.BaseTest;
import com.clevertap.api.ResponseFactory;
import com.clevertap.ui.pages.CTPageFactory;
import com.clevertap.ui.pages.segment_pages.FindPeoplePage;
import com.clevertap.utils.*;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.clevertap.utils.RestApiUtil.ApiUtility.getJsonBody;
import static com.clevertap.utils.RestApiUtil.ApiUtility.getJsonObjectFromJsonFile;

public class FindPeopleTests extends BaseTest{

    private static final String EVENT_PROPERTY = "Event property";
    private static final String APP_INSTALLED = "App Installed";
    private static final String ADDED_TO_CART = "Added To Cart";
    private static final String PRODUCT_RATED = "Product rated";
    private static final String DEMOGRAPHICS = "Demographics";
    private static final String SAVE_SEGMENT = "Save segment";
    private static final String PROFILES = "Profiles";
    private static String AUTOMATION_SEGMENT = "AutomationSegment";
    private Logger logger;
    private WebDriver driver;
    private static JSONObject jsonObject = null;
    private final static String campaignsPath = "Home/Segment/Find People";
    private final static String bookmarkName = "AutomationSaveSegmentBookMark";
    private final static String bookmarkType = "Segments";
    private static final String FILTER_NAME = "Event property";
    private int eventPushCounter = 0;
    private FindPeoplePage findPeoplePage;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver = getDriver();
        findPeoplePage = new FindPeoplePage(driver);
        AUTOMATION_SEGMENT=Data.randomAlphaNumeric(AUTOMATION_SEGMENT,4);
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        logger = configureLogger(getClass());
    }

    @Test(description = "Verify segment is created and saved successfully", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.FINDPPLCRITICAL})
    public void findPeopleSaveSegments() throws InterruptedException {
        try {
            logger.info("******findPeople_SaveSegments Test Started*****");
            Reporter.log("Segment name used for creation is : " + AUTOMATION_SEGMENT, true);
            Mocha.forceNavigate=true;
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
            FindPeoplePage findPeoplePage = new FindPeoplePage(driver);
            findPeoplePage.viewDetails();
//        SeleniumUtils.waitAndClick(driver,findPeoplePage.viewDetail);
            findPeoplePage.saveSegments(AUTOMATION_SEGMENT);
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
            List<String> listOfSegments = findPeoplePage.getTableCellData();
            Assert.assertTrue(listOfSegments.contains(AUTOMATION_SEGMENT), "segment view details/save segment");
            logger.info("******findPeople_SaveSegments Test Finished*****");
        }finally {
            Mocha.forceNavigate=false;
        }

    }

    @Test(description = "Verify segment is bookmarked successfully", groups = {TestCaseGroups.REGRESSION})
    public void saveSegmentCreateBookmark() throws InterruptedException {
        Reporter.log("******SaveSegment_CreateBookmark Test Started*****",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        findPeoplePage.viewDetails();
        findPeoplePage.saveSegmentAsBookmark();
        findPeoplePage.setAsBookmark(bookmarkName);
        List<String> actualBookmarkList = findPeoplePage.getBookmarksList();
        findPeoplePage.deleteBookmark(bookmarkType, bookmarkName);
        Assert.assertTrue(actualBookmarkList.contains(bookmarkName), "save segment bookmark created");
        logger.info("******findPeople_CreateBookmark Test Finished*****");


    }

    //@Test(description = "Verify CSV download", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.FINDPPLCRITICAL})
    public void findPeopleStartDownloading() {
        logger.info("******findPeople_startDownloading Test Started*****");
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        FindPeoplePage findPeoplePage = new FindPeoplePage(driver);
        findPeoplePage.viewDetails();
//        findPeoplePage.deleteExistingCSVFiles();
        findPeoplePage.startDownload();
        findPeoplePage.readCSVFiles();
        logger.info("******fp_startDownloading Test Finished*****");


    }

    @Test(description = "Verify  create link hyperlink is working", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.FINDPPLCRITICAL})
    public void findPeopleCreateCampaign() {
        try {
            logger.info("******fp_CreateCampaign Test Started*****");
            Mocha.forceNavigate = true;
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
            findPeoplePage.viewDetails();
            findPeoplePage.verifyCreateCampaign();
            Assert.assertTrue(true, "Create Campaign link is working");
            logger.info("******fp_CreateCampaign Test Finished*****");
        }finally {
            Mocha.forceNavigate = false;
        }
    }

//    @Test(description = "Verifying male and female percentage", dependsOnMethods = {"viewDetailsOnApplyingFilters"}, groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.FINDPPLCRITICAL})
////    @Test(description = "verify different filters applied successfully", dependsOnMethods = {"findPeopleCreateCampaign"}, groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.FINDPPLCRITICAL})
////    public void viewDetailsOnApplyingFilters() {
////        logger.info("******viewDetailsOnApplyingFilters Test Started*****");
////        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
////        /*Creating list of event properties*/
////        List<String> eventPropertiesLikeType = new ArrayList<>();
////        eventPropertiesLikeType.add(EVENT_PROPERTY);
////        List<String> eventPropertiesLikeEvents = new ArrayList<>();
////        eventPropertiesLikeEvents.add(APP_INSTALLED);
////        List<String> eventPropertiesEventsType = new ArrayList<>();
////        eventPropertiesEventsType.add(ADDED_TO_CART);
////        List<String> didNotWidgeFilterItems = new ArrayList<>();
////        didNotWidgeFilterItems.add(PRODUCT_RATED);
////        List<String> eventPropertiesCommonProperty = new ArrayList<>();
////        eventPropertiesCommonProperty.add(DEMOGRAPHICS);
////        FindPeoplePage findPeoplePage = new FindPeoplePage(driver);
//////        SegmentWidget segmentWidget = new SegmentWidget(driver);
//////        DidNotWidget didNotWidget = new DidNotWidget(driver);
//////        segmentWidget.enterAddLikeDidEventsFilters(eventPropertiesLikeType, eventPropertiesLikeEvents);
////
////        /* Tested and working functions
////        user who did*/
////
//////        DidWidget didWidget = new DidWidget(driver);
//////        didWidget.enterUserWhoDidFilters(eventPropertiesEventsType); /*Need to provide valid filter to get the data*/
////
////
////        /*user who did not*/
//////        didNotWidget.enterUserWhoDidNotFilters(didNotWidgeFilterItems);
////
////        /*Set common property*/
//////        CommonPropertyProfileWidget commonPropertyProfileWidget = new CommonPropertyProfileWidget(driver);
//////        commonPropertyProfileWidget.enterCommonPropertyFilters(eventPropertiesCommonProperty); /*This element is getting clicked but dropdown not opening*/
////        findPeoplePage.enterViewDetails();
////        String saveSegment = findPeoplePage.getSaveSegmentText();
////        Assert.assertTrue(saveSegment.contains(SAVE_SEGMENT), "view details are displayed");
////        logger.info("******viewDetailsOnApplyingFilters Test Finished*****");
////    }
//
    @Test(description = "Verifying male and female percentage", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.FINDPPLCRITICAL})
    public void verifyMaleFemalePercentage() {
        try {
            /*Write a code to add male and female percentage and it should be 100*/
            Mocha.forceNavigate=true;
            SeleniumUtils.pause(2);
            Reporter.log("******verifyMaleFemalePercentage Test Started*****",true);
//            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

            if(driver.findElements(By.xpath("//ul[@data-parent-menu='Segment' and @style='display: none;']")).size()>0){
                SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]")));
            }
            SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]/../..//a[contains(text(),'Find People')]")));

            findPeoplePage.viewDetails();
            SeleniumUtils.waitForPageLoaded(driver);//it is required coz value of percentage takes time to load
            String maleDemographicPercentOnStaging = findPeoplePage.getMaleDemographicPercent();
            Reporter.log("******male percentage on staging env***** " + maleDemographicPercentOnStaging,true);
            String femaleDemographicPercentOnStaging = findPeoplePage.getFemaleDemographicPercent();
            Reporter.log("******female percentage on staging env***** " + femaleDemographicPercentOnStaging,true);

            ((JavascriptExecutor) driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            driver.get(Mocha.prodUrl);
            SeleniumUtils.pause(2);
            Mocha.forceNavigate=true;

            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

//            if(driver.findElements(By.xpath("//ul[@data-parent-menu='Segment' and @style='display: none;']")).size()>0){
                SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]")));
                SeleniumUtils.pause(1);
//            }
//            SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]/../..//a[contains(text(),'Find People')]")));

            findPeoplePage.viewDetails();
            SeleniumUtils.waitForPageLoaded(driver);//it is required coz value of percentage takes time to load
            SeleniumUtils.scrollDownLittle(driver);
            String maleDemographicPercentOnProd = findPeoplePage.getMaleDemographicPercent();
            Reporter.log("******male percentage on prod env***** " + maleDemographicPercentOnProd,true);

            String femaleDemographicPercentOnProd = findPeoplePage.getFemaleDemographicPercent();
            Reporter.log("******female percentage on prod env***** " + femaleDemographicPercentOnProd,true);
            Assert.assertEquals(maleDemographicPercentOnStaging,maleDemographicPercentOnProd,"values of male percentage is not matching on prod and staging env");
            Assert.assertEquals(femaleDemographicPercentOnStaging,femaleDemographicPercentOnProd,"values of female percentage is not matching on prod and staging env");

            driver.close();
            driver.switchTo().window(tabs.get(0));
            Reporter.log("******verifyMaleFemalePercentage Test Finished*****",true);
        }finally {
            Mocha.forceNavigate=false;
        }
    }

    @Test(description = "verify different filters applied successfully", dependsOnMethods = {"findPeopleCreateCampaign"}, groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL})
    public void viewDetailsOnApplyingFilters() {
        logger.info("******viewDetailsOnApplyingFilters Test Started*****");
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        /*Creating list of event properties*/
        List<String> eventPropertiesLikeType = new ArrayList<>();
        eventPropertiesLikeType.add(EVENT_PROPERTY);
        List<String> eventPropertiesLikeEvents = new ArrayList<>();
        eventPropertiesLikeEvents.add(APP_INSTALLED);
        List<String> eventPropertiesEventsType = new ArrayList<>();
        eventPropertiesEventsType.add(ADDED_TO_CART);
        List<String> didNotWidgeFilterItems = new ArrayList<>();
        didNotWidgeFilterItems.add(PRODUCT_RATED);
        List<String> eventPropertiesCommonProperty = new ArrayList<>();
        eventPropertiesCommonProperty.add(DEMOGRAPHICS);
//        SegmentWidget segmentWidget = new SegmentWidget(driver);
//        DidNotWidget didNotWidget = new DidNotWidget(driver);
//        segmentWidget.enterAddLikeDidEventsFilters(eventPropertiesLikeType, eventPropertiesLikeEvents);

        /* Tested and working functions
        user who did*/

//        DidWidget didWidget = new DidWidget(driver);
//        didWidget.enterUserWhoDidFilters(eventPropertiesEventsType); /*Need to provide valid filter to get the data*/


        /*user who did not*/
//        didNotWidget.enterUserWhoDidNotFilters(didNotWidgeFilterItems);

        /*Set common property*/
//        CommonPropertyProfileWidget commonPropertyProfileWidget = new CommonPropertyProfileWidget(driver);
//        commonPropertyProfileWidget.enterCommonPropertyFilters(eventPropertiesCommonProperty); /*This element is getting clicked but dropdown not opening*/
        findPeoplePage.enterViewDetails();
        String saveSegment = findPeoplePage.getSaveSegmentText();
        Assert.assertTrue(saveSegment.contains(SAVE_SEGMENT), "view details are displayed");
        logger.info("******viewDetailsOnApplyingFilters Test Finished*****");
    }

    @Test(description = "Verifying kundli face", dependsOnMethods = {"verifyMaleFemalePercentage"}, groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL})
    public void verifyKundliFace() {
        logger.info("******verifyKundliFace Test Started*****");
        boolean kundliFaceFound = findPeoplePage.getKundliFace();
        Assert.assertTrue(kundliFaceFound, "more then one kundli face found");
        logger.info("******verifyKundliFace Test Finished*****");
    }

    @Test(description = "Click on kundli face and check if it navigate to profile page", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL})
    public void veifyKundliFaceProfile() {
        logger.info("******veifyKundliFaceProfile Test Started*****");
        String profileVerfication = findPeoplePage.clickFirstKundliFace();
        Assert.assertTrue(profileVerfication.contains(PROFILES), "profile opened for kundli face");
        logger.info("******veifyKundliFaceProfile Test Finished*****");

    }

    @Test(description = "verify find people magnifying glass icon with clevertap ID", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.FINDPPLCRITICAL})
    public void findPeopleMagnifyingIcon(){
        logger.info("******findPeopleMagnifyingIcon Test Started*****");
        JsonObject jsonObject = getJsonObjectFromJsonFile("ProfileJson/UploadProfile");
        String jsonBody = getJsonBody(jsonObject,"d[0]/profileData/email",getValue("UserName"),"d[0]/objectId","__7f92addc1ce243c59cfa65d5792c9489");
        ResponseFactory responseFactory=new ResponseFactory();
        responseFactory.uploadEventProperties(jsonBody);
        FindPeoplePage findPeoplePage = new FindPeoplePage(driver);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        SeleniumUtils.enterInputText(driver, findPeoplePage.returnPlaceHolder(), getValue("UserName"));
        SeleniumUtils.performClick(driver, findPeoplePage.returnFindMagnifyingGlass());
        SeleniumUtils.waitForElementToLoad(driver,findPeoplePage.getEmailId,10);
        Assert.assertEquals(getValue("UserName"),findPeoplePage.getEmailId.getText(),"Unexepected value was present for email field on kundli page");
        logger.info("******findPeopleMagnifyingIcon Test Finished*****");
    }

//    @Test(description = "Verify Different data types in filters", groups = {TestCaseGroups.FINDPPLCRITICAL}, dataProvider = "filter-name")
//    public void verifyFilterDataTypeValues(String filterName) throws IOException, ParseException, InterruptedException {
//        logger.info("******Verifying data types for " + filterName + "*****");
//        test = ExtentTestManager.startTest(getClass().getSimpleName() + " :->verifyFilterDataTypeValues", "");
//        parentTest.appendChild(test);
//
//        /*This block will verify whether event push or not*/
//        if (eventPushCounter == 0) {
//            Account acc = new Account(driver);
//            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ACCOUNT.toString(), "");
//            RestApi api = new RestApi();
//            String accountID = acc.getAccountId();
//            logger.info("Account ID for the account is: " + accountID);
//            String passcode = acc.getPasscode();
//            logger.info("Passcode for the account is: " + passcode);
//            Boolean eventUpload = api.pushAction(getUpdatedDataFile("eventUploadSchema.json"), BaseTest.getValue("EventsUploadApi"), "POST",accountID,passcode);
//            Assert.assertTrue(eventUpload, "Event upload api failed");
//            logger.info("Event Property upload : "+eventUpload);
//            SeleniumUtils.pause(100);
//            eventPushCounter++;
//        }
//
//        FindPeoplePage findPeoplePage = new FindPeoplePage(driver);
//        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
//        SeleniumUtils.waitForPageLoaded(driver);
//        findPeoplePage.selectEventFromUserWhoDid();
//        Assert.assertTrue(findPeoplePage.compareEventDataTypeValues(filterName), "Event data type values mismatch");
//    }

    @DataProvider(name = "filter-name")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{"Event property"},
                {"First Time"},
                {"Last Time"},
                {"Time of the day"},
                {"Day of the week"},
                {"Day of the month"}};
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        Reporter.log("inside after class of find people",true);
        driver.close();
        driver.quit();

    }

}

