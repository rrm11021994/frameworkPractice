package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.ui.pages.campaigns_page.WhatsAppCampaigns;
import com.clevertap.utils.Data;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.List;

public class WhatsAppIntegration {

    private static final List<String> templateValues = Arrays.asList("Hey Dude, ", "ABC", "30");
    private WebDriver driver;
    private static final String campaignDNDEndTime = "11_30_PM";
    private static final String campaignDNDStartTime = "10_30_PM";
    private String templateSelect = "//div/following-sibling::div[contains(@class,'whatsapp-template-select-buttons')]/button";

    @FindBy(xpath = "//li[@data-cg='templates']")
    private WebElement templatesTab;
    @FindBy(xpath = "//div[@class='js-create-custom-cg-btn']")
    private WebElement addNewTemplateButton;
    @FindBy(xpath = "//input[@class='js-template-name']")
    private WebElement templateNameInput;
    @FindBy(xpath = "//textarea[@class='js-template-text']")
    private WebElement templateTextInput;
    @FindBy(xpath = "//div[@id='addNewTemplate']//a[contains(text(), 'Save')]")
    private WebElement saveTemplateButton;
    @FindBy(xpath = "//div[@id='addNewTemplate']//a[contains(text(), 'Cancel')]")
    private WebElement cancelTemplateCreation;
    @FindBy(xpath = "//button[@class='cancel']")
    private WebElement popUpAck;
    @FindBy(xpath = "//div[@class='CT-table__left']")
    private WebElement templatesTable;
    @FindBy(xpath = "//button[@id='btn_top_nav_continue']")
    private WebElement buttonTopContinue;
    @FindBy(xpath = "//div[text()='verify']")
    private WebElement verifyTemplate;
    @FindBy(xpath = "//div[contains(@class,'showSweetAlert')]//button[@class='confirm']")
    private WebElement saveButton;
    @FindBy(xpath = "//select[@id='propSelect']/../div/a")
    private WebElement buttonToSelectDateForOnDateTime;
    @FindBy(xpath = "//select[@id='propSelect']/../div//li[contains(text(),'Date')]")
    private WebElement dateForOnDateCampaign;
    @FindBy(xpath = "//input[@id='jname']")
    private WebElement campaignNameInputBox;
    @FindBy(xpath = "//div[contains(@class,'emoji-wysiwyg-editor')]")
    private List<WebElement> textInput;


    public WhatsAppIntegration(WebDriver previousBrowserdriver) {
        driver = previousBrowserdriver;
        PageFactory.initElements(previousBrowserdriver, this);
    }


    public boolean createTemplate(String templateName, String templateText) {

        try {
            templatesTab.click();
            addNewTemplateButton.click();
            templateNameInput.sendKeys(templateName);
            templateTextInput.sendKeys(templateText);
            saveTemplateButton.click();

            SeleniumUtils.waitForElementToLoad(driver, saveButton);
            SeleniumUtils.performClick(driver, saveButton);
            return true;

        } catch (Exception e) {
            return false;
        }

    }


    public boolean validatePBSCampaigns(String segmentName) {

        WhatsAppCampaigns campaigns = new WhatsAppCampaigns(driver);

        SeleniumUtils.waitForElementToLoad(driver, campaigns.getCreateCampaign());
        SeleniumUtils.performClick(driver, campaigns.getCreateCampaign());

        driver.switchTo().frame(campaigns.getCampaignsIFrame());

        SeleniumUtils.performClick(driver, campaigns.getWhatsAppChannel());

        SeleniumUtils.performClick(driver, campaigns.getPBSOneTimeCampaign());

        campaigns.setWhenPagePropertiesPBS(false, true, false, true, true,
                campaignDNDStartTime, campaignDNDEndTime, "10_30_PM");

        SeleniumUtils.performClick(driver, buttonTopContinue);

        String segmentXPath = "//a[contains(text(),'" + segmentName + "')]";

        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(segmentXPath)));

        setWhoWhatPageProps("PBS_WA");


        return true;

    }

    public boolean validateSingleActionTriggerCampaigns(String segmentName) {

        WhatsAppCampaigns campaigns = new WhatsAppCampaigns(driver);

        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.performClick(driver,campaigns.getCreateCampaign());

        driver.switchTo().frame(campaigns.getCampaignsIFrame());

        SeleniumUtils.performClick(driver,campaigns.getWhatsAppChannel());

        SeleniumUtils.performClick(driver,campaigns.getSingleActionTriggerCampaignButton());

        campaigns.setWhenPageSingleActionTrigger(false, 5, false, campaignDNDStartTime, campaignDNDEndTime);

        SeleniumUtils.performClick(driver, buttonTopContinue);

        String segmentXPath = "//a[contains(text(),'" + segmentName + "')]";

        SeleniumUtils.performClick(driver,driver.findElement(By.xpath(segmentXPath)));

        setWhoWhatPageProps("SingleAction_WA");

        return true;
    }

    public boolean validateInactionWithinTimeCampaigns(String segmentName) {

        WhatsAppCampaigns campaigns = new WhatsAppCampaigns(driver);

        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.performClick(driver,campaigns.getCreateCampaign());

        driver.switchTo().frame(campaigns.getCampaignsIFrame());

        SeleniumUtils.performClick(driver,campaigns.getWhatsAppChannel());

        SeleniumUtils.performClick(driver,campaigns.getInactionWithinTimeCampaignButton());

        campaigns.setWhenPageInactionWithinTime(true, campaignDNDStartTime, campaignDNDEndTime);

        SeleniumUtils.performClick(driver, buttonTopContinue);

        System.out.println("******* "+segmentName);
        String segmentXPath = "//a[contains(text(),'" + segmentName + "')]";
        SeleniumUtils.performClick(driver,driver.findElement(By.xpath(segmentXPath)));

        setWhoWhatPageProps("InAction_WA");

        return true;
    }

    public boolean validateOnDateTimeCampaigns(String segmentName) {

        WhatsAppCampaigns campaigns = new WhatsAppCampaigns(driver);

        SeleniumUtils.waitForPageLoaded(driver);

        SeleniumUtils.performClick(driver,campaigns.getCreateCampaign());

        driver.switchTo().frame(campaigns.getCampaignsIFrame());

        SeleniumUtils.performClick(driver,campaigns.getWhatsAppChannel());

        SeleniumUtils.performClick(driver,campaigns.getOnDateTimeCampaignButton());

        campaigns.setWhenPageInactionWithinTime(false, campaignDNDStartTime, campaignDNDEndTime);

        SeleniumUtils.performClick(driver, buttonTopContinue);

        String segmentXPath = "//a[contains(text(),'" + segmentName + "')]";
        SeleniumUtils.performClick(driver,driver.findElement(By.xpath(segmentXPath)));

        SeleniumUtils.waitForPageLoaded(driver);

        buttonToSelectDateForOnDateTime.click();
        dateForOnDateCampaign.click();

        setWhoWhatPageProps("OnDateTime_WA");

        return true;
    }

    private void setWhoWhatPageProps(String campaignType){

        SeleniumUtils.performClick(driver, buttonTopContinue);

        List<WebElement> templates = driver.findElements(By.xpath(templateSelect));
        if (templates.size() > 0) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", templates.get(0));
        }


        SeleniumUtils.scrollDown(driver);

        setTemplateParameters(templateValues, textInput);

        SeleniumUtils.performClick(driver, buttonTopContinue);

        SeleniumUtils.performClick(driver, buttonTopContinue);

        SeleniumUtils.performClick(driver, buttonTopContinue);

        campaignNameInputBox.sendKeys(Data.randomAlphaNumeric(campaignType, 3));

        SeleniumUtils.waitForPageLoaded(driver);

        SeleniumUtils.performClick(driver, saveButton);
    }

    private void setTemplateParameters(List<String> values, List<WebElement> messagePersonalization) {
        int i = 0;
        for (WebElement element : messagePersonalization) {
            element.sendKeys(values.get(i++));
        }
    }

}
