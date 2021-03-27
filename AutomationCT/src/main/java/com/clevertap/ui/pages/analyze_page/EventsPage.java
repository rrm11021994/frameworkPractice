package com.clevertap.ui.pages.analyze_page;

import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsPage {

    private WebDriver driver;
    private final static Logger logger = Logger.getLogger("Mocha");

    @FindBy(className = "ct-breadcrumb_title") private List<WebElement> breadcrum;
    @FindBy(xpath = "//div[contains(@id,'selectSegment_chzn')]/a") private WebElement segmentSelector;
    @FindBy(xpath = "//div[contains(@class,'calendar-header')]") private WebElement durationSelector;
//    @FindBy(xpath = "//div[@id='evtSelection1_chzn']/a") private WebElement eventSelector;
    @FindBy(xpath = "//div[@id='evtSelection1_chzn']//a//div") private WebElement eventSelector;
    @FindBy(xpath = "//div[@id='evtSelection1_chzn']//div[@class='chzn-search']/input") private WebElement eventSelectorSearchBox;
    @FindBy(className = "stats-box-all-info") private WebElement eventsFound;
    @FindBy(xpath = "//a[text()='View details']") private WebElement viewDetailsBtn;
    @FindBy(id = "totalEvntNum") private WebElement totalEvent;
    @FindBy(id = "plCountU") private WebElement numberOfUsers;
    @FindBy(xpath = "//div[@class='ct-panel_item']/div[2]") private List<WebElement> listOfDevTypeNos;
    @FindBy(xpath = "//div[@class='ct-panel_item']/div[3]/span") private List<WebElement> listOfPercentageOfDevTypeNos;
    @FindBy(id = "malePercent") private WebElement malePercent;
    @FindBy(id = "femalePercent") private WebElement femalePercent;
    @FindBy(xpath = "//div[@id='fbGrid']/span[1]/a")private WebElement profileLink;
    @FindBy(xpath = "//label[@for='radioEP_1']") WebElement eventsRadioBtn;
    @FindBy(xpath = "//label[@for='radioEP_2']") WebElement pplRadioBtn;
    @FindBy(xpath = "//div[@data-at-select='chartInterval']//a") WebElement graphDurationSelector;
    @FindBy(xpath = "(//*[@class='filterClass'])[1]")
    private WebElement addfilter;
    @FindBy(xpath = "//span[text()='Campaign type']")
    private WebElement campaignDropdown;
    @FindBy(xpath = "(//li[text()='Campaign id'])[1]")
    private WebElement idDropdown;
    @FindBy(xpath = "//input[contains(@class,'evtPropValues')]")
    private WebElement campaignIDTextbox;
    @FindBy(id = "numEvents")
    private WebElement clickedCount;


    public String getEventCount(String eventName, String campaignID) {
        choseEvent(eventName);
        SeleniumUtils.pause(2);
        SeleniumUtils.performClick(driver, addfilter);
        SeleniumUtils.pause(2);
        campaignDropdown.click();
        idDropdown.click();
        SeleniumUtils.enterInputText(driver, campaignIDTextbox, Keys.chord(campaignID + Keys.RETURN));
        return clickedCount.getText();
    }


    public String getHeader(){
        String header = "";
        for (WebElement ele: breadcrum) {
            header += ele.getText().trim() + "/";
        }
        return header;
    }

    public void choseSegment(String optionToSelect){
//        segmentSelector.click();
//        SeleniumUtils.waitAndClick(driver,segmentSelector);
        SeleniumUtils.pause(1);
        segmentSelector.click();
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[contains(@id,'selectSegment_chzn')]//ul/li[text()='"+optionToSelect+"']")));
//        driver.findElement(By.xpath("//div[contains(@id,'selectSegment_chzn')]//ul/li[text()='"+optionToSelect+"']")).click();
    }

    public void choseDuration(String optionToSelect){
//        durationSelector.click();
        SeleniumUtils.waitAndClick(driver,durationSelector);
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@id='ct_date_widget']//ul/li/span[text()='"+optionToSelect+"']")));
    }

    public void choseEvent(String optionToSelect){
        SeleniumUtils.pause(1);
        eventSelector.click();
        SeleniumUtils.waitForElementToClickable(driver,eventSelectorSearchBox,20);
        SeleniumUtils.pause(2);
        driver.findElement(By.xpath("//div[@id='evtSelection1_chzn']//ul/li[text()='"+optionToSelect+"']")).click();
    }

    public void viewDetailsOf(String segment, String duration, String event){
        choseSegment(segment);
        choseDuration(duration);
        choseEvent(event);
        clickViewDetailsBtn();
    }

    public void clickEventDetailsBtn(String btnToSelect){
        driver.findElement(By.xpath("//div[@class='btn-group']//button[contains(text(),'"+btnToSelect+"')]")).click();
    }

    public int getEventsFound(){
        return Integer.parseInt(eventsFound.getText().replaceAll(",", ""));
    }

    public int getDetailEventsFound(){
        SeleniumUtils.waitForElementToLoad(driver,totalEvent);
        return Integer.parseInt(totalEvent.getText().replaceAll(",", ""));
    }

    public int getNumberOfUsers(){
        SeleniumUtils.waitForElementToLoad(driver,numberOfUsers);
        return Integer.parseInt(numberOfUsers.getAttribute("innerText").replaceAll(",", ""));
    }

    public int getTotalUsersFromSegregation(){
        int totalCount = 0;
        for (WebElement ele:listOfDevTypeNos){
            totalCount += Integer.parseInt(ele.getAttribute("innerText").replaceAll(",", ""));
        }
        return totalCount;
    }

    public void clickViewDetailsBtn(){
        SeleniumUtils.waitAndClick(driver,viewDetailsBtn);
    }

    public int getTotalPercentage(){
        int percentage= 0;
        for (WebElement ele:listOfPercentageOfDevTypeNos) {
            percentage += Integer.parseInt(ele.getText().substring(0,ele.getText().indexOf('%')));
        }
        return percentage;
    }

    public int getMalePercent(){
        if (!malePercent.getText().contains("-")){
            return Integer.parseInt(malePercent.getText().substring(0,malePercent.getText().indexOf('%')));
        }
        return 0;

    }

    public int getFemalePercent(){
        if (!femalePercent.getText().contains("-")) {
            return Integer.parseInt(femalePercent.getText().substring(0, femalePercent.getText().indexOf('%')));
        }

        return 0;
    }

    public void clickProfile(){
        SeleniumUtils.performClick(driver,profileLink);
    }

    public String getProfileHeader(){
        return driver.findElement(By.xpath("//span[@class='ct-breadcrumb_title']")).getText();
    }

    public void clickRadioBtn(String name){
        if(name.equalsIgnoreCase("events")){
            eventsRadioBtn.click();
        }
        else if(name.equalsIgnoreCase("people")){
            pplRadioBtn.click();
        }
    }

    public void selectGraphDuration(String durationToSelect){
        graphDurationSelector.click();
        driver.findElement(By.xpath("//div[@data-at-select='chartInterval']//li[text()='"+durationToSelect+"']")).click();
    }

    public Map<String,Integer> getDetailsFromEventPage(String segment, String duration, String event, String environment){
        viewDetailsOf(segment,duration,event);
        SeleniumUtils.waitForPageLoaded(driver);
        Map<String,Integer> eventsMap = new HashMap<>();
        eventsMap.put("Events",getDetailEventsFound());
        eventsMap.put("Users",getNumberOfUsers());
        eventsMap.put("UsersFromSegregation",getTotalUsersFromSegregation());
        eventsMap.put("TotalPercentage",getTotalPercentage());
        eventsMap.put("MalePercentage",getMalePercent());
        eventsMap.put("FemalePercentage",getFemalePercent());
        for (Map.Entry<String,Integer> entry : eventsMap.entrySet()){
            logger.info( "Environment : "+environment+ "  "+entry.getKey() + " = " + entry.getValue());
        }
        return eventsMap;
    }

    public EventsPage(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

}