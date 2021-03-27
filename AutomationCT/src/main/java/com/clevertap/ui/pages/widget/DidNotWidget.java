package com.clevertap.ui.pages.widget;

import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class DidNotWidget extends LoadYamlFile{

    private static final String PX = "px\" })";
    private static final String ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER = "(//*[@id='SEQuery']//div[@data-at-select='eventMain']/div[@class='evtOuter'])[";
    public static final String ADD_DID_NOT_EVENT = "addDidNotEvent";
    public static final String LI_TEXT = "]//li[text()='";
    public static final String WHO_DID_NOT_EVENT = "whoDidNotEvent";
    public static final String SPAN = "]//span";
    private WebDriver localDriver;
    public static int userWhoDidNotFilterCounter=1;
    public static int liveActionUserWhoDidNotFilterCounter=1;

    public void segEnterUserWhoDidNotFilters(List<String> didNotWidgeFilterItems,Map<String,Map<String,String>> csvMapObj){
        int size = didNotWidgeFilterItems.size();
        for (int i = 0; i < size; i++) {
            int j = i + 1;
            if (SeleniumUtils.getVisibility(By.id(ADD_DID_NOT_EVENT), 10, localDriver) != null) {
                if (userWhoDidNotFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id(ADD_DID_NOT_EVENT), 10, localDriver));
                    userWhoDidNotFilterCounter++;
                }
            } else {
            /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + PX);
            }
            try {
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_NOT_EVENT) + "']")).click();
            } catch (Exception e) {
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_NOT_EVENT) + "']")).click();
            }
        }
    }

    public void segEnterLiveActionUserWhoDidNotFilters(List<String> didNotWidgeFilterItems,Map<String,Map<String,String>> csvMapObj){
        int size = didNotWidgeFilterItems.size();
        for (int i = 0; i < size; i++) {
            int j = i + 1;
            if (SeleniumUtils.getVisibility(By.id(ADD_DID_NOT_EVENT), 10, localDriver) != null) {
                if (liveActionUserWhoDidNotFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id(ADD_DID_NOT_EVENT), 10, localDriver));
                    liveActionUserWhoDidNotFilterCounter++;
                }
            } else {
            /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + PX);
            }
            try {
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_NOT_EVENT) + "']")).click();
            } catch (Exception e) {
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_NOT_EVENT) + "']")).click();
            }
        }
    }

    public void enterUserWhoDidNotFilters(List<String> didNotWidgeFilterItems,Map<String,Map<String,String>> csvMapObj){
        int size = didNotWidgeFilterItems.size();
        for (int i = 0; i < size; i++) {

            int j = i + 1;

            if (SeleniumUtils.getVisibility(By.id(ADD_DID_NOT_EVENT), 10, localDriver) != null) {
                if (userWhoDidNotFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id(ADD_DID_NOT_EVENT), 10, localDriver));
                    userWhoDidNotFilterCounter++;
                }

            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + PX);
            }
            try {

                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();

                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_NOT_EVENT) + "']")).click();

            } catch (Exception e) {
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();

                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_NOT_EVENT) + "']")).click();
            }
        }
    }


    public void enterLiveActionUserWhoDidNotFilters(List<String> didNotWidgeFilterItems,Map<String,Map<String,String>> csvMapObj){
        int size = didNotWidgeFilterItems.size();
        for (int i = 0; i < size; i++) {

            int j = i + 1;

            if (SeleniumUtils.getVisibility(By.id(ADD_DID_NOT_EVENT), 10, localDriver) != null) {
                if (liveActionUserWhoDidNotFilterCounter==1){
                    SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id(ADD_DID_NOT_EVENT), 10, localDriver));
                    liveActionUserWhoDidNotFilterCounter++;
                }

            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) localDriver;
                js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + PX);
            }
            try {

                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();

                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_NOT_EVENT) + "']")).click();

            } catch (Exception e) {
                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + "]")).click();

                localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + LI_TEXT + csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_NOT_EVENT) + "']")).click();
            }
        }
    }

    public void verifyLiveActionUserWhoDidNotFilters(Map<String,Map<String,String>> csvMapObj){
        String selectedUserWhoDidNot=null;
        int size = localDriver.findElements(By.xpath("//*[@id='SEQuery']//div[@data-at-select='eventMain']/div[@class='evtOuter']")).size();
        for (int i = 0; i < size; i++) {

            int j = i + 1;

//            if (SeleniumUtils.getVisibility(By.id("addDidNotEvent"), 10, localDriver) != null) {
//                SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id("addDidNotEvent"), 10, localDriver));
//            } else {
//                /*below 2 lines of code is for scroll into view*/
//                JavascriptExecutor js = (JavascriptExecutor) localDriver;
//                js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + PX);
//            }
            try {

                selectedUserWhoDidNot=localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + SPAN)).getText();
                Assert.assertTrue(selectedUserWhoDidNot.contains(csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_NOT_EVENT)));

            } catch (Exception e) {
                selectedUserWhoDidNot=localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + SPAN)).getText();
                Assert.assertTrue(selectedUserWhoDidNot.contains(csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_NOT_EVENT)));
            }
        }
//        return selectedUserWhoDidNot;
    }

    public void verifySegmentQueryUserWhoDidNotFilters(Map<String, Map<String, String>> csvMapObj) {
        String selectedUserWhoDidNotDoEvent;

        try {
            selectedUserWhoDidNotDoEvent = localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + "1]//span")).getText();
            Assert.assertTrue(selectedUserWhoDidNotDoEvent.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_NOT_EVENT)));
        } catch (Exception e) {
            selectedUserWhoDidNotDoEvent = localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + "1]//span")).getText();
            Assert.assertTrue(selectedUserWhoDidNotDoEvent.equalsIgnoreCase(csvMapObj.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_NOT_EVENT)));
        }
    }

    public void verifyUserWhoDidNotFilters(List<String> didNotWidgeFilterItems,Map<String,Map<String,String>> csvMapObj){
        String selectedUserWhoDidNotDo=null;
        int size = didNotWidgeFilterItems.size();
        for (int i = 0; i < size; i++) {

            int j = i + 1;

            try {

                selectedUserWhoDidNotDo=localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + SPAN)).getText();
                Assert.assertTrue(selectedUserWhoDidNotDo.equalsIgnoreCase(csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_NOT_EVENT)));

            } catch (Exception e) {
                selectedUserWhoDidNotDo=localDriver.findElement(By.xpath(ID_SEQUERY_DIV_DATA_AT_SELECT_EVENT_MAIN_DIV_CLASS_EVT_OUTER + j + SPAN)).getText();
                Assert.assertTrue(selectedUserWhoDidNotDo.equalsIgnoreCase(csvMapObj.get(campaignMeta.getCsv_key()).get(WHO_DID_NOT_EVENT)));

            }
        }
    }


    public DidNotWidget(WebDriver previousBrowserDriver) {
        localDriver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }
}

