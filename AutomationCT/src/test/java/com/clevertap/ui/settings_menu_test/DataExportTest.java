package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.settings_menu_page.DataExports;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataExportTest extends BaseTest{
    private static final String SETTINGS_DATA_EXPORTS = "Settings\nData Exports";
    private static final String LIST = "List";
    private static final String SELECT_EVENTS = "Select Events";
    private static final String TESTING_1 = "Charged";
    private static final String TESTING_VIEW = "App Launched";
    private static final String ONE_TIME = "One time";
    private static final String LAST_10_DAYS = "Last 10 days";
    private static final String SETTINGS = "Settings";
    private static final String TEST = "Test";
    private Logger logger;
    private WebDriver driver;
    private static final String PASSED = "Passed";
    private Properties prop;
    private static List<String> createdBy = new ArrayList<>();

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.PARTNER_DATA_EXPORTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        logger = baseTest.configureLogger(getClass());
    }


    @Test(description = "verify the page header", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.DATAEXPORTCRITICAL})
    public void openDataExportSettingsPage() {
        logger.info("open Data Exports page Test Started");
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.PARTNER_DATA_EXPORTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        DataExports dataExports = new DataExports(driver);
        String dataExportPageHeaderText = dataExports.getHeaderText();
        Assert.assertTrue(dataExportPageHeaderText.contains(SETTINGS_DATA_EXPORTS), "Data Exports page successfully launched");
        logger.info("Open Data Exports Page  Test Finished");
    }


    @Test(description = "verify the Click of Two different Tabs", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.DATAEXPORTCRITICAL})
    public void verifyTheTabsAndSaveWithoutData() {
        logger.info("checking Tabs of data Exports Test Started");
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.PARTNER_DATA_EXPORTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        DataExports dataExports = new DataExports(driver);
        dataExports.dataExportClickOnTabs(SETTINGS);
        dataExports.saveDataOnSettingsTab();
        logger.info("checking Tabs of data Exports Test Finished");

    }

    @Test(description = "verify the Data exports with blank credentials ", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.DATAEXPORTCRITICAL})
    public void verifySettingCredentials() {
        logger.info("checking blank credentials of data Exports settings Test Started");
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.PARTNER_DATA_EXPORTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        DataExports dataExports = new DataExports(driver);
        dataExports.dataExportClickOnTabs(SETTINGS);
        dataExports.blankCredentials();
        SweetAlert sweetAlert = new SweetAlert(driver);
        Assert.assertTrue(sweetAlert.getSweetAlertRecBox(), "");
        logger.info("checking blank credentials of data Exports settings Test Finished");

    }


    @Test(description = "verify the Data exports with change in existing name credentials ", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.DATAEXPORTCRITICAL})
    public void verifyChangeInExistingCredentials() throws InterruptedException {
        logger.info("checking With change in existing credentials credentials of data Exports settings Test Started");
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.PARTNER_DATA_EXPORTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        DataExports dataExports = new DataExports(driver);
        dataExports.dataExportClickOnTabs(SETTINGS);
        dataExports.changeInExistingCredentials(TEST);
        SweetAlert sweetAlert = new SweetAlert(driver);
        SeleniumUtils.pause(2);
        Assert.assertTrue(sweetAlert.getSweetAlertRecBox(), "");
        logger.info("checking With change in existing credentials credentials of data Exports settings Test Finished");
    }


    @Test(description = "verify the page header", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.DATAEXPORTCRITICAL})
    public void openListTabsVerify() throws InterruptedException {
        logger.info("Creating new request for Data Export - Test Started");
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.PARTNER_DATA_EXPORTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        DataExports dataExports = new DataExports(driver);
        dataExports.dataExportClickOnTabs(LIST);
        SeleniumUtils.pause(2);
        dataExports.getTotalCountOFTable();
        dataExports.createNewRequest();
        dataExports.exportTypeRadioButton(SELECT_EVENTS);
        SeleniumUtils.pause(3);
        dataExports.addEvent(TESTING_1, "1");
        SeleniumUtils.pause(3);
        dataExports.addCustomEvent();
        SeleniumUtils.pause(3);
        dataExports.addEvent(TESTING_VIEW, "2");
        SeleniumUtils.pause(3);
        dataExports.deleteAddedEvent("2");
        SeleniumUtils.pause(2);
        dataExports.frequencyRadioButton(ONE_TIME);
        dataExports.selectDaysDropdown(LAST_10_DAYS);
        dataExports.checkBoxOutputAllData();
        dataExports.clickOnSaveForNewRequest();
        logger.info("Creating new request for Data Export - Test Finished");
    }


    @Test(description = "verify the Data exports with change in existing name credentials ", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.DATAEXPORTCRITICAL}, dependsOnMethods = {"openListTabsVerify"})
    public void verifyTheList() {
        logger.info("Checking if New request has added or not in List Test Started");
        DataExports dataExports = new DataExports(driver);
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.PARTNER_DATA_EXPORTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        dataExports.dataExportClickOnTabs(LIST);
        TableUtility.InitializeTable(driver, "//*[@id='exportsTable']");
        List<WebElement> cells = TableUtility.getDataFromSpecificCell(2);
        for (WebElement cellElement : cells) {
            createdBy.add(cellElement.getText());
        }
        Assert.assertTrue(createdBy.contains(BaseTest.getValue("UserName")), "User Name verified");
        dataExports.expectedTableCount();
        dataExports.getCurrentDate();
        Assert.assertTrue(dataExports.getDate().contains(dataExports.getCurrentDate()));
        logger.info("Checking if New request has added or not in List Test finished");
    }


    @Test(description = "verify the Data exports with change in existing name credentials ", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.DATAEXPORTCRITICAL}, dependsOnMethods = {"verifyTheList"})

    public void verifyRowWithUserNameAndCurrentDate() {
        logger.info("checking if new row consist UserName and Current date - Test Started ");
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.PARTNER_DATA_EXPORTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        DataExports dataExports = new DataExports(driver);
        boolean getTableData = dataExports.getUserNameAndCurrentDateFromTable(driver, BaseTest.getValue("UserName"), dataExports.getCurrentDate());
        Assert.assertTrue(getTableData, "Table Date and UserName are verified");
        logger.info("checking if new row consist UserName and Current date - Test finished");
    }
}

