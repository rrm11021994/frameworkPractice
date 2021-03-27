package com.clevertap.ui.campaign_tests.mobile_push;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.campaigns_page.CampaignsHomePage;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.clevertap.utils.LoadYamlFile.campaignMeta;

public class SingleAction extends BaseTest {

    private WebDriver driver;
    private SweetAlert sweetAlert;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.INTEGRATION.toString(), NavigateCTMenuEnums.SuperSubMenu.PUSH_NOTIFICATIONS.toString());
        if (driver.findElement(By.id("android-o-settings-check")).isSelected()){
            driver.findElement(By.xpath("//*[@for='android-o-settings-check']")).click();
            driver.findElement(By.xpath("//*[@class='android-o-settings']/..//input[@type='submit']")).click();
            SweetAlert sweetAlert=new SweetAlert(driver);
            sweetAlert.sweetAlertConfirmOK();
        }
        Mocha.openLeftNavMenu(driver,false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        sweetAlert = new SweetAlert(driver);
    }

    @Test(dataProvider = "campaignParametersProvider",groups = {TestCaseGroups.SINGLEACTIONCAMPAIGN})
    public void mobilePushSingleAction(String csvKey,String channel,String campaignType,String campaignStartType,String campaignEndType,
                                       String campaignDelayType,String campaignDelayBy, String segmentSelectionType,String segmentActionType,String osType, String deviceType, String campaignReachTo, String systemControlGroup,String messageToBeSelected
                                        ,String changeCampaignStart,String changeCampaignEndType,String changeCampaignDelay,String changeCampaignDelayBy,String changeSegmentSelectionType,String changeOsType,String changeDeviceType
                                        ,String changeCampaignReachTo,String changeControlGroup) throws InterruptedException, ParseException {

        campaignMeta.setCurrentRunningTestCaseName("mobilePushSingleAction");
        campaignMeta.setCsv_key(csvKey);
        campaignMeta.setChannel(channel);
        campaignMeta.setType(campaignType);
        //get when details
        campaignMeta.getWhen().setCampaign_start_type(campaignStartType);
        campaignMeta.getWhen().setCampaign_end_type(campaignEndType);
        campaignMeta.getWhen().setCampaign_delay(campaignDelayType);
        campaignMeta.getWhen().setCampaign_delay_by(campaignDelayBy);
        //get who details
        campaignMeta.getWho().setSegment_select_type(segmentSelectionType);
        campaignMeta.getWho().setSaved_segment_type(segmentActionType);
        campaignMeta.getWho().setDevice_type(deviceType);
        campaignMeta.getWho().setDevice_os(osType);
        campaignMeta.getWho().setCampaign_reach_type(campaignReachTo);
        //get what details
        campaignMeta.getWhat().setMessage_to_be_selected(messageToBeSelected);
        Map<String, String> controlGroupMap = new HashMap<String, String>();
        controlGroupMap.put("system control group", systemControlGroup);
        controlGroupMap.put("custom control group", "Custom Control Group");

        campaignMeta.getSetup().setControl_group(controlGroupMap);

        //clone campaign
        campaignMeta.getWhen().setChange_campaign_startType(changeCampaignStart);
        campaignMeta.getWhen().setChange_delay_type(changeCampaignDelay);
        campaignMeta.getWhen().setChange_campaign_endType(changeCampaignEndType);
        campaignMeta.getWhen().setCampaign_start_date(LocalDate.now().plusDays(1).toString().replace("-", ""));
        campaignMeta.getWhen().setChange_campaign_delay_by(changeCampaignDelayBy);
        campaignMeta.getWho().setChange_segment_type(changeSegmentSelectionType);
        campaignMeta.getWho().setChange_os_type(changeOsType);
        campaignMeta.getWho().setChange_device_type(changeDeviceType);
        campaignMeta.getWho().setChange_campaign_reachToType(changeCampaignReachTo);
        campaignMeta.getWho().setChange_os_type(changeOsType);


        Map<String, String> changeControlGroupMap = new HashMap<String, String>();
        changeControlGroupMap.put("change system control group", changeControlGroup);
        changeControlGroupMap.put(" change custom control group", "Custom Control Group");
        campaignMeta.getSetup().setChange_control_group(changeControlGroupMap);


        CampaignsHomePage campaignsHomePage = new CampaignsHomePage(driver);
        String campaignName = campaignsHomePage.createCampaign();
        campaignsHomePage.cloneCampaign(campaignName);




    }

    @DataProvider(name = "campaignParametersProvider")
    public Object[][] campaignParameters(Method method) {
        if (method.getName().equalsIgnoreCase("mobilePushSingleAction")) {
            return new String[][]{
                    {"mobile push+single action-test1","mobile push","single action","now","Never end", "no delay","", "+ create an ad-hoc segment","","ios", "all device type","all users", "apply", "singleMessage",
                            "later","select date and time", "delay","1", "change saved segment query", "android", "tablet", "only x users overall", "apply"},
                    {"mobile push+single action-test2","mobile push","single action","now","Never end","delay","1","Saved Segments","Single action","android","all device type","limit users","apply","singleMessage",
                              "later","Select date and time","no delay","","change saved segment query","ios","TV","all users","remove"},
                    {"mobile push+single action-test3","mobile push","single action","now","select date and time","no delay","","+ create an ad-hoc segment","","","all device type","only x users overall","remove","singleMessage",
                            "later","Never End","delay","1","change saved segment query","android","mobile","all users","apply"}
            };
        }else {
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
                Reporter.log(e.getMessage());
            }
        }
    }


}
