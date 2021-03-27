package com.clevertap.ui.pages.segment_pages;

import com.clevertap.utils.DashboardEnums;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

public class ProfilePage {

    private WebDriver driver;

    private List<WebElement> eventsCount;
    @FindBy(className = "ev_Reverse")
    private List<WebElement> eventsZList;
    private Logger logger = Logger.getLogger(getClass().getSimpleName());

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getEventsCount(String event){
        int eventsCount = 0;
        try{
            eventsCount = Integer.parseInt(driver.findElement(By.xpath("//div[@id='uEvtSummary']//tr/td[2]//b[text()='" + event + "']/../../following-sibling::td/p")).getText());
        }
        catch (NumberFormatException e){
            logger.error("Exception while parsing count of events to Number.");
        }
        return eventsCount;
    }

    public Map<String, String> getLatestEventProps(Object evtName) {
        Map<String, String > map = new HashMap<>();
        List<WebElement> eventProps = driver.findElements(By.xpath("(//table[@class='ev_" + evtName.toString().replaceAll("\\s+","")  +"'])[1]//span[@data-t='tooltip']"));
        for(WebElement e : eventProps){
            String prop = e.getAttribute("title");
            if(!DashboardEnums.SystemEventProperties.contains(prop)){
                map.put(prop, e.getText());
            }
        }
        return map;
    }

}
