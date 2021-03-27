package com.clevertap.ui.pages.boards;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MobileApp {
    WebDriver driver;
    @FindBy(xpath = "//div[@id='totalActiveUsers']")
    private WebElement totalActiveUser;
    @FindBy(xpath = "//div[@id='dau']")
    private WebElement totalDailyUsers;
    @FindBy(xpath = "//div[@id='wau']")
    private WebElement totalWeeklyUsers;
    @FindBy(xpath = "//div[@id='mau']")
    private WebElement totalMonthlyUsers;
    @FindBy(xpath = "//div[contains(@class,'calendar-header')]")
    private WebElement calendarHeaderDropDown;
    @FindBy(xpath = "//ul[@class='calendar-main-list-ul']")
    private WebElement listElementsCalendarHeader;
    @FindBy(xpath = "//*[contains(@class,'global-calendar-widget')]")
    private WebElement calendarWidget;
    @FindBy(xpath = "//*[contains(@class,'calendar-main-list-li')]")
    private List<WebElement> calendarList;
    @FindBy(xpath = "//*[@class='ctChart__hd']//a")
    private WebElement trendGraphDropdownLeftCorner;
    @FindBy(xpath = "//*[@class='ctChart__hd']//a/../div//li")
    private List<WebElement> trendDays;
    @FindBy(xpath = "//li[@data-id='moreView']")
    private WebElement moreDetailsView;
    @FindBy(xpath = "//*[@id='AllToggle']")
    private WebElement allToggle;
    @FindBy(xpath = "//*[@id='iOSToggle']")
    private WebElement iOSToggle;
    @FindBy(xpath = "//*[@id='AndroidToggle']")
    private WebElement androidToggle;
    @FindBy(xpath = "//*[@id='WindowsToggle']")
    private WebElement windowsToggle;
    @FindBy(xpath="//*[@id='trendgraphDpu']//div[@class='ctChart__dropDown']//a")private WebElement trendGraphDropdown2;
    @FindBy(xpath="//*[@id='trendgraphDau']//div[@class='ctChart__dropDown']//a")private WebElement trendGraphDropdown1;
    @FindBy(xpath = "//*[@id='totalConvertingUsers']")private WebElement totalConvertingUser;
    @FindBy(xpath = "//*[@id='dcu']")private WebElement totalDailyConvertingUser;
    @FindBy(xpath = "//*[@id='wcu']")private WebElement totalWeeklyConvertingUser;
    @FindBy(xpath = "//*[@id='mcu']")private WebElement totalMonthlyConvertingUser;
    @FindBy(xpath = "//input[@name='appVersionRadio']/../label[contains(text(),'App')]")
    private WebElement radioButton;

    public void selectCalendarDateInterval(String calendarItem) {
        calendarWidget.click();
        for (WebElement element : calendarList) {
            if (element.getText().equalsIgnoreCase(calendarItem)) {
                element.click();
            }
        }
    }
    public void selectTrendTypeDropdown1(String trendType) {
        trendGraphDropdown1.click();
        for (WebElement element : trendDays) {
            if (element.getText().equalsIgnoreCase(trendType)) {
                element.click();
            }
        }
    }
    public void selectTrendTypeDropdown2(String trendType)  {
        trendGraphDropdown2.click();
        for (WebElement element : trendDays) {
            if (element.getText().equalsIgnoreCase(trendType)) {
                element.click();
            }
        }


    }

    public void selectRadioButton() {
        SeleniumUtils.performClick(driver,radioButton);

    }

    public boolean verifyTotalActiveUsers(int numberOfDays){
        SeleniumUtils.performClick(driver, allToggle);
        SeleniumUtils.waitForPageLoaded(driver);
        float activeUser = getTotalActiveUsers();
        float dailyUsers = getTotalDailyUsersValue();
        float avgWeeklyUsers = getTotalWeeklyUsers();
        return (activeUser / numberOfDays == dailyUsers) && activeUser == avgWeeklyUsers;
    }

    public boolean verifyTotalConvertingUsers(int numberOfDays){
        float convertingUser=getTotalActiveConvertingUser();
        float dailyConvertingUser=getTotalDailyConvertingUser();
        float weeklyConvertingUser=getTotalWeeklyConvertingUser();
        float monthlyConvertingUser=getTotalMonthlyConvertingUser();
        return (convertingUser/numberOfDays==dailyConvertingUser)&& (convertingUser==monthlyConvertingUser) &&(dailyConvertingUser*numberOfDays==weeklyConvertingUser);
    }
    public WebElement getMobileAppMonthlyActiveUsers() {
        return totalMonthlyUsers;
    }

    public WebElement getMobileAppWeeklyActiveUsers() {
        return totalWeeklyUsers;
    }

    public WebElement getMobileAppMonthlyConvertingUsers(){
        return totalMonthlyConvertingUser;
    }

    public WebElement getMobileAppWeeklyConvertingUsers(){
        return  totalWeeklyConvertingUser;
    }

    public int getTotalDailyUsersValue() {
        return Integer.parseInt(totalDailyUsers.getText());
    }

    public int getTotalActiveUsers() {
            return Integer.parseInt(totalActiveUser.getText());
        }

    public int getTotalWeeklyUsers() {
        return Integer.parseInt(totalWeeklyUsers.getText());
    }

    public int getTotalActiveConvertingUser(){
        return Integer.parseInt(totalConvertingUser.getText());
    }

    public int getTotalDailyConvertingUser(){
       return Integer.parseInt(totalDailyConvertingUser.getText());
    }

    public int getTotalWeeklyConvertingUser(){
        return Integer.parseInt(totalWeeklyConvertingUser.getText());

    }

    private int getTotalMonthlyConvertingUser(){
        return Integer.parseInt(totalMonthlyConvertingUser.getText());
    }

    public MobileApp(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }
}