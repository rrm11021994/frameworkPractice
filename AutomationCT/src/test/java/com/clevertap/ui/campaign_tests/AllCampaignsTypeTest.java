package com.clevertap.ui.campaign_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.analyze_page.EventsPage;
import com.clevertap.ui.pages.campaigns_page.CampaignsHomePage;
import com.clevertap.ui.pages.campaigns_page.WhoPage;
import com.clevertap.ui.pages.widget.*;
import com.clevertap.utils.*;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.clevertap.utils.LoadYamlFile.campaignMeta;


public class AllCampaignsTypeTest extends BaseTest{

    private Logger logger;
    private WebDriver driver;
    private static final String PASSED = "Passed";
    private static final String STATUS = "Status";
    private static final String STOPPED = "Stopped";
    private static final String RUNNING = "Running";
    private static final String AUTOMATION = "Auto";
    private String campaignName;
    private SweetAlert sweetAlert;
    private WhoPage whoPage;
    private SegmentWidget segmentWidget;
    private DidWidget didWidget;
    private DidNotWidget didNotWidget;
    private ActionEventWidget actionEventWidget;
    private boolean hasFailures = false;
    private Map<String, String> temprorayDataProviderStore;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        logger = baseTest.configureLogger(getClass());
        sweetAlert = new SweetAlert(driver);
    }

    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void mobilePushOneTimeAndMultipleDate(String csvKey, String whatPageMessageType, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String osType, String deviceType, String campaignReachTo, String systemControlGroup, String changeCampaignSTartType, String changeSegmentType, String changeOsType, String changeDeviceType, String changeCampaignReachToType, String changeControlGroup,String includePushIntoInApp) throws InterruptedException, ParseException {


        /*Setting up data for creating mobile push one time campaign */
        campaignMeta.setCurrentRunningTestCaseName("mobilePushOneTimeAndMultipleDate");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.getWhat().setMessage_to_be_selected(whatPageMessageType);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
        campaignMeta.getWhen().setHow_often("Once per day");
        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_os_type(changeOsType);
        campaignMeta.getWho().setChange_device_type(changeDeviceType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
        campaignMeta.getWhat().setInclude_Msg_In_AppInbox(includePushIntoInApp);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignCrudFunction();
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }

    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void mobilePushSingleAction(String csvKey, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDelayType, String segmentSelectionType, String osType, String deviceType, String campaignReachTo, String systemControlGroup, String changeCampaignSTartType, String changeCampaignEndType, String changeDelayType, String changeSegmentType, String changeOsType, String changeDeviceType, String changeCampaignReachToType, String changeControlGroup, String personalisation) throws InterruptedException, ParseException {


        /*Setting up data for creating mobile push one time campaign */
        campaignMeta.setCurrentRunningTestCaseName("mobilePushSingleAction");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setChange_campaign_endType(changeCampaignEndType);
        campaignMeta.getWhen().setChange_delay_type(changeDelayType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
        campaignMeta.getWhen().setCampaign_delay(campaignDelayType);
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_os_type(changeOsType);
        campaignMeta.getWho().setChange_device_type(changeDeviceType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
        campaignMeta.setPersonalisation(personalisation);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignCrudFunction();
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }

    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void mobilePushRecurringCampaign(String csvKey, String channelName, String campaignType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String osType, String deviceType, String campaignReachTo, String systemControlGroup, String changeCampaignEndType, String changeSegmentType, String changeOsType, String changeDeviceType, String changeCampaignReachToType, String changeControlGroup, String personalisation) throws InterruptedException, ParseException {

        /*Setting up data for creating mobile push one time campaign */
        campaignMeta.setCurrentRunningTestCaseName("mobilePushRecurringCampaign");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
        campaignMeta.getWhen().setHow_often("Once per day");
        campaignMeta.getWhen().setChange_campaign_endType(changeCampaignEndType);
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_os_type(changeOsType);
        campaignMeta.getWho().setChange_device_type(changeDeviceType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
        campaignMeta.setPersonalisation(personalisation);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);
        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);

        campaignCrudFunction();
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }


    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void mobileInAppCampaign(String csvKey, String channelName, String campaignStartType, String campaignEndType, String howOften, String segmentSelectionType, String osType, String deviceType, String selectMessageType, String selectTemplateType, String selectTemplateButtonType, String systemControlGroup, String changeCampaignSTartType, String changeCampaignEndType, String changeSegmentType, String changeInAppMessageType, String changeInAppTemplateType, String changeTemplateButtonType, String changeOsType, String changeDeviceType, String changeControlGroup, String personalisation) throws InterruptedException, ParseException {

        /*Setting up data for creating mobile push one time campaign */
        campaignMeta.setCurrentRunningTestCaseName("mobileInAppCampaign");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setHow_often(howOften);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setHow_often("Once per day");

        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
        campaignMeta.getWhen().setChange_campaign_endType(changeCampaignEndType);


        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);

        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_os_type(changeOsType);
        campaignMeta.getWho().setChange_device_type(changeDeviceType);

        campaignMeta.getWhat().setSelect_message_type(selectMessageType);
        campaignMeta.getWhat().setSelect_template_type(selectTemplateType);
        campaignMeta.getWhat().setTemplate_button_type(selectTemplateButtonType);
        campaignMeta.getWhat().setChange_inapp_templateType(changeInAppTemplateType);
        campaignMeta.getWhat().setChange_inapp_messageType(changeInAppMessageType);
        campaignMeta.getWhat().setChange_inapp_templateButton(changeTemplateButtonType);
        campaignMeta.setPersonalisation(personalisation);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignCrudFunction();
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }

    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void smsCampaign(String csvKey, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String campaignReachTo, String systemControlGroup, String changeCampaignSTartType, String changeCampaignEndType, String changeSegmentType, String changeCampaignReachToType, String changeControlGroup, String personalisation) throws InterruptedException, ParseException {

        /*Setting up data for creating mobile push one time campaign */
        campaignMeta.setCurrentRunningTestCaseName("smsCampaign");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
        campaignMeta.getWhen().setChange_campaign_endType(changeCampaignEndType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
        campaignMeta.setPersonalisation(personalisation);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignCrudFunction();
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }

    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void appInboxCampaign(String csvKey, String channelName, String campaignType, String campaignStartType, String campaignEndType, String segmentSelectionType, String segmentActionType,String campaignReachTo, String selectMessageType, String osType, String deviceType, String selectTemplateType, String selectTemplateButtonType, String systemControlGroup, String changeCampaignSTartType, String changeCampaignEndType, String changeSegmentType, String changeCampaignReachToType, String changeInAppMessageType, String changeOsType, String changeDeviceType, String changeInAppTemplateType, String changeTemplateButtonType, String changeControlGroup) throws InterruptedException, ParseException {

        /*Setting up data for creating mobile push one time campaign */
        campaignMeta.setCurrentRunningTestCaseName("appInboxCampaign");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setHow_often("Once per day");

        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
        campaignMeta.getWhen().setChange_campaign_endType(changeCampaignEndType);


        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);

        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_os_type(changeOsType);
        campaignMeta.getWho().setChange_device_type(changeDeviceType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
        campaignMeta.getWho().setSaved_segment_type(segmentActionType);

        campaignMeta.getWhat().setSelect_message_type(selectMessageType);
        campaignMeta.getWhat().setSelect_template_type(selectTemplateType);
        campaignMeta.getWhat().setTemplate_button_type(selectTemplateButtonType);
        campaignMeta.getWhat().setChange_inapp_templateType(changeInAppTemplateType);
        campaignMeta.getWhat().setChange_inapp_messageType(changeInAppMessageType);
        campaignMeta.getWhat().setChange_inapp_templateButton(changeTemplateButtonType);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);

        campaignCrudFunction();
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }

    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void emailCampaign(String csvKey, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String osType, String deviceType, String campaignReachTo, String systemControlGroup, String changeCampaignSTartType, String changeSegmentType, String changeOsType, String changeDeviceType, String changeCampaignReachToType, String changeControlGroup, String personalisation) throws InterruptedException, ParseException {


        /*Setting up data for creating mobile push one time campaign */
        campaignMeta.setCurrentRunningTestCaseName("emailCampaign");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
        campaignMeta.getWhen().setHow_often("Once per day");
        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_os_type(changeOsType);
        campaignMeta.getWho().setChange_device_type(changeDeviceType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
        campaignMeta.setPersonalisation(personalisation);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignCrudFunction();
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }

    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void webPushCampaign(String csvKey, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String osType, String deviceType, String campaignReachTo, String systemControlGroup, String changeCampaignSTartType, String changeSegmentType, String changeOsType, String changeDeviceType, String changeCampaignReachToType, String changeControlGroup, String personalisation) throws InterruptedException, ParseException {


        /*Setting up data for creating mobile push one time campaign */
        campaignMeta.setCurrentRunningTestCaseName("webPushCampaign");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
        campaignMeta.getWhen().setHow_often("Once per day");
        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_os_type(changeOsType);
        campaignMeta.getWho().setChange_device_type(changeDeviceType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
        campaignMeta.setPersonalisation(personalisation);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignCrudFunction();


        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }


    private void campaignCrudFunction() throws InterruptedException, ParseException {
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

        CampaignsHomePage campaignsHomePage = new CampaignsHomePage(driver);
        campaignName = campaignsHomePage.createCampaign();


        /*Below method validating the target details for created campaign and its cloned one*/
//        validateTargetDetails();


        /*Cloning each campaign */

        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

        campaignsHomePage.searchAndStopCampaign(campaignName);

        campaignsHomePage.clickShowCampaignAction(campaignName);

        campaignsHomePage.cloneCampaignWithDifferentWhenWhoWhatQuery();


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

    private void validateTargetDetails() {
        JavascriptExecutor JS = (JavascriptExecutor) driver;
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@data-page='statsPg']")));
        SeleniumUtils.pause(3);
        String details1 = (String) JS.executeScript("return JSON.stringify(targetDetails);");
        System.out.println("*********before******"+ details1);
        JSONObject jsonObject1 = new JSONObject(details1);
        System.out.println("json1 " + jsonObject1);
        driver.findElement(By.id("targetReschedule")).click();
        SeleniumUtils.waitForPageLoaded(driver);
        driver.switchTo().frame(0);
        SeleniumUtils.pause(1);
        driver.findElement(By.id("btn_top_nav_continue")).click();
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@data-page='statsPg']")));
        SeleniumUtils.pause(3);

        String details2 = (String) JS.executeScript("return JSON.stringify(targetDetails);");
        System.out.println("*********after******"+ details2);
        JSONObject jsonObject2 = new JSONObject(details2);
        System.out.println("json2 " + jsonObject2);
        JSONObject differenceInTargetDetails = FileUtility.compare2JsonObject(jsonObject1, jsonObject2);
        switch (campaignMeta.getType().trim().toLowerCase()) {
            case "one time":
            case "multiple dates":
            case "recurring":
                Assert.assertTrue(Arrays.asList(JSONObject.getNames(differenceInTargetDetails)).size() == 8, "Only 8 differences should be found");
                for (String str : Arrays.asList(JSONObject.getNames(differenceInTargetDetails))) {
                    switch (str.trim().toLowerCase()) {
                        case "editurl":
                        case "tct":
                        case "provisionalid":
                        case "lastupdate":
                        case "tzinfo":
                        case "starttime":
                        case "_id":
                        case "startepoch":
                            Assert.assertTrue(true, "field matched");
                            break;
                        default:
                            Assert.assertTrue(false, "field mismatched");
                            break;
                    }
                }
                break;
            case "single action":
            case "inaction within time":
            case "on a date/time":
                Assert.assertTrue(Arrays.asList(JSONObject.getNames(differenceInTargetDetails)).size() == 7, "Only 7 differences should be found");
                for (String str : Arrays.asList(JSONObject.getNames(differenceInTargetDetails))) {
                    switch (str.trim().toLowerCase()) {
                        case "editurl":
                        case "tct":
                        case "provisionalid":
                        case "lastupdate":
                        case "starttime":
                        case "_id":
                        case "startepoch":
                            Assert.assertTrue(true, "field matched");
                            break;
                        default:
                            Assert.assertTrue(false, "field mismatched");
                            break;
                    }
                }
                break;
        }

    }

    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void whatsappCampaign(String csvKey, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String savedSegmentType, String campaignReachTo, String messageToBeSent, String templateType, String buttonType, String systemControlGroup, String changeCampaignSTartType, String changeSegmentType, String changeCampaignReachToType, String changeControlGroup, String personalisation) throws InterruptedException, ParseException {

        /*Setting up data for creating whatsapp one time campaign */
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
        campaignMeta.getWhen().setHow_often("Once per day");
        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        campaignMeta.getWho().setChange_segment_type(changeSegmentType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
        campaignMeta.getWhat().setWhatsAppMessageToBeSent(messageToBeSent);
        campaignMeta.getWhat().setSelect_template_type(templateType);
        campaignMeta.getWhat().setTemplate_button_type(buttonType);
        campaignMeta.getWho().setSaved_segment_type(savedSegmentType);
        campaignMeta.setPersonalisation(personalisation);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignCrudFunction();

        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }

    @Test(description = "verifying the status of campaign after actions like run, stop,", dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void verifyCampaignActions(String csvKey, String whatPageMessageType, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String osType, String deviceType, String campaignReachTo, String systemControlGroup, String changeCampaignReachToType) throws InterruptedException, ParseException {

        campaignMeta.setCsv_key(csvKey);
        campaignMeta.getWhat().setMessage_to_be_selected(whatPageMessageType);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
        campaignMeta.getWhen().setHow_often("Once per day");
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
        CampaignsHomePage campaignsHomePage = new CampaignsHomePage(driver);

        campaignsHomePage.createCampaignAndVerifyActions();

        campaignsHomePage.createCampaignWithApprovalPendingStatus();

        campaignsHomePage.searchAndVerifyCompletedCampaign();

        campaignsHomePage.createCampaignWithAwaitingNextRunStatus();

        logger.info("***** verifyCampaignActionsTest finished *****");
    }


    @Test(description = "Create mobile push campaign through API and Validate Stats page")
    public void SUC34629() throws IOException {
        logger.info("add new mobile push campaign Test Started");
        CampaignsHomePage campaignsHomePage = new CampaignsHomePage(driver);
        String campaignID = campaignsHomePage.createPushCampaignUsingAPI();
        campaignsHomePage.searchCampaign(campaignID);
        Assert.assertEquals(campaignsHomePage.verifyStatsPage(), campaignID, "Campaign doesnt match");
        logger.info("add new mobile push campaign Test Finished");
    }




    @Test
    public void test() throws IOException {

        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CATALOGS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        SeleniumUtils.pause(3);
        driver.findElement(By.xpath("//*[@for='fileInput']")).click();
        SeleniumUtils.pause(2);
        Runtime.getRuntime().exec("osascript " + System.getProperty("user.dir") + "/test_bed/AppleScriptForUploading/FileUploadOnMacOS.scpt");
        SeleniumUtils.pause(20);

    }
    @Test(dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void nativeDisplayTest(String csvKey, String channelName, String campaignStartType, String campaignEndType,  String segmentSelectionType, String osType, String deviceType, String selectMessageType, String selectTemplateType, String selectTemplateButtonType, String systemControlGroup) throws InterruptedException, ParseException {

        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channelName);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWhat().setSelect_message_type(selectMessageType);
        campaignMeta.getWhat().setMessage_to_be_selected(selectMessageType);
        campaignMeta.getWhat().setSelect_template_type(selectTemplateType);
        campaignMeta.getWhat().setTemplate_button_type(selectTemplateButtonType);

        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);

        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);
        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignCrudFunction();
        logger.info("***** Create " + campaignMeta.getChannel() + " ::::: " + campaignMeta.getType() + " ::::: " + " ::::: " + campaignMeta.getWhen().getCampaign_start_type() + " test finished *****");
    }


    //This test case requires recommendation of thai language as a pre-requisite else will not work
    //Catalog required containing thai language input (Name : DND Thai Language Catalog)
    //Recommendation required (Name : DND Thai Language Recommendation)
    @Test(description = "verifying campaign which is created using recommendation having a thai language catalog", dataProvider = "campaignParametersProvider", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION})
    public void SUC_34052(String csvKey, String whatPageMessageType, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String osType, String deviceType, String campaignReachTo, String systemControlGroup, String changeCampaignSTartType, String changeSegmentType, String changeOsType, String changeDeviceType, String changeCampaignReachToType, String changeControlGroup,String includePushIntoInApp) throws InterruptedException, ParseException {

            /*Setting up data for creating mobile push one time campaign */
            campaignMeta.setCurrentRunningTestCaseName("SUC-34052");
            campaignMeta.setCsv_key(csvKey);
            campaignMeta.getWhat().setMessage_to_be_selected(whatPageMessageType);
            campaignMeta.setChannel(channelName);
            campaignMeta.setType(campaignType);
            campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
            campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
            campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
            campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
            campaignMeta.getWhen().setHow_often("Once per day");
            campaignMeta.getWhen().setChange_campaign_startType(changeCampaignSTartType);
            campaignMeta.getWho().setDevice_os(osType);
            campaignMeta.getWho().setDevice_type(deviceType);
            campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
            campaignMeta.getWho().setChange_segment_type(changeSegmentType);
            campaignMeta.getWho().setChange_os_type(changeOsType);
            campaignMeta.getWho().setChange_device_type(changeDeviceType);
            campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachToType);
            campaignMeta.getWhat().setInclude_Msg_In_AppInbox(includePushIntoInApp);

            Map<String, String> controlGroupMap = new HashMap<String, String>();
            controlGroupMap.put("system control group", systemControlGroup);
            controlGroupMap.put("custom control group", "Custom Control Group");
            campaignMeta.getSetup().setControl_group(controlGroupMap);

            Map<String, String> changeControlGroupMap = new HashMap<String, String>();
            changeControlGroupMap.put("change system control group", changeControlGroup);
            changeControlGroupMap.put(" change custom control group", "Custom Control Group");
            campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);

            campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
            campaignCrudFunction();
            logger.info("***** verifyCampaignWithRecommendationOfThaiLanguageCatalog finished *****");
    }

    @Test(description = "To verify Notification click count on Campaign overview page and Events page", dataProvider = "campaignParametersProvider")
    public void SUC33389(String csvKey, String whatPageMessageType, String channelName, String campaignType, String campaignStartType, String campaignEndType, String campaignDeliveryType, String segmentSelectionType, String osType, String deviceType, String campaignReachTo, String systemControlGroup, String statName) throws ParseException, InterruptedException {

        logger.info("***** Verify Notification clicked count on Campaign overview and Event page started *****");

        campaignMeta.setCurrentRunningTestCaseName("SUC33389");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.getWhat().setMessage_to_be_selected(whatPageMessageType);
        campaignMeta.setChannel(channelName);
        campaignMeta.setType(campaignType);
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setCampaign_delivery(campaignDeliveryType);
        campaignMeta.getWhen().setHow_often("Once per day");
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");
        campaignMeta.getSetup().setControl_group(controlGroupMap);
        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);

        CampaignsHomePage campaignsHomePage = new CampaignsHomePage(driver);
        String campaignName = campaignsHomePage.createCampaign();
        String campaignID = campaignsHomePage.getCampaignID();

        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        campaignsHomePage.searchCampaign(campaignName);
        String notificationClicked = campaignsHomePage.getStatCount();

        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.EVENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        EventsPage eventsPage = new EventsPage(driver);

        Assert.assertEquals(eventsPage.getEventCount(statName, campaignID), notificationClicked);

        logger.info("***** Verify Notification clicked count on Campaign overview and Event page ended *****");
    }


    @DataProvider(name = "campaignParametersProvider")
    public Object[][] campaignParameters(Method method) {
        if (method.getName().equalsIgnoreCase("mobilePushOneTimeAndMultipleDate")) {
            return new String[][]{

                    /*setPushToInApp is not available for PBS later*/
                    {"Mobile push+One time-1", "singleMessage", "Mobile push", "One time", "now", "", "", "+ create an ad-hoc segment", "ios", "mobile", "all users", "apply", "later", "change saved segment query", "ios", "tablet", "limit users", "remove",""},
                    {"Mobile push+One time-2", "multiVariate", "Mobile push", "One time", "later", "", "Best time for every user", "+ create an ad-hoc segment", "ios", "tv", "limit users", "remove", "now", "change saved segment query", "android", "mobile", "all users", "apply",""},
                    {"Mobile push+One time-3", "a/b", "Mobile push", "One time", "later", "", "A fixed time", "+ create an ad-hoc segment", "ios", "tv", "limit users", "remove", "now", "change saved segment query", "android", "mobile", "all users", "apply",""},
                    {"Mobile push+One time-4", "singleMessage", "Mobile push", "One time", "Later", "", "Best time for every user", "+ create an ad-hoc segment", "ios", "mobile", "limit users", "apply", "now", "change saved segment query", "android", "mobile", "all users", "remove",""},
                    {"Mobile push+One time-5", "singleMessage", "Mobile push", "One time", "Later", "", "A fixed time", "+ create an ad-hoc segment", "ios", "tv", "limit users", "apply", "now", "change saved segment query", "ios", "mobile", "all users", "remove",""},
                    {"mobile push+multiple dates-6", "singleMessage", "Mobile push", "Multiple dates", "", "", "Best time for every user", "+ create an ad-hoc segment", "android", "mobile", "limit users", "apply", "", "change saved segment query", "ios", "mobile", "all users", "apply","setPushToInApp"},
                    {"mobile push+multiple dates-7", "singleMessage", "Mobile push", "Multiple dates", "", "", "Best time for every user", "+ create an ad-hoc segment", "android", "mobile", "all users", "remove", "", "change saved segment query", "ios", "mobile", "all users", "apply",""},
                    {"Mobile push+Inaction within time-1", "singleMessage", "Mobile push", "inaction within time", "now", "never end", "", "+ create an ad-hoc segment", "unchecked all os type", "unchecked all device type", "all users", "apply", "later", "change saved segment query", "ios", "tablet", "limit users", "remove","setPushToInApp"},
                    {"Mobile push+Inaction within time-2", "singleMessage", "Mobile push", "inaction within time", "later", "", "never end", "+ create an ad-hoc segment", "unchecked all os type", "unchecked all device type", "limit users", "", "", "", "apply", "now", "", "select date and time", "saved segments", "android", "tv", "all users",""},
                    {"Mobile push+Inaction within time-3", "singleMessage", "Mobile push", "inaction within time", "now", "", "select date and time", "+ create an ad-hoc segment", "ios", "mobile", "limit users", "", "", "", "remove", "later", "", "never end", "change saved segment query", "android", "tablet", "all users","setPushToInApp"},
                    {"Mobile push+Inaction within time-4", "singleMessage", "Mobile push", "inaction within time", "later", "", "select date and time", "+ create an ad-hoc segment", "unchecked all os type", "unchecked all device type", "limit users", "", "", "", ""},

            };
        }
        else if (method.getName().equalsIgnoreCase("nativeDisplayTest")) {
            return new String[][]{
                    {"NativeDisplay+SingleMsg+SimpleBanCon","native display","now","","+ create an ad-hoc segment","android", "mobile","Single message","Simple banner","With content notification","remove"},
                    {"NativeDisplay+SingleMsg+BanWithIcon","native display","now","","+ create an ad-hoc segment","android", "mobile","Single message","Banner with icon","Select","remove"},
                    {"NativeDisplay+SingleMsg+CarouselWithCon","native display","now","","+ create an ad-hoc segment","android", "mobile","Single message","Carousel banner","With content notification","remove"},
                    {"NativeDisplay+SingleMsg+CustomKV","native display","now","","+ create an ad-hoc segment","android", "mobile","Single message","Custom key value","Select","remove"},

            };
        }
        else if (method.getName().equalsIgnoreCase("mobileInAppCampaign")) {
            return new String[][]{
                    {"Mobile inapp-1", "Mobile in-app", "now", "never end", "Once per session", "+ create an ad-hoc segment", "", "", "Single message", "Cover", "With content notification", "apply", "later", "Select date and time", "change saved segment query", "Single message", "Interstitial", "Image only notification", "", "", "remove"},
                    {"Mobile inapp-2", "Mobile in-app", "now", "select date and time", "Exclude from campaign limits", "saved segments", "", "", "Single message", "Alert", "Select", "", "later", "Never end", "change saved segment query", "Single message", "Custom HTML", "Select", "", "", ""},
            };
        } else if (method.getName().equalsIgnoreCase("smsCampaign")) {
            return new String[][]{
                    {"sms+one time-1", "Sms", "One time", "now", "", "", "+ create an ad-hoc segment", "limit users", "remove", "later", "", "change saved segment query", "all users", "apply", "personalisation_false"},
                    {"sms+one time-2", "Sms", "Recurring", "", "never end", "best time for every user", "+ create an ad-hoc segment", "limit users", "apply", "", "Select date", "change saved segment query", "all users", "apply"}
            };
        } else if (method.getName().equalsIgnoreCase("appInboxCampaign")) {
            return new String[][]{
                    {"app inbox+one time-1", "app inbox", "One time", "now", "", "+ create an ad-hoc segment","", "limit users", "Single message", "", "", "Simple message", "Select", "remove", "later", "", "change saved segment query", "all users", "Single message", "", "", "Carousel message", "Image only notification", "remove"},
                    {"app inbox+one time-2", "app inbox", "One time", "now", "", "saved segments","Actions", "limit users", "Single message", "", "", "Carousel message", "With content notification", "remove", "later", "", "+ create an ad-hoc segment", "all users", "Single message", "", "", "Carousel message", "Image only notification", "remove"},
                    {"app inbox+multiple dates-3", "app inbox", "Multiple dates", "", "", "+ create an ad-hoc segment","", "limit users", "Single message", "", "", "Carousel message", "With content notification", "remove", "", "", "change saved segment query", "all users", "Single message", "", "", "Message with icon", "Select", "remove"},
                    {"app inbox+recurring-4", "app inbox", "Recurring", "", "", "saved segments","Actions", "limit users", "Single message", "", "", "Message with icon", "Select", "apply", "", "Select date", "+ create an ad-hoc segment", "all users", "Single message", "", "", "Carousel message", "Image only notification", "remove"},
                    {"app inbox+single action-5", "app inbox", "Single action", "Later", "select date and time", "saved segments","Single action", "all users", "Single message", "", "", "Carousel message", "With content notification", "apply", "", "never end", "+ create an ad-hoc segment", "limit users", "Single message", "", "", "Simple message", "Select", "remove"},
                    {"app inbox+inaction within time-6", "app inbox", "Inaction within time", "Now", "never end", "saved segments","Actions", "all users", "Single message", "", "", "Carousel message", "With content notification", "apply", "", "select date and time", "+ create an ad-hoc segment", "limit users", "Single message", "", "", "Simple message", "Select", "remove"},


            };
        } else if (method.getName().equalsIgnoreCase("mobilePushRecurringCampaign")) {
            return new String[][]{
                    {"Mobile push+Recurring-1", "Mobile push", "Recurring", "never end", "best time for every user", "+ create an ad-hoc segment", "android", "all device type", "all users", "apply", "Select date", "change saved segment query", "ios", "mobile", "limit users", "apply"},
                    {"Mobile push+Recurring-2", "Mobile push", "Recurring", "select date", "A fixed time", "+ create an ad-hoc segment", "unchecked all os type", "unchecked all device type", "limit users", "remove", "after", "change saved segment query", "android", "tv", "all users", "remove"},
                    {"Mobile push+Recurring-3", "Mobile push", "Recurring", "after", "A fixed time", "", "", "", "", "", "", "", "", "", "", "", ""},

            };
        } else if (method.getName().equalsIgnoreCase("mobilePushSingleAction")) {
            return new String[][]{

                    {"Mobile push+Single action-1", "Mobile push", "Single action", "now", "Never end", "No Delay", "+ create an ad-hoc segment", "ios", "all device type", "all users", "apply", "later", "Select date and time", "Delay by", "change saved segment query", "android", "tablet", "only x users overall", "apply"},
                    {"Mobile push+Single action-2", "Mobile push", "Single action", "later", "select date and time", "Delay by", "+ create an ad-hoc segment", "android", "mobile", "limit users", "remove", "now", "never end", "No Delay", "change saved segment query", "ios", "mobile", "all users", "apply"},
            };

        } else if (method.getName().equalsIgnoreCase("emailCampaign")) {
            return new String[][]{

                    {"email+one time-1", "Email", "One time", "now", "", "", "+ create an ad-hoc segment", "ios", "all device type", "all users", "apply", "later", "change saved segment query", "android", "tablet", "only x users overall", "apply"},
            };
        } else if (method.getName().equalsIgnoreCase("webPushCampaign")) {
            return new String[][]{
                    {"web push+one time-1", "web push", "One time", "now", "", "", "+ create an ad-hoc segment", "", "", "all users", "apply", "later", "change saved segment query", "", "", "only x users overall", "remove"},
                    {"web push+one time-2", "web push", "One time", "later", "", "", "+ create an ad-hoc segment", "", "", "all users", "apply", "later", "change saved segment query", "", "", "only x users overall", "remove"},
                    {"web push+one time-3", "web push", "Multiple dates", "", "", "Best time for every user", "+ create an ad-hoc segment", "", "", "all users", "apply", "", "change saved segment query", "", "", "only x users overall", "remove"},
                    {"web push+one time-5", "web push", "Recurring", "Never end", "", "Best time for every user", "+ create an ad-hoc segment", "", "", "all users", "apply", "", "change saved segment query", "", "", "only x users overall", "remove"},
                    {"web push+single action-6", "web push", "Single action", "now", "Never end", "", "+ create an ad-hoc segment", "", "", "all users", "apply", "", "change saved segment query", "", "", "only x users overall", "remove"},
                    {"web push+inaction within time-7", "web push", "Inaction within time", "now", "Never end", "", "+ create an ad-hoc segment", "", "", "all users", "apply", "", "change saved segment query", "", "", "only x users overall", "remove"},
                    {"web push+on a date/time-8", "web push", "on a date/time", "now", "Never end", "", "+ create an ad-hoc segment", "", "", "all users", "apply", "", "change saved segment query", "", "", "only x users overall", "remove"}
            };
        } else if (method.getName().equalsIgnoreCase("whatsappCampaign")) {
            return new String[][]{
                    {"whatsapp+one time-1", "whatsapp", "One time", "now", "", "", "+ create an ad-hoc segment", "all users", "Test message", "verify", "Select", "apply", "later", "change saved segment query", "only x users overall", "remove", "personalisation_false"},
                    {"whatsapp+single action-2", "whatsapp", "Single action", "now", "", "", "+ create an ad-hoc segment", "all users", "Test message", "verify", "Select", "apply", "later", "change saved segment query", "only x users overall", "remove", "personalisation_true"},
                    {"whatsapp+inaction within time-3", "whatsapp", "Inaction within time", "now", "", "", "+ create an ad-hoc segment", "all users", "Test message", "verify", "Select", "apply", "later", "change saved segment query", "only x users overall", "remove", "personalisation_false"},
                    {"whatsapp+on a date/time-4", "whatsapp", "on a date/time", "now", "", "", "+ create an ad-hoc segment", "all users", "Test message", "verify", "Select", "apply", "later", "change saved segment query", "all users", "remove", "personalisation_true"},
                    {"whatsapp+one time-1", "whatsapp", "One time", "now", "", "", "saved segments", "Actions", "all users", "Test message", "verify", "Select", "apply", "later", "change saved segment query", "only x users overall", "remove"},
            };
        } else if (method.getName().equalsIgnoreCase("verifyCampaignActions")) {
            return new String[][]{
                    //{"Mobile push+One time-1", "singleMessage", "Mobile push", "One time", "now", "", "", "+ create an ad-hoc segment", "ios", "mobile", "all users", "apply", "personalisation_false"},
                    {"mobile push+multiple dates-6", "singleMessage", "Mobile push", "Multiple dates", "", "", "Best time for every user", "+ create an ad-hoc segment", "android", "mobile", "limit users", "apply", "all users"},
                    // {"Mobile push+One time-1", "singleMessage", "Mobile push", "One time", "now", "", "", "+ create an ad-hoc segment", "ios", "mobile", "all users", "apply", "personalisation_false", "Approval Pending"},
            };
        } else if(method.getName().equalsIgnoreCase("SUC_34052")){
            return new String[][]{
                    {"Mobile push+One time-7", "singleMessage", "Mobile push", "One time", "Later", "", "A fixed time", "+ create an ad-hoc segment", "android", "tv", "limit users", "apply", "now", "change saved segment query", "android", "mobile", "all users", "remove",""}
            };
        } else if (method.getName().equalsIgnoreCase("SUC33389")) {
            return new String[][]{
                    {"SUC33389-1", "singleMessage", "Mobile push", "One time", "now", "", "", "+ create an ad-hoc segment", "ios", "mobile", "all users", "apply", "Notification Clicked"}
            };
        }else {
            logger.info("return empty object while no method matches");
            return new String[][]{};
        }
    }

    @AfterMethod(alwaysRun = true)
    private void testResult(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                driver.switchTo().defaultContent();
                WebDriverWait webDriverWait = new WebDriverWait(driver, 20);
                webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@class,'js-pane-container')]"))));
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[contains(@class,'js-pane-container_close')]")));

            } catch (NoSuchElementException e) {
                logger.info(e.getMessage());
            }
        } else {
            logger.info("test seems to be passed");
        }
    }

}