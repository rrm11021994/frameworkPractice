package com.clevertap.ui.pages.settings_menu_page;


import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataExports {
    WebDriver driver;

    @FindBy(className = "ct-breadcrumb")
    private WebElement pageTitle;
    @FindBy(xpath = "//*[@id='createNewRequest']//a")
    private WebElement createNewRequest;
    @FindBy(xpath = "//*[contains(@class,'ct-tab-line')]")
    private List<WebElement> dataExportTabs;
    @FindBy(xpath = "//*[@id='exportList']")
    private WebElement listOfExports;
    @FindBy(xpath = "//*[@id='exportSettings']")
    private WebElement settingsExports;
    @FindBy(xpath = "//*[@id='exportsTable']")
    private WebElement tableExports;
    @FindBy(xpath = "//*[@id='submitAWSInfo']")
    private WebElement saveSetting;
    @FindBy(xpath = "//*[@id='s3Bucket']")
    private WebElement inputName;
    @FindBy(xpath = "//*[@id='awsAccess']")
    private WebElement awsAccess;
    @FindBy(xpath = "//*[@id='awsSecret']")
    private WebElement awsSecretKey;
    @FindBy(xpath = "//input[@name='requestType']/../label")
    private List<WebElement> radioButtonsExportType;
    @FindBy(xpath = "//input[@name='frequency']/../label")
    private List<WebElement> radioButtonsFrequency;
    @FindBy(xpath = "//*[@id='oneTimeSelect_chzn']")
    private WebElement dropDownTimeLimit;
    @FindBy(xpath = "//*[@id='OutputAllDataasString']/../label")
    private WebElement checkBoxOutputAllData;
    @FindBy(xpath = "//*[@id='saveExportRequest']")
    private WebElement saveCreateDataExports;

    @FindBy(xpath = "//*[@id='customEventsDiv']//*[name()='svg']")
    private WebElement deleteSymbol;

    @FindBy(xpath = "(//div[@id='customEventsDiv']//a[@class='chzn-single'])")
    private WebElement dropDownAddEvents;

    @FindBy(xpath = "//*[@id='customEventsAddButton']")
    private WebElement addEvent;



    public void deleteAddedEvent(String countOfDropdown) {
        driver.findElement(By.xpath("(//*[@id='customEventsDiv']//*[name()='svg'])[" + countOfDropdown + "]")).click();

    }

    public void checkBoxOutputAllData() {
        SeleniumUtils.performClick(driver, checkBoxOutputAllData);
    }


    public void addCustomEvent() {
        addEvent.click();

    }

    public String getHeaderText() {
        return SeleniumUtils.getElementText(driver, pageTitle);

    }


    public void clickOnSaveForNewRequest() {
        SeleniumUtils.performClick(driver, saveCreateDataExports);
    }


    public void exportTypeRadioButton(String nameOfRadioButton) {
        for (WebElement radio : radioButtonsExportType) {
            if (radio.getText().equalsIgnoreCase(nameOfRadioButton)) {
                radio.click();
            }
        }
    }

    public void frequencyRadioButton(String nameOfRadioButton) {
        for (WebElement radio : radioButtonsFrequency) {
            if (radio.getText().equalsIgnoreCase(nameOfRadioButton)) {
                radio.click();
            }
        }
    }


    public void selectDaysDropdown(String timeToLive) {
        dropDownTimeLimit.click();
        driver.findElement(By.xpath("//*[@id='oneTimeSelect_chzn']" + "//li[contains(text(),'" + timeToLive + "')]")).click();

    }


    public void addEvent(String eventName, String dropDownNumber) {
        driver.findElement(By.xpath("(//div[@id='customEventsDiv']//a[@class='chzn-single'])[" + dropDownNumber + "]")).click();
        driver.findElement(By.xpath("(//*[@id='customEventsDiv']//*[name()='svg']/../div//li[text()='" + eventName + "'])[" + dropDownNumber + "]")).click();

    }


    public int getTotalCountOFTable() {
        List<WebElement> rows = driver.findElements(By.xpath("//*[@id='exportsTable']/tr"));
        return rows.size();

    }



    public boolean expectedTableCount() {
        int actualTableValue = getTotalCountOFTable();
        return actualTableValue > 1;

    }


    public void blankCredentials() {
        inputName.clear();
        awsAccess.clear();
        awsSecretKey.clear();
        SeleniumUtils.performClick(driver, saveSetting);

    }


    public void changeInExistingCredentials(String s3BucketName) {
        inputName.sendKeys(s3BucketName);
        SeleniumUtils.performClick(driver, saveSetting);
    }


    public void dataExportClickOnTabs(String tabName) {

        for (WebElement element : dataExportTabs) {
            if (element.getText().equalsIgnoreCase(tabName)) {
                SeleniumUtils.performClick(driver, element);
            }
        }
    }

    public void saveDataOnSettingsTab() {
        SeleniumUtils.performClick(driver, saveSetting);
    }


    public void createNewRequest() {
        createNewRequest.click();
    }



    public String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
        String date=dateFormat.format(new Date());
        return date;
    }

    public String getDate(){
        String dateValue= listOfExports.getText();
        return dateValue;
    }

    public boolean getUserNameAndCurrentDateFromTable(WebDriver driver, String creatorName, String creationDate) {
        WebElement tableRow = driver.findElement(By.xpath("//*[@id='exportList']//td[text()='" + creatorName + "']/following-sibling::td[text()='" + creationDate + "']"));
        return tableRow != null;


    }






    public DataExports(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);

    }
}



