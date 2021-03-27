package com.clevertap.ui.pages.settings_menu_page;


import com.clevertap.ui.pages.analyze_page.EventsPage;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.DashboardEnums;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;

public class EventsAndUserProperties {

    private WebDriver driver;
    private Logger logger = Logger.getLogger(getClass().getSimpleName());
    @FindBy(className = "filterbtn") private WebElement eventType;
    @FindBy(xpath = "//*[contains(@class,'controlDropdown')]//label") private List<WebElement> eventsList;
    @FindBy(xpath = "//*[contains(@class,'controlDropdown-Actions')]//a") private WebElement apply;
    @FindBy(xpath = "//*[@class='filterbtn']/../../../../input") private WebElement searchInputField;
    @FindBy(className = "clear-all-filter") private WebElement clearFilter;
    @FindBy(className = "event-id") private List<WebElement> allFilteredEvents;
    @FindBy(xpath = "//button[text()='Discard']")
    private WebElement discardEventConfirm;
    @FindBy(className = "discard_text")
    private WebElement discardEvent;



    public void selectEventType(String eventToBeSelected){
        SeleniumUtils.performClick(driver,eventType);
        try {
            for (WebElement eventTypeElement:eventsList){
                if (eventTypeElement.getText().contains(eventToBeSelected)){
                    SeleniumUtils.performClick(driver,eventTypeElement);
                    SeleniumUtils.performClick(driver,apply);
                    break;
                }
            }
        }catch (Exception e){
            //
        }
    }

    public void clearAllFilter(){
        SeleniumUtils.performClick(driver,eventType);
        SeleniumUtils.performClick(driver,clearFilter);
    }

    public void searchSpecificItem(String stringToBeSearched) {
        SeleniumUtils.enterInputText(driver,searchInputField,stringToBeSearched);
        SeleniumUtils.pause(1);
        searchInputField.sendKeys(Keys.RETURN);
        SeleniumUtils.pause(2);
    }

    public List<Boolean> getSearchedFilteredEvents(String searchedString){
        List<Boolean> status= new ArrayList<>();
        for (WebElement filteredEvents:allFilteredEvents){
            if (filteredEvents.getText().contains(searchedString)){
                status.add(true);
            }else {
                status.add(false);
            }
        }
        return status;
    }

    public void switchTab(String tabName){
        WebElement tabElement=driver.findElement(By.xpath("//*[contains(@class,'ct-tab-line') and text()='"+tabName+"']"));
        SeleniumUtils.performClick(driver,tabElement);
    }

    public EventsAndUserProperties(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

    public boolean isEvent(String eventName){
        List<WebElement> events = driver.findElements(By.xpath("//*[(text()= '" + eventName + "')]"));
        if(events.size() > 0){
            return true;
        }
        return false;
    }

    public List<String> getEventProperties(String event){
        List<String> eventProps = new ArrayList<>();
        if(isEvent(event)){
            driver.findElement(By.xpath("//*[(text()= '" + event + "')]")).click();
            List<WebElement> eventPropsList = driver.findElements(By.xpath("//*[(text()= '" + event + "')]/parent::td/parent::tr/following-sibling::tr[1]//tbody/tr/td[2]"));
            for(WebElement e : eventPropsList){
                String val = e.getText();
                if(!DashboardEnums.SystemEventProperties.contains(val)){
                    eventProps.add(val);
                }
            }
        }
        return eventProps;
    }

    public void discardEvent(String eventName){
        searchSpecificItem(eventName);
        driver.findElement(By.xpath("//*[text()='" + eventName + "']")).click();
        SeleniumUtils.performClick(driver, discardEvent);
        SeleniumUtils.performClick(driver, discardEventConfirm);
        SweetAlert swal = new SweetAlert(driver);
        swal.sweetAlertConfirmOK();
    }

    public boolean verifyEventsPageFilter(String event, String eventProp, int indexOfFilter) {
        boolean b = false;
        EventsPage evtPage = new EventsPage(driver);
        evtPage.choseEvent(event);
        try{
            driver.findElement(By.xpath("(//a[@class='filterClass'])[" + indexOfFilter + "]")).click();
            WebElement e1 = driver.findElement(By.xpath("//li[text()='" + eventProp + "']"));
            if(e1 != null){
                b = true;
            }
        }catch(NotFoundException | ElementNotInteractableException  e){
            logger.error("Exception while click or getText.");
        }
        return b;
    }

    public List<String> getSystemProps(String eventName){
        List<String> systemProps = new ArrayList<>();
        EventsPage evtPage = new EventsPage(driver);
        evtPage.choseEvent(eventName);
        driver.findElement(By.xpath("(//a[@class='filterClass'])[1]")).click();
        List<WebElement> optValues = driver.findElements(By.xpath("//select[contains(@class,'evtProperty')]/optgroup[@label='System properties']/option"));
        for(WebElement e: optValues){
            systemProps.add(e.getAttribute("innerHTML"));
        }
        return systemProps;
    }

    public List<String> getCustomProps(String eventName){
        List<String> customProps = new ArrayList<>();
        EventsPage evtPage = new EventsPage(driver);
        evtPage.choseEvent(eventName);
        driver.findElement(By.xpath("(//a[@class='filterClass'])[1]")).click();
        List<WebElement> optValues = driver.findElements(By.xpath("//select[contains(@class,'evtProperty')]/optgroup[@label='Custom properties']/option"));
        for(WebElement e: optValues){
            customProps.add(StringEscapeUtils.unescapeHtml4(e.getAttribute("innerHTML")));
        }
        return customProps;
    }
}
