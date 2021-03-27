package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.ExcelReading;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners(customListeners.class)
public class BaseClass {
    public static ExtentReports extent;
    public static ExtentTest test;
    public static Properties file;
    public static final Logger logger = LogManager.getLogger(BaseClass.class);
    public static WebDriver driver;
    public static ExcelReading excel;

    @BeforeSuite
    public void readingConfigFile() {
        try{
            file=new Properties();
            FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/org/example/config.properties");
            file.load(fis);
            excel=new ExcelReading("/Users/rahul.mahajan/Downloads/dataset.xlsx");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    public static void initilization(){
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark/Spark.html");
        spark.config().setDocumentTitle("Automation Testing");
        spark.config().setReportName("QA Testing");
        extent.attachReporter(spark);
        extent.setSystemInfo("os",System.getProperty("os"));
        extent.setSystemInfo("Author","Rahul R Mahajan");

        String browser=file.getProperty("Browser");
        if(browser.equals("Chrome")){
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
        driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
        }

        public void switchFrame(String frameId){
        driver.switchTo().frame(frameId);
        }

}