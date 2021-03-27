package com.clevertap.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Random;

public class SettingsCampaignIntegrationLimitsEmailPage {
    WebDriver driver;

    @FindBy(xpath = "//*[@id='Left-Nav']")
    public WebElement leftNav;
    @FindBy(xpath = "//*[@id='menu']/ul/li[6]/ul/li[1]/a")
    private WebElement settingsOption;
    @FindBy(id = "esp_hide")
    private WebElement provider;
    @FindBy(xpath = "//*[@id='select_email_provider_chzn']")
    private WebElement emailProviderDropdown;

    //    @FindBy(xpath = "//*[@id='kenscio']//input[@name='nickname']") private WebElement nickName;
//    @CacheLookup
//    @FindBy(xpath = "//*[@id='kenscio']//input[@id='host']") private WebElement host;
//    @CacheLookup
//    @FindBy(xpath = "//*[@id='kenscio']//input[@id='username']") private WebElement userName;
//    @CacheLookup
//    @FindBy(xpath = "//*[@id='kenscio']//input[@id='password']") private WebElement password;
    @FindBy(xpath = "//*[contains(@class,'sweet-alert')]")
    private WebElement sweetAlert;
    @FindBy(xpath = "//*[contains(@class,'confirm')]")
    private WebElement confirm;
    @FindBy(xpath = "//*[@id='saveEmailSettings']")
    private WebElement saveEmailSettings;
    @CacheLookup
    @FindBy(xpath = "//*[@id='providers_table']//div/a")
    private List<WebElement> nickNames;


    public void moveToSettingsPage() throws InterruptedException {
        Thread.sleep(1000);
        leftNav.click();
        Thread.sleep(1000);
        settingsOption.click();
        Thread.sleep(2000);
    }

    public String selectEmailProviderName(String emailProviderName) throws InterruptedException {
        Thread.sleep(3000);
        emailProviderDropdown.click();
        String beforeXpath = "//*[@class='chzn-drop']//li[text()='";
        String afterXpath = "']";
        String finalXpath = beforeXpath + emailProviderName + afterXpath;
        return finalXpath;
    }

    public String fillNickName(String providerName) throws InterruptedException {


        String nickNameBeforeXpath = "//*[@id='";
        String nickNameAfterXpath = "']//input[@name='nickname']";
        String nickNameFinalXpath = nickNameBeforeXpath + providerName + nickNameAfterXpath;
        return nickNameFinalXpath;

    }

    public void saveEmail() throws InterruptedException {
        saveEmailSettings.click();
        Thread.sleep(2000);
    }

    public String fillHostName(String providerName) throws InterruptedException {
        Random random = new Random();
        int n = random.nextInt(50);
        n += 1;

        String name = "TestAutomation" + n;

        //*[@id='kenscio']//input[@name='nickname']

        String hostBeforeXpath = "//*[@id='";
        String hostNameAfterXpath = "']//input[@name='host']";
        String hostNameFinalXpath = hostBeforeXpath + providerName + hostNameAfterXpath;

        return hostNameFinalXpath;

    }

    public String fillUserName(String providerName) throws InterruptedException {
        Random random = new Random();
        int n = random.nextInt(50);
        n += 1;

        String name = "TestAutomation" + n;

        //*[@id='kenscio']//input[@name='nickname']

        String userBeforeXpath = "//*[@id='";
        String userNameAfterXpath = "']//input[@name='username']";
        String userNameFinalXpath = userBeforeXpath + providerName + userNameAfterXpath;

        return userNameFinalXpath;

    }

    public String fillpassword(String providerName) throws InterruptedException {
        Random random = new Random();
        int n = random.nextInt(50);
        n += 1;

        String name = "TestAutomation" + n;

        //*[@id='kenscio']//input[@name='nickname']

        String passwordBeforeXpath = "//*[@id='";
        String passwordAfterXpath = "']//input[@name='password']";
        String passwordFinalXpath = passwordBeforeXpath + providerName + passwordAfterXpath;

        return passwordFinalXpath;

    }

    public boolean verifySaveEmailResponse() throws InterruptedException {
        String message = sweetAlert.getText();
        if (message.toLowerCase().contains("successfull")) {
            confirm.click();
            Thread.sleep(2000);
            return true;
        }
        confirm.click();
        return false;

    }

    public boolean verifyUpdatedEmailResponse() throws InterruptedException {
        String message = sweetAlert.getText();
        if (message.toLowerCase().contains("updated")) {
            confirm.click();
            Thread.sleep(2000);
            return true;
        }
        confirm.click();
        return false;

    }

    public List<WebElement> getAllNickNames() {

        return nickNames;
    }

    public void openEmailForm() throws InterruptedException {
        Thread.sleep(1000);
        provider.click();
        Thread.sleep(5000);
    }


}
