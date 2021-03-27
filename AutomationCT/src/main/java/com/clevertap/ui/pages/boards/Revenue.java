package com.clevertap.ui.pages.boards;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Revenue {

    WebDriver driver;
    @FindBy(xpath = "//div[@id='totalRevenueT']") private WebElement    totalRevenue;
    @FindBy(xpath = "//b[@id='avgDailyRevenue']") private WebElement avgDailyRevenue;
    @FindBy(xpath = "//div[@id='totalPayUsers']") private WebElement totalPayingUsers;
    @FindBy(xpath = "//b[@id='adpu']") private WebElement avgDailyPayingUsers;
        @FindBy(xpath = "//div[@id='revPerPayUser']") private WebElement revenuePerPayingUser;
    @FindBy(xpath = "//div[contains(@class,'calendar-header')]") private WebElement calendarHeaderDropDown;
    @FindBy(xpath = "//ul[@class='calendar-main-list-ul']") private WebElement listElementsCalendarHeader;

    @FindBy(xpath = "//*[contains(@class,'global-calendar-widget')]")  private WebElement calendarWidget;
    @FindBy(xpath = "//*[contains(@class,'calendar-main-list-li')]") private List<WebElement> calendarList;
    @FindBy(xpath = "//*[@class='ctChart__hd']//a")  private WebElement trendGraphDropdownLeftCorner;
    @FindBy(xpath = "//*[@class='ctChart__hd']//a/../div//li")  private List<WebElement> trendDays;

    @FindBy(xpath = "//div[@id='wzrk_gtw']//table[contains(@class,'table-bordered')]") private WebElement moreDetailsTable;
    public String moreDetailsTableXpath = "//div[@id='wzrk_gtw']//table[contains(@class,'table-bordered')]";
    @FindBy(xpath = "//li[@data-id='moreView']") private WebElement moreDetailsView;


    public Revenue(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

    public void selectCalendarDateInterval(String calendarItem) {
        calendarWidget.click();
        for (WebElement element : calendarList) {
            if (element.getText().equalsIgnoreCase(calendarItem)) {
                element.click();
            }
        }
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

    public boolean verifyTotalRevenueBoard(int numberOfDays){

        float totalRevenue = getTotalRevenuevalue();
        int totalPayingUsers = getTotalPayingUsersValue();
        float revenuePerPayingUser = getRevenuePerPayingUserValue();
        float avgDailyRevenueUI = getAvgDailyRevenueValue();

        float avgDailyRevenueCalc = totalRevenue/numberOfDays;
        int avgDailyPayingUsersUI = Integer.parseInt(getAvgDailyPayingUsers().getText().replace(",", ""));
        double x = Math.floor(totalPayingUsers/numberOfDays) ;

        //handling for case when totalPayingUsers and/or totalRevenue is 0, to avoid NaN or divide by zero error
        float totalRevenueByTotalPayingUsers;
        if(totalPayingUsers==0||totalRevenue==0){
            totalRevenueByTotalPayingUsers=0;
        }else{
            totalRevenueByTotalPayingUsers = totalRevenue / totalPayingUsers;
        }
        int avgDailyPayingUsersCalc = (int) x;

        return (totalRevenueByTotalPayingUsers == revenuePerPayingUser) && avgDailyRevenueCalc == avgDailyRevenueUI && avgDailyPayingUsersCalc == avgDailyPayingUsersUI;

    }

    public  List<Map<String, Object>> getSingleTableData(String tableXPath){
        List<Map<String, Object>> tableData = new ArrayList<>();

        List<Object> tableColumnsTmp = new ArrayList<>();
        List<Object> tableColumns = new ArrayList<>();
        List<WebElement> headerColumns = driver.findElements(By.xpath(tableXPath + "/thead/tr[1]/th"));

        for(WebElement element: headerColumns){
            tableColumnsTmp.add(element.getText());
        }
        for(int i = 0; i < tableColumnsTmp.size(); i++){
            if(i > 0 && (tableColumnsTmp.get(i).equals("") || tableColumnsTmp.get(i).equals(null))){
                tableColumns.add(tableColumnsTmp.get(i - 1) + "Percent");
            }
            else{
                tableColumns.add(tableColumnsTmp.get(i));
            }
        }

        List<WebElement> tableRows = driver.findElements(By.xpath(tableXPath + "/tbody/tr"));
        for(int i = 1; i < 8; i = i + 2){
            List<WebElement> columns = tableRows.get(i).findElements(By.xpath("td"));
            Map<String, Object> map = new HashMap<>();
            for(int j = 0; j < columns.size(); j++){
                Object columnVal = columns.get(j).getText();
                map.put(tableColumns.get(j).toString(), columnVal);
            }
            tableData.add(map);
        }

        return tableData;
    }

    public float getTotalRevenuevalue() {
        return Float.parseFloat(totalRevenue.getText().replace(",",""));
    }

    public WebElement getTotalRevenue() {
        return totalRevenue;
    }

    public float getAvgDailyRevenueValue() {

        return Float.parseFloat(avgDailyRevenue.getText().replace(",",""));
    }

    public int getTotalPayingUsersValue() {
        return Integer.parseInt(totalPayingUsers.getText().replace(",", ""));
    }

    public WebElement getAvgDailyPayingUsers() {
        return avgDailyPayingUsers;
    }

    public float getRevenuePerPayingUserValue() {
        return Float.parseFloat(revenuePerPayingUser.getText().replace(",", ""));
    }

    public WebElement getRevenuePerPayingUser() {
        return revenuePerPayingUser ;
    }

    public WebElement getMoreDetailsView() {
        return moreDetailsView;
    }
}
