package com.clevertap.ui.journey_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.journey_page.JourneyHomePage;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.javatuples.Quartet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.clevertap.ui.pages.journey_page.JourneyHomePage.ActionToPerform;
import static com.clevertap.ui.pages.journey_page.JourneyHomePage.LinkNodeNames;

public class JourneyTestCases extends BaseTest{

    private Logger logger;
    private WebDriver driver;
    private SweetAlert sweetAlert;
    private JourneyHomePage journeyHomePage;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.JOURNEYS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        logger = baseTest.configureLogger(getClass());
        sweetAlert = new SweetAlert(driver);
        journeyHomePage = new JourneyHomePage(driver);
    }

        @Test(dataProvider = "dataJourney")
        public void test(Quartet<String,String,String,String>...quartets) {

            SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//*[@class='btn btn-success btn-large journeyBtn']")));
            JavascriptExecutor js=(JavascriptExecutor)driver;
            String finalJourneyJson = journeyHomePage.createAndLinkNode(quartets);
            SeleniumUtils.pause(10);
            js.executeScript("setModel("+finalJourneyJson+")");
        }


        @DataProvider(name="dataJourney")
        public Object[][] dataProvider(){

            return new Object[][]{
                    {
                            Quartet.with(LinkNodeNames.ACTION,LinkNodeNames.PUSH,ActionToPerform.Yes.toString(),"0-minutes"),
                            Quartet.with(LinkNodeNames.ACTION+"2",LinkNodeNames.SMS,ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.INACTION,LinkNodeNames.PUSH,ActionToPerform.Yes.toString(),"10-hours"),
                            Quartet.with(LinkNodeNames.INACTION+"2",LinkNodeNames.SMS,ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.PASTBEHAVIOR,LinkNodeNames.EMAIL,ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.PASTBEHAVIOR+"2",LinkNodeNames.EMAIL,ActionToPerform.No,"1-days"),
                            Quartet.with(LinkNodeNames.DATETIME,LinkNodeNames.SMS+"2",ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.DATETIME+"2",LinkNodeNames.SMS+"2",ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.JOURNEYTRIGGER,LinkNodeNames.EMAIL+"2",ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.JOURNEYTRIGGER+"2",LinkNodeNames.EMAIL+"2",ActionToPerform.No,"10-days"),
                            Quartet.with(LinkNodeNames.PAGEVISIT,LinkNodeNames.WEBPUSH,ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.PAGEVISIT+"2",LinkNodeNames.EXITINTENT,ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.REFERRERENTRY,LinkNodeNames.WEBHOOK,ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.REFERRERENTRY+"2",LinkNodeNames.WEBPOPUP,ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.PAGECOUNT,LinkNodeNames.INBOX,ActionToPerform.No,"0-minutes"),
                            Quartet.with(LinkNodeNames.PAGECOUNT,"","",""),
                            Quartet.with(LinkNodeNames.ACTION,"","",""),
                            Quartet.with(LinkNodeNames.INACTION,"","","")
                    }
            };
        }




}
