package com.clevertap.ui.pages.widget;

import com.clevertap.utils.Calendar;
import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class DidWidget extends LoadYamlFile {

    private static final String PX = "px\" })";
    public static final String DATA_AT_SELECT_EVENT_WHERE_PROP_CONTAINER = "(//*[@data-at-select='eventWherePropContainer'])[";
    public static final String DATA_AT_SELECT_EVENT_WHERE_AGGR = "(//*[@data-at-select='eventWhereAggr'])[";
    public static final String DATA_AT_SELECT_EVENT_WHERE_OPERATOR = "(//*[@data-at-select='eventWhereOperator'])[";
    public static final String LI_TEXT = "]//li[text()='";
    public static final String WHO_DID_WHERE = "whoDidWhere";
    public static final String COUNT = "count";
    public static final String PROPERTY_SUM_OF = "property sum of";
    public static final String ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN = "((//*[@id='FEQuery']//div[@data-at-select='eventMain'])[";
    public static final String A = "]//a)[";
    public static final String ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN1 = "(//*[@id='FEQuery']//div[@data-at-select='eventMain'])[";
    public static final String WHO_DID_EVENT = "whoDidEvent";
    public static final String WHO_DID_OPERATOR = "whoDidOperator";
    public static final String WHO_DID_VALUE = "whoDidValue";
    public static final String WHO_DID_WHERE_PROP = "whoDidWhereProp";
    public static final String SPAN = "]//span";
    public static final String ADD_DID_EVENT = "addDidEvent";
    public static final String DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER = "(//*[@data-at-select='eventMain']/div[@class='evtOuter'])[";
    public static int userWhoDidFilterCounter=1;
    public static int liveActionUserWhoDidFilterCounter=1;

    String[] eventWhereAggrArray = {"Count", "Property sum of"};

    private WebDriver localDriver;

    public void segEnterUserWhoDidFilters(List<String> eventPropertiesEventsType, Map<String,Map<String,String>> csvMapObj) throws InterruptedException {
        int size = eventPropertiesEventsType.size();
        for (int i = 0; i < size; i++) {
            int j = i + 1;
            /*Below if code is to cancel the already displayed user who did query*/
            try {
                WebElement userWhoDidQueryBox = localDriver.findElement(By.xpath("//ul[@id='FEQuery']/li"));
                WebElement userWhoDidCloseButton = localDriver.findElement(By.xpath("//ul[@id='FEQuery']/li//div[@class='closeBtnB']"));
                if (SeleniumUtils.getVisibility(By.xpath("//ul[@id='FEQuery']/li//div[@class='closeBtnB']"), 10, localDriver) != null) {
                    if (userWhoDidFilterCounter == 1) {
                        try {
                            userWhoDidQueryBox.isDisplayed();
                            userWhoDidCloseButton.click();
                        } catch (NoSuchElementException nse) {
                        }
                    }
                }
            } catch (NoSuchElementException ne) {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"FEQuery\").animate({ scrollTop: \"" + 100 + PX);
            }
            if (SeleniumUtils.getVisibility(By.id(ADD_DID_EVENT), 10, localDriver) != null) {
                if (userWhoDidFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id(ADD_DID_EVENT), 10, localDriver));
                    userWhoDidFilterCounter++;
                }
            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"FEQuery\").animate({ scrollTop: \"" + 100 + PX);
            }
            try {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();
                localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + eventPropertiesEventsType.get(i) + "']")).click();
            } catch (Exception e) {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();
                localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + eventPropertiesEventsType.get(i) + "']")).click();
            }
            switch (csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_WHERE).trim().toLowerCase()) {
                case COUNT:
                    selectEventWhereAggr(j ,csvMapObj);
                    selectEvenWhereOperator(j,csvMapObj);
                    enterEventWhereData(j,csvMapObj);
//                        selectAfterDateFromCalendar("Mar","2018","21");
                    break;
                case PROPERTY_SUM_OF:
                    selectEventWhereAggr(j, csvMapObj);
                    selectEventWhereProp(j,csvMapObj);
                    selectEvenWhereOperator(j,csvMapObj);
                    enterEventWhereData(j,csvMapObj);
//                        selectOnDateFromCalendar("Mar","2019","20");
                    break;
            }
        }
    }

    public void enterUserWhoDidFilters(List<String> eventPropertiesEventsType, Map<String,Map<String,String>> csvMapObj) throws InterruptedException {

        int size = eventPropertiesEventsType.size();

        for (int i = 0; i < size; i++) {

            int j = i + 1;
            /*Below if code is to cancel the already displayed user who did query*/
            try {
                WebElement userWhoDidQueryBox = localDriver.findElement(By.xpath("//ul[@id='FEQuery']/li"));
                WebElement userWhoDidCloseButton = localDriver.findElement(By.xpath("//ul[@id='FEQuery']/li//div[@class='closeBtnB']"));
                if (SeleniumUtils.getVisibility(By.xpath("//ul[@id='FEQuery']/li//div[@class='closeBtnB']"), 10, localDriver) != null) {
                    if (userWhoDidFilterCounter == 1) {
                        try {
                            userWhoDidQueryBox.isDisplayed();
                            userWhoDidCloseButton.click();
                        } catch (NoSuchElementException nse) {
                        }
                    }
                }
            } catch (NoSuchElementException ne) {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"FEQuery\").animate({ scrollTop: \"" + 100 + PX);
            }

            if (SeleniumUtils.getVisibility(By.id(ADD_DID_EVENT), 10, localDriver) != null) {
                if (userWhoDidFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id(ADD_DID_EVENT), 10, localDriver));
                    userWhoDidFilterCounter++;
                }

            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"FEQuery\").animate({ scrollTop: \"" + 100 + PX);
            }


            try {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();

                localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + eventPropertiesEventsType.get(i) + "']")).click();
            } catch (Exception e) {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();

                localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + eventPropertiesEventsType.get(i) + "']")).click();
            }

            switch (csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_WHERE).trim().toLowerCase()) {

                case COUNT:
                    selectEventWhereAggr(j ,csvMapObj);
                    selectEvenWhereOperator(j,csvMapObj);
                    enterEventWhereData(j,csvMapObj);
//                        selectAfterDateFromCalendar("Mar","2018","21");
                    break;

                case PROPERTY_SUM_OF:
                    selectEventWhereAggr(j, csvMapObj);
                    selectEventWhereProp(j,csvMapObj);
                    selectEvenWhereOperator(j,csvMapObj);
                    enterEventWhereData(j,csvMapObj);
//                        selectOnDateFromCalendar("Mar","2019","20");
                    break;

            }


        }
    }

    public void enterLiveActionUserWhoDidFilters(List<String> eventPropertiesEventsType, List<String> eventOccurence) throws InterruptedException {

        int size = eventPropertiesEventsType.size();

        for (int i = 1; i <= size; i++) {

            int j = eventOccurence.size();
            int k = eventOccurence.size() - 1;
            int p = j - k;

            if (SeleniumUtils.getVisibility(By.id(ADD_DID_EVENT), 10, localDriver) != null) {
                if (liveActionUserWhoDidFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id(ADD_DID_EVENT), 10, localDriver));
                    liveActionUserWhoDidFilterCounter++;
                }

            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"FEQuery\").animate({ scrollTop: \"" + 100 + PX);
            }


            try {
                localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN + i + A + p + "]")).click();

                localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN + i + A + j + "]/following-sibling::div//li[text()='" + eventPropertiesEventsType.get(i - 1) + "']")).click();
            } catch (Exception e) {

                localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN + i + A + p + "]")).click();
                localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN + i + A + j + "]/following-sibling::div//li[text()='" + eventPropertiesEventsType.get(i - 1) + "']")).click();
            }

            try {
                k--;
                localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN1 + i + "]/div[2]//a")).click();

                localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN1 + i + "]/div[2]//a/following-sibling::div//li[text()='" + eventOccurence.get(p - 1) + "']")).click();
            } catch (Exception e) {
                k--;
                localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN1 + i + "]/div[2]//a")).click();

                localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN1 + i + "]/div[2]//a/following-sibling::div//li[text()='" + eventOccurence.get(p - 1) + "']")).click();
            }


        }
    }

    public void verifySegmentQueryWhoDidFilters(Map<String, Map<String, String>> csvMapObj) {

        switch (campaignMeta.getType().trim().toLowerCase()){
            case "one time":
            case "multiple dates":
            case "recurring":
                try {
                    String selectedUserWhoDidEvent = localDriver.findElement(By.xpath("(//*[@data-at-select='eventMain']/div[@class='evtOuter'])[1]//span")).getText();
                    Assert.assertTrue(selectedUserWhoDidEvent.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_EVENT)));
                } catch (Exception e) {
                    String selectedUserWhoDidEvent = localDriver.findElement(By.xpath("(//*[@data-at-select='eventMain']/div[@class='evtOuter'])[1]//span")).getText();
                    Assert.assertTrue(selectedUserWhoDidEvent.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_EVENT)));
                }
                break;
            case "single action":
            case "inaction within time":
            case "on a date/time":
                try {
                    String selectedUserWhoDidEvent = localDriver.findElement(By.xpath("(//*[@id='FEQuery']//div[@data-at-select='eventMain']/div)[1]//span")).getText();
                    Assert.assertTrue(selectedUserWhoDidEvent.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_EVENT)));
                } catch (Exception e) {
                    String selectedUserWhoDidEvent = localDriver.findElement(By.xpath("(//*[@id='FEQuery']//div[@data-at-select='eventMain']/div)[1]//span")).getText();
                    Assert.assertTrue(selectedUserWhoDidEvent.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_EVENT)));
                }
                break;
                default:
        }


        if(!SeleniumUtils.isNullOrEmpty(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_WHERE))){

            switch (csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_WHERE).trim().toLowerCase()) {

                case COUNT:
                    verifySegEventWhereAggr(csvMapObj);
                    verifySegEvenWhereOperator(csvMapObj);
                    verifySegEventWhereData(csvMapObj);
                    break;
                case PROPERTY_SUM_OF:
                    verifySegEventWhereAggr(csvMapObj);
                    verifySegEventWhereProp(csvMapObj);
                    verifySegEvenWhereOperator(csvMapObj);
                    verifySegEventWhereData(csvMapObj);
                    break;
            }

        }


    }

    private void verifySegEventWhereAggr(Map<String, Map<String, String>> csvMapObj) {

        String selectedEventAggregator = localDriver.findElement(By.xpath("(//*[@data-at-select='eventWhereAggr'])[1]//span")).getText();
        Assert.assertTrue(selectedEventAggregator.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_WHERE)));
    }

    private void verifySegEvenWhereOperator(Map<String, Map<String, String>> csvMapObj) {

        String selectedEventAggregatorOperator = localDriver.findElement(By.xpath("(//*[@data-at-select='eventWhereOperator'])[1]//span")).getText();
        Assert.assertTrue(selectedEventAggregatorOperator.contains(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_OPERATOR)));
    }

    private void verifySegEventWhereData(Map<String, Map<String, String>> csvMapObj) {

        String selectedEventValue = localDriver.findElement(By.xpath("(//input[@placeholder='Excludes'])[1]")).getAttribute("value");
        Assert.assertTrue(selectedEventValue.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_VALUE)));
    }

    private void verifySegEventWhereProp(Map<String, Map<String, String>> csvMapObj) {
        String selectedEventWhereProp = localDriver.findElement(By.xpath("(//*[@data-at-select='eventWherePropContainer'])[1]//span")).getText();
        Assert.assertTrue(selectedEventWhereProp.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_WHERE_PROP)));
    }


    public void verifyUserWhoDidFilters(List<String> eventPropertiesEventsType,Map<String,Map<String,String>> csvMapObj) throws InterruptedException {
        String selectedUserWhoDid = null;
        int size = eventPropertiesEventsType.size();

        for (int i = 0; i < size; i++) {

            int j = i + 1;


            try {
                selectedUserWhoDid = localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + SPAN)).getText();
                Assert.assertTrue(selectedUserWhoDid.equalsIgnoreCase(csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_EVENT)));
            } catch (Exception e) {
                selectedUserWhoDid = localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + SPAN)).getText();
                Assert.assertTrue(selectedUserWhoDid.equalsIgnoreCase(csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_EVENT)));
            }

            switch (csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_WHERE).trim().toLowerCase()) {

                case COUNT:
                    verifyEventWhereAggr(j,csvMapObj);
                    verifyEvenWhereOperator(j,csvMapObj);
                    verifyEventWhereData(j,csvMapObj);
//                        selectAfterDateFromCalendar("Mar","2018","21");
                    break;

                case PROPERTY_SUM_OF:
                    verifyEventWhereAggr(j,csvMapObj);
                    verifyEventWhereProp(j,csvMapObj);
                    verifyEvenWhereOperator(j,csvMapObj);
                    verifyEventWhereData(j,csvMapObj);
//                        selectOnDateFromCalendar("Mar","2019","20");
                    break;

            }
        }
    }


    public void verifyLiveActionUserWhoDidFilters(Map<String,Map<String,String>> csvMapObj) throws InterruptedException {

        int size = localDriver.findElements(By.xpath("(//*[@id='FEQuery']//div[@data-at-select='eventMain'])")).size();
//        List<String> selctedUserWhoDidSection = new ArrayList<>();

        for (int i = 1; i <= size; i++) {


//            if (SeleniumUtils.getVisibility(By.id("addDidEvent"), 10, localDriver) != null) {
//                SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id("addDidEvent"), 10, localDriver));
//            } else {
//                /*below 2 lines of code is for scroll into view*/
//                JavascriptExecutor js = (JavascriptExecutor) localDriver;
//                js.executeScript("$(\"FEQuery\").animate({ scrollTop: \"" + 100 + PX);
//            }


            try {
                String selectedEvent = localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN + i + "]//a)[1]/span")).getText();
                Assert.assertTrue(selectedEvent.contains(csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_EVENT)));
            } catch (Exception e) {
                String selectedEvent = localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN + i + "]//a)[1]/span")).getText();
                Assert.assertTrue(selectedEvent.contains(csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_EVENT)));
            }

            try {
                String selectedOccurence = localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN1 + i + "]/div[2]//a/span")).getText();
                Assert.assertTrue(selectedOccurence.contains(csvMapObj.get(campaignMeta.getCsv_key()).get("whoDidOccurence")));

            } catch (Exception e) {
                String selectedOccurence = localDriver.findElement(By.xpath(ID_FEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN1 + i + "]/div[2]//a/span")).getText();
                Assert.assertTrue(selectedOccurence.contains(csvMapObj.get(campaignMeta.getCsv_key()).get("whoDidOccurence")));
            }

//            selctedUserWhoDidSection.add(selectedEvent);
//            selctedUserWhoDidSection.add(selectedOccurence);
        }
//        return selctedUserWhoDidSection;
    }


    public void selectEventWhereAggr(int index, Map<String,Map<String,String>> csvObj) throws InterruptedException {
        //*[@data-at-select='eventWhereAggr'])
        try {
            localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_AGGR + index + "]")).click();
            Thread.sleep(1000);
            WebElement element = localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_AGGR + index + LI_TEXT + csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_WHERE) + "']"));
            if (element != null) {
                element.click();
                SeleniumUtils.pause(1);
            }
        } catch (Exception e) {
            localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_AGGR + index + "]")).click();
            Thread.sleep(1000);
            WebElement element = localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_AGGR + index + LI_TEXT + csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_WHERE) + "']"));
            if (element != null) {
                element.click();
                SeleniumUtils.pause(1);
            }
        }
    }

    public void verifyEventWhereAggr(int index,Map<String,Map<String,String>> csvObj) throws InterruptedException {
            String selectedEventAggregator=localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_AGGR + index + SPAN)).getText();
            Assert.assertTrue(selectedEventAggregator.equalsIgnoreCase(csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_WHERE)));
            Thread.sleep(1000);
    }

    public void selectEvenWhereOperator(int index,Map<String,Map<String,String>> csvObj) throws InterruptedException {
        localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_OPERATOR + index + "]")).click();
        WebElement element = localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_OPERATOR + index + "]//li[contains(text(),'" + csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_OPERATOR) + "')]"));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
        }
    }

    public void verifyEvenWhereOperator(int index,Map<String,Map<String,String>> csvObj) throws InterruptedException {
        String selectedEventAggregatorOperator=localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_OPERATOR + index + SPAN)).getText();
        Assert.assertTrue(selectedEventAggregatorOperator.contains(csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_OPERATOR)));
    }


    public void enterEventWhereData(int index,Map<String,Map<String,String>> csvObj) throws InterruptedException {
        WebElement element = localDriver.findElement(By.xpath("(//input[@placeholder='Excludes'])[" + index + "]"));
        element.click();
        element.clear();
        element.sendKeys(csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_VALUE));
        SeleniumUtils.pause(1);


    }

    public void verifyEventWhereData(int index,Map<String,Map<String,String>> csvObj) throws InterruptedException {
        String selectedEventValue=localDriver.findElement(By.xpath("(//input[@placeholder='Excludes'])[" + index + "]")).getAttribute("value");
        Assert.assertTrue(selectedEventValue.equalsIgnoreCase(csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_VALUE)));


    }

    public void selectEventWhereProp(int index,Map<String,Map<String,String>> csvObj) throws InterruptedException {
        localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_PROP_CONTAINER + index + "]")).click();
        Thread.sleep(1000);
        WebElement element = localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_PROP_CONTAINER + index + "]//li[contains(text(),'" + csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_WHERE_PROP) + "')]"));
        if (element != null) {
            element.click();
            Thread.sleep(1000);
        }

    }

    public void verifyEventWhereProp(int index,Map<String,Map<String,String>> csvObj) throws InterruptedException {
        String selectedEventWhereProp=localDriver.findElement(By.xpath(DATA_AT_SELECT_EVENT_WHERE_PROP_CONTAINER + index + SPAN)).getText();
        Assert.assertTrue(selectedEventWhereProp.equalsIgnoreCase(csvObj.get(campaignMeta.getCsv_key()).get(WHO_DID_WHERE_PROP)));

    }

    private void selectBetweenDateFromCalendar(String fromMonth, String fromYear, String toMonth, String toYear, String fromDate, String toDate) throws InterruptedException {
        Calendar calendar = new Calendar(localDriver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("Between", Calendar.globalCalendarInstance);
        calendar.selectBetweenDate(fromMonth, fromYear, toMonth, toYear, fromDate, toDate);
        Calendar.globalCalendarInstance = Calendar.globalCalendarInstance + 1;
    }

    private void selectBeforeDateFromCalendar(String month, String year, String date) throws InterruptedException {
        Calendar calendar = new Calendar(localDriver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("Before", Calendar.globalCalendarInstance);
        calendar.selectBeforeDate(month, year, date);
        Calendar.globalCalendarInstance = Calendar.globalCalendarInstance + 1;
    }

    private void selectAfterDateFromCalendar(String month, String year, String date) throws InterruptedException {
        Calendar calendar = new Calendar(localDriver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("After", Calendar.globalCalendarInstance);
        calendar.selectAfterDate(month, year, date);
        Calendar.globalCalendarInstance = Calendar.globalCalendarInstance + 1;
    }

    private void selectOnDateFromCalendar(String onMonth, String onYear, String whichDate) throws InterruptedException {
        Calendar calendar = new Calendar(localDriver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("On", Calendar.globalCalendarInstance);
        calendar.selectOnDate(onMonth, onYear, whichDate);
        Calendar.globalCalendarInstance = Calendar.globalCalendarInstance + 1;
    }


    public DidWidget(WebDriver previousBrowserDriver) {
        localDriver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }


}
