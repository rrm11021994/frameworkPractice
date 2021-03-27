package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.settings_menu_page.CSVUploads;
import com.clevertap.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.awt.*;
import java.io.File;
import java.util.List;

public class CSVUploadsTest extends BaseTest {
    private static final String TEST_CSV_AUTOMATION = "Test-Csv-Automation";
    private static final String SETTINGS_CSV_UPLOADS = "Settings\nCSV Uploads";
    private Logger logger;
    private WebDriver driver;
    private String csvDynamicName = "";


    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver = getDriver();
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        logger = configureLogger(getClass());
    }

    @Test(description = "verify the page header", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void openCsvSettingsPage() {
        Reporter.log("open Csv uploads page Test Started",true);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        CSVUploads csvUploads = new CSVUploads(driver);
        String csvUploadsPageHeaderText = csvUploads.getHeaderText();
        Assert.assertTrue(csvUploadsPageHeaderText.contains(SETTINGS_CSV_UPLOADS), "csv Uploads successfully launched");
        Reporter.log("Open CSV uploads Page Test Finished",true);
    }


    @Test(description = "verify and delete previous Csv files", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.REGRESSION},dependsOnMethods = {"openCsvSettingsPage"})
    public void deleteAllPreviousCsvFilesFormFolder() {
        Reporter.log("Page Test Started deleting all previous csv sample files",true);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        FileUtility.deleteCSVFiles();
        Reporter.log("Verified and deleted all previous csv files",true);
    }

    @Test(description = "verify the downloading of Sample Csv", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.CSVUPLOADCRITICAL},dependsOnMethods = {"deleteAllPreviousCsvFilesFormFolder"})
    public void downloadNewSampleCsv() {
        Reporter.log("download new csv sample file - started",true);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        CSVUploads csvUploads = new CSVUploads(driver);
        csvUploads.clickToDownload();
        Reporter.log("csv File is downloaded - Test Finished",true);
    }


    @Test(description = "import new csv 1", groups = {TestCaseGroups.ALL, TestCaseGroups.CSVUPLOADCRITICAL, TestCaseGroups.REGRESSION},dependsOnMethods = {"downloadNewSampleCsv"})
    public void importNewCSV() throws AWTException, InterruptedException {
        Reporter.log("Import new csv test started",true);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        CSVUploads csvUploads = new CSVUploads(driver);
        SeleniumUtils.waitForPageLoaded(driver);
        csvUploads.importNewCsv();
        csvUploads.uploadDownloadedCsv(new File("../resources/test_bed/csv_uploads/SampleCSV.csv"));
        csvUploads.clickUploadButton();
        csvDynamicName = Data.randomAlphaNumeric(TEST_CSV_AUTOMATION, 4);
        csvUploads.newCsvUploadName(csvDynamicName);
        csvUploads.confirmButton();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = driver.findElement(By.xpath("//*[@class='CT-table__right']//div[text()='"+csvDynamicName+"']"));
        wait.until(ExpectedConditions.visibilityOf(element));
        Reporter.log("imported CSV uploads Page Test Finished",true);
    }


    @Test(description = "verify the List of Csv", groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.CSVUPLOADCRITICAL}, dependsOnMethods = {"importNewCSV"})
    public void checkCsvList() {
        Reporter.log("Checking the List of Csv Files present",true);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        List<String> data = TableUtility.getDataFromRightTable("//*[@class='CT-table__right']", 1, driver);
        Assert.assertTrue(data.contains(csvDynamicName), "New csv file is uploaded");
        Reporter.log("Csv list consist new uploaded csv file",true);


    }

}
