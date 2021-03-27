package com.clevertap.ui.pages.settings_menu_page;


import com.clevertap.utils.FileUtility;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;

public class CSVUploads {

    private WebDriver driver;
    @FindBy(xpath = "//*[@class='import-section']//a")
    private WebElement dowmloadSampleCsv;
    @FindBy(xpath = "//*[@id='fileInput']")
    private WebElement importNewProfileFromCsv;
    @FindBy(className = "ct-breadcrumb")
    private WebElement pageTitle;
    @FindBy(xpath = "//*[@id='uploadBtn']")
    private WebElement uploadButton;
    @FindBy(xpath = "//*[@id='csvName']")
    private WebElement csvName;
    @FindBy(xpath = "//*[@class='confirm']")
    private WebElement confirmButton;


    public String getHeaderText() { return SeleniumUtils.getElementText(driver, pageTitle);
    }

    public void clickToDownload() {
        SeleniumUtils.performClick(driver, dowmloadSampleCsv);
    }

    public void clickUploadButton() {
        uploadButton.click();
//        SeleniumUtils.performClick(driver, uploadButton);
    }

    public void importNewCsv() { importNewProfileFromCsv.click();
    }

    public void uploadDownloadedCsv(File csvFile) throws AWTException, InterruptedException {
        FileUtility.uploadImage(csvFile);
    }


    public void newCsvUploadName(String newUploadCsvName) {
        SeleniumUtils.enterInputText(driver,csvName,newUploadCsvName);
    }

    public void confirmButton() {
        SeleniumUtils.performClick(driver, confirmButton);

    }


    public CSVUploads(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }
}
