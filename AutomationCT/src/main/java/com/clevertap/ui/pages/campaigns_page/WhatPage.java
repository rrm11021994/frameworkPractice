package com.clevertap.ui.pages.campaigns_page;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.FileUtility;
import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.clevertap.utils.SeleniumUtils.enterInputText;
import static com.clevertap.utils.SeleniumUtils.waitForElementToLoad;

public class WhatPage extends LoadYamlFile {

    public static final String RICHMEDIA = "richmedia";
    private static final String AUDIENCES = "audiences";
    private static final String WEB_POP_UP = "web pop up";
    private static final String ID_KEYVALUE_PAIR_CONTAINER_DIV_CLASS_FLEX_ROW = "(//*[@id='keyvaluePairContainer']//div[@class='flex-row'])[";
    private static final String CLASS_INAPP_TEMPLATE_NAME_AND_TEXT = "//*[@class='inapp-template-name' and text()='";
    private static final String BUTTON_TEXT = "']/..//button[text()='";
    private static final String ANDROID = "android";
    private static final String IOS = "ios";
    public static final String UNCHECKED_ALL_OS_TYPE = "unchecked all os type";
    private static final String CAROUSEL_MESSAGE = "carousel message";
    private static final String CUSTOM_KEY_VALUE = "custom key value";
    private static final String SIMPLE_BANNER = "simple banner";
    private static final String CAROUSEL_BANNER = "carousel banner";
    private static final String BANNER_WITH_ICON = "banner with icon";
    private static final String SIMPLE_MESSAGE = "simple message";
    private static final String CUSTOM_HTML = "custom html";
    private static final String ALERT = "alert";
    private static final String MESSAGE_WITH_ICON = "message with icon";
    private static final String FOOTER = "footer";
    private static final String HEADER = "header";
    private static final String HALF_INTERSTITIAL = "half interstitial";
    private static final String INTERSTITIAL = "interstitial";
    private static final String COVER = "cover";
    private static final String SELECTED_TEMPLATE = "Selected template - ";
    private static final String CLASS_WHATSAPP_TEMPLATE_DIV_TEXT = "//*[@class='whatsapp-template']/div[text()='";
    private static final String MOBILE_PUSH = "mobile push";
    private static final String MOBILE_IN_APP = "mobile in-app";
    private static final String APP_INBOX = "app inbox";
    private static final String SMS = "sms";
    private static final String EMAIL = "email";
    private static final String WEBHOOKS = "webhooks";
    private static final String WHATSAPP = "whatsapp";
    private static final String WEB_PUSH = "web push";

    private static final String WEB_EXIT_INTENT = "web exit intent";
    public static final String SINGLE_MEDIA = "single media";
    public static final String IMAGE_CAROUSEL = "image carousel";
    public static final String IOS_SOUND_FILE = "ios-sound-file";
    public static final String DEFAULT_OS_SOUND = "default os sound";
    public static final String CUSTOM_SOUND = "custom sound";
    public static final String IOS_BADGE_COUNT = "ios-badge-count";
    public static final String IOS_CATEGORY = "ios-category";
    public static final String IOS_DEEP_LINK = "ios-deep-link";
    public static final String ANDROID_SUBTITLE = "android-subtitle";
    public static final String ANDROID_IMAGE_URL = "android-image-url";
    public static final String ANDROID_LARGE_ICON_URL = "android-large-icon-url";
    public static final String ANDROID_SMALL_ICON_COLOR = "android-small-icon-color";
    public static final String ANDROID_DEEP_LINK = "android-deep-link";
    public static final String ANDROID_ACTIONS = "android-actions";
    public static final String ANDROID_SOUND = "android-sound";
    public static final String ALL_CHECKED = "all checked";
    public static final String SINGLEMESSAGE = "singlemessage";
    public static final String A_B = "a/b";
    public static final String MULTIVARIATE = "multivariate";
    Logger logger = Logger.getLogger(LoadYamlFile.class);
    private static final String SINGLE_MESSAGE = "single message";
    private static final String A_B_TEST = "a/b test";
    private static final String MESSAGE_ON_USER_PROPERTY = "message on user property";
    private static final String LEGACY = "legacy";
    private WebDriver driver;
    private SweetAlert sweetAlert;

    private int titleFieldCounter = 1;
    private int messageFieldCounter = 1;
    private int androidNotificationChannelCounter = 1;
    private static boolean isVariantAFilled = false;
    private static boolean isVariantBFilled = false;
    private static Set<String> titleUserProp = new HashSet<>();
    private static Set<String> titlePlaneText = new HashSet<>();
    private static Set<String> messageUserProp = new HashSet<>();
    private static Set<String> messagePlaneText = new HashSet<String>();
    private static Set<String> iosActionProp = new HashSet<String>();
    private static Set<String> androidActionProp = new HashSet<String>();
    private static int personalisationCount = 1;
    private static int propCounter = 1;
    private String CAROUSEL_SLIDE_LOC = "//div[@class='card-body']//a[contains(text(),'Slide %s')]";
    private String CAROUSEL_TITLE_TEXTBOX = "(//div[@class='title-card']//div[@data-type='input'])[%s]";
    private String CAROUSEL_MESSAGE_TEXTBOX = "(//div[@class='message-card']//div[@data-type='input'])[%s]";
    private String CAROUSEL_IOS_TEXTBOX = "(//div[text()='iOS URL']//..//div[@data-type='input'])[%s]";
    private String CAROUSEL_ANDROID_TEXTBOX = "(//div[text()='Android URL']//..//div[@data-type='input'])[%s]";
    private String CAROUSEL_UPLOADIMG_BUTTON = "(//div[@role='radiogroup']//span[text()='Upload image'])[%s]";
    private String CAROUSEL_PERSONALIZE_MEDIA_BUTTON = "(//div[@role='radiogroup']//span[text()='Personalized media'])[%s]";
    private String CAROUSEL_IMAGELOADER_BUTTON = "(//div[@class='media-container']//*[contains(@fill-rule,'evenodd')])[%s]";
    private String CAROUSEL_SAVEMEDIA_BUTTON = "(//div[@class='media-container']//div[@class='save-button'])[%s]";
    private String CAROUSEL_PERSONALIZE_TEXTBOX = "(//div[text()='Add personalized image']//..//div[@data-type='input'])[%s]";

    //Lists containing the messages to be filled in title and message on what page
    private List<String> titleDetailsForAll = new ArrayList<String>();
    private List<String> messageDetailsForAll = new ArrayList<String>();

    //SMS channel elements
    @FindBy(id = "tgt_message_text")
    private WebElement smsTextArea;

    //Mobile push what page elements
    @FindBy(xpath = "//*[@data-campaign-type='singleMessage']")
    private WebElement mobilePushSingleMessage;
//    @FindBy(xpath = "//div[@id='titleDiv']//div[@data-type='input']")
//    private WebElement mobilePushTitle;
    @FindBy(xpath = "//div[@id='emoji_wzrk_tgt_message_title']")
    private WebElement mobilePushTitle;
    @FindBy(xpath = "//*[contains(@id,'emoji_wzrk_tgt_message_text')]")
    private WebElement mobilePushMessage;
    @FindBy(xpath = "//*[@id='selectaochannel_chzn']//li")
    private List<WebElement> androidOChannelsList;
    @FindBy(xpath = "//*[@id='selectaochannel_chzn']/a")
    private WebElement channelDropDown;

    //email channel what elements
    @FindBy(xpath = "//span[@data-campaign-type='singleMessage']")
    private WebElement singleMessageEmail;
    @FindBy(xpath = "//div[@id='templates_gallery']/div")
    private List<WebElement> emailTemplatesList;
    @FindBy(id = "email_sender_name")
    private WebElement emailFromName;
    @FindBy(id = "email_subject")
    private WebElement emailSubject;
    @FindBy(className = "variant-dropdown")
    private WebElement variantDropdown;
    @FindBy(xpath = "//*[@class='variant-dropdown']//a[text()='Clear']")
    private WebElement variantDropdownClear;
    @FindBy(className = "template-title")
    private WebElement templateTitle;
    @FindBy(xpath = "//*[@for='advance-setting-checkbox']")
    private WebElement whatPageAdvanceSetting;
    @FindBy(id = "addKeyValuePair")
    private WebElement addKeyValuePair;
    @FindBy(id = "addWebHookKeyValuePair")
    private WebElement webhookAddKeyValuePair;

    @FindBy(xpath = "//*[@id='keyvaluePairContainer']//span")
    private List<WebElement> customKeyValuePairCheckBoxes;
    @FindBy(xpath = "//*[@id='keyvaluePairContainer']//div[contains(@class,'is-profile-key')]/input")
    private WebElement profileKey;
    @FindBy(xpath = "//*[@id='keyvaluePairContainer']//div[contains(@class,'is-profile-value')]/div")
    private WebElement profileValue;

    @FindBy(id = "customAndroid")
    private WebElement whatPageAdvanceAndroidSection;
    @FindBy(id = "customIOS")
    private WebElement whatPageAdvanceIosSection;
    @FindBy(xpath = "//*[@id='customIOS']//div[@class='richToggleItem']/a")
    private List<WebElement> iOsRishToggleItems;
    @FindBy(xpath = "//*[@for='singleMedia']")
    private WebElement iOsSingleMedia;
    @FindBy(id = "ct_mediaUrl")
    private WebElement singleMediaUrlBox;
    @FindBy(xpath = "//*[@for='carousalImg']")
    private WebElement iOsImageCarousel;
    @FindBy(xpath = "//*[@for='iosDefaultSound']")
    private WebElement iOsDefaultSound;
    @FindBy(xpath = "//*[@for='iosCustomSound']")
    private WebElement iOsCustomSound;
    @FindBy(xpath = "//*[@data-key='wzrk_badge']")
    private WebElement badgeCountInput;
    @FindBy(xpath = "//*[@data-key='wzrk_category']")
    private WebElement categoryInput;
    @FindBy(id = "wzrk_dl")
    private WebElement deepLink;
    @FindBy(xpath = "//div[@id='customAndroid']//div[@class='richToggleContainer']/div/a")
    private List<WebElement> androidRishToggles;
    @FindBy(id = "wzrk_st")
    private WebElement androidSubTitle;
    @FindBy(id = "wzrk_bp")
    private WebElement imageUrl;
    @FindBy(id = "emoji_wzrk_wzrk_nms")
    private WebElement summaryText;
    @FindBy(id = "ico")
    private WebElement largeIcon;
    @FindBy(className = "color-palette")
    private WebElement backgroudColor;
    @FindBy(id = "android_wzrk_dl")
    private WebElement androidDeepLink;
    @FindBy(id = "actionLabel")
    private WebElement androidActionLabel;
    @FindBy(id = "actionID")
    private WebElement androidActionID;
    @FindBy(xpath = "//*[@for='andDefaultSound']")
    private WebElement androidDefaultSound;
    @FindBy(id = "btn_top_nav_continue")
    private WebElement continueButton;
    @FindBy(id = "smsTargetTestButton")
    private WebElement sendTestSms;
    @FindBy(id = "wzrkSmsTestDialog")
    private WebElement smsTestDialog;
    @FindBy(xpath = "//*[@id='wzrkSmsTestDialog']//button[@class='close']")
    private WebElement smsTestDialogCloseButton;
    @FindBy(xpath = "//button[text()='Change']")
    private WebElement templateChangeBtn;
    @FindBy(xpath = "//div[contains(@class,'emoji-wysiwyg-editor')]")
    private List<WebElement> messageInput;
    @FindBy(xpath = "//*[contains(@data-variant,'Variant')]")
    private List<WebElement> variants;
    @FindBy(className = "TAL")
    private WebElement selectMessageOnUserProp;
    @FindBy(xpath = "//*[@class='TAL']//input")
    private WebElement searchProp;
    @FindBy(xpath = "//*[@id='selectedPivotValues']//span[@class='variantTabsDropDown_btn']")
    private WebElement messageOnUserPropTabPlusBtn;
    @FindBy(xpath = "(//*[@id='selectedPivotValues']//li/div//li)[1]/../../following-sibling::div//a")
    private WebElement applySelectedProp;
    @FindBy(xpath = "(//*[@id='selectedPivotValues']//li/div//li)[1]")
    private WebElement selectFirstProp;
    @FindBy(xpath = "//div[contains(@class,' custom-input ')]")
    private WebElement deepLinkTextbox;
    @FindBy(className = "custom-input")
    private WebElement imageLink;
    @FindBy(id = "wzrk_default")
    private WebElement defaultABTab;
    @FindBy(xpath = "//*[@id='selectedPivotValues']/li[contains(@class,'variantTabs__item')]")
    private List<WebElement> userPropsPivotTabs;
    @FindBy(xpath = "//*[@class='variantTabs']/li")
    private List<WebElement> abVariants;
    @FindBy(xpath = "//*[@for='send-to-inbox-checkbox']")
    private WebElement sendToInboxCheckBox;
    @FindBy(xpath = "//div[@aria-controls='messageDiv']")
    private WebElement expandMessageBox;
    @FindBy(xpath = "//div[@aria-controls='buttonsDiv']")
    private WebElement expandAction;
    @FindBy(xpath = "//div[@aria-controls='backgroundDiv']")
    private WebElement expandBackground;
    @FindBy(xpath = "//div[@aria-controls='imageDiv']")
    private WebElement expandMedia;
    @FindBy(xpath = "//div[@aria-controls='customKVHeader']")
    private WebElement expandCustomKV;
    @FindBy(xpath = "//div[text()='iOS URL']/..//div[@data-t='tooltip']")
    private WebElement actionIosUrl;
    @FindBy(xpath = "//div[text()='Android URL']/..//div[@data-t='tooltip']")
    private WebElement actionAndroidUrl;
    @FindBy(xpath = "(//input[@class='vc-input__input'])[1]")
    private WebElement backgroundColorInput;
    @FindBy(xpath = "//label[contains(@for,'inbox')]")
    private WebElement appInboxCheckbox;
    @FindBy(xpath = "//div[@id='messageDiv']//div[@data-type='input']")
    private WebElement whatPageMessageTextBox;
    @FindBy(xpath = "//div[text()='iOS URL']/..//div[@data-type='input']")
    private WebElement whatPageActionIosTextBox;
    @FindBy(xpath = "//div[text()='Android URL']/..//div[@data-type='input']")
    private WebElement whatPageActionAndroidTextBox;
    @FindBy(xpath = "//label[contains(text(),'Add filter tag')]")
    private WebElement filterTagCheckbox;
    @FindBy(xpath = "//div[@class='tags-input-content']//input")
    private WebElement filterTagInputBox;
    @FindBy(xpath = "//div[@id='backgroundandIconDiv']//span[text()='Upload image']")
    private WebElement uploadImageForBackgroundMedia;
    @FindBy(xpath = "//div[@id='backgroundandIconDiv']//span[text()='Personalized media']")
    private WebElement personalizMediaForBackground;
    @FindBy(xpath = "//div[@id='backgroundandIconDiv']//div[@class='loader']//*[contains(@fill-rule,'evenodd')]")
    private WebElement imageLoaderForBackgroudMedia;
    @FindBy(xpath = "//div[@id='backgroundandIconDiv']//div[@class='save-button']")
    private WebElement saveMediaBtnForBackgroudMedia;
    @FindBy(xpath = "//div[@id='backgroundandIconDiv']//div[text()='Add personalized image']/..//div[@data-type='input']")
    private WebElement personalizeMediaInputBackgroudMedia;
    @FindBy(xpath = "//button[contains(text(),'Add Pair')]")
    private WebElement addCustomKV;
    @FindBy(xpath = "//div[@aria-controls='backgroundandIconDiv']")
    private WebElement expandBackgroundAndIcon;
    @FindBy(xpath = "(//div[@class='icon-selector']//*[name()='g' and @fill-rule='evenodd'])[1]")
    private WebElement defaultIcon;
    @FindBy(xpath = "//div[@class='cropper-container']")
    private WebElement iconSavedLoc;
    @FindBy(xpath = "//div[@id='imageDiv']//span[text()='Upload image']")
    private WebElement uploadImageForMedia;
    @FindBy(xpath = "//div[@id='imageDiv']//div[@class='loader']//*[contains(@fill-rule,'evenodd')]")
    private WebElement imageLoaderForMedia;
    @FindBy(xpath = "//div[@id='imageDiv']//div[@class='save-button']")
    private WebElement saveMediaBtnForMedia;
    @FindBy(xpath = "//div[@id='imageDiv']//span[text()='Personalized media']")
    private WebElement personalizeForMedia;
    @FindBy(xpath = "//div[@id='imageDiv']//div[text()='Add personalized image']/..//div[@data-type='input']")
    private WebElement personalizeMediaInputMedia;
    @FindBy(xpath = "//div[@class='card-body']//button[@class='btn btn-white tab-btn']")
    private WebElement addSlideButton;
    @FindBy(xpath = "//input[@data-key='wzrk_cid']")
    private WebElement notificationChannelInput;


    public WhatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        sweetAlert = new SweetAlert(driver);
    }

    public void setWhatPage() {
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case CampaignsHomePage.MOBILE_PUSH:
                switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
                    case SINGLEMESSAGE:
                        setMobilePushWhatPage();
                        setUpWhatPageAdvanceSettings();
                        SeleniumUtils.pause(3);
                        setPushToInboxTarget();

                        break;
                    case A_B:
                        /*set value to variant 1*/
                        setMobilePushWhatPage();
                        SeleniumUtils.pause(1);

                        /*Copy value from variant 1 and validate the same value should be populated to variant 2 as well*/

                        setPushToInboxTarget();
                        break;
                    case MULTIVARIATE:
                        selectMessageOnUserProp.click();
                        SeleniumUtils.enterInputText(driver, searchProp, Keys.chord("QA-Automation-DND" + Keys.RETURN));
                        SeleniumUtils.pause(2);
                        setMobilePushWhatPage();

                        setPushToInboxTarget();
                        break;
                    default:
                }

                break;
            case CampaignsHomePage.MOBILE_INAPP:
                configureMobileInAppWhatPage();
                break;
            case CampaignsHomePage.SMS:
                setSMSWhatPage();
                break;
            case CampaignsHomePage.APP_INBOX:
                configureMobileInBoxWhatPage();
                break;
            case CampaignsHomePage.EMAIL:
                setEmailPage();
                break;
            case CampaignsHomePage.WEB_PUSH:
                setWebpushWhatPage();
                break;
            case CampaignsHomePage.WEB_POPUP:
                break;
            case CampaignsHomePage.WEB_EXIT_INTENT:
                break;
            case CampaignsHomePage.WHATSAPP:
                setWhatsAppPage();
                break;
            case CampaignsHomePage.FB_AUDIENCES:
                break;
            case CampaignsHomePage.GOOGLE_ADS:
                break;
            case CampaignsHomePage.WEBHOOKS:
                break;
            case CampaignsHomePage.NATIVEDISPLAY:
                switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {

//                    case SINGLEMESSAGE:
                    case SINGLE_MESSAGE:
                    configureMobileInBoxWhatPage();
                    configureAdviewWhatMessagePage();
                    break;

                    case A_B:

                        break;

                    case MESSAGE_ON_USER_PROPERTY:

                        break;
                }
                break;

            default:
        }
    }

    private void setPushToInboxTarget() {
        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhat().getInclude_Msg_In_AppInbox()) && campaignMeta.getWhat().getInclude_Msg_In_AppInbox().equalsIgnoreCase("setPushToInApp")){
            SeleniumUtils.scrollDown(driver);
            SeleniumUtils.performClick(driver, sendToInboxCheckBox);
        }
    }

    public void changeWhatPage() {
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case CampaignsHomePage.MOBILE_PUSH:
                changeMobilePushWhatPage();
//                setUpWhatPageAdvanceSettings();
                SeleniumUtils.pause(3);
                break;
            case CampaignsHomePage.MOBILE_INAPP:
                changeMobileInAppWhatPage();
                break;
            case CampaignsHomePage.SMS:
                setSMSWhatPage();
                break;
            case CampaignsHomePage.APP_INBOX:
                changeMobileInBoxWhatPage();
                break;
            case CampaignsHomePage.EMAIL:
                setEmailPage();
                break;
            case CampaignsHomePage.WEB_PUSH:
                break;
            case CampaignsHomePage.WEB_POPUP:
                break;
            case CampaignsHomePage.WEB_EXIT_INTENT:
                break;
            case CampaignsHomePage.WHATSAPP:
                SeleniumUtils.scrollUp(driver);
                SeleniumUtils.performClick(driver, templateChangeBtn);
                setWhatsAppPage();
                break;
            case CampaignsHomePage.FB_AUDIENCES:
                break;
            case CampaignsHomePage.GOOGLE_ADS:
                break;
            case CampaignsHomePage.WEBHOOKS:
                break;
            default:
        }
    }

    public void verifyWhatPage() {
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case CampaignsHomePage.MOBILE_PUSH:
                verifyMobilePushWhatPage();
                break;
            case CampaignsHomePage.MOBILE_INAPP:
                changeMobileInAppWhatPage();
                break;
            case CampaignsHomePage.SMS:
                setSMSWhatPage();
                break;
            case CampaignsHomePage.APP_INBOX:
                changeMobileInBoxWhatPage();
                break;
            case CampaignsHomePage.EMAIL:
                setEmailPage();
                break;
            case CampaignsHomePage.WEB_PUSH:
                break;
            case CampaignsHomePage.WEB_POPUP:
                break;
            case CampaignsHomePage.WEB_EXIT_INTENT:
                break;
            case CampaignsHomePage.WHATSAPP:
                SeleniumUtils.scrollUp(driver);
                SeleniumUtils.performClick(driver, templateChangeBtn);
                setWhatsAppPage();
                break;
            case CampaignsHomePage.FB_AUDIENCES:
                break;
            case CampaignsHomePage.GOOGLE_ADS:
                break;
            case CampaignsHomePage.WEBHOOKS:
                break;
            default:
        }
    }

    private void setWhatsAppPage() {
        selectWhatsAppTemplate();
        int messageTextBoxCount = messageInput.size();
        for (int i = 0; i < messageTextBoxCount; i++) {
            SeleniumUtils.enterInputText(driver, messageInput.get(i), campaignMeta.getWhat().getWhatsAppMessageToBeSent() + "" + i);
        }
    }

    private void setWebpushWhatPage() {
        if (titleFieldCounter == 1) {
            clickContinue();
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
        }
        mobilePushTitle.sendKeys(campaignMeta.getWhat().getPush_title());
        if (messageFieldCounter == 1) {
            clickContinue();
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
        }
        mobilePushMessage.sendKeys(campaignMeta.getWhat().getPush_message());
//        System.out.println(whatPageAdvanceSettingsCSVMap.get("webpush").get("deep_link").replace("-",":"));
        SeleniumUtils.enterInputText(driver, deepLinkTextbox, whatPageAdvanceSettingsCSVMap.get("webpush").get("deep_link").replace("-", ":"));
//        System.out.println(whatPageAdvanceSettingsCSVMap.get("webpush").get("image_link").replace("-",":"));
//        SeleniumUtils.enterInputText(driver, imageLink, whatPageAdvanceSettingsCSVMap.get("webpush").get("image_link").replace("-",":"));
    }

    private void setEmailPage() {

        singleMessageEmail.click();
        SeleniumUtils.waitForPageLoaded(driver);
        WebElement emailTemplate = driver.findElement(By.xpath("//div[@id='templates_gallery']/div[" + campaignMeta.getWhat().getEmail_template_number() + "]"));
        SeleniumUtils.moveToElementAndClick(emailTemplate, driver);
        SeleniumUtils.waitForPageLoaded(driver);
//        emailTemplatesList.get(campaignMeta.getWhat().getEmail_template_number()).click();
//        SeleniumUtils.pause(15);
        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhat().getEmail_from_name())) {
            emailFromName.sendKeys(campaignMeta.getWhat().getEmail_from_name());
        } else {
            emailFromName.sendKeys("default from name");
        }
        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhat().getEmail_subject())) {
            emailSubject.sendKeys(campaignMeta.getWhat().getEmail_subject());
        } else {
            emailSubject.sendKeys("default subject");
        }
    }

    private void setMobilePushWhatPage() {

        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case MOBILE_IN_APP:
            case APP_INBOX:
            case SMS:
            case EMAIL:
            case AUDIENCES:
            case WEBHOOKS:
            case WHATSAPP:
//                SeleniumUtils.performClick(driver, mobilePushSingleMessage);
                WebElement whatMessageToBeSelected = driver.findElement(By.xpath("//*[@data-campaign-type='" + campaignMeta.getWhat().getMessage_to_be_selected() + "']"));
                SeleniumUtils.performClick(driver, whatMessageToBeSelected);
                SeleniumUtils.pause(1);
                break;
            case WEB_PUSH:
            case WEB_POP_UP:
            case WEB_EXIT_INTENT:
                break;
            default:
        }


        switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
            case A_B:
                setDetailsInABVariants();

                break;
            case MULTIVARIATE:
                messageOnUserPropTabPlusBtn.click();
                driver.findElement(By.xpath("//*[@id='selectedPivotValues']//li//input[@data-value='Automation-Prop']/../label")).click();
//                selectFirstProp.click();
                applySelectedProp.click();
                setDetailsInUserOnMessageProp();


                break;
            default:
//                if (titleFieldCounter == 1) {
//                    clickContinue();
//                    Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
//                }
//
//                String userProp="";
//                String plaingText="";
//
//                if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("title"))) {
//
//                    String[] personalisationProps=whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("title").split(" ");
//
//                    for (String str:personalisationProps){
//                        if (str.contains("@")){
//                            userProp+=str+" ";
//                        }else {
//                            plaingText+=str+" ";
//                        }
//                    }
//
//                }
//
//
//                if (!SeleniumUtils.isNullOrEmpty(userProp)){
//                    setPersonalization(mobilePushTitle, "title", userProp.trim(), "def def");
//                }
//                if (!SeleniumUtils.isNullOrEmpty(plaingText)){
//                    mobilePushTitle.sendKeys(plaingText);
//                }

                switch (campaignMeta.getCurrentRunningTestCaseName().trim().toLowerCase()){
                    case "suc-34052":
                        fillRecommendationPersonalization(mobilePushTitle,new ArrayList<String>(Arrays.asList("Identity","Name","ImageURL")));// provide the keys of the recommendation
                        fillRecommendationPersonalization(mobilePushMessage,new ArrayList<String>(Arrays.asList("Identity","Name","ImageURL")));
                        break;
                    default:
//                        fillDetailsInTitle();
                        fillDetailsInTitleForAll();
//                        fillDetailsInMessage();
                        fillDetailsInMessageForAll();
                }


//                if (messageFieldCounter == 1) {
//                    clickContinue();
//                    Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
//                }
//
//                if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("message"))) {
//
//                    String[] personalisationProps=whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("message").split(" ");
//
//                    for (String str:personalisationProps){
//                        if (str.contains("@")){
//                            userProp+=str+" ";
//                        }else {
//                            plaingText+=str+" ";
//                        }
//                    }
//
//                }
//
//
//                if (!SeleniumUtils.isNullOrEmpty(userProp)){
//                    setPersonalization(mobilePushMessage, "message", userProp.trim(), "def def");
//                }
//                if (!SeleniumUtils.isNullOrEmpty(plaingText)){
//                    mobilePushMessage.sendKeys(plaingText);
//                }
//
////                if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getPersonalisation())) {
////                    setPersonalization(mobilePushMessage, "message", "QA.Automation2-DND", "HI TIGER");
////                }else {
////                    mobilePushMessage.sendKeys(campaignMeta.getWhat().getPush_message());
////                }


                setOthersWhatPageDetails();

                break;
        }


    }

    private void setDetailsInABVariants() {
        Iterator<String> iterator = abTestCSVMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            switch (key) {
                case "Variant A":
                    if (isVariantBFilled) {
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        SeleniumUtils.moveToElement(driver.findElement(By.xpath("(//*[@data-variant='" + key + "'])")), driver);
                        driver.findElement(By.xpath("(//*[@data-variant='" + key + "']//i)[1]")).click();
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']//li[text()='Copy from Variant B']")).click();
                        SeleniumUtils.pause(1);
                    } else {
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        /*
                        Siddhartha Das  1 hour ago
                        I understand you concern @Manmohan Roy
                        but how does it matter when you have entered no
                        data in either of the variants?  From a hygiene perspective I
                        do understand, at the same time it's a very conner case. We aren't going to
                        work on this immediately so please go ahead and add the extra bit to your automation code.
                        Also, please send a request to product-requests@clevertap.com so that this gets tracked
                         */

                        mobilePushTitle.clear();
                        if (titleFieldCounter == 1) {
                            clickContinue();
                            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
                        }
                        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getPersonalisation()) && campaignMeta.getPersonalisation().trim().toLowerCase().contains("true")) {
//                            setPersonalization("title", "QA.Automation-DND", "hello Mr India");
                        } else {
                            mobilePushTitle.sendKeys(abTestCSVMap.get(key).get("Title"));
                        }
                        mobilePushMessage.clear();
                        if (messageFieldCounter == 1) {
                            clickContinue();
                            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
                        }
                        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getPersonalisation()) && campaignMeta.getPersonalisation().trim().toLowerCase().contains("true")) {
//                            setPersonalization("message", "QA.Automation2-DND", "hello Mrs India");
                        } else {
                            mobilePushMessage.sendKeys(abTestCSVMap.get(key).get("Message"));
                        }
                        setOthersWhatPageDetails();
                        setUpWhatPageAdvanceSettings();
                        isVariantAFilled = true;
                    }
                    break;
                case "Variant B":
                    if (isVariantAFilled) {
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        SeleniumUtils.moveToElement(driver.findElement(By.xpath("(//*[@data-variant='" + key + "'])")), driver);
                        driver.findElement(By.xpath("(//*[@data-variant='" + key + "']//i)[1]")).click();
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']//li[text()='Copy from Variant A']")).click();
                        SeleniumUtils.pause(1);
                    } else {
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        mobilePushTitle.clear();
                        if (titleFieldCounter == 1) {
                            clickContinue();
                            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
                        }
                        SeleniumUtils.pause(2);
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        mobilePushTitle.sendKeys(abTestCSVMap.get(key).get("Title"));

                        mobilePushMessage.clear();
                        if (messageFieldCounter == 1) {
                            clickContinue();
                            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
                        }
                        SeleniumUtils.pause(2);
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        mobilePushMessage.sendKeys(abTestCSVMap.get(key).get("Message"));
                        setOthersWhatPageDetails();
                        setUpWhatPageAdvanceSettings();
                        isVariantBFilled = true;
                    }

                    break;
                default:
            }
        }
    }

    private void verifyDetailsInABVariants() {
        Iterator<String> iterator = abTestCSVMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            switch (key) {
                case "Variant A":
                    if (isVariantBFilled) {
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        SeleniumUtils.moveToElement(driver.findElement(By.xpath("(//*[@data-variant='" + key + "'])")), driver);
                        driver.findElement(By.xpath("(//*[@data-variant='" + key + "']//i)[1]")).click();
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']//li[text()='Copy from Variant B']")).click();
                        SeleniumUtils.pause(1);
                    } else {
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        /*
                        Siddhartha Das  1 hour ago
                        I understand you concern @Manmohan Roy
                        but how does it matter when you have entered no
                        data in either of the variants?  From a hygiene perspective I
                        do understand, at the same time it's a very conner case. We aren't going to
                        work on this immediately so please go ahead and add the extra bit to your automation code.
                        Also, please send a request to product-requests@clevertap.com so that this gets tracked
                         */

                        if (titleFieldCounter == 1) {
                            clickContinue();
                            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
                        }
                        mobilePushTitle.sendKeys(abTestCSVMap.get(key).get("Title"));
                        if (messageFieldCounter == 1) {
                            clickContinue();
                            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
                        }
                        mobilePushMessage.sendKeys(abTestCSVMap.get(key).get("Message"));
                        setOthersWhatPageDetails();
                        setUpWhatPageAdvanceSettings();
                        isVariantAFilled = true;
                    }
                    break;
                case "Variant B":
                    if (isVariantAFilled) {
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        SeleniumUtils.moveToElement(driver.findElement(By.xpath("(//*[@data-variant='" + key + "'])")), driver);
                        driver.findElement(By.xpath("(//*[@data-variant='" + key + "']//i)[1]")).click();
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']//li[text()='Copy from Variant A']")).click();
                        SeleniumUtils.pause(1);
                    } else {
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        if (titleFieldCounter == 1) {
                            clickContinue();
                            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
                        }
                        SeleniumUtils.pause(2);
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        mobilePushTitle.sendKeys(abTestCSVMap.get(key).get("Title"));

                        if (messageFieldCounter == 1) {
                            clickContinue();
                            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
                        }
                        SeleniumUtils.pause(2);
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                        mobilePushMessage.sendKeys(abTestCSVMap.get(key).get("Message"));
                        setOthersWhatPageDetails();
                        setUpWhatPageAdvanceSettings();
                        isVariantBFilled = true;
                    }

                    break;
                default:
            }
        }
    }

    private void setDetailsInUserOnMessageProp() {

        Set<String> messageOnUserPropSet = messageOnUserPropTestCSVMap.keySet();
        if (messageOnUserPropSet.contains("Default")) {
            SeleniumUtils.performClick(driver, defaultABTab);

//            if (titleFieldCounter == 1) {
//                clickContinue();
//                Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
//            }
//
//            if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getPersonalisation()) && campaignMeta.getPersonalisation().trim().toLowerCase().contains("true")){
//                setPersonalization(mobilePushTitle,"title","QA.Automation1-DND","hello dear");
//            }else {
//                mobilePushTitle.sendKeys(campaignMeta.getWhat().getPush_title());
//            }
//            fillDetailsInTitle();
//            fillDetailsInMessage();
            fillDetailsInTitleForAll();
            fillDetailsInMessageForAll();

//            if (messageFieldCounter == 1) {
//                clickContinue();
//                Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
//            }
//
//            if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getPersonalisation()) && campaignMeta.getPersonalisation().trim().toLowerCase().contains("true")){
//                setPersonalization(mobilePushMessage,"message","QA.Automation1-DND","hello dear");
//            }else {
//                mobilePushMessage.sendKeys(campaignMeta.getWhat().getPush_message());
//            }

            setOthersWhatPageDetails();
            setUpWhatPageAdvanceSettings();

        }

        if (messageOnUserPropSet.contains("QA-Automation-DND")) {
            SeleniumUtils.performClick(driver, driver.findElement(By.id("Automation-Prop")));
//            if (titleFieldCounter == 1) {
//                clickContinue();
//                Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
//            }
//
//            mobilePushTitle.sendKeys(campaignMeta.getWhat().getPush_title());
//
//            if (messageFieldCounter == 1) {
//                clickContinue();
//                Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
//            }
//
//
//            mobilePushMessage.sendKeys(campaignMeta.getWhat().getPush_message());
//            fillDetailsInTitle();
//            fillDetailsInMessage();
            fillDetailsInTitleForAll();
            fillDetailsInMessageForAll();

            setOthersWhatPageDetails();
            setUpWhatPageAdvanceSettings();
        }

    }

    /***
     *
     * @param isChanged : optional parameter to consider OS type as changed in case of cloning the campaign,
     *                  true for using changedDeviceOS
     *                  default is false, ie if false or nothing is sent then changedOSType will not be considered
     */
    private void setOthersWhatPageDetails(boolean... isChanged) {
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case MOBILE_IN_APP:
            case APP_INBOX:
            case SMS:
            case EMAIL:
            case AUDIENCES:
            case WEBHOOKS:
            case WHATSAPP:
//                if (androidNotificationChannelCounter == 1 && !campaignMeta.getWho().getDevice_os().equalsIgnoreCase(IOS)) {
//                    clickContinue();
//                    /*Commenting below line as providing android channel name in input box , removed dropdown so no verification required */
////                    Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Notification channel required");
//
//                    androidNotificationChannelCounter++;
//                }
//                // case when creating campaign osType was android and then while editing its again android
//                if(androidNotificationChannelCounter!=1 && !campaignMeta.getWho().getChange_os_type().equalsIgnoreCase(IOS)){
//                    clickContinue();
//                    Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Notification channel required");
//                }
//                clickContinue();
                break;
            case WEB_PUSH:
            case "web pop-up":
            case WEB_EXIT_INTENT:
                //
                break;
            default:

        }


        //code to select Android O channel name:
        SeleniumUtils.pause(2);
        SeleniumUtils.scrollDown(driver, "400");
        SeleniumUtils.pause(2);
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case MOBILE_IN_APP:
            case APP_INBOX:
            case SMS:
            case EMAIL:
            case AUDIENCES:
            case WEBHOOKS:
            case WHATSAPP:
                String osType="";
                if(isChanged.length>0 && isChanged[0]==true){
                    osType = campaignMeta.getWho().getChange_os_type().trim().toLowerCase();
                }else{
                    osType=campaignMeta.getWho().getDevice_os().trim().toLowerCase();
                }
                switch (osType) {
                    case IOS:
                        logger.info("Do nothing as notifications channel not required");
                        break;
                    case ANDROID:
                        setNotificationChannel();
                        break;
                    case UNCHECKED_ALL_OS_TYPE:
                        setNotificationChannel();
                        break;
                    default:
                        setNotificationChannel();
                        break;


                }
                break;
            case WEB_PUSH:
            case "web pop-up":
            case WEB_EXIT_INTENT:
                //
                break;
            default:

        }
    }

    private void setWhatPageTitleAndMessage(String key) {
        if (titleFieldCounter == 1) {
            clickContinue();
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
        }

        mobilePushTitle.sendKeys(abTestCSVMap.get(key).get("Title"));

        if (messageFieldCounter == 1) {
            clickContinue();
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
        }


        mobilePushMessage.sendKeys(abTestCSVMap.get(key).get("Message"));
    }

    private void changeMobilePushWhatPage() {


        switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
            case A_B:
//                setDetailsInABVariants();

//                SeleniumUtils.scrollDown(driver);
//
//                try {
//                    CampaignsHomePage campaignsHomePage=new CampaignsHomePage(driver);
//                    campaignsHomePage.selectNotificationChannel();
//                    SeleniumUtils.performClick(driver, driver.findElement(By.id("btn_save_as_draft")));
//                    sweetAlert.getSweetAlertErrorSignal();
//                    SeleniumUtils.scrollDown(driver);
//                    campaignsHomePage.selectNotificationChannel();
//
//
//                }catch (org.openqa.selenium.NoSuchElementException nse){
//                    logger.error(nse);
//                }
                changeNotificationChannel();

                break;
            case MULTIVARIATE:
//                messageOnUserPropTabPlusBtn.click();
//                driver.findElement(By.xpath("//*[@id='selectedPivotValues']//li//input[@data-value='Automation-Prop']/../label")).click();
//                selectFirstProp.click();
//                applySelectedProp.click();
//                setDetailsInUserOnMessageProp();


                break;
            default:

                mobilePushTitle.clear();
                mobilePushTitle.sendKeys("changedWhileCloning-" + campaignMeta.getWhat().getPush_title());
                mobilePushMessage.clear();
                mobilePushMessage.sendKeys("changedWhileCloning-" + campaignMeta.getWhat().getPush_message());

//                if (titleFieldCounter == 1) {
//                    clickContinue();
//                    Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
//                    titleFieldCounter++;
//                }

//                mobilePushTitle.sendKeys(campaignMeta.getWhat().getPush_title());

//                if (messageFieldCounter == 1) {
//                    clickContinue();
//                    Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
//                    messageFieldCounter++;
//                }


//                mobilePushMessage.sendKeys(campaignMeta.getWhat().getPush_message());

                setOthersWhatPageDetails(true);
                changeNotificationChannel();

                break;
        }

//        changeNotificationChannel();


    }

    private void changeNotificationChannel() {
        //code to select Android O channel name:
        SeleniumUtils.pause(2);
        SeleniumUtils.scrollDown(driver, "400");
        SeleniumUtils.pause(2);
        switch (campaignMeta.getWho().getChange_os_type().trim().toLowerCase()) {
            case IOS:
                logger.info("Do nothing as notifications channel not required");
                break;
            case ANDROID:
                switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
                    case SINGLEMESSAGE:
                        setNotificationChannel();
                        break;
                    case A_B:
                        Iterator<String> iterator = abTestCSVMap.keySet().iterator();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            switch (key) {
                                case "Variant A":
                                    switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
                                        case SINGLEMESSAGE:
                                            break;
                                        case A_B:
                                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@class='framework-nav--parent'][1]")));
                                            SeleniumUtils.pause(1);
//                                            SeleniumUtils.performClick(driver,driver.findElement(By.xpath("(//*[@class='framework-nav--parent'])[2]")));
                                            SeleniumUtils.performClick(driver, continueButton);
                                            SeleniumUtils.pause(1);
                                            break;
                                        case MULTIVARIATE:
                                            break;
                                        default:
                                            break;
                                    }
                                    driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                                    setNotificationChannel();
                                    break;
                                case "Variant B":
                                    switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
                                        case SINGLEMESSAGE:
                                            break;
                                        case A_B:
                                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@class='framework-nav--parent'][1]")));
                                            SeleniumUtils.pause(1);
//                                            SeleniumUtils.performClick(driver,driver.findElement(By.xpath("(//*[@class='framework-nav--parent'])[2]")));
                                            SeleniumUtils.performClick(driver, continueButton);
                                            SeleniumUtils.pause(1);
                                            break;
                                        case MULTIVARIATE:
                                            break;
                                        default:
                                            break;
                                    }
                                    driver.findElement(By.xpath("//*[@data-variant='" + key + "']")).click();
                                    setNotificationChannel();
                                    break;
                                default:
                            }
                        }

                        break;
                    case MULTIVARIATE:
                        for (WebElement propTab:userPropsPivotTabs){
                            propTab.click();
                            SeleniumUtils.pause(2);
                            setNotificationChannel();
                        }
                        break;
                    default:
                        break;
                }
                break;
            case UNCHECKED_ALL_OS_TYPE:
                setNotificationChannel();
                break;
            default:

        }
    }

    private void verifyMobilePushWhatPage() {

        switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
            case SINGLEMESSAGE:

                /*validate campaign what page title */
                verifyPersonalizationText("title");
//                Assert.assertTrue(mobilePushTitle.getText().contains(titlePlaneText.toString()));
                Assert.assertTrue(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("title").contains(mobilePushTitle.getText()));
                    /*validate campaign what page message*/
                verifyPersonalizationText("message");
//                Assert.assertTrue(mobilePushMessage.getText().contains(messagePlaneText.toString()));
                Assert.assertTrue(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("message").contains(mobilePushMessage.getText()));


                verifyOSType();
                break;
            case MULTIVARIATE:
                for (WebElement element : userPropsPivotTabs) {
                    SeleniumUtils.performClick(driver, element);

                    /*validate campaign what page title */
                    verifyPersonalizationText("title");
                    Assert.assertTrue(mobilePushTitle.getText().contains(titlePlaneText.toString()));

                    /*validate campaign what page message*/
                    verifyPersonalizationText("message");
                    Assert.assertTrue(mobilePushMessage.getText().contains(messagePlaneText.toString()));
                }
                break;
            case A_B:
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@data-step-name='what']/a")));
                for (WebElement element : abVariants) {
                    if (element.getAttribute("class").contains("is-visible")) {
                        SeleniumUtils.performClick(driver, element);
                        if (isVariantAFilled) {
                            /*validate campaign what page title */
                            Assert.assertTrue(mobilePushTitle.getText().equalsIgnoreCase(abTestCSVMap.get("Variant A").get("Title")));
                    /*validate campaign what page message*/
                            Assert.assertTrue(mobilePushMessage.getText().equalsIgnoreCase(abTestCSVMap.get("Variant A").get("Message")));
                            verifyWhatPageAdvanceSettings();
                        } else if (isVariantBFilled) {
                            /*validate campaign what page title */
                            Assert.assertTrue(mobilePushTitle.getText().equalsIgnoreCase(abTestCSVMap.get("Variant B").get("Title")));
                    /*validate campaign what page message*/
                            Assert.assertTrue(mobilePushMessage.getText().equalsIgnoreCase(abTestCSVMap.get("Variant B").get("Message")));
                            verifyWhatPageAdvanceSettings();
                        } else {
                            //
                        }

                    }
                }


                break;
            case LEGACY:
                break;
            default:
        }

         /*Validating custom key-value pairs*/
        int customKeyValueMapSize = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").size();

        for (int i = 1; i <= customKeyValueMapSize; i++) {
            WebDriverWait wait = new WebDriverWait(driver, 10);

            /*validating the checkbox checked or not*/
//            Assert.assertTrue(driver.findElement(By.id("android_chk_"+(i-1)+"")).isSelected());
//            Assert.assertTrue(driver.findElement(By.id("ios_chk_"+(i-1)+"")).isSelected());

            WebElement keyInput = driver.findElement(By.xpath(ID_KEYVALUE_PAIR_CONTAINER_DIV_CLASS_FLEX_ROW + i + "]//div[contains(@class,'is-profile-key')]/input"));
            String customKey = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").keySet().toArray()[+(i - 1)].toString();
            Assert.assertTrue(keyInput.getAttribute("value").equalsIgnoreCase(customKey));

            WebElement valueInput = driver.findElement(By.xpath(ID_KEYVALUE_PAIR_CONTAINER_DIV_CLASS_FLEX_ROW + i + "]//div[contains(@class,'is-profile-value')]/div"));
            String customValue = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").get(customKey);
            Assert.assertTrue(valueInput.getText().equalsIgnoreCase(customValue));
        }


        SeleniumUtils.scrollDown(driver, "400");
        switch (campaignMeta.getWho().getDevice_os().trim().toLowerCase()) {
            case IOS:
                logger.info("Do nothing as notifications channel not required");
                break;
            case ANDROID:
//                verifyNotificationChannel();
                break;
            case UNCHECKED_ALL_OS_TYPE:
                verifyNotificationChannel();
                break;
            default:

        }

    }

    private void verifyOSType() {
        switch (campaignMeta.getWho().getDevice_os().trim().toLowerCase()) {
            case IOS:
                Assert.assertTrue(whatPageAdvanceAndroidSection.getAttribute("style").equalsIgnoreCase("display: none;"));
                verifyWhatPageIosSettings();
                break;
            case ANDROID:
                Assert.assertTrue(whatPageAdvanceIosSection.getAttribute("style").equalsIgnoreCase("display: none;"));

                verifyWhatPageAndroidSettings();

                break;
            case UNCHECKED_ALL_OS_TYPE:
                verifyWhatPageIosSettings();

                verifyWhatPageAndroidSettings();
                break;

            default:

        }
    }

    private void verifyWhatPageIosSettings() {
        for (WebElement element : iOsRishToggleItems) {
            switch (element.getAttribute("data-toggle").trim().toLowerCase()) {
                case RICHMEDIA:
                    switch (whatPageAdvanceSettingsCSVMap.get("ios").get("rich_media").trim().toLowerCase()) {
                        case SINGLE_MEDIA:
                            Assert.assertTrue(driver.findElement(By.id("singleMedia")).isSelected());
                            Assert.assertTrue(singleMediaUrlBox.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("ios").get("single_media_url").replace("-", ":")));
                            break;
                        case IMAGE_CAROUSEL:
                            Assert.assertTrue(driver.findElement(By.id("carousalImg")).isSelected());
                            break;
                        default:

                    }
                    break;
                case IOS_SOUND_FILE:
                    switch (whatPageAdvanceSettingsCSVMap.get("ios").get("sound_file").trim().toLowerCase()) {
                        case DEFAULT_OS_SOUND:
                            Assert.assertTrue(driver.findElement(By.id("iosDefaultSound")).isSelected());
                            break;
                        case CUSTOM_SOUND:
                            Assert.assertTrue(driver.findElement(By.id("iosCustomSound")).isSelected());
                            break;
                        default:
                    }
                    break;
                case IOS_CATEGORY:
                    Assert.assertTrue(categoryInput.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("ios").get("category")));
                    break;
                case IOS_BADGE_COUNT:
                    Assert.assertTrue(badgeCountInput.getAttribute("value").equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("ios").get("badge_count")));
                    break;
                case IOS_DEEP_LINK:
                    if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("iosDeepLink").contains("@")) {
                        verifyPersonalizationText("whatpageiosdeeplink");
                    }else {

//                       Assert.assertTrue(deepLink.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("iosDeepLink").replace("-", ":")));
                        //because while setting we are picking from map IOS begining of what page advn setings csv
                        Assert.assertTrue(deepLink.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("ios").get("deep_link").replace("-", ":")));

                    }

                    break;
                default:
            }

        }
    }

    private void verifyWhatPageAndroidSettings() {
        for (WebElement element : androidRishToggles) {
            switch (element.getAttribute("data-toggle").trim().toLowerCase()) {
                case ANDROID_SUBTITLE:
                    Assert.assertTrue(androidSubTitle.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("subtitle")));
                    break;
                case ANDROID_IMAGE_URL:
                    Assert.assertTrue(imageUrl.getText().equalsIgnoreCase((whatPageAdvanceSettingsCSVMap.get("android").get("image_summary").split("|"))[0].replace("-", ":")));
                    Assert.assertTrue(summaryText.getText().equalsIgnoreCase((whatPageAdvanceSettingsCSVMap.get("android").get("image_summary").split("|"))[1]));
                    break;
                case ANDROID_LARGE_ICON_URL:
                    Assert.assertTrue(largeIcon.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("large_icon_url").replace("-", ":")));
                    break;
                case ANDROID_SMALL_ICON_COLOR:
//                    Assert.assertTrue(backgroudColor.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("small_app_icon_color")));
                    Assert.assertTrue(backgroudColor.getAttribute("value").equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("small_app_icon_color")));
                    break;
                case ANDROID_DEEP_LINK:
                    Assert.assertTrue(androidDeepLink.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("deep_link").replace("-", ":")));
                    break;
                case ANDROID_ACTIONS:
                    Assert.assertTrue(androidActionLabel.getText().equalsIgnoreCase((whatPageAdvanceSettingsCSVMap.get("android").get("actions").split("|")[0])));
                    Assert.assertTrue(androidActionID.getText().equalsIgnoreCase((whatPageAdvanceSettingsCSVMap.get("android").get("actions").split("|")[1])));
                    break;
                case ANDROID_SOUND:

                    switch (whatPageAdvanceSettingsCSVMap.get("android").get("sound_file").trim().toLowerCase()) {
                        case DEFAULT_OS_SOUND:
                            Assert.assertTrue(driver.findElement(By.id("andDefaultSound")).isSelected());
                            break;
                        case CUSTOM_SOUND:
                            Assert.assertTrue(driver.findElement(By.id("andCustomSound")).isSelected());
                            break;
                        default:
                    }
                    break;
                default:
            }


        }
    }

    private void setNotificationChannel() {
            waitForElementToLoad(driver,notificationChannelInput);
            enterInputText(driver,notificationChannelInput,campaignMeta.getWhat().getPush_androido_channel());
    }

    private void verifyNotificationChannel() {
        SeleniumUtils.scrollDown(driver);
        Assert.assertTrue(channelDropDown.getText().equalsIgnoreCase(campaignMeta.getWhat().getPush_androido_channel()));
    }


    private void configureMobileInAppWhatPage() {

        switch (campaignMeta.getWhat().getSelect_message_type().trim().toLowerCase()) {
            case SINGLE_MESSAGE:
            case A_B_TEST:
            case MESSAGE_ON_USER_PROPERTY:
            case LEGACY:
                /*this method will select the template type like cover,interstitial, half interstitial, footer, header etc*/
                selectTemplateType();
                break;
            default:
        }


    }

    private void changeMobileInAppWhatPage() {

        switch (campaignMeta.getWhat().getChange_inapp_messageType().trim().toLowerCase()) {
            case SINGLE_MESSAGE:
            case A_B_TEST:
            case MESSAGE_ON_USER_PROPERTY:
            case LEGACY:
                /*this method will select the template type like cover,interstitial, half interstitial, footer, header etc*/
                SeleniumUtils.performClick(driver, templateChangeBtn);
                changeTemplateType();
                break;
            default:
        }


    }

    private void configureMobileInBoxWhatPage() {

        switch (campaignMeta.getWhat().getSelect_message_type().trim().toLowerCase()) {
            case SINGLE_MESSAGE:
            case A_B_TEST:
            case MESSAGE_ON_USER_PROPERTY:
            case LEGACY:
                /*this method will select the template type like cover,interstitial, half interstitial, footer, header etc*/
                selectTemplateType();
                break;
            default:
        }


    }

    private void changeMobileInBoxWhatPage() {

        switch (campaignMeta.getWhat().getChange_inapp_messageType().trim().toLowerCase()) {
            case SINGLE_MESSAGE:
            case A_B_TEST:
            case MESSAGE_ON_USER_PROPERTY:
            case LEGACY:
                /*this method will select the template type like cover,interstitial, half interstitial, footer, header etc*/
                changeTemplateType();
                break;
            default:
        }


    }

    private void selectTemplateType() {

        switch (campaignMeta.getWhat().getSelect_template_type().trim().toLowerCase()) {
            case COVER:
                selectMobileInAppTemplate();
                break;
            case INTERSTITIAL:
                selectMobileInAppTemplate();
                break;
            case HALF_INTERSTITIAL:
                selectMobileInAppTemplate();
                break;
            case HEADER:
                selectMobileInAppTemplate();
                break;
            case FOOTER:
                selectMobileInAppTemplate();
                break;
            case ALERT:
                selectMobileInAppTemplate();
                break;
            case CUSTOM_HTML:
                selectMobileInAppTemplate();
                break;
            case SIMPLE_MESSAGE:
            case CAROUSEL_MESSAGE:
            case MESSAGE_WITH_ICON:
            case CUSTOM_KEY_VALUE:
            case SIMPLE_BANNER:
            case CAROUSEL_BANNER:
            case BANNER_WITH_ICON:
                selectMobileInBoxTemplate();
                break;
            default:


        }
    }

    private void changeTemplateType() {
        switch (campaignMeta.getWhat().getChange_inapp_templateType().trim().toLowerCase()) {
            case COVER:
                changeMobileInAppTemplate();
                break;
            case INTERSTITIAL:
                changeMobileInAppTemplate();
                break;
            case HALF_INTERSTITIAL:
                changeMobileInAppTemplate();
                break;
            case HEADER:
                changeMobileInAppTemplate();
                break;
            case FOOTER:
                changeMobileInAppTemplate();
                break;
            case ALERT:
                changeMobileInAppTemplate();
                break;
            case CUSTOM_HTML:
                changeMobileInAppTemplate();
                break;
            case SIMPLE_MESSAGE:
            case CAROUSEL_MESSAGE:
            case MESSAGE_WITH_ICON:
                changeMobileInBoxTemplate();
                break;
            default:
                logger.info("Not a valid template type::::::!!!!!!!!!");


        }
    }

    private void selectMobileINBoxTemplateType() {
        switch (campaignMeta.getWhat().getSelect_template_type().trim().toLowerCase()) {
            case SIMPLE_MESSAGE:
                selectMobileInAppTemplate();
                break;
            case CAROUSEL_MESSAGE:
                break;
            case MESSAGE_WITH_ICON:
                break;
            default:
                logger.info("Not a valid template type::::::!!!!!!!!!");


        }
    }

    private void selectMobileInAppTemplate() {
        WebElement singleMessageElement = driver.findElement(By.xpath("//label[@class='custom-control-label']//span[text()='" + campaignMeta.getWhat().getSelect_message_type() + "']"));
        SeleniumUtils.performClick(driver, singleMessageElement);
        variantDropdown.click();
        SeleniumUtils.performClick(driver, variantDropdownClear);
        WebElement inAppTemplateSingleMessage = driver.findElement(By.xpath("//*[@class='inapp-template']/div[text()='" + campaignMeta.getWhat().getSelect_template_type() + "']"));
        SeleniumUtils.moveToElement(inAppTemplateSingleMessage, driver);
        SeleniumUtils.moveToElement(driver.findElement(By.xpath(CLASS_INAPP_TEMPLATE_NAME_AND_TEXT + campaignMeta.getWhat().getSelect_template_type() + "']")), driver);
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_INAPP_TEMPLATE_NAME_AND_TEXT + campaignMeta.getWhat().getSelect_template_type() + BUTTON_TEXT + campaignMeta.getWhat().getTemplate_button_type() + "']")));
        SeleniumUtils.pause(3);
        /*below line will veriofy the selected template by getting its title*/
        Assert.assertEquals(SELECTED_TEMPLATE + campaignMeta.getWhat().getSelect_template_type(), templateTitle.getText());
    }

    private void selectWhatsAppTemplate() {
        variantDropdown.click();
        SeleniumUtils.performClick(driver, variantDropdownClear);
        WebElement whatsAppTemplate = driver.findElement(By.xpath(CLASS_WHATSAPP_TEMPLATE_DIV_TEXT + campaignMeta.getWhat().getSelect_template_type() + "']"));
        SeleniumUtils.moveToElement(whatsAppTemplate, driver);
        SeleniumUtils.moveToElement(driver.findElement(By.xpath(CLASS_WHATSAPP_TEMPLATE_DIV_TEXT + campaignMeta.getWhat().getSelect_template_type() + "']")), driver);
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_WHATSAPP_TEMPLATE_DIV_TEXT + campaignMeta.getWhat().getSelect_template_type() + BUTTON_TEXT + campaignMeta.getWhat().getTemplate_button_type() + "']")));
        SeleniumUtils.pause(3);
        /*below line will verify the selected template by getting its title*/
        Assert.assertEquals(SELECTED_TEMPLATE + campaignMeta.getWhat().getSelect_template_type(), templateTitle.getText());
    }

    private void changeMobileInAppTemplate() {
        WebElement singleMessageElement = driver.findElement(By.xpath("//label[@class='custom-control-label']//span[text()='" + campaignMeta.getWhat().getChange_inapp_messageType() + "']"));
        SeleniumUtils.performClick(driver, singleMessageElement);
        variantDropdown.click();
        SeleniumUtils.performClick(driver, variantDropdownClear);
        WebElement inAppTemplateSingleMessage = driver.findElement(By.xpath("//*[@class='inapp-template']/div[text()='" + campaignMeta.getWhat().getChange_inapp_templateType() + "']"));
        SeleniumUtils.moveToElement(inAppTemplateSingleMessage, driver);
        SeleniumUtils.moveToElement(driver.findElement(By.xpath(CLASS_INAPP_TEMPLATE_NAME_AND_TEXT + campaignMeta.getWhat().getChange_inapp_templateType() + "']")), driver);
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_INAPP_TEMPLATE_NAME_AND_TEXT + campaignMeta.getWhat().getChange_inapp_templateType() + BUTTON_TEXT + campaignMeta.getWhat().getChange_inapp_templateButton() + "']")));
        SeleniumUtils.pause(3);
        /*below line will veriofy the selected template by getting its title*/
        Assert.assertTrue(templateTitle.getText().contains(campaignMeta.getWhat().getChange_inapp_templateType()));
    }


    private void selectMobileInBoxTemplate() {

        WebElement singleMessageElement = driver.findElement(By.xpath("//label[@class='custom-control-label']//span[text()='" + campaignMeta.getWhat().getSelect_message_type() + "']"));
        SeleniumUtils.performClick(driver, singleMessageElement);
        variantDropdown.click();
        SeleniumUtils.performClick(driver, variantDropdownClear);

        switch (campaignMeta.getWhat().getSelect_template_type().trim().toLowerCase()) {
            case SIMPLE_MESSAGE:
            case CAROUSEL_MESSAGE:
            case MESSAGE_WITH_ICON:
            case CUSTOM_KEY_VALUE:
            case SIMPLE_BANNER:
            case CAROUSEL_BANNER:
            case BANNER_WITH_ICON:
                SeleniumUtils.scrollDown(driver, "150");
//                WebElement inBoxTemplateSingleMessage = driver.findElement(By.xpath("//*[@class='inbox-template']//div[text()='" + campaignMeta.getWhat().getSelect_template_type() + "']"));
                WebElement inBoxTemplateSingleMessage = driver.findElement(By.xpath("//div[text()='Select template']//..//div[text()='" + campaignMeta.getWhat().getSelect_template_type() + "']"));
                SeleniumUtils.moveToElement(inBoxTemplateSingleMessage, driver);
                SeleniumUtils.pause(1);
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[text()='" + campaignMeta.getWhat().getSelect_template_type() + BUTTON_TEXT + campaignMeta.getWhat().getTemplate_button_type() + "']")));
                SeleniumUtils.pause(3);
                /*below line will veriofy the selected template by getting its title*/
                Assert.assertEquals(SELECTED_TEMPLATE + campaignMeta.getWhat().getSelect_template_type(), templateTitle.getText());
                break;
            default:
                break;
        }

    }

    private void changeMobileInBoxTemplate() {
        WebElement singleMessageElement = driver.findElement(By.xpath("//label[@class='custom-control-label']//span[text()='" + campaignMeta.getWhat().getChange_inapp_messageType() + "']"));
        SeleniumUtils.performClick(driver, singleMessageElement);
        variantDropdown.click();
        SeleniumUtils.performClick(driver, variantDropdownClear);

        SeleniumUtils.performClick(driver, templateChangeBtn);

        switch (campaignMeta.getWhat().getChange_inapp_templateType().trim().toLowerCase()) {
            case SIMPLE_MESSAGE:
            case CAROUSEL_MESSAGE:
            case MESSAGE_WITH_ICON:
                SeleniumUtils.scrollDown(driver, "150");
                WebElement inBoxTemplateSingleMessage = driver.findElement(By.xpath("//*[@class='inbox-template']//div[text()='" + campaignMeta.getWhat().getChange_inapp_templateType() + "']"));
                SeleniumUtils.moveToElement(inBoxTemplateSingleMessage, driver);
                SeleniumUtils.pause(1);
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[text()='" + campaignMeta.getWhat().getChange_inapp_templateType() + BUTTON_TEXT + campaignMeta.getWhat().getChange_inapp_templateButton() + "']")));
                SeleniumUtils.pause(3);
                /*below line will veriofy the selected template by getting its title*/
                Assert.assertTrue(templateTitle.getText().contains(campaignMeta.getWhat().getChange_inapp_templateType()));
                break;
            default:
        }

    }

    public void setSMSWhatPage() {

        smsTextArea.sendKeys(campaignMeta.getWhat().getSms_message());
        /*Validating the "Send a test SMS" hyperlink working or not*/

        SeleniumUtils.performClick(driver, sendTestSms);
        SeleniumUtils.pause(2);
        Assert.assertTrue(smsTestDialog.getAttribute("aria-hidden").equalsIgnoreCase("false"));
        SeleniumUtils.performClick(driver, smsTestDialogCloseButton);

    }

    private void setUpWhatPageAdvanceSettings() {

        SeleniumUtils.performClick(driver, whatPageAdvanceSetting);

        switch (campaignMeta.getWho().getDevice_os().trim().toLowerCase()) {
            case IOS:
                Assert.assertTrue(whatPageAdvanceAndroidSection.getAttribute("style").equalsIgnoreCase("display: none;"));
                for (WebElement element : iOsRishToggleItems) {
                    switch (element.getAttribute("data-toggle").trim().toLowerCase()) {
                        case RICHMEDIA:
                            SeleniumUtils.performClick(driver, element);
                            switch (whatPageAdvanceSettingsCSVMap.get("ios").get("rich_media").trim().toLowerCase()) {
                                case SINGLE_MEDIA:
                                    SeleniumUtils.performClick(driver, iOsSingleMedia);
                                    SeleniumUtils.enterInputText(driver, singleMediaUrlBox, whatPageAdvanceSettingsCSVMap.get("ios").get("single_media_url").replace("-", ":"));
                                    break;
                                case IMAGE_CAROUSEL:
                                    SeleniumUtils.performClick(driver, iOsImageCarousel);
                                    //Write more code to handle image carousel

                                    break;
                                default:

                            }
                            break;
                        case IOS_SOUND_FILE:
                            SeleniumUtils.performClick(driver, element);
                            switch (whatPageAdvanceSettingsCSVMap.get("ios").get("sound_file").trim().toLowerCase()) {
                                case DEFAULT_OS_SOUND:
                                    SeleniumUtils.performClick(driver, iOsDefaultSound);
                                    break;
                                case CUSTOM_SOUND:
                                    SeleniumUtils.performClick(driver, iOsCustomSound);
                                    break;
                                default:
                            }
                            break;
                        case IOS_BADGE_COUNT:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.enterInputText(driver, badgeCountInput, whatPageAdvanceSettingsCSVMap.get("ios").get("badge_count"));

                            break;
                        case IOS_CATEGORY:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.enterInputText(driver, categoryInput, whatPageAdvanceSettingsCSVMap.get("ios").get("category"));
                            break;
                        case IOS_DEEP_LINK:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.pause(2);
                            if(!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("iosDeepLink"))){
                                if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("iosDeepLink").contains("@")) {
                                    String[] iosDLPersonalization=whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("iosDeepLink").split(" ");
                                    Set<String> iosDLPersonalizationSet=new HashSet<>(Arrays.asList(iosDLPersonalization));
//                                    setPersonalization("whatPageIOSDeepLink", iosDLPersonalizationSet, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationIOSDeepLinkMessage"));
                                    setPersonalisationForAll(deepLink, Arrays.asList(iosDLPersonalization), whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationIOSDeepLinkMessage"));
                                } else {
                                    SeleniumUtils.enterInputText(driver, deepLink, whatPageAdvanceSettingsCSVMap.get("ios").get("deep_link").replace("-", ":"));
                                }
                            }

                            break;
                        default:
                    }

                }
                break;
            case ANDROID:
                Assert.assertTrue(whatPageAdvanceIosSection.getAttribute("style").equalsIgnoreCase("display: none;"));

                for (WebElement element : androidRishToggles) {
                    switch (element.getAttribute("data-toggle").trim().toLowerCase()) {
                        case ANDROID_SUBTITLE:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.enterInputText(driver, androidSubTitle, whatPageAdvanceSettingsCSVMap.get("android").get("subtitle"));
                            break;
                        case ANDROID_IMAGE_URL:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.enterInputText(driver, imageUrl, (whatPageAdvanceSettingsCSVMap.get("android").get("image_summary").split("|"))[0].replace("-", ":"));
                            SeleniumUtils.enterInputText(driver, summaryText, (whatPageAdvanceSettingsCSVMap.get("android").get("image_summary").split("|"))[1]);
                            break;
                        case ANDROID_LARGE_ICON_URL:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.enterInputText(driver, largeIcon, whatPageAdvanceSettingsCSVMap.get("android").get("large_icon_url").replace("-", ":"));
                            break;
                        case ANDROID_SMALL_ICON_COLOR:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.enterInputText(driver, backgroudColor, whatPageAdvanceSettingsCSVMap.get("android").get("small_app_icon_color"));
                            break;
                        case ANDROID_DEEP_LINK:
                            SeleniumUtils.performClick(driver, element);
                            if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("androidDeepLink").contains("@")) {
                                String[] androidDLPersonalization=whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("iosDeepLink").split(" ");
                                Set<String> androidDLPersonalizationSet=new HashSet<>(Arrays.asList(androidDLPersonalization));
//                                setPersonalization("whatPageAndroidDeepLink", androidDLPersonalizationSet, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationAndroidDeepLinkMessage"));
                                setPersonalisationForAll(androidDeepLink, Arrays.asList(androidDLPersonalization), whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationAndroidDeepLinkMessage"));
                            } else {
                                SeleniumUtils.enterInputText(driver, androidDeepLink, whatPageAdvanceSettingsCSVMap.get("android").get("deep_link").replace("-", ":"));
                            }
                            break;
                        case ANDROID_ACTIONS:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.enterInputText(driver, androidActionLabel, (whatPageAdvanceSettingsCSVMap.get("android").get("actions").split("|")[0]));
                            SeleniumUtils.enterInputText(driver, androidActionID, (whatPageAdvanceSettingsCSVMap.get("android").get("actions").split("|")[1]));

                            break;
                        case ANDROID_SOUND:
                            SeleniumUtils.performClick(driver, element);
                            SeleniumUtils.performClick(driver, androidDefaultSound);
                            break;
                        default:
                    }


                }

                break;
            case ALL_CHECKED:
                break;
            default:

        }


        /*Setting up custom key-value pairs*/
        int customKeyValueMapSize = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").size();

        for (int i = 1; i <= customKeyValueMapSize; i++) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addKeyValuePair")));
            if (element.isDisplayed()) {
                SeleniumUtils.scrollDown(driver, "200");
                SeleniumUtils.performClick(driver, addKeyValuePair);
            } else {
                SeleniumUtils.performClick(driver, addKeyValuePair);
            }

            List<WebElement> flexRowCheckBoxes = driver.findElements(By.xpath(ID_KEYVALUE_PAIR_CONTAINER_DIV_CLASS_FLEX_ROW + i + "]//span"));
            for (WebElement checkbox : flexRowCheckBoxes) {
                ((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+checkbox.getLocation().y+")");
                SeleniumUtils.waitAndClick(driver,checkbox);
            }

            WebElement keyInput = driver.findElement(By.xpath(ID_KEYVALUE_PAIR_CONTAINER_DIV_CLASS_FLEX_ROW + i + "]//div[contains(@class,'is-profile-key')]/input"));
            String customKey = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").keySet().toArray()[+(i - 1)].toString();
            SeleniumUtils.enterInputText(driver, keyInput, customKey);

            WebElement valueInput = driver.findElement(By.xpath(ID_KEYVALUE_PAIR_CONTAINER_DIV_CLASS_FLEX_ROW + i + "]//div[contains(@class,'is-profile-value')]/div"));
            String customValue = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").get(customKey);
            SeleniumUtils.enterInputText(driver, valueInput, customValue);
        }


       /* this block will be enabled when isWebhookEnabled flag is set to QA BeardedRobot account
       * *//*Setting up Webhooks key-value pairs*//*
        int webhookseMapSize = whatPageAdvanceSettingsCSVMap.get("webhookKeyValuePair").size();

        for (int i = 1; i <= webhookseMapSize; i++) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addWebHookKeyValuePair")));
            if (element.isDisplayed()) {
                SeleniumUtils.scrollDown(driver, "200");
                SeleniumUtils.performClick(driver, webhookAddKeyValuePair);
            } else {
                SeleniumUtils.performClick(driver, webhookAddKeyValuePair);
            }

            WebElement keyInput = driver.findElement(By.xpath("((//*[@id='addWebHookKeyValuePairContainer']//div[@class='flex-row'])["+i+"]//div)[1]"));
            String webhookKey=whatPageAdvanceSettingsCSVMap.get("webhookKeyValuePair").keySet().toArray()[+(i - 1)].toString();
            SeleniumUtils.enterInputText(driver, keyInput, webhookKey);

            WebElement valueInput = driver.findElement(By.xpath("((//*[@id='addWebHookKeyValuePairContainer']//div[@class='flex-row'])["+i+"]//div)[2]"));
            String webhookValue=whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").get(webhookKey);
            SeleniumUtils.enterInputText(driver, valueInput,webhookValue );
        }*/


    }


    private void verifyWhatPageAdvanceSettings() {

        switch (campaignMeta.getWho().getDevice_os().trim().toLowerCase()) {
            case IOS:
                Assert.assertTrue(whatPageAdvanceAndroidSection.getAttribute("style").equalsIgnoreCase("display: none;"));
                for (WebElement element : iOsRishToggleItems) {
                    switch (element.getAttribute("data-toggle").trim().toLowerCase()) {
                        case RICHMEDIA:
                            switch (whatPageAdvanceSettingsCSVMap.get("ios").get("rich_media").trim().toLowerCase()) {
                                case SINGLE_MEDIA:
                                    Assert.assertTrue(singleMediaUrlBox.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("ios").get("single_media_url").replace("-", ":")));
                                    break;
                                case IMAGE_CAROUSEL:
                                    //Write more code to handle image carousel
                                    break;
                                default:

                            }
                            break;
                        case IOS_SOUND_FILE:
                            switch (whatPageAdvanceSettingsCSVMap.get("ios").get("sound_file").trim().toLowerCase()) {
                                case DEFAULT_OS_SOUND:
                                    Assert.assertTrue(driver.findElement(By.id("iosDefaultSound")).isSelected());
                                    break;
                                case CUSTOM_SOUND:
                                    Assert.assertTrue(driver.findElement(By.id("iOsCustomSound")).isSelected());
                                    break;
                                default:
                            }
                            break;
                        case IOS_BADGE_COUNT:
                            Assert.assertTrue(badgeCountInput.getAttribute("value").equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("ios").get("badge_count")));
                            break;
                        case IOS_CATEGORY:
                            Assert.assertTrue(categoryInput.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("ios").get("category")));
                            break;
                        case IOS_DEEP_LINK:
                            verifyPersonalizationText("whatpageiosdeeplink");
//                            Assert.assertTrue(deepLink.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("iosDeepLink").replace("-", ":")));
                            break;
                        default:
                    }

                }
                break;
            case ANDROID:
                Assert.assertTrue(whatPageAdvanceIosSection.getAttribute("style").equalsIgnoreCase("display: none;"));

                for (WebElement element : androidRishToggles) {
                    switch (element.getAttribute("data-toggle").trim().toLowerCase()) {
                        case ANDROID_SUBTITLE:
                            Assert.assertTrue(androidSubTitle.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("subtitle")));
                            break;
                        case ANDROID_IMAGE_URL:
                            Assert.assertTrue(imageUrl.getText().equalsIgnoreCase((whatPageAdvanceSettingsCSVMap.get("android").get("image_summary").split("|"))[0].replace("-", ":")));
                            Assert.assertTrue(summaryText.getText().equalsIgnoreCase((whatPageAdvanceSettingsCSVMap.get("android").get("image_summary").split("|"))[1]));
                            break;
                        case ANDROID_LARGE_ICON_URL:
                            Assert.assertTrue(largeIcon.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("large_icon_url").replace("-", ":")));
                            break;
                        case ANDROID_SMALL_ICON_COLOR:
                            Assert.assertTrue(backgroudColor.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("small_app_icon_color")));
                            break;
                        case ANDROID_DEEP_LINK:
                            verifyPersonalizationText("whatpageandroiddeeplink");
                            Assert.assertTrue(androidDeepLink.getText().equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get("android").get("deep_link").replace("-", ":")));
                            break;
                        case ANDROID_ACTIONS:
                            Assert.assertTrue(androidActionLabel.getText().equalsIgnoreCase((whatPageAdvanceSettingsCSVMap.get("android").get("actions").split("|")[0])));
                            Assert.assertTrue(androidActionID.getText().equalsIgnoreCase((whatPageAdvanceSettingsCSVMap.get("android").get("actions").split("|")[1])));
                            break;
                        case ANDROID_SOUND:
                            Assert.assertTrue(driver.findElement(By.id("andDefaultSound")).isSelected());
                            break;
                        default:
                    }
                }

                break;
            case ALL_CHECKED:
                break;
            default:

        }


        /*Setting up custom key-value pairs*/
        int customKeyValueMapSize = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").size();

        for (int i = 1; i <= customKeyValueMapSize; i++) {

            WebElement keyInput = driver.findElement(By.xpath(ID_KEYVALUE_PAIR_CONTAINER_DIV_CLASS_FLEX_ROW + i + "]//div[contains(@class,'is-profile-key')]/input"));
            String customKey = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").keySet().toArray()[+(i - 1)].toString();
            Assert.assertTrue(keyInput.getAttribute("value").equalsIgnoreCase(customKey));

            WebElement valueInput = driver.findElement(By.xpath(ID_KEYVALUE_PAIR_CONTAINER_DIV_CLASS_FLEX_ROW + i + "]//div[contains(@class,'is-profile-value')]/div"));
            String customValue = whatPageAdvanceSettingsCSVMap.get("customKeyValuePair").get(customKey);
            Assert.assertTrue(valueInput.getText().equalsIgnoreCase(customValue));
        }
    }

    public void clickContinue() {
        SeleniumUtils.performClick(driver, continueButton);
    }

    public void setPersonalization(String forWhichSection, Set<String> userProp, String userPropVal) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        int propCounter = 1;
        Iterator<String> iterator=userProp.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next().replace("@", "");

            switch (forWhichSection.trim().toLowerCase()) {
                case "title":
                    mobilePushTitle.sendKeys("@");
                    SeleniumUtils.pause(1);
                    WebElement userPropTitleEle = driver.findElement(By.xpath("//*[@id='atwho-ground-emoji_wzrk_tgt_message_title']//div[@id='type_Profile']//li[text()='" + item.trim() + "']"));
                    js.executeScript("arguments[0].scrollIntoView();", userPropTitleEle);
                    js.executeScript("arguments[0].click();", userPropTitleEle);
                    SeleniumUtils.pause(1);
                    WebElement title;
                    try {
                        title = driver.findElement(By.xpath("(//*[@id='emoji_wzrk_tgt_message_title']//input)[" + propCounter + "]"));
                    } catch (NoSuchElementException nse) {
                        SeleniumUtils.pause(2);
                        title = driver.findElement(By.xpath("(//*[@id='emoji_wzrk_tgt_message_title']//input)[" + propCounter + "]"));
                    }

                    title.click();
//                js.executeScript("arguments[0].click();",title);
                    title.sendKeys(userPropVal);
                    SeleniumUtils.pause(1);
                    break;
                case "message":
                    mobilePushMessage.sendKeys("@");
                    SeleniumUtils.pause(1);
                    WebElement userPropMsgEle = driver.findElement(By.xpath("//*[@id='atwho-ground-emoji_wzrk_tgt_message_text']//div[@id='type_Profile']//li[text()='" + item.trim() + "']"));
                    js.executeScript("arguments[0].scrollIntoView();", userPropMsgEle);
                    js.executeScript("arguments[0].click();", userPropMsgEle);
                    SeleniumUtils.pause(3);

                    SeleniumUtils.pause(1);
                    WebElement message = driver.findElement(By.xpath("(//*[@id='emoji_wzrk_tgt_message_text']//input)[" + propCounter + "]"));
//                message.click();
                    js.executeScript("arguments[0].click();", message);
                    message.sendKeys(userPropVal);
                    SeleniumUtils.pause(1);
                    break;

                case "whatpageiosdeeplink":
                    deepLink.sendKeys("@");
                    SeleniumUtils.pause(1);
                    WebElement deepLinkEle = driver.findElement(By.xpath("//*[@id='atwho-ground-wzrk_dl']//div[@id='type_Profile']//li[text()='" + item.trim() + "']"));
                    js.executeScript("arguments[0].scrollIntoView();", deepLinkEle);
                    js.executeScript("arguments[0].click();", deepLinkEle);
                    SeleniumUtils.pause(3);

                    SeleniumUtils.pause(1);
                    WebElement iOSDeepLinkMessage = driver.findElement(By.xpath("(//*[@id='wzrk_dl']//input)[" + propCounter + "]"));
//                message.click();
                    js.executeScript("arguments[0].click();", iOSDeepLinkMessage);
                    iOSDeepLinkMessage.sendKeys(userPropVal);
                    SeleniumUtils.pause(1);
                    break;
                case "whatpageandroiddeeplink":
                    androidDeepLink.sendKeys("@");
                    SeleniumUtils.pause(1);
                    WebElement androidDeepLinkEle = driver.findElement(By.xpath("//*[@id='atwho-ground-android_wzrk_dl']//div[@id='type_Profile']//li[text()='" + item.trim() + "']"));
                    js.executeScript("arguments[0].scrollIntoView();", androidDeepLinkEle);
                    js.executeScript("arguments[0].click();", androidDeepLinkEle);
                    SeleniumUtils.pause(3);

                    SeleniumUtils.pause(1);
                    WebElement androidDeepLinkMessage = driver.findElement(By.xpath("(//*[@id='android_wzrk_dl']//input)[" + propCounter + "]"));
//                message.click();
                    js.executeScript("arguments[0].click();", androidDeepLinkMessage);
                    androidDeepLinkMessage.sendKeys(userPropVal);
                    SeleniumUtils.pause(1);
                    break;
                    default:
            }
            propCounter++;

        }

        //*[@id='atwho-ground-android_wzrk_dl']//div[@id='type_Profile']//li[text()='Name']
        //*[@id='atwho-ground-emoji_wzrk_tgt_message_title']//div[@id='type_Profile']
        //*[@id='atwho-ground-emoji_wzrk_tgt_message_text']//div[@id='type_Profile']

    }

    public void verifyPersonalizationText(String forWhichSection) {

        switch (forWhichSection.trim().toLowerCase()) {
            case "title":
                List<WebElement> numberOfPersonalisationPropsInTitle = driver.findElements(By.xpath("//*[@id='emoji_wzrk_tgt_message_title']//input"));
                if (numberOfPersonalisationPropsInTitle!=null){
                    for (WebElement elemnt : numberOfPersonalisationPropsInTitle) {
                        Assert.assertTrue(elemnt.getAttribute("value").equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationTitle")));
                    }
                }

                break;
            case "message":

                List<WebElement> numberOfPersonalisationPropsInMessage = driver.findElements(By.xpath("//*[@id='emoji_wzrk_tgt_message_text']//input"));
                if (numberOfPersonalisationPropsInMessage!=null){
                    for (WebElement elemnt : numberOfPersonalisationPropsInMessage) {
                        Assert.assertTrue(elemnt.getAttribute("value").equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationMessage")));
                    }
                }

                break;

            case "whatpageiosdeeplink":
                List<WebElement> numberOfPersonalisationPropsInIOSDeepLink = driver.findElements(By.xpath("//*[@id='wzrk_dl']//input"));
                if (numberOfPersonalisationPropsInIOSDeepLink!=null){
                    for (WebElement elemnt : numberOfPersonalisationPropsInIOSDeepLink) {
                        Assert.assertTrue(elemnt.getAttribute("value").equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationIOSDeepLinkMessage")));
                    }
                }

                break;
            case "whatpageandroiddeeplink":
                List<WebElement> numberOfPersonalisationPropsInAndroidDeepLink = driver.findElements(By.xpath("//*[@id='android_wzrk_dl']//input"));
                if (numberOfPersonalisationPropsInAndroidDeepLink!=null){
                    for (WebElement elemnt : numberOfPersonalisationPropsInAndroidDeepLink) {
                        Assert.assertTrue(elemnt.getAttribute("value").equalsIgnoreCase(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationAndroidDeepLinkMessage")));
                    }
                }

                break;
            default:

        }

    }


    public void fillDetailsInTitle() {
        if (titleFieldCounter == 1) {
            clickContinue();
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Title field cannot be empty");
        }


        if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("title"))) {

            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("title").trim().split(" ");

            for (String str : personalisationProps) {
                if (str.startsWith("@")) {
//                    titleUserProp += str + " ";
                    titleUserProp.add(str);
                } else {
//                    titlePlaneText += str + " ";
                    titlePlaneText.add(str);
                }
            }

        }


        if (titleUserProp.size() > 0) {
            setPersonalization("title", titleUserProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationTitle").trim());
        }
        if (titlePlaneText.size() > 0) {
//            mobilePushTitle.sendKeys(titlePlaneText.toString());
            for(String message : titlePlaneText){
                mobilePushTitle.sendKeys(message);
            }
        }
    }

    public void fillDetailsInMessage() {
        if (messageFieldCounter == 1) {
            clickContinue();
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Message field cannot be empty");
        }

        if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("message"))) {

            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("message").trim().split(" ");

            for (String str : personalisationProps) {
                if (str.contains("@")) {
//                    messageUserProp += str + " ";
                    messageUserProp.add(str);
                } else {
//                    messagePlaneText += str + " ";
                    messagePlaneText.add(str);
                }
            }

        }


        if (messageUserProp.size() > 0) {
            setPersonalization("message", messageUserProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim()).get("personalisationMessage").trim());
        }
        if (messagePlaneText.size() > 0) {

//            mobilePushMessage.sendKeys(messagePlaneText.toString());
            for(String message : messagePlaneText){
                mobilePushMessage.sendKeys(message);
            }
        }
    }


    public void setPersonalisation_gaur(WebElement webElement, Set<String> userProp, String userPropVal) {
        Actions actions = new Actions(driver);
        Iterator<String> iterator = userProp.iterator();
        WebElement userPropTitleEle=null;
        String item=null;
        while (iterator.hasNext()) {

            try {
                item = iterator.next().replace("@", "");
                webElement.sendKeys("@");
                SeleniumUtils.pause(2);
                System.out.println("(//div[contains(@id,'atwho-ground-emoji_wzrk')]//li[text()='" + item.trim() + "'])[" + personalisationCount + "]");
                userPropTitleEle = driver.findElement(By.xpath("(//div[contains(@id,'atwho-ground-emoji_wzrk')]//li[text()='" + item.trim() + "'])[" + personalisationCount + "]"));
                actions.moveToElement(userPropTitleEle);
                SeleniumUtils.pause(2);
                actions.perform();
                SeleniumUtils.pause(1);
                userPropTitleEle.click();
            }
            catch (Exception e){
                webElement.sendKeys(Keys.BACK_SPACE);
                SeleniumUtils.pause(1);
                webElement.sendKeys("@");
                SeleniumUtils.pause(1);
                userPropTitleEle = driver.findElement(By.xpath("(//div[contains(@id,'atwho-ground-emoji_wzrk')]//li[text()='" + item.trim() + "'])[" + personalisationCount + "]"));
                actions.moveToElement(userPropTitleEle);
                SeleniumUtils.pause(1);
                actions.perform();
                SeleniumUtils.pause(1);
                userPropTitleEle.click();

            }

            WebElement title;
            try {
                title = driver.findElement(By.xpath("(//input[@class='default_value_text_box'])[" + (propCounter) + "]"));
            } catch (NoSuchElementException nse) {
                SeleniumUtils.pause(2);
                title = driver.findElement(By.xpath("(//input[@class='default_value_text_box'])[" + (propCounter) + "]"));
            }

            title.click();
            title.sendKeys(userPropVal);
            SeleniumUtils.pause(1);
            propCounter++;
        }
        personalisationCount++;
    }

    public void fillDetailsInTitleForAdview() {
        mobilePushTitle.clear();
        String plainText = "";
        if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("title"))) {
            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("title").split(" ");
            for (String str : personalisationProps) {
                if (str.startsWith("@")) {
                    titleUserProp.add(str);
                } else {
                    plainText += " " + str;
                }
            }

        } else {
            logger.info("No title was sent");
        }

        if (titleUserProp.size() > 0) {
            setPersonalisation_gaur(mobilePushTitle, titleUserProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationTitle"));

        }
        if (plainText.length() > 0) {
            mobilePushTitle.sendKeys(plainText.trim());
        }
    }


    private void configureAdviewWhatMessagePage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        switch (campaignMeta.getWhat().getSelect_template_type().trim().toLowerCase()) {
            case SIMPLE_BANNER:
//                fillDetailsInTitleForAdview();
                fillDetailsInTitleForAll();
                js.executeScript("arguments[0].scrollIntoView();", expandMessageBox);
                SeleniumUtils.pause(2);
                //verifying whether Message tab is open or not, if not clicking on it to open
                if (driver.findElement(By.xpath("//div[@aria-controls='messageDiv' and @class='collapsed']")).isDisplayed()) {
                    SeleniumUtils.performClick(driver, expandMessageBox);
                }
//                fillDetailsInMessgeForAdview();
                fillDetailsInMessageForAll();
                fillDetailsInActionsForAdview();

                //background colour code
                if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("backgroundcolor"))) {
                    //verifying whether Background tab is open or not, if not clicking on it to open
                    if (driver.findElement(By.xpath("//div[@aria-controls='backgroundDiv' and @class='collapsed']")).isDisplayed()) {
                        SeleniumUtils.performClick(driver, expandBackground);
                    }

                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//div[@id='backgroundDiv']//div[@title='Fill color']")));
                    SeleniumUtils.pause(2);
                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//div[@id='backgroundDiv']//div[@title='Fill color']")));
                    SeleniumUtils.enterInputText(driver, backgroundColorInput, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("backgroundcolor"));
                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//div[@id='backgroundDiv']//div[@title='Fill color']")));

                }

                //media tab code
                fillMediaForAdview();
                //custom kv code
                if (driver.findElement(By.xpath("//div[@aria-controls='customKVHeader' and @class='collapsed']")).isDisplayed()) {
                    SeleniumUtils.performClick(driver, expandCustomKV);
                }
                fillDetailsInCustomKVForAdview();


                break;
            case BANNER_WITH_ICON:
//                fillDetailsInTitleForAdview();
                fillDetailsInTitleForAll();
                js.executeScript("arguments[0].scrollIntoView();", expandMessageBox);
                SeleniumUtils.pause(2);
                //verifying whether Message tab is open or not, if not clicking on it to open
                if (driver.findElement(By.xpath("//div[@aria-controls='messageDiv' and @class='collapsed']")).isDisplayed()) {
                    SeleniumUtils.performClick(driver, expandMessageBox);
                }
//                fillDetailsInMessgeForAdview();
                fillDetailsInMessageForAll();
                fillDetailsInActionsForAdview();

                //background and icon code:
                if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("backgroundcolor"))) {
                    //verifying whether Background tab is open or not, if not clicking on it to open
                    if (driver.findElement(By.xpath("//div[@aria-controls='backgroundandIconDiv' and @class='collapsed']")).isDisplayed()) {
                        SeleniumUtils.performClick(driver, expandBackgroundAndIcon);
                    }

                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//div[@id='backgroundandIconDiv']//div[@title='Fill color']")));
                    SeleniumUtils.pause(2);
                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//div[@id='backgroundandIconDiv']//div[@title='Fill color']")));
                    SeleniumUtils.enterInputText(driver, backgroundColorInput, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("backgroundcolor"));
                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//div[@id='backgroundandIconDiv']//div[@title='Fill color']")));

                }
                if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("uploadIcon")) {
                    SeleniumUtils.performClick(driver, uploadImageForBackgroundMedia);

                    if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("uploadIcon").equalsIgnoreCase("default")) {
                        defaultIcon.click();
                        Assert.assertTrue(iconSavedLoc.isDisplayed(), "Failed to save icon");
                        SeleniumUtils.performClick(driver, saveMediaBtnForBackgroudMedia);

                    } else {
                        js.executeScript("arguments[0].scrollIntoView();", imageLoaderForBackgroudMedia);
                        SeleniumUtils.pause(2);
                        imageLoaderForBackgroudMedia.click();
                        FileUtility.uploadImage(new File(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("uploadIcon")));
                        SeleniumUtils.performClick(driver, saveMediaBtnForBackgroudMedia);
                        Assert.assertTrue(iconSavedLoc.isDisplayed(), "Failed to upload icon");

                    }
                } else if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("personalizedIcon")) {
                    SeleniumUtils.performClick(driver, personalizMediaForBackground);
                    SeleniumUtils.pause(1);
                    SeleniumUtils.enterInputText(driver, personalizeMediaInputBackgroudMedia, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalizedIcon"));

                }

                fillMediaForAdview();

                if (driver.findElement(By.xpath("//div[@aria-controls='customKVHeader' and @class='collapsed']")).isDisplayed()) {
                    SeleniumUtils.performClick(driver, expandCustomKV);
                }
                fillDetailsInCustomKVForAdview();

            case CAROUSEL_BANNER:
                if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("backgroundcolor"))) {
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//div[@class='orientation-background']//div[@title='Fill color']")));
                    SeleniumUtils.pause(1);
                    SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//div[@class='orientation-background']//div[@title='Fill color']")));
                    SeleniumUtils.enterInputText(driver, backgroundColorInput, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("backgroundcolor"));
                    SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//div[@class='orientation-background']//div[@title='Fill color']")));
                }

                if(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("totalslides")){
                    int slideCount=Integer.parseInt(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("totalslides"));
                    fillDataForCarousel(slideCount);
                }

            case CUSTOM_KEY_VALUE:
                if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("customkv")) {
                    fillDetailsInCustomKVForAdview();
                }




        }
    }


    public void fillDetailsInMessgeForAdview() {
        whatPageMessageTextBox.clear();
        String plainText = "";
        if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("message"))) {
            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("message").split(" ");
            for (String str : personalisationProps) {
                if (str.startsWith("@")) {
                    messageUserProp.add(str);
                } else {
                    plainText += " " + str;
                }
            }

        } else {
            logger.info("No Message was sent");
        }

        if (messageUserProp.size() > 0) {
//            setPersonalisation(whatPageMessageTextBox, messageUserProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationMessage"));
        }
        if (plainText.length() > 0) {
            whatPageMessageTextBox.sendKeys(plainText.trim());
        }
    }

    public void fillDetailsInActionsForAdview() {
        String plainTextIos = "";
        String plainTextAndroid = "";
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //verifying whether Action tab is open or not, if not clicking on it
        js.executeScript("arguments[0].scrollIntoView();", expandMessageBox);
        SeleniumUtils.pause(2);
        if (driver.findElement(By.xpath("//div[@aria-controls='buttonsDiv' and @class='collapsed']")).isDisplayed()) {
            SeleniumUtils.performClick(driver, expandAction);
        }

        if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("iosDeepLink")) {
            List<String> iosActionProperties = new ArrayList<String>();
            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("iosDeepLink").split(" ");
            for (String str : personalisationProps) {
                if (str.startsWith("@")) {
//                    iosActionProp.add(str);
                    iosActionProperties.add(str);
                } else {
                    plainTextIos += " " + str;
                }
            }

            if (iosActionProperties.size() > 0) {
//                setPersonalisation_gaur(whatPageActionIosTextBox, iosActionProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationIOSDeepLinkMessage"));
                setPersonalisationForAll(whatPageActionIosTextBox, iosActionProperties, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationIOSDeepLinkMessage"));
            }
            if (messagePlaneText.size() > 0) {
                whatPageActionIosTextBox.sendKeys(plainTextIos);
            }

        }
        if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("androidDeepLink")) {

            List<String> androidActionProperties = new ArrayList<String>();
            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("iosDeepLink").split(" ");

            for (String str : personalisationProps) {
                if (str.startsWith("@")) {
                    androidActionProperties.add(str);
                } else {
                    plainTextAndroid += " " + str;
                }
            }

            if (androidActionProperties.size() > 0) {
//                setPersonalisation_gaur(whatPageActionAndroidTextBox, androidActionProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationAndroidDeepLinkMessage"));
                setPersonalisationForAll(whatPageActionAndroidTextBox, androidActionProperties, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationAndroidDeepLinkMessage"));
            }
            if (messagePlaneText.size() > 0) {
                whatPageActionAndroidTextBox.sendKeys(plainTextAndroid);
            }
        }

    }

    public boolean checkIfImageUploaded(String section) {
        boolean imageUploaded = false;
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            if (section.equalsIgnoreCase("imageUpload")) {
                if (driver.findElements(By.xpath("//div[@class='canvas']/img")).size() > 0) {
                }
            } else if (section.equalsIgnoreCase("preview")) {
                if (driver.findElements(By.xpath("//div[contains(@class,'phone-preview-content') and contains(@style,'background: url(\"https:')]")).size() > 0) {
                    imageUploaded = true;
                }
            }

        } catch (NoSuchElementException e) {
            logger.info("something went wrong while validating image upload");
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return imageUploaded;
    }

    public void fillDetailsInCustomKVForAdview() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int inboxCount = 1;
        String plainText = "";
        WebElement keyTextBox;
        WebElement valueTextBox;
        SeleniumUtils.pause(2);
        if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("customkv"))) {
            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("customkv").split(" ");
            for (int i = 0; i < personalisationProps.length; i++) {
                js.executeScript("arguments[0].scrollIntoView();", addCustomKV);
                SeleniumUtils.pause(3);
                SeleniumUtils.performClick(driver, addCustomKV);
                keyTextBox = driver.findElement(By.xpath("(//input[@class='input-key'])[" + inboxCount + "]"));
                valueTextBox = driver.findElement(By.xpath("(//div[@id='customKVHeader']//div[@data-type='input'])[" + inboxCount + "]"));
                keyTextBox.sendKeys(personalisationProps[i].split("-")[0]);

//                Set<String> kvUserProp = new HashSet<String>();
                List<String> kvUserProp = new ArrayList<String>();
                String normalString = "";
                String vals = personalisationProps[i].split("-")[1];
                if (vals.startsWith("@")) {
                    if (vals.contains("=")) {
                        kvUserProp.add(vals.split("=")[0]);
                        normalString = " " + vals.split("=")[1];
                    } else {
                        kvUserProp.add(vals);
                    }

                } else {
                    normalString = " " + vals;
                }

                if (kvUserProp.size() > 0) {
//                    setPersonalisation_gaur(valueTextBox, kvUserProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationKv"));
                    setPersonalisationForAll(valueTextBox, kvUserProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationKv"));
                }
                if (normalString.length() > 0) {
                    valueTextBox.sendKeys(normalString.trim());
                }
                inboxCount++;
            }

        } else {
            logger.info("No Custom KV was sent");
        }
    }

    public void fillMediaForAdview() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("upload_image")) ||
                !SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalized_media"))) {

            js.executeScript("arguments[0].scrollIntoView();", expandMedia);
            SeleniumUtils.pause(2);
            //verifying whether Media tab is open or not, if not clicking on it to open
            if (driver.findElement(By.xpath("//div[@aria-controls='imageDiv'and @class='collapsed']")).isDisplayed()) {
                SeleniumUtils.performClick(driver, expandMedia);
            }

            if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("upload_image")) {
                SeleniumUtils.performClick(driver, uploadImageForMedia);
                js.executeScript("arguments[0].scrollIntoView();", imageLoaderForMedia);
                SeleniumUtils.pause(3);
                imageLoaderForMedia.click();
                FileUtility.uploadImage(new File(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("upload_image")));
                SeleniumUtils.performClick(driver, saveMediaBtnForMedia);
                Assert.assertTrue(checkIfImageUploaded("imageUpload"));
            } else if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("personalized_media")) {
                SeleniumUtils.performClick(driver, personalizeForMedia);
                SeleniumUtils.pause(1);
                SeleniumUtils.enterInputText(driver, personalizeMediaInputMedia, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalized_media"));

            }
        }
    }

    public void fillDataForCarousel(int slideCount) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 1; i <= slideCount; i++) {
            if (i > 2) {
                SeleniumUtils.performClick(driver, addSlideButton);
                SeleniumUtils.pause(2);
            } else {
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(String.format(CAROUSEL_SLIDE_LOC, i))));
                SeleniumUtils.pause(2);
            }

            if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "upload_image")) ||
                    !SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalized_media")) && i==1) {

                if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("slide" + i + "upload_image")) {
                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(String.format(CAROUSEL_UPLOADIMG_BUTTON, i))));
                    js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(String.format(CAROUSEL_IMAGELOADER_BUTTON, i))));
                    SeleniumUtils.pause(3);
                    driver.findElement(By.xpath(String.format(CAROUSEL_IMAGELOADER_BUTTON, i))).click();
                    FileUtility.uploadImage(new File(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "upload_image")));
                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(String.format(CAROUSEL_SAVEMEDIA_BUTTON, i))));
                    Assert.assertTrue(checkIfImageUploaded("imageUpload"));
                } else if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("slide" + i + "personalized_media")) {
                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(String.format(CAROUSEL_PERSONALIZE_MEDIA_BUTTON, i))));
                    SeleniumUtils.pause(1);
                    SeleniumUtils.enterInputText(driver, driver.findElement(By.xpath(String.format(CAROUSEL_PERSONALIZE_TEXTBOX, i))), whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalized_media"));

                }
            }

            //title code
            List<String> tittleDetails = new ArrayList<String>();
            String plainText = "";
            if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("slide" + i + "title")) {
                String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "title").split(" ");

                for (String str : personalisationProps) {
                    if (str.startsWith("@")) {
//                        titleUserProp.add(str);
                        tittleDetails.add(str);
                    } else {
                        plainText += " " + str;
                    }
                }

            } else {
                logger.info("No title was sent for slide" + i + "title");
            }

//            if (titleUserProp.size() > 0) {
             if (tittleDetails.size() > 0) {
//              driver.findElement(By.xpath(String.format(CAROUSEL_TITLE_TEXTBOX, i))).clear();
                WebElement titleTextBox=driver.findElement(By.xpath(String.format(CAROUSEL_TITLE_TEXTBOX, i)));
                titleTextBox.sendKeys(Keys.CONTROL,"a");
                titleTextBox.sendKeys(Keys.DELETE);
//                setPersonalisation_gaur(titleTextBox, titleUserProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalisationTitle"));
                setPersonalisationForAll(titleTextBox, tittleDetails, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalisationTitle"));
            }
            if (plainText.length() > 0) {
                driver.findElement(By.xpath(String.format(CAROUSEL_TITLE_TEXTBOX, i))).sendKeys(plainText.trim());
                plainText = "";
            }

            // Message code
            List<String> messageDetails = new ArrayList<String>();
            if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("slide" + i + "message")) {
                String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "message").split(" ");
                for (String str : personalisationProps) {
                    if (str.startsWith("@")) {
//                        messageUserProp.add(str);
                        messageDetails.add(str);
                    } else {
                        plainText += " " + str;
                    }
                }

            } else {
                logger.info("No title was sent for slide" + i + "message");
            }

//            if (messageUserProp.size() > 0) {
            if (messageDetails.size() > 0) {
//                setPersonalisation_gaur(driver.findElement(By.xpath(String.format(CAROUSEL_MESSAGE_TEXTBOX, i))), messageUserProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalisationMessage"));
                setPersonalisationForAll(driver.findElement(By.xpath(String.format(CAROUSEL_MESSAGE_TEXTBOX, i))), messageDetails, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalisationMessage"));
            }
            if (plainText.length() > 0) {
                driver.findElement(By.xpath(String.format(CAROUSEL_MESSAGE_TEXTBOX, i))).sendKeys(plainText.trim());
                plainText = "";
            }

            //action url code
            if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("slide" + i + "iosDeepLink")) {

                List<String> iosActionProperties = new ArrayList<String>();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(String.format(CAROUSEL_IOS_TEXTBOX, i))));
                SeleniumUtils.pause(2);
                String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "iosDeepLink").split(" ");

                for (String str : personalisationProps) {
                    if (str.startsWith("@")) {
//                        iosActionProp.add(str);
                        iosActionProperties.add(str);
                    } else {
                        plainText += " " + str;
                    }
                }

                if (iosActionProperties.size() > 0) {
//                    setPersonalisation_gaur(driver.findElement(By.xpath(String.format(CAROUSEL_IOS_TEXTBOX, i))), iosActionProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalisationIOSDeepLinkMessage"));
                    setPersonalisationForAll(driver.findElement(By.xpath(String.format(CAROUSEL_IOS_TEXTBOX, i))), iosActionProperties, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalisationIOSDeepLinkMessage"));
                }
                if (plainText.length() > 0) {
                    driver.findElement(By.xpath(String.format(CAROUSEL_IOS_TEXTBOX, i))).sendKeys(plainText);
                    plainText = "";
                }

            }

            if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("slide" + i + "androidDeepLink")) {
                List<String> androidActionProperties = new ArrayList<String>();
                js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath(String.format(CAROUSEL_ANDROID_TEXTBOX, i))));
                SeleniumUtils.pause(2);
                String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "androidDeepLink").split(" ");

                for (String str : personalisationProps) {
                    if (str.startsWith("@")) {
//                        androidActionProp.add(str);
                        androidActionProperties.add(str);
                    } else {
                        plainText += " " + str;
                    }
                }

                if (androidActionProperties.size() > 0) {
//                    setPersonalisation_gaur(driver.findElement(By.xpath(String.format(CAROUSEL_ANDROID_TEXTBOX, i))), androidActionProp, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalisationAndroidDeepLinkMessage"));
                    setPersonalisationForAll(driver.findElement(By.xpath(String.format(CAROUSEL_ANDROID_TEXTBOX, i))), androidActionProperties, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("slide" + i + "personalisationAndroidDeepLinkMessage"));
                }
                if (plainText.length() > 0) {
                    driver.findElement(By.xpath(String.format(CAROUSEL_ANDROID_TEXTBOX, i))).sendKeys(plainText);
                    plainText = "";
                }
            }

        }
        if (whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).containsKey("customkv")) {
            if (driver.findElement(By.xpath("//div[@aria-controls='customKVHeader' and @class='collapsed']")).isDisplayed()) {
                SeleniumUtils.performClick(driver, expandCustomKV);
            }
            fillDetailsInCustomKVForAdview();
        }

    }



    /***
     * This method fills the recommendations personalization in the required web element
     * @param element - #WebElement which requires recommendations personalization
     * @param recommendationValues - #List containing the values for recommendation
     *(only keys to be provided ie. Identity ImageURL Name as the values for them are default and not editable)
     */
    public void fillRecommendationPersonalization(WebElement element,List<String> recommendationValues){

        JavascriptExecutor js = (JavascriptExecutor) driver;
        element.sendKeys("@");
        SeleniumUtils.pause(2);
        if(driver.findElements(By.xpath("//div[@id='type_Recommendation']//li/..//li[contains(@data-value,'DND Thai L-Charged')]")).size()==0){

            WebElement recommendation = driver.findElement(By.xpath("//div[@id='type_Recommendation']//span"));
            js.executeScript("arguments[0].scrollIntoView();", recommendation);
            SeleniumUtils.pause(2);
            js.executeScript("arguments[0].click();", recommendation);
            SeleniumUtils.pause(2);
            driver.findElement(By.xpath("//*[@id='recommendationIds_chzn']//a//span")).click();
            SeleniumUtils.pause(2);
            driver.findElement(By.xpath("//li[text()='DND Thai Language Recommendation']")).click();
            SeleniumUtils.pause(2);
            driver.findElement(By.xpath("//div[contains(@class,'footer')]//button[contains(@class,'recommendation')]")).click();

        }

        SeleniumUtils.pause(2);
        js.executeScript("arguments[0].scrollIntoView();", element);
        SeleniumUtils.pause(2);
        js.executeScript("arguments[0].click();", element);
        element.sendKeys(Keys.BACK_SPACE);

        for(String recommendation : recommendationValues){

            SeleniumUtils.pause(2);
            element.sendKeys("@");
            SeleniumUtils.pause(2);

            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.xpath("//div[@id='type_Recommendation']//li[@data-value]")));
            action.perform();
            action.moveToElement(driver.findElement(By.xpath("//div[text()='Select catalog field to map']")));
            action.perform();
            SeleniumUtils.pause(2);
            driver.findElement(By.xpath("//div[@class='cat_data']//li[contains(text(),'"+recommendation+"')]")).click();

        }


    }

    public void fillDetailsInTitleForAll(WebElement... webElements) {
        System.out.println("filling title through new method");
        WebElement webElement = null;
        if(webElements.length > 0){
            webElement=webElements[0];
        }else{
            webElement = driver.findElements(By.xpath("//div[contains(@class,'emoji-wysiwyg-editor')]")).get(0);
        }

        webElement.clear();
        String plainText = "";

        if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("title"))) {
            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("title").split(" ");
            for (String str : personalisationProps) {
                if (str.startsWith("@")) {
                    titleDetailsForAll.add(str);
                } else {
                    plainText += " " + str;
                }
            }

        } else {
            logger.info("No title was sent");
        }

        if (titleDetailsForAll.size() > 0) {
            setPersonalisationForAll(webElement, titleDetailsForAll, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationTitle"));
        }
        if (plainText.length() > 0) {
            webElement.sendKeys(plainText.trim());
        }
    }

    public void fillDetailsInMessageForAll(WebElement... webElements) {
        System.out.println("filling message through new method");
        WebElement webElement = null;
        if(webElements.length > 0){
            webElement=webElements[0];
        }else{
            webElement = driver.findElements(By.xpath("//div[contains(@data-original-title,'Type @ to trigger profile field')]//div[contains(@class,'emoji-wysiwyg-editor look-like-input')]")).get(1);
        }
        webElement.clear();
        String plainText = "";

        if (!SeleniumUtils.isNullOrEmpty(whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("message"))) {
            String[] personalisationProps = whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("message").split(" ");
            for (String str : personalisationProps) {
                if (str.startsWith("@")) {
                    messageDetailsForAll.add(str);
                } else {
                    plainText += " " + str;
                }
            }

        } else {
            logger.info("No Message was sent");
        }

        if (messageDetailsForAll.size() > 0) {
            setPersonalisationForAll(webElement, messageDetailsForAll, whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationMessage"));
//            setPersonalization("message",messageDetails.stream().collect(Collectors.toSet()), whatPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get("personalisationMessage"));
        }
        if (plainText.length() > 0) {
            webElement.sendKeys(plainText.trim());
        }
    }

    public void setPersonalisationForAll(WebElement webElement, List<String> userProp, String userPropVal) {
        System.out.println("set personalization message through new method");
        Actions actions = new Actions(driver);
        Iterator<String> iterator = userProp.iterator();
        WebElement userPropTitleEle=null;
        String item=null;
        String xpath="";
        String xpath1="";
        String xpath2="";


        while (iterator.hasNext()) {

            try {
                item = iterator.next().replace("@", "");
                webElement.sendKeys("@");
                SeleniumUtils.pause(2);

                xpath1="(//div[contains(@id,'atwho-ground-emoji_wzrk')]//li[text()='" + item.trim() + "'])[" + personalisationCount + "]";
                xpath2="//div[@class='atwho-view']//li[text()='" + item.trim() + "'])[" + personalisationCount + "]";

                if(driver.findElements(By.xpath(xpath1)).size()>0){
                    xpath=xpath1;

                }else{
                    xpath=xpath2;
                }
                System.out.println("in try");
                System.out.println(xpath);
                userPropTitleEle = driver.findElement(By.xpath(xpath));
                actions.moveToElement(userPropTitleEle);
                SeleniumUtils.pause(2);
                actions.perform();
                SeleniumUtils.pause(3);
                userPropTitleEle.click();

            }
            catch (Exception e){

                SeleniumUtils.scrollDown(driver);
                SeleniumUtils.pause(2);
                webElement.sendKeys(Keys.BACK_SPACE);
                SeleniumUtils.pause(1);
                webElement.sendKeys("@");
                SeleniumUtils.pause(2);


                xpath1="(//div[contains(@id,'atwho-ground-emoji_wzrk')]//li[text()='" + item.trim() + "'])[" + personalisationCount + "]";
                xpath2="(//div[@class='atwho-view']//li[text()='" + item.trim() + "'])[" + personalisationCount + "]";

                if(driver.findElements(By.xpath(xpath1)).size()>0){
                    xpath=xpath1;

                }else{
                    xpath=xpath2;
                }
                System.out.println("in catch");
                System.out.println(xpath);
                userPropTitleEle = driver.findElement(By.xpath(xpath));
                SeleniumUtils.pause(4);

                actions.moveToElement(userPropTitleEle);
                actions.build();
                SeleniumUtils.pause(4);
                actions.perform();
                SeleniumUtils.pause(4);
                userPropTitleEle.click();

            }
            SeleniumUtils.pause(1);
            WebElement title;
            try {
                title = driver.findElement(By.xpath("(//input[@class='default_value_text_box'])[" + (propCounter) + "]"));
            } catch (NoSuchElementException nse) {
                SeleniumUtils.pause(2);
                title = driver.findElement(By.xpath("(//input[@class='default_value_text_box'])[" + (propCounter) + "]"));
            }

            title.click();
            title.sendKeys(userPropVal);
            SeleniumUtils.pause(1);
            propCounter++;
        }
        personalisationCount++;
    }
}


