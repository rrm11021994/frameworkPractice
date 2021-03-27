package com.clevertap.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class SettingsCampaignIntegrationLimitsPushNotificationPage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='pushAmplificationSettingsButton']") private WebElement pushNotificationAmpli;
    @FindBy(xpath = "//*[@class='confirm']") private WebElement confirm;
    @FindBy(xpath = "//*[@id='Left-Nav']") public WebElement leftNav;
    @FindBy(xpath = "//*[@id='menu']/ul/li[6]/ul/li[1]/a") private WebElement settingsOption;
//    @FindBy(xpath = "//*[@class='setting-dashboard']//a") private List<WebElement> dashboardOptions;
    @CacheLookup
    @FindBy(xpath = "//*[@name='gcmSenderId']") private WebElement gcmSenderID;
    @CacheLookup
    @FindBy(xpath = "//*[@name='gcmServerKey']") private WebElement gcmServerKey;
    @CacheLookup
    @FindBy(xpath = "//*[@name='gcmServerKey']/../../following-sibling::div//input") private WebElement androidSaveButton;
    @FindBy(xpath = "//*[contains(@class,'sweet-alert')]") private WebElement sweetAlert;
    @FindBy(xpath = "//*[@id='apnsPushModeTokenBased']/..//span") private WebElement iOSPushModeTokenBased;
    @FindBy(xpath = "//*[@name='apnsTeamID']") private WebElement teamID;
    @FindBy(xpath = "//*[@name='apnsBundleID']") private WebElement bundleID;
    @FindBy(xpath = "(//*[@name='apnsBundleID']/../../following-sibling::div//input)[2]") private WebElement iOSSaveButton;
    @FindBy(xpath = "//*[@id='apnsPushModeCertBased']/..//span") private WebElement iOSPushModeCertBased;
    @FindBy(xpath = "//*[@name='apnsPassphrase']") private WebElement passPhrase;
    @FindBy(xpath = "(//*[@name='apnsPassphrase']/../../following-sibling::div//input)[2]") private WebElement iOSSaveButtonCertBased;




    public void moveToSettingsPage() throws InterruptedException {
        Thread.sleep(1000);
        leftNav.click();
        Thread.sleep(1000);
        settingsOption.click();
        Thread.sleep(2000);
    }

    public void clickOnPushModeCertRadioBUtton() throws InterruptedException {
        iOSPushModeCertBased.click();
        Thread.sleep(500);
    }

    public String enterDetailsForAndroid(String andrioidSenderID,String androidServerKey) throws InterruptedException {
        gcmSenderID.clear();
        Thread.sleep(2000);
        gcmSenderID.sendKeys(andrioidSenderID);
        Thread.sleep(1000);
        gcmServerKey.clear();
        Thread.sleep(1000);
        gcmServerKey.sendKeys(androidServerKey);
        Thread.sleep(500);
        androidSaveButton.click();
        Thread.sleep(2000);
        String response=sweetAlert.getText();
        confirm.click();
        return response;


    }

    public String enterDetailsForIOSPushModeTokenBased(String teamIDValue,String bundleIDValue) throws InterruptedException {
        iOSPushModeTokenBased.click();
        teamID.clear();
        Thread.sleep(1000);
        teamID.sendKeys(teamIDValue);
        Thread.sleep(1000);
        bundleID.clear();
        Thread.sleep(1000);
        bundleID.sendKeys(bundleIDValue);
        Thread.sleep(1000);
        iOSSaveButton.click();
        Thread.sleep(3000);
        String response=sweetAlert.getText();
        return response;
    }

    public String enterDetailsForIOSPushModeCertBased() throws InterruptedException {
        iOSPushModeCertBased.click();
        Thread.sleep(1000);
        passPhrase.sendKeys("");
        Thread.sleep(1000);
        iOSSaveButtonCertBased.click();
        Thread.sleep(3000);
        String response=sweetAlert.getText();
        return response;
    }



    public boolean verifyPushNotificationAmplificationButton() throws InterruptedException {
        String classes=pushNotificationAmpli.getAttribute("class");
        if (!classes.contains("act")){
            pushNotificationAmpli.click();
            Thread.sleep(1000);
            confirm.click();
            Thread.sleep(1000);
        }

        String className=pushNotificationAmpli.getAttribute("class");
        return className.contains("act");

    }


//    public SettingsCampaignIntegrationLimitsPushNotificationPage(WebDriver driverFromPreviousPage){
//        driver=driverFromPreviousPage;
//        PageFactory.initElements(driver,this);
//    }
}
