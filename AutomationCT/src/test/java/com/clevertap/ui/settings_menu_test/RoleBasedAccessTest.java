package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.AnalyzeEventPage;
import com.clevertap.ui.pages.RoleBasedAccess;
import com.clevertap.ui.pages.analyze_page.FunnelsPage;
import com.clevertap.ui.pages.boards.Boards;
import com.clevertap.ui.pages.campaigns_page.CampaignsHomePage;
import com.clevertap.ui.pages.campaigns_page.MobilePushPage;
import com.clevertap.ui.pages.journey_page.JourneyHomePage;
import com.clevertap.ui.pages.login_page.LoginPageNegativeScenarios;
import com.clevertap.ui.pages.segment_pages.FindPeoplePage;
import com.clevertap.ui.pages.segment_pages.Segments;
import com.clevertap.ui.pages.settings_menu_page.Account;
import com.clevertap.ui.pages.settings_menu_page.Roles;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*Scenarios covered in RBAC automation:
Scenarios:
Verify different users Write access
Login with users having different roles
Go to users and select user and corresponding roles
If multiple roles are assigned then save it in an array
Go to role and get the access matrix of each role saved in array and generate a resultant matrix having least access.
According to the matrix generated go to every page and verify access rights.
Verify Creator User is able to invite other users with creator and member rights
Verify Creator User is not able to invite other users with admin and custom role rights
Verify Member User is not able to invite any other user*/

public class RoleBasedAccessTest extends BaseTest{

    private Logger logger;
    private WebDriver driver;
    private RoleBasedAccess roleBasedAccess;
    private LoginPageNegativeScenarios loginPageNegativeScenarios;
    private Boards boards;
    private SweetAlert sweetAlert;
    private FindPeoplePage findPeoplePage;
    private Segments segments;
    private JourneyHomePage journeyHomePage;
    private AnalyzeEventPage analyzeEventPage;
    private FunnelsPage funnelsPage;
    private CampaignsHomePage campaignsHomePage;
    private MobilePushPage mobilePushPage;
    private Account account;
    private Roles roles;
    private String CampaignName=Data.randomAlphaNumeric("AutomationCampaign",4);

    @BeforeClass(alwaysRun = true)
    public void init() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        logger = baseTest.configureLogger(getClass());
        roleBasedAccess = new RoleBasedAccess(driver);
        loginPageNegativeScenarios=new LoginPageNegativeScenarios(driver);
        boards=new Boards(driver);
        sweetAlert=new SweetAlert(driver);
        findPeoplePage= new FindPeoplePage(driver);
        segments=new Segments(driver);
        journeyHomePage=new JourneyHomePage(driver);
        analyzeEventPage=new AnalyzeEventPage(driver);
        funnelsPage=new FunnelsPage(driver);
        campaignsHomePage=new CampaignsHomePage(driver);
        mobilePushPage=new MobilePushPage(driver);
        roles=new Roles(driver);
        account=new Account(driver);

        Reporter.log(">>>creating Push Campaign",true);
//        Mocha.openLeftNavMenu(driver,true,NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
//        mobilePushPage.createCampaign();
//        mobilePushPage.createMobilePush();
//        mobilePushPage.selectOneTimePBS();
//        mobilePushPage.selectCampaignStartDate();
//        mobilePushPage.selectMessageType();
//        mobilePushPage.enterCampaignMeta("Automation Title", "Campign created through", CampaignName);
//        Mocha.forceNavigate = true;
//        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
//
//        List<String> campaignsList = mobilePushPage.getTableCellData();
//        Assert.assertTrue(campaignsList.contains(CampaignName), "created campaigin was not present on listing page");
//        Reporter.log(">>>Push Campaign created",true);
//        loginPageNegativeScenarios.logOutApplication();

    }

//    @Test(description = "Verify different users Write access", groups = {TestCaseGroups.ROLEBASEDACCESSREGRESSION}, dataProvider = "RBACUsers")
    public void verifyUserRights(String userName, String password, String role) {
        try {
            Reporter.log("****** verify UserRights test started ******", true);
            Mocha.forceNavigate = true;
            Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), null);
            Map<String, Integer> roleAccess = roleBasedAccess.resultantRoleAccess(userName);
            Set<String> subComponents = roleAccess.keySet();
            for (String subComponent : subComponents) {
                Assert.assertTrue(roleBasedAccess.hasWriteAccess(subComponent, roleAccess.get(subComponent)));
            }
            driver.navigate().refresh();
            SeleniumUtils.pause(10);
            roleBasedAccess.logOutApplication();
            driver.manage().deleteAllCookies();
            Reporter.log("****** verify UserRights test ended ******", true);
        }finally {
            Mocha.forceNavigate = false;
        }
    }

    @DataProvider(name = "RBACUsers")
    public Object[][] usersDataProvider() {
        return new Object[][]{
                {"ben.sequeira+rbac@clevertap.com", "Clevertap@123", "Creator_segmentRead_Settings Role"},
                {"ben.sequeira+q2@clevertap.com", "Clevertap@123", "OnlyReadAccess"},
                {"ben.sequeira+allWrite@clevertap.com", "Clevertap@123", "Creator_AllWriteAccess"}
        };
    }

    //@Test(description = "Verify Creator User is able to invite other users with creator and member rights", dataProvider = "validUsers", groups = {TestCaseGroups.ROLEBASEDACCESSREGRESSION})
    public void verifyCreatorUserRights(String emailID, String roles) {
        try {
            Reporter.log("****** verify CreatorUserRights test started ******", true);
            Mocha.forceNavigate = true;
            Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), null);
            roleBasedAccess.addUsers(roles, emailID);
            Assert.assertTrue(roleBasedAccess.isInvitationSent(), "Invitation is not sent");
            SeleniumUtils.pause(10);
            roleBasedAccess.logOutApplication();
            Reporter.log("****** verify CreatorUserRights test ended ******", true);
        }finally {
            Mocha.forceNavigate = false;
        }
    }

    //@Test(description = "Verify Creator User is not able to invite other users with admin and custom role rights", dataProvider = "invalidUsers", groups = {TestCaseGroups.ROLEBASEDACCESSREGRESSION})
    public void verifyCreatorUsrRights(String emailID, String roles) {
        try {
            Reporter.log("****** verify CreatorUsrRights test started ******", true);
            Mocha.forceNavigate = true;
            Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), null);
            roleBasedAccess.addUsers(roles, emailID);
            Assert.assertTrue(roleBasedAccess.isErrorPopUpDisplayed(), "Invitation is sent");
            SeleniumUtils.pause(10);
            roleBasedAccess.logOutApplication();
            Reporter.log("****** verify CreatorUsrRights test ended ******", true);
        }finally {
            Mocha.forceNavigate = false;
        }
    }

    //@Test(description = "Verify Member User is not able to invite any other user", groups = {TestCaseGroups.ROLEBASEDACCESSREGRESSION})
    public void verifyMemberUserrights() {
        Reporter.log("****** verify MemberUserRights test started ******",true);
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), null);
        Assert.assertTrue(roleBasedAccess.isAccessDeniedPopUpDisplayed(), "Error message is not displayed");
        Reporter.log("****** verify MemberUserRights test ended ******",true);
    }

    @DataProvider(name = "validUsers")
    public Object[][] giveDiffUsersAndRolesCombination() {
        return new String[][]{
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Creator"},
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Member"},
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Creator/Member"},
        };
    }

    @DataProvider(name = "invalidUsers")
    public Object[][] provideDiffUsersAndRolesCombination() {
        return new String[][]{
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Admin"},
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Settings Role"},
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Admin/Settings Role"},
        };
    }

    @Test(description = "Verify Member User should have only read access", groups = {TestCaseGroups.RBACCRITICAL},priority = 1)
    public void verifyMemberUserShouldHaveOnlyReadAccess(){
        Reporter.log("****** verifyMemberUserShouldHaveOnlyReadAccess test started ******",true);
//        driver.close();
//        driver.quit();
//        driver = getDriver();
//        loginPageNegativeScenarios.logOutApplication();
        driver.manage().deleteAllCookies();

        Mocha.openLeftNavMenu(driver,true,NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "",getValue("MEMBER_USER_EMAIL"),getValue("MEMBER_USER_PASSWORD"));
        SeleniumUtils.pause(2);
//        if(driver.findElements(By.xpath("//ul[@data-parent-menu='Segment' and @style='display: none;']")).size()>0){
//            SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]")));
//        }
//        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]/../..//a[contains(text(),'Find People')]")));
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.FIND_PEOPLE.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());


//        Assert.assertTrue(loginPageNegativeScenarios.verifyUserLoggedIn(),"User was not logged in Sucessfully");

//        Reporter.log("verifying member should not have access to create board : started ",true);
//        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.All_BOARDS.toString(), null);
//        SeleniumUtils.waitAndClick(driver,boards.newBoard);
//        sweetAlert.verifyAccessDeniedAlert();
//        Reporter.log("verifying member should not have access to create board : ended ",true);
//
        Reporter.log("verifying member should not have access to create campaign from Find People : started ",true);
        findPeoplePage.viewDetails();
        SeleniumUtils.waitAndClick(driver,findPeoplePage.findPeopleCreateWebPushCampaign);
        SeleniumUtils.pause(2);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1); // this pause is neccessary dont remove otherwise test will fail
        SeleniumUtils.waitAndClick(driver,findPeoplePage.findPeopleCreateMobilePushCampaign);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1); // this pause is neccessary dont remove otherwise test will fail
        SeleniumUtils.waitAndClick(driver,findPeoplePage.findPeopleCreateSmsCampaign);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1); // this pause is neccessary dont remove otherwise test will fail
        SeleniumUtils.waitAndClick(driver,findPeoplePage.findPeopleCreateEmailCampaign);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1); // this pause is neccessary dont remove otherwise test will fail
        SeleniumUtils.waitAndClick(driver,findPeoplePage.findPeopleCreateFbCampaign);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        Reporter.log("verifying member should not have access to create campaign from Find People : ended ",true);

        Reporter.log("verify member user should not have access to Settings/Integartion Tab : started",true);


        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and text()='Settings']")));
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Integration')]")),10);
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Integration')]")));
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Integration')]")),10);
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Integration')]")));
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Integration')]/../../ul/li/a[text()='Push Notifications']")),10);
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Integration')]/../../ul/li/a[text()='Push Notifications']")));
        SeleniumUtils.pause(1);
//        Mocha.forceNavigate=true;
//        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());

//        if(driver.findElements(By.xpath("//ul[@data-parent-menu='Integration' and @style='display: none;']")).size()>0){
//            driver.findElement(By.xpath("//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Integration')]")).click();
//        }
//        SeleniumUtils.pause(2);
//        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Integration')]/../../ul/li/a[text()='Push Notifications']")));

        sweetAlert.verifyAccessDeniedToFeature();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        Assert.assertTrue(account.leftBreadCrumAccount.getText().contains("Account"),"User was not navigated back to account page");
        Reporter.log("verify member user should not have access to Settings/Integartion Tab : ended",true);

        Reporter.log("verify member user should not have access to change timezone : started",true);
        account.selectTimezoneFromDropDown("Pacific/Midway");
        SeleniumUtils.pause(1);
        sweetAlert.verifyAccessDeniedToFeature();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        Reporter.log("verify member user should not have access to change timezone : ended",true);

//        Reporter.log("verifying member should not have access to create segment : started ",true);
//        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
//        segments.createSegment();
//        sweetAlert.verifyAccessDeniedAlert();
//        Reporter.log("verifying member should not have access to create segment : ended ",true);
//
//        Reporter.log("verifying member should not have access to segment actions : started ",true);
//        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("(//div[@data-original-title='Show segment actions'])[1]")));
//        Assert.assertFalse(segments.verifySegementActionsVisiblity(),"Segement was present for member user");
//        Reporter.log("verifying member should not have access to segment actions : ended ",true);
//
        Reporter.log("verifying member should not have access to engage under segment : started ",true);
//        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());

        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and text()='Back to main menu']")),10);
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and text()='Back to main menu']")));
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]")),10);
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]")));
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]/../..//a[contains(text(),'Segments')]")),10);
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]/../..//a[contains(text(),'Segments')]")));

//        if(driver.findElements(By.xpath("//ul[@data-parent-menu='Segment' and @style='display: none;']")).size()>0){
//            SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]")));
//        }
//        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and contains(text(),'Segment')]/../..//a[contains(text(),'Segments')]")));

        SeleniumUtils.waitAndClick(driver,segments.segmentGridView);
        new Actions(driver).moveToElement(driver.findElement(By.xpath("(//a[text()='Engage'])[1]"))).click().build().perform();
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver,segments.segmentListView);
        Reporter.log("verifying member should not have access to engage under segment : ended ",true);

//        Reporter.log("verifying member should not have access to mark segment as precomputed: started ",true);
//        SeleniumUtils.waitAndClick(driver,segments.firstSegmentLoc);
//        SeleniumUtils.waitAndClick(driver,segments.precomputedLink);
//        sweetAlert.verifyAccessDeniedAlert();
//        SeleniumUtils.pause(1);
//
//        Reporter.log("verifying member should not have access to create segment goal: started ",true);
//        Assert.assertTrue(segments.createSegmenGoaltDisabledButton.isDisplayed(),"Member is able to create segment goal");
//        Reporter.log("verifying member should not have access to create segment goal: ended ",true);
//
        Reporter.log("verifying member should not have access to create campaign and journey under segment: started ",true);
        SeleniumUtils.waitAndClick(driver,segments.firstSegmentLoc);
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//div[@class='card__body--unequal']"))).moveToElement(segments.segmentReachabilityCreateCampLink).click().build().perform();
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1);

        SeleniumUtils.scrollDownLittle(driver);
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//div[@class='card__body--unequal']"))).moveToElement(segments.segmentReachabilityCreateJourneyLink).click().build().perform();
//        SeleniumUtils.waitAndClick(driver,segments.segmentReachabilityCreateJourneyLink);
        SeleniumUtils.pause(1);// dont remove this wait it is required
        journeyHomePage.publishJourney("random nm");
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1);
        driver.navigate().back();// dont use mocha here cause it takes time
        driver.navigate().back();

        SeleniumUtils.waitAndClick(driver,segments.doMoreSegementCreateCampaign);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver,segments.doMoreSegementCreateJourney);
        SeleniumUtils.pause(1);// dont remove this wait it is required
        journeyHomePage.publishJourney("random nm");
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        Reporter.log("verifying member should not have access to create campaign and journey under segment: ended ",true);
//
//        Reporter.log("verifying member should not have access to create Segment under Anayalze->event page: started ",true);
//        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.EVENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
//        SeleniumUtils.pause(2);
//        SeleniumUtils.waitAndClick(driver,analyzeEventPage.viewDetails);
//        analyzeEventPage.saveSegementOnAnalyzePage("random nm");
//        sweetAlert.verifyAccessDeniedAlert();
//        SeleniumUtils.pause(1);
//        Reporter.log("verifying member should not have access to create Segment under Anayalze->event page: ended ",true);
//
//        Reporter.log("verifying member should not have access to create Segment under Anayalze->event page: started ",true);
//        SeleniumUtils.waitAndClick(driver,analyzeEventPage.createMessageLink);
//        sweetAlert.verifyAccessDeniedAlert();
//        Assert.assertFalse(analyzeEventPage.userDownloadDisableLink.isDisplayed(),"download user option should not be present");
//        Reporter.log("verifying member should not have access to create Segment under Anayalze->event page: ended ",true);

        Reporter.log("verify user should not have option to create/edit/clone/stop campaign under Engage->campaign page : started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        SeleniumUtils.waitAndClick(driver,campaignsHomePage.campaignCreateBtn);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1);
        mobilePushPage.clickShowCampaignAction(CampaignName);
        SeleniumUtils.waitAndClick(driver,mobilePushPage.editCampaign);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1);
        mobilePushPage.clickShowCampaignAction(CampaignName);
        SeleniumUtils.waitAndClick(driver,mobilePushPage.cloneCampaignBtn);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.pause(1);
        mobilePushPage.clickShowCampaignAction(CampaignName);
        SeleniumUtils.waitAndClick(driver,mobilePushPage.stopCampaignButton);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();

        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath(mobilePushPage.DIV_CLASS_FILTERBTN_AND_TEXT+"Status']")));
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath(mobilePushPage.DIV_CLASS_FILTERBTN_AND_TEXT+"Status']/..//ul//label[text()='Stopped']")));
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath(mobilePushPage.DIV_CLASS_FILTERBTN_AND_TEXT+"Status']/..//a")));
        SeleniumUtils.pause(2);//this wait is required dont remove it
        SeleniumUtils.waitAndClick(driver,mobilePushPage.selectAllVisibleCampaignBtn);
        SeleniumUtils.waitAndClick(driver,mobilePushPage.archiveBtn);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        Reporter.log("verify user should not have option to create/edit/clone/stop campaign under Engage->campaign page : ended",true);

        Reporter.log("verify user should not have option to create journey under Engage->journey page : started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.JOURNEYS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        SeleniumUtils.waitAndClick(driver,journeyHomePage.createJourneyBtn);
        sweetAlert.verifyAccessDeniedAlert();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
//        journeyHomePage.filterJourneyPage();
//        SeleniumUtils.pause(2);
//        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("(//div[@data-original-title='Show journey actions'])[1]")));
//        SeleniumUtils.waitAndClick(driver,journeyHomePage.cloneJourneyBtn);
//        sweetAlert.verifyAccessDeniedAlert();
//        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
//        SeleniumUtils.pause(1);
//        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("(//div[@data-original-title='Show journey actions'])[1]")));
//        SeleniumUtils.waitAndClick(driver,journeyHomePage.editjourneyBtn);
//        sweetAlert.verifyAccessDeniedAlert();
//        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
//        SeleniumUtils.pause(1);
        Reporter.log("verify user should not have option to create journey under Engage->journey page : ended",true);

//        Reporter.log("verifying member should not have access to create Campaign under Anayalze->funnel page: started ",true);
//        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ANALYZE.toString(), NavigateCTMenuEnums.Submenu.FUNNELS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
//        SeleniumUtils.waitAndClick(driver,funnelsPage.viewFunnel);
//        SeleniumUtils.pause(2);
//        Actions action=new Actions(driver);
//        action.moveToElement(driver.findElement(By.xpath("//*[name()='g' and @class='highcharts-series-group']"))).click().build().perform();
//        new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[name()='g' and @class='highcharts-series-group']"))).click().build().perform();
//        SeleniumUtils.pause(1);
//        SeleniumUtils.waitAndClick(driver,funnelsPage.createCampaignBtn);
//        sweetAlert.verifyAccessDeniedAlert();
//        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
//        Reporter.log("verifying member should not have access to create Campaign under Anayalze->funnel page: ended ",true);

        // commenting below test case because it is known issue

//        Reporter.log("verifying member should not have access to create segment under Anayalze->funnel page: started ",true);
//        new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[name()='g' and @class='highcharts-series-group']"))).click().build().perform();
//        SeleniumUtils.waitAndClick(driver,funnelsPage.createSegmentBtn);
//        SeleniumUtils.enterInputText(driver,funnelsPage.segmentNameInput,"automation segment");
//        SeleniumUtils.waitAndClick(driver,funnelsPage.createSegmentInput);
//        sweetAlert.verifyAccessDeniedAlert();
//        SeleniumUtils.pause(1);
//        Reporter.log("verifying member should not have access to create segment under Anayalze->funnel page: ended ",true);

    }

    @Test(description = "Verify creator User should have only read access for Billing,Roles,Timezone", groups = {TestCaseGroups.RBACCRITICAL},priority = 0)
    public void verifyCreatorUserHasCorrectAccess(){
        driver.manage().deleteAllCookies();
//        driver.get(Mocha.url);
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.NOSUBMENU.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString(),getValue("CREATOR_USER_EMAIL"),getValue("CREATOR_USER_PASSWORD"));
        //Below if condition is required coz in parallel execution mocha sometime fails to click on element.
        if(driver.findElements(By.xpath("//div[@class='sidebar__menu-parentname' and text()='Back to main menu']")).size()==0){
            SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//div[@class='sidebar__menu-parentname' and text()='Settings']")));
        }

        Reporter.log("verify creator user should not have access to change timezone : started",true);
        account.selectTimezoneFromDropDown("Pacific/Midway");
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver,account.proceedAnywayBtn);
        SeleniumUtils.pause(1);
        sweetAlert.errorUpdatingValue();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        Reporter.log("verify creator user should not have access to change timezone : ended",true);

        Reporter.log("verify creator user should not have access to Settings->Billing page : started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.BILLING.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        sweetAlert.verifyAccessDeniedToFeature();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        Assert.assertTrue(account.leftBreadCrumAccount.getText().contains("Account"),"User was not navigated back to account page");
        Reporter.log("verify creator user should not have access to Settings->Billing page : ended",true);

        Reporter.log("verify creator user should not have access to create new role : started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ROLES.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        roles.addRole.click();
        roles.saveRole.click();
        SeleniumUtils.pause(1);
        roles.saveRoleAs.sendKeys("Automation Role");
        roles.saveRoleButton.click();
        roles.errorUpdatingValue();
        driver.findElement(By.xpath("//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")).click();
        SeleniumUtils.waitForElementToClickable(driver,roles.cancelSaveRoleModal,10);
        SeleniumUtils.waitAndClick(driver,roles.cancelSaveRoleModal);
        Reporter.log("verify creator user should not have access to create new role : ended",true);

        Reporter.log("verify creator user should able to create campaign : started",true);

        Mocha.openLeftNavMenu(driver,false,NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");
        if(driver.findElements(By.xpath("//button[@class='annoucement-popup__btn' and text()='Close']")).size()>0){
            Reporter.log("closing campaign popup",true);
            SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//button[@class='annoucement-popup__btn' and text()='Close']")));
        }

        mobilePushPage.createCampaign();
        mobilePushPage.createMobilePush();
        mobilePushPage.selectOneTimePBS();
        mobilePushPage.selectCampaignStartDate();
        mobilePushPage.selectMessageType();
        mobilePushPage.enterCampaignMeta("Automation Title", "Campign created through", CampaignName);
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver,false, "", NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), "");

        List<String> campaignsList = mobilePushPage.getTableCellData();
        Assert.assertTrue(campaignsList.contains(CampaignName), "created campaigin was not present on listing page");

        Reporter.log("verify creator user should able to create campaign : ended",true);

    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        Reporter.log("inside after class of RBAC ",true);
        driver.close();
        driver.quit();

    }
}
