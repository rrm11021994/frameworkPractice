package com.clevertap.ui.pages.campaigns_page;

import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TimeSetter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WhatsAppCampaigns {

    private WebDriver driver;

    @FindBy(xpath = "//input[@id='js-date-time']/..")
    private WebElement messageEndDateTime;
    @FindBy(xpath = "//button[contains(@class,'js-createCampaign')]")
    private WebElement createCampaign;
    @FindBy(xpath = "//div[contains(@class,'pane-container_iframe')]/iframe")
    private WebElement campaignsIFrame;
    @FindBy(xpath = "//li[@data-mode='whatsApp']")
    private WebElement whatsAppChannel;
    @FindBy(xpath = "//div[@id='past-user-block']//li[1]")
    private WebElement _PBSOneTimeCampaign;
    @FindBy(xpath = "//ul[@id='channel_cards_live']/li[1]")
    private WebElement singleActionTriggerCampaignButton;
    @FindBy(xpath = "//ul[@id='channel_cards_live']/li[2]")
    private WebElement inactionWithinTimeCampaignButton;
    @FindBy(xpath = "//ul[@id='channel_cards_live']/li[3]")
    private WebElement onDateTimeCampaignButton;
    @FindBy(xpath = "//input[@id='js-start-later']/..")
    private WebElement laterStartDateRadio;
    @FindBy(xpath = "//label[@class='advance-block']")
    private WebElement whenAdvancedCheckBox;
    @FindBy(xpath = "//label[@for='userTzSelectBox']")
    private WebElement deliverInUserTZ;
    @FindBy(xpath = "//label[@for='dndSelectBox']")
    private WebElement campaignDND;
    @FindBy(xpath = "//label[@for='js-fixed-time-campaign_0']")
    private WebElement fixedDeliveryTimeRadio;
    @FindBy(xpath = "//label[@for='tcSelectBox']")
    private WebElement campaignCutOffTimeCheckBox;
    /*When page elements for Trigger campaigns*/
    @FindBy(xpath = "//input[@id='js-date-time']/..")
    private WebElement singleActionWhenPageEndDate;
    @FindBy(xpath = "//label[@for='send-delay']")
    private WebElement singleActionTriggerWhenPageDelay;
    @FindBy(xpath = "//input[@id='delayByTimeValue']")
    private WebElement singleActionWhenPageDelayMinutes;

    String campaignDNDStartTimeStr = "//div[@id='camp_dnd_start_time']",
            CampaignDNDEndTimeStr = "//div[@id='camp_dnd_end_time']",
            campaignCutOffStr = "//div[@id='fixedTimeWidget_0']",
            campaignCutOffTimeBoxStr = "//div[@id='tcTime']";

    public WhatsAppCampaigns(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setWhenPagePropertiesPBS(boolean scheduleItForLater, boolean withAdvancedSettings, boolean timeZone, boolean campaignDNDSetting, boolean campaignCutOff,
                                         String campaignDNDStartTime, String campaignDNDEndTime, String campaignCutOffTime) {


        TimeSetter d = new TimeSetter(driver);

        if (scheduleItForLater) {
            laterStartDateRadio.click();
            fixedDeliveryTimeRadio.click();
            d.setTime("05_30_PM", campaignCutOffStr);
        }


        if (withAdvancedSettings) {
            whenAdvancedCheckBox.click();

            if (timeZone && deliverInUserTZ.isDisplayed()) {
                deliverInUserTZ.click();
            }
            if (campaignDNDSetting && campaignDND.isDisplayed()) {
                campaignDND.click();
                SeleniumUtils.waitForElementToLoad(driver, driver.findElement(By.xpath(campaignDNDStartTimeStr)));
                d.setTime(campaignDNDStartTime, campaignDNDStartTimeStr);
                d.setTime(campaignDNDEndTime, CampaignDNDEndTimeStr);
            }
            if (campaignCutOff && campaignCutOffTimeCheckBox.isDisplayed()) {
                campaignCutOffTimeCheckBox.click();
                d.setTime(campaignCutOffTime, campaignCutOffTimeBoxStr);
            }
        }
    }

    public void setWhenPageSingleActionTrigger(boolean setLater, int delay, boolean dndSet, String dndStartTime, String dndEndTime) {

        TimeSetter d = new TimeSetter(driver);
        if (setLater) {
            laterStartDateRadio.click();
        }
        if (delay > 0) {
            singleActionWhenPageDelayMinutes.sendKeys(String.valueOf(delay));
        }

        if (dndSet) {
            whenAdvancedCheckBox.click();
            campaignDND.click();
            SeleniumUtils.waitForElementToLoad(driver, driver.findElement(By.xpath(campaignDNDStartTimeStr)));
            d.setTime(dndStartTime, campaignDNDStartTimeStr);
            d.setTime(dndEndTime, CampaignDNDEndTimeStr);
        }

    }

    public void setWhenPageInactionWithinTime(boolean dndSet, String dndStartTime, String dndEndTime) {
        TimeSetter d = new TimeSetter(driver);
        if (dndSet) {
            whenAdvancedCheckBox.click();
            campaignDND.click();
            SeleniumUtils.waitForElementToLoad(driver, driver.findElement(By.xpath(campaignDNDStartTimeStr)));
            d.setTime(dndStartTime, campaignDNDStartTimeStr);
            d.setTime(dndEndTime, CampaignDNDEndTimeStr);
        }
    }


    public WebElement getCreateCampaign() {
        return createCampaign;
    }

    public WebElement getWhatsAppChannel() {
        return whatsAppChannel;
    }

    public WebElement getPBSOneTimeCampaign() {
        return _PBSOneTimeCampaign;
    }

    public WebElement getCampaignsIFrame() {
        return campaignsIFrame;
    }

    public WebElement getSingleActionTriggerCampaignButton() {
        return singleActionTriggerCampaignButton;
    }

    public WebElement getInactionWithinTimeCampaignButton() {
        return inactionWithinTimeCampaignButton;
    }

    public WebElement getOnDateTimeCampaignButton() {
        return onDateTimeCampaignButton;
    }
}
