package com.clevertap.ui;

import com.clevertap.BaseTest;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AllDashboardMenusLoadTest extends BaseTest{
    private Logger logger;
    private WebDriver driver;
    private String xpath = "//div[@class='ct-breadcrumb']";
    private String id="breadcrumbs";

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, "", "", "");
        logger = baseTest.configureLogger(getClass());
    }

    @Test(description = "Verify dashboards menu", groups = {TestCaseGroups.LOADINGALLMENUTESTCRITICAL}, dataProvider = "menuDetails")
    public void verifyMenus(String menu, String subMenu, String superSubMenu,String menuDevelopedInView) {
        Reporter.log(("Verify " + menu + "-" + subMenu + "-" + superSubMenu + " menu"));
        String pageHeader;
        Mocha.openLeftNavMenu(driver,false, menu, subMenu, superSubMenu);
        if (subMenu!=null && !subMenu.isEmpty() && subMenu.equalsIgnoreCase(menuDevelopedInView)){
            pageHeader = driver.findElement(By.id(id)).getText().toLowerCase();
        }else {
            pageHeader = driver.findElement(By.xpath(xpath)).getText().toLowerCase();
        }

        SeleniumUtils.waitForPageLoaded(driver);
        Reporter.log(("Respective Page Header Text is: " + pageHeader));
        Assert.assertTrue(pageHeader.contains(subMenu.toLowerCase()) || pageHeader.contains(superSubMenu.toLowerCase()));



    }

    @DataProvider(name = "menuDetails")
    public Object[][] dataProviderMethod() {
        return new Object[][]
                {
                        {NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.All_BOARDS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.TODAY.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.MOBILE_APP.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.Unistall.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.REVENUE.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.EVENTS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.FUNNELS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.COHORTS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.TRENDS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.PIVOTS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.FLOWS.toString(), "",""},
                        /*{NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.ATTRIBUTION.toString(), ""},- deprecated*/
                        /*{NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.DEVICE_CROSSOVERS.toString(), ""},-depreacted*/
                        {NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.JOURNEYS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CLEVER_CAMPAIGNS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CATALOGS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.RECOMMENDATIONS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.REAL_IMPACT.toString(), NavigateCTMenuEnums.Submenu.NOSUBMENU.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ACCOUNT.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.APPS_AND_USAGE.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.MYPROFILE.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), "",NavigateCTMenuEnums.Submenu.USERS.toString()},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ROLES.toString(), "",NavigateCTMenuEnums.Submenu.ROLES.toString()},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.MY_DOWNLOADS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.DATA_EXPORTS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.EMAIL.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.SMS.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBPUSH.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WHATSAPP.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.REMARKETING.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBHOOKS.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGN_LABELS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGN_REPORTS.toString(), "",""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGN_SETTINGS.toString(), NavigateCTMenuEnums.SuperSubMenu.GLOBAL_CAMPAIGN_LIMITS.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGN_SETTINGS.toString(), NavigateCTMenuEnums.SuperSubMenu.BEST_TIME_CAMPAIGN.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGN_SETTINGS.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_AMPLIFICATION.toString(),""},
                        {NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.APP_UNINSTALLS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString(),""},

                };
    }

}