package com.clevertap.ui;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.analyze_page.EventsPage;
import com.clevertap.ui.pages.analyze_page.FunnelsPage;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompareToProdTest extends BaseTest{

    private static final String ALL_USERS = "All users";
    private static final String THIS_MONTH = "This month";
    private static final String APP_LAUNCHED = "App Launched";
    private static final String STAGING = "Staging";
    private static final String PRODUCTION = "Production";
    private static final String PRODUCT_VIEWED = "Product Viewed";
    private static final String ADDED_TO_CART = "Added To Cart";
    private static final String CHARGED = "Charged";
    private WebDriver driver;
    private Logger logger;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.EVENTS.toString(), "");
        logger = baseTest.configureLogger(getClass());
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.COMPARETOPRODCRITICAL, TestCaseGroups.REGRESSION, TestCaseGroups.EVENTS}, description = "Verify if all the details related to Events page match prod")
    public void compareEventsWithProd(){
        logger.info(">>>compareEventsWithProd");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.EVENTS.toString(), "");
        EventsPage events = new EventsPage(driver);
        SeleniumUtils.pause(3);
        Map<String, Integer> mapOfDetailsOnStaging = events.getDetailsFromEventPage(ALL_USERS, THIS_MONTH, APP_LAUNCHED, STAGING);
//        SeleniumUtils.openElementInNewTab(driver,driver.findElement(By.xpath("//li/a[text()='Events']")));
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
//        Set<String> tabs = driver.getWindowHandles();
//        List<String> handles = new ArrayList<>(tabs);
//        driver.switchTo().window(handles.get(1));
        driver.get(Mocha.prodUrl);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.EVENTS.toString(), "");
        SeleniumUtils.pause(3);
        Map<String, Integer> mapOfDetailsOnProd = events.getDetailsFromEventPage(ALL_USERS, THIS_MONTH, APP_LAUNCHED, PRODUCTION);
        driver.close();
        driver.switchTo().window(tabs.get(0));
        Assert.assertEquals(mapOfDetailsOnProd, mapOfDetailsOnStaging);
        logger.info("<<<compareEventsWithProd");
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.COMPARETOPRODCRITICAL, TestCaseGroups.REGRESSION, TestCaseGroups.FUNNEL}, description = "Verify if all the details related to Events page match prod")
    public void compareFunnelWithProd() throws InterruptedException {
        logger.info(">>>compareFunnelWithProd");
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.FUNNELS.toString(), "");
        FunnelsPage funnel = new FunnelsPage(driver);
        List<String> listOfEvents = new ArrayList<>();
        listOfEvents.add(APP_LAUNCHED);
        listOfEvents.add(PRODUCT_VIEWED);
        listOfEvents.add(ADDED_TO_CART);
        listOfEvents.add(CHARGED);
        funnel.setUpFunnel(ALL_USERS, THIS_MONTH, listOfEvents);
//        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.pause(3);
        List<String> StagingFunnelData = funnel.getFunnelData(STAGING);
//        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.pause(3);
//        SeleniumUtils.openElementInNewTab(driver,driver.findElement(By.xpath("//li/a[text()='Funnels']")));
//        Set<String> tabs = driver.getWindowHandles();
//        List<String> handles = new ArrayList<>(tabs);
//        driver.switchTo().window(handles.get(1));

        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(Mocha.prodUrl);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.FUNNELS.toString(), "");
        funnel.setUpFunnel(ALL_USERS, THIS_MONTH, listOfEvents);
//        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.pause(3);
        List<String> ProdFunnelData = funnel.getFunnelData(PRODUCTION);
        driver.close();
        driver.switchTo().window(tabs.get(0));
        Assert.assertEquals(ProdFunnelData, StagingFunnelData);
        logger.info("<<<compareFunnelWithProd");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        Reporter.log("inside after class of Compare to Prod test ",true);
        driver.close();
        driver.quit();

    }
}