package com.clevertap.ui.engage_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.engage_page.ControlGroupPage;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ControlGroupTests extends BaseTest{

    private Logger logger;
    private static WebDriver driver;
    private static String TOOLTIP_TEXT = "A control group is randomly selected users who will not receive campaign messages. Control groups are the best way to measure the effectivity of your campaigns and journeys";
    private static String[] tabsName = {"System control group", "Custom control group"};
    private static String SYSTEMCONTROLGROUPTOOLTIP_TEXT = "The system control group is an account level control group. By default, users in this control control group will not receive any campaigns/ journey message. You can override this default at a campaign level";
    private static String WARNINGTEXTFORADDINGSYSTEMCONTROLGROUP = "You need to add the system control group to be able to use custom control group functionality.";


    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        logger = baseTest.configureLogger(getClass());
    }


    @Test(groups = {TestCaseGroups.CONTROLGROUPREGRESSION}, description = "Verify header of page")
    public void verifyControlGroupPageLaunched() {
        logger.info(">>>verifyControlGroupPageLaunched test started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        ControlGroupPage controlGroups = new ControlGroupPage(driver);
        String controlGroupTooltipText = controlGroups.getControlGroupsTooltipText();
        Assert.assertEquals(controlGroupTooltipText, TOOLTIP_TEXT);
        logger.info("<<<verifyControlGroupPageLaunched test finished");

    }

    @Test(groups = {TestCaseGroups.CONTROLGROUPREGRESSION}, description = "Verify control group tabs",dependsOnMethods = {"verifyControlGroupPageLaunched"})
    public void verifyControlGroupTabs() {
        logger.info(">>>verifyControlGroupTabs test started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        ControlGroupPage controlGroups = new ControlGroupPage(driver);
        List<String> controlGroupTooltabs = controlGroups.getTabsName();
        logger.info("Tabs present are: " + controlGroupTooltabs);
        Assert.assertTrue(controlGroupTooltabs.equals(Arrays.asList(tabsName)));
        logger.info("<<<verifyControlGroupTabs test finished");

    }

    @Test(groups = {TestCaseGroups.CONTROLGROUPREGRESSION}, description = "verify the the system control group tooltip shown is correct when there is no system control group exist",dependsOnMethods = {"verifyControlGroupTabs"})
    public void verifySystemControlGroupTooltipText() {
        logger.info(">>>verifySystemControlGroupTooltipText test started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        ControlGroupPage controlGroups = new ControlGroupPage(driver);
        controlGroups.switchToTab("Custom control group");
        int count = controlGroups.getNumberOfReconrdsInControlGroupTable();
        if (count > 1) {
            controlGroups.deleteAllControlGroup();
            controlGroups.switchToTab("System control group");
            controlGroups.deleteSystemControlGroup();

        }
        controlGroups.switchToTab("System control group");
        if (!controlGroups.isSystemControlGroupTooltipIconExist()) {
            controlGroups.deleteSystemControlGroup();
        }

        String tooltipText = controlGroups.getSystemControlGroupTooltipText();
        Assert.assertEquals(tooltipText, SYSTEMCONTROLGROUPTOOLTIP_TEXT);
        logger.info("<<<verifySystemControlGroupTooltipText test finished");
    }


    @Test(groups = {TestCaseGroups.CONTROLGROUPREGRESSION}, description = "verify text present on custom control tab without any existing system control group",dependsOnMethods = {"verifySystemControlGroupTooltipText"})
    public void verifyTextWithoutSystemGroup() {

        logger.info(">>>verifyTextWithoutSystemGroup test started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        ControlGroupPage controlGroups = new ControlGroupPage(driver);
        controlGroups.switchToTab("Custom control group");
        if (controlGroups.getCustomControlButtonStatus()) {
            String warningMesaageForAddingSystemGroup = controlGroups.getMessageOnCustomControlGroupPage();
            Assert.assertEquals(warningMesaageForAddingSystemGroup, WARNINGTEXTFORADDINGSYSTEMCONTROLGROUP);

        } else {
            Assert.assertTrue(false);
        }

        logger.info("<<<verifyTextWithoutSystemGroup test finished");
    }

    @Test(groups = {TestCaseGroups.CONTROLGROUPREGRESSION}, description = "adding system control group from custom control tab",dependsOnMethods = {"verifyTextWithoutSystemGroup"})
    public void addSystemControlGroupFromCustomTab() {

        logger.info(">>>addSystemControlGroupFromCustomTab test started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        ControlGroupPage controlGroups = new ControlGroupPage(driver);
        controlGroups.switchToTab("Custom control group");
        controlGroups.addSystemControlGroup();
        controlGroups.switchToTab("System control group");
        Assert.assertTrue(controlGroups.verifySystemGroupCreatedByField(),"created by field found");

        logger.info("<<<addSystemControlGroupFromCustomTab test finished");
    }

    @Test(groups = {TestCaseGroups.CONTROLGROUPREGRESSION}, description = "adding custom control group ",dependsOnMethods = {"addSystemControlGroupFromCustomTab"})
    public void addCustomControlGroup() {

        logger.info(">>>addCustomControlGroup test started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        ControlGroupPage controlGroups = new ControlGroupPage(driver);
        controlGroups.switchToTab("Custom control group");
        controlGroups.addCustomControlGroup();
        int records=controlGroups.getNumberOfReconrdsInControlGroupTable();
        if (records==2){
            Assert.assertTrue(true);
        }else {
            Assert.assertTrue(false);
        }

        logger.info("<<<addCustomControlGroup test finished");
    }

    @Test(groups = {TestCaseGroups.CONTROLGROUPREGRESSION}, description = "Adding custom control group with special characters in group name")
    public void addCustomControlGroupWithSpecialCharInName() {
        logger.info("Adding custom control group with special characters in group name test started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        ControlGroupPage controlGroups = new ControlGroupPage(driver);
        controlGroups.switchToTab("Custom control group");
        Assert.assertTrue(controlGroups.addCustomControlGroupWithSpecialCharInName(), "Custom Control group created with special chars in group name");
    }

    @Test(groups = {TestCaseGroups.CONTROLGROUPREGRESSION}, description = "Adding custom control group with special characters in description")
    public void addCustomControlGroupWithSpecialCharInDesc() {
        logger.info("Adding custom control group with special characters in description test started");
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), "");
        ControlGroupPage controlGroups = new ControlGroupPage(driver);
        controlGroups.switchToTab("Custom control group");
        Assert.assertTrue(controlGroups.addCustomControlGroupWithSpecialCharInDesc(), "Custom Control group created with special chars in description");
    }

}
