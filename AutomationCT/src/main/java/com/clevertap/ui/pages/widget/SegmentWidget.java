package com.clevertap.ui.pages.widget;

import com.clevertap.utils.Calendar;
import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class SegmentWidget extends LoadYamlFile {

    public static final String DATA_AT_SELECT_LIKE_TYPE = "(//*[@data-at-select='likeType'])[";
    public static final String DATA_AT_SELECT_LIKE_EVENT = "(//*[@data-at-select='likeEvent'])[";
    public static final String LI_TEXT = "]//li[text()='";
    public static final String PREDOMINANTLY = "predominantly";
    public static final String AT_LEAST = "at least";
    public static final String WHERE_IT_OCURS_FREQUENCY = "whereItOcursFrequency";
    public static final String EVENT_PROPERTY = "Event property";
    public static final String WHO_LIKE_HAVING_PROP = "whoLikeHavingProp";
    public static final String WHO_LIKE_HAVING_PROP_OPERATOR = "whoLikeHavingPropOperator";
    public static final String WHO_LIKE_HAVING_PROP_VALUE = "whoLikeHavingPropValue";
    public static final String TIME_OF_THE_DAY = "Time of the day";
    public static final String DAY_OF_THE_WEEK = "Day of the week";
    public static final String WHO_LIKE_EVENT_PROP = "whoLikeEventProp";
    public static final String WHERE_IT_OCCURS = "whereItOccurs";
    public static final String DATA_AT_SELECT_LIKE_DOMINANT_TYPE = "(//*[@data-at-select='likeDominantType'])[";
    public static final String DATA_AT_SELECT_LIKE_EVENT_PROP = "(//*[@data-at-select='likeEventProp'])[";
    public static final String SPAN = "]//span";
    public static final String SPAN1 = "1]//span";
    public static final String DATA_AT_SELECT_LIKE_EVENT_OPERATOR = "(//*[@data-at-select='likeEventOperator'])[";
    public static final String DATA_AT_SELECT_LIKE_SIGNIFICANT_VAL = "(//*[@data-at-select='likeSignificantVal'])[";
    private WebDriver localDriver;
    private static final String PX = "px\" })";
    public static int userWhoLikeFilterCounter=1;
    @FindBy(className = "search-choice-close")
    private WebElement dayOfWeek;

    public void segEnterAddLikeDidEventsFilters(List<String> eventPropertiesLikeType, List<String> eventPropertiesLikeEvents, Map<String,Map<String,String>> csvMapObj) {
        int size = eventPropertiesLikeType.size();
        for (int i = 0; i < size; i++) {
            int j = i + 1;
            if (SeleniumUtils.getVisibility(By.id("addLikeDidEvent"), 10, localDriver) != null) {
                /*applying user who like filter to use the same method while changing the segment query as well.*/
                if (userWhoLikeFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id("addLikeDidEvent"), 10, localDriver));
                    userWhoLikeFilterCounter++;
                }
            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"likeQuery\").animate({ scrollTop: \"" + 100 + PX);
            }
            try {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + "]")).click();
                Thread.sleep(1000);
            } catch (Exception e) {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + "]")).click();
            }
            try {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + LI_TEXT + eventPropertiesLikeType.get(i) + "']")).click();
                Thread.sleep(1000);
            } catch (Exception e) {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + LI_TEXT + eventPropertiesLikeType.get(i) + "']")).click();
            }
            try {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + "]")).click();
                Thread.sleep(1000);
            } catch (Exception e) {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + "]")).click();
            }
            try {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + LI_TEXT + eventPropertiesLikeEvents.get(i) + "']")).click();
                Thread.sleep(1000);
            } catch (Exception e) {
                localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + LI_TEXT + eventPropertiesLikeEvents.get(i) + "']")).click();
            }
            switch (csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get("whereItOccurs").trim().toLowerCase()) {
                case "predominantly":
                    selectDominantType(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get("whereItOccurs"), j);
                    break;
                case "at least":
                    selectDominantType(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get("whereItOccurs"), j);
                    selectSignificantValue(Integer.parseInt(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get("whereItOcursFrequency")), j);
                    break;
                default:
            }
            switch (eventPropertiesLikeType.get(i)) {
                case "Event property":
                    System.out.println(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase());
                    System.out.println(csvMapObj);
                    selectEventProperties(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get("whoLikeHavingProp"), j);
                    selectEventOperator(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get("whoLikeHavingPropOperator"), j);
                    enterPropValue(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get("whoLikeHavingPropValue"), j);
                    break;
                case "Time of the day":
                    selectLikeFromHour(j,csvMapObj);
                    selectLikeToHour(j,csvMapObj);
//                        selectAfterDateFromCalendar("Feb", "2017", "5");
                    break;
                case "Day of the week":
                    selectSomeOptions(j,csvMapObj);
//                        selectBetweenDateFromCalendar("Feb", "2017", "Jan", "2019", "10", "15");
                    break;
                default:
                    break;
            }
        }
    }

    public void enterAddLikeDidEventsFilters(List<String> eventPropertiesLikeType, List<String> eventPropertiesLikeEvents, Map<String,Map<String,String>> csvMapObj) {
        int size = eventPropertiesLikeType.size();
        for (int i = 0; i < size; i++) {

            int j = i + 1;

            if (SeleniumUtils.getVisibility(By.id("addLikeDidEvent"), 10, localDriver) != null) {
                /*applying user who like filter to use the same method while changing the segment query as well.*/
                if (userWhoLikeFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id("addLikeDidEvent"), 10, localDriver));
                    userWhoLikeFilterCounter++;
                }

            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"likeQuery\").animate({ scrollTop: \"" + 100 + PX);
            }

                try {
                    localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + "]")).click();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + "]")).click();
                }


                try {
                    localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + LI_TEXT + eventPropertiesLikeType.get(i) + "']")).click();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + LI_TEXT + eventPropertiesLikeType.get(i) + "']")).click();
                }

                try {
                    localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + "]")).click();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + "]")).click();
                }

                try {
                    localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + LI_TEXT + eventPropertiesLikeEvents.get(i) + "']")).click();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + LI_TEXT + eventPropertiesLikeEvents.get(i) + "']")).click();
                }

                switch (csvMapObj.get(campaignMeta.getCsv_key()).get("whereItOccurs").trim().toLowerCase()) {
                    case "predominantly":
                        selectDominantType(csvMapObj.get(campaignMeta.getCsv_key()).get("whereItOccurs"), j);
                        break;
                    case "at least":
                        selectDominantType(csvMapObj.get(campaignMeta.getCsv_key()).get("whereItOccurs"), j);
                        selectSignificantValue(Integer.parseInt(csvMapObj.get(campaignMeta.getCsv_key()).get("whereItOcursFrequency")), j);
                        break;
                    default:
                }


                switch (eventPropertiesLikeType.get(i)) {
                    case "Event property":
                        selectEventProperties(csvMapObj.get(campaignMeta.getCsv_key()).get("whoLikeHavingProp"), j);
                        selectEventOperator(csvMapObj.get(campaignMeta.getCsv_key()).get("whoLikeHavingPropOperator"), j);
                        enterPropValue(csvMapObj.get(campaignMeta.getCsv_key()).get("whoLikeHavingPropValue"), j);
                        break;

                    case "Time of the day":
                        selectLikeFromHour(j,csvMapObj);
                        selectLikeToHour(j,csvMapObj);
//                        selectAfterDateFromCalendar("Feb", "2017", "5");
                        break;

                    case "Day of the week":
                        selectSomeOptions(j,csvMapObj);
//                        selectBetweenDateFromCalendar("Feb", "2017", "Jan", "2019", "10", "15");
                        break;

                    default:
                        break;

                }

        }
    }

    public void verifySegmentQueryAddLikeDidEventsFilters(Map<String, Map<String, String>> csvMapObj) {
        try {
            String selectedUserWhoLikeProp = localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + SPAN1)).getText();
            Assert.assertTrue(selectedUserWhoLikeProp.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_LIKE_EVENT_PROP)));
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // do nothing
            }
            String selectedUserWhoLikeEvent = localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + SPAN1)).getText();
            Assert.assertTrue(selectedUserWhoLikeEvent.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get("whoLikeEvent")));
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                // do nothing
            }

            switch (segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHERE_IT_OCCURS).trim().toLowerCase()) {
                case PREDOMINANTLY:
                    verifySegDominantType(csvMapObj);
                    break;
                case AT_LEAST:
                    verifySegDominantType(csvMapObj);
                    selectSignificantValue(5, 1);
                    verifySegSignificantValue(csvMapObj);
                    break;
                default:
            }

            switch (csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_LIKE_EVENT_PROP)) {
                case EVENT_PROPERTY:
                    verifySegEventProperties(csvMapObj);
                    System.out.println("1");
                    verifySegEventOperator(csvMapObj);
                    System.out.println("2");
                    verifySegPropValue(csvMapObj);
                    System.out.println("3");
                    break;
                case TIME_OF_THE_DAY:
                case DAY_OF_THE_WEEK:
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
//            String selectedUserWhoLikeProp = localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + SPAN1)).getText(); intrest section removed may be thats why error need to check
            try {
                Thread.sleep(1000);
            } catch (Exception e1) {
                // do nothing
            }
//            String selectedUserWhoLikeEvent = localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + SPAN1)).getText();
            try {
                Thread.sleep(1000);
            } catch (Exception e2) {
                // do nothing
            }
        }
    }

    private void verifySegDominantType(Map<String, Map<String, String>> csvMapObj) {
        String selectedDomainType = localDriver.findElement(By.xpath("(//div[@data-at-select='likeDominantType'])[1]//span")).getText();
        Assert.assertTrue(selectedDomainType.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHERE_IT_OCCURS)));
    }

    private void verifySegSignificantValue(Map<String, Map<String, String>> csvMapObj) {
        String selectedSignificantValue = localDriver.findElement(By.xpath("(//*[@data-at-select='likeSignificantVal'])[1]//span")).getText();
        Assert.assertTrue(selectedSignificantValue.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHERE_IT_OCURS_FREQUENCY)));
    }

    private void verifySegEventProperties(Map<String, Map<String, String>> csvMapObj) {
        String selectedUserWhoLikeHavingProp = localDriver.findElement(By.xpath("(//div[@data-at-select='likeEventProp'])[1]//span")).getText();
        Assert.assertTrue(selectedUserWhoLikeHavingProp.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_LIKE_HAVING_PROP)));
    }

    private void verifySegEventOperator(Map<String, Map<String, String>> csvMapObj) {
        String selectedUserWhoLikeHavingPropOperator = localDriver.findElement(By.xpath("(//div[@data-at-select='likeEventOperator'])[1]//span")).getText();
        Assert.assertTrue(selectedUserWhoLikeHavingPropOperator.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_LIKE_HAVING_PROP_OPERATOR)));
    }

    private void verifySegPropValue(Map<String, Map<String, String>> csvMapObj) {
        String selectedUserWhoLikeHavingPropValue = localDriver.findElement(By.xpath("(//input[@class='ui-autocomplete-input'])[1]")).getAttribute("value");
        Assert.assertTrue(selectedUserWhoLikeHavingPropValue.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_LIKE_HAVING_PROP_VALUE)));
    }


    public void verifyAddLikeDidEventsFilters(List<String> eventPropertiesLikeType, List<String> eventPropertiesLikeEvents,Map<String,Map<String,String>> csvMapObj) {
        int size = eventPropertiesLikeType.size();
        for (int i = 0; i < size; i++) {

            int j = i + 1;

            try {
                String selectedUserWhoLikeProp = localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + "]//span")).getText();
                Assert.assertTrue(selectedUserWhoLikeProp.equalsIgnoreCase(csvMapObj.get(campaignMeta.getCsv_key()).get("whoLikeEventProp")));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    // do nothing
                }
                String selectedUserWhoLikeEvent = localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + "]//span")).getText();
                Assert.assertTrue(selectedUserWhoLikeEvent.equalsIgnoreCase(csvMapObj.get(campaignMeta.getCsv_key()).get("whoLikeEvent")));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    // do nothing
                }



                switch (segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whereItOccurs").trim().toLowerCase()) {
                    case "predominantly":
                        verifyDominantType(j,csvMapObj);
                        break;
                    case "at least":
                        verifyDominantType(j,csvMapObj);
                        verifySignificantValue( j,csvMapObj);
                        break;
                    default:
                }


                switch (eventPropertiesLikeType.get(i)) {
                    case "Event property":
                        verifyEventProperties(j,csvMapObj);
                        verifyEventOperator(j,csvMapObj);
                        verifyPropValue(j);  //dom issue found
                        break;

                    case "Time of the day":
//                        selectLikeFromHour(j);
//                        selectLikeToHour(j);
//                        selectAfterDateFromCalendar("Feb", "2017", "5");
                        break;

                    case "Day of the week":
//                        selectSomeOptions(j);
//                        selectBetweenDateFromCalendar("Feb", "2017", "Jan", "2019", "10", "15");
                        break;

                    default:
                        break;

                }


            } catch (Exception e) {

                String selectedUserWhoLikeProp = localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_TYPE + j + "]//span")).getText();
                try {
                    Thread.sleep(1000);
                } catch (Exception e1) {
                    // do nothing
                }
                String selectedUserWhoLikeEvent = localDriver.findElement(By.xpath(DATA_AT_SELECT_LIKE_EVENT + j + "]//span")).getText();
                try {
                    Thread.sleep(1000);
                } catch (Exception e2) {
                    // do nothing
                }
            }

        }

    }


    private void selectDominantType(String dominantType, int i) {
        localDriver.findElement(By.xpath("(//*[@data-at-select='likeDominantType'])[" + i + "]")).click();
        String xpath = "(//*[@data-at-select='likeDominantType'])[" + i + LI_TEXT + dominantType + "']";
        WebElement element = localDriver.findElement(By.xpath(xpath));
        if (element != null) {
            element.click();
        }
    }

    public void verifyDominantType( int i,Map<String,Map<String,String>> csvObj) {
        String selectedDomainTYpe=localDriver.findElement(By.xpath("(//*[@data-at-select='likeDominantType'])[" + i + "]//span")).getText();
        Assert.assertTrue(selectedDomainTYpe.equalsIgnoreCase(csvObj.get(campaignMeta.getCsv_key()).get("whereItOccurs")));
    }

    private void verifySignificantValue(int i,Map<String,Map<String,String>> csvObj) {
        String selectedSignificantValue=localDriver.findElement(By.xpath("(//*[@data-at-select='likeSignificantVal'])[" + i + "]//span")).getText();
        Assert.assertTrue(selectedSignificantValue.equalsIgnoreCase(csvObj.get(campaignMeta.getCsv_key()).get("whereItOcursFrequency")));
    }

    private void selectSignificantValue(int significantValues, int i) {
        localDriver.findElement(By.xpath("(//*[@data-at-select='likeSignificantVal'])[" + i + "]")).click();
        String xpath = "(//*[@data-at-select='likeSignificantVal'])[" + i + LI_TEXT + significantValues + "']";
        WebElement element = localDriver.findElement(By.xpath(xpath));
        if (element != null) {
            element.click();
        }
    }

    private void selectEventProperties(String eventProp, int i) {
        localDriver.findElement(By.xpath("(//*[@data-at-select='likeEventProp'])[" + i + "]")).click();
        String xpath = "(//*[@data-at-select='likeEventProp'])[" + i + LI_TEXT + eventProp + "']";
        WebElement element = localDriver.findElement(By.xpath(xpath));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
        }
    }

    private void verifyEventProperties(int i,Map<String,Map<String,String>> csvObj) {
        String selectedEventProp=localDriver.findElement(By.xpath("(//*[@data-at-select='likeEventProp'])[" + i + "]//span")).getText();
        Assert.assertTrue(selectedEventProp.equalsIgnoreCase(csvObj.get(campaignMeta.getCsv_key()).get("whoLikeHavingProp")));
    }

    private void selectEventOperator(String operatorName, int i) {
        localDriver.findElement(By.xpath("(//*[@data-at-select='likeEventOperator'])[" + i + "]")).click();
        String xpath = "(//*[@data-at-select='likeEventOperator'])[" + i + LI_TEXT + operatorName + "']";
        WebElement element = localDriver.findElement(By.xpath(xpath));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
        }
    }

    private void verifyEventOperator(int i,Map<String,Map<String,String>> csvObj) {
        String selectedEventOperator=localDriver.findElement(By.xpath("(//*[@data-at-select='likeEventOperator'])[" + i + "]//span")).getText();
        Assert.assertTrue(selectedEventOperator.equalsIgnoreCase(csvObj.get(campaignMeta.getCsv_key()).get("whoLikeHavingPropOperator")));
    }

    private void enterPropValue(String value, int i) {
        WebElement element = localDriver.findElement(By.xpath("(//*[@class='ui-autocomplete-input'])[" + i + "]"));
        if (element != null) {
//            element.click();
            SeleniumUtils.pause(2);
            element.sendKeys(value);
        }
    }

    private void verifyPropValue(int i) {
        String propValue=localDriver.findElement(By.xpath("(//*[@class='ui-autocomplete-input'])[" + i + "]")).getAttribute("value");
        Assert.assertTrue(propValue.equalsIgnoreCase(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoLikeHavingPropValue")));
    }

    private void selectLikeFromHour(int index,Map<String,Map<String,String>> csvObj) {
        localDriver.findElement(By.xpath("(//*[@data-at-select='likeFromHour'])[" + index + "]")).click();
        String selectFromHr=csvObj.get(campaignMeta.getCsv_key()).get("from");
        WebElement element = localDriver.findElement(By.xpath("(//*[@data-at-select='likeFromHour'])[" + index + "]//li[text()='"+selectFromHr+" ']"));
        if (element != null) {
            element.click();
        }

    }

    private void selectLikeToHour(int index,Map<String,Map<String,String>> csvObj) {
        localDriver.findElement(By.xpath("(//*[@data-at-select='likeToHour'])[" + index + "]")).click();
        String selectToHr=csvObj.get(campaignMeta.getCsv_key()).get("to");
        WebElement element = localDriver.findElement(By.xpath("(//*[@data-at-select='likeToHour'])[" + index + "]//li[text()='"+selectToHr+" ']"));
        if (element != null) {
            element.click();
        }
    }

    private void selectBeforeDateFromCalendar(String month, String year, String date) {
        Calendar calendar = new Calendar(localDriver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("Before", Calendar.globalCalendarInstance);
        calendar.selectBeforeDate(month, year, date);
        Calendar.globalCalendarInstance = Calendar.globalCalendarInstance + 1;
    }

    private void selectBetweenDateFromCalendar(String fromMonth, String fromYear, String toMonth, String toYear, String fromDate, String toDate) {
        Calendar calendar = new Calendar(localDriver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("Between", Calendar.globalCalendarInstance);
        calendar.selectBetweenDate(fromMonth, fromYear, toMonth, toYear, fromDate, toDate);
        Calendar.globalCalendarInstance = Calendar.globalCalendarInstance + 1;
    }

    private void selectAfterDateFromCalendar(String month, String year, String date) {
        Calendar calendar = new Calendar(localDriver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("After", Calendar.globalCalendarInstance);
        calendar.selectAfterDate(month, year, date);
        Calendar.globalCalendarInstance = Calendar.globalCalendarInstance + 1;
    }

    private void selectOnDateFromCalendar(String onMonth, String onYear, String whichDate) {
        Calendar calendar = new Calendar(localDriver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("On", Calendar.globalCalendarInstance);
        calendar.selectOnDate(onMonth, onYear, whichDate);
        Calendar.globalCalendarInstance = Calendar.globalCalendarInstance + 1;
    }

    private void selectSomeOptions(int index,Map<String,Map<String,String>> csvObj) {

//        try {
//            explicitWait.until(ExpectedConditions.visibilityOf(dayOfWeek));
//            SeleniumUtils.performClick(localDriver,dayOfWeek);
//
//        }catch (Exception e){
//            WebElement element=localDriver.findElement(By.className("search-choice-close"));
//            explicitWait.until(ExpectedConditions.visibilityOf(element));
//            SeleniumUtils.performClick(localDriver,element);
//        }
        localDriver.findElement(By.xpath("(//*[@data-at-select='likeEventWeekday'])[" + index + "]")).click();
        WebElement element = localDriver.findElement(By.xpath("(//*[@data-at-select='likeEventWeekday'])[" + index + "]//li[text()='"+csvObj.get(campaignMeta.getCsv_key()).get("dayOfWeek")+"']"));
        if (element != null) {
            element.click();
        }

    }

    public SegmentWidget(WebDriver previousBrowserDriver) {
        localDriver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

}
