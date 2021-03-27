package com.clevertap.ui.pages.campaigns_page;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.DateUtil;
import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TimeSetter;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhenPage extends LoadYamlFile {

    private static final String DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS = "discard messages scheduled during dnd hours";
    private static final String DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS = "delay delivery until the end of dnd hours";
    private static final String DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS1 = "discard messages scheduled during dnd hours";
    private static final String DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS1 = "delay delivery until the end of dnd hours";
    private static final String DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS2 = "discard messages scheduled during dnd hours";
    private static final String DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS2 = "delay delivery until the end of dnd hours";
    private static final String ID = "(//*[@id='";
    private static final String DISPLAY_NONE = "display: none;";
    private static final String MULTIPLE_DATES = "multiple dates";
    private static final String RECURRING = "recurring";
    private static final String NEVER_END = "never end";
    private static final String SELECT_DATE = "select date";
    private static final String AFTER = "after";
    private static final String CAMPAIGN_DO_NOT_DISTURB = "Campaign_do_not_disturb";
    private static final String STYLE = "style";
    private static final String NOT_A_VALID_TYPE_OF_CAMPAIGN_DND = "!!!!not a valid type of campaign DND!!!!! ";
    private static final String SELECT_DATE_AND_TIME = "select date and time";
    public static final String DROP_THE_CAMPAIGN = "drop the campaign";
    public static final String DELIVER_THE_CAMPAIGNS_THE_NEXT_DAY = "deliver the campaigns the next day";
    public static final String MOBILE_PUSH = "mobile push";
    public static final String SMS = "sms";
    public static final String EMAIL = "email";
    public static final String WEB_PUSH = "web push";
    public static final String WEB_POP_UP = "web pop up";
    public static final String WEB_EXIT_INTENT = "web exit intent";
    public static final String WHATSAPP = "whatsapp";
    public static final String APP_INBOX = "app inbox";
    public static final String MOBILE_IN_APP = "mobile in-app";
    public static final String NATIVE_DISPLAY = "native display";
    public static final String EXCLUDE_FROM_CAMPAIGN_LIMITS = "exclude from campaign limits";
    public static final String ONCE_PER_PERSON = "once per person";
    public static final String ONCE_PER_DAY = "once per day";
    public static final String ONCE_PER_USER_FOR_THE_DURATION_OF_THIS_CAMPAIGN = "once per user for the duration of this campaign";
    public static final String TIME_ZONE = "Time_zone";
    private WebDriver driver;
    private SweetAlert sweetAlert;
    private static Logger logger = Logger.getLogger(WhenPage.class);

    private static final String START_NOW = "now";
    private static final String START_LATER = "later";
    private static final String ACTION_CAMPAIGN_NO_DELAY = "no delay";
    private static final String ACTION_CAMPAIGN_DELAY = "delay";
    private static final String ACTION_END_NEVER_END = "never end";
    private static final String ACTION_END_ON_SELECT_DATE_TIME = "select date and time";
    private static final String RECURRING_END_SELECT_DATE = "select date";
    private static final String RECURRING_END_AFTER = "after";
    private static final String BEST_TIME_FOR_EVERY_USER = "best time for every user";
    private static final String A_FIXED_TIME = "a fixed time";

    @FindBy(xpath = "//label[@for='oneTimeType']")
    private WebElement oneTime;
    @FindBy(xpath = "//label[@for='certainDatesType']")
    private WebElement multipleDates;
    @FindBy(xpath = "//label[@for='recurringType']")
    private WebElement recurring;
    @FindBy(xpath = "//label[@for='js-start-now']")
    private WebElement campaignStartNow;
    @FindBy(xpath = "//label[@for='js-start-later']")
    private WebElement campaignStartLater;
    @FindBy(id = "campaign-start")
    private WebElement campaignStartDate;
    @FindBy(id = "recurrCampaign-startDate")
    private WebElement recurringCampaignStartDate;
    @FindBy(id = "certainDates_date")
    private WebElement multipleDateCalendarElement;
    @FindBy(id = "recurrCampaign-startDate")
    private WebElement recurrCampaignStartDateElement;

    //Elements for campaign end
    @FindBy(xpath = "//label[@for='endNever']")
    private WebElement campaignNeverEndRec;
    @FindBy(xpath = "//label[@for='endOnDate']")
    private WebElement campaignEndDate;
    @FindBy(xpath = "//label[@for='endAfterOccurences']")
    private WebElement campaignEndAfterOccurences;
    @FindBy(xpath = "//input[contains(@class,'js-totalOccurrences')]")
    private WebElement campaignEndAfterOccNum;
    @FindBy(id = "recurrCampaign-endDate")
    private WebElement campaignEndDateSetRecurring;
    @FindBy(xpath = "//label[@for='js-date-time']")
    private WebElement triggerEndDateRadio;
    @FindBy(id = "campaign-end")
    private WebElement triggerCampaignEndDate;
    @FindBy(xpath = "//label[@for='js-never-end']")
    private WebElement triggerNeverEnd;


    @FindBy(xpath = "//label[@for='js-best-time-campaign_0']")
    private WebElement campaignDeliveryBestTime;
    @FindBy(xpath = "//label[@for='js-fixed-time-campaign_0']")
    private WebElement campaignDeliveryFixedTime;

    //Delay only in Single action campaigns
    @FindBy(xpath = "//label[@for='send-now']")
    private WebElement delayNoDelay;
    @FindBy(xpath = "//label[@for='send-delay']")
    private WebElement delayDelayBy;
    @FindBy(id = "delayByTimeValue")
    private WebElement delayAmount;
    @FindBy(id = "delayDisplayUnits_chzn")
    private WebElement delayUnit;

    //Advanced checkbox
    @FindBy(xpath = "//label[@for='advanceSelectBox']")
    private WebElement advancedCheckBox;
    @FindBy(xpath = "//label[@for='userTzSelectBox']")
    private WebElement deliverInUserTZCheckBox;
    @FindBy(xpath = "//label[@for='userTzDropIfTimePassedRadio']")
    private WebElement dropCampaignInUserTZ;
    @FindBy(xpath = "//label[@for='userTzSendNextDayIfTimePassedRadio']")
    private WebElement deliverCampaignNextDayInUserTZ;

    @FindBy(xpath = "//label[@for='dndSelectBox']")
    private WebElement campaignDNDCheckBox;
    @FindBy(xpath = "//label[@for='purge_dnd_radio_button']")
    private WebElement campaignDiscardInDND;
    @FindBy(xpath = "//label[@for='postpone_dnd_radio_button']")
    private WebElement campaignDelayInDND;

    @FindBy(xpath = "//label[@for='tcSelectBox']")
    private WebElement campaignCutOffCheckBox;
    @FindBy(xpath = "//label[@for='throttleCheckBox']")
    private WebElement globalThrottleLimits;


    //Deliver the campaign at

    @FindBy(xpath = "//*[@for='js-best-time-campaign_0']")
    private WebElement deliverBestTimeForUserElement;
    @FindBy(xpath = "//*[@for='js-fixed-time-campaign_0']")
    private WebElement deliverFixTimeForUserElement;
    @FindBy(xpath = "//*[@for='advanceSelectBox']")
    private WebElement whenPageAdvancedCheckbox;
    @FindBy(xpath = "//*[@for='userTzSelectBox']")
    private WebElement deliverUserTimeZone;
    @FindBy(xpath = "//*[@for='userTzDropIfTimePassedRadio']")
    private WebElement dropTheCamapign;
    @FindBy(xpath = "//*[@for='userTzSendNextDayIfTimePassedRadio']")
    private WebElement deliverTheCampaignNextDay;
    @FindBy(xpath = "//*[@for='dndSelectBox']")
    private WebElement dndSelectbox;
    @FindBy(xpath = "//*[@for='purge_dnd_radio_button']")
    private WebElement dndDiscardMesaage;
    @FindBy(xpath = "//*[@for='postpone_dnd_radio_button']")
    private WebElement dndDelayDelivery;
    @FindBy(xpath = "//*[@for='tcSelectBox']")
    private WebElement campaignCutOffCheckbox;
    @FindBy(xpath = "//*[@for='throttleCheckBox']")
    private WebElement globalThrottleLimitsCheckbox;
    @FindBy(id = "throttleWhenDiv")
    private WebElement throttleDivID;
    @FindBy(xpath = "//*[@for='js-best-time-campaign_1']")
    private WebElement bestTimeForEveryUser;
    @FindBy(xpath = "//*[@for='js-fixed-time-campaign_1']")
    private WebElement aFixedTime;

    @FindBy(id = "timezone-container")
    private WebElement timeZone;
    @FindBy(id = "time_critical_div")
    private WebElement campaignCutOff;
    @FindBy(className = "js-throttle-container")
    private WebElement currentThrottleDiv;
    @FindBy(xpath = "//*[@for='js-never-end']")
    private WebElement neverEnd;
    @FindBy(xpath = "//*[@for='js-date-time']")
    private WebElement selectDateAndTime;
    @FindBy(xpath = "//*[@for='excludeFromFC']")
    private WebElement howOftenExclude;
    @FindBy(xpath = "//*[@for='oncePerSession']")
    private WebElement howOftenOncePerPerson;
    @FindBy(xpath = "//*[@for='oncePerDay']")
    private WebElement howOftenOncePerDay;
    @FindBy(xpath = "oncePerLifetime")
    private WebElement howOftenOncePerLifeTime;
    @FindBy(xpath = "//*[@data-note-id='oncePerDay']//li")
    private List<WebElement> howOftenOncePerDayNotesList;
    @FindBy(xpath = "//*[@data-note-id='oncePerLifetime']//li")
    private List<WebElement> howOftenOncePerListTimeNotesList;
    @FindBy(id = "btn_top_nav_continue")
    private WebElement continueButton;


    public void selectWhenPageAdvancedProperties() {
        SeleniumUtils.performClick(driver, whenPageAdvancedCheckbox);

    }

    public WhenPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        sweetAlert = new SweetAlert(driver);
    }

    public void setMessageType(String messageType) {
        switch (messageType.trim()) {
            case CampaignsHomePage.ONE_TIME:
                SeleniumUtils.performClick(driver, oneTime);
                break;
            case CampaignsHomePage.MULTIPLE_DATES:
                SeleniumUtils.performClick(driver, multipleDates);
                break;
            case CampaignsHomePage.RECURRING:
                SeleniumUtils.performClick(driver, recurring);
                break;
            default:

                break;
        }
    }

    public void setWhenPage() throws ParseException {

        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case SMS:
            case EMAIL:
            case WEB_PUSH:
            case WEB_POP_UP:
            case WEB_EXIT_INTENT:
            case WHATSAPP:

                setCampaignStart();
                setCampaignEnd();
                setAdvancedProps();
                break;
            case APP_INBOX:
                setCampaignStart();
                setCampaignEnd();
                break;
            case MOBILE_IN_APP:
            case NATIVE_DISPLAY:
                setCamapignStartDateTime();
                switch (campaignMeta.getWhen().getCampaign_end_type().trim().toLowerCase()) {
                    case NEVER_END:
                        SeleniumUtils.performClick(driver, neverEnd);
                        break;
                    case SELECT_DATE_AND_TIME:
                        SeleniumUtils.performClick(driver, selectDateAndTime);
                        setCampaignDate(DateUtil.getDateTimeInYYYYMMDDObject(campaignMeta.getWhen().getCampaign_start_date()), "campaign-end");
                        TimeSetter timeSetter = new TimeSetter(driver);
                        String xpath = "//*[@id='endTime']";
                        timeSetter.setTime(DateUtil.getCurrentTimeInStringFormat(), xpath);
                        break;
                    default:
                }

                /*Setting how often for mobile in app*/
                if(!campaignMeta.getChannel().equalsIgnoreCase(NATIVE_DISPLAY)){
                switch (campaignMeta.getWhen().getHow_often().trim().toLowerCase()) {
                    case EXCLUDE_FROM_CAMPAIGN_LIMITS:
                        SeleniumUtils.performClick(driver, howOftenExclude);
                        break;
                    case ONCE_PER_PERSON:
                        SeleniumUtils.performClick(driver, howOftenOncePerPerson);
                        break;
                    case ONCE_PER_DAY:
                        SeleniumUtils.performClick(driver, howOftenOncePerDay);
                        Assert.assertEquals(howOftenOncePerDayNotesList.get(0).getText(), "Works for Android and iOS SDK versions 2.0.9 and above");
                        Assert.assertEquals(howOftenOncePerDayNotesList.get(1).getText(), "For Android and iOS SDK versions below 2.0.9 and for Windows Phone, this option will set the limit to once per session");
                        break;
                    case ONCE_PER_USER_FOR_THE_DURATION_OF_THIS_CAMPAIGN:
                        SeleniumUtils.performClick(driver, howOftenOncePerLifeTime);
                        Assert.assertEquals(howOftenOncePerListTimeNotesList.get(0).getText(), "Works for Android and iOS SDK versions 2.0.9 and above");
                        Assert.assertEquals(howOftenOncePerListTimeNotesList.get(1).getText(), "Does not work for Windows Phone");
                        break;
                    default:

                }
        }

                break;
            default:

        }

    }

    public void changeWhenPage() {
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case SMS:
            case EMAIL:
            case WEB_PUSH:
            case WEB_POP_UP:
            case WEB_EXIT_INTENT:
            case WHATSAPP:
                driver.findElement(By.xpath("//ul[@id ='framework_nav_ul']//li[@data-step-name='when']")).click();
                changeCampaignStart();
                changeCampaignEnd();
                break;
            case MOBILE_IN_APP:
                break;
            case APP_INBOX:
                break;
            default:
        }
    }

    private void changeCampaignStart() {
        switch (campaignMeta.getType().toLowerCase()) {
            case CampaignsHomePage.ONE_TIME:
                changeCamapignStartDateTime();
                break;
            case CampaignsHomePage.MULTIPLE_DATES:
                try {
                    setCampaignDate(DateUtil.getDateTimeInYYYYMMDDObject(campaignMeta.getWhen().getCampaign_start_date()), "certainDates_date");
                    switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
                        case BEST_TIME_FOR_EVERY_USER:
                            SeleniumUtils.performClick(driver, bestTimeForEveryUser);
                            break;
                        case A_FIXED_TIME:
                            SeleniumUtils.performClick(driver, aFixedTime);
                            break;
                        default:
                    }
                } catch (ParseException e) {
                    //
                }
                break;
            case CampaignsHomePage.RECURRING:
                try {
                    setCampaignDate(DateUtil.getDateTimeInYYYYMMDDObject(campaignMeta.getWhen().getCampaign_start_date()), "recurrCampaign-startDate");

                    if (campaignMeta.getWhen().getCampaign_delivery() != null) {
                        switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
                            case BEST_TIME_FOR_EVERY_USER:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@for='js-best-time-campaign_2']")));
                                break;
                            case A_FIXED_TIME:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@for='js-fixed-time-campaign_2']")));
                                break;
                            default:
                        }
                    }
                } catch (ParseException e) {
                    //
                }
                break;
            case CampaignsHomePage.SINGLE_ACTION:
                changeCamapignStartDateTime();

                switch (campaignMeta.getWhen().getChange_delay_type().toLowerCase()) {
//            switch (campaignMeta.getWhen().getCampaign_delay()) {
                    case ACTION_CAMPAIGN_NO_DELAY:
                        SeleniumUtils.performClick(driver, delayNoDelay);
                        break;
                    case ACTION_CAMPAIGN_DELAY:
                        SeleniumUtils.performClick(driver, delayDelayBy);
                        SeleniumUtils.enterInputText(driver, delayAmount, campaignMeta.getWhen().getChange_campaign_delay_by());
                        break;
                    default:
                }
                break;
            case CampaignsHomePage.INACTION_WITHIN_TIME:
                setCamapignStartDateTime();
                break;
            case CampaignsHomePage.ON_A_DATE_TIME:
                setCamapignStartDateTime();
                break;
            default:

        }
    }

    public void setCampaignStart() {

        switch (campaignMeta.getType().toLowerCase()) {
            case CampaignsHomePage.ONE_TIME:
                setCamapignStartDateTime();
                break;
            case CampaignsHomePage.MULTIPLE_DATES:

                if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getCampaign_delivery())) {
                    try {
                        setCampaignDate(DateUtil.getDateTimeInYYYYMMDDObject(campaignMeta.getWhen().getCampaign_start_date()), "certainDates_date");
                        switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
                            case BEST_TIME_FOR_EVERY_USER:
                                SeleniumUtils.performClick(driver, bestTimeForEveryUser);
                                break;
                            case A_FIXED_TIME:
                                SeleniumUtils.performClick(driver, aFixedTime);
                                break;
                            default:
                        }
                    } catch (ParseException e) {
                        //
                    }
                }
                break;
            case CampaignsHomePage.RECURRING:
                try {
                    setCampaignDate(DateUtil.getDateTimeInYYYYMMDDObject(campaignMeta.getWhen().getCampaign_start_date()), "recurrCampaign-startDate");

                    if (campaignMeta.getWhen().getCampaign_delivery() != null) {
                        switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
                            case BEST_TIME_FOR_EVERY_USER:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@for='js-best-time-campaign_2']")));
                                break;
                            case A_FIXED_TIME:
                                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@for='js-fixed-time-campaign_2']")));
                                break;
                            default:
                        }
                    }
                } catch (ParseException e) {
                    //
                }
                break;
            case CampaignsHomePage.SINGLE_ACTION:
                setCamapignStartDateTime();
                switch (campaignMeta.getWhen().getCampaign_delay().toLowerCase()) {
                    case ACTION_CAMPAIGN_NO_DELAY:
                        SeleniumUtils.performClick(driver, delayNoDelay);
                        break;
                    case ACTION_CAMPAIGN_DELAY:
                        SeleniumUtils.performClick(driver, delayDelayBy);
                        SeleniumUtils.enterInputText(driver, delayAmount, campaignMeta.getWhen().getCampaign_delay_by());
                        break;
                    default:
                }
                break;
            case CampaignsHomePage.INACTION_WITHIN_TIME:
                setCamapignStartDateTime();
                break;
            case CampaignsHomePage.ON_A_DATE_TIME:
                setCamapignStartDateTime();
                break;
            default:

        }


    }

    public void setCampaignEnd() {


        switch (campaignMeta.getType().toLowerCase()) {
            case CampaignsHomePage.RECURRING:
                switch (campaignMeta.getWhen().getCampaign_end_type().trim()) {
                    case ACTION_END_NEVER_END:
                        campaignNeverEndRec.click();
                        break;
                    case RECURRING_END_SELECT_DATE:
                        SeleniumUtils.performClick(driver, campaignEndDate);
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("yyyyMMdd").parse(DateUtil.get2DaysLaterInYYYYMMDDString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SeleniumUtils.scrollDownLittle(driver);
                        setCampaignDate(date, "recurrCampaign-endDate");
                        break;
                    case RECURRING_END_AFTER:
                        SeleniumUtils.performClick(driver, campaignEndAfterOccurences);
                        SeleniumUtils.enterInputText(driver, campaignEndAfterOccNum, campaignMeta.getWhen().getCampaign_end_occurences());
                        break;
                    default:
                        break;
                }
                break;

            case CampaignsHomePage.SINGLE_ACTION:
            case CampaignsHomePage.INACTION_WITHIN_TIME:
            case CampaignsHomePage.ON_A_DATE_TIME:
                switch (campaignMeta.getWhen().getCampaign_end_type().trim().toLowerCase()) {
                    case ACTION_END_NEVER_END:
                        triggerNeverEnd.click();
                        break;

                    case ACTION_END_ON_SELECT_DATE_TIME:
                        SeleniumUtils.performClick(driver, triggerEndDateRadio);

                        try {
                            Date date = new SimpleDateFormat("yyyyMMdd").parse(DateUtil.get2DaysLaterInYYYYMMDDString());
                            setCampaignDate(date, "campaign-end"); //provide ID of the calendar instance
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }

                        TimeSetter setTime = new TimeSetter(driver);
                        String xPath = "//div[@id='endTime']";
                        setTime.setTime(DateUtil.getCustomTime(20), xPath);
                    default:


                }
                break;
            default:

        }
    }

    public void changeCampaignEnd()  {

        switch (campaignMeta.getType().toLowerCase()) {
            case CampaignsHomePage.RECURRING:
                switch (campaignMeta.getWhen().getCampaign_end_type().trim()) {
                    case ACTION_END_NEVER_END:
                        campaignNeverEndRec.click();
                        break;
                    case RECURRING_END_SELECT_DATE:
                        SeleniumUtils.performClick(driver, campaignEndDate);
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("yyyyMMdd").parse(DateUtil.get2DaysLaterInYYYYMMDDString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SeleniumUtils.scrollDownLittle(driver);
                        setCampaignDate(date, "recurrCampaign-endDate");
                        break;
                    case RECURRING_END_AFTER:
                        SeleniumUtils.performClick(driver, campaignEndAfterOccurences);
                        SeleniumUtils.enterInputText(driver, campaignEndAfterOccNum, campaignMeta.getWhen().getCampaign_end_occurences());
                        break;
                    default:
                        break;
                }
                break;

            case CampaignsHomePage.SINGLE_ACTION:
            case CampaignsHomePage.INACTION_WITHIN_TIME:
            case CampaignsHomePage.ON_A_DATE_TIME:
                switch (campaignMeta.getWhen().getChange_campaign_endType().trim().toLowerCase()) {
                    case ACTION_END_NEVER_END:
                        triggerNeverEnd.click();
                        break;

                    case ACTION_END_ON_SELECT_DATE_TIME:
                        SeleniumUtils.performClick(driver, triggerEndDateRadio);

                        try {
                            Date date = new SimpleDateFormat("yyyyMMdd").parse(DateUtil.get2DaysLaterInYYYYMMDDString());
                            setCampaignDate(date, "campaign-end"); //provide ID of the calendar instance
                        } catch (ParseException e) {
                            logger.error(e.getMessage());
                        }

                        TimeSetter setTime = new TimeSetter(driver);
                        String xPath = "//div[@id='endTime']";
                        setTime.setTime(DateUtil.getCustomTime(20), xPath);
                    default:


                }
                break;
            default:

        }
    }

    public void setAdvancedProps() {

        if(!SeleniumUtils.isNullOrEmpty(whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()))
                && !SeleniumUtils.isNullOrEmpty(whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(CAMPAIGN_DO_NOT_DISTURB))) {

            whenPageAdvancedCheckbox.click();
            SeleniumUtils.pause(3);
            if (driver.findElement(By.id("advance_div")).getAttribute("style").equalsIgnoreCase("display: none;")) {
                whenPageAdvancedCheckbox.click();
                SeleniumUtils.pause(3);
                whenPageAdvancedCheckbox.click();
                SeleniumUtils.pause(3);
            }

            switch (campaignMeta.getType().trim().toLowerCase()) {
                case "one time":
                    switch (campaignMeta.getWhen().getCampaign_start_type().trim().toLowerCase()) {
                        case START_NOW:
                            setWhenPageAdvancePropsUtil();
                            break;
                        case START_LATER:
                            setDeliverTheCampaignAtProps();
                            break;
                        default:
                    }
                    break;
                case MULTIPLE_DATES:
                    setDeliverTheCampaignAtProps();
                    break;
                case RECURRING:
                    switch (campaignMeta.getWhen().getCampaign_end_type().trim().toLowerCase()) {
                        case NEVER_END:
                        case SELECT_DATE:
                        case AFTER:
                            setDeliverTheCampaignAtProps();
                            break;
                        default:
                    }
                case "single action":
                case "inaction within time":
                case "on a date/time":
                    SeleniumUtils.performClick(driver, dndSelectbox);


                    switch (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key()).get(CAMPAIGN_DO_NOT_DISTURB)) {
                        case DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS:
                            SeleniumUtils.performClick(driver, dndDiscardMesaage);
                            break;
                        case DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS:
                            SeleniumUtils.performClick(driver, dndDelayDelivery);
                            break;
                        default:
                            logger.info(NOT_A_VALID_TYPE_OF_CAMPAIGN_DND);
                    }
                    break;
                default:

            }
        }else{
            Reporter.log("Advanced properties not included in when page while creating campaign",true);
        }
    }

    private void setDeliverTheCampaignAtProps() {
        switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
            case BEST_TIME_FOR_EVERY_USER:
                Assert.assertTrue(timeZone.getAttribute(STYLE).equalsIgnoreCase(DISPLAY_NONE));
                Assert.assertTrue(campaignCutOff.getAttribute(STYLE).equalsIgnoreCase(DISPLAY_NONE));
                /*global throttle limit div does not have style as display:none;, Ninad has to put this.
                Assert.assertTrue(currentThrottleDiv.getAttribute(STYLE).equalsIgnoreCase(DISPLAY_NONE));*/
                setDNDOnWhenPageAdvanceSettings();
                break;
            case A_FIXED_TIME:
                setWhenPageAdvancePropsUtil();
                break;
            default:
        }
    }

    private void setWhenPageAdvancePropsUtil() {


        switch (campaignMeta.getCsv_key().trim().toLowerCase().split("-")[0]) {
            case "mobile push+one time":
            case "email+one time":
            case "sms+one time":
            case "suc33389":

                /*when time zone will be selected then throttle limit wont be enabled to configure. so adjust the test data accordingly*/
                setTZOnWhenPageAdvanceSettings();
                setDNDOnWhenPageAdvanceSettings();
                setCampaignCutOffOnWhenPageAdvanceSettings();
                setThrottleLimitOnWhenPageAdvanceSettings();

                break;
            case "web push+one time":

                setTZOnWhenPageAdvanceSettings();
                setDNDOnWhenPageAdvanceSettings();
                setCampaignCutOffOnWhenPageAdvanceSettings();

                break;

            case "mobile push+single action" :
                setDNDOnWhenPageAdvanceSettings();
                break;
            default:
        }

    }

    private void setThrottleLimitOnWhenPageAdvanceSettings() {
        switch (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get("Global_throttle_limits").trim().toLowerCase()) {
            case "check":
                SeleniumUtils.performClick(driver, globalThrottleLimitsCheckbox);
                break;
            case "uncheck":
                break;
            default:
        }
    }

    private void setCampaignCutOffOnWhenPageAdvanceSettings() {
        switch (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get("Campaign_cutoff").trim().toLowerCase()) {
            case "check":
                SeleniumUtils.performClick(driver, campaignCutOffCheckBox);
                setCampaignCuttOffTime();
                break;
            case "uncheck":
                break;
            default:
        }
    }

    private void setDNDOnWhenPageAdvanceSettings() {

        if(!SeleniumUtils.isNullOrEmpty(whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()))
        && !SeleniumUtils.isNullOrEmpty(whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(CAMPAIGN_DO_NOT_DISTURB))){
            switch (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(CAMPAIGN_DO_NOT_DISTURB).trim().toLowerCase()) {
                case DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS2:
                    SeleniumUtils.performClick(driver, dndSelectbox);
                    SeleniumUtils.performClick(driver, dndDiscardMesaage);
                    break;
                case DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS2:
                    SeleniumUtils.performClick(driver, dndSelectbox);
                    SeleniumUtils.performClick(driver, dndDelayDelivery);
                    break;
                default:
                    logger.info(NOT_A_VALID_TYPE_OF_CAMPAIGN_DND);
            }
        }else{
            Reporter.log("Advanced settings not included in when page while creating campaign.",true);
        }


    }

    private void setTZOnWhenPageAdvanceSettings() {
        switch (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(TIME_ZONE).trim().toLowerCase()) {
            case DROP_THE_CAMPAIGN:
                SeleniumUtils.performClick(driver, deliverUserTimeZone);
                SeleniumUtils.performClick(driver, dropTheCamapign);
                break;
            case DELIVER_THE_CAMPAIGNS_THE_NEXT_DAY:
                SeleniumUtils.performClick(driver, deliverUserTimeZone);
                SeleniumUtils.performClick(driver, deliverTheCampaignNextDay);
                break;
            default:
                logger.info("!!!! not a valid type of time_zone !!!!!!");
        }
    }

    private void setCampaignCuttOffTime() {
        TimeSetter setTime = new TimeSetter(driver);
        String xPath = "//div[@id='tcTime']";

        /*putting validation tocheck if cuttoff time is behind the campaign start time then it should fail*/

        if (driver.findElement(By.id("dndSelectBox")).isSelected()) {

            String[] hhmmA = DateUtil.getCurrentTimeInStringFormat().split(" ");
            String[] hhmm = hhmmA[0].split(":");
            int hh = Integer.parseInt(hhmm[0]) - 1;
            String hhmmaaPrev = "";
            String hhmmaaLater = "";


            if (hhmmA[1].equalsIgnoreCase("PM")) {
                if (Integer.parseInt(hhmm[0]) - 1 < Integer.parseInt("12")) {
                    hhmmaaPrev = Integer.parseInt(hhmm[0]) - 1 + ":" + hhmm[1] + " AM";
                } else {
                    hhmmaaPrev = Integer.parseInt(hhmm[0]) - 1 + ":" + hhmm[1] + " PM";
                }
            } else {
                if (Integer.parseInt(hhmm[0]) - 1 < Integer.parseInt("00")) {
                    hhmmaaPrev = Integer.parseInt(hhmm[0]) - 1 + ":" + hhmm[1] + " PM";
                } else {
                    hhmmaaPrev = Integer.parseInt(hhmm[0]) - 1 + ":" + hhmm[1] + " AM";
                }
            }


            if (Integer.parseInt(hhmmaaPrev.split(":")[0]) < 10) {
                hhmmaaPrev = "0" + hhmmaaPrev.split(":")[0] + ":" + hhmmaaPrev.split(":")[1];
            }

            setTime.setTime(hhmmaaPrev, xPath);
            SeleniumUtils.performClick(driver, continueButton);
            SeleniumUtils.pause(1);
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal());

            if (hhmmA[1].equalsIgnoreCase("PM")) {
                if (Integer.parseInt(hhmm[0]) + 2 < Integer.parseInt("00")) {
                    hhmmaaLater = Integer.parseInt(hhmm[0]) + 2 + ":" + hhmm[1] + " PM";
                } else {
                    hhmmaaLater = Integer.parseInt(hhmm[0]) + 2 + ":" + hhmm[1] + " AM";
                }
            } else {
                if (Integer.parseInt(hhmm[0]) + 2 > Integer.parseInt("00")) {
                    hhmmaaLater = Integer.parseInt(hhmm[0]) + 2 + ":" + hhmm[1] + " AM";
                } else {
                    hhmmaaLater = Integer.parseInt(hhmm[0]) + 2 + ":" + hhmm[1] + " PM";
                }
            }

            setTime.setTime(hhmmaaLater, xPath);

        } else {
        }
    }

    public void setCampaignDelay() {
        switch (campaignMeta.getType().trim().toLowerCase()) {
            case CampaignsHomePage.SINGLE_ACTION:
                switch (campaignMeta.getWhen().getCampaign_delay()) {
                    case ACTION_CAMPAIGN_NO_DELAY:
                        SeleniumUtils.performClick(driver, delayNoDelay);
                        break;
                    case ACTION_CAMPAIGN_DELAY:
                        SeleniumUtils.performClick(driver, delayDelayBy);
                        SeleniumUtils.enterInputText(driver, delayAmount, campaignMeta.getWhen().getCampaign_delay_by());
                        break;
                    default:
                }
                break;
            case CampaignsHomePage.ONE_TIME:
            case CampaignsHomePage.MULTIPLE_DATES:
            case CampaignsHomePage.RECURRING:
            case CampaignsHomePage.INACTION_WITHIN_TIME:
            case CampaignsHomePage.ON_A_DATE_TIME:
                logger.info(" other campaign has no option for campaign deplay/No delay");
                break;
            default:


        }
    }


    public void setCampaignDate(Date date, String calendarID) {

        String year = new SimpleDateFormat("yyyy").format(date);
        String dateNum = new SimpleDateFormat("d").format(date);
        String month = new SimpleDateFormat("MMM").format(date);
        try {
            SeleniumUtils.pause(5);
        } catch (Exception e) {
            logger.error(e.getMessage());

        }
        SeleniumUtils.waitForPageLoaded(driver);
        driver.findElement(By.id(calendarID)).click();
        SeleniumUtils.pause(2);
        driver.findElement(By.xpath(ID + calendarID + "']//div[@class='ui-datepicker-title']//span)[1]")).click();
        driver.findElement(By.xpath(ID + calendarID + "']//div[@class='ui-datepicker-title']//span)[1]/../..//li[text()='" + month + "']")).click();
        driver.findElement(By.xpath(ID + calendarID + "']//div[@class='ui-datepicker-title']//span)[2]")).click();
        driver.findElement(By.xpath(ID + calendarID + "']//div[@class='ui-datepicker-title']//span)[2]/../..//li[text()='" + year + "']")).click();
        SeleniumUtils.pause(2);
        driver.findElement(By.xpath("//*[@id='" + calendarID + "']//*[@class='ui-datepicker-calendar']//a[text()='" + dateNum + "']")).click();
        SeleniumUtils.pause(1);

    }

    public void setCamapignStartDateTime() {

        switch (campaignMeta.getWhen().getCampaign_start_type().trim().toLowerCase()) {

            case START_NOW:
                SeleniumUtils.performClick(driver, campaignStartNow);
                break;
            case START_LATER:
                SeleniumUtils.performClick(driver, campaignStartLater);

                try {
                    setCampaignDate(DateUtil.getDateTimeInYYYYMMDDObject(campaignMeta.getWhen().getCampaign_start_date()), "campaign-start");
                    //set the deliver the campaign at
                    logger.info("******before applyCampaignDeliveryTime******* " + campaignMeta.getWhen().getCampaign_delivery());
                    switch (campaignMeta.getChannel().trim().toLowerCase()) {
                        case MOBILE_PUSH:
                        case SMS:
                        case APP_INBOX:
                        case EMAIL:
                        case WEB_PUSH:
                        case WEB_POP_UP:
                            applyCampaignDeliveryTime();
                            break;
                        case MOBILE_IN_APP:
                        case NATIVE_DISPLAY:
                            TimeSetter timeSetter = new TimeSetter(driver);
                            String xpath = "//*[@id='fixedTimeWidget_0']";
                            timeSetter.setTime(campaignMeta.getWhen().getCampaign_delivery_fixed_time(), xpath);
                            break;
                        default:
                    }


                } catch (ParseException e) {
                    //
                }
                break;
            default:

        }
        logger.info("******setCamapignStartDateTime******* " + campaignMeta.getWhen().getCampaign_delivery());
    }

    public void changeCamapignStartDateTime() {

        switch (campaignMeta.getWhen().getChange_campaign_startType().trim().toLowerCase()) {

            case START_NOW:
                SeleniumUtils.performClick(driver, campaignStartNow);
                break;
            case START_LATER:
                SeleniumUtils.performClick(driver, campaignStartLater);

                try {
                    try {
                        setCampaignDate(DateUtil.getDateTimeInYYYYMMDDObject(campaignMeta.getWhen().getCampaign_start_date()), "campaign-start");

                    } catch (ParseException e) {
                        //
                    }
                }catch (ElementClickInterceptedException e){
                    try {
                        SeleniumUtils.scrollUp(driver);
                        setCampaignDate(DateUtil.getDateTimeInYYYYMMDDObject(campaignMeta.getWhen().getCampaign_start_date()), "campaign-start");
                    } catch (ParseException e1) {

                    }
                }


                break;
            default:

        }
        logger.info("******changeCamapignStartDateTime******* " + campaignMeta.getWhen().getCampaign_delivery());
    }

    public void applyCampaignDeliveryTime() {
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case MOBILE_IN_APP:
            case SMS:
            case EMAIL:
            case WEB_PUSH:
            case "web pop-up":
            case WEB_EXIT_INTENT:
            case WHATSAPP:
            case "audiences":
            case "google ads":
            case "webhooks":
                switch (campaignMeta.getType().trim().toLowerCase()) {
                    case CampaignsHomePage.ONE_TIME:
                    case CampaignsHomePage.MULTIPLE_DATES:
                    case CampaignsHomePage.RECURRING:
                        if (campaignMeta.getWhen().getCampaign_delivery().equalsIgnoreCase(BEST_TIME_FOR_EVERY_USER)) {
                            SeleniumUtils.performClick(driver, deliverBestTimeForUserElement);
                            return;
                        } else if (campaignMeta.getWhen().getCampaign_delivery().equalsIgnoreCase(A_FIXED_TIME)) {
                            SeleniumUtils.performClick(driver, deliverFixTimeForUserElement);
                            TimeSetter timeSetter = new TimeSetter(driver);
                            String xpath = "//*[@id='fixedTimeWidget_0']";
                            timeSetter.setTime(DateUtil.getCurrentTimeInStringFormat(), xpath);
                        }
                        break;

                    case CampaignsHomePage.SINGLE_ACTION:
                    case CampaignsHomePage.INACTION_WITHIN_TIME:
                    case CampaignsHomePage.ON_A_DATE_TIME:
                        logger.info(" Do something else as required");
                    default:

                }
                break;
            case APP_INBOX:
                break;
            default:
        }
    }


    public void getWhenSummary() {
        Map<String, String> whenSummary = new HashMap<>();

        List<WebElement> whenSpans = driver.findElements(By.xpath("//*[@id='summaryWhen']//span"));
        for (int i = 1; i <= whenSpans.size(); i++) {
            WebElement div = driver.findElement(By.xpath("(//*[@id='summaryWhen']//span)[" + i + "]/.."));
            whenSummary.put(whenSpans.get(i).getText(), div.getText());
            logger.info("************** " + whenSummary.keySet());
            logger.info("*************** " + whenSummary.get(whenSummary.keySet()));
        }

    }
}
