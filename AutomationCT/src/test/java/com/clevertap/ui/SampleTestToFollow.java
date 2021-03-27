package com.clevertap.ui;

import com.clevertap.utils.FileUtility;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class SampleTestToFollow {

    public static final String PASSED = "Passed";
    private ExtentTest test;
    private ExtentTest parentTest;
    private Logger logger;


    @BeforeClass
    public void initialize() {
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(true, "Segment", "Find People", "");
//        parentTest = mocha.configureExtentReport("Test case description");
//        logger = mocha.configureLogger();

    }


    //Put test cases here

    @Test(priority = 1,description = "")
    public void test1()throws InterruptedException{
//        driver.findElement(By.xpath("//div[contains(@class,'calendar-header js-calendar-header')]")).click();
//        driver.findElement(By.xpath("//div[contains(@class,'custom-absolute-date-range custom-date-range js-absolute-range date-group-header')]")).click();
//        driver.findElement(By.id("addLikeDidEvent")).click();
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(0,200)");
//        driver.findElement(By.xpath("//div[@class='calendar-header js-calendar-header']")).click();
//        driver.findElement(By.xpath("//img[@class='js-custom-back custom-header-back']")).click();
//        driver.findElement(By.xpath("//ul[@class='calendar-absolute-list-ul']/li")).click();
//        CalendarUtil cal= new CalendarUtil();
//        cal.selectCalendarDate(driver,"Between","12/Mar/2019-18/May/2019","1");
//        Thread.sleep(5000);
//        driver.findElement(By.id("addLikeDidEvent")).click();
//        JavascriptExecutor js1 = (JavascriptExecutor) driver;
//        js1.executeScript("window.scrollBy(0,150)");
//        driver.findElement(By.xpath("(//div[@class='calendar-header js-calendar-header'])[2]")).click();
//        driver.findElement(By.xpath("(//img[@class='js-custom-back custom-header-back'])[3]")).click();
//        driver.findElement(By.xpath("//*[@id='LEQuery2']/div/div/div/div/div[1]/div/div[4]/div[2]/div[3]/ul/li[1]")).click();
//        cal.selectCalendarDate(driver,"Between","16/Apr/2019-18/May/2019","2");

//        issue :
//        File file1 = new File("/Users/omprakashmishra/Downloads/15-Prod-Summary.csv");
//        File file2 = new File("/Users/omprakashmishra/Downloads/15-Stg2-Summary.csv");
//
        File file1 = new File("/Users/omprakashmishra/Downloads/28 to 3  june weekly report summary.csv");
        File file2 = new File("/Users/omprakashmishra/Downloads/ZWW-WWW-WWRZ-campaign-reports-20190604-weekly.csv");
        new FileUtility().compareCsv(file1,file2);
        //Assert.assertEquals("29/5/201918:1229/5/2019Once per day_21559119368InApp NotificationActionSingle messageSingleAndroidCharged72002201220000000https://eu1.dashboard.clevertap.com/ZWW-WWW-WWRZ/targets/1559119368/show.htmlSCHEDULEDdeepak@clevertap.com-00000",
                //"29/5/201918:1229/5/2019Once per day_21559119368InApp NotificationActionSingle messageSingleAndroidCharged72001102110000000https://eu1.dashboard.clevertap.com/ZWW-WWW-WWRZ/targets/1559119368/show.htmlSCHEDULEDdeepak@clevertap.com-00000");
    }


    @AfterMethod

    private void testResult(ITestResult result) throws Exception {
        //Mocha.setResult(result, test);

    }
}
