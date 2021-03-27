package com.clevertap.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SettingsCampaignIntegrationLimitsPageSMSPage {

//    WebDriver driver;

    @FindBy(id = "esp_hide")
    private WebElement provider;
    @FindBy(id = "select_sms_provider_chzn")
    private WebElement sms_provider;
    @FindBy(xpath = "//*[@id='saveSmsSettings']") private WebElement saveSMSInfo;
    @FindBy(xpath = "//*[contains(@class,'sweet-alert')]")
    private WebElement sweetAlert;
    @FindBy(xpath = "//*[contains(@class,'confirm')]")
    private WebElement confirm;
    @FindBy(xpath = "//*[@id='providers_table']//div/a")
    private List<WebElement> nickNames;


    public void openSMSForm() throws InterruptedException {
        provider.click();
        Thread.sleep(2000);
    }

    public String selectSMSProvider(String smsProviderName) throws InterruptedException {
        sms_provider.click();
        Thread.sleep(500);

        String beforeXpath = "//*[@class=\"chzn-drop\"]//li[text()='";
        String afterXpath = "']";
        String finalXpath = beforeXpath + smsProviderName + afterXpath;

        return finalXpath;

    }

    public String fillNickName(String smsProviderName) throws InterruptedException {


        String nickNameBeforeXpath = "//*[@id='";
        String nickNameAfterXpath = "']//input[@name='nickname']";
        String nickNameFinalXpath = nickNameBeforeXpath + smsProviderName.toLowerCase() + nickNameAfterXpath;
        return nickNameFinalXpath;

    }

    public String fillAuthKey(String smsProviderName) throws InterruptedException {

        //*[@id="msg91"]//input[@name='authKey']

        String hostBeforeXpath = "//*[@id='";
        String hostNameAfterXpath = "']//input[@name='authKey']";
        String hostNameFinalXpath = hostBeforeXpath + smsProviderName.toLowerCase() + hostNameAfterXpath;

        return hostNameFinalXpath;

    }

    public String fillRoute(String smsProviderName) throws InterruptedException {

        String userBeforeXpath = "//*[@id='";
        String userNameAfterXpath = "']//input[@name='route']";
        String userNameFinalXpath = userBeforeXpath + smsProviderName.toLowerCase() + userNameAfterXpath;

        return userNameFinalXpath;

    }

    public String fillSenderID(String smsProviderName) throws InterruptedException {
        String passwordBeforeXpath = "//*[@id='";
        String passwordAfterXpath = "']//input[@name='senderId']";
        String passwordFinalXpath = passwordBeforeXpath + smsProviderName.toLowerCase() + passwordAfterXpath;

        return passwordFinalXpath;

    }

    public void saveSMS() throws InterruptedException {
        saveSMSInfo.click();
        Thread.sleep(2000);
    }

    public boolean verifySaveSMSResponse() throws InterruptedException {
        String message = sweetAlert.getText();
        if (message.toLowerCase().contains("success")) {
            confirm.click();
            Thread.sleep(2000);
            return true;
        }
        confirm.click();
        return false;

    }

    public List<WebElement> getAllSMSNickNames() {

        return nickNames;
    }

    public boolean verifyUpdatedSMSResponse() throws InterruptedException {
        String message = sweetAlert.getText();
        System.out.println("*************************** "+message);
        if (message.toLowerCase().contains("updated")) {
            confirm.click();
            Thread.sleep(2000);
            return true;
        }
        confirm.click();
        return false;

    }


}
