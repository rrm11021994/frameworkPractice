package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class DiscardEventsAndUserProperties {

    private WebDriver driver;

    @FindBy(className = "filterbtn") private WebElement eventType;
    @FindBy(xpath = "//*[contains(@class,'controlDropdown')]//label") private List<WebElement> eventsList;
    @FindBy(xpath = "//*[contains(@class,'controlDropdown-Actions')]//a") private WebElement apply;
    @FindBy(xpath = "//*[@class='filterbtn']/../../../../input") private WebElement searchInputField;
    @FindBy(className = "clear-all-filter") private WebElement clearFilter;
    @FindBy(className = "event-id") private List<WebElement> allFilteredEvents;




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

    public void searchSpecificItem(String stringToBeSearched) throws InterruptedException{
        SeleniumUtils.enterInputText(driver,searchInputField,stringToBeSearched);
        SeleniumUtils.pause(1);
        searchInputField.sendKeys(Keys.RETURN);
        SeleniumUtils.pause(2);
    }

    public List<Boolean> getSearchedFilteredEvents(String searchedString){
        List<Boolean> status= new ArrayList<>();
        for (WebElement filteredEvents:allFilteredEvents){
            System.out.println("*********** "+filteredEvents.getText());
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

    public DiscardEventsAndUserProperties(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }
}
