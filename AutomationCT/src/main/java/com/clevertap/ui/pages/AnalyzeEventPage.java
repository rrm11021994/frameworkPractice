package com.clevertap.ui.pages;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AnalyzeEventPage {

    WebDriver driver;

    @FindBy(xpath = "//div[@id='selectSegment_chzn']")
    WebElement segmentUserDropdown;
    @FindBy(xpath = "//div[@id='selectSegment_chzn']/div/ul/li[text()='Test - ankush']") WebElement user;
    @FindBy(xpath = "//div[@id='ct_date_widget']") WebElement dateDropdown;
    @FindBy(xpath = "//div[@id='ct_date_widget']//span[contains(.,'In the last 15 days')]") WebElement timeDuration;
    @FindBy(xpath = "//div[@class='evtOuter']") WebElement selectEventDropdown;
    //    @FindBy(xpath = "//div[@class='evtOuter']/../..//span[text()='Added To Cart']") WebElement selectEvent;
    @FindBy(xpath = "//*[@id='evtSelection1_chzn']//input") WebElement enterEvent;
    @FindBy(xpath = "//*[@id='aViewDetails']")
    public WebElement viewDetails;
    //    @FindBy(xpath = "(//*[@class='panel_count-lg PB25'])[0]") WebElement numberOfUsers;
    @FindBy(xpath = "//*[@id='totalEvntNum']") WebElement numberOfEvents;

    @FindBy(xpath = "//img[@data-original-title='Save segment']")
    public WebElement saveSegmentAnalyze;
    @FindBy(id="evtbkName")
    public WebElement segmentNameInputAnalyze;
    @FindBy(xpath="//button[contains(text(),'Create')]")
    public WebElement segmentCreateBtn;
    @FindBy(xpath="//img[@data-original-title='Create message']")
    public WebElement createMessageLink;
    @FindBy(xpath="//span[@class='startDownloading']")
    public WebElement userDownloadDisableLink;

    public void setUpEvents(){
        segmentUserDropdown.click();
        user.click();
        dateDropdown.click();
        timeDuration.click();
        selectEventDropdown.click();
        enterEvent.sendKeys("Added To Cart");
        enterEvent.sendKeys(Keys.RETURN);
        viewDetails.click();


    }

    public int getEventssCount(WebDriver driver){
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        String eventNumber=numberOfEvents.getText();
        if (eventNumber.contains(",")){
            return Integer.parseInt(eventNumber.replace(",",""));
        }else{
            return  Integer.parseInt(eventNumber);
        }
        // Integer.parseInt(userNumber);
    }

    public boolean isNumberGreaterThanZero(int num){

        return num >= 0;

    }

    public AnalyzeEventPage(WebDriver driverFromPreviousPage){
        this.driver=driverFromPreviousPage;
        PageFactory.initElements(driverFromPreviousPage,this);
    }

    public void saveSegementOnAnalyzePage(String segmentName){
        SeleniumUtils.waitAndClick(driver,saveSegmentAnalyze);
        SeleniumUtils.waitForElementToLoad(driver,segmentNameInputAnalyze);
        SeleniumUtils.enterInputText(driver,segmentNameInputAnalyze,segmentName);
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver,segmentCreateBtn);
    }
}
