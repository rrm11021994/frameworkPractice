package com.clevertap.ui.campaign_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.campaigns_page.MobileInAppPage;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.io.File;


public class MobileInAppTest extends BaseTest{

    private static final String UPLOAD_IMAGE = "Upload image";
    private static final String PORTRAIT = "Portrait";
    private static final String BACKGROUND = "Background";
    private static final String LANDSCAPE = "Landscape";
    private static final String BOTH = "Both";
    private static final String CAMPAIGNS = "Campaigns";
    private static final String COVER = "Cover";
    private static final String IMAGE_ONLY_NOTIFICATION = "Image only notification";
    private static final String CONTENT = "Content";
    private static final String INTERSTITIAL = "Interstitial";
    private static final String WITH_CONTENT_NOTIFICATION = "With content notification";
    private static final String IMAGE = "Image";
    private static final String ENGAGE = "Engage";
    private static final String MESSAGE_TYPE = "Select message type";
    private static final String HALF_INTERSTITIAL = "Half Interstitial";
    private static final String PREVIEW = "Preview";
    private static final String SOME_RANDOM_TITLE = "Some Random Title";
    private static final String MESSAGE = "Message";
    private static final String SOME_RANDOM_MESSAGE = "Some Random Message";
    private static final String BUTTONS = "Buttons";
    private static final String SAMPLE_BUTTON = "Sample Button";
    private static final String TITLE = "Title";
    private static final String RANDOM_TEST = "Automation_Campaign";
    private Logger logger;
    private WebDriver driver;
    private static SoftAssert softAssert = new SoftAssert();


    @BeforeClass(alwaysRun=true)
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        logger = baseTest.configureLogger(getClass());
    }

    @Test(groups={"Regression","All"},description = "Check if user can reach to InApp Message formation page")
    public void verifyHeader(){
        logger.info(">>>verifyHeader");
        Mocha.openLeftNavMenu(driver,false,  NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        Assert.assertTrue(mobileInAppPage.getBreadcumbText().contains(ENGAGE) && mobileInAppPage.getBreadcumbText().contains(CAMPAIGNS));
        logger.info("<<<verifyHeader");
    }

    @Test(groups={"Regression","All"},description = "Check if tabs for Cover with Content Notification page work")
    public void verifyCoverWithContentNotificationTabs(){
        logger.info(">>>verifyCoverWithNotificationTabs");
        Mocha.openLeftNavMenu(driver,false,  NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        mobileInAppPage.goToCreateMsgPage();
        softAssert.assertEquals(mobileInAppPage.getSelectMessageTypeLabel(), MESSAGE_TYPE);
        logger.info(">>>reachCoverWithNotificationPage");
        mobileInAppPage.selectMessageFormat(COVER, WITH_CONTENT_NOTIFICATION) ;
        softAssert.assertEquals(mobileInAppPage.getContentHeader(),"Content");
        logger.info("<<<reachCoverWithNotificationPage");
        logger.info(">>>checkTabs");
        SeleniumUtils.scrollDown(driver,"500");
        mobileInAppPage.selectTile(BACKGROUND);
        mobileInAppPage.selectLayout(PORTRAIT);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/");
        mobileInAppPage.selectLayout(LANDSCAPE);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),LANDSCAPE+"/");
        mobileInAppPage.selectLayout(BOTH);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/"+LANDSCAPE+"/");
        logger.info("<<<checkTabs");
        try{
            mobileInAppPage.clickClose();
            Assert.assertTrue(mobileInAppPage.getBreadcumbText().contains(CAMPAIGNS));}
        catch (Exception e){
            //DO NOthing
        }
        softAssert.assertAll();
        logger.info("<<<verifyCoverWithNotificationTabs");
    }

    @Test(groups={"Regression","All"},description = "Check if tabs for Cover with Content Notification page work")
    public void verifyCoverWithImageOnlyNotificationTabs(){
        logger.info(">>>verifyCoverWithImageOnlyNotificationTabs");

        Mocha.openLeftNavMenu(driver,false,  NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        mobileInAppPage.goToCreateMsgPage();
        softAssert.assertEquals(mobileInAppPage.getSelectMessageTypeLabel(), MESSAGE_TYPE);
        logger.info(">>>reachCoverImageOnlyNotificationPage");
        mobileInAppPage.selectMessageFormat(COVER, IMAGE_ONLY_NOTIFICATION) ;
        softAssert.assertEquals(mobileInAppPage.getContentHeader(),CONTENT);
        logger.info("<<<reachCoverImageOnlyNotificationPage");
        logger.info(">>>checkTabs");
        mobileInAppPage.selectLayout(PORTRAIT);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/");
        mobileInAppPage.selectLayout(LANDSCAPE);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),LANDSCAPE+"/");
        mobileInAppPage.selectLayout(BOTH);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/"+LANDSCAPE+"/");
        logger.info("<<<checkTabs");
        try{
            mobileInAppPage.clickClose();
            Assert.assertTrue(mobileInAppPage.getBreadcumbText().contains(CAMPAIGNS));}
        catch (Exception e){
            //DO NOthing
        }
        softAssert.assertAll();
        logger.info("<<<verifyCoverWithNotificationTabs");
    }

    @Test(groups={"Regression","All"},description = "Check if tabs for Cover with Content Notification page work")
    public void verifyHalfInterstitialWithImageOnlyNotificationTabs(){
        logger.info(">>>verifyHalfInterstitialWithImageOnlyNotificationTabs");
        Mocha.openLeftNavMenu(driver,false,  NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        mobileInAppPage.goToCreateMsgPage();
        softAssert.assertEquals(mobileInAppPage.getSelectMessageTypeLabel(), MESSAGE_TYPE);
        logger.info(">>>reachHalfInterstitialWithImageOnlyNotificationTabs");
        mobileInAppPage.selectMessageFormat(HALF_INTERSTITIAL, IMAGE_ONLY_NOTIFICATION) ;
        softAssert.assertEquals(mobileInAppPage.getContentHeader(),CONTENT);
        logger.info("<<<reachHalfInterstitialWithImageOnlyNotificationTabs");
        logger.info(">>>checkTabs");
        mobileInAppPage.selectLayout(PORTRAIT);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/");
        mobileInAppPage.selectLayout(LANDSCAPE);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),LANDSCAPE+"/");
        mobileInAppPage.selectLayout(BOTH);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/"+LANDSCAPE+"/");
        logger.info("<<<checkTabs");
        try{
            mobileInAppPage.clickClose();
            Assert.assertTrue(mobileInAppPage.getBreadcumbText().contains(CAMPAIGNS));}
        catch (Exception e){
            //DO NOthing
        }
        softAssert.assertAll();
        logger.info("<<<verifyHalfInterstitialWithImageOnlyNotificationTabs");
    }

    @Test(groups={"Regression","All"},description = "Check if tabs for Cover with Content Notification page work")
    public void verifyHalfInterstitialWithContentNotificationTabs(){
        logger.info(">>>verifyHalfInterstitialWithContentNotificationTabs");
        Mocha.openLeftNavMenu(driver,false,  NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        mobileInAppPage.goToCreateMsgPage();
        softAssert.assertEquals(mobileInAppPage.getSelectMessageTypeLabel(), MESSAGE_TYPE);
        logger.info(">>>reachCoverImageOnlyNotificationPage");
        mobileInAppPage.selectMessageFormat(HALF_INTERSTITIAL, WITH_CONTENT_NOTIFICATION) ;
        softAssert.assertEquals(mobileInAppPage.getContentHeader(),CONTENT);
        logger.info("<<<reachCoverImageOnlyNotificationPage");
        logger.info(">>>checkTabs");
        SeleniumUtils.scrollDown(driver,"500");
        mobileInAppPage.selectTile(BACKGROUND);
        mobileInAppPage.selectLayout(PORTRAIT);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/");
        mobileInAppPage.selectLayout(LANDSCAPE);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),LANDSCAPE+"/");
        mobileInAppPage.selectLayout(BOTH);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/"+LANDSCAPE+"/");
        logger.info("<<<checkTabs");
        try{
            mobileInAppPage.clickClose();
            Assert.assertTrue(mobileInAppPage.getBreadcumbText().contains(CAMPAIGNS));}
        catch (Exception e){
            //DO NOthing
        }
        softAssert.assertAll();
        logger.info("<<<verifyHalfInterstitialWithContentNotificationTabs");
    }

    @Test(groups={"Regression","All"},description = "Check if tabs for Interstitial with Image only Notification page work")
    public void verifyInterstitialWithImageOnlyNotificationTabs(){
        logger.info(">>>verifyInterstitialWithImageOnlyNotificationTabs");
        Mocha.openLeftNavMenu(driver,false,  NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        mobileInAppPage.goToCreateMsgPage();
        softAssert.assertEquals(mobileInAppPage.getSelectMessageTypeLabel(), MESSAGE_TYPE);
        logger.info(">>>reachInterstitialWithImageOnlyNotificationTabs");
        mobileInAppPage.selectMessageFormat(INTERSTITIAL, IMAGE_ONLY_NOTIFICATION) ;
        softAssert.assertEquals(mobileInAppPage.getContentHeader(),IMAGE);
        logger.info("<<<reachInterstitialWithImageOnlyNotificationTabs");
        logger.info(">>>checkTabs");
        mobileInAppPage.selectLayout(PORTRAIT);
        softAssert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/");
        mobileInAppPage.selectLayout(LANDSCAPE);
        softAssert.assertEquals(mobileInAppPage.getLayoutTabs(),LANDSCAPE+"/");
        mobileInAppPage.selectLayout(BOTH);
        softAssert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/"+LANDSCAPE+"/");
        logger.info("<<<checkTabs");
        try{
            mobileInAppPage.clickClose();
            Assert.assertTrue(mobileInAppPage.getBreadcumbText().contains(CAMPAIGNS));}
        catch (Exception e){
            //DO NOthing
        }
        softAssert.assertAll();
        logger.info("<<<verifyInterstitialWithImageOnlyNotificationTabs");
    }

    @Test(groups={"Regression","All"},description = "verify if all tabs are working for Cover with Image only Notification page of AB Test for Variant B")
    public void verifyTabsForABTestVariantBCoverWithImageOnlyNotification(){
        logger.info(">>>verifyTabsForABTestVariantBCoverWithImageOnlyNotification");
        Mocha.openLeftNavMenu(driver,false,  NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        logger.info(">>>reachABTestVariantACoverWithContentNotification");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        mobileInAppPage.goToCreateMsgPage();
        mobileInAppPage.selectABRadioBtn();
        mobileInAppPage.selectVariantB();
        mobileInAppPage.selectMessageFormat(COVER, IMAGE_ONLY_NOTIFICATION);
        softAssert.assertEquals(mobileInAppPage.getContentHeader(),CONTENT);
        logger.info("<<<reachABTestVariantACoverWithContentNotification");
        mobileInAppPage.selectLayout(PORTRAIT);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/");
        mobileInAppPage.selectLayout(LANDSCAPE);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),LANDSCAPE+"/");
        mobileInAppPage.selectLayout(BOTH);
        Assert.assertEquals(mobileInAppPage.getLayoutTabs(),PORTRAIT+"/"+LANDSCAPE+"/");
        try {
            mobileInAppPage.clickClose();
            Assert.assertTrue(mobileInAppPage.getBreadcumbText().contains(CAMPAIGNS));
        } catch (Exception e) {
            //DO Nothing
        }
        softAssert.assertAll();
        logger.info("<<<verifyTabsForABTestVariantBCoverWithImageOnlyNotification");
    }

    @Test(groups={"Regression","All"},description = "verify if you can add a pic via robot class")
    public void addPicViaRobotClass() throws AWTException,InterruptedException {
        logger.info(">>>addPicViaRobotClass");
        Mocha.openLeftNavMenu(driver,false,  NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        mobileInAppPage.goToCreateMsgPage();
        mobileInAppPage.selectABRadioBtn();
        mobileInAppPage.selectVariantB();
        mobileInAppPage.selectMessageFormat(COVER, IMAGE_ONLY_NOTIFICATION);
        mobileInAppPage.selectImageRadioBtn(UPLOAD_IMAGE);
        SeleniumUtils.scrollDown(driver);
        mobileInAppPage.clickToBrowzeImage();
        mobileInAppPage.uploadImage(new File("../Images/Leaf.jpg"));
        try {
        Assert.assertTrue(mobileInAppPage.checkIfImageLoaded(UPLOAD_IMAGE));
        mobileInAppPage.clickSaveImageBtn();
        Assert.assertTrue(mobileInAppPage.checkIfImageLoaded(PREVIEW));
        mobileInAppPage.clickClose();
        Assert.assertTrue(mobileInAppPage.getBreadcumbText().contains(CAMPAIGNS));
        } catch (Exception e) {
            mobileInAppPage.clickClose();
        }
        logger.info("<<<addPicViaRobotClass");
    }

    @Test(groups={"Regression","All"},description = "create end to end single message campaign")
    public void createEndTOEndSingleMessageCampaign()throws InterruptedException{
        logger.info(">>>createEndTOEndSingleMessageCampaign");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(),"");
        MobileInAppPage mobileInAppPage = new MobileInAppPage(driver);
        mobileInAppPage.goToCreateMsgPage();
        mobileInAppPage.selectMessageFormat(COVER,WITH_CONTENT_NOTIFICATION);
        mobileInAppPage.giveTitle(SOME_RANDOM_TITLE);
        SeleniumUtils.scrollDown(driver,"500");
        mobileInAppPage.selectTile(MESSAGE);
        mobileInAppPage.giveMessage(SOME_RANDOM_MESSAGE);
        mobileInAppPage.selectTile(MESSAGE);
        SeleniumUtils.waitForPageLoaded(driver);
        mobileInAppPage.selectTile(BUTTONS);
        mobileInAppPage.addBtn();
        mobileInAppPage.addTextToBtn("1", SAMPLE_BUTTON);
        mobileInAppPage.selectTile(BUTTONS);
        SeleniumUtils.scrollDown(driver);
        mobileInAppPage.selectTile(BACKGROUND);
        SeleniumUtils.scrollDown(driver);
        mobileInAppPage.clickContinueOnWhatBtn();
        mobileInAppPage.clickContinueOnSetupBtn();
        mobileInAppPage.clickscheduleNotificationBtn();
        mobileInAppPage.setCampaignNameAndSave(RANDOM_TEST);
        SeleniumUtils.waitForPageLoaded(driver);
        Assert.assertEquals(mobileInAppPage.getTextForCampaignBreadCrum(),"Report for "+RANDOM_TEST);
        logger.info("<<<createEndTOEndSingleMessageCampaign");
    }

}