package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.campaigns_page.MobilePushPage;
import com.clevertap.ui.pages.settings_menu_page.CampaignIntegration;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.*;
import com.clevertap.utils.RestApiUtil.ApiUtility;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static com.jayway.restassured.RestAssured.given;


public class CampaignIntegrationTest extends BaseTest{

    private static final String I_OS = "ios";
    private static final String ANDROID = "android";
    private static final String SENDGRID="sendgrid";
    private static final String MSG91="msg91";
    private static final String ENABLED = "ENABLED";
    private static final String SETTINGS_WEB_PUSH = "Settings\nWeb Push";
    private static final String TEST_AUTOMATION_EMAIL = "Automation-Email_provider";
    private static final String TEST_AUTOMATION_SMS = "Automation-sms_provider";
    private static final String TEST_AUTOMATION_WEBHOOK = "Automation-WEBHOOK";
    private static final String SETTINGS_SMS = "Settings\nSMS";
    private static final String SETTINGS_AD_NETWORKS = "Settings\nAD networks";
    private static final String SETTINGS_PUSH_NOTIFICATIONS = "Settings\n" +
            "Push Notifications";
    private static final String SETTINGS_EMAIL = "Settings\nEmail";
    private Logger logger;
    private WebDriver driver;
    private CampaignIntegration integrate;
    private SweetAlert sweetAlert;
    private MobilePushPage mobilePushPage;
    private static String emailProviderName;
    private static String smsProviderName;
    private static String whatsappProviderName="Automation-Whatsapp_providerNom";
    private static String webhookName;


    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver=getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
        logger = configureLogger(getClass());
        integrate = new CampaignIntegration(driver);
        sweetAlert = new SweetAlert(driver);
        mobilePushPage=new MobilePushPage(driver);
    }


    @Test(description = "verify push Notification can be saved for ios and android page  under integration", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void openPushNotificationsAndSaveConfigurationForIosAndAndroid() {
        logger.info("open Push Notification Page Test Started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
        String campIntegPageHeaderText = integrate.getHeaderText().toLowerCase();
        Assert.assertTrue(campIntegPageHeaderText.contains(SETTINGS_PUSH_NOTIFICATIONS.toLowerCase()), "CampaignIntegration Page successfully launched");

        //saving configuration for IOS
        integrate.getOsForCampInt(I_OS);
        SeleniumUtils.waitForElementToClickable(driver,CampaignIntegration.appBundleId,10);
        CampaignIntegration.appBundleId.clear();
        SeleniumUtils.enterInputText(driver, CampaignIntegration.appBundleId, getValue("IosAppBundleID"));
        SeleniumUtils.waitForElementToClickable(driver,CampaignIntegration.teamId,10);
        CampaignIntegration.teamId.clear();
        SeleniumUtils.enterInputText(driver, CampaignIntegration.teamId, getValue("IosTeamID"));
        integrate.saveButtonForIos();
        SeleniumUtils.waitForElementToClickable(driver,sweetAlert.sweetAlertConfirmOK,10);
        Assert.assertTrue(sweetAlert.getSweetAlertSuccessSignal(), "Failed to save configuration for IOS");

        //saving configuration for ANDROID
        //verifying FCM tokens can be saved
        integrate.getOsForCampInt(ANDROID);
        SeleniumUtils.waitForElementToLoad(driver, CampaignIntegration.GCMSenderIdInput);
        SeleniumUtils.enterInputText(driver, CampaignIntegration.GCMSenderIdInput, getValue("AndroidGCM/FCM"));
        SeleniumUtils.waitForElementToLoad(driver, CampaignIntegration.GCMServerApiKeyInput);
        SeleniumUtils.enterInputText(driver, CampaignIntegration.GCMServerApiKeyInput, getValue("GCM/FCMApiKey"));
        SeleniumUtils.waitAndClick(driver,integrate.saveFCMCredentialsSettings);
        SeleniumUtils.waitForElementToClickable(driver,sweetAlert.sweetAlertConfirmOK,10);
        Assert.assertTrue(sweetAlert.getSweetAlertSuccessSignal(), "Failed to save FCM keys for Android");

        //Verifying Xiaomi Credentials can be saved
        SeleniumUtils.waitAndClick(driver,integrate.xiaomiCredentials);
        SeleniumUtils.waitForElementToLoad(driver, integrate.xiaomiAppSecret);
        integrate.xiaomiAppSecret.clear();
        SeleniumUtils.enterInputText(driver, integrate.xiaomiAppSecret, getValue("AppSecret"));
        SeleniumUtils.waitForElementToLoad(driver, integrate.xiaomiAppPackage);
        integrate.xiaomiAppPackage.clear();
        SeleniumUtils.enterInputText(driver, integrate.xiaomiAppPackage, getValue("package_name"));
        SeleniumUtils.waitAndClick(driver,integrate.saveXiaomiCredentialsSettings);
        SeleniumUtils.waitForElementToClickable(driver,sweetAlert.sweetAlertConfirmOK,10);
        Assert.assertTrue(sweetAlert.getSweetAlertSuccessSignal(), "Failed to save Xiaomi keys for Android");


        //Verifying Baidu Credentials can be saved
        SeleniumUtils.waitAndClick(driver,integrate.baiduCredentials);
        SeleniumUtils.waitForElementToLoad(driver, integrate.baiduSecretKey);
        integrate.baiduSecretKey.clear();
        SeleniumUtils.enterInputText(driver, integrate.baiduSecretKey, getValue("Baidu_Secretkey"));
        SeleniumUtils.waitForElementToLoad(driver, integrate.baiduApiKey);
        integrate.baiduApiKey.clear();
        SeleniumUtils.enterInputText(driver, integrate.baiduApiKey, getValue("Baidu_APIkey"));
        SeleniumUtils.waitAndClick(driver,integrate.saveBaiduCredentialsSettings);
        SeleniumUtils.waitForElementToClickable(driver,sweetAlert.sweetAlertConfirmOK,10);
        Assert.assertTrue(sweetAlert.getSweetAlertSuccessSignal(), "Failed to save Baidu keys for Android");


        //ToDo : add verification for turn off notification channel :: MS remaining because it was not working during baidu,xioami integration feature
//        mobilePushPage.turnOffNotificationchannel();
//        integrate.saveButtonForAndroid();
//        SeleniumUtils.waitForElementToClickable(driver,sweetAlert.sweetAlertConfirmOK,10);
//        Assert.assertTrue(sweetAlert.getSweetAlertSuccessSignal(), "Failed to save configuration for Android");

    }

    @Test(description = "verify push Notification cannot be configured page  under integration", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void verifyPushNotificationCannotBeConfiguredWithWrongDataForAndroid(){
        try {
            Mocha.forceNavigate=true;
            //Entering invalid GCM Sender ID
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
            integrate.getOsForCampInt(ANDROID);
            SeleniumUtils.waitForElementToLoad(driver, CampaignIntegration.GCMSenderIdInput);
            SeleniumUtils.enterInputText(driver, CampaignIntegration.GCMSenderIdInput, getValue("AndroidGCM/FCM") + "7");
            SeleniumUtils.waitAndClick(driver,integrate.saveFCMCredentialsSettings);
            SeleniumUtils.waitForElementToLoad(driver,sweetAlert.sweetAlertError);
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Error message was not present");
            Mocha.forceNavigate=true;

            //Entering invalid GCM Server Api Key
//            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
            driver.navigate().refresh();
            SeleniumUtils.waitForElementToLoad(driver, CampaignIntegration.GCMServerApiKeyInput);
            SeleniumUtils.enterInputText(driver, CampaignIntegration.GCMServerApiKeyInput, getValue("GCM/FCMApiKey") + "7");
            SeleniumUtils.waitAndClick(driver,integrate.saveFCMCredentialsSettings);
            SeleniumUtils.waitForElementToLoad(driver,sweetAlert.sweetAlertError);
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Error message was not present");

            //Entering blank Xiaomi AppSecret key
//            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
            driver.navigate().refresh();
            SeleniumUtils.waitAndClick(driver,integrate.xiaomiCredentials);
            SeleniumUtils.scrollDownLittle(driver);
            SeleniumUtils.waitForElementToLoad(driver, integrate.xiaomiAppSecret);
            integrate.xiaomiAppSecret.clear();
            SeleniumUtils.waitAndClick(driver,integrate.saveXiaomiCredentialsSettings);
            Assert.assertTrue(integrate.xiaomiAppSecret.getAttribute("style").contains("red"),"Should not allow to save blank app secret key for xiaomi");

            //Entering invalid Xiaomi AppSecret key
//            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
            driver.navigate().refresh();
            SeleniumUtils.waitAndClick(driver,integrate.xiaomiCredentials);
            SeleniumUtils.scrollDownLittle(driver);
            integrate.xiaomiAppSecret.clear();
            SeleniumUtils.enterInputText(driver, integrate.xiaomiAppSecret, getValue("AppSecret")+"7");
            SeleniumUtils.waitAndClick(driver,integrate.saveXiaomiCredentialsSettings);
            SeleniumUtils.waitForElementToLoad(driver,sweetAlert.sweetAlertError);
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Should not allow to save invalid App Secret for Xiaomi");

            //Entering blank Xiaomi Package Name key
//            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
            driver.navigate().refresh();
            SeleniumUtils.waitAndClick(driver,integrate.xiaomiCredentials);
            SeleniumUtils.scrollDownLittle(driver);
            SeleniumUtils.waitForElementToLoad(driver, integrate.xiaomiAppPackage);
            integrate.xiaomiAppPackage.clear();
            SeleniumUtils.waitAndClick(driver,integrate.saveXiaomiCredentialsSettings);
            Assert.assertTrue(integrate.xiaomiAppPackage.getAttribute("style").contains("red"),"Should not allow to save blank app secret key for xiaomi");


            //Entering blank baidu secret key
            driver.navigate().refresh();
            SeleniumUtils.waitAndClick(driver,integrate.baiduCredentials);
            SeleniumUtils.scrollDownLittle(driver);
            SeleniumUtils.waitForElementToLoad(driver, integrate.baiduSecretKey);
            integrate.baiduSecretKey.clear();
            SeleniumUtils.waitAndClick(driver,integrate.saveBaiduCredentialsSettings);
            Assert.assertTrue(integrate.baiduSecretKey.getAttribute("style").contains("red"),"Should not allow to save blank secret key for baidu");

            //Entering invalid baidu secret key
            driver.navigate().refresh();
            SeleniumUtils.waitAndClick(driver,integrate.baiduCredentials);
            SeleniumUtils.scrollDownLittle(driver);
            integrate.baiduSecretKey.clear();
            SeleniumUtils.enterInputText(driver, integrate.baiduSecretKey, getValue("Baidu_Secretkey")+"7");
            SeleniumUtils.waitAndClick(driver,integrate.saveBaiduCredentialsSettings);
            SeleniumUtils.waitForElementToLoad(driver,sweetAlert.sweetAlertError);
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Should not allow to save invalid Secret key for Baidu");


            //Entering blank baidu api key
            driver.navigate().refresh();
            SeleniumUtils.waitAndClick(driver,integrate.baiduCredentials);
            SeleniumUtils.scrollDownLittle(driver);
            SeleniumUtils.waitForElementToLoad(driver, integrate.baiduApiKey);
            integrate.baiduApiKey.clear();
            SeleniumUtils.waitAndClick(driver,integrate.saveBaiduCredentialsSettings);
            Assert.assertTrue(integrate.baiduApiKey.getAttribute("style").contains("red"),"Should not allow to save blank api key for baidu");

            //Entering invalid baidu api key
            driver.navigate().refresh();
            SeleniumUtils.waitAndClick(driver,integrate.baiduCredentials);
            SeleniumUtils.scrollDownLittle(driver);
            SeleniumUtils.enterInputText(driver, integrate.baiduApiKey, getValue("Baidu_APIkey")+"7");
            SeleniumUtils.waitAndClick(driver,integrate.saveBaiduCredentialsSettings);
            SeleniumUtils.waitForElementToLoad(driver,sweetAlert.sweetAlertError);
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Should not allow to save invalid api key for Baidu");


        }finally {
            Mocha.forceNavigate=false;
        }

    }

    @Test(description = "verify push Notification cannot be configured for wrong data page  under integration", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void verifyPushNotificationCannotBeConfiguredWithWrongDataForIos() {
        try {
            //Entering invalid Team ID
            Mocha.forceNavigate=true;
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
            integrate.getOsForCampInt(I_OS);
            SeleniumUtils.waitForElementToLoad(driver,CampaignIntegration.teamId);
            SeleniumUtils.enterInputText(driver, CampaignIntegration.teamId, getValue("IosTeamID")+"7");
            SeleniumUtils.scrollDownLittle(driver);
            integrate.saveButtonForIos();
            SeleniumUtils.waitForElementToLoad(driver,sweetAlert.sweetAlertError);
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Error message was not present");

            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
            integrate.getOsForCampInt(I_OS);
            SeleniumUtils.waitForElementToLoad(driver,CampaignIntegration.appBundleId);
            SeleniumUtils.enterInputText(driver, CampaignIntegration.appBundleId, getValue("IosAppBundleID")+"7");
            integrate.saveButtonForIos();
            SeleniumUtils.waitForElementToLoad(driver,sweetAlert.sweetAlertError);
            Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Error message was not present");

        }
        finally {
            Mocha.forceNavigate=false;
        }
    }

    @Test(description = "verify user is able to create Email provider under integration page", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void creatVerifyAndSendTestSendGridEmailProvider() {
        Reporter.log("create Email provider Test Started",true);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.EMAIL.toString());
        String campIntegPageHeaderText = integrate.getHeaderText();
        Assert.assertTrue(campIntegPageHeaderText.contains(SETTINGS_EMAIL), "CampaignIntegration Page successfully launched");

        emailProviderName= Data.randomAlphaNumeric(TEST_AUTOMATION_EMAIL, 3);
        Reporter.log("email provider name : "+emailProviderName,true);
        integrate.openProvider();

        List<String> expectedValues=new ArrayList<String>();
        expectedValues.add(emailProviderName);
        expectedValues.add(getValue("SENDGRID_HOST_KENSCIO"));
        expectedValues.add(getValue("SENDGRID_USERNAME"));
        expectedValues.add(getValue("SENDGRID_PASSWORD"));
        expectedValues.add(getValue("SENDGRID_FROMADDRESS"));
        expectedValues.add(getValue("SENDGRID_PORT"));
        Collections.sort(expectedValues);

        Reporter.log("selecting email provider as 'SendGrid'"+emailProviderName,true);
        integrate.selectEmailProviderName("SendGrid");

        Reporter.log("filling all the details in the form",true);
        integrate.fillNickName(SENDGRID,emailProviderName);
        integrate.fillHostName(SENDGRID,getValue("SENDGRID_HOST_KENSCIO"));
        integrate.fillUserName(SENDGRID,getValue("SENDGRID_USERNAME"));
        integrate.fillPassword(SENDGRID,getValue("SENDGRID_PASSWORD"));
        integrate.fillFromAddress(SENDGRID,getValue("SENDGRID_FROMADDRESS"));

        integrate.sendTestEmailClick(SENDGRID);
        integrate.setUpEmailTitleAndMsg(getValue("SENDTEST_EMAILID"));
        SeleniumUtils.pause(3);
        Assert.assertTrue(integrate.verifySentTestResponse(),"email not sent successfully");
        SeleniumUtils.waitForElementToClickable(driver,integrate.closeSendTestModal,10);
        SeleniumUtils.performClick(driver,integrate.closeSendTestModal);

        integrate.saveEmail();
        integrate.waitForTableToLoad();
        integrate.verifySettingSaveResponse();

        Reporter.log("verifying the send grid is reflected on the page",true);
        List<String> cellValue = integrate.getDataFromRightTable("//*[@class='CT-table__right']", 1, driver);
        Assert.assertTrue(cellValue.contains(emailProviderName), "newly created sendgrid was not present on listing page");

        List<String> actualValueList=integrate.verifyEmailProviderDetails(SENDGRID,emailProviderName);
        Assert.assertEquals(expectedValues,actualValueList,"values for email provider was not saved properly");
        Reporter.log("create Email provider Test Started",true);
    }

    @Test(description = "verify user is able to edit Email provider under integration page", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL},dependsOnMethods = {"creatVerifyAndSendTestSendGridEmailProvider"})
    public void editSendGridEmailProviderWithValidAndInvalidValues() {
        Reporter.log("edit Email provider Test Started",true);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.EMAIL.toString());
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='"+emailProviderName+"']")));
        integrate.fillFromAddress(SENDGRID,"gaurav.yadav@clevertap.com");
        integrate.saveEmail();
        integrate.waitForTableToLoad();
        integrate.verifySettingSaveResponse();

        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='"+emailProviderName+"']")),10);
        SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='"+emailProviderName+"']")));
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+SENDGRID+"']//input[@name='fromAddress']")));
        Assert.assertEquals("gaurav.yadav@clevertap.com",driver.findElement(By.xpath("//*[@id='"+SENDGRID+"']//input[@name='fromAddress']")).getAttribute("value"));

        integrate.fillUserName(SENDGRID,getValue("SENDGRID_USERNAME")+"a");
        integrate.saveEmail();
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Error message was not present");
        Reporter.log("edit Email provider Test Ended",true);
    }



    //@Test(description = "verify SMS Notification page of under integration", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void OpenSmsNotification() {
        Reporter.log("open SMS Notification Page Test Started",true);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.SMS.toString());
        String campIntegPageHeaderText = integrate.getHeaderText();
        Assert.assertTrue(campIntegPageHeaderText.contains(SETTINGS_SMS), "CampaignIntegration Page successfully launched");
        Reporter.log("settings_menu_test");


    }

    @Test(description = "verify Web push Notification page of under integration")
    public void openWebPushNotification() {
        Reporter.log("open WebPush Notification Page Test Started",true);
        String greenRGBA = "rgba(30, 184, 88, 1)";
        String greyRGBA = "rgba(210, 213, 216, 1)";
        String switchSliderText = null;
        Mocha.forceNavigate=true;
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBPUSH.toString());
        String CampIntegPageHeaderText = integrate.getHeaderText();
        Assert.assertTrue(CampIntegPageHeaderText.contains(SETTINGS_WEB_PUSH), "CampaignIntegration Page successfully launched");
        switchSliderText = integrate.getSwitchSliderInnerText();
        if (switchSliderText.contains(ENABLED)) {
            String cssValue = integrate.getCssValue();
            Assert.assertTrue(cssValue.contains(greenRGBA), "Button is Enabled");
        } else {
            String cssValue = integrate.getCssValue();
            Assert.assertTrue(cssValue.contains(greyRGBA), "Button is Disabled");

        }
        boolean status = integrate.getValidLink();
        Assert.assertTrue(status, "");
        Reporter.log("open WebPush Notification Page Test Finished",true);

    }

    //@Test(description = "verify Remarketing Notification page of under integration", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void openRemarketingNotification() {
        Reporter.log("open remarketing Notification Page Test Started",true);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.REMARKETING.toString());
        String campIntegPageHeaderText = integrate.getHeaderText();
        Assert.assertTrue(campIntegPageHeaderText.contains(SETTINGS_AD_NETWORKS), "CampaignIntegration Page successfully launched");
        Reporter.log("open re-marketing Notification Page Test Finished",true);
    }

    @Test(description = "verify user is able to create Sms provider under integration page", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void createVerifyAndSendTestSmsProvider(){
            Reporter.log("create Sms provider Test Started", true);
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.SMS.toString());
            smsProviderName = Data.randomAlphaNumeric(TEST_AUTOMATION_SMS, 3);
            Reporter.log("Sms provider name : " + smsProviderName, true);
            integrate.openProvider();

            List<String> expectedValues = new ArrayList<String>();
            expectedValues.add(smsProviderName);
            expectedValues.add(getValue("MSG91_AUTHKEY"));
            expectedValues.add(getValue("MSG91_ROUTE"));
            expectedValues.add(getValue("MSG91_SENDER"));
            Collections.sort(expectedValues);

            Reporter.log("selecting Sms provider as 'MSG91' " + smsProviderName, true);
            integrate.selectMessageProvider("MSG91");

            Reporter.log("filling all the details in the form", true);
            integrate.fillNickName(MSG91, smsProviderName);
            integrate.fillAuthKey(MSG91, getValue("MSG91_AUTHKEY"));
            integrate.fillRoute(MSG91, getValue("MSG91_ROUTE"));
            integrate.fillSender(MSG91, getValue("MSG91_SENDER"));

            integrate.sendTestSMSClick(MSG91);
            integrate.setUpMobileNoAndMsg(getValue("MSG91_MOBILENO"));
            SeleniumUtils.pause(2);
            Assert.assertTrue(integrate.verifySentTestResponse(), "test message not sent successfully");
            SeleniumUtils.waitForElementToClickable(driver, integrate.closeSendTestSmsModal, 10);
            SeleniumUtils.performClick(driver, integrate.closeSendTestSmsModal);

            integrate.saveSmsSetting();
            integrate.waitForTableToLoad();
            integrate.verifySettingSaveResponse();

            Reporter.log("verifying the created provider is reflected on the sms listing page", true);
            SeleniumUtils.pause(2);
            List<String> cellValue = integrate.getDataFromRightTable("//*[@class='CT-table__right']", 1, driver);
            Assert.assertTrue(cellValue.contains(smsProviderName), "newly created MSG91 was not present on listing page");

            List<String> actualValueList = integrate.verifySmsProviderDetails(MSG91, smsProviderName);
            Assert.assertEquals(expectedValues, actualValueList, "values for sms provider was not saved properly");
            Reporter.log("create sms provider Test Ended", true);

    }

    @Test(description = "verify user is able to edit Sms provider under integration page", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL},dependsOnMethods = {"createVerifyAndSendTestSmsProvider"})
    public void editMSG91SmsProvider() {
        try {
            Reporter.log("edit Sms provider Test Started", true);
            Mocha.forceNavigate=true;
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.SMS.toString());
            SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='" + smsProviderName + "']")));

            integrate.fillSender(MSG91, getValue("MSG91_SENDER") + "edited");
            integrate.saveSmsSetting();
            integrate.waitForTableToLoad();
            integrate.verifySettingSaveResponse();

            SeleniumUtils.waitForElementToClickable(driver, driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='" + smsProviderName + "']")), 10);
            SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='" + smsProviderName + "']")));
            SeleniumUtils.waitForElementToLoad(driver, driver.findElement(By.xpath("//*[@id='" + MSG91 + "']//input[@name='senderId']")));
            Assert.assertEquals(getValue("MSG91_SENDER") + "edited", driver.findElement(By.xpath("//*[@id='" + MSG91 + "']//input[@name='senderId']")).getAttribute("value"));
            Reporter.log("edit Sms provider Test Ended", true);
        }finally {
            Mocha.forceNavigate=false;
        }
    }

    @Test(description = "verify user is able to create whatsapp provider under integration page", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void createAndVerifyWhatsappProvider(){
        Reporter.log("create Whatsapp provider Test Started", true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WHATSAPP.toString());

        if(integrate.whatsappKeyInput.getAttribute("value").length()==0) {
            List<String> expectedValues = new ArrayList<String>();
            expectedValues.add(getValue("WHATSAPP_KEY"));
            expectedValues.add(getValue("WHATSAPP_SECRETKEY"));
            expectedValues.add(getValue("WHATSAPP_MOBILENO"));
            expectedValues.add(getValue("WHATSAPP_APPID"));
            expectedValues.add(getValue("WHATSAPP_FACEBOOKURL"));
            expectedValues.add(getValue("WHATSAPP_PRIVATEKEY"));
            Collections.sort(expectedValues);

            Reporter.log("filling all the details in the form", true);
            integrate.fillWhatsappKey(getValue("WHATSAPP_KEY"));
            integrate.fillWhatsappSecretKey(getValue("WHATSAPP_SECRETKEY"));
            integrate.fillWhatsappName(whatsappProviderName);
            integrate.fillWhatsappMobileNumber(getValue("WHATSAPP_MOBILENO"));
            integrate.fillWhatsappAppId(getValue("WHATSAPP_APPID"));
            integrate.fillWhatsappFacebookUrl(getValue("WHATSAPP_FACEBOOKURL"));
            integrate.fillWhatsappPrivateKey(getValue("WHATSAPP_PRIVATEKEY"));
            integrate.sendAutoRepliesForWhatsapp(true, getValue("WHATSAPP_AUTOREPLYNO"));
            integrate.saveWhatsappSetting();
            integrate.verifySettingSaveResponse();
            List<String> actualValueList=integrate.verifyWhatsappProviderDetails();
            Assert.assertEquals(expectedValues,actualValueList,"values for Whatsapp provider was not saved properly");
            Reporter.log("create Whatsapp provider Test Ended",true);
        }
        integrate.saveWhatsappSetting();
        integrate.verifySettingSaveResponse();
    }

    @Test(description = "verify user is able to edit whatsapp provider under integration page", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL},dependsOnMethods = {"createAndVerifyWhatsappProvider"})
    public void editWhatsappProviderWithValidAndInvalidValues(){
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WHATSAPP.toString());
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.className("js-edit-what")),10);
        SeleniumUtils.performClick(driver,driver.findElement(By.className("js-edit-what")));

        integrate.fillWhatsappKey(getValue("WHATSAPP_KEY"));
        integrate.fillWhatsappSecretKey(getValue("WHATSAPP_SECRETKEY"));
        integrate.fillWhatsappName(whatsappProviderName+"edited");
        integrate.fillWhatsappFacebookUrl(getValue("WHATSAPP_FACEBOOKURL"));
        integrate.saveWhatsappSetting();
        integrate.verifySettingSaveResponse();

        integrate.fillWhatsappMobileNumber(getValue("WHATSAPP_MOBILENO")+"7");
        integrate.saveWhatsappSetting();
        SeleniumUtils.waitForElementToLoad(driver,sweetAlert.sweetAlertError);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Error message was not present");

    }

    @Test(description = "verify user is able to create webpush provider under integration page with GCM key only and with combination of GCM and VAPID spec", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void createAndVerifyWebPushProviderWithGcmApiKeyAndVapid(){
        Reporter.log("create webpush provider Test Started", true);

        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBPUSH.toString());

        Reporter.log("Saving webpush provider with Enabled Status", true);
        integrate.changeWebPushToggleStatus(true);
        integrate.fillGcmApiKey(getValue("WEBPUSH_GCM_APIKEY"));
        SeleniumUtils.performClick(driver,integrate.saveBtn);
        integrate.verifySettingSaveResponse();
        SeleniumUtils.waitForElementToLoad(driver,integrate.gcmApiKeyInput);
        Assert.assertEquals(getValue("WEBPUSH_GCM_APIKEY"),integrate.gcmApiKeyInput.getAttribute("value"),"unexpected value was present for gcm api key after saving");

        //with VAPID AND GCM API KEY
        Reporter.log("Saving webpush provider with Disable Status", true);
        integrate.changeWebPushToggleStatus(false);
        if(driver.findElements(By.xpath("//div[@class='hide js-vapidDiv' and @style='display: block;']")).size()==0){
            //clicking on use vapid checkbox
            SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//label[@for='useVapid']/span")));
        }
        integrate.fillFcmPublicKey(getValue("WEBPUSH_FCM_PUBLICKEY"));
        integrate.fillFcmPrivateKey(getValue("WEBPUSH_FCM_PRIVATEKEY"));
        SeleniumUtils.performClick(driver,integrate.saveBtn);
        integrate.verifySettingSaveResponse();
        SeleniumUtils.waitForElementToLoad(driver,integrate.gcmApiKeyInput);
        Assert.assertEquals(getValue("WEBPUSH_GCM_APIKEY"),integrate.gcmApiKeyInput.getAttribute("value"),"unexpected value was present for gcm api key after saving");
        SeleniumUtils.waitForElementToLoad(driver,integrate.fcmPublicKeyInput);
        Assert.assertEquals(getValue("WEBPUSH_FCM_PUBLICKEY"),integrate.fcmPublicKeyInput.getAttribute("value"),"unexpected value was present for fcm public api key after saving");
        SeleniumUtils.waitForElementToLoad(driver,integrate.fcmPrivateKeyInput);
        Assert.assertEquals(getValue("WEBPUSH_FCM_PRIVATEKEY"),integrate.fcmPrivateKeyInput.getAttribute("value"),"unexpected value was present for gcm api key after saving");
        Reporter.log("create webpush provider Test Ended", true);

    }

    @Test(description = "verify user should not be able to create webpush provider under integration page with wrong GCM key", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void verifyWebPushCannotBeCreatedWithWrongData(){
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBPUSH.toString());
        integrate.fillGcmApiKey(getValue("WEBPUSH_GCM_APIKEY")+"u");
        SeleniumUtils.performClick(driver,integrate.saveBtn);
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Error message was not present");
    }

    @Test(description = "verify user be able to create webhook under integration page ", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void createAndVerfiyWebhook(){
            Reporter.log("create webhook Test Started", true);
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBHOOKS.toString());
            webhookName = Data.randomAlphaNumeric(TEST_AUTOMATION_WEBHOOK, 3);

            RestAssured.baseURI = "https://bin-api.pipedream.com/api/v2/http_endpoints";
            Response res = given().body(getValue("WEBHOOK_CREATION_API_BODY")).when().post();
            Assert.assertEquals("success", ApiUtility.getMemberValue(res, "message").getAsString(), "creating webhook url from Request bin failed");
            String webhookurl = "https://" + ApiUtility.getMemberValue(res, "data/api_key").getAsString() + ".x.pipedream.net/";
            Reporter.log("Webhook url used for creating webhook is " + webhookurl, true);

            SeleniumUtils.pause(5);
            integrate.createWebhook(webhookName, webhookurl);
            integrate.verifySettingSaveResponse();
            integrate.getWebhookTableData(webhookName);
            Reporter.log("create webhook Test Ended", true);
    }

    @Test(description = "verify user should not be able to create webhook under integration page with wrong webhook url", groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
    public void verifyWebhookCannotBeCreatedWithWrongData(){
        Reporter.log("webhook cannot be created with wrong data Test Started", true);

        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBHOOKS.toString());
        webhookName= Data.randomAlphaNumeric(TEST_AUTOMATION_WEBHOOK, 3);
        integrate.createWebhook(webhookName,"wrong url ");
        Assert.assertTrue(sweetAlert.getSweetAlertErrorSignal(), "Error message was not present");
        Reporter.log("webhook cannot be created with wrong data Test Ended", true);
    }






//    @Test(description = "send test email",groups = {TestCaseGroups.CAMPAIGNINTEGRATIONCRITICAL})
//    public void sendTestEmail(){
//        Reporter.log("open remarketing Notification Page Test Started",true);
//        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.EMAIL.toString());
//        String emailName = Data.randomAlphaNumeric(TEST_AUTOMATION, 3);
//        integrate.fillEmailForm("SendGrid");
//        integrate.setUpEmailTitleAndMsg();
//        SeleniumUtils.pause(3);
//        Assert.assertTrue(integrate.verifyEmailSentResponse(),"email not sent successfully");
//        SeleniumUtils.waitForElementToClickable(driver,integrate.closeSendTestModal,10);
//        SeleniumUtils.performClick(driver,integrate.closeSendTestModal);
//        Reporter.log("open remarketing Notification Page Test Ended",true);
//    }


//    @Test(description = "verify Webhooks Notification page of under integration", groups = {"Regression", "All"})
//    public void openWebhooksNotification() {
//        logger.info("open Webhooks Notification Page Test Started");
//        test = ExtentTestManager.startTest("openWebhooksNotificationPage", "Verify Webhooks Notification page open");
//        parentTest.appendChild(test);
//        Mocha mocha = new Mocha();
//        mocha.openLeftNavMenu(false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.WEBHOOKS.toString());
//        CampaignIntegration integrate = new CampaignIntegration(driver);
//        String campIntegPageHeaderText = integrate.getHeaderText();
//        Assert.assertTrue(campIntegPageHeaderText.contains("Settings\n" + "Webhooks\n"), "CampaignIntegration Page successfully launched");
//        integrate.createNewWebhook();
//        SeleniumUtils.waitForPageLoaded(driver);
//        String webhookDynamicName = Data.randomAlphaNumeric("Test-Automation", 4);
//        integrate.newWebhookform(webhookDynamicName);
//        List cellValue = integrate.getDataFromSpecificCell(0);
//        System.out.println(cellValue);
//        System.out.println("********"+webhookDynamicName);
//        Assert.assertTrue(cellValue.contains(webhookDynamicName), "New default web-hook is created");
//        test.log(LogStatus.PASS, PASSED);
//        ExtentTestManager.endTest();
//        logger.info("open AppUninstalls Page Test Finished");
//
//    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        Reporter.log("inside after class of campaign Integration ",true);
        driver.close();
        driver.quit();
    }


}
