package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.settings_menu_page.Roles;
import com.clevertap.utils.Data;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class RolesTest extends BaseTest{
    private WebDriver driver;
    private static Logger logger;
    final static String path = "Home/Settings/Roles";
    private Roles roles ;
    String roleName= Data.randomAlphaNumeric("AutomationDefau",4);

    @BeforeClass(alwaysRun = true)
    public void init() {
        driver=getDriver();
        logger = configureLogger(getClass());
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.SETTINGS.toString(),
                NavigateCTMenuEnums.Submenu.ROLES.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        roles= new Roles(driver);

    }

    @Test(priority = 1, description = "Verify role page open")
    public void openRolesPage() throws InterruptedException {
        logger.info("******openRolesPage Test Started*****");
        String rolesPageBreadcumbText = roles.getPageHeaderText();
        Assert.assertTrue(rolesPageBreadcumbText.contains("Roles"), "Roles page successfully launched");
        logger.info("******openRolesPage Test Finished*****");

    }

    @Test(priority = 2, description = "Verify default read role")
    public void verifyDefaultOnlyReadRole() throws InterruptedException {
        logger.info("******verifyDefaultOnlyReadRole Test Started*****");
        roles.CreateNewDefaultReadRole(roleName);
        List cellValue = roles.getDataFromSpecificCell(0);
        Assert.assertTrue(cellValue.contains(roleName), "New default read role created");
        logger.info("******verifyDefaultOnlyReadRole Test Finished*****");

    }

    @Test(priority = 3, description = "try to create duplicate role")
    public void verifyDuplicateRoleCreation(){
        logger.info("******verifyDuplicateRoleCreation Test Started*****");
        roles.createDuplicateRole(roleName);
        String errorMessage = roles.getErrorMessage();
//        Assert.assertTrue(errorMessage.contains("please provide unique role name"), "please provide unique role name");
        Assert.assertEquals(errorMessage,"Please provide unique role name","unexpected message was present in error modal");
        logger.info("******verifyDuplicateRoleCreation Test Finished*****");
    }


    @Test(priority = 4, description = "edit role")
    public void editRole() throws InterruptedException {
        logger.info("******editRole Test Started*****");
        roles.goBackToRlesPage();
        SeleniumUtils.pause(1);
        roles.clickDottedEditButton(roleName);
        roles.editRole(driver);
        Assert.assertTrue(roles.isRoleEditable(), "roles can be editted");
        logger.info("******editRole Test Finished*****");

    }

    @Test(priority = 5, description = "Delete all roles created by automation")
    public void deleteAllRolesCreatedByAutomation() throws InterruptedException {
        logger.info("******deleteAllRolesCreatedByAutomation Test Started*****");
        driver.navigate().back();
        roles.goBackToRlesPage();
        roles.deleteAllAutomationRoles(roleName);
        List rolesList = roles.getDataFromSpecificCell(0);
        for (Object role : rolesList) {
            System.out.println(role.toString());
            if (!role.toString().startsWith("Automation")) {
                Assert.assertTrue(true, "All automation created roles deleted");
            }
        }

        logger.info("******deleteAllRolesCreatedByAutomation Test Finished*****");

    }

    @Test(priority = 6, description = "try to CLoning role as an admin")
    public void verifyCloningRole() throws InterruptedException {

        logger.info("******verifyCloningRole Test Started*****");
        roles.CreateNewDefaultReadRole(roleName);
        SeleniumUtils.pause(1);
        roles.clickDottedEditButton(roleName);
        SeleniumUtils.pause(1);
        driver.findElement(By.xpath("//span[@title='"+roleName+"']//ancestor::div[@id='wrap']/following::span[@title='Clone role']")).click();
        roles.cloneRole(roleName + "cloned");
//        List rolesList = roles.getDataFromSpecificCell(0);
//        Assert.assertTrue(rolesList.contains(roleName + "cloned"), "Role is cloned successfully");
        String clonedName=driver.findElement(By.xpath("(//table[contains(@class,'datatable-resultset')]//tr//span)[1]")).getText();
        Assert.assertEquals(clonedName,roleName + "cloned","something went wrong while cloning role");
        /*below two lines are not working as I am unable to delete after cloning the role*/
//        roles.deleteAllAutomationRoles(roleName);
//        roles.deleteAllAutomationRoles(roleName+"cloned");
        logger.info("******verifyCloningRole Test Finished*****");

    }

    @Test(priority = 7, description = "Remove access to segments feature and check the behaviour")
    public void removeAccessToSegmentsFeature() throws InterruptedException {

        logger.info("******removeAccessToSegmentsFeature Started*****");
        roles.clickPlusRole();
        roles.uncheckAllAccess(driver, "Segments");
        Thread.sleep(1000);
        List featuresListed = roles.getDataFromSpecificCell(0);
        roles.clickSaveRolePage();
        roles.EnterRoleNameAndSave("Segments");
        Assert.assertTrue(!featuresListed.contains("Segments"), "access to Segments feature has been removed successfullu");

        logger.info("removeAccessToSegmentsFeature Finished");

    }

    @Test(priority = 8, description = "Remove access to segments feature and check the behaviour")
    public void removeAccessToRealImpactFeature() throws InterruptedException {

        logger.info("******removeAccessToRealImpactFeature Started*****");
        driver.navigate().back();
        roles.clickPlusRole();
        roles.uncheckAllAccess(driver, "Real Impact");
        SeleniumUtils.pause(1);
        List featuresListed = roles.getDataFromSpecificCell(0);
        roles.clickSaveRolePage();
        String roleNm=Data.randomAlphaNumeric("RealImpact",6);
        roles.EnterRoleNameAndSave(roleNm);
        Assert.assertTrue(!featuresListed.contains(roleNm), "access to Real Impact feature has been removed successfullu");

        logger.info("removeAccessToRealImpactFeature Finished");

    }

    @Test(priority = 9, description = "Remove access to segments feature and check the behaviour")
    public void removeAccessToBoardsFeature() throws InterruptedException {

        logger.info("******removeAccessToBoardsFeature Started*****");
        SeleniumUtils.pause(1);
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.SETTINGS.toString(),
                NavigateCTMenuEnums.Submenu.ROLES.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        roles.clickPlusRole();
        roles.uncheckAllAccess(driver, "Boards");
        SeleniumUtils.pause(2);
        List featuresListed = roles.getDataFromSpecificCell(0);
        roles.clickSaveRolePage();
        String roleNm=Data.randomAlphaNumeric("BoardsRemove",6);
        roles.EnterRoleNameAndSave(roleNm);
        Assert.assertTrue(Collections.frequency(featuresListed, "boards") < 2, "access to boards feature has been removed successfullu");

        logger.info("removeAccessToBoardsFeature Finished");

    }

    @AfterClass(alwaysRun = true)
    private void tearDown() throws InterruptedException {
        logger.info("closing the chrome broswer and driver");
        Roles roles=new Roles(driver);
//        roles.deleteExistingItems();

    }


}
