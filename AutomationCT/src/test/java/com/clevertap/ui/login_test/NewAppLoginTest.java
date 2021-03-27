package com.clevertap.ui.login_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.Login;
import com.clevertap.ui.pages.login_page.NewAppLogin;
import com.clevertap.utils.Data;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class NewAppLoginTest extends BaseTest {

    private Logger logger;
    public static WebDriver driver;
    private NewAppLogin newAppLogin;

    @BeforeClass(alwaysRun = true)
    public void init() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        logger = baseTest.configureLogger(getClass());
        driver.get(Mocha.getUrl(getValue("environment")));
        newAppLogin = new NewAppLogin(driver);
    }

    @Test(description = "Verify on log in, user should be navigated to the recently created app", groups = {TestCaseGroups.NEWAPPLOGINREGRESSION})
    public void verifyLoginWithNewApp() {
        logger.info("****** verify loginWithNewApp test started ******");
        String appName = Data.randomAlphaNumeric("Automation+", 5);
        String catgOfBusinessValue = "E-Commerce";
        Mocha.openLeftNavMenu(driver, true, null, null, null);
        newAppLogin.addApp(appName, catgOfBusinessValue);
        Login login = new Login(driver);
        login.logOutApplication();
        Mocha.openLeftNavMenu(driver, true, null, null, null);
        Assert.assertEquals(Mocha.currentAccountName, appName);
        login.logOutApplication();
        logger.info("****** verify loginWithNewApp test ended ******");
    }

    @Test(description = "TestImplicit")
    public void testImplicitWait() {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        long startTime = System.currentTimeMillis();
        System.out.println(startTime);
        driver.get("https://mail.google.com");
        WebElement myDynamicElement = driver.findElement(By.xpath("//input[@type='email']"));
        myDynamicElement.click();
        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        System.out.println("Elapsed time in milliseconds: " + timeDiff);
        logger.info("Test passed");
    }

    @Test(description = "TestExplicit")
    public void testExplicitWait() {
        DateFormat startTime = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String startingTime = startTime.format(date);
        System.out.println(startingTime);
        driver.get("https://mail.google.com");
        WebDriverWait wait = new WebDriverWait(driver, 120);
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='Campaign__innerWrapper']/button"))));
            driver.findElement(By.xpath("//div[@class='Campaign__innerWrapper']/button")).click();
        }
        catch (NoSuchElementException e){
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='Campaign__innerWrapper']/button"))));
            driver.findElement(By.xpath("//div[@class='Campaign__innerWrapper']/button")).click();
            DateFormat endTime = new SimpleDateFormat("HH:mm:ss");
            String endingTime = endTime.format(date);
            System.out.println(endingTime);
        }

    }





    @Test(description = "TestExplicit")
    public void testFluentWait() {
        DateFormat startTime = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String startingTime = startTime.format(date);
        System.out.println(startingTime);
        driver.get("https://mail.google.com");
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        driver.findElement(By.xpath("//div[@class='Campaign__innerWrapper']/button")).click();
        DateFormat endTime = new SimpleDateFormat("HH:mm:ss");
        String endingTime = endTime.format(date);
        System.out.println(endingTime);
    }

}



