package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.clevertap.utils.DashboardEnums.EmailFormData;

public class CampaignIntegration {
    private static final String HTTP_REQUESTBIN_FULLCONTACT_COM_12_QXUJY_1 = "http://requestbin.fullcontact.com/ydabuqyd";
    private static final String ID = "//*[@id='";
    private static final String SENDGRID = "sendgrid";
    private static final String MSG91 = "msg91";
    private static final String NEXMO = "nexmo";
    private static final String TWILIO = "twilio";
    private static final String EXOTEL = "exotel";
    private static final String NETCORE = "netcore";
    private static final String OTHER = "other";
    private static final String NEXMOCT = "nexmoct";
    private static final String KENSICO = "kensico";;
    private static final String MANDRILL="mandrill";
    private static final String AMAZONSES="amazon ses";
    private static final String POSTMARK="postmark";
    private static final String SMTP="smtp";

    private SweetAlert swal;
    private WebDriver driver;
    @FindBy(className = "ct-breadcrumb")
    private WebElement pageTitle;
    @FindBy(xpath = "//*[contains(@class,'js-apnsPushModeTokenBasedConfig')]//input[@type='submit']")
    private WebElement saveButtonforIos;
    @FindBy(xpath = "//*[contains(@class,'act AndroidClass listCont')]//input[@type='submit']")
    private WebElement saveButtonforAndroid;
    @FindBy(xpath = "//*[contains(@class,'WindowsClass')]//input[@type='submit']")
    private WebElement savebuttonforWindows;
    @FindBy(id = "select_email_provider_chzn")
    private WebElement emailProviderDropdown;
    @FindBy(id = "esp_hide")
    private WebElement provider;
    @FindBy(xpath = "//*[@id='saveEmailSettings']")
    private WebElement saveEmailSettings;
    @CacheLookup
    @FindBy(xpath = "//*[contains(@class,'Switch_slider-lg')]")
    private WebElement webPushSwitchBtn;
    @FindBy(id = "addNewAppBtn")
    private WebElement addNewWebhook;
    @FindBy(xpath = "//a[contains(text(),'documentation.')]")
    private WebElement linkValidate;
    @FindBy(id = "webhookName")
    private WebElement webhookName;
    @FindBy(id = "webhookUrl")
    private WebElement webhookUrl;
    @FindBy(xpath = "//input[@value='Save']")
    private WebElement saveWebhook;
    @FindBy(xpath = "//table[contains(@class,'gtwTable')]")
    private WebElement tableData;
    @FindBy(xpath = "(//*[@class='btn-close'])[1]")
    private WebElement closeForm;
    @FindBy(className = "Switch_slider")
    private WebElement switchSlider;
    @FindBy(xpath = "//*[contains(@class,'Switch')]")
    private WebElement slider;

    @FindBy(className = "android-o-settings-check")
    private WebElement androidOChannel;
    @FindBy(id = "android-o-settings-check")
    private WebElement checkBoxAndroidO;
    @FindBy(id = "currentAndroidOChannel")
    private WebElement textBoxAndroidOChannel;
    @FindBy(id = "addAndroidOChannel")
    private WebElement addAndroidOChannel;
    @FindBy(xpath = "//div[contains(@class, 'androidOcheckWrapper')]//input[@type='submit']")
    private WebElement saveAndroidOChannel;
    @FindBy(xpath = "//div[@class='addedAOChannels']/div/span[1]")
    private List<WebElement> accountAndroidOChannels;
    @FindBy(xpath = "//div[@class='addedAOChannels']//span[@class='delAOChannel']")
    private WebElement deletAndroidOChannels;
    @FindBy(id = "test-email-message-title")
    private WebElement sendTestEmailPopUpTitle;
    @FindBy(id = "test-push-email-message-body")
    private WebElement sendTestEmailPopUpMsg;
    @FindBy(id = "test-email-ids")
    private WebElement sendToEmailIds;
    @FindBy(id = "PushEmailTargetTestSendButton")
    private WebElement sendTestBtn;
    @FindBy(id = "pushTestSuccess")
    private WebElement msgSentResp;
    @FindBy(name ="gcmSenderId")
    public static WebElement GCMSenderIdInput;
    @FindBy(name ="gcmServerKey")
    public static WebElement GCMServerApiKeyInput;
    @FindBy(name = "apnsTeamID")
    public static WebElement teamId;
    @FindBy(name="apnsBundleID")
    public static WebElement appBundleId;
    @FindBy(name = "apnsPushMode")
    public static WebElement selectPushMode;
    @FindBy(xpath = "//div[@id='wzrkPushEmailTestDialog']//button[@class='close']")
    public static WebElement closeSendTestModal;
    @FindBy(id = "select_sms_provider_chzn")
    private static WebElement messageProviderDropdown;
    @FindBy(id = "country-code")
    private static WebElement countryCodeInput;
    @FindBy(id = "test-numbers")
    private static WebElement mobileNoInput;
    @FindBy(id = "test-sms-message-body")
    private static WebElement testSmsMessageInput;
    @FindBy(id = "SmsTargetTestSendButton")
    private WebElement sendTestSmsBtn;
    @FindBy(xpath = "//div[@id='wzrkSmsTestDialog']//button[@class='close']")
    public static WebElement closeSendTestSmsModal;
    @FindBy(xpath = "//*[@id='saveSmsSettings']")
    private WebElement saveSmsSettings;
    @FindBy(xpath = "//label[@for='autoMessage']/span")
    private WebElement sendAutoRepliesCheckbox;
    @FindBy(xpath = "//*[@id='mandrill']//input[@name='customMessage']")
    private WebElement sendAutoRepliesInput;
    @FindBy(xpath = "//*[@id='mandrill']//input[@name='privateKey']")
    private WebElement whatsappPrivateKeyInput;
    @FindBy(xpath = "//*[@id='mandrill']//input[@name='facebookUrl']")
    private WebElement whatsappFacebookUrlInput;
    @FindBy(xpath = "//*[@id='mandrill']//input[@name='appId']")
    private WebElement whatsappAppIdInput;
    @FindBy(xpath = "//*[@id='mandrill']//input[@name='mobileNumber']")
    private WebElement whatsappMobileNoInput;
    @FindBy(xpath = "//*[@id='mandrill']//input[@name='name']")
    private WebElement whatsappNameInput;
    @FindBy(xpath = "//*[@id='mandrill']//input[@name='secret']")
    private WebElement whatsappSecretKeyInput;
    @FindBy(xpath = "//*[@id='mandrill']//input[@name='key']")
    public WebElement whatsappKeyInput;
    @FindBy(id = "saveWhatsappSettings")
    private WebElement saveWhatsappSettings;
    @FindBy(id = "serverApiKey")
    public WebElement gcmApiKeyInput;
    @FindBy(xpath = "//input[@value='Save']")
    public WebElement saveBtn;
    @FindBy(id = "fcmPublicKey")
    public WebElement fcmPublicKeyInput;
    @FindBy(id = "fcmPrivateKey")
    public WebElement fcmPrivateKeyInput;
    @FindBy(id = "addNewAppBtn")
    public WebElement createWebhookBtn;
    @FindBy(id = "webhookName")
    public WebElement webhookNameInput;
    @FindBy(id = "webhookUrl")
    public WebElement webhookUrlInput;
    @FindBy(xpath = "//table[@class='table table-bordered gtwTable']//th[@data-column='3']")
    public WebElement webhookCreatedOnCol;

    @FindBy(xpath = "//form[@id='fcmForm']//input[@type='submit']")
    public WebElement saveFCMCredentialsSettings;

    @FindBy(xpath = "//form[@id='xiaomiForm']//input[@type='submit']")
    public  WebElement saveXiaomiCredentialsSettings;

    @FindBy(xpath = "//form[@id='baiduForm']//input[@type='submit']")
    public  WebElement saveBaiduCredentialsSettings;

    @FindBy(xpath = "//span[text()='Baidu credentials']")
    public WebElement baiduCredentials;

    @FindBy(xpath = "//span[text()='Xiaomi credentials']")
    public WebElement xiaomiCredentials;

    @FindBy(xpath = "//form[@id='xiaomiForm']//input[@name='appSecret']")
    public WebElement xiaomiAppSecret;

    @FindBy(xpath = "//form[@id='xiaomiForm']//input[@name='appPackage']")
    public WebElement xiaomiAppPackage;

    @FindBy(xpath = "//form[@id='baiduForm']//input[@name='secretKey']")
    public WebElement baiduSecretKey;

    @FindBy(xpath = "//form[@id='baiduForm']//input[@name='apiKey']")
    public WebElement baiduApiKey;

    public void fillEmailForm(String serviceProvider) {
        String emailProvider = EmailFormData.valueOf(serviceProvider.trim().toLowerCase()).getServiceProvider().trim().toLowerCase();
        switch (emailProvider) {
            case SENDGRID:
            case KENSICO:
            case NEXMO:

                /*click on provider button to open the email form*/
                SeleniumUtils.performClick(driver, provider);

                /*Select email service provider from dropdown*/
                emailProviderDropdown.click();
                driver.findElement(By.xpath("//*[@class='chzn-drop']//li[text()='" + serviceProvider + "']")).click();

                /*enter nick name*/
                if (!SeleniumUtils.isNullOrEmpty(EmailFormData.valueOf(emailProvider).getNickName())) {
                    WebElement nickNameInput = driver.findElement(By.xpath("//*[@id='" + emailProvider + "']//input[@name='nickname']"));
                    SeleniumUtils.enterInputText(driver, nickNameInput,EmailFormData.valueOf(emailProvider).getNickName());
                }

                /*enter host detail*/
                if (!SeleniumUtils.isNullOrEmpty(EmailFormData.valueOf(emailProvider).getHost())) {
                    WebElement hostNameInput = driver.findElement(By.xpath("//*[@id='" + emailProvider + "']//input[@name='host']"));
                    SeleniumUtils.enterInputText(driver, hostNameInput, EmailFormData.valueOf(emailProvider).getHost());
                }

                /*enter user name*/
                if (!SeleniumUtils.isNullOrEmpty(EmailFormData.valueOf(emailProvider).getUserName())) {
                    WebElement userName = driver.findElement(By.xpath("//*[@id='" + emailProvider + "']//input[@name='username']"));
                    SeleniumUtils.enterInputText(driver, userName, EmailFormData.valueOf(emailProvider).getUserName());
                }

                /*enter password*/
                if (!SeleniumUtils.isNullOrEmpty(EmailFormData.valueOf(emailProvider).getPwd())) {
                    WebElement password = driver.findElement(By.xpath("//*[@id='" + emailProvider + "']//input[@name='password']"));
                    SeleniumUtils.enterInputText(driver, password, EmailFormData.valueOf(emailProvider).getPwd());
                }

                /*enter from address*/
                if (!SeleniumUtils.isNullOrEmpty(EmailFormData.valueOf(emailProvider).getFromAddress())) {
                    WebElement fromAddress = driver.findElement(By.xpath("//*[@id='" + emailProvider + "']//input[@name='fromAddress']"));
                    SeleniumUtils.enterInputText(driver, fromAddress, EmailFormData.valueOf(emailProvider).getFromAddress());
                }

                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@id='sendgrid']//span[text()='Send a test email']")));

                SeleniumUtils.pause(2);
                break;
            default:
                break;
        }

    }


    public void waitForTableToLoad() {
        SeleniumUtils.waitForElementToLoad(driver, provider);
    }


    public String getSwitchSliderInnerText() {
        return SeleniumUtils.getElementText(driver, switchSlider);
    }

    public String getCssValue() {
        return slider.getCssValue("background-color");
    }

    public WebElement operatingSystems(String itemName) {
        String finalXpath = "//*[@data-cg='" + itemName + "']";
        return driver.findElement(By.xpath(finalXpath));


    }

    public void getOsForCampInt(String intComponent) {
        WebElement osComponent = this.operatingSystems(intComponent);
        SeleniumUtils.waitForElementToClickable(driver,osComponent,10);
        SeleniumUtils.performClick(driver, osComponent);
    }

    public void selectEmailProviderName(String emailProviderName) {
        Actions actions=new Actions(driver);
        actions.moveByOffset(emailProviderDropdown.getRect().getX(),emailProviderDropdown.getRect().getY()).click().build().perform();
        SeleniumUtils.pause(2);
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[@class='chzn-drop']//li[text()='" + emailProviderName + "']")),10);
        driver.findElement(By.xpath("//*[@class='chzn-drop']//li[text()='" + emailProviderName + "']")).click();
//        SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//*[@class='chzn-drop']//li[text()='" + emailProviderName + "']")));
    }

    public void fillNickName(String providerName,String nickName) {
       SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='nickname']")));
       SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='nickname']")),nickName);
    }

    public void fillHostName(String providerName,String host) {
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='host']")));
        SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='host']")),host);
    }

    public void fillUserName(String providerName,String userName) {
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='username']")));
        SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='username']")),userName);
    }

    public void fillPassword(String providerName,String password) {
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='password']")));
        SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='password']")),password);
    }

    public void fillFromAddress(String providerName,String fromAddress) {
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='fromAddress']")));
        SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='fromAddress']")),fromAddress);
    }

    public void fillAuthKey(String providerName,String authKey) {
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='authKey']")));
        SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='authKey']")),authKey);
    }

    public void fillRoute(String providerName,String route) {
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='route']")));
        SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='route']")),route);
    }

    public void fillSender(String providerName,String sender) {
        SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='senderId']")));
        SeleniumUtils.enterInputText(driver,driver.findElement(By.xpath("//*[@id='"+providerName+"']//input[@name='senderId']")),sender);
    }

    public void fillWhatsappKey(String key) {
        SeleniumUtils.waitForElementToLoad(driver,whatsappKeyInput);
        SeleniumUtils.enterInputText(driver,whatsappKeyInput,key);
    }

    public void fillWhatsappSecretKey(String secretKey) {
        SeleniumUtils.waitForElementToLoad(driver,whatsappSecretKeyInput);
        SeleniumUtils.enterInputText(driver,whatsappSecretKeyInput,secretKey);
    }

    public void fillWhatsappName(String name) {
        SeleniumUtils.waitForElementToLoad(driver,whatsappNameInput);
        whatsappNameInput.clear();
        SeleniumUtils.enterInputText(driver,whatsappNameInput,name);
    }

    public void fillWhatsappMobileNumber(String mobileNumber) {
        SeleniumUtils.waitForElementToLoad(driver,whatsappMobileNoInput);
        SeleniumUtils.enterInputText(driver,whatsappMobileNoInput,mobileNumber);
    }

    public void fillWhatsappAppId(String appId) {
        SeleniumUtils.waitForElementToLoad(driver,whatsappAppIdInput);
        SeleniumUtils.enterInputText(driver,whatsappAppIdInput,appId);
    }

    public void fillWhatsappFacebookUrl(String facebookUrl) {
        SeleniumUtils.waitForElementToLoad(driver,whatsappFacebookUrlInput);
        SeleniumUtils.enterInputText(driver,whatsappFacebookUrlInput,facebookUrl);
    }

    public void fillWhatsappPrivateKey(String privateKey) {
        SeleniumUtils.waitForElementToLoad(driver,whatsappPrivateKeyInput);
        SeleniumUtils.enterInputText(driver,whatsappPrivateKeyInput,privateKey);
    }

    public void fillGcmApiKey(String gcmApiKey) {
        SeleniumUtils.waitForElementToClickable(driver,gcmApiKeyInput,10);
        gcmApiKeyInput.clear();
        SeleniumUtils.enterInputText(driver,gcmApiKeyInput,gcmApiKey);
    }

    public void fillFcmPublicKey(String fcmPublicKey) {
        SeleniumUtils.waitForElementToLoad(driver,fcmPublicKeyInput);
        SeleniumUtils.enterInputText(driver,fcmPublicKeyInput,fcmPublicKey);
    }

    public void fillFcmPrivateKey(String fcmPrivateKey) {
        SeleniumUtils.waitForElementToLoad(driver,fcmPrivateKeyInput);
        SeleniumUtils.enterInputText(driver,fcmPrivateKeyInput,fcmPrivateKey);
    }

    public void sendAutoRepliesForWhatsapp(boolean send,String mobileNo){
        if(send){
            SeleniumUtils.pause(2);
            if(driver.findElements(By.xpath("//div[@id='customMessageDiv' and @style='display: block;']")).size()==0){
                SeleniumUtils.performClick(driver,sendAutoRepliesCheckbox);
                SeleniumUtils.enterInputText(driver,sendAutoRepliesInput,mobileNo);
            }
        }else{
            if(driver.findElements(By.xpath("//div[@id='customMessageDiv' and @style='display: block;']")).size()>0){
                SeleniumUtils.performClick(driver,sendAutoRepliesCheckbox);
            }
        }
    }


    public void saveEmail() {
        SeleniumUtils.performClick(driver, saveEmailSettings);
    }

    public void verifySettingSaveResponse() {
        SweetAlert sweetAlert = new SweetAlert(driver);
        SeleniumUtils.waitForElementToClickable(driver,sweetAlert.sweetAlertConfirmOK,10);
        Assert.assertTrue(sweetAlert.getSweetAlertSuccessSignal(),"Something went wrong while creating provider");
    }

    public void setUpEmailTitleAndMsg(String emailId){
        SeleniumUtils.waitForElementToClickable(driver,sendTestEmailPopUpTitle,10);
        sendTestEmailPopUpTitle.clear();
        SeleniumUtils.enterInputText(driver,sendTestEmailPopUpTitle,"Hello Automation Title");
        sendTestEmailPopUpMsg.clear();
        SeleniumUtils.enterInputText(driver,sendTestEmailPopUpMsg,"Hello Automation message");
        sendToEmailIds.clear();
        SeleniumUtils.enterInputText(driver,sendToEmailIds,emailId);
        SeleniumUtils.waitForElementToClickable(driver,sendTestBtn,10);
        SeleniumUtils.performClick(driver,sendTestBtn);
    }


    public boolean verifySentTestResponse(){
        return msgSentResp.getText().contains("Message Sent");
    }


    public String getHeaderText() {
        return pageTitle.getText();
    }

    public void saveButtonForIos() {
//        SeleniumUtils.scrollDownLittle(driver);
        SeleniumUtils.waitForElementToClickable(driver,saveButtonforIos,10);
        SeleniumUtils.performClick(driver, saveButtonforIos);

    }

    public void saveButtonForAndroid() {
        SeleniumUtils.waitForElementToClickable(driver,saveButtonforAndroid,10);
        SeleniumUtils.performClick(driver, saveButtonforAndroid);

    }

    public void saveButtonForWindows() {
        SeleniumUtils.performClick(driver, savebuttonforWindows);
    }


    public void openProvider() {
        SeleniumUtils.performClick(driver, provider);


    }

    public String getSwitchBkgColor() {
        return webPushSwitchBtn.getCssValue("color");
    }

    public boolean getValidLink() {

        boolean status = false;

        SeleniumUtils.scrollDown(driver);
        SeleniumUtils.performClick(driver, linkValidate);

        Set<String> windowHandles = driver.getWindowHandles();
        List<String> windowHandlesList = new ArrayList<>(windowHandles);
        String originalTab = windowHandlesList.get(0);
        String newTab = windowHandlesList.get(1);
        driver.switchTo().window(newTab);

        if (driver.switchTo().window(newTab).getTitle().contains("Web")) {
            status = true;
        }
        driver.switchTo().window(newTab).close();
        driver.switchTo().window(originalTab);
        return status;

    }

    public void createNewWebhook() {
        SeleniumUtils.performClick(driver, addNewWebhook);

    }

    public void newWebhookform(String newWebhookName) {
        webhookName.sendKeys(newWebhookName);
        webhookUrl.sendKeys(HTTP_REQUESTBIN_FULLCONTACT_COM_12_QXUJY_1);
        SeleniumUtils.performClick(driver, saveWebhook);
        SweetAlert sweetAlert = new SweetAlert(driver);
        sweetAlert.sweetAlertConfirmOK();
        SeleniumUtils.performClick(driver, closeForm);

    }

    public List getDataFromSpecificCell(int colNum) {
        List cellValue = new ArrayList();
        List<WebElement> rows = tableData.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            try {

                List<WebElement> cells = row.findElements(By.xpath("./*"));
                SeleniumUtils.elementHighlighter(driver, cells.get(colNum));
                cellValue.add(cells.get(colNum).getText());
            } catch (IndexOutOfBoundsException ex) {
                //do nothing
            }
        }
        return cellValue;
    }


    public List<String> getDataFromRightTable(String rightTableXpath, int colCOunt, WebDriver driver) {
        List<String> columnDataList = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.xpath(rightTableXpath + "/div"));
        int numberOfRows = rows.size();
        for (int i = 2; i < numberOfRows; i++) {
            WebElement column = rows.get(i).findElement(By.xpath(rightTableXpath + "/div" + "[" + i + "]/div[" + colCOunt + "]"));
            SeleniumUtils.elementHighlighter(driver, column);
            columnDataList.add(column.getAttribute("innerText"));
        }

        return columnDataList;
    }


    public CampaignIntegration(WebDriver previousBrowserDriver) {

        driver = previousBrowserDriver;

        PageFactory.initElements(previousBrowserDriver, this);
        swal = new SweetAlert(driver);

    }

    public List<String> validateAndroidChannel(List<String> channels) {
        SeleniumUtils.scrollDown(driver, "250");
        for (String channel : channels) {
            SeleniumUtils.enterInputText(driver, textBoxAndroidOChannel, channel);
            SeleniumUtils.performClick(driver, addAndroidOChannel);
        }
        SeleniumUtils.waitForElementToClickable(driver,saveAndroidOChannel,10);
        SeleniumUtils.performClick(driver, saveAndroidOChannel);
        SeleniumUtils.waitForElementToClickable(driver,swal.sweetAlertConfirmOK,10);
        swal.getSweetAlertSuccessSignal();

        List<String> savedChannelNames = new ArrayList<>();
        for (WebElement channel : accountAndroidOChannels) {
            savedChannelNames.add(channel.getText().trim());
        }

        return savedChannelNames;
    }

    public List<String> verifyEmailProviderDetails(String providerType,String providerName){
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='"+providerName+"']")),10);
        SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='"+providerName+"']")));
        List<String> valueList=new ArrayList<String>();

        switch (providerType.trim().toLowerCase()){

            case SENDGRID:
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='nickname']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='nickname']")).getAttribute("value"));
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='host']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='host']")).getAttribute("value"));
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='port']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='port']")).getAttribute("value"));
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='username']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='username']")).getAttribute("value"));
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='password']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='password']")).getAttribute("value"));
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='fromAddress']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='fromAddress']")).getAttribute("value"));

                break;

            case KENSICO:

                break;

            case NEXMO:

                break;

            case MANDRILL:

                break;

            case AMAZONSES:

                break;

            case POSTMARK:

                break;

            case SMTP:

                break;


        }

        Collections.sort(valueList);
        return valueList;

    }

    public void sendTestEmailClick(String providerType){
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//span[text()='Send a test email']")),10);
        SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//span[text()='Send a test email']")));
    }

    public void selectMessageProvider(String emailProviderName) {
        Actions actions=new Actions(driver);
//        actions.moveByOffset(messageProviderDropdown.getRect().getX(),messageProviderDropdown.getRect().getY()).click().build().perform();
        SeleniumUtils.waitForElementToClickable(driver,messageProviderDropdown,10);
        messageProviderDropdown.click();
        SeleniumUtils.pause(2);
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[@class='chzn-drop']//li[text()='" + emailProviderName + "']")),10);
        driver.findElement(By.xpath("//*[@class='chzn-drop']//li[text()='" + emailProviderName + "']")).click();
    }

    public void sendTestSMSClick(String providerType){
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//span[text()='Send a test SMS']")),10);
        SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//span[text()='Send a test SMS']")));
    }

    public void setUpMobileNoAndMsg(String mobileNo){
        SeleniumUtils.waitForElementToLoad(driver,countryCodeInput);
        SeleniumUtils.enterInputText(driver,countryCodeInput,"+91");
        SeleniumUtils.waitForElementToLoad(driver,mobileNoInput);
        SeleniumUtils.enterInputText(driver,mobileNoInput,mobileNo);
        SeleniumUtils.waitForElementToLoad(driver,testSmsMessageInput);
        SeleniumUtils.enterInputText(driver,testSmsMessageInput,"Automation Test Sms");
        SeleniumUtils.performClick(driver,sendTestSmsBtn);
    }

    public void saveSmsSetting() {
        SeleniumUtils.performClick(driver, saveSmsSettings);
    }

    public void saveWhatsappSetting() {
        SeleniumUtils.performClick(driver, saveWhatsappSettings);
    }

    public List<String> verifySmsProviderDetails(String providerType,String providerName){

        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='"+providerName+"']")),10);
        SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//div[@id='providers_table']//a[text()='"+providerName+"']")));
        List<String> valueList=new ArrayList<String>();

        switch (providerType.trim().toLowerCase()) {

            case MSG91:
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='nickname']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='nickname']")).getAttribute("value"));
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='authKey']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='authKey']")).getAttribute("value"));
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='route']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='route']")).getAttribute("value"));
                SeleniumUtils.waitForElementToLoad(driver,driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='senderId']")));
                valueList.add(driver.findElement(By.xpath("//*[@id='"+providerType+"']//input[@name='senderId']")).getAttribute("value"));

                break;

            case NEXMO:

                break;

            case TWILIO:

                break;

            case EXOTEL:

                break;

            case NETCORE:

                break;

            case OTHER:

                break;

            case NEXMOCT:

                break;

        }

        Collections.sort(valueList);
        return valueList;

    }

    public List<String> verifyWhatsappProviderDetails(){

        List<String> valueList=new ArrayList<String>();
        SeleniumUtils.waitForElementToLoad(driver,whatsappKeyInput);
        valueList.add(whatsappKeyInput.getAttribute("value"));
        SeleniumUtils.waitForElementToLoad(driver,whatsappSecretKeyInput);
        valueList.add(whatsappSecretKeyInput.getAttribute("value"));
//        SeleniumUtils.waitForElementToLoad(driver,whatsappNameInput);
//        valueList.add(whatsappNameInput.getAttribute("value"));
        SeleniumUtils.waitForElementToLoad(driver,whatsappMobileNoInput);
        valueList.add(whatsappMobileNoInput.getAttribute("value"));
        SeleniumUtils.waitForElementToLoad(driver,whatsappAppIdInput);
        valueList.add(whatsappAppIdInput.getAttribute("value"));
        SeleniumUtils.waitForElementToLoad(driver,whatsappFacebookUrlInput);
        valueList.add(whatsappFacebookUrlInput.getAttribute("value"));
//        SeleniumUtils.waitForElementToLoad(driver,sendAutoRepliesInput);
//        valueList.add(sendAutoRepliesInput.getAttribute("value"));
        SeleniumUtils.waitForElementToLoad(driver,whatsappPrivateKeyInput);
        valueList.add(whatsappPrivateKeyInput.getAttribute("value"));
        Collections.sort(valueList);

        return valueList;
    }

    public void changeWebPushToggleStatus(boolean status){

        if(status){
            if(driver.findElements(By.xpath("//div[text()='Disabled']")).size()>0){
                Reporter.log("Enabling Webpush toggle",true);
                SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//div[text()='Disabled']")));
            }
        }else{
            if(driver.findElements(By.xpath("//div[text()='Enabled']")).size()>0){
                Reporter.log("Disabling Webpush toggle",true);
                SeleniumUtils.performClick(driver,driver.findElement(By.xpath("//div[text()='Enabled']")));
            }
        }
    }

    public void createWebhook(String webhookName,String webhookUrl){
        SeleniumUtils.performClick(driver,createWebhookBtn);
        SeleniumUtils.waitForElementToLoad(driver,webhookNameInput);
        SeleniumUtils.enterInputText(driver,webhookNameInput,webhookName);
        SeleniumUtils.waitForElementToLoad(driver,webhookUrlInput);
        SeleniumUtils.enterInputText(driver,webhookUrlInput,webhookUrl);
        SeleniumUtils.performClick(driver,saveBtn);
        SeleniumUtils.pause(2);
    }

    public void getWebhookTableData(String webhookName){
        //first clicking on created on so that webhook can be sorted and in less iteration we can validate webhook
        SeleniumUtils.performClick(driver,webhookCreatedOnCol);

        List<String> webhookNames=new ArrayList<String>();
        List<WebElement> webElements=driver.findElements(By.xpath("//table[@class='table table-bordered gtwTable']/tbody/tr/td[1]"));
        boolean isPresent=false;

        for (WebElement webElement:webElements){
            if(webElement.getText().equalsIgnoreCase(webhookName)){
                isPresent=true;
                break;
            }
        }

        Assert.assertTrue(isPresent,"created webhook was not present on webhook listing page");
    }



}



