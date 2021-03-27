
package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.Login;
import com.clevertap.ui.pages.settings_menu_page.Users;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.util.List;


public class UsersTest extends BaseTest {

    private static final String SETTINGS_USERS = "/Settings/Users";
    private static final String CLEVER_TAP_SIGNUP = "CleverTap Signup";
    private Logger logger;
    private  WebDriver driver;
    private String newUserID;
    private static final String adminRole = "Admin";
    private static final String creatorRole = "Creator";
    private Login login;
    private Gmail gmail;
    private Users user;
    private SweetAlert swal;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver = getDriver();
        login = new Login(driver);
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        logger = configureLogger(getClass());
        gmail = new Gmail(driver);
        user = new Users(driver);
        swal = new SweetAlert(driver);
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USER, TestCaseGroups.REGRESSION}, description = "To verify the page header of user page")
    public void verifyHeader() {
        Reporter.log("****verifyHeader Started*****", true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(),NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        Users user = new Users(driver);
        String header = user.extractHeader();
        Assert.assertEquals(header, SETTINGS_USERS);
        Reporter.log("****verifyHeader Finished*****", true);
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION}, dataProvider = "provideDiffUsers", description = "To verify if user can be added newly")
    public void addUserAndVerifyIfAdded(String emailId, String roles) {
        Reporter.log(">>>addUserAndVerifyIfAdded Started", true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(),NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        Users user = new Users(driver);
        user.addUsers(roles, emailId);
        driver.navigate().refresh();
        user.enterTextInSearchBox(emailId);
        SeleniumUtils.pause(1);
        Assert.assertTrue(user.verifyListOfItemsWithEmails(emailId), "Verified newly added user");
        boolean status = user.isUserActive(emailId);
        Assert.assertFalse(status, "status is incorrect for newly invited user");
        Assert.assertTrue(user.verifyRole(roles.split("/")[0]), "role is incorrect for newly invited user");

        Reporter.log("*******addUserAndVerifyIfAdded Finished*****", true);
    }

    @DataProvider(name = "provideDiffUsers")
    public Object[][] giveDiffUsersAndRolesCombination() {
        return new String[][]{
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Creator"},
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Admin/Member"},
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Admin"},
                {Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com", "Creator/Member"}
        };
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION}, description = "To verify that User invite is working and user is active after invitation is accepted")
    public void verifyUserInvite(){
        Reporter.log(">>>verifyUserInvite Test started<<<", true);
        String cTAutomationUserName = BaseTest.getValue("GoogleUserName");
        String cTAutomationPassword = BaseTest.getValue("GooglePassword");

        gmail.logInToGMail(cTAutomationUserName, cTAutomationPassword);
        gmail.deleteEmailsWithHeader(CLEVER_TAP_SIGNUP);
        Mocha.forceNavigate = true;
        driver.navigate().to(Mocha.url);
        Alert alert = SeleniumUtils.switchToAlertIfPresent(driver);
        if(alert!=null){
            alert.accept();
        }
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), "");
        newUserID = user.inviteNewUser(cTAutomationUserName);
        user.acceptGmailInvite(cTAutomationUserName, cTAutomationPassword);
        driver.navigate().to(Mocha.url);
        alert = SeleniumUtils.switchToAlertIfPresent(driver);
        if(alert!=null){
            alert.accept();
        }
        login.login(BaseTest.getValue("UserName"), BaseTest.getValue("Password"));
        Mocha.forceNavigate = true;
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), "");
        boolean status = user.isUserActive(newUserID);
        Assert.assertTrue(status, "Existing user is not able to invite a new user");
        Assert.assertTrue(user.verifyRole(creatorRole), "Able to edit user role");
        Reporter.log(">>>verifyUserInvite Test end<<<", true);

    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION}, dependsOnMethods = {"verifyUserInvite"},description = "To verify user edit role functionality")
    public void editRoleViaTripleDot() {
        Reporter.log(">>>editRoleViaTripleDot", true);
        Mocha.forceNavigate = true;
        driver.navigate().to(Mocha.url);
        Alert alert = SeleniumUtils.switchToAlertIfPresent(driver);
        if(alert!=null){
            alert.accept();
        }
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), "");
        Users user = new Users(driver);
        driver.navigate().refresh();
        SeleniumUtils.pause(3);
        user.enterTextInSearchBox(newUserID);
        Assert.assertTrue(user.verifyRole(creatorRole), "Newly invited user should be as creator as per automation");
        user.editRolesTripleDot(adminRole);
        Assert.assertTrue(user.verifyRole(adminRole), "Able to edit user role");
        Reporter.log("<<<editRoleViaTripleDot", true);
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION}, dataProvider = "ProvideDiffRoleFilter", dependsOnMethods = {"editRoleViaTripleDot"}, description = "To verify that role filter on users page is working as expected")
    public void verifyRoleFilter(String roles, String outputRole) {
        Reporter.log(">>>verifyRoleFilter", true);
        Users user = new Users(driver);
        Mocha.forceNavigate = true;
        driver.navigate().to(Mocha.url);
        Alert alert = SeleniumUtils.switchToAlertIfPresent(driver);
        if(alert!=null){
            alert.accept();
        }
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), "");
        driver.navigate().refresh();
        user.selectRoles(roles);
        boolean result = user.verifyListOfItems(outputRole, 3);
        Assert.assertTrue(result, "Role filter works effectively");
        Reporter.log("<<<verifyRoleFilter", true);
    }

    @DataProvider(name = "ProvideDiffRoleFilter")
    public Object[][] giveDiffRoleCombination() {
        return new String[][]{
                {"Creator", "Creator"},
                {"Admin", "Admin"},
                {"Admin/Member", "Admin/Member"},
                {"Creator/Member", "Creator/Member"}
        };
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION}, dataProvider = "provideDiffStatusFilter", dependsOnMethods = {"verifyRoleFilter"}, description = "To verify that status filter on users page is working as expected")
    public void verifyStatusFilter(String status, String outputStatus) {
        Reporter.log(">>>verifyStatusFilter", true);
        Users user = new Users(driver);
        Mocha.forceNavigate = true;
        driver.navigate().to(Mocha.url);
        Alert alert = SeleniumUtils.switchToAlertIfPresent(driver);
        if(alert!=null){
            alert.accept();
        }
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), "");
        driver.navigate().refresh();
        // This also tests whether revoke access in triple dot is working for active users
        if(status.equalsIgnoreCase("revoked")){
            user.revokeUserAccessFromUser(newUserID);
            driver.navigate().refresh();
            user.enterTextInSearchBox(newUserID);
            TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
            List<WebElement> listOfStatus = TableUtility.getDataFromSpecificCell(4);
            Assert.assertEquals(listOfStatus.get(0).getText(),"Revoked","Revoke Access button not working in triple dot");

        }
        user.selectStatus(status);
        boolean result = user.verifyListOfItems(outputStatus, 4);
        Assert.assertTrue(result, "Status filter works properly");
        Reporter.log("<<<verifyStatusFilter", true);
    }

    @DataProvider(name = "provideDiffStatusFilter")
    public Object[][] giveDiffStatusCombination() {
        return new String[][]{
                {"Active", "Active"},
                {"Invited", "Invited"},
                {"Revoked", "Revoked"},
        };
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION}, description = "To verify that re-invite button on triple dot is working")
    public void reInviteUserFromTripleDotTest(){

        Reporter.log(">>>reInviteUserFromTripleDotTest started<<<", true);
        String cTAutomationUserName = BaseTest.getValue("GoogleUserName");
        Mocha.forceNavigate = true;
        driver.navigate().to(Mocha.url);
        Alert alert = SeleniumUtils.switchToAlertIfPresent(driver);
        if(alert!=null){
            alert.accept();
        }
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), "");

        String reInvitedUser = user.inviteNewUser(cTAutomationUserName);

        user.enterTextInSearchBox(reInvitedUser);
        TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
        List<WebElement> listOfTripleDotElements = TableUtility.getDataFromSpecificCell(6);
        boolean flag = false;
        if (listOfTripleDotElements.size() != 0) {
            listOfTripleDotElements.get(0).click();
            SeleniumUtils.performClick(driver, user.reInviteUserPopOverBtn);
            SeleniumUtils.pause(1);
            flag = swal.getSweetAlertSuccessSignal();

        }
        Assert.assertTrue(flag,"Re-invite button is not working is not working.");

    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION}, description = "To verify that revoke access button on triple dot is working," +
            "This test case covers the case of revoke access for Invited user ie after revoking access for invited user, user will not be present in the users tab" +
            "For revoke access of Active user, this scenario is covered in verifyStatusFilter test case")
    public void revokeUserAccessViaTripleDotTestForInvitedUser(){

        Reporter.log("*******revokeUserAccessViaTripleDotTestForInvitedUser Started*****", true);

        String emailId = Data.randomAlphaNumeric("Automation+", 5) + "@clevertap.com";
        String role = "Admin";
        Reporter.log(">>>addUserAndVerifyIfAdded Started", true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(),NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        Users user = new Users(driver);
        user.addUsers(role, emailId);
        driver.navigate().refresh();
        user.enterTextInSearchBox(emailId);
        Assert.assertTrue(user.verifyListOfItemsWithEmails(emailId), "Verified newly added user");
        boolean status = user.isUserActive(emailId);
        Assert.assertFalse(status, "status is incorrect for newly invited user");
        Assert.assertTrue(user.verifyRole(role), "role is incorrect for newly invited user");

        user.revokeUserAccessFromUser(emailId);
        driver.navigate().refresh();
        user.enterTextInSearchBox(emailId);
        SeleniumUtils.pause(1);
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='datatable-collection']/table//td")).getText(),"No data",
                "invited user should not be present while searching in users when access is revoked");
        Reporter.log("*******revokeUserAccessViaTripleDotTestForInvitedUser Finished*****", true);

    }


    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION},dependsOnMethods = {"verifyStatusFilter"}, description = "To verify that grant access button on triple dot is working")
    public  void grantUserAccessViaTripleDotTest() {

        Reporter.log("*******grantUserAccessViaTripleDotTest Started*****", true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        Users user = new Users(driver);
        driver.navigate().refresh();
        user.enterTextInSearchBox("clevertapmumbaitest");
        user.selectStatus("Revoked");
        TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
        List<WebElement> listOfEmails = TableUtility.getDataFromSpecificCell(2);
        if (listOfEmails.size() == 0) {
            Reporter.log("User not found with revoked access", true);
        }
        String emailId = listOfEmails.get(0).getText();
        try {
            user.grantUserAccessViaTripleDot(emailId);
            driver.navigate().refresh();
            user.enterTextInSearchBox(emailId);
            TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
            List<WebElement> listOfStatus = TableUtility.getDataFromSpecificCell(4);
            Assert.assertEquals(listOfStatus.get(0).getText(), "Active", "Grant Access button not working in triple dot");

            Reporter.log("*******grantUserAccessViaTripleDotTest Finished*****", true);

        } finally {
            user.revokeUserAccessFromUser(emailId);
        }
    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION},dependsOnMethods = {"verifyUserInvite"}, description = "To verify that grant passcode button on triple dot is working")
    public void grantPasscodeViaTripeDot(){

        Reporter.log("*******grantPasscodeViaTripeDot Started*****", true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(),NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        Users user = new Users(driver);
        driver.navigate().refresh();
        user.enterTextInSearchBox("clevertapmumbaitest");
        user.selectStatus("active");
        user.selectPasscodeStatus("inactive/expired/revoked");
        TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
        List<WebElement> listOfEmails = TableUtility.getDataFromSpecificCell(2);
        if(listOfEmails.size()==0){
            Reporter.log("User not found ",true);
        }
        String emailId = listOfEmails.get(0).getText();
        user.grantPasscodeViaTripleDot(emailId);
        driver.navigate().refresh();
        user.enterTextInSearchBox(emailId);
        TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
        List<WebElement> listOfStatus = TableUtility.getDataFromSpecificCell(5);
        Assert.assertEquals(listOfStatus.get(0).getText(),"active","Grant Passcode button not working in triple dot");
        Reporter.log("*******grantPasscodeViaTripeDot Finished*****", true);

    }

    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION},dependsOnMethods = {"verifyUserInvite"}, description = "To verify that revoke passcode button on triple dot is working")
    public void revokePasscodeViaTripeDot(){

        Reporter.log("*******revokePasscodeViaTripeDot Started*****", true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(),NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        Users user = new Users(driver);
        driver.navigate().refresh();
        user.enterTextInSearchBox("clevertapmumbaitest");
        user.selectPasscodeStatus("active");
        TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
        List<WebElement> listOfEmails = TableUtility.getDataFromSpecificCell(2);
        String emailId = "";
        if(listOfEmails.size()==0){
            Reporter.log("User not found with active passcode status",true);
            //Hence first making an user as active
            driver.navigate().refresh();
            user.enterTextInSearchBox("clevertapmumbaitest");
            user.selectStatus("active");
            user.selectPasscodeStatus("inactive/expired/revoked");
            TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
            listOfEmails = TableUtility.getDataFromSpecificCell(2);
            emailId = listOfEmails.get(0).getText();
            user.grantPasscodeViaTripleDot(emailId);
            driver.navigate().refresh();
        }else{
            emailId = listOfEmails.get(0).getText();
        }

        user.revokePasscodeViaTripleDot(emailId);
        driver.navigate().refresh();
        user.enterTextInSearchBox(emailId);
        TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
        List<WebElement> listOfStatus = TableUtility.getDataFromSpecificCell(5);
        Assert.assertEquals(listOfStatus.get(0).getText(),"revoked","Revoke Passcode button not working in triple dot");
        Reporter.log("*******revokePasscodeViaTripeDot Finished*****", true);

    }


    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION},dependsOnMethods = {"verifyUserInvite"}, description = "To verify that reset passcode button on triple dot is working")
    public void resetPasscodeViaTripeDot(){

        Reporter.log("*******resetPasscodeViaTripeDot Started*****", true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(),NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        Users user = new Users(driver);
        driver.navigate().refresh();
        user.enterTextInSearchBox("clevertapmumbaitest");
        user.selectStatus("Active");
        user.selectPasscodeStatus("inactive");
        TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
        List<WebElement> listOfEmails = TableUtility.getDataFromSpecificCell(2);
        if(listOfEmails.size()==0){
            Reporter.log("User not found with active passcode status",true);
        }
        String emailId = listOfEmails.get(0).getText();
        user.resetPasscodeViaTripleDot(emailId);
        driver.navigate().refresh();
        user.enterTextInSearchBox(emailId);
        TableUtility.InitializeTable(driver, Users.TABLECONTAININGUSERS);
        List<WebElement> listOfStatus = TableUtility.getDataFromSpecificCell(5);
        Assert.assertEquals(listOfStatus.get(0).getText(),"expired","Reset Passcode button not working in triple dot");
        Reporter.log("*******resetPasscodeViaTripeDot Finished*****", true);

    }



    @Test(groups = {TestCaseGroups.ALL, TestCaseGroups.USERSTESTCRITICAL, TestCaseGroups.REGRESSION}, dataProvider = "provideDiffPasscodeFilter", dependsOnMethods = {"verifyStatusFilter"}, description = "To verify that passcode filter on users page is working as expected")
    public void verifyPasscodeStatusFilter(String passcode, String outputPasscode) {
        Reporter.log(">>>verifyPasscodeStatusFilter", true);
        Mocha.forceNavigate = true;
        driver.navigate().to(Mocha.url);
        Alert alert = SeleniumUtils.switchToAlertIfPresent(driver);
        if(alert!=null){
            alert.accept();
        }
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), "");
        driver.navigate().refresh();
        user.selectPasscodeStatus(passcode);
        boolean result = user.verifyListOfItems(outputPasscode, 5);
        Assert.assertTrue(result, "Passcode filter work effectively");
        Reporter.log("<<<verifyPasscodeStatusFilter", true);
    }



    @DataProvider(name = "provideDiffPasscodeFilter")
    public Object[][] giveDiffPasscodeCombination() {
        return new String[][]{
                {"inactive", "inactive"},
                {"expired", "expired"},
                {"revoked", "revoked"},
                {"active", "active"}
        };
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        Reporter.log("inside after class of user test",true);
        driver.close();
        driver.quit();

    }

}
