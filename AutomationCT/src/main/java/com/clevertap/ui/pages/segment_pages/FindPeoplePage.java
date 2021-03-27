package com.clevertap.ui.pages.segment_pages;


import com.clevertap.ui.pages.settings_menu_page.Bookmark;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.FileUtility;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindPeoplePage {


    private static final String LAST_TIME = "last time";
    private static final String EVENT_PROPERTY = "event property";
    private static final String TIME_OF_THE_DAY = "time of the day";
    private static final String DAY_OF_THE_WEEK = "day of the week";
    private static final String DAY_OF_THE_MONTH = "day of the month";
    private static final String LI_TEXT = "]//li[text()='";
    private static final String DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER = "(//*[@data-at-select='profileRow']//div[@class='evtOuter'])[";
    private static final String FIRST_TIME = "first time";
    private static final String AUTOMATION_EVENT = "Automation-Event";
    private static final String OPTION = "option";
    private static final String INNER_TEXT = "innerText";
    public static final boolean BOOLEAN = false;
    private WebDriver driver;
    private SweetAlert sweetAlert;
    private Bookmark bookmark;
    private Logger logger = Logger.getLogger("FindPeoplePage");
    private List<String> actualEventPropertyValues = new ArrayList<>();
    private List<String> actualDayOfWeekValues = new ArrayList<>();
    private List<String> actualFirstTimeValues = new ArrayList<>();
    private List<String> dayOfWeekDataTypeValues = new ArrayList<>(Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"));
    private List<String> eventPropertyDataTypeValues = new ArrayList<>(Arrays.asList("∋   (contains)", "∌   (does not contain)", "=   (equals)", "≠   (not equals)", "∃   (exists)", "∄   (does not exist)"));
    private List<String> firstTimeDataTypesValues = new ArrayList<>(Arrays.asList("Yes"));

    @FindBy(id = "find")
    public WebElement viewDetail;
    @FindBy(id = "saveSegmentList")
    private WebElement saveSegment;
    @FindBy(id = "saveSegmentName")
    private WebElement segmentName;
    @FindBy(className = "confirm")
    private WebElement createBtn;
    @FindBy(className = "confirm")
    private WebElement confirm;
    @FindBy(id = "targetByWebPush")
    public WebElement findPeopleCreateWebPushCampaign;
    @FindBy(className = "startDownloading")
    private WebElement fp_Download;
    @FindBy(id = "id='btn_top_nav_continue")
    private WebElement continueBtn;
    @FindBy(xpath = "//*[contains(@class,'js-pane-container_close')]")
    private WebElement frameCloseBtn;
    @FindBy(xpath = "//*[@id='fbGrid']/span/a")
    private List<WebElement> kundliFaces;
    @FindBy(id = "malePercent")
    private WebElement maleDemographicPercent;
    @FindBy(id = "femalePercent")
    private WebElement femaleDemographicPercent;
    @FindBy(className = "ct-breadcrumb")
    private WebElement breadcumbText;
    @FindBy(xpath = "//*[@title='CleverTap ID']/../span")
    private WebElement cleverTapID;
    @FindBy(xpath = "//*[@placeholder='Email/Identity/CleverTap ID']")
    private WebElement placeHolder;
    @FindBy(xpath = "//*[@placeholder='Email/Identity/CleverTap ID']/..//a")
    private WebElement findPeopleMagnifyingGlass;
    @FindBy(id = "save")
    private WebElement saveSegmentBookmark;
    @FindBy(id = "groupName")
    private WebElement bookmarkTextField;
    @FindBy(id = "saveGroupButton")
    private WebElement saveBookmarkButton;
    @FindBy(xpath = "//*[@id='usersFav']//a")
    private List<WebElement> bookmarkList;
    @FindBy(xpath = "//*[@id='usersFav']//a[text()='Manage']")
    private WebElement manageBookmarkElement;

    @FindBy(xpath = "(//*[@data-at-select='eventMain']//*[@class='evtOuter'])[1]//a/div")
    private WebElement userWhoDidEventDropdown;
    @FindBy(id = "addDidEvent")
    private WebElement userWhoDidEventButton;
    @FindBy(xpath = "(//*[@class='event_widget']//*[@class='filterClass'])[2]")
    private WebElement userWhoDidEventFilter;
    @FindBy(xpath = "//*[@data-at-select='eventFilter']//*[@class='evtOuter'][1]")
    private WebElement userWhoDidEventFilterDropDown;
    @FindBy(xpath = "(//*[@data-at-select='eventFilter']//*[@class='evtOuter'])[3]//a/div")
    private WebElement userWhoDidEventFilterDataTypeDropDown;
    @FindBy(xpath = "(//*[@data-at-select='eventFilter']//*[@class='evtOuter'])[3]//select")
    private WebElement eventPropertyDataTypeDropDown;
    @FindBy(xpath = "(//*[@data-at-select='eventFilter']//*[@class='evtOuter'])[2]//select")
    private WebElement dataTypeDropDown;
    @FindBy(id = "startTime0")
    private WebElement startTime;
    @FindBy(id = "endTime0")
    private WebElement endTime;
    @FindBy(id = "targetByPush")
    public WebElement findPeopleCreateMobilePushCampaign;
    @FindBy(id = "targetBySms")
    public WebElement findPeopleCreateSmsCampaign;
    @FindBy(id = "targetByEmail")
    public WebElement findPeopleCreateEmailCampaign;
    @FindBy(id = "targetByFb")
    public WebElement findPeopleCreateFbCampaign;
    @FindBy(id = "more-emails")
    public WebElement getEmailId;

    public void saveSegmentAsBookmark() {
        SeleniumUtils.performClick(driver, saveSegmentBookmark);

    }

    public void setAsBookmark(String bookmarkName) {
        SeleniumUtils.enterInputText(driver, bookmarkTextField, bookmarkName);
        SeleniumUtils.waitAndClick(driver, saveBookmarkButton);
        sweetAlert.sweetAlertConfirmOK();

    }

    public List<String> getBookmarksList() {
        List<String> bookmarks = new ArrayList<>();
        for (WebElement bookmarkElement : bookmarkList) {
            bookmarks.add(bookmarkElement.getText());
        }
        return bookmarks;
    }

    public void openManageBookmarkPage() {
        SeleniumUtils.waitAndClick(driver, manageBookmarkElement);
    }

    public void deleteBookmark(String bookmarkType, String bookMarkToBeDeleted) {
        openManageBookmarkPage();
        bookmark.selectSpecificBookmarkTab(bookmarkType);
        bookmark.identifyAndDeleteBookmarkInTable(bookMarkToBeDeleted);

    }

    public WebElement returnPlaceHolder() {
        return placeHolder;
    }

    public WebElement returnFindMagnifyingGlass() {
        return findPeopleMagnifyingGlass;
    }


    public String getCleverTapID() {
        return SeleniumUtils.getElementText(driver, cleverTapID);
    }


    public String getMaleDemographicPercent() {
        SeleniumUtils.waitForElementToLoad(driver,maleDemographicPercent);
        return maleDemographicPercent.getText();
    }

    public String getFemaleDemographicPercent() {
        SeleniumUtils.waitForElementToLoad(driver,femaleDemographicPercent);
        return femaleDemographicPercent.getText();
    }


    public void viewDetails() {
        SeleniumUtils.scrollDown(driver);
        SeleniumUtils.waitAndClick(driver, viewDetail);
    }

    public String getSaveSegmentText() {
        return saveSegment.getText();
    }

    public void saveSegments(String segment) throws InterruptedException {
        SeleniumUtils.pause(1);
//        SeleniumUtils.performClick(driver, saveSegment);
        SeleniumUtils.waitAndClick(driver,saveSegment);
        SeleniumUtils.waitForElementToLoad(driver,segmentName);
        SeleniumUtils.pause(1);
        SeleniumUtils.enterInputText(driver, segmentName, segment);
//        SeleniumUtils.waitForElementToLoad(driver,createBtn);
//        SeleniumUtils.performClick(driver, createBtn);
        SeleniumUtils.waitAndClick(driver,createBtn);
        SeleniumUtils.pause(1);
//        SeleniumUtils.waitForElementToLoad(driver,confirm);
//        SeleniumUtils.performClick(driver, confirm);
        SeleniumUtils.waitAndClick(driver,confirm);
    }

    public void verifyCreateCampaign() {
        try {
            SeleniumUtils.performClick(driver, findPeopleCreateWebPushCampaign);
            String currentWindow = driver.getWindowHandle();
            driver.switchTo().frame(0);
            SeleniumUtils.isClickable(continueBtn, driver);
            driver.switchTo().window(currentWindow);
            SeleniumUtils.performClick(driver, frameCloseBtn);
            SeleniumUtils.pause(2);
            driver.switchTo().frame(0);
            SeleniumUtils.elementHighlighter(driver, confirm);
//            confirm.click();
            confirm.sendKeys(Keys.ENTER);
            SeleniumUtils.pause(2);

        } catch (Exception e) {
            logger.error("Something went wrong" + e.getMessage());
        }


    }

    public void deleteExistingCSVFiles() {
        FileUtility.deleteCSVFiles();
    }

    public void startDownload() {
        SeleniumUtils.performClick(driver, fp_Download);
        SeleniumUtils.performClick(driver, confirm);
    }


    public void readCSVFiles() {

        String downloadDir = System.getProperty("user.home") + "/Downloads";
        File[] newCSVFile = FileUtility.fileFinder(downloadDir);
        for (File f : newCSVFile) {
            FileUtility.readDataLineByLine(f);
        }
    }


    public List<String> getTableCellData() {

        List<String> campaignsList = new ArrayList<>();

        try {

            List<WebElement> rows = driver.findElements(By.xpath("//*[@class='CT-table__left']//div[3]"));

            for (WebElement row : rows) {

                campaignsList.add(row.getText());

            }


        } catch (Exception e) {

            List<WebElement> rows = driver.findElements(By.xpath("//*[@class='CT-table__left']//div[3]"));

            for (WebElement row : rows) {

                campaignsList.add(row.getText());

            }

        }


        return campaignsList;

    }

    public String clickFirstKundliFace() {
    // illogical code thats why commenting

//        boolean flag = BOOLEAN;
        String profilePageText = null;
//        for (WebElement kundliFace : kundliFaces) {
//            if (flag == BOOLEAN) {
                SeleniumUtils.waitForElementToClickable(driver,kundliFaces.get(0),10);
                kundliFaces.get(0).click();
//                SeleniumUtils.waitAndClick(driver, kundliFaces.get(0));
//                SeleniumUtils.waitForPageLoaded(driver);
                SeleniumUtils.waitForElementToLoad(driver,breadcumbText,20);
                profilePageText = SeleniumUtils.getElementText(driver, breadcumbText);
//                flag = true;
//            }
//        }
        return profilePageText;
    }

    public String getProfilePageHeader() {
        SeleniumUtils.waitForElementToLoad(driver,breadcumbText);
        return SeleniumUtils.getElementText(driver, breadcumbText);
    }

    public void enterViewDetails() {
        try {
            SeleniumUtils.scrollDown(driver);
            SeleniumUtils.performClick(driver, viewDetail);
        } catch (Exception e) {
            //do nothing
        }

    }

    public void enterCommonPropertyFilters(List<String> eventPropertiesCommonProperty) {

        int size = eventPropertiesCommonProperty.size();

        for (int i = 0; i < size; i++) {

            int j = i + 1;

            SeleniumUtils.scrollDown(driver);

            if (SeleniumUtils.getVisibility(By.id("addAnotherStepFromUI"), 10, driver) != null) {
                SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.id("addAnotherStepFromUI"), 10, driver));

                try {

                    driver.findElement(By.xpath(DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER + j + "]")).click();

                    driver.findElement(By.xpath(DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER + j + LI_TEXT + eventPropertiesCommonProperty.get(i) + "']")).click();

                } catch (Exception e) {

                    driver.findElement(By.xpath(DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER + j + "]")).click();

                    driver.findElement(By.xpath(DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER + j + LI_TEXT + eventPropertiesCommonProperty.get(i) + "']")).click();


                }
            }
        }
    }


    public boolean getKundliFace() {
        SeleniumUtils.scrollDown(driver);
        int numberOfFaces = kundliFaces.size();
        return numberOfFaces >= 1;
    }

    public void selectEventFromUserWhoDid() {
        SeleniumUtils.performClick(driver, userWhoDidEventButton);
        SeleniumUtils.selectElementFromDropDownItem(driver, userWhoDidEventDropdown, AUTOMATION_EVENT);
    }

    public boolean compareEventDataTypeValues(String filterName) {

        List<WebElement> allOptions;
        SeleniumUtils.performClick(driver, userWhoDidEventFilter);
        SeleniumUtils.selectElementFromDropDownItem(driver, userWhoDidEventFilterDropDown, filterName);

        switch (filterName.trim().toLowerCase()) {
            case EVENT_PROPERTY:
                SeleniumUtils.pause(30);
                allOptions = eventPropertyDataTypeDropDown.findElements(By.tagName(OPTION));
                if (allOptions.size() != 6)
                    return BOOLEAN;

                for (WebElement option : allOptions) {
                    actualEventPropertyValues.add(option.getAttribute(INNER_TEXT));
                }

                if (! (eventPropertyDataTypeValues.equals( actualEventPropertyValues )))
                    return BOOLEAN;

                break;

            case FIRST_TIME:
            case LAST_TIME:
                SeleniumUtils.pause(30);
                allOptions = dataTypeDropDown.findElements(By.tagName(OPTION));
                if (allOptions.size() != 1)
                    return BOOLEAN;

                for (WebElement option : allOptions) {
                    actualFirstTimeValues.add(option.getAttribute(INNER_TEXT));

                    if (! (actualFirstTimeValues.equals(firstTimeDataTypesValues)))
                        return BOOLEAN;
                }
                break;

            case TIME_OF_THE_DAY:
                SeleniumUtils.pause(30);
                if (! (startTime.isDisplayed() && endTime.isDisplayed()))
                    return BOOLEAN;
                break;

            case DAY_OF_THE_WEEK:
                SeleniumUtils.pause(30);
                allOptions = dataTypeDropDown.findElements(By.tagName(OPTION));
                if (allOptions.size() != 7)
                    return BOOLEAN;

                for (WebElement option : allOptions) {
                    actualDayOfWeekValues.add(option.getAttribute(INNER_TEXT));
                }

                if (! (dayOfWeekDataTypeValues.equals( actualDayOfWeekValues )))
                    return BOOLEAN;

                break;

            case DAY_OF_THE_MONTH :
                SeleniumUtils.pause(30);
                allOptions = dataTypeDropDown.findElements(By.tagName(OPTION));
                if (allOptions.size() !=31)
                    return BOOLEAN;
                break;

            default:
                logger.info("Not a valid filter name");
        }
        return true;
    }

    public FindPeoplePage(WebDriver driverFromPreviousBrowser) {
        this.driver = driverFromPreviousBrowser;
        PageFactory.initElements(driverFromPreviousBrowser, this);
        sweetAlert = new SweetAlert(driverFromPreviousBrowser);
        bookmark = new Bookmark(driverFromPreviousBrowser);
    }
}

