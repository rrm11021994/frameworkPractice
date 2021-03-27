package com.clevertap.ui.segment_menu;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.segment_pages.Segments;
import com.clevertap.ui.pages.widget.DidNotWidget;
import com.clevertap.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class SegmentsTest extends BaseTest{
    private static final String SEGMENTS_OVERVIEW = "Segments\nOverview";
    private static final String SEGMENTS_CREATE_NEW = "Segments\nCreate New";
    private static final String ACTIONS = "Actions";
    private static final String PRODUCT_RATED = "Product Viewed";
    private String segmentName = "";
    private WebDriver driver;
    private static final String PASSED = "Passed";
    private Segments segments;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        driver = getDriver();
        segments = new Segments(driver);
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), "");
    }


    @Test(groups = {TestCaseGroups.REGRESSION}, description = "verify segment page header")
    public void verifySegmentPageHeader() {
        Reporter.log("verify Segment page header test Started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        String segmentPageHeaderText = segments.getHeaderText();
        Assert.assertTrue(segmentPageHeaderText.contains(SEGMENTS_OVERVIEW), "Segments page successfully launched");
        Reporter.log("verify segment page header test Finished",true);
    }

    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.SEGMENTSCRITICAL}, description = "verify segment creation  button", dependsOnMethods = {"verifySegmentPageHeader"})
    public void verifySegmentCreationButton() {
        Reporter.log("verify segment creation Button test Started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        segments.createSegment();
        String segmentPageHeaderText = segments.getHeaderText();
        Assert.assertTrue(segmentPageHeaderText.contains(SEGMENTS_CREATE_NEW), "Segments page successfully launched");
        Reporter.log("verify segment creation Button test Finished",true);
    }


    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.SEGMENTSCRITICAL}, description = "verify segments types test Started", dependsOnMethods = {"verifySegmentCreationButton"})
    public void verifySegmentsType() {
        Reporter.log("verify all segment Types test Started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        List<String> segmentsName = segments.getSegmentsNameList();
        for (String seg : segmentsName) {
            boolean verifySegmentClickable = segments.verifyEachSegment(seg.trim());
            Assert.assertTrue(verifySegmentClickable);
        }
        Reporter.log("verify all segment Types test Finished",true);
    }


    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.SEGMENTSCRITICAL}, description = "verify segment creation  button test Started", dependsOnMethods = {"verifySegmentsType"})
    public void segmentCreationWithFilters() {
        Reporter.log("verify segment creation  test Started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        segments.clickSegment(ACTIONS);
        List<String> didNotWidgetFilterItems = new ArrayList<>();
        didNotWidgetFilterItems.add(PRODUCT_RATED);
        segmentName = Data.randomAlphaNumeric("Test-Segment-Automation", 3);
        segments.saveSegment(segmentName);
        Reporter.log("verify segment creation Button test Finished",true);
    }


    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.SEGMENTSCRITICAL}, description = "verify segment creation  button test Started", dependsOnMethods = {"segmentCreationWithFilters"})
    public void searchNewCreatedSegment() {
        Reporter.log("verify segment creation  test Started",true);
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        segments.sendTextToSearchBox(segmentName);
        List<String> segmentList = TableUtility.getDataFromRightTable("//*[@class='CT-table__left']", 3, driver);
        Assert.assertTrue(segmentList.contains(segmentName), "New Segment is created");
        Reporter.log("verify segment creation Button test Finished",true);
    }
}
