package com.clevertap.ui.segment_menu;


import com.clevertap.BaseTest;
import com.clevertap.ui.pages.segment_pages.RFM;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

//This code will run only on BR and not on QBR
public class RFMTest extends BaseTest{


    private static final String EVENT_PROPERTY = "Event property";
    private static final String CONTAINS = "(contains)";
    private static final String RFM = "RFM";
    private static final String TRANSITIONS = "Transitions";
    private static final String DISTRIBUTION = "Distribution";
    private static final String REAL_IMPACT = "Real Impact";
    private static final String REAL_IMPACT_ON_RFM_SCORE = "Real Impact on RFM score";
    private static final String ADDED_TO_CART = "Added To Cart";
    private static final String APP_LAUNCHED = "App Launched";
    private static final String SEGMENT_RFM = "Segment\nRFM";
    private static final String PRODUCT_VIEWED = "Product viewed";
    private static final String DEFINITION = "Definition";
    private static final String DELETE = "Delete";
    private static final String QA_AUTOMATION = "QA-Automation";
    private static final String CT_SOURCE = "CT Source";
    private static final String MOBILE = "Mobile";
    private Logger logger;
    private WebDriver driver;

    private final static String bookmarkType = "RFM";
    private final static String tooltipXpath = "//p[@class='chartTooltipLegend']";
    private final static String recenyGraph = "//div[@id='recRFM']//*[name()='rect' and contains(@class,'highcharts-point') and ( (@fill='#009cf2') or (@fill='rgb(0,156,242'))]";
    private final static String frequencyGraph = "//div[@id='freqRFM']//*[name()='rect' and contains(@class,'highcharts-point') and ( (@fill='#009cf2') or (@fill='rgb(0,156,242'))]";
    private final static String monetaryGraph = "//div[@id='moneyRFM']//*[name()='rect' and contains(@class,'highcharts-point') and(@fill='rgb(25,181,255)') or (@fill='rgb(0,156,242)') or (@fill='#009cf2')]";
    private String bookmarkName = null;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), "");
        logger = baseTest.configureLogger(getClass());
    }


    @Test(description = "verify the page header", groups = {TestCaseGroups.ALL, TestCaseGroups.REGRESSION, TestCaseGroups.CRITICAL})
    public void verifyTheHeader() {
        logger.info("open RFM page Test Started");
        Mocha mocha = new Mocha();
        Mocha.forceNavigate = true;
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        String rfmPageHeaderText = rfm.getHeaderText();
        Assert.assertTrue(rfmPageHeaderText.contains(SEGMENT_RFM), "RFM page successfully launched");
        logger.info("Verify the header Of The current page");
    }

    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.CRITICAL, TestCaseGroups.ALL}, description = "verify Event widget and monetary event in Rfm")
    public void openDropdownAndSelectEvent() {
        logger.info("verify Event Widget");
        Mocha mocha = new Mocha();
        Mocha.forceNavigate = true;
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        rfm.eventWidget(APP_LAUNCHED);
        rfm.unCheckOrCheckMonetary();
        SeleniumUtils.pause(2);
        rfm.clickCalculateButton();
        SeleniumUtils.pause(3);
        Assert.assertTrue(rfm.verifyBookmarkEvent());
        logger.info("verify The Events Of The From dropdown without and filter or monetary event");
    }


    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.CRITICAL, TestCaseGroups.ALL}, description = "verify Rfm bookmark creation and deletion")
    public void verifyBookmarkQuery() {
        logger.info("verifyBookmark");
        Mocha mocha = new Mocha();
        Mocha.forceNavigate = true;
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        rfm.eventWidget(PRODUCT_VIEWED);
        SeleniumUtils.pause(2);
        rfm.clickCalculateButton();
        Assert.assertTrue(rfm.verifyBookmarkEvent());
        bookmarkName = Data.randomAlphaNumeric(QA_AUTOMATION, 4);
        rfm.createNewBookmark(bookmarkName);
        SweetAlert sweetAlert = new SweetAlert(driver);
        sweetAlert.getSweetAlertSuccessSignal();
        rfm.openManageBookmarkPage();
        SeleniumUtils.pause(5);
        rfm.switchToSpecificTab(bookmarkType);
        List<String> actualBookmarkList = rfm.getRFMBookmarksList();
        Assert.assertTrue(actualBookmarkList.contains(bookmarkName), "Rfm bookmark created and deleted");
        rfm.deleteBookmark(bookmarkType, bookmarkName);
        logger.info("verify the creation/deletion of Bookmark ");
    }


    @Test(groups = {TestCaseGroups.REGRESSION}, description = "verify added event in summary")
    public void checkTheSummaryForRfm() {
        logger.info("verifyAddedEventInSummary");
        Mocha mocha = new Mocha();
        Mocha.forceNavigate = true;
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        rfm.eventWidget(APP_LAUNCHED);
        Assert.assertEquals(rfm.toCheckEventName(), APP_LAUNCHED);
        rfm.clickCalculateButton();
        logger.info("Verify added event in summary");
    }


    @Test(groups = {TestCaseGroups.REGRESSION}, description = "Add event filters in rfm and verify the Summary ")
    public void addFiltersToEvents() {
        logger.info("verifyEventFiltersInSummary");
        Mocha mocha = new Mocha();
        Mocha.forceNavigate = true;
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        rfm.eventWidget(ADDED_TO_CART);
        rfm.addFilterToEventProperty(EVENT_PROPERTY, CT_SOURCE, CONTAINS, MOBILE);
        Assert.assertTrue(rfm.getFilterData().contains(rfm.checkSummary()));
        rfm.clickCalculateButton();
        logger.info("verify The Events filters in summary");
    }


    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.CRITICAL}, description = "verify rfm results")
    public void verifyRfmResults() {
        logger.info("VerifyRfmResults");
        Mocha mocha = new Mocha();
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        Mocha.forceNavigate = true;
        SeleniumUtils.waitForPageLoaded(driver);
        rfm.clickCalculateButton();
        SeleniumUtils.waitForPageLoaded(driver);
        List<String> buttonsText = new ArrayList<>();
        buttonsText.add(RFM);
        buttonsText.add(TRANSITIONS);
        buttonsText.add(DISTRIBUTION);
        buttonsText.add(REAL_IMPACT);
        List<String> expectedResultsText = rfm.rfmResults();
        Assert.assertTrue(buttonsText.equals(expectedResultsText), "RFM results");
        logger.info("verify rfm results");
    }


    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.CRITICAL}, description = "verify rfm tooltip based on results")
    public void verifyTheGraphOnButtonClick() {
        logger.info("verifyRfmGraphGTooltip");
        Mocha mocha = new Mocha();
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        Mocha.forceNavigate = true;
        SeleniumUtils.waitForPageLoaded(driver);
        rfm.clickCalculateButton();
        rfm.getRfmResultsOnClick(RFM);
        String rfmTitleChart = rfm.getRfmAsResult();
        Assert.assertTrue(rfmTitleChart.equalsIgnoreCase(RFM));
        rfm.getRfmResultsOnClick(TRANSITIONS);
        String transitionTitleChart = rfm.getRfmTransitions();
        Assert.assertTrue(transitionTitleChart.contains(TRANSITIONS));
        rfm.getRfmResultsOnClick(DISTRIBUTION);
        SeleniumUtils.getTooltipText(driver, recenyGraph, tooltipXpath);
        SeleniumUtils.getTooltipText(driver, frequencyGraph, tooltipXpath);
        SeleniumUtils.getTooltipText(driver, monetaryGraph, tooltipXpath);
        rfm.getRfmResultsOnClick(REAL_IMPACT);
        String rfmSpeedoMeterHeader = rfm.getHeaderOfSpeedoMeter();
        Assert.assertTrue(rfmSpeedoMeterHeader.contains(REAL_IMPACT_ON_RFM_SCORE));
        logger.info("verify the rfm results and graph accordingly");
    }

    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.CRITICAL, TestCaseGroups.ALL}, description = "verify  Block of Segment is clickable or not")
    public void verifyBlockTitles() {
        logger.info("verifyBlockOfSegmentIsClickable");
        Mocha mocha = new Mocha();
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        rfm.clickCalculateButton();
        List<String> segmentBlocks = rfm.segmentBlocks();
        for (String segmentBlockName : segmentBlocks) {
            Assert.assertTrue(rfm.verifyEachBlockOfSegment(segmentBlockName));
        }
        logger.info("verified");

    }

    @Test(groups = {TestCaseGroups.REGRESSION, TestCaseGroups.ALL, TestCaseGroups.CRITICAL}, description = "verify segment creation through blocks")
    public void VerifySegmentCreation() throws InterruptedException {
        logger.info("verifySegmentCreationAndDeletion");
        Mocha mocha = new Mocha();
        mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        RFM rfm = new RFM(driver);
        rfm.clickCalculateButton();
        List<String> segmentBlocks = new ArrayList<String>();
        segmentBlocks= rfm.segmentBlocks();
        List<String> segmentDefinition = new ArrayList<>();
        for (String segmentBlockName : segmentBlocks) {
            rfm.actionsOnSavedSegments(segmentBlockName,DELETE);
            boolean segmentCreation = rfm.verifyBlockButton(segmentBlockName);
            Assert.assertTrue(segmentCreation);
            segmentDefinition = rfm.actionsOnSavedSegments(segmentBlockName,DEFINITION);
            Assert.assertEquals(segmentDefinition.get(0), segmentDefinition.get(1), "Queries are not equal");
        }

    }
}