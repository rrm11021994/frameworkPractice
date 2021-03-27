package com.clevertap.ui.pages.boards;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UninstallsPage {


    WebDriver driver;

    @FindBy(id = "newCount")
    private WebElement totalNewActivations;
    @FindBy(id = "evCount")
    private WebElement totalUninstalls;
    @FindBy(id = "newUninstall")
    private WebElement netActivations;
    @FindBy(xpath = "//*[@class='ctChart__hd']//a")
    private WebElement trendGraphDropdownLeftCorner;
    @FindBy(xpath = "//*[@class='ctChart__hd']//a/../div//li")
    private List<WebElement> trendDays;
    @FindBy(id = "totalEngagedLost")
    private WebElement totalLostUsers;
    @FindBy(id = "androidLost")
    private WebElement androidusers;
    @FindBy(id = "iosLost")
    private WebElement iOSUsers;
    @FindBy(xpath = "//*[contains(@class,'global-calendar-widget')]")
    private WebElement calendarWidget;
    @FindBy(xpath = "//*[contains(@class,'calendar-main-list-li')]")
    private List<WebElement> calendarList;
    @FindBy(xpath = "//*[@id='uninstallViewMode']//li[2]")
    private WebElement viewMode;
    @FindBy(xpath = "//*[@class='grid-title']//span")
    private WebElement flowsGridTitle;


    public void switchMode() {

        SeleniumUtils.performClick(driver, viewMode);

    }

    public String getFlowsGridTitle() {
        SeleniumUtils.waitForElementToLoad(driver, flowsGridTitle);
        return flowsGridTitle.getText();
    }


    public void selectCalendarDateInterval(String calendarItem) {
        calendarWidget.click();
        for (WebElement element : calendarList) {
            if (element.getText().equalsIgnoreCase(calendarItem)) {
                element.click();
            }
        }
    }


    public Integer getTotalLostUsers() {
        return Integer.valueOf(totalLostUsers.getText());
    }

    public Integer getAndroidLostUsers() {
        return Integer.valueOf(androidusers.getText());
    }

    public Integer getIOSLostUsers() {
        return Integer.valueOf(iOSUsers.getText());
    }

    /*below method will return number of uninstalls, net activation count and total new activation count*/
    public Map<String, Integer> getEventsCount() {

        Map<String, Integer> eventsMap = new HashMap<>();

        eventsMap.put("TotalNewActivtions", Integer.valueOf(totalNewActivations.getText()));
        eventsMap.put("TotalUninstalls", Integer.valueOf(totalUninstalls.getText()));
        eventsMap.put("NetActivations", Integer.valueOf(netActivations.getText()));


        return eventsMap;
    }

    public void selectTrendTypeDropdown(String trendType) throws InterruptedException {
        Thread.sleep(3000);
        trendGraphDropdownLeftCorner.click();
        for (WebElement element : trendDays) {
            if (element.getText().equalsIgnoreCase(trendType)) {
                element.click();
            }
        }


    }


    public UninstallsPage(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;

        PageFactory.initElements(previousBrowserDriver, this);
    }
}
