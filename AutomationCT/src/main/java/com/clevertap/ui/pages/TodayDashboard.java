package com.clevertap.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TodayDashboard {

    WebDriver driver;

    /*Elements to launch Today dashboard*/
    @FindBy(xpath = "//*[@class='hamburger-box']")
    WebElement collapseMenu;
    @FindBy(xpath = "//*[@id='menu']/ul/li//div[text()='boards']") WebElement menu;
    @FindBy(xpath="//*[@id='menu']/ul/li//div[text()='boards']/../../ul//a[text()='Today']") WebElement subMenuToday;

    /*Elements to locate date and time */
    @FindBy(xpath = "//time[@id='accountTz']") WebElement dateTime;

    @FindBy(xpath = "//*[@id='evStats']//td[@class='toggle-graph-tr ']") WebElement events;
    @FindBy(xpath = "//*[@class='btn detailedView']") WebElement activityStream;

    @FindBy(xpath = "//*[@class='highcharts-exporting-group']") WebElement chartContextMenu;
    @FindBy(xpath = "//*[@id=\"todayDash\"]/li[1]") WebElement quickView;
    @FindBy(xpath = "//*[@class='ct-breadcrumb']") WebElement breadCrumb;
    @CacheLookup
    @FindBy(xpath = "//*[@id='actStream']//li/div/p[@class='fname']/a") WebElement userProfile;
    @FindBy(xpath = "//*[@id='actStream']//li/div/p[@class='fname']/a") List<WebElement> userProfiles;






    public void launchTodayDashboard(){
        collapseMenu.click();
        menu.click();
        subMenuToday.click();
    }



    public String getDateTime(){
        String dateTimeValue= dateTime.getText();
        return dateTimeValue;


    }

    public String verifyDashboardName(){
        return breadCrumb.getText();
    }

//    public void clickActivityStream(){
//        activityStream.click();
////        driver.findElement(By.xpath("//*[@class='btn detailedView']")).click();
////        Thread.sleep(2000);
//        int rowNumber = list.size();
//        for (int i = 0; i < rowNumber; i++) {
////            String text = driver.findElement(By.xpath("//*[@id='evStats']//tr[i]/td[0])")).getText();
//
////            System.out.println("***************** " + text);
//
//        }
//    }

    /*
    * Author-Manmohan
    * below method returns date time as Fri,3:45 PM
    * returns as string
    * */
    public String getFormatteddateTime(){
        SimpleDateFormat dateFormate=new SimpleDateFormat("E");
        String currentDay=dateFormate.format(new Date()); /*it gives abbreviated name of the day as fri,Wed etc*/

        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        String currentLocalTime=dateFormat.format(new Date()); /*get the local date and time*/

        return currentDay+", "+currentLocalTime;
    }

    public boolean checkContextMenuItems(int i){

        this.driver.findElement(By.xpath("//*[@class='highcharts-exporting-group']")).click();
        boolean displayed= this.driver.findElement(By.xpath("(//*[@class='highcharts-menu']/div)["+i+"]")).isDisplayed();
//        this.driver.findElement(By.xpath("//*[@class='highcharts-exporting-group']")).click();
        quickView.click(); /*clicking on this element so that menu item pop up will disappear and again appear on next click*/
        return displayed;


    }

    public WebElement getUsersProfile(){

//        for (WebElement element:userProfiles){
//            String actualTitle=element.getText();
//            element.click();
//            String expectedTitle=breadCrumb.getText();
//            if (expectedTitle.contains(actualTitle)){
//                return true;
//            }
//        }
//        return false;

        return userProfile;

    }

    public int getUsersTotalCount(){

        return userProfiles.size();

    }

    public TodayDashboard(WebDriver driverFromPreviousPage){
        this.driver=driverFromPreviousPage;
        PageFactory.initElements(driverFromPreviousPage,this);
    }
}
