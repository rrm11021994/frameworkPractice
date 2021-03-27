package com.clevertap.ui.pages.campaigns_page;

import com.clevertap.ui.pages.widget.*;
import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhoPage extends LoadYamlFile {
    private static final String RECURRING = "recurring";
    private static final String MULTIPLE_DATES = "multiple dates";
    private static final String SINGLE_ACTION = "single action";
    private static final String INACTION_WITHIN_TIME = "inaction within time";
    private static final String ON_A_DATE_TIME = "on a date/time";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK = "//*[@class='targetDeviceList']//label[@for='android_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK = "//*[@class='targetDeviceList']//label[@for='ios_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK1 = "//*[@class='targetDeviceList']//label[@for='android_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK1 = "//*[@class='targetDeviceList']//label[@for='ios_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK2 = "//*[@class='targetDeviceList']//label[@for='android_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK2 = "//*[@class='targetDeviceList']//label[@for='ios_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK1 = "//*[@class='targetDeviceList']//label[@for='mobile_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK1 = "//*[@class='targetDeviceList']//label[@for='tv_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK2 = "//*[@class='targetDeviceList']//label[@for='mobile_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2 = "//*[@class='targetDeviceList']//label[@for='tablet_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK2 = "//*[@class='targetDeviceList']//label[@for='tv_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK3 = "//*[@class='targetDeviceList']//label[@for='mobile_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK3 = "//*[@class='targetDeviceList']//label[@for='tablet_chk']/span";
    private static final String CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK3 = "//*[@class='targetDeviceList']//label[@for='tv_chk']/span";
    private static final String ONE_TIME = "one time";
    private static final String SAVED_BOOKMARKS_SEGMENTS = "saved bookmarks(segments)";
    private static final String CREATE_AN_AD_HOC_SEGMENT = "+ create an ad-hoc segment";
    private static final String SAVED_SEGMENTS = "saved segments";
    private static final String DATA_CT_TAB_SAVED_BOOKMARKS = "//*[@data-ct-tab='saved_bookmarks']";
    private static final String INLINE_WHO_CREATE_ADHOC = "inlineWhoCreateAdhoc";
    private static final String CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_ANDROID = "//*[@class='custom-control-label']//span[text()='Android']";
    private static final String CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_I_OS = "//*[@class='custom-control-label']//span[text()='iOS']";
    private static final String CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET = "//*[@class='custom-control-label']//span[text()='Tablet']";
    private static final String CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID = "//*[@class='custom-control-label']//span[text()='TV (Android)']";
    private static final String CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE = "//*[@class='custom-control-label']//span[text()='Mobile']";
    private static final String DATA_CT_TAB_SAVED_SEGMENTS = "//*[@data-ct-tab='saved_segments']";
    private static final String ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_1 = "(//*[@id='segment-table']//tbody//td[contains(@class,'segment-name-col')]//a)[1]";
    private static final String EVENT_PROPERTY = "Event property";
    private static final String APP_LAUNCHED = "App Launched";
    private static final String CHARGED = "Charged";
    private static final String INLINE_WHO_ALL_USERS = "inlineWhoAllUsers";
    public static final String NO_SAFTEY_CHECK_REQUIRED_ALL_USERS_SHOULD_QUALIFY = "No saftey check required. All users should qualify!!!";
    public static final String MOBILE = "mobile";
    public static final String TABLET = "tablet";
    public static final String TV = "tv";
    public static final String ALL_DEVICE_TYPE = "all device type";
    public static final String UNCHECKED_ALL_DEVICE_TYPE = "unchecked all device type";
    public static final String TV_CHK = "tv_chk";
    public static final String TABLET_CHK = "tablet_chk";
    public static final String MOBILE_CHK = "mobile_chk";
    public static final String DO_NOTHING_AS_BYDEFAULT_ALL_DEVICES_ARE_SELECTED = "Do nothing as bydefault all devices are selected!!!!";
    public static final String UNCHECKED_ALL_OS_TYPE = "unchecked all os type";
    public static final String PLEASE_SELECT_ATLEAST_ONE_OF_OS = "please select atleast one of os";
    public static final String UNDER_DEVICE_OS_TYPE_BLOCK = "Under device OS type block!!!!";
    public static final String IOS_CHK = "ios_chk";
    public static final String ANDROID_CHK = "android_chk";
    public static final String PLEASE_SELECT_ATLEAST_ONE_DEVICE = "please select atleast one device";
    public static final String ANDROID = "android";
    public static final String CHANGE_SAVED_SEGMENT_QUERY = "change saved segment query";
    public static final String ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_TEXT = "//*[@id='segment-table']//tbody//td[contains(@class,'segment-name-col')]//a[text()='";
    public static final String FILTER_STREAM = "filterStream";
    public static final String WHO_DID_NOT_EVENT = "whoDidNotEvent";
    public static final String WHATSAPP_ONE_TIME = "whatsapp+one time";
    public static final String WHATSAPP_INACTION_WITHIN_TIME = "whatsapp+inaction within time";
    public static final String WHATSAPP_ON_A_DATE_TIME = "whatsapp+on a date/time";
    public static final String WHATSAPP = "whatsapp";
    public static final String APP_INBOX = "app inbox";
    public static final String MOBILE_PUSH = "mobile push";
    public static final String EMAIL = "email";
    public static final String AUDIENCES = "audiences";
    public static final String WEBHOOKS = "webhooks";
    public static final String GOOGLE_ADS = "google ads";
    public static final String WHO_DID_EVENT = "whoDidEvent";
    public static final String MOBILE_PUSH_ONE_TIME = "mobile push+one time";
    public static final String AS_SOON_AS_USER_DOES = "AsSoonAsUserDoes";
    public static final String WHO_DID_OCCURENCE = "whoDidOccurence";
    public static final String EMAIL_MULTIPLE_DATES = "email+multiple dates";
    public static final String EMAIL_RECURRING = "email+recurring";
    public static final String EMAIL_INACTION_WITHIN_TIME = "email+inaction within time";
    public static final String WHATSAPP_SINGLE_ACTION = "whatsapp+single action";
    public static final String WEB_PUSH = "web push";
    public static final String WEB_POP_UP = "web pop-up";
    public static final String WEB_EXIT_INTENT = "web exit intent";
    public static final String MOBILE_PUSH_MULTIPLE_DATES = "mobile push+multiple dates";
    public static final String MOBILE_PUSH_RECURRING = "mobile push+recurring";
    public static final String SMS_ONE_TIME = "sms+one time";
    public static final String APP_INBOX_ONE_TIME = "app inbox+one time";
    public static final String APP_INBOX_MULTIPLE_DATES = "app inbox+multiple dates";
    public static final String APP_INBOX_RECURRING = "app inbox+recurring";
    public static final String EMAIL_ONE_TIME = "email+one time";
    public static final String MOBILE_PUSH_INACTION_WITHIN_TIME = "mobile push+inaction within time";
    public static final String MOBILE_PUSH_ON_A_DATE_TIME = "mobile push+on a date/time";
    public static final String APP_INBOX_SINGLE_ACTION = "app inbox+single action";
    public static final String APP_INBOX_INACTION_WITHIN_TIME = "app inbox+inaction within time";
    public static final String EMAIL_SINGLE_ACTION = "email+single action";
    public static final String MOBILE_PUSH_SINGLE_ACTION = "mobile push+single action";
    public static final String MOBILE_INAPP = "mobile inapp";
    private WebDriver driver;
    private SweetAlert sweetAlert;
    private SegmentWidget segmentWidget;
    private DidWidget didWidget;
    public DidNotWidget didNotWidget;
    private CommonPropertyProfileWidget commonPropertyProfileWidget;
    public ActionEventWidget actionEventWidget;
    public List<String> userWhoLikeType = new ArrayList<>();
    public List<String> userWhoLikeEvents = new ArrayList<>();
    public List<String> userWhoDid = new ArrayList<>();
    public List<String> userWhoDidNotDo = new ArrayList<>();
    public List<String> liveUserWhoDidEvent = new ArrayList<>();
    public List<String> liveUserWhoDidOccurences = new ArrayList<>();
    public List<String> pbsUserWhoDidNotDo = new ArrayList<>();
    public List<String> changeUserWhoLikeType = new ArrayList<>();
    public List<String> changeUserWhoLikeEvents = new ArrayList<>();
    public List<String> changeUserWhoDid = new ArrayList<>();
    public List<String> changeUserWhoDidNotDo = new ArrayList<>();
    public List<String> changeLiveUserWhoDidEvent = new ArrayList<>();
    public List<String> changeLiveUserWhoDidOccurences = new ArrayList<>();
    public List<String> changePbsUserWhoDidNotDo = new ArrayList<>();

    private static final String ONLY_X_USERS_OVERALL = "only x users overall";
    private static final String LIMIT_USERS = "limit users";
    private static final String ALL_USERS = "all users";
    private static Logger logger = Logger.getLogger(WhoPage.class);

    private int noSavedSegmentscounter = 0;

    //PBS campaign elements
    @FindBy(xpath = "//label[@for='oneTimeUserQualifyId']")
    private WebElement oneTimeAllUsersRadio;
    @FindBy(xpath = "//label[@for='oneTimeCampaignQualifyId']")
    private WebElement oneTimeAllUsersSafetyCheck;
    @FindBy(id = "oneTimeEstimatelimitnumId")
    private WebElement oneTimeAllUserSafetyChecklimit;
    @FindBy(xpath = "//div[@class='flex-row']/label[@for='oneTimeCampaignReachLimitId']")
    private WebElement oneTimeLimitToUsersRadio;
    @FindBy(id = "oneTimeCampaignLimitNum")
    private WebElement oneTImeLimitToUsersText;

    //recurring elements
    @FindBy(xpath = "//label[@for='recurringUserQualifyId']")
    private WebElement recAllUsersRadio;
    @FindBy(xpath = "//label[@for='recurringCampaignQualifyId']")
    private WebElement recAllUsersSafetyCheck;
    @FindBy(id = "recurringEstimatelimitNumId")
    private WebElement recAllUsersSafetyTextBox;
    @FindBy(xpath = "//label[@for='recurrringCampaignReachLimitedId']")
    private WebElement recLimitToUsersRadio;
    @FindBy(id = "recurringLimitNumId")
    private WebElement recLimitUsersPerDayText;
    @FindBy(xpath = "//label[@for='recurringTotalCampaignLimitId']")
    private WebElement recCapToUserOverallCheck;
    @FindBy(id = "recurringtotalLimitNum")
    private WebElement recCapToUserOverallText;

    //Trigger campaign elements:
    @FindBy(xpath = "//label[@for='triggerUserQualifyId']")
    private WebElement trigAllUsersSelectRadio;
    @FindBy(xpath = "//label[@for='triggerCampaignReachLimitedPerDayId']")
    private WebElement trigOnlyUsersPerDayRadio;
    @FindBy(id = "triggerlimitNumPerDay")
    private WebElement trigOnlyUsersPerDayText;
    @FindBy(xpath = "//label[@for='triggerTotalCampaignLimitId']")
    private WebElement trigCapToUserOverallCheck;
    @FindBy(id = "triggerTotalCampaignLimitNum")
    private WebElement trigrCapToUsersOverallText;
    @FindBy(xpath = "//label[@for='triggerCampaignReachLimitTotalId']")
    private WebElement trigUsersOverAllRadio;
    @FindBy(id = "triggerLimitOverallNum")
    private WebElement trigUsersOverAllText;
    @FindBy(id = "inputLoader")
    private WebElement doesNotDoWithinTime;
    @FindBy(id = "btn_top_nav_continue")
    private WebElement continueButton;
    @FindBy(xpath = "//*[@id='segment-table']//tbody//td[contains(@class,'segment-name-col')]//a")
    private List<WebElement> allSavedSegments;
    @FindBy(xpath = "//*[@data-step-name='who_list']/a[text()='Select']")
    private WebElement whoPageSelectSegment;
    @FindBy(xpath = "//*[@for='filterStream']")
    private WebElement filterPastUserBehaviour;


    public Map<String, String> getWhoSummaryItems() {
        Map<String, String> whoSummaryFieldsMap = new HashMap<>();
        int totalWhoSummarySpans = driver.findElements(By.xpath("//*[@id='summaryWho']//div[contains(@class,'overview-item')]//span")).size();
        for (int i = 1; i <= totalWhoSummarySpans; i += 2) {
            whoSummaryFieldsMap.put(driver.findElement(By.xpath("(//*[@id='summaryWho']//div[contains(@class,'overview-item')]//span)[" + i + "]")).getText(), driver.findElement(By.xpath("(//*[@id='summaryWho']//div[contains(@class,'overview-item')]//span)[" + (i + 1) + "]")).getText());
        }
        return whoSummaryFieldsMap;

    }


    public void restrictDeliveryToDeviceAndOSType() {

        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case "mobile push":
            case "mobile in-app":
            case "email":
            case "web push":
            case "web pop-up":
            case "web exit intent":
            case "audiences":
            case "google ads":
            case "webhooks":
            case  "native display":

                switch (campaignMeta.getWho().getDevice_os().trim().toLowerCase()) {
                    case "ios":

                        if (driver.findElement(By.id(ANDROID_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK)));
                        }

                        if (!driver.findElement(By.id(IOS_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK)));
                        }
                        break;
                    case ANDROID:
                        if (driver.findElement(By.id(IOS_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK)));
                        }

                        if (!driver.findElement(By.id(ANDROID_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK)));
                        }

                        break;
                    case UNCHECKED_ALL_OS_TYPE:
                        /*Written below switch case to avoid bu #. Bugs exists with live action campaign only
                         * https://wizrocket.atlassian.net/browse/CLEVER-1797*/
                        switch (campaignMeta.getType().trim().toLowerCase()) {
                            case ONE_TIME:
                            case MULTIPLE_DATES:
                            case RECURRING:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK1)));
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK1)));
                                SeleniumUtils.pause(2);
                                SeleniumUtils.performClick(driver, continueButton);
                                boolean errorFoundForOS = sweetAlert.getSweetAlertErrorSignal();
                                Assert.assertTrue(errorFoundForOS, PLEASE_SELECT_ATLEAST_ONE_OF_OS);
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK2)));
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK2)));
                                SeleniumUtils.pause(2);
                                break;
                            case SINGLE_ACTION:
                            case INACTION_WITHIN_TIME:
                            case ON_A_DATE_TIME:
                                break;
                            default:
                        }


                        break;
                    default:
                        logger.info(UNDER_DEVICE_OS_TYPE_BLOCK);
                }

                switch (campaignMeta.getWho().getDevice_type().trim().toLowerCase()) {
                    case MOBILE:
                        if (driver.findElement(By.id(TV_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK1)));
                        }
                        if (driver.findElement(By.id(TABLET_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2)));
                        }
                        if (!driver.findElement(By.id(MOBILE_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK1)));
                        }
                        break;
                    case TABLET:
                        if (driver.findElement(By.id(TV_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK1)));
                        }
                        if (!driver.findElement(By.id(TABLET_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2)));
                        }
                        if (driver.findElement(By.id(MOBILE_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK1)));
                        }
                        break;
                    case TV:
                        if (!driver.findElement(By.id(TV_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK1)));
                        }
                        if (driver.findElement(By.id(TABLET_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2)));
                        }
                        if (driver.findElement(By.id(MOBILE_CHK)).isSelected()) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK1)));
                        }
                        break;
                    case ALL_DEVICE_TYPE:
                        logger.info(DO_NOTHING_AS_BYDEFAULT_ALL_DEVICES_ARE_SELECTED);
                        break;
                    case UNCHECKED_ALL_DEVICE_TYPE:
                        switch (campaignMeta.getType().trim().toLowerCase()) {
                            case ONE_TIME:
                            case MULTIPLE_DATES:
                            case RECURRING:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK2)));
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2)));
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK2)));
                                SeleniumUtils.performClick(driver, continueButton);
                                boolean errorFoundForDevice = sweetAlert.getSweetAlertErrorSignal();
                                Assert.assertTrue(errorFoundForDevice, PLEASE_SELECT_ATLEAST_ONE_DEVICE);
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK3)));
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK3)));
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK3)));
                                break;
                            case SINGLE_ACTION:
                            case INACTION_WITHIN_TIME:
                            case ON_A_DATE_TIME:
                                break;
                            default:
                        }

                    default:
                }
                break;
            case "app inbox":

                SeleniumUtils.performClick(driver, continueButton);

                switch (campaignMeta.getWho().getDevice_os().trim().toLowerCase()) {
                    case "ios":
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_ANDROID)));
                        break;
                    case ANDROID:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_I_OS)));
                        break;
                    case UNCHECKED_ALL_OS_TYPE:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_ANDROID)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_I_OS)));
                        SeleniumUtils.pause(2);
                        SeleniumUtils.performClick(driver, continueButton);
                        boolean errorFoundForOS = sweetAlert.getSweetAlertErrorSignal();
                        Assert.assertTrue(errorFoundForOS, PLEASE_SELECT_ATLEAST_ONE_OF_OS);
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_ANDROID)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_I_OS)));
                        SeleniumUtils.pause(2);

                        break;
                    default:
                        logger.info(UNDER_DEVICE_OS_TYPE_BLOCK);
                }

                switch (campaignMeta.getWho().getDevice_type().trim().toLowerCase()) {
                    case MOBILE:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID)));
                        break;
                    case TABLET:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID)));
                        break;
                    case "tv android":
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET)));
                        break;
                    case ALL_DEVICE_TYPE:
                        logger.info(DO_NOTHING_AS_BYDEFAULT_ALL_DEVICES_ARE_SELECTED);
                        break;
                    case UNCHECKED_ALL_DEVICE_TYPE:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID)));
                        SeleniumUtils.performClick(driver, continueButton);
                        boolean errorFoundForDevice = sweetAlert.getSweetAlertErrorSignal();
                        Assert.assertTrue(errorFoundForDevice, PLEASE_SELECT_ATLEAST_ONE_DEVICE);
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID)));
                        break;

                    default:
                }


                break;

            case "sms":
            case "whatsapp":
                break;
            default:
        }


    }

    public void changeRestrictDeliveryToDeviceAndOSType() {

        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case "mobile push":
            case "mobile in-app":
            case "web push":
            case "web pop-up":
            case "web exit intent":

            case "audiences":
            case "google ads":
            case "webhooks":

                switch (campaignMeta.getWho().getChange_os_type().trim().toLowerCase()) {
                    case "ios":
                        SeleniumUtils.scrollUp(driver);
                        if (driver.findElement(By.id(ANDROID_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK)).click();
                        }

                        if (!driver.findElement(By.id(IOS_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK)).click();
                        }
                        break;
                    case ANDROID:
                        SeleniumUtils.scrollUp(driver);
                        if (driver.findElement(By.id(IOS_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK)).click();
                        }

                        if (!driver.findElement(By.id(ANDROID_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK)).click();
                        }
                        break;
                    case UNCHECKED_ALL_OS_TYPE:
                        SeleniumUtils.scrollUp(driver);
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK1)).click();
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK1)).click();
                        SeleniumUtils.pause(2);
                        SeleniumUtils.performClick(driver, continueButton);
                        boolean errorFoundForOS = sweetAlert.getSweetAlertErrorSignal();
                        Assert.assertTrue(errorFoundForOS, PLEASE_SELECT_ATLEAST_ONE_OF_OS);
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_ANDROID_CHK2)).click();
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_IOS_CHK2)).click();
                        SeleniumUtils.pause(2);

                        break;
                    default:
                        logger.info(UNDER_DEVICE_OS_TYPE_BLOCK);
                }

                switch (campaignMeta.getWho().getChange_device_type().trim().toLowerCase()) {
                    case MOBILE:
                        SeleniumUtils.scrollUp(driver);
                        if (driver.findElement(By.id(TV_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK1)).click();
                        }
                        if (driver.findElement(By.id(TABLET_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2)).click();
                        }
                        if (!driver.findElement(By.id(MOBILE_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK1)).click();
                        }


                        break;

                    case TABLET:
                        SeleniumUtils.scrollUp(driver);
                        if (driver.findElement(By.id(TV_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK1)).click();
                        }
                        if (!driver.findElement(By.id(TABLET_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2)).click();
                        }
                        if (driver.findElement(By.id(MOBILE_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK1)).click();
                        }
                        break;
                    case TV:
                        SeleniumUtils.scrollUp(driver);
                        if (!driver.findElement(By.id(TV_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK1)).click();
                        }
                        if (driver.findElement(By.id(TABLET_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2)).click();
                        }
                        if (driver.findElement(By.id(MOBILE_CHK)).isSelected()) {
                            driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK1)).click();
                        }
                        break;
                    case ALL_DEVICE_TYPE:
                        logger.info(DO_NOTHING_AS_BYDEFAULT_ALL_DEVICES_ARE_SELECTED);
                        break;
                    case UNCHECKED_ALL_DEVICE_TYPE:
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK2)).click();
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK2)).click();
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK2)).click();
                        SeleniumUtils.performClick(driver, continueButton);
                        boolean errorFoundForDevice = sweetAlert.getSweetAlertErrorSignal();
                        Assert.assertTrue(errorFoundForDevice, PLEASE_SELECT_ATLEAST_ONE_DEVICE);
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_MOBILE_CHK3)).click();
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TABLET_CHK3)).click();
                        driver.findElement(By.xpath(CLASS_TARGET_DEVICE_LIST_LABEL_FOR_TV_CHK3)).click();
                        break;

                    default:
                }
                break;
            case "app inbox":

                SeleniumUtils.performClick(driver, continueButton);

                switch (campaignMeta.getWho().getChange_os_type().trim().toLowerCase()) {
                    case "ios":
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_ANDROID)));
                        break;
                    case ANDROID:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_I_OS)));
                        break;
                    case UNCHECKED_ALL_OS_TYPE:
                        driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_ANDROID)).click();
                        driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_I_OS)).click();
                        SeleniumUtils.pause(2);
                        SeleniumUtils.performClick(driver, continueButton);
                        boolean errorFoundForOS = sweetAlert.getSweetAlertErrorSignal();
                        Assert.assertTrue(errorFoundForOS, PLEASE_SELECT_ATLEAST_ONE_OF_OS);
                        driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_ANDROID)).click();
                        driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_I_OS)).click();
                        SeleniumUtils.pause(2);

                        break;
                    default:
                        logger.info(UNDER_DEVICE_OS_TYPE_BLOCK);
                }

                switch (campaignMeta.getWho().getChange_device_type().trim().toLowerCase()) {
                    case MOBILE:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID)));
                        break;
                    case TABLET:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID)));
                        break;
                    case "tv android":
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET)));
                        break;
                    case ALL_DEVICE_TYPE:
                        logger.info(DO_NOTHING_AS_BYDEFAULT_ALL_DEVICES_ARE_SELECTED);
                        break;
                    case UNCHECKED_ALL_DEVICE_TYPE:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID)));
                        SeleniumUtils.performClick(driver, continueButton);
                        boolean errorFoundForDevice = sweetAlert.getSweetAlertErrorSignal();
                        Assert.assertTrue(errorFoundForDevice, PLEASE_SELECT_ATLEAST_ONE_DEVICE);
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TABLET)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_MOBILE)));
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(CLASS_CUSTOM_CONTROL_LABEL_SPAN_TEXT_TV_ANDROID)));
                        break;

                    default:
                }


                break;

            case "sms":
            case "whatsapp":
            case "email":
                break;
            default:
        }


    }

    public WhoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        sweetAlert = new SweetAlert(driver);
        segmentWidget = new SegmentWidget(driver);
        didWidget = new DidWidget(driver);
        didNotWidget = new DidNotWidget((driver));
        actionEventWidget = new ActionEventWidget(driver);
        commonPropertyProfileWidget = new CommonPropertyProfileWidget(driver);

    }

    public void setWhoPage() throws InterruptedException {
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case CampaignsHomePage.MOBILE_PUSH:
            case CampaignsHomePage.SMS:
            case CampaignsHomePage.EMAIL:
            case CampaignsHomePage.WEB_PUSH:
            case CampaignsHomePage.WEB_POPUP:
            case CampaignsHomePage.WEB_EXIT_INTENT:
            case CampaignsHomePage.WHATSAPP:
            case CampaignsHomePage.FB_AUDIENCES:
            case CampaignsHomePage.GOOGLE_ADS:
            case CampaignsHomePage.WEBHOOKS:
                switch (campaignMeta.getType().trim().toLowerCase()) {
                    case ONE_TIME:
                    case MULTIPLE_DATES:
                        switch (campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                String dynamicXpath = ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_TEXT + campaignMeta.getCsv_key() + "']";
                                boolean isVisible = false;
                                do {
                                    if (SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver) != null) {
                                        SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                        isVisible = true;
                                    } else {
                                        /*below 2 lines of code is for scroll into view*/
                                        JavascriptExecutor js = (JavascriptExecutor) driver;
                                        js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + "px\" })");
                                        isVisible = false;
                                    }
                                } while (isVisible = false);
                                segmentWidget.verifySegmentQueryAddLikeDidEventsFilters(segmentQueryCSVMap);
                                didWidget.verifySegmentQueryWhoDidFilters(segmentQueryCSVMap);
                                didNotWidget.verifySegmentQueryUserWhoDidNotFilters(segmentQueryCSVMap);
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CREATE_AN_AD_HOC_SEGMENT:
                                SeleniumUtils.pause(3);
                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                                SeleniumUtils.performClick(driver, element);
                                SeleniumUtils.pause(2);

                                /*Set up user who likes query*/

//                                userWhoLikeType.add(campaignMeta.getWho().getUser_who_like_prop());
//                                userWhoLikeEvents.add(campaignMeta.getWho().getUser_who_like_event());
//                                if (!userWhoLikeEvents.get(0).isEmpty()) {
//                                    segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents);
//                                }
//
//                                /*Set up user who did query*/
//                                userWhoDid.add(campaignMeta.getWho().getUser_who_did());
//                                if (!userWhoDid.get(0).isEmpty()) {
//                                    logger.info("user who did : " + userWhoDid);
//                                    didWidget.enterUserWhoDidFilters(userWhoDid);
//                                }
//
//                                /*Set up user who did not do query*/
//                                userWhoDidNotDo.add(campaignMeta.getWho().getUser_who_did_not_do());
//                                if (!userWhoDidNotDo.get(0).isEmpty()) {
//                                    didNotWidget.enterUserWhoDidNotFilters(userWhoDidNotDo);
//                                }


                                setWhoQueryFromCSV();


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                                logger.info("if no info is given about segment selection then by default it will move ahead");
                                SeleniumUtils.performClick(driver, continueButton);
                                break;
                        }
                        setUpCampaignReach();
                        switch (campaignMeta.getChannel().trim().toLowerCase()) {
                            case "mobile push":
                            case "app inbox":
                            case "web push":
                            case "web pop-up":
                            case "web exit intent":
                            case "audiences":
                            case "google ads":
                            case "webhooks":
                                restrictDeliveryToDeviceAndOSType();
                                break;
                            case "sms":
                            case "email":
                            case "whatsapp":
                                //do nothing
                                break;


                        }


                        break;
                    case RECURRING:
                        switch (campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                String dynamicXpath = ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_TEXT + campaignMeta.getCsv_key() + "']";
                                if (SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver) != null) {
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                } else {
                                    /*below 2 lines of code is for scroll into view*/
                                    JavascriptExecutor js = (JavascriptExecutor) driver;
                                    js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + "px\" })");
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                }
                                segmentWidget.verifySegmentQueryAddLikeDidEventsFilters(segmentQueryCSVMap);
                                didWidget.verifySegmentQueryWhoDidFilters(segmentQueryCSVMap);
                                didNotWidget.verifySegmentQueryUserWhoDidNotFilters(segmentQueryCSVMap);
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CREATE_AN_AD_HOC_SEGMENT:
                                SeleniumUtils.pause(3);
                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                                SeleniumUtils.performClick(driver, element);
                                SeleniumUtils.pause(2);
                                setWhoQueryFromCSV();
//                                List<String> userWhoLikeType = new ArrayList<>();
//                                userWhoLikeType.add(EVENT_PROPERTY);
//
//                                List<String> userWhoLikeEvents = new ArrayList<>();
//                                userWhoLikeEvents.add(APP_LAUNCHED);
//
//                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents);
//
//
//                                List<String> userWhoDid = new ArrayList<>();
//                                userWhoDid.add(CHARGED);
//
//
//                                didWidget.enterUserWhoDidFilters(userWhoDid);


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                                SeleniumUtils.performClick(driver, continueButton);
                        }
                        setUpCampaignReachPBSRecurring();
                        restrictDeliveryToDeviceAndOSType();

                        break;
                    case SINGLE_ACTION:
                    case INACTION_WITHIN_TIME:
                    case ON_A_DATE_TIME:
                        switch (campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                String dynamicXpath = ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_TEXT + campaignMeta.getCsv_key() + "']";
                                if (SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver) != null) {
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                } else {
                                    /*below 2 lines of code is for scroll into view*/
                                    JavascriptExecutor js = (JavascriptExecutor) driver;
                                    js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + "px\" })");
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                }
                                segmentWidget.verifySegmentQueryAddLikeDidEventsFilters(segmentQueryCSVMap);
                                didWidget.verifySegmentQueryWhoDidFilters(segmentQueryCSVMap);
                                didNotWidget.verifySegmentQueryUserWhoDidNotFilters(segmentQueryCSVMap);
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CREATE_AN_AD_HOC_SEGMENT:
                                switch (campaignMeta.getType().trim().toLowerCase()) {
                                    case "one time":
                                    case "multiple dates":
                                    case "recurring":
                                        SeleniumUtils.pause(3);
                                        WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                                        SeleniumUtils.performClick(driver, element);
                                        SeleniumUtils.pause(2);
                                        List<String> userWhoLikeType = new ArrayList<>();
                                        userWhoLikeType.add(EVENT_PROPERTY);

                                        List<String> userWhoLikeEvents = new ArrayList<>();
                                        userWhoLikeEvents.add(APP_LAUNCHED);

                                        segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents, segmentQueryCSVMap);


                                        List<String> userWhoDid = new ArrayList<>();
                                        userWhoDid.add(CHARGED);


                                        didWidget.enterUserWhoDidFilters(userWhoDid, segmentQueryCSVMap);
                                        break;
                                    case "single action":
                                    case "inaction within time":
                                    case "on a date/time":
                                        SeleniumUtils.pause(3);
                                        SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC)));
                                        SeleniumUtils.pause(2);
                                        setWhoQueryFromCSV();
//                                        actionEventWidget.selectEvent(campaignMeta.getWho().getUser_who_like_event());
//                                        if (!driver.findElement(By.id("filterStream")).isSelected()) {
//                                            SeleniumUtils.performClick(driver, filterPastUserBehaviour);
//                                        }
//                                        liveUserWhoDidEvent.add("App Uninstalled");
//                                        liveUserWhoDidOccurences.add("Last Time");
//                                        pbsUserWhoDidNotDo.add("Charged");
//                                        didWidget.enterLiveActionUserWhoDidFilters(liveUserWhoDidEvent, liveUserWhoDidOccurences);
//                                        didNotWidget.enterLiveActionUserWhoDidNotFilters(pbsUserWhoDidNotDo);

                                        break;
                                }


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                        }

                        setUpCampaignReachLiveUser();
                        restrictDeliveryToDeviceAndOSType();

                        break;
                    default:
                }
                break;
            case CampaignsHomePage.MOBILE_INAPP:
            case CampaignsHomePage.NATIVEDISPLAY:
                switch (campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()) {
                    case SAVED_SEGMENTS:
                        SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                        String dynamicXpath = ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_TEXT + campaignMeta.getCsv_key() + "']";
                        if (SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver) != null) {
                            SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                        } else {
                            /*below 2 lines of code is for scroll into view*/
                            JavascriptExecutor js = (JavascriptExecutor) driver;
                            js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + "px\" })");
                            SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                        }
                        segmentWidget.verifySegmentQueryAddLikeDidEventsFilters(segmentQueryCSVMap);
                        didWidget.verifySegmentQueryWhoDidFilters(segmentQueryCSVMap);
                        didNotWidget.verifySegmentQueryUserWhoDidNotFilters(segmentQueryCSVMap);
                        break;
                    case CREATE_AN_AD_HOC_SEGMENT:
                        SeleniumUtils.pause(3);
                        WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                        SeleniumUtils.performClick(driver, element);
                        SeleniumUtils.pause(2);


                        //*[@id='evtSelection1']/..//a/following-sibling::div//li[text()='Added To Cart']

//                        driver.findElement(By.xpath("//*[@id='evtSelection1']/..//a")).click();
//                        SeleniumUtils.pause(1);
//                        driver.findElement(By.xpath("//*[@id='evtSelection1']/..//a/following-sibling::div//li[text()='Added To Cart']")).click();
                        actionEventWidget.selectEvent(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("AsSoonAsUserDoes"));
                        if (!driver.findElement(By.id("filterStream")).isSelected()) {
                            SeleniumUtils.performClick(driver, filterPastUserBehaviour);
                        }
                        if(!SeleniumUtils.isNullOrEmpty(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent"))){
                            liveUserWhoDidEvent.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent"));
                        }
                        if(!SeleniumUtils.isNullOrEmpty(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidOccurence"))){
                            liveUserWhoDidOccurences.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidOccurence"));
                        }
                        if(!SeleniumUtils.isNullOrEmpty(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"))){
                            pbsUserWhoDidNotDo.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"));
                        }

                        if(liveUserWhoDidEvent.size()>0 && liveUserWhoDidOccurences.size()>0) {
                            didWidget.enterLiveActionUserWhoDidFilters(liveUserWhoDidEvent, liveUserWhoDidOccurences);
                        }
                        if(pbsUserWhoDidNotDo.size()>0) {
                            didNotWidget.enterLiveActionUserWhoDidNotFilters(pbsUserWhoDidNotDo, segmentQueryCSVMap);
                        }
                        break;
                    default:
                        SeleniumUtils.performClick(driver, continueButton);
                }
                break;

            case CampaignsHomePage.APP_INBOX:
                switch (campaignMeta.getType().trim().toLowerCase()) {
                    case ONE_TIME:
                    case MULTIPLE_DATES:
                        switch (campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                String dynamicXpath = ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_TEXT + campaignMeta.getCsv_key() + "']";
                                if (SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver) != null) {
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                } else {
                                    /*below 2 lines of code is for scroll into view*/
                                    JavascriptExecutor js = (JavascriptExecutor) driver;
                                    js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + "px\" })");
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                }
                                segmentWidget.verifySegmentQueryAddLikeDidEventsFilters(segmentQueryCSVMap);
                                didWidget.verifySegmentQueryWhoDidFilters(segmentQueryCSVMap);
                                didNotWidget.verifySegmentQueryUserWhoDidNotFilters(segmentQueryCSVMap);
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CREATE_AN_AD_HOC_SEGMENT:
                                SeleniumUtils.pause(3);
                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                                SeleniumUtils.performClick(driver, element);
//                                SeleniumUtils.pause(2);
//                                List<String> userWhoLikeType = new ArrayList<>();
//                                userWhoLikeType.add(EVENT_PROPERTY);
//
//                                List<String> userWhoLikeEvents = new ArrayList<>();
//                                userWhoLikeEvents.add(APP_LAUNCHED);
//
//                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents,segmentQueryCSVMap);
//
//
//                                List<String> userWhoDid = new ArrayList<>();
//                                userWhoDid.add(CHARGED);
//
//
//                                didWidget.enterUserWhoDidFilters(userWhoDid,segmentQueryCSVMap);

                                setWhoQueryFromCSV();


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                        }
                        setUpCampaignReach();
                        restrictDeliveryToDeviceAndOSType();

                        break;
                    case RECURRING:
                        switch (campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                String dynamicXpath = ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_TEXT + campaignMeta.getCsv_key() + "']";
                                if (SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver) != null) {
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                } else {
                                    /*below 2 lines of code is for scroll into view*/
                                    JavascriptExecutor js = (JavascriptExecutor) driver;
                                    js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + "px\" })");
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                }
                                segmentWidget.verifySegmentQueryAddLikeDidEventsFilters(segmentQueryCSVMap);
                                didWidget.verifySegmentQueryWhoDidFilters(segmentQueryCSVMap);
                                didNotWidget.verifySegmentQueryUserWhoDidNotFilters(segmentQueryCSVMap);
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CREATE_AN_AD_HOC_SEGMENT:
                                SeleniumUtils.pause(3);
                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                                SeleniumUtils.performClick(driver, element);
                                SeleniumUtils.pause(2);
                                List<String> userWhoLikeType = new ArrayList<>();
                                userWhoLikeType.add(EVENT_PROPERTY);

                                List<String> userWhoLikeEvents = new ArrayList<>();
                                userWhoLikeEvents.add(APP_LAUNCHED);

                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents, segmentQueryCSVMap);


                                List<String> userWhoDid = new ArrayList<>();
                                userWhoDid.add(CHARGED);


                                didWidget.enterUserWhoDidFilters(userWhoDid, segmentQueryCSVMap);


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                        }
                        setUpCampaignReachPBSRecurring();
                        restrictDeliveryToDeviceAndOSType();

                        break;
                    case SINGLE_ACTION:
                    case INACTION_WITHIN_TIME:
                    case ON_A_DATE_TIME:
                        switch (campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                String dynamicXpath = ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_TEXT + campaignMeta.getCsv_key() + "']";
                                if (SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver) != null) {
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                } else {
                                    /*below 2 lines of code is for scroll into view*/
                                    JavascriptExecutor js = (JavascriptExecutor) driver;
                                    js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + "px\" })");
                                    SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.xpath(dynamicXpath), 10, driver));
                                }

                                /* in single action, there is no section for Add like events
                                * segmentWidget.verifySegmentQueryAddLikeDidEventsFilters(segmentQueryCSVMap);
                                *
                                * */

                                didWidget.verifySegmentQueryWhoDidFilters(segmentQueryCSVMap);
                                didNotWidget.verifySegmentQueryUserWhoDidNotFilters(segmentQueryCSVMap);
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CREATE_AN_AD_HOC_SEGMENT:
                                SeleniumUtils.pause(3);
                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                                SeleniumUtils.performClick(driver, element);
                                SeleniumUtils.pause(2);
                                List<String> userWhoLikeType = new ArrayList<>();
                                userWhoLikeType.add(EVENT_PROPERTY);

                                List<String> userWhoLikeEvents = new ArrayList<>();
                                userWhoLikeEvents.add(APP_LAUNCHED);

                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents, segmentQueryCSVMap);


                                List<String> userWhoDid = new ArrayList<>();
                                userWhoDid.add(CHARGED);


                                didWidget.enterUserWhoDidFilters(userWhoDid, segmentQueryCSVMap);


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                        }

                        setUpCampaignReachLiveUser();
                        restrictDeliveryToDeviceAndOSType();

                        break;
                    default:
                }


            default:

        }
    }

    /*this method set the who query while campaign creation */
    private void setWhoQueryFromCSV() throws InterruptedException {
        switch (campaignMeta.getCsv_key().trim().toLowerCase().split("-")[0]) {
            case "mobile push+one time":
            case "mobile push+multiple dates":
            case "mobile push+recurring":
            case "sms+one time":
            case "app inbox+one time":
            case "app inbox+multiple dates":
            case "app inbox+recurring":
            case "email+one time":
            case "email+multiple dates":
            case "email+recurring":
            case "whatsapp+one time":
            case "web push+one time":
            case "suc33389":
                userWhoLikeType.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoLikeEventProp"));
                userWhoLikeEvents.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoLikeEvent"));
                if (!userWhoLikeEvents.get(0).isEmpty()) {
                    segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents, segmentQueryCSVMap);
                }

                /*Set up user who did query*/
                userWhoDid.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent"));
                if (!userWhoDid.get(0).isEmpty()) {
                    logger.info("user who did : " + userWhoDid);
                    didWidget.enterUserWhoDidFilters(userWhoDid, segmentQueryCSVMap);
                }

                /*Set up user who did not do query*/
                userWhoDidNotDo.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"));
                if (!userWhoDidNotDo.get(0).isEmpty()) {
                    didNotWidget.enterUserWhoDidNotFilters(userWhoDidNotDo, segmentQueryCSVMap);
                }
                break;
            case "mobile push+single action":
            case "mobile push+inaction within time":
            case "mobile push+on a date/time":
            case "app inbox+single action":
            case "app inbox+inaction within time":
            case "email+single action":
            case "email+inaction within time":
            case "whatsapp+single action":
            case "whatsapp+inaction within time":
            case "web push+single action":
            case "web push+inaction within time":
            case "mobile push+single action+appinbox":
                /*commenting below line as dropdown does not work in headless mode in bamboo*/
//                actionEventWidget.selectEvent(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("AsSoonAsUserDoes"));
                if (!driver.findElement(By.id("filterStream")).isSelected()) {
                    SeleniumUtils.performClick(driver, filterPastUserBehaviour);
                }
                liveUserWhoDidEvent.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent"));
                liveUserWhoDidOccurences.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidOccurence"));
                pbsUserWhoDidNotDo.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"));
                //remove comment and add check
//                didWidget.enterLiveActionUserWhoDidFilters(liveUserWhoDidEvent, liveUserWhoDidOccurences);
//                didNotWidget.enterLiveActionUserWhoDidNotFilters(pbsUserWhoDidNotDo, segmentQueryCSVMap);
                break;
            case "whatsapp+on a date/time":
            case "web push+on a date/time":
                actionEventWidget.selectEvent(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("AsSoonAsUserDoes"));
                actionEventWidget.selEvent(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("EventProperty"));
                if (!driver.findElement(By.id("filterStream")).isSelected()) {
                    SeleniumUtils.performClick(driver, filterPastUserBehaviour);
                }
                liveUserWhoDidEvent.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent"));
                liveUserWhoDidOccurences.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidOccurence"));
                pbsUserWhoDidNotDo.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"));
                didWidget.enterLiveActionUserWhoDidFilters(liveUserWhoDidEvent, liveUserWhoDidOccurences);
                didNotWidget.enterLiveActionUserWhoDidNotFilters(pbsUserWhoDidNotDo, segmentQueryCSVMap);
                break;
            default:
        }
    }

    /*this method change the who query for PBS post campaign clone */
    private void changeWhoQueryFromCSV() throws InterruptedException {
        switch (campaignMeta.getCsv_key().trim().toLowerCase().split("-")[0]) {
            case "mobile push+one time":
            case "mobile push+multiple dates":
            case "mobile push+recurring":
            case "sms+one time":
            case "app inbox+one time":
            case "app inbox+multiple dates":
            case "app inbox+recurring":
            case "email+one time":
            case "email+multiple dates":
            case "email+recurring":
            case "whatsapp+one time":
            case "web push+one time":
                changeUserWhoLikeType.add(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoLikeEventProp"));
                changeUserWhoLikeEvents.add(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoLikeEvent"));
                if (!changeUserWhoLikeEvents.get(0).isEmpty()) {
                    segmentWidget.enterAddLikeDidEventsFilters(changeUserWhoLikeType, changeUserWhoLikeEvents, changeSegmentQueryCSVMap);
                }

                /*Set up user who did query*/
                changeUserWhoDid.add(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent"));
                if (!changeUserWhoDid.get(0).isEmpty()) {
                    logger.info("user who did : " + changeUserWhoDid);
                    didWidget.enterUserWhoDidFilters(changeUserWhoDid, changeSegmentQueryCSVMap);
                }

                /*Set up user who did not do query*/
                changeUserWhoDidNotDo.add(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"));
                if (!changeUserWhoDidNotDo.get(0).isEmpty()) {
                    didNotWidget.enterUserWhoDidNotFilters(changeUserWhoDidNotDo, changeSegmentQueryCSVMap);
                }
                break;

            case "mobile push+single action":
            case "mobile push+inaction within time":
            case "mobile push+on a date/time":
            case "mobile inapp":
            case "app inbox+single action":
            case "app inbox+inaction within time":
            case "email+single action":
            case "email+inaction within time":
            case "whatsapp+single action":
            case "whatsapp+inaction within time":
            case "web push+single action":
            case "web push+inaction within time":
            case "mobile push+single action+appinbox":
                actionEventWidget.selectEvent(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("AsSoonAsUserDoes"));
                if (!driver.findElement(By.id("filterStream")).isSelected()) {
                    SeleniumUtils.performClick(driver, filterPastUserBehaviour);
                }

                if(!SeleniumUtils.isNullOrEmpty((changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent")))){
                    changeLiveUserWhoDidEvent.add(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent"));
                }
                if(!SeleniumUtils.isNullOrEmpty(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidOccurence"))){
                    changeLiveUserWhoDidOccurences.add(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidOccurence"));
                }

                if(!SeleniumUtils.isNullOrEmpty(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"))){
                    changePbsUserWhoDidNotDo.add(changeSegmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"));
                }

                if(changeLiveUserWhoDidEvent.size()>0 && changeLiveUserWhoDidOccurences.size()>0){
                    didWidget.enterLiveActionUserWhoDidFilters(changeLiveUserWhoDidEvent, changeLiveUserWhoDidOccurences);
                }

                if(changePbsUserWhoDidNotDo.size()>0){
                    didNotWidget.enterLiveActionUserWhoDidNotFilters(changePbsUserWhoDidNotDo, changeSegmentQueryCSVMap);
                }

                break;

            case "whatsapp+on a date/time":
            case "web push+on a date/time":
                actionEventWidget.selectEvent(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("AsSoonAsUserDoes"));
                actionEventWidget.selEvent(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("EventProperty"));
                if (!driver.findElement(By.id("filterStream")).isSelected()) {
                    SeleniumUtils.performClick(driver, filterPastUserBehaviour);
                }
                changeLiveUserWhoDidEvent.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidEvent"));
                changeLiveUserWhoDidOccurences.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidOccurence"));
                changePbsUserWhoDidNotDo.add(segmentQueryCSVMap.get(campaignMeta.getCsv_key()).get("whoDidNotEvent"));
                didWidget.enterLiveActionUserWhoDidFilters(changeLiveUserWhoDidEvent, changeLiveUserWhoDidOccurences);
                didNotWidget.enterLiveActionUserWhoDidNotFilters(changePbsUserWhoDidNotDo, changeSegmentQueryCSVMap);
                break;
            default:
        }
    }

    public void verifyWhoQueryFromCSV() throws InterruptedException {
        switch (campaignMeta.getCsv_key().trim().toLowerCase().split("-")[0]) {
            case "mobile push+one time":
            case "mobile push+multiple dates":
            case "mobile push+recurring":
            case "sms+one time":
            case "app inbox+one time":
            case "app inbox+multiple dates":
            case "app inbox+recurring":
            case "email+one time":
            case "email+multiple dates":
            case "email+recurring":
            case "whatsapp+one time":
            case "web push+one time":
                if (!userWhoLikeEvents.get(0).isEmpty()) {
                    segmentWidget.verifyAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents, segmentQueryCSVMap);
                }
                if (!userWhoDid.get(0).isEmpty()) {
                    didWidget.verifyUserWhoDidFilters(userWhoDid, segmentQueryCSVMap);
                }
                if (!userWhoDidNotDo.get(0).isEmpty()) {
                    didNotWidget.verifyUserWhoDidNotFilters(userWhoDidNotDo, segmentQueryCSVMap);
                }
                break;

            case "mobile push+single action":
            case "mobile push+inaction within time":
            case "mobile push+on a date/time":
            case "mobile inapp":
            case "app inbox+single action":
            case "app inbox+inaction within time":
            case "email+single action":
            case "email+inaction within time":
            case "whatsapp+single action":
            case "whatsapp+inaction within time":
            case "whatsapp+on a date/time":
            case "web push+single action":
            case "web push+inaction within time":
            case "web push+on a date/time":

                actionEventWidget.verifyEvent(segmentQueryCSVMap);
                didWidget.verifyLiveActionUserWhoDidFilters(segmentQueryCSVMap);
                didNotWidget.verifyLiveActionUserWhoDidNotFilters(segmentQueryCSVMap);
                break;

            default:
        }
    }

    public void changeWhoPage() throws InterruptedException {

//        switch (campaignMeta.getChannel().trim().toLowerCase()) {
//            case "mobile push":
//            case "sms":
//        /*User who like query validation*//*
//        issue found and raised https://wizrocket.atlassian.net/browse/CLEVER-2184
//        segmentWidget.verifyAddLikeDidEventsFilters(userWhoLikeType,userWhoLikeEvents);*/
//
//        /*user who did query validation*/
//                switch (campaignMeta.getType().trim().toLowerCase()) {
//                    case "one time":
//                    case "multiple dates":
//                    case "recurring":
//                        if (!userWhoDid.get(0).isEmpty()) {
////                            Assert.assertTrue(userWhoDid.get(0).equalsIgnoreCase(didWidget.verifyUserWhoDidFilters(userWhoDid)));
//                        }
//                        /*user who did not do query validation*/
//                        if (!userWhoDidNotDo.get(0).isEmpty()) {
////                            Assert.assertTrue(userWhoDidNotDo.get(0).equalsIgnoreCase(didNotWidget.verifyUserWhoDidNotFilters(userWhoDidNotDo)));
//                        }
//
//                        if (noSavedSegmentscounter == 0) {
//                            SeleniumUtils.performClick(driver, whoPageSelectSegment);
//                        }
//                        break;
//                    case "single action":
//                    case "inaction within time":
//                    case "on a date/time":
//                        List<String> actualWhoUserDidQuery = didWidget.verifyLiveActionUserWhoDidFilters();
//                        Assert.assertTrue(actualWhoUserDidQuery.contains(liveUserWhoDidEvent.get(0)));
//                        Assert.assertTrue(actualWhoUserDidQuery.contains(liveUserWhoDidOccurences.get(0)));
//                        String actualSelectedUserWhoDidNotQuery = didNotWidget.verifyLiveActionUserWhoDidNotFilters();
//                        Assert.assertTrue(pbsUserWhoDidNotDo.contains(actualSelectedUserWhoDidNotQuery));
//                        break;
//                    default:
//                }
//
//
//                break;
//            case "mobile in-app":
//                if (campaignMeta.getWho().getSegment_select_type().equalsIgnoreCase("+ create an ad-hoc segment")) {
//                    Assert.assertTrue(campaignMeta.getWho().getUser_who_like_event().equalsIgnoreCase(actionEventWidget.verifySetEvent()));
//                }
//
//                break;
//
//            default:
//
//        }


//        if (noSavedSegmentscounter == 0) {
//            SeleniumUtils.performClick(driver, whoPageSelectSegment);
//        }

        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case CampaignsHomePage.MOBILE_PUSH:
            case CampaignsHomePage.SMS:
            case CampaignsHomePage.EMAIL:
            case CampaignsHomePage.WEB_PUSH:
            case CampaignsHomePage.WEB_POPUP:
            case CampaignsHomePage.WEB_EXIT_INTENT:
            case CampaignsHomePage.WHATSAPP:
            case CampaignsHomePage.FB_AUDIENCES:
            case CampaignsHomePage.GOOGLE_ADS:
            case CampaignsHomePage.WEBHOOKS:
                switch (campaignMeta.getType().trim().toLowerCase()) {
                    case ONE_TIME:
                    case MULTIPLE_DATES:
                    case RECURRING:
                    case SINGLE_ACTION:
                    case INACTION_WITHIN_TIME:
                    case ON_A_DATE_TIME:
                        switch (campaignMeta.getWho().getChange_segment_type().trim().toLowerCase()) {
//                            case SAVED_SEGMENTS:
//                                if (allSavedSegments.size() >= 1) {
//                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
//                                    /*If segment table contains more than one saved segments then just sewlect the first one*/
//                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_1)));
//                                }
//                                break;
//                            case SAVED_BOOKMARKS_SEGMENTS:
//                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));
//
//                                break;
                            case CHANGE_SAVED_SEGMENT_QUERY:
                                SeleniumUtils.pause(3);
//                                try {
//                                    WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
//                                    SeleniumUtils.performClick(driver, element);
//                                } catch (Exception e) {
//                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@class='framework-nav--parent']/..//a[text()='Select']")));
//                                    WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
//                                    SeleniumUtils.performClick(driver, element);
//                                }

//                                SeleniumUtils.pause(2);

                                changeWhoQueryFromCSV();

//                                List<String> userWhoLikeType = new ArrayList<>();
//                                userWhoLikeType.add(EVENT_PROPERTY);
//
//                                List<String> userWhoLikeEvents = new ArrayList<>();
//                                userWhoLikeEvents.add(APP_LAUNCHED);
//
//                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents);
//
//
//                                List<String> userWhoDid = new ArrayList<>();
//                                userWhoDid.add(CHARGED);
//
//
//                                didWidget.enterUserWhoDidFilters(userWhoDid);


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                                logger.info("if no info is given about segment selection then by default it will move ahead");
                                SeleniumUtils.performClick(driver, continueButton);
                                break;
                        }
                        changeSetUpCampaignReach();
                        changeRestrictDeliveryToDeviceAndOSType();

                        break;
//                    case RECURRING:
//                        switch (campaignMeta.getWho().getChange_segment_type().trim().toLowerCase()) {
//                            case SAVED_SEGMENTS:
//                                if (allSavedSegments.size() >= 1) {
//                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
//                                    /*If segment table contains more than one saved segments then just sewlect the first one*/
//                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_1)));
//                                }
//                                break;
//                            case SAVED_BOOKMARKS_SEGMENTS:
//                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));
//
//                                break;
//                            case CREATE_AN_AD_HOC_SEGMENT:
//                                SeleniumUtils.pause(3);
//                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
//                                SeleniumUtils.performClick(driver, element);
//                                SeleniumUtils.pause(2);
//                                List<String> userWhoLikeType = new ArrayList<>();
//                                userWhoLikeType.add(EVENT_PROPERTY);
//
//                                List<String> userWhoLikeEvents = new ArrayList<>();
//                                userWhoLikeEvents.add(APP_LAUNCHED);
//
//                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents,changeSegmentQueryCSVMap);
//
//
//                                List<String> userWhoDid = new ArrayList<>();
//                                userWhoDid.add(CHARGED);
//
//
//                                didWidget.enterUserWhoDidFilters(userWhoDid,changeSegmentQueryCSVMap);
//
//
//                                break;
//                            case ALL_USERS:
//                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
//                                break;
//                            default:
//                        }
//                        changeCampaignReachPBSRecurring();
//                        changeRestrictDeliveryToDeviceAndOSType();
//
//                        break;
//                    case SINGLE_ACTION:
//                    case INACTION_WITHIN_TIME:
//                    case ON_A_DATE_TIME:
//                        switch (campaignMeta.getWho().getChange_segment_type().trim().toLowerCase()) {
////                            case SAVED_SEGMENTS:
////                                if (allSavedSegments.size() >= 1) {
////                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
////                                    /*If segment table contains more than one saved segments then just sewlect the first one*/
////                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_1)));
////                                } else {
////                                    SeleniumUtils.performClick(driver, continueButton);
////                                }
////                                break;
////                            case SAVED_BOOKMARKS_SEGMENTS:
////                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));
////
////                                break;
////                            case CREATE_AN_AD_HOC_SEGMENT:
////                                SeleniumUtils.pause(3);
////                                try {
////                                    WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
////                                    SeleniumUtils.performClick(driver, element);
////                                } catch (Exception e) {
////                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@class='framework-nav--parent']/..//a[text()='Select']")));
////                                    WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
////                                    SeleniumUtils.performClick(driver, element);
////                                }
////                                SeleniumUtils.pause(2);
////
////                                switch (campaignMeta.getType().trim().toLowerCase()) {
////                                    case ONE_TIME:
////                                    case MULTIPLE_DATES:
////                                    case RECURRING:
////                                        List<String> userWhoLikeType = new ArrayList<>();
////                                        userWhoLikeType.add(EVENT_PROPERTY);
////                                        List<String> userWhoLikeEvents = new ArrayList<>();
////                                        userWhoLikeEvents.add(APP_LAUNCHED);
////                                        segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents,changeSegmentQueryCSVMap);
////                                        List<String> userWhoDid = new ArrayList<>();
////                                        userWhoDid.add(CHARGED);
////                                        didWidget.enterUserWhoDidFilters(userWhoDid,changeSegmentQueryCSVMap);
////                                        break;
////                                    case SINGLE_ACTION:
////                                    case ON_A_DATE_TIME:
////                                    case INACTION_WITHIN_TIME:
////                                        break;
////                                    default:
////                                }
////
////                                break;
////                            case ALL_USERS:
////                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
////                                break;
////                            default:
//                        }
//
//                        changeCampaignReachLiveUser();
//                        changeRestrictDeliveryToDeviceAndOSType();
//
//                        break;
//                    default:
                }
                break;
            case CampaignsHomePage.MOBILE_INAPP:
//                SeleniumUtils.performClick(driver, whoPageSelectSegment);
                switch (campaignMeta.getWho().getChange_segment_type().trim().toLowerCase()) {
                    case SAVED_SEGMENTS:
                        if (allSavedSegments.size() >= 1) {
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                            /*If segment table contains more than one saved segments then just sewlect the first one*/
                            SeleniumUtils.performClick(driver, driver.findElement(By.xpath(ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_1)));
                        }
                        break;
                    case CHANGE_SAVED_SEGMENT_QUERY:
                        SeleniumUtils.pause(3);
//                        WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
//                        SeleniumUtils.performClick(driver, element);
//                        SeleniumUtils.pause(2);

                        changeWhoQueryFromCSV();

                        //*[@id='evtSelection1']/..//a/following-sibling::div//li[text()='Added To Cart']

//                        driver.findElement(By.xpath("//*[@id='evtSelection1']/..//a")).click();
//                        SeleniumUtils.pause(1);
//                        driver.findElement(By.xpath("//*[@id='evtSelection1']/..//a/following-sibling::div//li[text()='Added To Cart']")).click();

                        break;
                    default:
                        SeleniumUtils.performClick(driver, continueButton);
                }
                break;

            case CampaignsHomePage.APP_INBOX:
                switch (campaignMeta.getType().trim().toLowerCase()) {
                    case ONE_TIME:
                    case MULTIPLE_DATES:
                        switch (campaignMeta.getWho().getChange_segment_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                if (allSavedSegments.size() >= 1) {
                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                    /*If segment table contains more than one saved segments then just sewlect the first one*/
                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_1)));
                                } else {
                                    SeleniumUtils.performClick(driver, continueButton);
                                }
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CHANGE_SAVED_SEGMENT_QUERY:
                                SeleniumUtils.pause(3);
//                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
//                                SeleniumUtils.performClick(driver, element);
//                                SeleniumUtils.pause(2);
//                                List<String> userWhoLikeType = new ArrayList<>();
//                                userWhoLikeType.add(EVENT_PROPERTY);
//
//                                List<String> userWhoLikeEvents = new ArrayList<>();
//                                userWhoLikeEvents.add(APP_LAUNCHED);
//
//                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents,changeSegmentQueryCSVMap);
//
//
//                                List<String> userWhoDid = new ArrayList<>();
//                                userWhoDid.add(CHARGED);
//
//
//                                didWidget.enterUserWhoDidFilters(userWhoDid,changeSegmentQueryCSVMap);

                                changeWhoQueryFromCSV();

                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                        }
                        setUpCampaignReach();
                        restrictDeliveryToDeviceAndOSType();

                        break;
                    case RECURRING:
                        switch (campaignMeta.getWho().getChange_segment_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                if (allSavedSegments.size() >= 1) {
                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                    /*If segment table contains more than one saved segments then just sewlect the first one*/
                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_1)));
                                }
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CHANGE_SAVED_SEGMENT_QUERY:
                                SeleniumUtils.pause(3);
                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                                SeleniumUtils.performClick(driver, element);
//                                SeleniumUtils.pause(2);
//                                List<String> userWhoLikeType = new ArrayList<>();
//                                userWhoLikeType.add(EVENT_PROPERTY);
//
//                                List<String> userWhoLikeEvents = new ArrayList<>();
//                                userWhoLikeEvents.add(APP_LAUNCHED);
//
//                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents,changeSegmentQueryCSVMap);
//
//
//                                List<String> userWhoDid = new ArrayList<>();
//                                userWhoDid.add(CHARGED);
//
//
//                                didWidget.enterUserWhoDidFilters(userWhoDid,changeSegmentQueryCSVMap);

                                changeWhoQueryFromCSV();


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                        }
                        changeCampaignReachPBSRecurring();
                        changeRestrictDeliveryToDeviceAndOSType();

                        break;
                    case SINGLE_ACTION:
                    case INACTION_WITHIN_TIME:
                    case ON_A_DATE_TIME:
                        switch (campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()) {
                            case SAVED_SEGMENTS:
                                if (allSavedSegments.size() >= 1) {
                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_SEGMENTS)));
                                    /*If segment table contains more than one saved segments then just sewlect the first one*/
                                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath(ID_SEGMENT_TABLE_TBODY_TD_CONTAINS_CLASS_SEGMENT_NAME_COL_A_1)));
                                } else {
                                    SeleniumUtils.performClick(driver, continueButton);
                                }
                                break;
                            case SAVED_BOOKMARKS_SEGMENTS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(DATA_CT_TAB_SAVED_BOOKMARKS)));

                                break;
                            case CHANGE_SAVED_SEGMENT_QUERY:
                                SeleniumUtils.pause(3);
                                WebElement element = driver.findElement(By.id(INLINE_WHO_CREATE_ADHOC));
                                SeleniumUtils.performClick(driver, element);
//                                SeleniumUtils.pause(2);
//                                List<String> userWhoLikeType = new ArrayList<>();
//                                userWhoLikeType.add(EVENT_PROPERTY);
//
//                                List<String> userWhoLikeEvents = new ArrayList<>();
//                                userWhoLikeEvents.add(APP_LAUNCHED);
//
//                                segmentWidget.enterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents,changeSegmentQueryCSVMap);
//
//
//                                List<String> userWhoDid = new ArrayList<>();
//                                userWhoDid.add(CHARGED);
//
//
//                                didWidget.enterUserWhoDidFilters(userWhoDid,changeSegmentQueryCSVMap);

                                changeWhoQueryFromCSV();


                                break;
                            case ALL_USERS:
                                SeleniumUtils.performClick(driver, driver.findElement(By.id(INLINE_WHO_ALL_USERS)));
                                break;
                            default:
                        }

                        setUpCampaignReachLiveUser();
                        restrictDeliveryToDeviceAndOSType();

                        break;
                    default:
                }


            default:

        }
    }

    private void setUpCampaignReachLiveUser() {

        switch (campaignMeta.getWho().getCampaign_reach_type()) {
            case ALL_USERS:
                SeleniumUtils.performClick(driver, trigAllUsersSelectRadio);
                break;
            case LIMIT_USERS:
                SeleniumUtils.performClick(driver, trigOnlyUsersPerDayRadio);
                SeleniumUtils.enterInputText(driver, trigOnlyUsersPerDayText, campaignMeta.getWho().getSafety_check());
                if (campaignMeta.getWho().getOverall_safety_check() != null) {
                    SeleniumUtils.performClick(driver, trigCapToUserOverallCheck);
                    SeleniumUtils.enterInputText(driver, trigrCapToUsersOverallText, campaignMeta.getWho().getOverall_safety_check());
                }
                break;
            case ONLY_X_USERS_OVERALL:
                SeleniumUtils.performClick(driver, trigUsersOverAllRadio);
                SeleniumUtils.enterInputText(driver, trigUsersOverAllText, campaignMeta.getWho().getOverall_safety_check());
                break;
            default:
        }

        switch (campaignMeta.getType().trim().toLowerCase()) {
            case CampaignsHomePage.INACTION_WITHIN_TIME:
                SeleniumUtils.enterInputText(driver, doesNotDoWithinTime, String.valueOf(2));
                break;
            case CampaignsHomePage.SINGLE_ACTION:
                break;
            case CampaignsHomePage.ON_A_DATE_TIME:
                break;
            case CampaignsHomePage.RECURRING:
                break;
            case CampaignsHomePage.MULTIPLE_DATES:
                break;
            case CampaignsHomePage.ONE_TIME:
                break;
            default:
                logger.info(" ********************* ");
        }
    }

    private void changeCampaignReachLiveUser() {

        switch (campaignMeta.getWho().getChange_campaign_reachToType()) {
            case ALL_USERS:
                SeleniumUtils.performClick(driver, trigAllUsersSelectRadio);
                break;
            case LIMIT_USERS:
                SeleniumUtils.performClick(driver, trigOnlyUsersPerDayRadio);
                SeleniumUtils.enterInputText(driver, trigOnlyUsersPerDayText, campaignMeta.getWho().getSafety_check());
                if (campaignMeta.getWho().getOverall_safety_check() != null) {
                    SeleniumUtils.performClick(driver, trigCapToUserOverallCheck);
                    SeleniumUtils.enterInputText(driver, trigrCapToUsersOverallText, campaignMeta.getWho().getOverall_safety_check());
                }
                break;
            case ONLY_X_USERS_OVERALL:
                SeleniumUtils.performClick(driver, trigUsersOverAllRadio);
                SeleniumUtils.enterInputText(driver, trigUsersOverAllText, campaignMeta.getWho().getOverall_safety_check());
                break;
            default:
        }

        switch (campaignMeta.getType().trim().toLowerCase()) {
            case CampaignsHomePage.INACTION_WITHIN_TIME:
                SeleniumUtils.enterInputText(driver, doesNotDoWithinTime, String.valueOf(2));
                break;
            case CampaignsHomePage.SINGLE_ACTION:
                break;
            case CampaignsHomePage.ON_A_DATE_TIME:
                break;
            case CampaignsHomePage.RECURRING:
                break;
            case CampaignsHomePage.MULTIPLE_DATES:
                break;
            case CampaignsHomePage.ONE_TIME:
                break;
            default:
                logger.info(" ********************* ");
        }
    }

    private void setUpCampaignReachPBSRecurring() {

        switch (campaignMeta.getWho().getCampaign_reach_type()) {
            case ALL_USERS:
                SeleniumUtils.performClick(driver, recAllUsersRadio);
                if (campaignMeta.getWho().getSafety_check() != null) {
                    int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                    SeleniumUtils.performClick(driver, recAllUsersSafetyCheck);
                    SeleniumUtils.enterInputText(driver, recAllUsersSafetyTextBox, String.valueOf(number));
                }
                break;
            case LIMIT_USERS:
                SeleniumUtils.performClick(driver, recLimitToUsersRadio);
                SeleniumUtils.enterInputText(driver, recLimitUsersPerDayText, campaignMeta.getWho().getSafety_check());
                if (campaignMeta.getWho().getOverall_safety_check() != null) {
                    int number = Integer.parseInt(campaignMeta.getWho().getOverall_safety_check());
                    if (!driver.findElement(By.id("recurringTotalCampaignLimitId")).isSelected()) {
                        SeleniumUtils.performClick(driver, recCapToUserOverallCheck);
                        SeleniumUtils.enterInputText(driver, recCapToUserOverallText, String.valueOf(number));
                    }

                }
                break;
            default:
        }
    }

    private void changeCampaignReachPBSRecurring() {

        switch (campaignMeta.getWho().getChange_campaign_reachToType()) {
            case ALL_USERS:
                SeleniumUtils.performClick(driver, recAllUsersRadio);
                if (campaignMeta.getWho().getSafety_check() != null) {
                    int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                    SeleniumUtils.performClick(driver, recAllUsersSafetyCheck);
                    SeleniumUtils.enterInputText(driver, recAllUsersSafetyTextBox, String.valueOf(number));
                }
                break;
            case LIMIT_USERS:
                SeleniumUtils.performClick(driver, recLimitToUsersRadio);
                SeleniumUtils.enterInputText(driver, recLimitUsersPerDayText, campaignMeta.getWho().getSafety_check());
                if (campaignMeta.getWho().getOverall_safety_check() != null) {
                    int number = Integer.parseInt(campaignMeta.getWho().getOverall_safety_check());
                    SeleniumUtils.performClick(driver, recCapToUserOverallCheck);
                    SeleniumUtils.enterInputText(driver, recCapToUserOverallText, String.valueOf(number));
                }
                break;
            default:
        }
    }

    private void setUpCampaignReach() {
        switch (campaignMeta.getType().trim().toLowerCase()) {
            case ONE_TIME:
                switch (campaignMeta.getWho().getCampaign_reach_type().trim().toLowerCase()) {
                    case ALL_USERS:

                        SeleniumUtils.performClick(driver, oneTimeAllUsersRadio);
                        if (campaignMeta.getWho().getSafety_check() != null) {
                            int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                            SeleniumUtils.performClick(driver, oneTimeAllUsersSafetyCheck);
                            SeleniumUtils.enterInputText(driver, oneTimeAllUserSafetyChecklimit, String.valueOf(number));
                        } else {
                            logger.info(NO_SAFTEY_CHECK_REQUIRED_ALL_USERS_SHOULD_QUALIFY);
                        }
                        break;
                    case LIMIT_USERS:
                        SeleniumUtils.performClick(driver, oneTimeLimitToUsersRadio);
                        int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                        SeleniumUtils.enterInputText(driver, oneTImeLimitToUsersText, String.valueOf(number));

                        break;
                    default:
                }
                break;
            case MULTIPLE_DATES:
                switch (campaignMeta.getWho().getChange_campaign_reachToType().trim().toLowerCase()) {
                    case ALL_USERS:

                        SeleniumUtils.performClick(driver, recAllUsersRadio);
                        if (campaignMeta.getWho().getSafety_check() != null) {
                            int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                            if (!driver.findElement(By.id("recurringCampaignQualifyId")).isSelected()) {
                                SeleniumUtils.performClick(driver, recAllUsersSafetyCheck);
                            }
                            SeleniumUtils.pause(1);
                            SeleniumUtils.enterInputText(driver, recAllUsersSafetyTextBox, String.valueOf(number));
                        } else {
                            logger.info(NO_SAFTEY_CHECK_REQUIRED_ALL_USERS_SHOULD_QUALIFY);
                        }
                        break;
                    case LIMIT_USERS:
                        SeleniumUtils.performClick(driver, recLimitToUsersRadio);
                        int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                        SeleniumUtils.enterInputText(driver, recLimitUsersPerDayText, String.valueOf(number));
                        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWho().getLimit_users_cap())) {
                            SeleniumUtils.performClick(driver, recCapToUserOverallCheck);
                            SeleniumUtils.enterInputText(driver, recCapToUserOverallText, campaignMeta.getWho().getLimit_users_cap());
                        } else {
                            logger.info("No limit users cap provided");
                        }

                        break;
                    default:
                }
                break;
            case SINGLE_ACTION:
                break;
            case INACTION_WITHIN_TIME:
                break;
            case ON_A_DATE_TIME:
                break;
            default:

        }

    }

    private void changeSetUpCampaignReach() {
        switch (campaignMeta.getType().trim().toLowerCase()) {
            case ONE_TIME:
                switch (campaignMeta.getWho().getChange_campaign_reachToType().trim().toLowerCase()) {
                    case ALL_USERS:

                        SeleniumUtils.performClick(driver, oneTimeAllUsersRadio);
                        if (campaignMeta.getWho().getSafety_check() != null) {
                            int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                            SeleniumUtils.performClick(driver, oneTimeAllUsersSafetyCheck);
                            SeleniumUtils.enterInputText(driver, oneTimeAllUserSafetyChecklimit, String.valueOf(number));
                        } else {
                            logger.info(NO_SAFTEY_CHECK_REQUIRED_ALL_USERS_SHOULD_QUALIFY);
                        }
                        break;
                    case LIMIT_USERS:
                        SeleniumUtils.performClick(driver, oneTimeLimitToUsersRadio);
                        int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                        SeleniumUtils.enterInputText(driver, oneTImeLimitToUsersText, String.valueOf(number));

                        break;
                    default:
                }
                break;
            case MULTIPLE_DATES:
                switch (campaignMeta.getWho().getChange_campaign_reachToType().trim().toLowerCase()) {
                    case ALL_USERS:

                        SeleniumUtils.performClick(driver, recAllUsersRadio);
                        if (campaignMeta.getWho().getSafety_check() != null) {
                            int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                            if (!driver.findElement(By.id("recurringCampaignQualifyId")).isSelected()) {
                                recAllUsersSafetyCheck.click();
                            }

                            SeleniumUtils.enterInputText(driver, recAllUsersSafetyTextBox, String.valueOf(number));
                        } else {
                            logger.info(NO_SAFTEY_CHECK_REQUIRED_ALL_USERS_SHOULD_QUALIFY);
                        }
                        break;
                    case LIMIT_USERS:
                        SeleniumUtils.performClick(driver, recLimitToUsersRadio);
                        int number = Integer.parseInt(campaignMeta.getWho().getSafety_check());
                        SeleniumUtils.enterInputText(driver, recLimitUsersPerDayText, String.valueOf(number));
                        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWho().getLimit_users_cap())) {
                            SeleniumUtils.performClick(driver, recCapToUserOverallCheck);
                            SeleniumUtils.enterInputText(driver, recCapToUserOverallText, campaignMeta.getWho().getLimit_users_cap());
                        } else {
                            logger.info("No limit users cap provided");
                        }

                        break;
                    default:
                }
                break;
            case RECURRING:
                break;
            case SINGLE_ACTION:
                changeCampaignReachLiveUser();
                break;
            case INACTION_WITHIN_TIME:
                break;
            case ON_A_DATE_TIME:
                break;
            default:

        }

    }

    public void customArrayListCleaner() {
        /*it will free all unused array lists so that they can be used in next run again*/
        userWhoLikeType = null;
        userWhoLikeEvents = null;
        userWhoDid = null;
        userWhoDidNotDo = null;
        liveUserWhoDidEvent = null;
        liveUserWhoDidOccurences = null;
        pbsUserWhoDidNotDo = null;
        changeUserWhoLikeType = null;
        changeUserWhoLikeEvents = null;
        changeUserWhoDid = null;
        changeUserWhoDidNotDo = null;
        changeLiveUserWhoDidEvent = null;
        changeLiveUserWhoDidOccurences = null;
        changePbsUserWhoDidNotDo = null;

    }

}
