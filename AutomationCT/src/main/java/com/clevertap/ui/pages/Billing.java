package com.clevertap.ui.pages;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Billing {

    WebDriver driver;
    final static Logger logger=Logger.getLogger("Billing");


    @FindBy(xpath = "//*[@id='Left-Nav']")
    private static WebElement leftNav;
    @FindBy(xpath = "//*[@id='menu']/ul/li[6]/ul/li[3]/a")
    private WebElement billing;
    @FindBy(xpath = "//*[contains(@class,'btn-group')]/button")
    private List<WebElement> billingButtons;
    @FindBy(xpath = "//*[contains(@class, 'plan-name-container')]") private WebElement billingElement;
    @FindBy(xpath = "//*[contains(@class,'billing-toggle')]/button") private List<WebElement> billingToggle;


    public void launchBillingPage() {

        try {
            PropertyConfigurator.configure("log4j.properties");
            logger.info("Clicking on left bottom nav icon");
            leftNav.click();
            logger.info("Clicking on item 'billing'");
            billing.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<String> verifyBillingButtons() {
        logger.info("collecting all billing buttons info from page");
        List<String> buttonList = new ArrayList<>();
        for (WebElement button : billingButtons) {
            buttonList.add(button.getText());
        }

        return buttonList;
    }

    public List<WebElement> getAllBillingTabs(){

        return billingToggle;
    }

    public WebElement getElement(){
        return billingElement;
    }

    public Billing(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }
}
