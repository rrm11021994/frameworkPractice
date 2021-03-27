package com.clevertap.ui.pages.segment_pages;


//import com.clevertap.ui.pages.Bookmark;

import com.clevertap.ui.pages.settings_menu_page.Bookmark;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class RFM {
    private static final String SELECT_A_CHANNEL_FOR_THIS_CAMPAIGN = "Select a channel for this campaign";
    private static final String AUTO = "[Auto]";
    private static final String VIEW_DETAILS = "View Details";
    private static final String SAVE_SEGMENT = "Save segment";
    private static final String CREATE_CAMPAIGN = "Create campaign";
    private static final String INNER_HTML = "innerHTML";
    private static final String DELETE = "Delete";
    private WebDriver driver;
    private Bookmark bookmark;
    private SweetAlert sweetAlert;
    private static final String RFMTabs = "//*[contains(@class,'btn active')]/../li[contains(text(),'";
    private static final String eventForRFM = "//*[@id='evtSelection1_chzn']/..//li[text()='";
    private static final String eventData = "//li[contains(@class,'ui-menu-item')]/..//li/a[text()='";
    private static final String eventFilters = "(//*[@data-at-select='eventFilter']//div[contains(@class,'chzn-container chzn-container-single')])";
    private static final String calculateButton = "//a[text()='Calculate']";

    @FindBy(className = "ct-breadcrumb")
    private WebElement pageTitle;
    @FindBy(id = "evtSelection1_chzn")
    private WebElement selectEventFromDropdown;
    @FindBy(xpath = "//*[@id='rfmSummaryParagraph']/..//a")
    private WebElement calculateEvent;
    @FindBy(xpath = "//input[@id='acquiredQueryCheckbox']/..//span")
    private WebElement checkBoxMonetaryEvent;
    @FindBy(id = "rfmSummaryParagraph")
    private WebElement summarybox;
    @FindBy(xpath = "//*[@class='fltrCls' and not(contains(@style,'display: none;'))]/a")
    private WebElement addNewFilterToEvent;
    @FindBy(xpath = "//*[@data-property-type-wrapper]/div")
    private WebElement eventPropertyDropdown;
    @FindBy(xpath = "(//*[@data-at-select='eventFilter']//div[contains(@class,'chzn-container chzn-container-single')])[2]")
    private WebElement osVersionDropdown;
    @FindBy(xpath = "(//*[@data-at-select='eventFilter']//div[contains(@class,'chzn-container chzn-container-single')])[3]")
    private WebElement operatorDropdown;
    @FindBy(xpath = "//*[contains(@class,'btn active')]/../li")
    private List<WebElement> clickRfmResults;
    @FindBy(xpath = "//input[contains(@class,'span4')]")
    private WebElement inputDropdown;
    @FindBy(xpath = "//*[@id='rfmSummaryParagraph']/div[4]")
    private WebElement eventNameSummary;
    @FindBy(xpath = "//*[@id='evtSelection1_chzn']//span")
    private WebElement eventText;
    @FindBy(xpath = "//*[@class='grid-title']//span[text()='RFM']")
    private WebElement rfmBlocks;
    @FindBy(xpath = "//*[@class='ctChart__title' and text()='Transitions']")
    private WebElement transitions;
    @FindBy(className = "speedometerHeader")
    private WebElement speedoMeterHeader;
    @FindBy(id = "eventWidget")
    private WebElement eventWidgetRFM;
    @FindBy(id = "saveEvent")
    private WebElement bookmarkEvent;
    @FindBy(xpath = "//*[@data-at-select='eventFilter']")
    private WebElement data;
    @FindBy(className = "is-clickable")
    private List<WebElement> blockOfSegments;
    @FindBy(id = "bkName")
    private WebElement nameBookMark;
    @FindBy(xpath = "//*[contains(@class,'btn') and text()='Save']")
    private WebElement saveBookmark;
    @FindBy(className = "confirm")
    private WebElement createSegButton;
    @FindBy(className = "rfm-ghost-btn")
    private List<WebElement> checkQuery;
    @FindBy(id = "RFMSegmentName")
    private WebElement saveSegmentQuery;
    @FindBy(className = "rfm-close-btn js-close-overlay")
    private List<WebElement> closeButton;
    @FindBy(className = "rfm-detail-heading rfm-detail-heading--main")
    private List<WebElement> segmentHeader;
    @FindBy(className = "rfm-label")
    private List<WebElement> blocks;
    @FindBy(xpath = "//*[@id='usersFav']//a")
    private List<WebElement> bookmarkList;
    @FindBy(xpath = "//*[@id='rfmFav']//a[text()='Manage']")
    private WebElement manageBookmark;
    @FindBy(xpath = "//*[contains(@class,'rfm-type-details')]")
    private List<WebElement> inspectTheSegment;
    @FindBy(className = "rfm-ghost-btn")
    private List<WebElement> buttonToInspect;
    @FindBy(xpath = "//*[contains(@class,'rfm-cta-container')]")
    private WebElement blackSegmentBlock;
    @FindBy(xpath = "//span[contains(@class,'rfm-detail-heading')]")
    private List<WebElement> blockHeading;
    @FindBy(id = "saveSegmentList")
    private WebElement saveQuerySegment;
    @FindBy(id = "saveSegmentName")
    private WebElement saveQuerySegmentName;
    @FindBy(id = "searchDiv")
    private WebElement searchSegment;
    @FindBy(id = "saveRFMSegmentButton")
    private WebElement saveSegmentButton;
    @FindBy(xpath = "//img[@class='pane-container_close js-pane-container_close']")
    private WebElement crossBtn;
    @FindBy(xpath = "//*[@data-original-title='Show segment actions']")
    private WebElement segmentAction;
    @FindBy(xpath = "//*[@class='CT-table__left']/div")
    private List<WebElement> segmentTable;
    @FindBy(xpath = "//*[contains(@class,'segment-details__query')]")
    private WebElement segmentQuery;
    @FindBy(xpath = "//a[text()='Calculate']")
    private WebElement calculateBtn;
    @FindBy(xpath = "//*[@id='rfm-bookmark-table']//td[contains(@class,'bookmarkCol2')]/a")
    private List<WebElement> rfmBookmarkList;
    @FindBy(id = "sel5CF_chzn")
    private WebElement eventType;


    public boolean verifyBookmarkEvent() {
        SeleniumUtils.waitForElementToLoad(driver, bookmarkEvent);
        if (bookmarkEvent.isDisplayed()) {
            SeleniumUtils.elementHighlighter(driver, bookmarkEvent);
            return true;
        }
        return false;
    }

    public String getHeaderText() {
        return SeleniumUtils.getElementText(driver, pageTitle);

    }

    public List<String> getBookmarksList() {
        List<String> bookmarks = new ArrayList<>();
        for (WebElement bookmarkElement : bookmarkList) {
            bookmarks.add(bookmarkElement.getAttribute(INNER_HTML));
        }
        return bookmarks;
    }

    public List<String> getRFMBookmarksList() {
        List<String> rfmBookmarks = new ArrayList<>();
        for (WebElement bookmarkElement : rfmBookmarkList) {
            rfmBookmarks.add(bookmarkElement.getAttribute(INNER_HTML));
        }
        return rfmBookmarks;
    }

    public void switchToSpecificTab(String bookmarkType) {
        bookmark.selectSpecificBookmarkTab(bookmarkType);
    }

    public void deleteBookmark(String bookmarkType, String bookMarkToBeDeleted) {
        bookmark.selectSpecificBookmarkTab(bookmarkType);
        bookmark.identifyAndDeleteBookmarkInTable(bookMarkToBeDeleted);

    }

    public List<String> rfmResults() {
        List<String> buttonsList = new ArrayList<>();
        for (WebElement buttons : clickRfmResults) {
            buttonsList.add(buttons.getText());
        }
        return buttonsList;
    }

    public void openManageBookmarkPage() {
        try {
            SeleniumUtils.performClick(driver, manageBookmark);
        } catch (StaleElementReferenceException e) {
            SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@id='rfmFav']//a[text()='Manage']")));
        }

    }

    public void createNewBookmark(String bookmarkName) {
        SeleniumUtils.performClick(driver, bookmarkEvent);
        SeleniumUtils.enterInputText(driver, nameBookMark, bookmarkName);
        SeleniumUtils.performClick(driver, saveBookmark);
    }

    public String getFilterData() {
        return SeleniumUtils.getElementText(driver, data);
    }


    private WebElement rfmTabs(String nameOfButton) {
        String buttons = RFMTabs + nameOfButton + "')]";
        return driver.findElement(By.xpath(buttons));

    }

    public void getRfmResultsOnClick(String resultClick) {
        WebElement rfm = rfmTabs(resultClick);
        SeleniumUtils.performClick(driver, rfm);
    }

    public String getRfmAsResult() {
        return SeleniumUtils.getElementText(driver, rfmBlocks);
    }

    public String getRfmTransitions() {
        return SeleniumUtils.getElementText(driver, transitions);
    }


    public String getHeaderOfSpeedoMeter() {
        return SeleniumUtils.getElementText(driver, speedoMeterHeader);
    }

    public void eventWidget(String eventName) {
        selectEventFromDropdown.click();
        driver.findElement(By.xpath(eventForRFM + eventName + "']")).click();
    }

    public void addFilterToEventProperty(String selectEventProperty, String selectOsVersion, String operators, String eventDataValue) {
        SeleniumUtils.performClick(driver, addNewFilterToEvent);
        eventPropertyDropdown.click();
        driver.findElement(By.xpath(eventFilters + "[1]//li[text()='" + selectEventProperty + "']")).click();
        osVersionDropdown.click();
        driver.findElement(By.xpath(eventFilters + "[2]//li[text()='" + selectOsVersion + "']")).click();
        SeleniumUtils.pause(3);
        operatorDropdown.click();
        driver.findElement(By.xpath(eventFilters + "[3]//li[contains(text(),'" + operators + "')]")).click();
        inputDropdown.click();
        driver.findElement(By.xpath(eventData + eventDataValue + "']")).click();
    }


    public List<String> segmentBlocks() {
        List<String> blocksOfSegment = new ArrayList<>();
        for (WebElement segment : blocks) {
            blocksOfSegment.add(segment.getAttribute("innerText"));
        }
        return blocksOfSegment;

    }


    public boolean verifyEachBlockOfSegment(String blockSegmentTitle) {
        WebElement segmentTitle = driver.findElement(By.xpath("//*[contains(@class,'rfm-label') and text()='" + blockSegmentTitle + "']"));
        WebElement blocksTitle = driver.findElement(By.xpath("//span[contains(@class,'rfm-detail-heading') and text()='" + blockSegmentTitle + "']"));
        SeleniumUtils.performClick(driver, segmentTitle);
        if (SeleniumUtils.getVisibility(By.xpath("//span[contains(@class,'rfm-detail-heading') and text()='" + blockSegmentTitle + "']"), 3, driver) != null) {
            SeleniumUtils.elementHighlighter(driver, blocksTitle);
        }
        Assert.assertEquals(blockSegmentTitle, SeleniumUtils.getElementText(driver, blocksTitle));
        return true;
    }



    public boolean verifyBlockButton(String labelName) throws InterruptedException {
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), "");
        SeleniumUtils.pause(2);
        calculateBtn.click();
        WebElement segmentTitle = driver.findElement(By.xpath("//*[contains(@class,'rfm-label') and text()='" + labelName + "']"));
        SeleniumUtils.performClick(driver, segmentTitle);
        SeleniumUtils.scrollDown(driver);
        if (SeleniumUtils.getVisibility(By.xpath("//*[@data-label='" + labelName + "']//div[@class='rfm-cta-container']//a"), 5, driver) != null) {
            int buttonElement = driver.findElements(By.xpath("//*[@data-label='" + labelName + "']//div[@class='rfm-cta-container']//a")).size();
            for (int rfmButtons = 1; rfmButtons <= buttonElement; rfmButtons++) {
                WebElement buttons = driver.findElement(By.xpath("(//*[@data-label='" + labelName + "']//div[@class='rfm-cta-container']//a)[" + rfmButtons + "]"));
                if (buttons.getText().contains(VIEW_DETAILS)) {
                    SeleniumUtils.performClick(driver, buttons);
                    Set<String> handles = driver.getWindowHandles();
                    driver.switchTo().window(handles.toArray()[1].toString());
                    Thread.sleep(1000);
                    SeleniumUtils.scrollDown(driver);
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", saveQuerySegment);
                    SeleniumUtils.enterInputText(driver, saveQuerySegmentName, AUTO + labelName + VIEW_DETAILS);
                    SeleniumUtils.pause(2);
                    createSegButton.click();
                    SeleniumUtils.pause(2);
                    sweetAlert.getSweetAlertSuccessSignal();
                    SeleniumUtils.pause(1);
                    driver.switchTo().window(handles.toArray()[1].toString()).close();
                    SeleniumUtils.pause(2);
                    driver.switchTo().window(handles.toArray()[0].toString());
                }
                if (buttons.getText().contains(SAVE_SEGMENT)) {
                    SeleniumUtils.performClick(driver, buttons);
                    SeleniumUtils.enterInputText(driver, saveSegmentQuery, AUTO + labelName + SAVE_SEGMENT);
                    saveSegmentButton.click();
                    SeleniumUtils.pause(4);
                    sweetAlert.getSweetAlertSuccessSignal();
                    SeleniumUtils.performClick(driver, segmentTitle);
                }
                if (buttons.getText().contains(CREATE_CAMPAIGN)) {
                    SeleniumUtils.performClick(driver, buttons);
                    driver.switchTo().frame(0);
                    String campaignHeader = driver.findElement(By.xpath("//*[@class='campaign-page-title TAL']")).getAttribute(INNER_HTML);
                    Assert.assertTrue(campaignHeader.contains(SELECT_A_CHANNEL_FOR_THIS_CAMPAIGN), "RFM create campaign page successfully launched");
                    SeleniumUtils.pause(2);
                    clickClose();
                    Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.RFM.toString(), "");
                    SeleniumUtils.pause(4);
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", driver.findElement(By.xpath(calculateButton)));
                }
            }
            return true;
        }
        return false;
    }



    public List<String> actionsOnSavedSegments(String labelName, String actionOnSegment) {
        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SEGMENT.toString(), NavigateCTMenuEnums.Submenu.SEGMENTS.toString(), "");
        SeleniumUtils.enterInputText(driver, driver.findElement(By.id("searchDiv")), Keys.chord(AUTO + labelName + Keys.RETURN));
        List<String> segmentList = new ArrayList<>();
        if (driver.findElements(By.xpath("//*[@class='CT-table__left']/div")).size() > 1) {
            segmentList = getSegmentList();
        }
        List<String> segmentDefinition = new ArrayList<>();
        int segmentsCount = segmentList.size();
        for (int i = 0; i < segmentsCount; i++) {
            try {
                SeleniumUtils.enterInputText(driver, searchSegment, Keys.chord(AUTO + labelName + Keys.RETURN));
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[contains(@class,'CT-table__header--name')]//a[text()='" + segmentList.get(i) + "']/../..//div[@data-original-title='Show segment actions']")));
                SeleniumUtils.pause(3);
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@data-original-title='" + actionOnSegment + "']")));

                SeleniumUtils.waitForPageLoaded(driver);
                if (actionOnSegment.equalsIgnoreCase(DELETE)) {
                    sweetAlert.getSweetAlertWarningSignal();
                } else {
                    segmentDefinition.add(SeleniumUtils.getElementText(driver, segmentQuery));
                }
            } catch (Exception e) {
                SeleniumUtils.waitForPageLoaded(driver);
                SeleniumUtils.enterInputText(driver, driver.findElement(By.id("searchDiv")), Keys.chord(AUTO + labelName + Keys.RETURN));
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[contains(@class,'CT-table__header--name')]//a[text()='" + segmentList.get(i) + "']/../..//div[@data-original-title='Show segment actions']")));
                SeleniumUtils.pause(3);
                SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@data-original-title='" + actionOnSegment + "']")));
                SeleniumUtils.waitForPageLoaded(driver);
                sweetAlert.getSweetAlertWarningSignal();
                if (actionOnSegment.equalsIgnoreCase(DELETE)) {
                    SeleniumUtils.pause(2);
                    sweetAlert.getSweetAlertWarningSignal();
                } else {
                    segmentDefinition.add(SeleniumUtils.getElementText(driver, segmentQuery));
                }
            }
        }
        List<WebElement> li = driver.findElements(By.xpath("//*[@class='CT-table__left']"));
        Assert.assertEquals(1, li.size());
        return segmentDefinition;
    }

    private void clickClose() throws InterruptedException {
        driver.switchTo().defaultContent();
        crossBtn.click();
        Thread.sleep(2000);
        driver.switchTo().frame(0);
        Thread.sleep(2000);
        driver.switchTo().defaultContent();
    }


    public void clickCalculateButton() {
        SeleniumUtils.performClick(driver, calculateBtn);
    }

    public void unCheckOrCheckMonetary() {
        SeleniumUtils.performClick(driver, checkBoxMonetaryEvent);
    }

    public String checkSummary() {
        WebElement summaryEle=null;
        try{
            return SeleniumUtils.getElementText(driver, summarybox);
        }catch(StaleElementReferenceException stale){
            summaryEle = driver.findElement(By.xpath("//*[@id='rfmSummaryParagraph']//div[4]"));
        }
        return SeleniumUtils.getElementText(driver, summaryEle);
    }


    public String toCheckEventName() {
        return SeleniumUtils.getElementText(driver, eventText);
    }


    private List<String> getSegmentList() {
        List<String> segmentList = new ArrayList<>();
        List<WebElement> rows = driver.findElements(By.xpath("//*[@class='CT-table__left']//div[3]//a"));
        for (WebElement seg : rows) {
            segmentList.add(seg.getText());
        }
        return segmentList;
    }


    public RFM(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
        sweetAlert = new SweetAlert(previousBrowserDriver);
        bookmark = new Bookmark(previousBrowserDriver);
    }

}




