package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WebHookIntegration {

    private WebDriver driver;
    private int addWebhookMethodCounter=0;

    @FindBy(id = "addNewAppBtn")
    private WebElement addWebHookBtn;

    @FindBy(id = "webhookName")
    private WebElement webHookName;

    @FindBy(id = "webhookUrl")
    private WebElement webHookUrl;

    @FindBy(xpath = "//input[@value='Save']")
    private WebElement saveButton;

    public void addNewWebHook(String WebhookName, String WebHookURL){
        if (addWebhookMethodCounter==0){
            SeleniumUtils.performClick(driver,addWebHookBtn);
        }

        SeleniumUtils.enterInputText(driver,webHookName,WebhookName);
        SeleniumUtils.enterInputText(driver,webHookUrl,WebHookURL);
        SeleniumUtils.performClick(driver,saveButton);
        SeleniumUtils.pause(2);
        addWebhookMethodCounter++;
    }

    public WebHookIntegration(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);

    }
}
