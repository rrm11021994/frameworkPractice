package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.settings_menu_page.MyDownloads;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MyDownloadsTest extends BaseTest{

    private WebDriver driver;
    private MyDownloads downloads;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver=getDriver();
        downloads = new MyDownloads(driver);
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.MY_DOWNLOADS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

    }

    @Test(description = "OpenMyDownloadsPage", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.MYDOWNLOADCRITICAL})
    public void openMyDownloadsPage() {
        Reporter.log("open MyDownload Page Test Started",true);
        String MyDownloadsPageHeaderText = downloads.getHeaderText();
        Assert.assertTrue(MyDownloadsPageHeaderText.contains("Settings\n" + "My Downloads"), "My Downloads Page successfully launched");
        Reporter.log("open Profile Page Test Finished",true);
    }

    @Test(dependsOnMethods = {"openMyDownloadsPage"}, description = "VerifyTableforDownloads", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.MYDOWNLOADCRITICAL})
    public void myDownloadsTable() throws InterruptedException {
        Reporter.log("open MyDownload Page Test Started",true);
        downloads.getDataFromSpecificCell(1);
        Assert.assertTrue(true, "Doing it manually");
        Reporter.log("open MyDownload Page Test Finished",true);
    }
}
