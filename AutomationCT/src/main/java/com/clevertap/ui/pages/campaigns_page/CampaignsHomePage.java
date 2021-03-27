package com.clevertap.ui.pages.campaigns_page;

import com.clevertap.ui.pages.Login;
import com.clevertap.ui.pages.segment_pages.Segments;
import com.clevertap.ui.pages.widget.DidNotWidget;
import com.clevertap.ui.pages.widget.DidWidget;
import com.clevertap.ui.pages.widget.SegmentWidget;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Data;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static com.clevertap.utils.DashboardEnums.campaignCrudOperation;
import static com.clevertap.utils.LoadYamlFile.campaignMeta;
import static com.clevertap.utils.LoadYamlFile.whenPageAdvanceSettingsCSVMap;
import static com.jayway.restassured.RestAssured.given;


public class CampaignsHomePage {

    public static final String EDITED = "-edited";
    public static final String LATER = "later";
    public static final String NOW = "now";
    public static final String CLASS = "class";
    public static final String A_FIXED_TIME = "a fixed time";
    public static final String BEST_TIME_FOR_EVERY_USER = "best time for every user";
    public static final String DND_SELECT_BOX = "dndSelectBox";
    public static final String PURGE_DND_RADIO_BUTTON = "purge_dnd_radio_button";
    public static final String TIME_ZONE = "Time_zone";
    public static final String DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS = "discard messages scheduled during dnd hours";
    public static final String DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS = "delay delivery until the end of dnd hours";
    public static final String DROP_THE_CAMPAIGN = "drop the campaign";
    public static final String DELIVER_THE_CAMPAIGNS_THE_NEXT_DAY = "deliver the campaigns the next day";
    public static final String USER_TZ_DROP_IF_TIME_PASSED_RADIO = "userTzDropIfTimePassedRadio";
    public static final String USER_TZ_SEND_NEXT_DAY_IF_TIME_PASSED_RADIO = "userTzSendNextDayIfTimePassedRadio";
    public static final String POSTPONE_DND_RADIO_BUTTON = "postpone_dnd_radio_button";
    public static final String CAMPAIGN_DO_NOT_DISTURB = "Campaign_do_not_disturb";

    public static final String SAVED_SEGMENTS = "saved segments";

    public static final String ACTIONS = "actions";
    public static final String INACTION = "inaction";
    public static final String ACTIONS_WITH_USER_PROPERTIES = "actions with user properties";
    public static final String ON_A_DATE_TIME1 = "on a date/ time";
    public static final String COMPLETED = "completed";
    public static final String RUNNING = "running";
    public static final String STOPPED = "stopped";
    public static final String AWAITING_NEXT_RUN = "awaiting next run";
    public static final String DRAFT = "draft";
    public static final String SCHEDULED = "scheduled";
    public static final String APPROVAL_PENDING = "approval pending";

    public static final String TV = "tv";

    public static final String INPUT = "']/../../input";
    public static final String CLASS_SELECTOR_GROUP_PLATFORM_DEVICES_BOX_SPAN_TEXT = "//*[@class='selector-group platform devices-box']//span[text()='";
    public static final String SCHEDULED_FINALLY = "scheduledFinally";
    public static final String DATA_STEP_NAME_WHEN_A = "//*[@data-step-name='when']/a";
    public static final String INBOX = "app inbox";

    private WebDriver driver;
    private WhenPage whenPage;
    private WhoPage whoPage;
    private WhatPage whatPage;
    private SetupPage setupPage;
    public SweetAlert sweetAlert;
    private Segments segments;
    Logger logger = Logger.getLogger(getClass().getSimpleName());
    private String campaignName;

    private SegmentWidget segmentWidget;
    private DidWidget didWidget;
    private DidNotWidget didNotWidget;

    private static final String DIV_CLASS_FILTERBTN_AND_TEXT = "//div[@class='filterbtn' and text()='";


    public static final String MOBILE_PUSH = "mobile push";
    public static final String MOBILE_INAPP = "mobile in-app";
    public static final String SMS = "sms";
    public static final String EMAIL = "email";
    public static final String APP_INBOX = "app inbox";
    public static final String WEB_PUSH = "web push";
    public static final String WEB_POPUP = "web pop-up";
    public static final String WEB_EXIT_INTENT = "web exit intent";
    public static final String WHATSAPP = "whatsapp";
    public static final String FB_AUDIENCES = "fbaudiences";
    public static final String GOOGLE_ADS = "google ads";
    public static final String WEBHOOKS = "webhooks";
    public static final String INCLUDE_APP_INBOX = "include App Inbox";
    public static final String NATIVEDISPLAY = "native display";

    /*Keeping below constants as public as they are used in other classes as well*/

    public static final String ONE_TIME = "one time";
    public static final String MULTIPLE_DATES = "multiple dates";
    public static final String RECURRING = "recurring";
    public static final String SINGLE_ACTION = "single action";
    public static final String INACTION_WITHIN_TIME = "inaction within time";
    public static final String ON_A_DATE_TIME = "on a date/time";
    public static final String EXTERNAL_TRIGGER = "external trigger";
    public static final String SINGLEMESSAGE = "singlemessage";
    public static final String A_B = "a/b";
    public static final String MULTIVARIATE = "multivariate";
    public static RequestSpecification requestSpecification = null;
    public static JSONObject createPushCampaignApiPayLoad = null;
    public static Response response = null;
    public static JsonPath jsonPath = null;

    protected static final List<String> PBSCampaign = Arrays.asList(ONE_TIME, MULTIPLE_DATES, RECURRING);
    protected static final List<String> actionCampaign = Arrays.asList(SINGLE_ACTION, INACTION_WITHIN_TIME, ON_A_DATE_TIME);
    public List<String> listOfCampaignAction = new ArrayList<>();

    @FindBy(xpath = "//button[contains(@class,'js-createCampaign')]")
    public WebElement campaignCreateBtn;

    @FindBy(id = "btn_top_nav_continue")
    private WebElement continueButton;
    @FindBy(xpath = "//*[contains(@class,'templates-continue-button')]")
    private WebElement templateContinueBtn;
    @FindBy(id = "jname")
    private WebElement campaignNameTemp;
    @FindBy(xpath = "//div[contains(@class,'showSweetAlert')]//button[@class='confirm']")
    private WebElement saveCampaignButton;

    //campaign channels
    @FindBy(xpath = "//*[@data-mode='push']")
    private WebElement push;
    @FindBy(xpath = "//*[@data-mode='inapp']")
    private WebElement inapp;
    @FindBy(xpath = "//*[@data-mode='sms']")
    private WebElement sms;
    @FindBy(xpath = "//*[@data-mode='notificationinbox']")
    private WebElement appInBox;
    @FindBy(xpath = "//*[@data-mode='email']")
    private WebElement email;
    @FindBy(xpath = "//*[@data-mode='browser']")
    private WebElement webPush;
    @FindBy(xpath = "//*[@data-mode='web_popup']")
    private WebElement webPopup;
    @FindBy(xpath = "//*[@data-mode='web_exit']")
    private WebElement webExitIntent;
    @FindBy(xpath = "//*[@data-mode='whatsApp']")
    private WebElement whatsApp;
    @FindBy(xpath = "//*[@data-mode='fb']")
    private WebElement fbAudience;
    @FindBy(xpath = "//*[@data-mode='googleadwords']")
    private WebElement googleAdWords;
    @FindBy(xpath = "//*[@data-mode='webhooks']")
    private WebElement webHooks;
    @FindBy(xpath = "//*[@class='js-step-name' and text()='Setup']")
    private WebElement setUp;
    @FindBy(xpath = "//*[@data-mode='nativedisplay']")
    private WebElement nativeDisplay;

    //campaign types
    @FindBy(xpath = "//div[@id='past-user-block']//li[@id='campaignType0']")
    private WebElement oneTimePastBehaviour;
    @FindBy(xpath = "//div[@id='past-user-block']//li[@id='campaignType1']")
    private WebElement multipleDatesPastBehaviour;
    @FindBy(xpath = "//div[@id='past-user-block']//li[@id='campaignType2']")
    private WebElement recurringPastBehaviour;
    @FindBy(xpath = "//div[@id='live-user-block']//li[@id='campaignType0']")
    private WebElement singleActionLiveUser;
    @FindBy(xpath = "//div[@id='live-user-block']//li[@id='campaignType1']")
    private WebElement inactionWithinTimeLiveUser;
    @FindBy(xpath = "//div[@id='live-user-block']//li[@id='campaignType2']")
    private WebElement onADateTimeLiveUser;
    @FindBy(xpath = "//div[@id='external-trigger-block']//li[@id='campaignType0']")
    private WebElement externalTrigger;

    @FindBy(xpath = "//a[contains(@class,'campaign-edit')]")
    private WebElement editCampaign;
    @FindBy(id = "builderCustomName")
    private WebElement campaignNameBox;
    @FindBy(id = "btn_top_nav_continue")
    private WebElement continueBtn;
    @FindBy(className = "breadcrumb-text-overflow")
    private WebElement cloneCampaignBreadcrum;
    @FindBy(xpath = "//a[contains(@class,'campaign-clone')]")
    private WebElement cloneCampaignBtn;
    @FindBy(xpath = "//a[contains(@class,'campaign-stop')]")
    private WebElement stopCampaignButton;
    @FindBy(id = "searchDiv")
    private WebElement searchBox;
    @FindBy(xpath = "//label[@for='selectId']/span")
    private WebElement selectAllVisibleCampaignBtn;
    @FindBy(className = "bulk_stop_link")
    private WebElement bulkStopBtn;
    @FindBy(className = "archive_link")
    private WebElement archiveBtn;

    //validate the campaign who,waht,when and setup details
    @FindBy(xpath = "//*[@id='summaryWho']//span")
    private WebElement whoSection;
    @FindBy(xpath = "//*[@id='summaryWhen']//span")
    private WebElement whenSection;
    @FindBy(xpath = "//*[@class='ct-nav-tabs']/li")
    private List<WebElement> campaignTabs;

    @FindBy(xpath = "//*[@id='campaign_bulk_stop']/a")
    private WebElement bulkStopButton;

    @FindBy(xpath = "//*[contains(@class,'js-pane-container_close')]")
    private WebElement frameCloseButton;

    @FindBy(xpath = "//*[@data-note-id='oncePerDay']//li")
    private List<WebElement> oncePerDayNotes;

    @FindBy(xpath = "//*[@data-note-id='oncePerLifetime']//li")
    private List<WebElement> oncePerLifeTimeNotes;
    @FindBy(xpath = "//*[@class='selector-group platform']//input")
    private List<WebElement> osListView;
    @FindBy(id = "btn_save_as_draft")
    private WebElement saveAsDraftBtn;
    @FindBy(id = "createSegmentBtn")
    private WebElement createSegmentButton;
    @FindBy(id = "searchDiv")
    private WebElement searchSegment;
    @FindBy(xpath = "//div[contains(@class,'segment-delete')]")
    private WebElement clickDots;
    @FindBy(xpath = "//a[contains(@class,'segment-delete')]")
    private WebElement deleteSeg;
    @FindBy(xpath = "(//div[contains(@class,'archivable ')])[1]")
    private WebElement campaignActions;
    @FindBy(xpath = "//label[contains(@for,'inbox')]")
    private WebElement appInboxCheckbox;
    @FindBy(id = "ttlInputDays")
    private WebElement appInboxTime;

    @FindBy(xpath = "//div[contains(@class,'body')]//div[contains(@class,'cellindex_6')]")
    private WebElement campaignStatus;
    @FindBy(xpath = "//a[contains(@class,'name')]")
    private WebElement clickSelectedCampaign;
    @FindBy(xpath = "//div[@class = 'filterbtn' and text() ='Status']")
    private WebElement statusFilter;
    @FindBy(xpath = "(//a[text()='Apply'])[3]")
    private WebElement applyButton;
    @FindBy(id = "totalClicked")
    private WebElement notificationClickedCount;
    @FindBy(id = "targetId")
    private WebElement campaignID;

    public String getStatCount() {
        clickSelectedCampaign.click();
        SeleniumUtils.pause(2);
        return notificationClickedCount.getText();
    }

    public String getCampaignID() {
        return campaignID.getText();
    }

    public void closeCampaignFrame() {
        driver.switchTo().defaultContent();
        SeleniumUtils.performClick(driver, frameCloseButton);
        SeleniumUtils.pause(1);
        driver.switchTo().frame(0);
        sweetAlert.sweetAlertConfirmOK();
        if ((campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()).equals(SAVED_SEGMENTS)) {
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), null);
            deleteSegment();
        }
    }

    public void deleteSegment() {
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), null);
        SeleniumUtils.enterInputText(driver, searchSegment, Keys.chord(campaignMeta.getCsv_key() + Keys.RETURN));
        clickDots.click();
        deleteSeg.click();
        SeleniumUtils.pause(2);
        sweetAlert.sweetAlertConfirmOK();
    }


    public CampaignsHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        sweetAlert = new SweetAlert(this.driver);
    }


    public String createCampaign() throws InterruptedException, ParseException {
        whoPage = new WhoPage(driver);

        if ((campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()).equals(SAVED_SEGMENTS)) {
            createNewSegment();
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), null);
        }

        openCreateCampaignsFrame();

        campaignName = Data.randomAlphaNumeric(campaignMeta.getCampaign_name(), 4);
        logger.info("--Campaign to be created: " + campaignName + " --Channel to be selected: " + campaignMeta.getChannel() + " --and type is: " + campaignMeta.getType());
        SeleniumUtils.pause(1);
        selectCampaignChannel();
        selectCampaignType();
        whenPage = new WhenPage(driver);
        SeleniumUtils.pause(2); /*dashboard is responding slow so putting long wait. can be removed later*/
        whenPage.setWhenPage();
        clickContinue();
        checkForWarning();
        whoPage = new WhoPage(driver);
        whoPage.setWhoPage();
        if (!campaignMeta.getChannel().equalsIgnoreCase(INBOX)) {
            clickContinue();
        }

        whatPage = new WhatPage(driver);
        whatPage.setWhatPage();

        clickContinue();

        try {
            switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
                case "single message":
                case "message on user property":
                    break;
                case "a/b":
                    clickContinue();
                    break;
            }
        } catch (NullPointerException ne) {
            //do nothing
        }
        setupPage = new SetupPage(driver);
        setupPage.setSetupPage();
        clickContinue();
        SeleniumUtils.pause(2);

        clickContinue();
        campaignNameTemp.sendKeys(campaignName);
        SeleniumUtils.pause(4);
        SeleniumUtils.performClick(driver, saveCampaignButton);
        SeleniumUtils.pause(4);
        return campaignName;
    }

    public String createsaveAsDraftCampaign() throws InterruptedException, ParseException {
        whoPage = new WhoPage(driver);

        if ((campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()).equals(SAVED_SEGMENTS)) {
            createNewSegment();
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), null);
        }

        openCreateCampaignsFrame();
        campaignName = Data.randomAlphaNumeric(campaignMeta.getCampaign_name(), 4);
        logger.info("--Campaign to be created: " + campaignName + " --Channel to be selected: " + campaignMeta.getChannel() + " --and type is: " + campaignMeta.getType());
        selectCampaignChannel();
        selectCampaignType();
        whenPage = new WhenPage(driver);
        SeleniumUtils.pause(2); /*dashboard is responding slow so putting long wait. can be removed later*/
        whenPage.setWhenPage();
        clickContinue();
        checkForWarning();
        whoPage.setWhoPage();
        if (!campaignMeta.getChannel().equalsIgnoreCase(INBOX)) {
            clickContinue();
        }
        whatPage = new WhatPage(driver);
        whatPage.setWhatPage();
        clickContinue();
        setupPage = new SetupPage(driver);
        setupPage.setSetupPage();
        clickContinue();
        SeleniumUtils.pause(4);
        saveAsDraftBtn.click();
        campaignNameTemp.sendKeys(campaignName);
        SeleniumUtils.performClick(driver, saveCampaignButton);
        SeleniumUtils.performClick(driver, saveCampaignButton);
        SeleniumUtils.pause(3);
        closeCampaignFrame();
        return campaignName;
    }

    private void createNewSegment() throws InterruptedException {
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), null);
        segments = new Segments(driver);
        createSegmentButton.click();
        switch (campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()) {
            case ACTIONS:
            case INACTION:
            case ACTIONS_WITH_USER_PROPERTIES:
            case SINGLE_ACTION:
            case INACTION_WITHIN_TIME:
            case ON_A_DATE_TIME1:
                segments.clickSegment(campaignMeta.getWho().getSaved_segment_type().trim());
                segments.setQueryFromCSV();
                segments.saveSegment(campaignMeta.getCsv_key());
                break;
            default:
                logger.info("Invalid option selected");
        }
    }

    public void clickContinue() {
        SeleniumUtils.performClick(driver, continueButton);

    }

    public void checkForWarning() {
        try {
            sweetAlert.getSweetAlertWarningSignal();
        } catch (Exception e) {
            logger.info(e.getMessage() + " No warning found");
        }

    }

    public void openCreateCampaignsFrame() {
        boolean createCampaignBtnEnabled=false;
        while (!campaignCreateBtn.isEnabled()){
            createCampaignBtnEnabled=campaignCreateBtn.isEnabled();
            System.out.println("***********createCampaignBtnEnabled found enabled***********"+createCampaignBtnEnabled);

        }
        SeleniumUtils.performClick(driver, campaignCreateBtn);
        driver.switchTo().frame(0);
    }

    public void selectCampaignChannel() {

        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
                SeleniumUtils.waitAndClick(driver, push);
                break;
            case MOBILE_INAPP:
                SeleniumUtils.performClick(driver, inapp);
                break;
            case SMS:
                SeleniumUtils.performClick(driver, sms);
                break;
            case APP_INBOX:
                SeleniumUtils.performClick(driver, appInBox);
                break;
            case EMAIL:
                SeleniumUtils.performClick(driver, email);
                break;
            case WEB_PUSH:
                SeleniumUtils.performClick(driver, webPush);
                break;
            case WEB_POPUP:
                SeleniumUtils.performClick(driver, webPopup);
                break;
            case WEB_EXIT_INTENT:
                SeleniumUtils.performClick(driver, webExitIntent);
                break;
            case WHATSAPP:
                SeleniumUtils.performClick(driver, whatsApp);
                break;
            case FB_AUDIENCES:
                SeleniumUtils.performClick(driver, fbAudience);
                break;
            case GOOGLE_ADS:
                SeleniumUtils.performClick(driver, googleAdWords);
                break;
            case WEBHOOKS:
                SeleniumUtils.performClick(driver, webHooks);
                break;
            case NATIVEDISPLAY:
                SeleniumUtils.performClick(driver,nativeDisplay);
                break;

            default:
        }
    }

    public void selectCampaignType() {
        if (!(campaignMeta.getChannel().equalsIgnoreCase(MOBILE_INAPP)) && !(campaignMeta.getChannel().equalsIgnoreCase(NATIVEDISPLAY))) {
            switch (campaignMeta.getType().trim().toLowerCase()) {
                case ONE_TIME:
                    SeleniumUtils.performClick(driver, oneTimePastBehaviour);
                    break;
                case MULTIPLE_DATES:
                    SeleniumUtils.performClick(driver, multipleDatesPastBehaviour);
                    break;
                case RECURRING:
                    SeleniumUtils.performClick(driver, recurringPastBehaviour);
                    break;
                case SINGLE_ACTION:
                    SeleniumUtils.waitAndClick(driver, singleActionLiveUser);
                    break;
                case INACTION_WITHIN_TIME:
                    SeleniumUtils.performClick(driver, inactionWithinTimeLiveUser);
                    break;
                case ON_A_DATE_TIME:
                    SeleniumUtils.performClick(driver, onADateTimeLiveUser);
                    break;
                case EXTERNAL_TRIGGER:
                    SeleniumUtils.performClick(driver, externalTrigger);
                    break;
                default:
            }
        }

    }

    public List<String> getTableCellData() {
        List<String> campaignsList = new ArrayList<>();
        try {
            List<WebElement> rows = driver.findElements(By.xpath("//*[@class='CT-table__left']//div[2]"));
            for (WebElement row : rows) {
                campaignsList.add(row.getText());
            }
        } catch (Exception e) {
            List<WebElement> rows = driver.findElements(By.xpath("//*[@class='CT-table__left']//div[2]"));
            for (WebElement row : rows) {
                campaignsList.add(row.getText());
            }
        }
        return campaignsList;
    }


    public void clickShowCampaignAction(String campaignName) {
        SeleniumUtils.pause(3);
        driver.findElement(By.xpath("(//*[@data-original-title='" + campaignName + "']/../..//div[@data-original-title='Show campaign actions'])[1]")).click();
        SeleniumUtils.pause(3);
    }

    public void editCampaign(String newName) {
        SeleniumUtils.performClick(driver, editCampaign);
        SeleniumUtils.waitForPageLoaded(driver);
        driver.switchTo().frame(0);
        campaignNameBox.clear();
        campaignNameBox.sendKeys(newName + EDITED);
        continueBtn.click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.switchTo().defaultContent();
    }

    public String getClonedBreadcrumb() {
        return cloneCampaignBreadcrum.getText();
    }

   /* public void cloneCampaign(String newName) {
        cloneCampaignBtn.click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.switchTo().frame(0);
        campaignNameBox.clear();
        campaignNameBox.sendKeys(newName + "-cloned");
        continueBtn.click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.switchTo().defaultContent();
    }*/

    public void cloneCampaign(String campaignName) {

        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

        searchCampaign(campaignName);

        clickShowCampaignAction(campaignName);

        try {
            cloneCampaignWithDifferentWhenWhoWhatQuery();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void verifySetupPageForMobilePushAndAppInbox() throws InterruptedException {
        cloneCampaignBtn.click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.switchTo().frame(0);

        SeleniumUtils.moveToElementAndClick(setUp, driver);
        SeleniumUtils.pause(1);

        Assert.assertTrue(appInboxTime.getText().equalsIgnoreCase("12"));

        Assert.assertTrue(driver.findElement(By.id("androidTtlInputDays")).getText().equalsIgnoreCase(String.valueOf(campaignMeta.getSetup().getPush_android_ttl())));

        SeleniumUtils.pause(2);

        SeleniumUtils.pause(5);
        /*make change in who query*/
        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("//*[@class='framework-nav--parent'][1]")), driver);
        SeleniumUtils.pause(1);

        whoPage.changeWhoPage();
        SeleniumUtils.pause(5);
        SeleniumUtils.performClick(driver, saveAsDraftBtn);
        SeleniumUtils.pause(2);
        sweetAlert.getSweetAlertSuccessSignal();


        /*make change in what query*/
        SeleniumUtils.pause(3);
        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("(//*[@class='framework-nav--parent'])[2]")), driver);
        SeleniumUtils.pause(1);

        switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
            case SINGLEMESSAGE:
                break;
            case A_B:
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@class='framework-nav--parent'][1]")));
                SeleniumUtils.pause(1);
                SeleniumUtils.performClick(driver, continueButton);
//                SeleniumUtils.performClick(driver,driver.findElement(By.xpath("(//*[@class='framework-nav--parent'])[2]")));
                SeleniumUtils.pause(1);
                break;
            case MULTIVARIATE:
                break;
            default:
                break;
        }


        whatPage.changeWhatPage();

        SeleniumUtils.performClick(driver, saveAsDraftBtn);
        SeleniumUtils.pause(3);
        sweetAlert.getSweetAlertSuccessSignal();

        /*make change in setup query*/
        SeleniumUtils.pause(3);
        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("//*[@data-step-name='goal']/a")), driver);
        SeleniumUtils.pause(1);

        setupPage.changeSetupPage();
        SeleniumUtils.performClick(driver, saveAsDraftBtn);
        sweetAlert.getSweetAlertSuccessSignal();


        SeleniumUtils.performClick(driver, continueBtn);
        campaignNameBox.clear();
        SeleniumUtils.pause(2);
        campaignNameBox.sendKeys(campaignName + SCHEDULED_FINALLY + Keys.RETURN);
        SeleniumUtils.moveToElement(continueBtn, driver);
        SeleniumUtils.pause(1);
        continueBtn.click();
        SeleniumUtils.pause(10);
        driver.switchTo().defaultContent();
    }

    public void cloneCampaignWithDifferentWhenWhoWhatQuery() throws InterruptedException {
        cloneCampaignBtn.click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.switchTo().frame(0);

        SeleniumUtils.pause(5);
        /*make change in when query*/
        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath(DATA_STEP_NAME_WHEN_A)), driver);
        SeleniumUtils.pause(1);
        /*verifying when page advanced settings before changing the query in cloned campaign because these fields are different
         * for different start and end types*/
        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case SMS:
            case WEB_PUSH:
                verifyWhenAdvanceSettings();
                break;
            case MOBILE_INAPP:
            case APP_INBOX:
                break;
            default:
        }
        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("//*[@class='framework-nav--parent'][1]")), driver);
        SeleniumUtils.pause(1);
        /*Verifyinf who query upon cloning*/
        try {
            if (!(campaignMeta.getWho().getSegment_select_type().trim().toLowerCase()).equals(SAVED_SEGMENTS)) {
                whoPage.verifyWhoQueryFromCSV();
            }
        } catch (NullPointerException ne) {
            //do nothing
        }

        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("(//*[@class='framework-nav--parent'])[2]")), driver);
        SeleniumUtils.pause(1);
        whatPage.verifyWhatPage();

        SeleniumUtils.moveToElementAndClick(setUp, driver);
        SeleniumUtils.pause(1);

        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhat().getInclude_Msg_In_AppInbox())){
            setupPage.verifyAppInboxTargetWithPush();
        }




        /*change when , who and what page*/

//        SeleniumUtils.moveToElementAndClick(setUp, driver);
//        SeleniumUtils.pause(1);

        SeleniumUtils.pause(2); /*dashboard is responding slow so putting long wait. can be removed later*/
        whenPage.changeWhenPage();
        SeleniumUtils.pause(2);
        SeleniumUtils.performClick(driver, saveAsDraftBtn);
        SeleniumUtils.pause(2);
        sweetAlert.getSweetAlertSuccessSignal();
        SeleniumUtils.performClick(driver, continueBtn);
        SeleniumUtils.pause(2);

        SeleniumUtils.pause(5);
        /*make change in who query*/
        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("(//*[@class='framework-nav--parent'])[1]")), driver);
        SeleniumUtils.pause(1);

//        /*Verifyinf who query upon cloning*/
//        whoPage.verifyWhoQueryFromCSV();


        whoPage.changeWhoPage();
        SeleniumUtils.pause(5);

        /*this is good scenario as android fields are missing*/
        SeleniumUtils.performClick(driver, saveAsDraftBtn);
        SeleniumUtils.pause(2);
        sweetAlert.getSweetAlertErrorSignal();


        /*make change in what query*/
        SeleniumUtils.pause(3);
        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("(//*[@class='framework-nav--parent'])[2]")), driver);
        SeleniumUtils.pause(1);


//        whatPage.verifyWhatPage();
        try {
            switch (campaignMeta.getWhat().getMessage_to_be_selected().trim().toLowerCase()) {
                case SINGLEMESSAGE:
                    break;
                case A_B:
                    SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@class='framework-nav--parent'][1]")));
                    SeleniumUtils.pause(1);
                    SeleniumUtils.performClick(driver, continueButton);
//                SeleniumUtils.performClick(driver,driver.findElement(By.xpath("(//*[@class='framework-nav--parent'])[2]")));
                    SeleniumUtils.pause(1);
                    break;
                case MULTIVARIATE:
                    SeleniumUtils.scrollDown(driver);

                    try {
                        selectNotificationChannel();
                        SeleniumUtils.performClick(driver, saveAsDraftBtn);
                        sweetAlert.getSweetAlertErrorSignal();
                        SeleniumUtils.scrollDown(driver);
                        selectNotificationChannel();


                    }catch (NoSuchElementException nse){
                        logger.error(nse);
                    }
                    break;
                default:
                    break;
            }
        } catch (NullPointerException ne) {
            //do nothing
        }


        whatPage.changeWhatPage();

        SeleniumUtils.performClick(driver, saveAsDraftBtn);
        SeleniumUtils.pause(3);
        sweetAlert.getSweetAlertSuccessSignal();

        /*make change in setup query*/
        SeleniumUtils.pause(3);
        SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("//*[@data-step-name='goal']/a")), driver);
        SeleniumUtils.pause(1);

        setupPage.changeSetupPage();
        SeleniumUtils.performClick(driver, saveAsDraftBtn);
        sweetAlert.getSweetAlertSuccessSignal();


        SeleniumUtils.performClick(driver, continueBtn);
        campaignNameBox.clear();
        SeleniumUtils.pause(2);
        campaignNameBox.sendKeys(campaignName + SCHEDULED_FINALLY + Keys.RETURN);
        SeleniumUtils.moveToElement(continueBtn, driver);
        SeleniumUtils.pause(1);
        continueBtn.click();
        SeleniumUtils.pause(10);
        driver.switchTo().defaultContent();
    }

    public void selectNotificationChannel() {
        WebElement notificationChannel=driver.findElement(By.id("selectaochannel_chzn"));
        if (notificationChannel.isDisplayed()){
            String selectedChannelType=driver.findElement(By.xpath("//*[@id='selectaochannel_chzn']//span")).getText();
            if (!selectedChannelType.equalsIgnoreCase("BRTesting")){
                driver.findElement(By.xpath("//*[@id='selectaochannel_chzn']/a")).click();
                driver.findElement(By.xpath("//*[@id='selectaochannel_chzn']//li[text()='BRTesting']")).click();
                SeleniumUtils.pause(1);
            }
        }
    }

    public void clickCloneCurdButton() {
        cloneCampaignBtn.click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.switchTo().frame(0);
    }

    public void stopCampaign() {
        stopCampaignButton.click();
        SeleniumUtils.waitForPageLoaded(driver);
        sweetAlert.sweetAlertConfirmOK();
        SeleniumUtils.waitForPageLoaded(driver);
        sweetAlert.sweetAlertConfirmOK();
    }

    public void filter(String filterIn, String type) {
        driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT + filterIn + "']")).click();
        driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT + filterIn + "']/..//div[@class='clear-all-filter']")).click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT + filterIn + "']")).click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT + filterIn + "']/..//li/input[@data-name='" + type + "']/..")).click();
        driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT + filterIn + "']/..//a")).click();
    }

    public void stopAllAutomationCampaign(String campaignName) {
        SeleniumUtils.enterInputText(driver, searchBox, Keys.chord(campaignName + Keys.RETURN));
        SeleniumUtils.pause(3);
        SeleniumUtils.performClick(driver, selectAllVisibleCampaignBtn);
        SeleniumUtils.performClick(driver, bulkStopBtn);
        SeleniumUtils.pause(3);
        sweetAlert.sweetAlertConfirmOK();
        SeleniumUtils.waitForPageLoaded(driver);
        sweetAlert.sweetAlertConfirmOK();
    }

    public void searchAndStopCampaign(String campaignNameToBeStopped) {
        searchBox.clear();
        SeleniumUtils.enterInputText(driver, searchBox, Keys.chord(campaignNameToBeStopped + Keys.RETURN));
        SeleniumUtils.pause(3);
        List<WebElement> campaignsList = driver.findElements(By.xpath("//*[@id='tgt_table_data']//a[text()='" + campaignNameToBeStopped + "']/../..//span"));
        for (WebElement campaign : campaignsList) {
            SeleniumUtils.performClick(driver, campaign);
        }
        SeleniumUtils.performClick(driver, bulkStopBtn);
        sweetAlert.sweetAlertConfirmOK();
        sweetAlert.sweetAlertConfirmOK();
        SeleniumUtils.pause(1);

    }


    public boolean checkIfCampaignCompleted(String campaignNameToBeChecked) {
        searchBox.clear();
        SeleniumUtils.enterInputText(driver, searchBox, Keys.chord(campaignNameToBeChecked + Keys.RETURN));
        SeleniumUtils.pause(3);
        List<WebElement> campaignsList = driver.findElements(By.xpath("//*[@id='tgt_table_data']//a[text()='" + campaignNameToBeChecked + "']/../..//span"));

        for (WebElement campaign : campaignsList) {
            SeleniumUtils.performClick(driver, campaign);
            if (driver.findElement(By.id("campaign_bulk_stop")).getAttribute(CLASS).contains("is-disabled")) {
                return true;
            } else {
                return false;
            }
        }
        return true;

    }

    public void searchCampaign(String campaign) {
        SeleniumUtils.enterInputText(driver, searchBox, Keys.chord(campaign + Keys.RETURN));
        SeleniumUtils.pause(3);

    }

    public void archiveAutomationCampaigns(String campaignName) {
        SeleniumUtils.enterInputText(driver, searchBox, Keys.chord(campaignName + Keys.RETURN));
        SeleniumUtils.pause(3);
        SeleniumUtils.performClick(driver, selectAllVisibleCampaignBtn);
        SeleniumUtils.performClick(driver, archiveBtn);
        SeleniumUtils.pause(3);
        sweetAlert.sweetAlertConfirmOK();
        SeleniumUtils.pause(3);
        sweetAlert.sweetAlertConfirmOK();
    }

    public void editAllTypesOfCampaign() {
        switch (campaignMeta.getType().trim().toLowerCase()) {
            case ONE_TIME:
                switch (campaignMeta.getWhen().getCampaign_start_type().trim().toLowerCase()) {
                    case NOW:
                        logger.info("No action required for one time , now");
                        break;
                    case LATER:
                        Mocha.forceNavigate = true;
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
                        SeleniumUtils.enterInputText(driver, driver.findElement(By.id("searchDiv")), Keys.chord(campaignName + Keys.RETURN));
                        SeleniumUtils.pause(3);
                        clickShowCampaignAction(campaignName);
                        editCampaign(campaignName);
                        SeleniumUtils.waitForPageLoaded(driver);
                        Assert.assertTrue(getClonedBreadcrumb().contains(campaignName + EDITED));
                        break;
                    default:
                }
                break;
            case MULTIPLE_DATES:
            case RECURRING:
            case SINGLE_ACTION:
            case INACTION_WITHIN_TIME:
            case ON_A_DATE_TIME:
                Mocha.forceNavigate = true;
                Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
                SeleniumUtils.enterInputText(driver, driver.findElement(By.id("searchDiv")), Keys.chord(campaignName + SCHEDULED_FINALLY + Keys.RETURN));
                SeleniumUtils.pause(3);
                clickShowCampaignAction(campaignName + SCHEDULED_FINALLY);
                editCampaign(campaignName + SCHEDULED_FINALLY);
                SeleniumUtils.waitForPageLoaded(driver);
                Assert.assertTrue(getClonedBreadcrumb().contains(campaignName + SCHEDULED_FINALLY + EDITED));
                break;
            default:
        }
    }

    public void switchToTab(String tanName) {
        switch (tanName.trim().toLowerCase()) {
            case "overview":
                SeleniumUtils.performClick(driver, campaignTabs.get(0));
                break;
            case "stats":
                SeleniumUtils.performClick(driver, campaignTabs.get(1));
                break;
            default:
        }


    }

    public void compareClonedCampaignWithOriginalCampaign(String whichSection) {

        switch (whichSection.trim().toLowerCase()) {
            case "when":
                driver.switchTo().frame(0);
                SeleniumUtils.pause(3);
                SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath(DATA_STEP_NAME_WHEN_A)), driver);
                SeleniumUtils.pause(1);
//                SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//*[@data-step-name='when']/a")));

                switch (campaignMeta.getChannel().trim().toLowerCase()) {
                    case MOBILE_PUSH:
                    case SMS:
                    case EMAIL:
                    case WEB_PUSH:
                    case WEB_POPUP:
                    case WEB_EXIT_INTENT:
                    case WHATSAPP:
                    case WEBHOOKS:
                    case FB_AUDIENCES:
                    case GOOGLE_ADS:
                        switch (campaignMeta.getType().trim().toLowerCase()) {
                            case ONE_TIME:
                                Assert.assertTrue(driver.findElement(By.id("oneTimeType")).isSelected());

                                switch (campaignMeta.getWhen().getCampaign_start_type().trim().toLowerCase()) {
                                    case NOW:
                                        if (SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getChange_campaign_startType())) {
                                            Assert.assertTrue(driver.findElement(By.id("js-start-now")).isSelected());
                                        } else {
                                            Assert.assertTrue(driver.findElement(By.id("js-start-later")).isSelected());
                                        }

                                        break;
                                    case LATER:
                                        if (SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getChange_campaign_startType())) {
                                            Assert.assertTrue(driver.findElement(By.id("js-start-later")).isSelected());
                                        } else {
                                            Assert.assertTrue(driver.findElement(By.id("js-start-now")).isSelected());
                                        }

                                        switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
                                            case BEST_TIME_FOR_EVERY_USER:
                                                Assert.assertTrue(driver.findElement(By.id("js-best-time-campaign_0")).isSelected());
                                                break;
                                            case A_FIXED_TIME:
//                                                Assert.assertTrue(driver.findElement(By.id("js-fixed-time-campaign_0")).isSelected());
                                                break;
                                            default:
                                        }
                                        break;
                                    default:
                                }


                                break;
                            case MULTIPLE_DATES:
                                Assert.assertTrue(driver.findElement(By.id("certainDatesType")).isSelected());
                                break;
                            case RECURRING:
                                Assert.assertTrue(driver.findElement(By.id("recurringType")).isSelected());
                                switch (campaignMeta.getWhen().getCampaign_end_type().trim().toLowerCase()) {
                                    case "never end":
                                        Assert.assertTrue(driver.findElement(By.id("endNever")).isSelected());
                                        switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
                                            case BEST_TIME_FOR_EVERY_USER:
                                                Assert.assertTrue(driver.findElement(By.id("js-best-time-campaign_2")).isSelected());
                                                break;
                                            case A_FIXED_TIME:
                                                Assert.assertTrue(driver.findElement(By.id("js-fixed-time-campaign_2")).isSelected());
                                                break;
                                            default:
                                        }
                                        break;
                                    case "select date":
                                        Assert.assertTrue(driver.findElement(By.id("endOnDate")).isSelected());
                                        break;
                                    case "after":
                                        Assert.assertTrue(driver.findElement(By.id("endAfterOccurences")).isSelected());
                                        break;
                                    default:
                                }
                                break;

                            case SINGLE_ACTION:
                                if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getCampaign_delay())) {
                                    switch (campaignMeta.getWhen().getCampaign_delay().trim().toLowerCase()) {
                                        case "no delay(send as soon as user qualifies for segment)":
                                            Assert.assertTrue(driver.findElement(By.id("send-now")).isSelected());
                                            break;
                                        case "delay":
                                            Assert.assertTrue(driver.findElement(By.id("send-delay")).isSelected());
//                                    Assert.assertTrue(driver.findElement(By.id("delayByTimeValue")).getText().equalsIgnoreCase(campaignMeta.getWhen().getCampaign_delay_by()));
                                            break;
                                        default:

                                    }
                                }
                                if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getAdvanced().get(CAMPAIGN_DO_NOT_DISTURB))) {
                                    Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
                                    switch (campaignMeta.getWhen().getAdvanced().get(CAMPAIGN_DO_NOT_DISTURB).trim().toLowerCase()) {
                                        case "discard messages scheduled during DND hours":
                                            Assert.assertTrue(driver.findElement(By.id(PURGE_DND_RADIO_BUTTON)).isSelected());
                                            break;
                                        case DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS:
                                            Assert.assertTrue(driver.findElement(By.id(POSTPONE_DND_RADIO_BUTTON)).isSelected());
                                            break;
                                        default:
                                    }
                                }
                                break;
                            default:
                        }
                        break;


                    case MOBILE_INAPP:
                        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getHow_often())) {
                            switch (campaignMeta.getWhen().getHow_often().trim().toLowerCase()) {
                                case "exclude from campaign limits":
                                    Assert.assertTrue(driver.findElement(By.id("excludeFromFC")).isSelected());
                                    break;
                                case "once per session":
                                    Assert.assertTrue(driver.findElement(By.id("oncePerSession")).isSelected());
                                    break;
                                case "once per day":
                                    List<String> oncePerDayNotesList = new ArrayList<>();
                                    oncePerDayNotesList.add("Works for Android and iOS SDK versions 2.0.9 and above");
                                    oncePerDayNotesList.add("For Android and iOS SDK versions below 2.0.9 and for Windows Phone, this option will set the limit to once per session");
                                    Assert.assertTrue(driver.findElement(By.id("oncePerDay")).isSelected());
                                    for (WebElement element : oncePerDayNotes) {
                                        Assert.assertTrue(oncePerDayNotesList.contains(element.getText()));
                                    }
                                    break;
                                case "once per user for the duration of this campaign":
                                    List<String> oncePerLifeTimeNotesList = new ArrayList<>();
                                    oncePerLifeTimeNotesList.add("Works for Android and iOS SDK versions 2.0.9 and above");
                                    oncePerLifeTimeNotesList.add("Does not work for Windows Phone");
                                    Assert.assertTrue(driver.findElement(By.id("oncePerLifetime")).isSelected());
                                    for (WebElement element : oncePerLifeTimeNotes) {
                                        Assert.assertTrue(oncePerLifeTimeNotesList.contains(element.getText()));
                                    }
                                    break;
                                default:
                            }
                        }

                        break;
                    case APP_INBOX:
                        logger.info("!!!!No need to validate advance setting in app inbox!!!!!!");
                        break;
                    default:
                }


                break;
            case "who":
                SeleniumUtils.pause(3);
                SeleniumUtils.moveToElementAndClick(driver.findElement(By.xpath("//*[@class='framework-nav--parent'][1]")), driver);
                SeleniumUtils.pause(1);

//                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@class='framework-nav--parent'][1]")));
                switch (campaignMeta.getChannel().trim().toLowerCase()) {
                    case MOBILE_PUSH:
                    case MOBILE_INAPP:
                    case SMS:
                    case EMAIL:
                    case WHATSAPP:
                        break;
                    case APP_INBOX:
                        checkCampaignUserLimitForLiveUser();
                        SeleniumUtils.performClick(driver, continueBtn);
                        checkOsTypeForLiveUser();
                        checkDeviceTypeForLiveUser();
                        break;
                    default:
                }

                switch (campaignMeta.getChannel().trim().toLowerCase()) {
                    case MOBILE_PUSH:
                    case MOBILE_INAPP:
                    case APP_INBOX:
                    case EMAIL:
                    case WEB_EXIT_INTENT:
                    case WEB_POPUP:
                    case WEB_PUSH:
                    case WEBHOOKS:
                        checkOsTypeForLiveUser();
                        checkDeviceTypeForLiveUser();
                        break;
                    case SMS:
                        break;
                    default:
                }

                checkCampaignUserLimitForLiveUser();


                break;
            case "what":
                break;
            case "setup":
                break;
            case "overview":
                break;
            default:
        }
    }

//    private void verifyWhenPageAdvanceSettings() {
//
//        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getType())) {
//            switch (campaignMeta.getType().trim().toLowerCase()) {
//                case ONE_TIME:
//                case MULTIPLE_DATES:
//                    switch (campaignMeta.getWhen().getCampaign_start_type().trim().toLowerCase()) {
//                        case NOW:
////                        if (SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getChange_campaign_startType())) {
//                            Assert.assertTrue(driver.findElement(By.id("userTzSelectBox")).isSelected());
//                            verifyTZOnWhenPageAdvanceSetting();
//
//                            Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
//                            verifyFixTimeOnWhenPageAdvanceSetting();
//                            verifyCampaignCutOffOnWhenPageAdvanceSetting();
//                            break;
//                        case LATER:
//                            switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
//                                case BEST_TIME_FOR_EVERY_USER:
//                                    Assert.assertTrue(driver.findElement(By.id("timezone-container")).getAttribute(STYLE).equalsIgnoreCase(DISPLAY_NONE));
//
////                                    Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
//                                    verifyBTForEveryUserOnWhenPageAdvanceSetting();
//
//                                    Assert.assertTrue(driver.findElement(By.id(TIME_CRITICAL_DIV)).getAttribute(STYLE).equalsIgnoreCase(DISPLAY_NONE));
//
//                                    break;
//                                case A_FIXED_TIME:
//                                    Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
//                                    verifyFixTimeOnWhenPageAdvanceSetting();
//
//                                    break;
//                                default:
//                            }
//
//                            break;
//                        default:
//                    }
//                    break;
//                case RECURRING:
//                    switch (campaignMeta.getWhen().getCampaign_end_type().trim().toLowerCase()) {
//                        case "never end":
//                        case "select date":
//                        case "after":
//                            switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
//                                case BEST_TIME_FOR_EVERY_USER:
//                                    Assert.assertTrue(driver.findElement(By.id("timezone-container")).getAttribute(STYLE).equalsIgnoreCase(DISPLAY_NONE));
//                                    Assert.assertTrue(driver.findElement(By.id(TIME_CRITICAL_DIV)).getAttribute(STYLE).equalsIgnoreCase(DISPLAY_NONE));
//                                    Assert.assertTrue(driver.findElement(By.className("js-thorttle-container")).getAttribute(STYLE).equalsIgnoreCase(DISPLAY_NONE));
//                                    if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getAdvanced().get(CAMPAIGN_DO_NOT_DISTURB))) {
//                                        verifyBTForEveryUserOnWhenPageAdvanceSetting();
//                                    }
//                                    break;
//                                case A_FIXED_TIME:
//                                    if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getAdvanced().get(TIME_ZONE))) {
//                                        verifyTZOnWhenPageAdvanceSetting();
//                                    }
//
//                                    if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getAdvanced().get(CAMPAIGN_DO_NOT_DISTURB))) {
//                                        switch (campaignMeta.getWhen().getAdvanced().get(CAMPAIGN_DO_NOT_DISTURB).trim().toLowerCase()) {
//                                            case DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS:
//                                                Assert.assertTrue(driver.findElement(By.id("dndDiv")).getAttribute(CLASS).contains("is-disable"));
//                                                break;
//                                            case DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS:
//                                                Assert.assertTrue(driver.findElement(By.id("dndDiv")).getAttribute(CLASS).contains("is-disable"));
//                                                break;
//                                            default:
//                                        }
//                                    }
//
//                                    Assert.assertTrue(driver.findElement(By.id(TIME_CRITICAL_DIV)).getAttribute(STYLE).equalsIgnoreCase("display: block;"));
//
//
//                                    break;
//                                default:
//                            }
//                            break;
//                        default:
//                    }
//                    break;
//                case SINGLE_ACTION:
//                case INACTION_WITHIN_TIME:
//                case ON_A_DATE_TIME:
//                    Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
//                    if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getAdvanced().get(CAMPAIGN_DO_NOT_DISTURB))) {
//                        verifyFixTimeOnWhenPageAdvanceSetting();
//                    }
//                    break;
//                default:
//            }
//        }
//    }


    public void verifyWhenAdvanceSettings() {

        //flag to check that test case has set advanced settings on when page or not, true if selected else false,use with all combinations, first using with mobile push single action
        boolean isAdvancedPropsSetOnWhenPage = !SeleniumUtils.isNullOrEmpty(whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()))
                && !SeleniumUtils.isNullOrEmpty(whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(CAMPAIGN_DO_NOT_DISTURB));

        switch (campaignMeta.getChannel().trim().toLowerCase()) {
            case MOBILE_PUSH:
            case SMS:
            case EMAIL:
            case WEBHOOKS:
                switch (campaignMeta.getType().trim().toLowerCase()) {
                    case ONE_TIME:
                        switch (campaignMeta.getWhen().getCampaign_start_type().trim().toLowerCase()) {
                            case NOW:
                                verifyTZOnWhenPageAdvanceSetting();
                                verifyDNDOnWhenPageAdvanceSetting();
                                verifyCampaignCutOffOnWhenPageAdvanceSetting();
                                verifyThrottleLimitOnWhenPageAdvvanceSetting();
                                break;
                            case LATER:
                                switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
                                    case BEST_TIME_FOR_EVERY_USER:
                                        verifyBTForEveryUserOnWhenPageAdvanceSetting();
                                        break;
                                    case A_FIXED_TIME:
                                        verifyTZOnWhenPageAdvanceSetting();
                                        verifyDNDOnWhenPageAdvanceSetting();
                                        verifyFixTimeOnWhenPageAdvanceSetting();
                                        verifyCampaignCutOffOnWhenPageAdvanceSetting();
                                        break;
                                    default:
                                }

                                break;
                            default:
                        }
                        break;
                    case MULTIPLE_DATES:
                    case RECURRING:
                        break;
                    case SINGLE_ACTION:
                    case INACTION_WITHIN_TIME:
                    case ON_A_DATE_TIME:
//                        Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
                        Assert.assertEquals(driver.findElement(By.id(DND_SELECT_BOX)).isSelected(),isAdvancedPropsSetOnWhenPage);
                        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getAdvanced().get(CAMPAIGN_DO_NOT_DISTURB))) {
                            verifyFixTimeOnWhenPageAdvanceSetting();
                        }
                        break;
                    default:
                }
                break;
            case WEB_PUSH:
                switch (campaignMeta.getType().trim().toLowerCase()) {
                    case ONE_TIME:
                        switch (campaignMeta.getWhen().getCampaign_start_type().trim().toLowerCase()) {
                            case NOW:
                                verifyTZOnWhenPageAdvanceSetting();
                                verifyFixTimeOnWhenPageAdvanceSetting();
                                verifyCampaignCutOffOnWhenPageAdvanceSetting();
                                break;
                            case LATER:
                                switch (campaignMeta.getWhen().getCampaign_delivery().trim().toLowerCase()) {
                                    case BEST_TIME_FOR_EVERY_USER:
                                        verifyBTForEveryUserOnWhenPageAdvanceSetting();
                                        break;
                                    case A_FIXED_TIME:
                                        verifyTZOnWhenPageAdvanceSetting();
                                        verifyFixTimeOnWhenPageAdvanceSetting();
                                        verifyCampaignCutOffOnWhenPageAdvanceSetting();

                                        break;
                                    default:
                                }

                                break;
                            default:
                        }
                        break;
                    case MULTIPLE_DATES:
                    case RECURRING:
                        break;
                    case SINGLE_ACTION:
                    case INACTION_WITHIN_TIME:
                    case ON_A_DATE_TIME:
                        Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
                        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getWhen().getAdvanced().get(CAMPAIGN_DO_NOT_DISTURB))) {
                            verifyFixTimeOnWhenPageAdvanceSetting();
                        }
                        break;
                    default:
                }
                break;
            case MOBILE_INAPP:
            case APP_INBOX:
            case WEB_POPUP:
            case WEB_EXIT_INTENT:
            case FB_AUDIENCES:
            case GOOGLE_ADS:
                break;
            default:
        }
    }

    private void verifyThrottleLimitOnWhenPageAdvvanceSetting() {
        boolean isTZChecked = driver.findElement(By.id("userTzSelectBox")).isSelected();
        if (!isTZChecked) {
            Assert.assertTrue(driver.findElement(By.id("throttleCheckBox")).isSelected());
        }

    }

    private void verifyFixTimeOnWhenPageAdvanceSetting() {
        Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
        switch (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(CAMPAIGN_DO_NOT_DISTURB).trim().toLowerCase()) {
            case DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS:
                Assert.assertTrue(driver.findElement(By.id(PURGE_DND_RADIO_BUTTON)).isSelected());
                break;
            case DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS:
                Assert.assertTrue(driver.findElement(By.id(POSTPONE_DND_RADIO_BUTTON)).isSelected());
                break;
            default:
        }
    }

    private void verifyBTForEveryUserOnWhenPageAdvanceSetting() {
        verifyDNDOnWhenPageAdvanceSetting();
    }

    private void verifyCampaignCutOffOnWhenPageAdvanceSetting() {
        Assert.assertTrue(driver.findElement(By.id("tcSelectBox")).isSelected());
    }

    private void verifyDNDOnWhenPageAdvanceSetting() {
        switch (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(CAMPAIGN_DO_NOT_DISTURB).trim().toLowerCase()) {
            case DISCARD_MESSAGES_SCHEDULED_DURING_DND_HOURS:
                Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
                Assert.assertTrue(driver.findElement(By.id(PURGE_DND_RADIO_BUTTON)).isSelected());
                break;
            case DELAY_DELIVERY_UNTIL_THE_END_OF_DND_HOURS:
                Assert.assertTrue(driver.findElement(By.id(DND_SELECT_BOX)).isSelected());
                Assert.assertTrue(driver.findElement(By.id(POSTPONE_DND_RADIO_BUTTON)).isSelected());
                break;
            default:
        }
    }

    private void verifyTZOnWhenPageAdvanceSetting() {
        if (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(TIME_ZONE).matches("^[a-zA-Z]*$")) {
            Assert.assertTrue(driver.findElement(By.id("userTzSelectBox")).isSelected());
        }

        switch (whenPageAdvanceSettingsCSVMap.get(campaignMeta.getCsv_key().trim().toLowerCase()).get(TIME_ZONE).trim().toLowerCase()) {
            case DROP_THE_CAMPAIGN:
                Assert.assertTrue(driver.findElement(By.id(USER_TZ_DROP_IF_TIME_PASSED_RADIO)).isSelected());
                break;
            case DELIVER_THE_CAMPAIGNS_THE_NEXT_DAY:
                Assert.assertTrue(driver.findElement(By.id(USER_TZ_SEND_NEXT_DAY_IF_TIME_PASSED_RADIO)).isSelected());
                break;
            default:
        }
    }

    private void checkOsTypeForLiveUser() {
        switch (campaignMeta.getWho().getChange_os_type().trim().toLowerCase()) {
            case "ios":
                switch (campaignMeta.getChannel().trim().toLowerCase()) {
                    case MOBILE_PUSH:
                    case SMS:
                    case EMAIL:
                    case WEB_PUSH:
                    case WEB_POPUP:
                    case WEB_EXIT_INTENT:
                    case FB_AUDIENCES:
                    case GOOGLE_ADS:
                    case WEBHOOKS:

                        Assert.assertTrue(driver.findElement(By.id("ios_chk")).isSelected());
                        break;
                    case APP_INBOX:
                    case WHATSAPP:
                        String id = driver.findElement(By.xpath("//*[@class='selector-group platform']//span[text()='" + campaignMeta.getWho().getDevice_os().trim() + INPUT)).getAttribute("id");
                        Assert.assertTrue(driver.findElement(By.id(id)).isSelected());
                        break;
                    case MOBILE_INAPP:
                        break;
                    default:
                }


                break;
            case "android":
                switch (campaignMeta.getChannel().trim().toLowerCase()) {
                    case MOBILE_PUSH:
                    case SMS:
                    case EMAIL:
                    case WEB_PUSH:
                    case WEB_POPUP:
                    case WEB_EXIT_INTENT:
                    case FB_AUDIENCES:
                    case GOOGLE_ADS:
                    case WEBHOOKS:

                        Assert.assertTrue(driver.findElement(By.id("android_chk")).isSelected());
                        break;
                    case MOBILE_INAPP:
                    case APP_INBOX:
                    case WHATSAPP:
                        String id = driver.findElement(By.xpath("//*[@class='selector-group platform']//span[text()='" + campaignMeta.getWho().getDevice_os().trim() + INPUT)).getAttribute("id");
                        Assert.assertTrue(driver.findElement(By.id(id)).isSelected());
                        break;
                    default:
                }

                break;
            case "unchecked all os type":
                Assert.assertTrue(driver.findElement(By.id("ios_chk")).isSelected());
                Assert.assertTrue(driver.findElement(By.id("android_chk")).isSelected());
                break;
            default:
        }
    }

    private void checkDeviceTypeForLiveUser() {
        switch (campaignMeta.getWho().getChange_device_type().trim().toLowerCase()) {
            case "mobile":

                switch (campaignMeta.getChannel().trim().toLowerCase()) {
                    case MOBILE_PUSH:
                    case SMS:
                    case EMAIL:
                    case WEB_PUSH:
                    case WEB_POPUP:
                    case WEB_EXIT_INTENT:
                    case FB_AUDIENCES:
                    case GOOGLE_ADS:
                    case WEBHOOKS:
                        Assert.assertTrue(driver.findElement(By.id("mobile_chk")).isSelected());
//                        Assert.assertFalse(driver.findElement(By.id("tablet_chk")).isSelected());
//                        Assert.assertFalse(driver.findElement(By.id("tv_chk")).isSelected());
                        break;
                    case MOBILE_INAPP:
                    case APP_INBOX:
                    case WHATSAPP:
                        String id = driver.findElement(By.xpath(CLASS_SELECTOR_GROUP_PLATFORM_DEVICES_BOX_SPAN_TEXT + campaignMeta.getWho().getDevice_type().trim() + INPUT)).getAttribute("id");
                        Assert.assertTrue(driver.findElement(By.id(id)).isSelected());
                        break;
                    default:
                }

                break;
            case "tablet":

                switch (campaignMeta.getChannel().trim().toLowerCase()) {
                    case MOBILE_PUSH:
                    case SMS:
                    case EMAIL:
                    case WEB_PUSH:
                    case WEB_POPUP:
                    case WEB_EXIT_INTENT:
                    case FB_AUDIENCES:
                    case GOOGLE_ADS:
                    case WEBHOOKS:
//                        Assert.assertFalse(driver.findElement(By.id("mobile_chk")).isSelected());
                        Assert.assertTrue(driver.findElement(By.id("tablet_chk")).isSelected());
//                        Assert.assertFalse(driver.findElement(By.id("tv_chk")).isSelected());
                        break;
                    case MOBILE_INAPP:
                    case APP_INBOX:
                    case WHATSAPP:
                        String id = driver.findElement(By.xpath(CLASS_SELECTOR_GROUP_PLATFORM_DEVICES_BOX_SPAN_TEXT + campaignMeta.getWho().getDevice_type().trim() + INPUT)).getAttribute("id");
                        Assert.assertTrue(driver.findElement(By.id(id)).isSelected());
                        break;
                    default:
                }
                break;
            case "tv":
                switch (campaignMeta.getChannel().trim().toLowerCase()) {
                    case MOBILE_PUSH:
                    case SMS:
                    case EMAIL:
                    case WEB_PUSH:
                    case WEB_POPUP:
                    case WEB_EXIT_INTENT:
                    case FB_AUDIENCES:
                    case GOOGLE_ADS:
                    case WEBHOOKS:
//                        Assert.assertFalse(driver.findElement(By.id("mobile_chk")).isSelected());
//                        Assert.assertFalse(driver.findElement(By.id("tablet_chk")).isSelected());
                        Assert.assertTrue(driver.findElement(By.id("tv_chk")).isSelected());
                        break;
                    case MOBILE_INAPP:
                    case APP_INBOX:
                    case WHATSAPP:
                        String id = driver.findElement(By.xpath(CLASS_SELECTOR_GROUP_PLATFORM_DEVICES_BOX_SPAN_TEXT + campaignMeta.getWho().getDevice_type().trim() + INPUT)).getAttribute("id");
                        Assert.assertTrue(driver.findElement(By.id(id)).isSelected());
                        break;
                    default:
                }

                break;
            case "unchecked all device type":
                Assert.assertTrue(driver.findElement(By.id("mobile_chk")).isSelected());
                Assert.assertTrue(driver.findElement(By.id("tablet_chk")).isSelected());
                Assert.assertTrue(driver.findElement(By.id("tv_chk")).isSelected());
                break;

            default:
        }
    }

    private void checkCampaignUserLimitForLiveUser() {
        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getType())) {
            switch (campaignMeta.getType().trim().toLowerCase()) {
                case ONE_TIME:
                case MULTIPLE_DATES:
                case RECURRING:
                    switch (campaignMeta.getWho().getCampaign_reach_type().trim().toLowerCase()) {
                        case "all users":
                            Assert.assertTrue(driver.findElement(By.id("oneTimeUserQualifyId")).isSelected());
                            Assert.assertFalse(driver.findElement(By.id("oneTimeCampaignReachLimitId")).isSelected());
                            break;
                        case "only users":
                            Assert.assertTrue(driver.findElement(By.id("oneTimeCampaignReachLimitId")).isSelected());
                            Assert.assertFalse(driver.findElement(By.id("oneTimeUserQualifyId")).isSelected());
                            break;
                        default:
                    }
                    break;
                case ON_A_DATE_TIME:
                    switch (campaignMeta.getWho().getChange_campaign_reachToType().trim().toLowerCase()) {
                        case "all users":
                            Assert.assertTrue(driver.findElement(By.id("triggerUserQualifyId")).isSelected());
                            break;
                        case "limit users":
                            Assert.assertTrue(driver.findElement(By.id("triggerCampaignReachLimitedPerDayId")).isSelected());
                            break;
                        case "only x users overall":
                            Assert.assertTrue(driver.findElement(By.id("triggerCampaignReachLimitTotalId")).isSelected());
                            break;
                        default:
                    }
                    break;
                case SINGLE_ACTION:
                case INACTION_WITHIN_TIME:
                    break;
                default:
            }
        }
    }

    public void selectFilter(String campaignStatus) {
        statusFilter.click();
        SeleniumUtils.pause(5);
        driver.findElement(By.xpath("//label[text()='" + campaignStatus + "']")).click();
        SeleniumUtils.pause(5);
        applyButton.click();
        SeleniumUtils.pause(5);
    }

    /*Below method is used to create Draft -> Running -> Stopped campaign*/
    public void createCampaignAndVerifyActions() throws ParseException, InterruptedException {
        String campaignName = createsaveAsDraftCampaign();
        String status = getCampaignStatus(campaignName);
        Assert.assertEquals(status, "Draft", "Campaign is not in Draft status");
        Assert.assertTrue(verifyCampaignActions(status), "Campaign action for Draft status is invalid");

        clickSelectedCampaign.click();
        SeleniumUtils.pause(3);
        driver.navigate().refresh();
        driver.switchTo().frame(0);
        SeleniumUtils.performClick(driver, continueButton);
        status = getCampaignStatus(campaignName);
        if (status.equals("Running") || status.equals("Scheduled")) {       //Depends if the campaign is 'Now' or 'Later'
            Assert.assertTrue(true, "Campaign is not in Scheduled/Running status");
        }
        Assert.assertTrue(verifyCampaignActions(status), "Campaign action for Running or Scheduled status is invalid");

        searchAndStopCampaign(campaignName);
        status = campaignStatus.getText();
        Assert.assertEquals(status, "Stopped", "Campaign is not in Stopped status");
        getActionList();
        Assert.assertTrue(verifyCampaignActions(status), "Campaign action for Stopped status is invalid");
    }

    public void createCampaignWithApprovalPendingStatus() throws ParseException, InterruptedException {
        String username = "ben.sequeira+creator@clevertap.com";
        String password = "Clevertap@123";
        Login.logOutApplication();
//        BaseTest.prop.setProperty("UserName", username);
//        BaseTest.prop.setProperty("Password", password);
        driver.manage().deleteAllCookies();
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        String approvalPendingCampaignName = createCampaign();
        String status = getCampaignStatus(approvalPendingCampaignName);
        Assert.assertTrue(verifyCampaignActions(status), "Campaign action for Approval pending status is invalid");
    }

    public void searchAndVerifyCompletedCampaign() {
        selectFilter("Completed");
        WebElement campaignStatus = driver.findElement(By.xpath("(//div[contains(@class,'body')]//div[contains(@class,'cellindex_6')])[1]"));
        String status = campaignStatus.getText();
        getActionList();
        Assert.assertTrue(verifyCampaignActions(status), "Campaign action for Completed status is invalid");
    }

    public void createCampaignWithAwaitingNextRunStatus() throws ParseException, InterruptedException {
        String campaignName = createCampaign();
        SeleniumUtils.pause(20);
        String status = getCampaignStatus(campaignName);
        int refreshCount = 0;
        while (!status.equals("Awaiting next run") && refreshCount <= 10) {
            searchCampaign(campaignName);
            SeleniumUtils.pause(3);
            status = campaignStatus.getText();
            refreshCount++;
        }
        Assert.assertTrue(verifyCampaignActions(status), "Campaign action for Awaiting Next Run status is invalid");
    }

    public String getCampaignStatus(String campaignName) {
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        searchCampaign(campaignName);
        String status = campaignStatus.getText();
        getActionList();
        return status;
    }

    public void getActionList() {
        Actions actions = new Actions(driver);
        campaignActions.click();
        List<WebElement> actionList = driver.findElements(By.xpath("//a[contains(@class,'operation-tool')]"));
        for (WebElement actionValue : actionList) {
            actions.moveToElement(actionValue).perform();
            listOfCampaignAction.add(actionValue.getAttribute("data-original-title"));
        }
    }

    public boolean verifyCampaignActions(String campaignStatus) {
        switch (campaignStatus.trim().toLowerCase()) {
            case COMPLETED:
                if (listOfCampaignAction.size() == 2) {
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.COMPLETED.getViewReportStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.COMPLETED.getCloneStatus()));
                    return true;
                }
                break;
            case RUNNING:
                if (listOfCampaignAction.size() == 4) {
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.RUNNING.getViewReportStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.RUNNING.getEditStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.RUNNING.getCloneStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.RUNNING.getStopStatus()));
                    return true;
                }
                break;
            case STOPPED:
                if (listOfCampaignAction.size() == 2) {
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.STOPPED.getViewReportStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.STOPPED.getCloneStatus()));
                    return true;
                }
                break;
            case AWAITING_NEXT_RUN:
                if (listOfCampaignAction.size() == 4) {
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.AWAITNEXTRUN.getViewReportStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.AWAITNEXTRUN.getEditStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.AWAITNEXTRUN.getCloneStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.AWAITNEXTRUN.getStopStatus()));
                    return true;
                }
                break;
            case DRAFT:
                if (listOfCampaignAction.size() == 2) {
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.DRAFT.getEditStatus()));
                    Assert.assertTrue(listOfCampaignAction.contains(campaignCrudOperation.DRAFT.getCloneStatus()));
                    return true;
                }
                break;
            case SCHEDULED:
                if (listOfCampaignAction.size() == 4) {
                    Assert.assertTrue(listOfCampaignAction.get(0).equalsIgnoreCase(campaignCrudOperation.SCHEDULED.getViewReportStatus()));
                    Assert.assertTrue(listOfCampaignAction.get(1).equalsIgnoreCase(campaignCrudOperation.SCHEDULED.getEditStatus()));
                    Assert.assertTrue(listOfCampaignAction.get(2).equalsIgnoreCase(campaignCrudOperation.SCHEDULED.getCloneStatus()));
                    Assert.assertTrue(listOfCampaignAction.get(3).equalsIgnoreCase(campaignCrudOperation.SCHEDULED.getStopStatus()));
                    return true;
                }
                break;
            case APPROVAL_PENDING:
                if (listOfCampaignAction.size() == 1) {
                    Assert.assertTrue(listOfCampaignAction.get(0).equalsIgnoreCase(campaignCrudOperation.APPROVALPENDING.getCloneStatus()));
                    return true;
                }
                break;
            default:
                logger.info("Invalid Campaign Status");
                return false;
        }
        return false;
    }

    public JSONObject getJsonObjectFromJsonFile(String jsonFileName) throws IOException {
        FileReader file;
        JSONParser parser;
        file = new FileReader(System.getProperty("user.dir") + "/JSONData/" + jsonFileName + ".json");
        parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(file);
        } catch (org.json.simple.parser.ParseException e) {
            //do nothing
        }
        file.close();
        return jsonObject;
    }
    public Map<String, String> setAPIHeader() {
        Map<String, String> headerValues = new HashMap<>();
        headerValues.put("Content-Type", "application/json");
        headerValues.put("X-CleverTap-Account-Id", "65R-44Z-R65Z");
        headerValues.put("X-CleverTap-Passcode", "32f75eb0763a400584215e7403efc773");
        return headerValues;
    }
    public String createPushCampaignUsingAPI() throws IOException {
        requestSpecification = given();
        createPushCampaignApiPayLoad = getJsonObjectFromJsonFile("PushCampaignAPI");
        logger.info("Payload for counting the campaigns: " + createPushCampaignApiPayLoad.toString());
        requestSpecification.body(createPushCampaignApiPayLoad.toString());
        response = requestSpecification.headers(setAPIHeader()).get("https://api.clevertap.com/1/targets/create.json");
        jsonPath = new JsonPath(response.asString());
        logger.info("Response for creating campaign is: " + jsonPath.get());
        Assert.assertEquals(response.getStatusCode(), 200);
        return jsonPath.get("id").toString();
    }
    public String verifyStatsPage() {
        clickSelectedCampaign.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String statsPageDetails = (String) js.executeScript("return JSON.stringify(targetDetails);");
        JSONObject jsonObject = null;
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(statsPageDetails);
        } catch (org.json.simple.parser.ParseException e) {
            //do nothing
        }
        return jsonObject.get("_id").toString();
    }

    /*
        This is an end to end method which will create ,clone and edit a campaign and compares what,when,who,setup
        and overview pages of the cloned campaign with the original
     */
    public void campaignCrudFunctionEndToEnd()  {
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

        CampaignsHomePage campaignsHomePage = new CampaignsHomePage(driver);
        try {
            campaignName = campaignsHomePage.createCampaign();
        } catch (InterruptedException | ParseException e) {
            e.printStackTrace();
        }


        /*Below method validating the target details for created campaign and its cloned one*/
//        validateTargetDetails();


        /*Cloning each campaign */

        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

        campaignsHomePage.searchAndStopCampaign(campaignName);

        campaignsHomePage.clickShowCampaignAction(campaignName);

        try {
            campaignsHomePage.cloneCampaignWithDifferentWhenWhoWhatQuery();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        campaignsHomePage.searchCampaign(campaignName + "scheduledFinally");
        campaignsHomePage.clickShowCampaignAction(campaignName + "scheduledFinally");
        SeleniumUtils.pause(1);
        driver.findElement(By.xpath("//a[contains(@class,'campaign-clone')]")).click();

        campaignsHomePage.compareClonedCampaignWithOriginalCampaign("when");
        campaignsHomePage.compareClonedCampaignWithOriginalCampaign("who");
        campaignsHomePage.compareClonedCampaignWithOriginalCampaign("what");
        campaignsHomePage.compareClonedCampaignWithOriginalCampaign("setup");
        campaignsHomePage.compareClonedCampaignWithOriginalCampaign("overview");


        /*closing the frame to start another test case*/
        campaignsHomePage.closeCampaignFrame();


        /*Edit each campaign*/

        if (!SeleniumUtils.isNullOrEmpty(campaignMeta.getType())) {

            switch (campaignMeta.getType().trim().toLowerCase()) {
                case "one time":
                    break;
                case "multiple dates":
                case "recurring":
                case "single action":
                case "inaction within time":
                case "on a date/time":
                    if (!campaignsHomePage.checkIfCampaignCompleted(campaignName + "scheduledFinally")) {
                        campaignsHomePage.editAllTypesOfCampaign();
                    }

                    break;

            }
        }

        /*to free up all old array lists to use them again in next run*/
        whoPage = new WhoPage(driver);
        whoPage.customArrayListCleaner();

        segmentWidget = new SegmentWidget(driver);
        segmentWidget.userWhoLikeFilterCounter = 1;
        didWidget = new DidWidget(driver);
        didWidget.userWhoDidFilterCounter = 1;
        didWidget.liveActionUserWhoDidFilterCounter = 1;
        didNotWidget = new DidNotWidget(driver);
        didNotWidget.userWhoDidNotFilterCounter = 1;
        didNotWidget.liveActionUserWhoDidNotFilterCounter = 1;

    }
}