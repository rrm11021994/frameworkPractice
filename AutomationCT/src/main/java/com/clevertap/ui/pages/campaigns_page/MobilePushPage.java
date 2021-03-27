package com.clevertap.ui.pages.campaigns_page;


import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import static com.clevertap.utils.SeleniumUtils.*;

import java.util.ArrayList;
import java.util.List;


public class MobilePushPage {

    private static final String BR_TESTING = "BRTesting";
    public static final String DIV_CLASS_FILTERBTN_AND_TEXT = "//div[@class='filterbtn' and text()='";
    private WebDriver driver;
    private static SweetAlert sweetAlert;

    @FindBy(className="ct-breadcrumb")
    private WebElement breadcumbTitle;

    @FindBy(xpath = "//button[contains(@class,'js-createCampaign')]")
    private WebElement campaignCreateBtn;

    @FindBy(xpath = "//*[@class='channel-blocks']//div[text()='Mobile Push']")
    private WebElement mobilePushBtn;

    @FindBy(xpath = "//*[@id='live-user-block']//div[text()='Single action']")
    private WebElement pbsOneTime;

    @FindBy(xpath = "//*[@id='js-start-now']/../label")
    private WebElement campaignStartDateNow;

    @FindBy(id="btn_top_nav_continue")
    private WebElement continueBtn;

    @FindBy(xpath = "//*[@data-campaign-type='singleMessage']")
    private WebElement singleMessageSelect;

    @FindBy(id = "emoji_wzrk_tgt_message_title")
    private WebElement inputTitle;

    @FindBy(id = "emoji_wzrk_tgt_message_text")
    private WebElement inputText;

    @FindBy(id = "androidTtlInputDays")
    private WebElement pushMessageTimeToLive;

    @FindBy(id = "jname")
    private WebElement inputCampaignName;

    @FindBy(xpath = "//*[@id='jname']/../../button[@class='confirm']")
    private WebElement saveCampaign;

    @FindBy(xpath="//input[@id='android-o-check']/../label")
    private WebElement androidOCheckbox;

    @FindBy(xpath = "//*[@id='selectaochannel_chzn']/a")
    private WebElement channelDropDown;
    @FindBy(xpath = "//*[@id='selectaochannel_chzn']//li")
    private List<WebElement> androidOChannelsList;

    @FindBy(id="aochannel")
    private WebElement channelInputBox;

    @FindBy(xpath = "//a[contains(@class,'campaign-stop')]")
    public WebElement stopCampaignButton;

    @FindBy(xpath = "//a[contains(@class,'campaign-edit')]")
    public WebElement editCampaign;

    @FindBy(xpath = "//a[contains(@class,'campaign-clone')]")
    public WebElement cloneCampaignBtn;

    @FindBy(id="builderCustomName")
    private WebElement campaignNameBox;

    @FindBy(className="breadcrumb-text-overflow")
    private WebElement cloneCampaignBreadcrum;

    @FindBy(id = "searchDiv")
    public WebElement searchBox;

    @FindBy(xpath = "//label[@for='selectId']/span")
    public WebElement selectAllVisibleCampaignBtn;

    @FindBy(className = "archive_link")
    public WebElement archiveBtn;

    @FindBy(className = "bulk_stop_link")
    private WebElement bulkStopBtn;

    @FindBy(xpath = "//button[@id='btn_top_nav_continue' and @disabled='disabled']")
    private WebElement continueDisabledButton;

    @FindBy(xpath = "//label[@class='android-o-settings-check']/span")
    private WebElement addNotificationChannelCheckBox;

    @FindBy(name ="currentAndroidOChannel")
    private WebElement notificationChannelTextBox;

    @FindBy(xpath ="//input[@value='Save']")
    private WebElement saveAndroidChannel;

    @FindBy(xpath = "//p[text()='Notification Channels updated successfully']")
    private WebElement notificationChannelUpdatedText;

    @FindBy(id ="addAndroidOChannel")
    private WebElement addAndroidChannel;

    @FindBy(id="addAOChannelsLink")
    private WebElement addChannelLink;

    @FindBy(className = "confirm")
    private WebElement confirmSaveChannelButton;

    @FindBy(xpath = "//input[@data-key='wzrk_cid']")
    private WebElement notificationChannelInput;

    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")
    private static WebElement sweetAlertConfirmOK;

    @FindBy(xpath = "//li[@data-step-name='overview' and @class='is-current']")
    private static WebElement onOverviewPage;

    @FindBy(xpath = "//button[text()='Schedule cloned notification' and @id='btn_top_nav_continue']")
    private static WebElement scheduleCloneNotification;

//    @FindBy(id="android-o-settings-check")
    @FindBy(xpath="//*[@for='android-o-settings-check']//span")
    private  WebElement addNotificationCheckBox;

    @FindBy(id = "aOChannelsButton")
    private static WebElement savePushNotificationChannelSetting;

    public String getBreadcumbText() {
        return breadcumbTitle.getText();
    }

    public void createCampaign() {
        try{
            SeleniumUtils.waitAndClick(driver,campaignCreateBtn);
        }catch(Exception e){
//            driver.navigate().refresh();
            SeleniumUtils.pause(3);
            driver.findElement(By.xpath("//button[contains(@class,'js-createCampaign')]")).click();
        }

        driver.switchTo().frame(0);
    }

    public void createMobilePush() {
        SeleniumUtils.waitAndClick(driver, mobilePushBtn);
    }

    public void selectOneTimePBS() {
        SeleniumUtils.waitAndClick(driver, pbsOneTime);
    }

    public void selectCampaignStartDate() {
        SeleniumUtils.waitAndClick(driver, campaignStartDateNow);
        SeleniumUtils.pause(2);
        boolean flag=false;
        while (!flag){
            flag=continueBtn.isEnabled();
        }
        SeleniumUtils.waitAndClick(driver, continueBtn);
        SeleniumUtils.waitAndClick(driver, continueBtn);
        SeleniumUtils.waitAndClick(driver, continueBtn);
        SeleniumUtils.pause(2);
    }


    public void selectMessageType() {
        SeleniumUtils.waitAndClick(driver, singleMessageSelect);
    }


    public void enterCampaignMeta(String campaignTitle, String campaignText, String campaignName){
        SeleniumUtils.waitForElementToLoad(driver,inputTitle);
        SeleniumUtils.enterInputText(driver, inputTitle, campaignTitle);
        SeleniumUtils.enterInputText(driver, inputText, campaignText);
        SeleniumUtils.scrollDown(driver,"250");
        selectAndroidOChannel(BR_TESTING);
        SeleniumUtils.waitAndClick(driver, continueBtn);
        SeleniumUtils.enterInputText(driver, pushMessageTimeToLive, "1");
        SeleniumUtils.waitAndClick(driver, continueBtn);
        SeleniumUtils.waitAndClick(driver, continueBtn);
        SeleniumUtils.waitForElementToLoad(driver,inputCampaignName);
        SeleniumUtils.enterInputText(driver, inputCampaignName, campaignName);
        SeleniumUtils.pause(1);//Do not remove this pause as it effects headless mode
        SeleniumUtils.waitAndClick(driver, saveCampaign);
        SeleniumUtils.pause(2);
    }

    public void selectAndroidOChannel(String channelName){
//        if(!channelDropDown.isDisplayed()){
        if(driver.findElements(By.xpath("//*[@id='selectaochannel_chzn']/a")).size()==0){
            waitForElementToLoad(driver,notificationChannelInput);
            enterInputText(driver,notificationChannelInput,channelName);
        }else {
            channelDropDown.click();
            for (WebElement element : androidOChannelsList) {
                if (element.getText().trim().equalsIgnoreCase(channelName)) {
                    element.click();
                }
            }
        }
    }

    public List<String> getTableCellData() {
        List<String> campaignsList = new ArrayList<>();
        try {
            List<WebElement> rows = driver.findElements(By.xpath("//*[@class='CT-table__left']//div[2]"));
            for (WebElement row : rows) {
                campaignsList.add(row.getText());
            }
        } catch (Exception e) {
            List<WebElement> rows = driver.findElements(By.xpath("//*[@class='CT-table__left']//div[2]"));
            for (WebElement row : rows) {
                campaignsList.add(row.getText());
            }
        }
        return campaignsList;
    }

    public void clickShowCampaignAction(String campaignName){
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[@data-original-title='" + campaignName + "']/../..//div[@data-original-title='Show campaign actions']")),10);
        WebElement element=driver.findElement(By.xpath("//*[@data-original-title='" + campaignName + "']/../..//div[@data-original-title='Show campaign actions']"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
    }

    public void editCampaign(String newName){
        SeleniumUtils.waitAndClick(driver,editCampaign);
        SeleniumUtils.waitForElementToLoad(driver,campaignNameBox);
        driver.switchTo().frame(0);
//        campaignNameBox.clear();
//        campaignNameBox.sendKeys(newName+"-edited");
//        continueBtn.click();
//        SeleniumUtils.waitForPageLoaded(driver);
//        campaignNameBox.clear();
//        campaignNameBox.sendKeys(newName+"-edited");
        SeleniumUtils.enterInputText(driver,campaignNameBox,newName+"-edited");
//        continueBtn.click();
        SeleniumUtils.waitAndClick(driver,continueBtn);
        driver.switchTo().defaultContent();
    }

    public void stopCampaign(String campaignName){
//        stopCampaignButton.click();
        SeleniumUtils.enterInputText(driver,searchBox,Keys.chord(campaignName+Keys.RETURN));
        SeleniumUtils.pause(1);
        SeleniumUtils.waitForElementToLoad(driver,selectAllVisibleCampaignBtn);
        SeleniumUtils.waitAndClick(driver,selectAllVisibleCampaignBtn);
        SeleniumUtils.waitForElementToLoad(driver,bulkStopBtn);
//        SeleniumUtils.waitAndClick(driver,bulkStopBtn);
        bulkStopBtn.click();
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver,sweetAlertConfirmOK);
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver,sweetAlertConfirmOK);
    }

    public void stopAllAutomationCampaign(String campaignName){
        SeleniumUtils.enterInputText(driver,searchBox,Keys.chord(campaignName+Keys.RETURN));
        SeleniumUtils.waitForElementToLoad(driver,selectAllVisibleCampaignBtn);
        SeleniumUtils.waitAndClick(driver,selectAllVisibleCampaignBtn);
        SeleniumUtils.waitAndClick(driver,bulkStopBtn);
        SeleniumUtils.waitForElementToLoad(driver,sweetAlertConfirmOK);
        sweetAlert.sweetAlertConfirmOK();
        SeleniumUtils.waitForPageLoaded(driver);
        sweetAlert.sweetAlertConfirmOK();
    }

    public void cloneCampaign(String newName){
        SeleniumUtils.waitAndClick(driver,cloneCampaignBtn);
        driver.switchTo().frame(0);
        SeleniumUtils.pause(1);//added pause
//        SeleniumUtils.waitForElementToLoad(driver,campaignNameBox);
//        campaignNameBox.clear();
//        campaignNameBox.sendKeys(Keys.CONTROL+"a");
//        campaignNameBox.sendKeys(Keys.DELETE);
//        campaignNameBox.sendKeys(newName+"-cloned");
        SeleniumUtils.enterInputText(driver,campaignNameBox,newName+"-cloned");
//        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.waitForElementToLoad(driver,onOverviewPage,20);
        SeleniumUtils.waitForElementToClickable(driver,scheduleCloneNotification,20);
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+scheduleCloneNotification.getLocation().x+")");
        SeleniumUtils.waitAndClick(driver,scheduleCloneNotification);
        driver.switchTo().defaultContent();
    }

    public void archiveAutomationCampaigns(String campaignName){
        SeleniumUtils.enterInputText(driver,searchBox,Keys.chord(campaignName+Keys.RETURN));
        SeleniumUtils.pause(1);
        SeleniumUtils.waitForElementToLoad(driver,selectAllVisibleCampaignBtn);
        SeleniumUtils.waitAndClick(driver,selectAllVisibleCampaignBtn);
        SeleniumUtils.waitForElementToLoad(driver,archiveBtn);
        SeleniumUtils.waitAndClick(driver,archiveBtn);
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver,sweetAlertConfirmOK);
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver,sweetAlertConfirmOK);
    }

//    public void filter(String filterIn, String type){
//        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT +filterIn+"']")),10);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT +filterIn+"']")));
//        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//div[@class='clear-all-filter']")),10);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//div[@class='clear-all-filter']")));
//        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']")),10);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']")));
//        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//li/input[@data-name='"+type+"']/..")),10);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].click();",  driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//li/input[@data-name='"+type+"']/..")));
//        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//a")),10);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].click();",driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//a")));
//    }

    public void filter(String filterIn, String type){
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT +filterIn+"']")),10);
         driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT +filterIn+"']")).click();
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//div[@class='clear-all-filter']")),10);
         driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//div[@class='clear-all-filter']")).click();
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']")),10);
        driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']")).click();
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//li/input[@data-name='"+type+"']/..")),10);
        driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//li/input[@data-name='"+type+"']/..")).click();
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//a")),10);
        driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+filterIn+"']/..//a")).click();
    }


    public String getClonedBreadcrumb(){
        SeleniumUtils.waitForElementToClickable(driver,cloneCampaignBreadcrum,10);
        return cloneCampaignBreadcrum.getText();
    }

    public MobilePushPage(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
        sweetAlert = new SweetAlert(previousBrowserDriver);
    }

    public void addChannel(String channelName){
        String currentWindow=driver.getWindowHandle();
        String verifyAddedChannel="//div[@class='aoChannelsContainer']//span[text()='"+channelName+"']";
        if(addChannelLink.isDisplayed()){
            waitAndClick(driver,addChannelLink);
            ArrayList<String> integrationWindow = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(integrationWindow.get(1));
            waitForElementToLoad(driver,addNotificationChannelCheckBox);
            addNotificationChannelCheckBox.click();
            waitForElementToLoad(driver,notificationChannelTextBox);
            enterInputText(driver,notificationChannelTextBox,channelName);
            waitForElementToLoad(driver,addAndroidChannel);
            waitAndClick(driver,addAndroidChannel);
            waitForElementToLoad(driver,driver.findElement(By.xpath(verifyAddedChannel)));
            if(!driver.findElement(By.xpath(verifyAddedChannel)).isDisplayed()){
                Reporter.log("Something went wrong while adding android channel",true);
            }else{
                Reporter.log("adnroid channel was added sucessfully",true);
            }

        }
        waitForElementToLoad(driver,saveAndroidChannel);
        waitAndClick(driver,saveAndroidChannel);
        waitForElementToLoad(driver,notificationChannelUpdatedText);
        if(notificationChannelUpdatedText.isDisplayed()){
            Reporter.log("Android channel updated sucessfully",true);
        }else{
            Reporter.log("Something went wrong while updating Android channel",true);
        }

        waitForElementToLoad(driver,confirmSaveChannelButton);
        waitAndClick(driver,confirmSaveChannelButton);
        waitForElementToLoad(driver,driver.findElement(By.xpath(verifyAddedChannel)));
        if(!driver.findElement(By.xpath(verifyAddedChannel)).isDisplayed()){
            Reporter.log("channel was not added",true);
        }else{
            Reporter.log("adnroid channel was added sucessfully",true);
        }
    }

    public void turnOffNotificationchannel(){
//        SeleniumUtils.scrollDownLittle(driver);
        System.out.println("check this : ======"+addNotificationCheckBox.isSelected());
        System.out.println("chck : "+driver.findElement(By.xpath("//*[@for='android-o-settings-check']")).isSelected());

        if (addNotificationCheckBox.isSelected()){
            SeleniumUtils.waitForElementToClickable(driver,addNotificationCheckBox,10);
            driver.findElement(By.xpath("//*[@for='android-o-settings-check']")).click();
//            SeleniumUtils.scrollDownLittle(driver);
            SeleniumUtils.waitForElementToClickable(driver, savePushNotificationChannelSetting,10);
            savePushNotificationChannelSetting.click();
            SeleniumUtils.pause(2);
            sweetAlert.sweetAlertConfirmOK();

        }
    }

}
