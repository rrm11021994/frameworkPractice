package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.RestApiUtil.RestApi;
import com.clevertap.ui.pages.segment_page.FindPeoplePage;
import com.clevertap.ui.pages.segment_pages.ProfilePage;
import com.clevertap.ui.pages.settings_menu_page.EventsAndUserProperties;
import com.clevertap.utils.*;
import com.clevertap.utils.RestApiUtil.ApiUtility;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.javatuples.Triplet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;

public class EventsAndUserPropertiesTest extends BaseTest {

    private Logger logger = Logger.getLogger(getClass().getSimpleName());
    private static WebDriver driver;
    EventsAndUserProperties eventsAndUserProperties;
    private static final String event = Data.randomAlphaNumeric("Automation event ", 3);

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver = getDriver();
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), "");
        logger = configureLogger(getClass());
        eventsAndUserProperties = new EventsAndUserProperties(driver);

    }


    @Test(description = "Selecting different types of events")
    public void verifySelectedEventsListedProper() {

        logger.info(">>>verifySelectedEventsListedProper started");
        String[] eventsArray = {"System", "Active", "Discarded"};
        EventsAndUserProperties eventsAndUserProperties = new EventsAndUserProperties(driver);
        for (int i = 0; i < eventsArray.length; i++) {
            eventsAndUserProperties.selectEventType(eventsArray[i]);
            eventsAndUserProperties.clearAllFilter();
        }

        logger.info("<<<verifySelectedEventsListedProper finished");

    }

    @Test(dependsOnMethods = {"verifySelectedEventsListedProper"}, description = "Searching for a specific events")
    public void searchForSpecificEvent() {
        logger.info(">>>searchForSpecificEvent started");
        String eventToBeSearched = "App Laucnhed";

        EventsAndUserProperties eventsAndUserProperties = new EventsAndUserProperties(driver);
        eventsAndUserProperties.searchSpecificItem("App Launched");

        List<Boolean> status = eventsAndUserProperties.getSearchedFilteredEvents("App Launched");
        Assert.assertTrue(!status.contains(false), "Passed");
        logger.info("<<<searchForSpecificEvent finished");

    }

    @Test(dependsOnMethods = {"searchForSpecificEvent"}, description = "")
    public void test1()  {
        RestApi restApi = new RestApi();
        JSONObject responseObject = restApi.fetchDetails("eventsCount.json", getValue("EventsCountApi"), "POST");
        if (responseObject.get("status").toString().equalsIgnoreCase("partial")) {
            String req_id = responseObject.get("req_id").toString();
            String modifiedURL = getValue("EventsCountApi") + "?req_id=" + req_id;
            JSONObject response = restApi.fetchDetails("eventsCount.json", getValue("EventsCountApi") + "?req_id=" + req_id, "POST");
        }
        restApi.pushAction("eventsUpload.json", getValue("EventsUploadApi"), "POST");
        SeleniumUtils.pause(20);
        restApi.fetchDetails("eventsCount.json", getValue("EventsCountApi"), "POST");
        if (responseObject.get("status").toString().equalsIgnoreCase("partial")) {
            String req_id = responseObject.get("req_id").toString();
            String modifiedURL =getValue("EventsCountApi") + "?req_id=" + req_id;
            JSONObject response = restApi.fetchDetails("eventsCount.json", getValue("EventsCountApi") + "?req_id=" + req_id, "POST");
        }
    }

    @Test(description = "Validate that an event with property of html tags is uploaded successfully",
            groups = {TestCaseGroups.EVENTSANDUSERPROPERTIES})

    public void testHtmlEventProp() {
//        logger.info(">>>verifyHtmlAsEventProperty started");
//        Map<String, String> evtProps = new HashMap<>();
//        Map<Object, Object> evtDetails = new HashMap<>();
//        JSONArray evtArray = new JSONArray();
//        List<String> eventPropList = new ArrayList<>();
//
//        List<String> openingTags = Arrays.asList("<b>", "<a>", "<h>", "<img>", "<h2>", "<div>", "<tr>", "<td>", "<input>");
//        for(String x: openingTags){
//            String evtProp = x + "Event Property" + x.substring(0, 1) + "/" + x.substring(1);
//            evtProps.put(evtProp, evtProp);
//            eventPropList.add(evtProp);
//        }
//
//        evtDetails.put("identity", "Hud-efbl9bdr496cf04d6b");
//        evtDetails.put("evtName", event);
//        evtDetails.put("type", "event");
//        evtDetails.put("evtData", evtProps);
//
//        evtArray.add(evtDetails);
//        JSONObject payload = new JSONObject();
//        payload.put("d", evtArray);
//
//        ProfilePage profile = new ProfilePage(driver);
//        FindPeoplePage findPeople = new FindPeoplePage(driver);
//
//        int initialCount = 0;
//        RestApi restAPI = new RestApi();
//        boolean res = false;
//
//        res = restAPI.pushAction(payload, getValue("EventsUploadApi").toString(), "POST",
//                getValue("X-CleverTap-Account-Id").toString(), getValue("X-CleverTap-Passcode").toString());
//
//        Mocha.forceNavigate = true;
//        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(),
//                NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), "");
//
//        findPeople.placeHolder.sendKeys(evtDetails.get("identity").toString());
//        findPeople.returnFindMagnifyingGlass().click();
//        if (res) {
//            int newCount = 0;
//            int watchCount = 5;
//            while (newCount <= initialCount) {
//                watchCount--;
//                SeleniumUtils.pause(10);
//                driver.navigate().refresh();
//                if (watchCount < 0) {
//                    break;
//                }
//                newCount = profile.getEventsCount(evtDetails.get("evtName").toString());
//            }
//            Map<String, String> eventProps = profile.getLatestEventProps(evtDetails.get("evtName").toString());
//            Assert.assertTrue(eventProps.equals(evtProps), "The event property " + evtProps + " is uploaded successfully and verified as tooltip text on user profile page.");
//
//            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), "");
//
//            SeleniumUtils.pause(2);
//            List<String> evtPropsUI = eventsAndUserProperties.getEventProperties(evtDetails.get("evtName").toString());
//            Collections.sort(evtPropsUI);
//            Collections.sort(eventPropList);
//            Assert.assertTrue(evtPropsUI.equals(eventPropList), "The event property is verified on the Events page.");
//
//            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.EVENTS.toString(), "");
//            List<String> b = eventsAndUserProperties.getCustomProps(evtDetails.get("evtName").toString());
//            Collections.sort(b);
//            Assert.assertTrue(b.equals(eventPropList), "Event property is validated on the Events property dropdown");



//        }

        Map<String,String> eventData=new HashMap<>();
        eventData.put("Auto-evtData","Automation Test");
        Response response=apiLibrary.uploadEventProperties(Triplet.with(getValue("UserName"),event,eventData));
        Assert.assertEquals(ApiUtility.getMemberValueAsString(response,"status"),"success","upload event failed");
        ProfilePage profile = new ProfilePage(driver);
        FindPeoplePage findPeople = new FindPeoplePage(driver);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(),
                NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), "");
        findPeople.placeHolder.sendKeys(getValue("UserName"));
        findPeople.returnFindMagnifyingGlass().click();
        int count=5;
        while(driver.findElements(By.xpath("//table[@class='ev_"+event+"']")).size()<=0 && count>=0){
            driver.navigate().refresh();
            SeleniumUtils.pause(1);
            count--;
        }
        Map<String, String> eventProps = profile.getLatestEventProps(event);
        Assert.assertEquals(eventProps.get("Auto-evtData"),eventData.get("Auto-evtData"),"Event Data was not uploaded succesfully");
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), "");
        SeleniumUtils.pause(2);
        List<String> evtPropsUI = eventsAndUserProperties.getEventProperties(event);
        Assert.assertTrue(eventData.keySet().contains(evtPropsUI.get(0)));
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.EVENTS.toString(), "");
        List<String> b = eventsAndUserProperties.getCustomProps(event);
        Assert.assertTrue(eventData.keySet().contains(evtPropsUI.get(0)),"Event was not present on analyze->event page");

    }


    @AfterClass(alwaysRun = true)
    private void tearDown() {
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), "");
        eventsAndUserProperties.discardEvent(event);

    }

}
