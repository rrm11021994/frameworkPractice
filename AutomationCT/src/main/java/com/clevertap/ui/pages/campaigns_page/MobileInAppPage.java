package com.clevertap.ui.pages.campaigns_page;

import com.clevertap.utils.FileUtility;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class MobileInAppPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@class='ct-breadcrumb']")
    private WebElement breadcumpTitle;

    @FindBy(xpath = "//*[contains(@class,'js-createCampaign')]")
    private WebElement campaignCreateBtn;

    @FindBy(xpath = "//*[@class='channel-blocks']//div[text()='Mobile In-app']")
    private WebElement mobileInAppBtn;

    @FindBy(xpath = "//*[@id='js-start-now']/../label")
    private WebElement campaignStartDateNow;

    @FindBy(id = "btn_top_nav_continue")
    private WebElement continueBtn;

    @FindBy(xpath = "//*[text()='Select message type']")
    private WebElement selectMessageTypeLabel;

    @FindBy(xpath = "//div[@class='inapp-template-controls-header']")
    private WebElement contentHeader;

    @FindBy(xpath = "//div[@class='selectOrientationBox']//div/a")
    private WebElement layoutDropDown;

    @FindBy(xpath = "//div[@class='selectOrientationBox']//li")
    private List<WebElement> listOfLayoutToSelect;

    @FindBy(xpath = "//ul[@class='ct-nav-tabs brdb']/li")
    private List<WebElement> listOfLayoutTabs;

    @FindBy(xpath = "//div[@id='sel1S3_chzn']")
    private WebElement selectBtn;

    @FindBy(xpath = "//img[@class='pane-container_close js-pane-container_close']")
    private WebElement crossBtn;

    @FindBy(xpath = "//button[@class='confirm' and text()='Discard']")
    private WebElement discardBtn;

    @FindBy(xpath="//*[text()='A/B test']")
    private WebElement abRadioBtn;

    @FindBy(xpath = "(//div[contains(@class,'dropdown__label') ]/span[text()='Variant B'])[2]")
    private WebElement VariantBActivate;

    @FindBy(xpath = "//div[@class='loader']//*[contains(@fill-rule,'evenodd')]")
    private WebElement imageLoader;

    @FindBy(className = "save-button")
    private WebElement saveImageBtn;

    @FindBy(xpath = "//div[contains(text(),'Title')]/..//div[contains(@class,'emoji-wysiwyg')]")
    private WebElement titleInputBox;

    @FindBy(xpath = "//div[contains(text(),'Message')]/..//div[contains(@class,'emoji-wysiwyg')]")
    private WebElement messageInputBox;

    @FindBy(xpath= "//*[@class='btn btn-white']")
    private WebElement addNewBtn;

    @FindBy(xpath = "(//input[contains(@id,'button')])[1]")
    private WebElement messageButton1;

    @FindBy(xpath = "(//input[contains(@id,'button')])[2]")
    private WebElement messageButton2;

    @FindBy(xpath = "//button[@data-sn='what']")
    private WebElement continueOnWhatBtn;

    @FindBy(xpath = "//button[@data-sn='goal']")
    private WebElement continueOnSetupBtn;

    @FindBy(id="createTarget")
    private WebElement scheduleNotificationBtn;

    @FindBy(id="jname")
    private WebElement campaignNameTextBox;

    @FindBy(xpath = "//button[@class='confirm' and text()='Save']")
    private WebElement campaignSaveBtn;

    @FindBy(xpath = "//div[contains(@class,'breadcrum') and contains(text(),'Report for')]")
    private WebElement campaignCreatedBreadcrum;

    public String getBreadcumbText() {
        return breadcumpTitle.getText();
    }


    private void createMobileInAppCampaign() {
        SeleniumUtils.performClick(driver, campaignCreateBtn);
        driver.switchTo().frame(0);
    }


    private void createMobileInApp() {
        SeleniumUtils.performClick(driver, mobileInAppBtn);
    }


    private void jumpToMessagePage() {
        SeleniumUtils.performClick(driver, campaignStartDateNow);
        SeleniumUtils.performClick(driver, continueBtn);
        SeleniumUtils.performClick(driver, continueBtn);
        SeleniumUtils.performClick(driver, continueBtn);
    }

    public void selectMessageFormat(String formatName, String formatType) {
        String xpathPrefix = "//div[@class='inapp-template-name' and text()='";
        String xpathSuffix = "']";
        String xpath = xpathPrefix + formatName + xpathSuffix;
        SeleniumUtils.moveToElement(driver.findElement(By.xpath(xpath)), driver);
        xpathPrefix = xpath + "/..//div/button[text()='";
        xpath = xpathPrefix + formatType + xpathSuffix;
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(xpath)));
    }


    public String getSelectMessageTypeLabel() {
        return selectMessageTypeLabel.getText();
    }

    public String getContentHeader() {
        return contentHeader.getText();
    }

    public void selectTile(String tileName) {
        driver.findElement(By.xpath("//div[@class='custom-collapse']/div[contains(text(),'"+tileName+"')]")).click();
    }


    public void selectLayout(String option) {
        driver.findElement(By.xpath("//div[@class='selectOrientationBox']//a")).click();
        driver.findElement(By.xpath("//div[@class='selectOrientationBox']//li[contains(text(),'"+option+"')]")).click();
    }

    public String getLayoutTabs() {
        StringBuilder strBld=new StringBuilder();
        for (WebElement ele : listOfLayoutTabs) {
            strBld.append(ele.getText() + "/");
        }
        return strBld.toString();
    }

    public void clickClose() throws InterruptedException {
        driver.switchTo().defaultContent();
        crossBtn.click();
        Thread.sleep(2000);
        driver.switchTo().frame(0);
        discardBtn.click();
        Thread.sleep(2000);
        driver.switchTo().defaultContent();

    }

    public void goToCreateMsgPage() {
        createMobileInAppCampaign();
        createMobileInApp();
        jumpToMessagePage();
    }

    public void selectVariantB(){
        SeleniumUtils.performClick(driver,VariantBActivate);
    }

    public void selectABRadioBtn(){
        SeleniumUtils.performClick(driver, abRadioBtn);
    }

    public void selectImageRadioBtn(String btnName){
        driver.findElement(By.xpath("//div[contains(@class,'custom-radio')]//span[text()='"+btnName+"']")).click();
    }

    public void clickToBrowzeImage(){
        imageLoader.click();
    }

    public void uploadImage(File file)throws AWTException,InterruptedException{
        FileUtility.uploadImage(file);
    }

    public boolean checkIfImageLoaded(String section){
        boolean imageUploaded = false;
        try{
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            if(section.equalsIgnoreCase("ImageUpload")) {
                driver.findElement(By.xpath("//div[@class='canvas']/img"));
            }
            else if(section.equalsIgnoreCase("Preview")){
                driver.findElement(By.xpath("//div[contains(@class,'phone-preview-content') and contains(@style,'background: url(\"https:')]"));
            }
            imageUploaded = true;
        }catch (NoSuchElementException e){
            //DO Nothing
        }
        finally {
            driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
        }
        return imageUploaded;
    }

    public void clickSaveImageBtn(){
        SeleniumUtils.performClick(driver,saveImageBtn);
    }

    public void giveTitle(String title){
        SeleniumUtils.enterInputText(driver,titleInputBox,title);
    }

    public void giveMessage(String message){
        SeleniumUtils.enterInputText(driver,messageInputBox,message);
    }

    public void addBtn(){
        SeleniumUtils.performClick(driver,addNewBtn);
    }
    public void addTextToBtn(String number, String buttonTitle){
        SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//input[contains(@id,'button-"+(Integer.parseInt(number)-1)+"')]")),buttonTitle);
    }
    public void clickContinueOnWhatBtn(){
        SeleniumUtils.performClick(driver,continueOnWhatBtn);
    }

    public void clickContinueOnSetupBtn(){
        SeleniumUtils.performClick(driver,continueOnSetupBtn);
    }
    public void clickscheduleNotificationBtn(){
        SeleniumUtils.performClick(driver,scheduleNotificationBtn);
    }

    public void setCampaignNameAndSave(String name)throws InterruptedException{
        SeleniumUtils.enterInputText(driver,campaignNameTextBox,name);
        SeleniumUtils.performClick(driver,campaignSaveBtn);
        Thread.sleep(2000);
    }

    public String getTextForCampaignBreadCrum(){
        String breadCrum="";
        breadCrum = campaignCreatedBreadcrum.getText();
        return breadCrum;
    }

    public MobileInAppPage(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }
}
