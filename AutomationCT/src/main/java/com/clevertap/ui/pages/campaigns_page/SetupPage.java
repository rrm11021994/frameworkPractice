package com.clevertap.ui.pages.campaigns_page;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SetupPage extends LoadYamlFile {

    private static final String WEB_POP_UP = "web pop up";
    private static final String MOBILE_IN_APP = "mobile in-app";
    private static final String CUSTOM_CONTROL_GROUP = "campaign control group";
    private static final String APPLY = "apply";
    private static final String REMOVE = "remove";
    private static final String MOBILE_PUSH = "mobile push";
    private static final String SMS = "sms";
    private static final String APP_INBOX = "app inbox";
    private static final String EMAIL = "email";
    private static final String WEB_PUSH = "web push";
    private static final String WEB_EXIT_INTENT = "web exit intent";
    private static final String WHATSAPP = "whatsapp";
    private static final String AUDIENCES = "audiences";
    private static final String GOOGLE_ADS = "google ads";
    private static final String WEBHOOKS = "webhooks";
    public static final String IOS = "ios";
    public static final String ANDROID = "android";
    public static final String UNCHECKED_ALL_OS_TYPE = "unchecked all os type";
    private WebDriver driver;
    private SweetAlert swarl;
    Logger logger = Logger.getLogger(SetupPage.class);

    @FindBy(id = "androidTtlInputDays")
    private WebElement androidPushTTLText;
    @FindBy(className = "scgToggleLink")
    private WebElement systemCGToggleLink;
    @FindBy(xpath = "//div[contains(@id,'parent_cg_selector')]/a")
    private WebElement customCG;
    @FindBy(xpath = "(//*[@id='select_notification_inbox_ttl']//a)[2]")
    private WebElement inboxTimeUnitDrpDwn;
    @FindBy(xpath = "(//*[@id='select_notification_inbox_ttl']//a)[2]/..//li[text()='Hour(s)']")
    private WebElement inboxTimeUnit;
    @FindBy(xpath = "//*[@id='select_notification_inbox_ttl']//input[@id='ttlInputDays']")
    private WebElement appInboxMsgTimeToLive;

    public SetupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        swarl = new SweetAlert(driver);
    }

    public void setSetupPage() {

        String key1 = campaignMeta.getSetup().getControl_group().keySet().toArray()[0].toString();
        String key2 = campaignMeta.getSetup().getControl_group().keySet().toArray()[1].toString();

        if (key1.equalsIgnoreCase("custom control group")) {
            switch (campaignMeta.getSetup().getControl_group().get(key1).trim().toLowerCase()) {
                case CUSTOM_CONTROL_GROUP:
                    customCG.click();
                    WebElement customCGElement = driver.findElement(By.xpath("//div[contains(@id,'parent_cg_selector')]/a/..//li[text()='" + campaignMeta.getSetup().getControl_group().get(key1) + "']"));
                    customCGElement.click();
                    break;
                default:
                    logger.info("we can put conditions for different custom control group. This is why I am intentionally putting switch case block....");
            }


        }
        if (key2.equalsIgnoreCase("system control group")) {
            switch (campaignMeta.getSetup().getControl_group().get(key2).trim().toLowerCase()) {
                case APPLY:
                    if (systemCGToggleLink.getText().equalsIgnoreCase("apply to this campaign")) {
                        SeleniumUtils.performClick(driver, systemCGToggleLink);
                        swarl.sweetAlertConfirmOK();
                    } else {
                        logger.info(" System control group is already applied:::: ");
                    }
                    break;
                case REMOVE:
                    if (systemCGToggleLink.getText().equalsIgnoreCase("remove for this campaign")) {
                        SeleniumUtils.performClick(driver, systemCGToggleLink);
                        swarl.sweetAlertConfirmOK();
                        swarl.sweetAlertConfirmOK();
                    } else {
                        logger.info(" System control group is already removed:::: ");
                    }
                    break;
                default:
            }

        }

        /*Set inbox day and time */
        try {
            if (campaignMeta.getChannel().trim().equalsIgnoreCase("mobile push")){
                if (campaignMeta.getWhat().getInclude_Msg_In_AppInbox().equalsIgnoreCase("setPushToInApp")){
                    inboxTimeUnitDrpDwn.click();
                    SeleniumUtils.pause(1);
                    inboxTimeUnit.click();
                    SeleniumUtils.pause(1);
                    SeleniumUtils.enterInputText(driver,appInboxMsgTimeToLive,"5");

                }
            }
        }catch (NullPointerException ne){
            //
        }



        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case WEB_PUSH:
            case WEB_POP_UP:
            case WEB_EXIT_INTENT:
            case AUDIENCES:
            case GOOGLE_ADS:
            case WEBHOOKS:
            case APP_INBOX:
                switch (campaignMeta.getWho().getDevice_os().trim().toLowerCase()) {
                    case IOS:
                        logger.info("No need to set the android push message time to live");
                        break;
                    case ANDROID:
                        switch (campaignMeta.getChannel().trim().toLowerCase()) {
                            case MOBILE_PUSH:
                            case SMS:
                            case EMAIL:
                            case WEB_POP_UP:
                            case WEB_EXIT_INTENT:
                            case WHATSAPP:
                            case AUDIENCES:
                            case GOOGLE_ADS:
                            case WEBHOOKS:
                                setAndroidTTL();
                                break;
                            case APP_INBOX:
                            case WEB_PUSH:
                                break;
                            default:

                        }

                        break;
                    case UNCHECKED_ALL_OS_TYPE:
                        setAndroidTTL();
                        break;
                    default:
                        switch (campaignMeta.getChannel().trim().toLowerCase()) {
                            case MOBILE_PUSH:
                            case SMS:
                            case EMAIL:
                            case WHATSAPP:
                            case AUDIENCES:
                            case GOOGLE_ADS:
                                setAndroidTTL();
                                break;
                            case MOBILE_IN_APP:
                            case APP_INBOX:
                            case WEB_PUSH:
                            case WEB_POP_UP:
                            case WEB_EXIT_INTENT:
                                break;
                            default:

                        }

                        break;
                }
                break;
            case SMS:
            case EMAIL:
            case WHATSAPP:
                break;
            default:
        }
    }

    public void changeSetupPage() {

        String key1 = campaignMeta.getSetup().getChange_control_group().keySet().toArray()[0].toString();
        String key2 = campaignMeta.getSetup().getChange_control_group().keySet().toArray()[1].toString();

        if (key1.equalsIgnoreCase("change custom control group")) {
            switch (campaignMeta.getSetup().getChange_control_group().get(key1).trim().toLowerCase()) {
                case CUSTOM_CONTROL_GROUP:
                    customCG.click();
                    WebElement customCGElement = driver.findElement(By.xpath("//div[contains(@id,'parent_cg_selector')]/a/..//li[text()='" + campaignMeta.getSetup().getControl_group().get(key1) + "']"));
                    customCGElement.click();
                    break;
                default:
                    logger.info("we can put conditions for different custom control group. This is why I am intentionally putting switch case block....");
            }


        }
        if (key2.equalsIgnoreCase("change system control group")) {
            switch (campaignMeta.getSetup().getChange_control_group().get(key2).trim().toLowerCase()) {
                case APPLY:
                    if (systemCGToggleLink.getText().equalsIgnoreCase("apply to this campaign")) {
                        SeleniumUtils.performClick(driver, systemCGToggleLink);
                        swarl.sweetAlertConfirmOK();
                    } else {
                        logger.info(" System control group is already applied:::: ");
                    }
                    break;
                case REMOVE:
                    if (systemCGToggleLink.getText().equalsIgnoreCase("remove for this campaign")) {
                        SeleniumUtils.performClick(driver, systemCGToggleLink);
                        swarl.sweetAlertConfirmOK();
                        swarl.sweetAlertConfirmOK();
                    } else {
                        logger.info(" System control group is already removed:::: ");
                    }
                    break;
                default:
            }

        }
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case MOBILE_IN_APP:
            case APP_INBOX:
            case EMAIL:
            case WEB_PUSH:
            case WEB_EXIT_INTENT:
            case WEB_POP_UP:
            case WEBHOOKS:
            case GOOGLE_ADS:
                switch (campaignMeta.getWho().getChange_os_type().trim().toLowerCase()) {
                    case IOS:
                        logger.info("No need to set the android push message time to live");
                        break;
                    case ANDROID:
                        switch (campaignMeta.getChannel().trim().toLowerCase()) {
                            case MOBILE_PUSH:
                            case SMS:
                            case EMAIL:
                            case WEB_PUSH:
                            case "web pop-up":
                            case WEB_EXIT_INTENT:
                            case AUDIENCES:
                            case GOOGLE_ADS:
                            case WEBHOOKS:
                                setAndroidTTL();
                                break;
                            case APP_INBOX:
                                break;
                            default:

                        }

                        break;
                    case UNCHECKED_ALL_OS_TYPE:
                        setAndroidTTL();
                        break;
                    default:
                }
                break;
            case SMS:
            case WHATSAPP:
                break;
            default:
        }

    }

    public void verifyAppInboxTargetWithPush(){
        Assert.assertTrue(driver.findElement(By.xpath("(//*[@id='select_notification_inbox_ttl']//a)[2]//span")).getText().equalsIgnoreCase("Hour(s)"));
        Assert.assertTrue(appInboxMsgTimeToLive.getAttribute("value").equalsIgnoreCase("5"));

    }

    public void setAndroidTTL() {
        if (!campaignMeta.getChannel().equalsIgnoreCase(MOBILE_IN_APP))
            androidPushTTLText.clear();
        androidPushTTLText.sendKeys(String.valueOf(campaignMeta.getSetup().getPush_android_ttl()));
    }
}
