package com.clevertap.ui.pages.segment_page;

import com.clevertap.utils.Calendar;
import com.clevertap.utils.FileUtility;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FindPeoplePage {

    public static final String PX = "px\" })";
    WebDriver driver;
    private static String[] eventWhereAggrArray = {"Count", "Property sum of"};
    Logger logger = Logger.getLogger(getClass().getSimpleName());

    @FindBy(id = "find")
    private WebElement viewDetail;
    @FindBy(id = "saveSegmentList")
    private WebElement saveSegment;
    @FindBy(id = "saveSegmentName")
    private WebElement segmentName;
    @FindBy(className = "confirm")
    private WebElement createBtn;
    @FindBy(className = "confirm")
    private WebElement confirm;
    @FindBy(id = "targetByWebPush")
    private WebElement fpCreateCampaign;
    @FindBy(className = "startDownloading")
    private WebElement fpDownload;
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
    @FindBy(xpath = "//*[@id='FEQuery']//span[text()='Charged']")
    private WebElement feQueryDidEvent;
    @FindBy(xpath = "//*[@id='didntClauseDiv']//span[text()='Charged']")
    private WebElement didClauseEvent;
    @FindBy(className = "ct-breadcrumb")
    private WebElement breadcumbText;
    @FindBy(xpath = "//*[@title='CleverTap ID']/../span")
    private WebElement cleverTapID;
    @FindBy(xpath = "//*[@placeholder='Email/Identity/CleverTap ID']")
    public WebElement placeHolder;
    @FindBy(xpath = "//*[@placeholder='Email/Identity/CleverTap ID']/..//a")
    private WebElement findPeopleMagnifyingGlass;
    @FindBy(xpath = "//*[@data-at-select='likeDominantType']")
    private WebElement whereItOccurs;
    @FindBy(xpath = "//*[@data-at-select='likeSignificantVal']")
    private WebElement significantValue;
    @FindBy(xpath = "//*[@data-at-select='likeEventProp']")
    private WebElement likeEventProp;
    @FindBy(xpath = "//*[@data-at-select='likeEventOperator']")
    private WebElement likeEventOperator;
    @FindBy(className = "ui-autocomplete-input")
    private WebElement propValue;
    @FindBy(xpath = "//*[@data-at-select='likeFromHour']")
    private WebElement likeFromHour;


    public void selectEventWhereAggr(int index, int eventWhereIndex) {
        try {
            driver.findElement(By.xpath("(//*[@data-at-select='eventWhereAggr'])[" + index + "]")).click();
            SeleniumUtils.pause(1);
            WebElement element = driver.findElement(By.xpath("(//*[@data-at-select='eventWhereAggr'])[" + index + "]//li[text()='" + eventWhereAggrArray[eventWhereIndex] + "']"));
            System.out.println("(//*[@data-at-select='eventWhereAggr'])[" + index + "]//li[text()='" + eventWhereAggrArray[eventWhereIndex] + "']");
            if (element != null) {
                element.click();
                SeleniumUtils.pause(1);
            }
        } catch (Exception e) {
            driver.findElement(By.xpath("(//*[@data-at-select='eventWhereAggr'])[" + index + "]")).click();
            SeleniumUtils.pause(1);
            WebElement element = driver.findElement(By.xpath("(//*[@data-at-select='eventWhereAggr'])[" + index + "]//li[text()='" + eventWhereAggrArray[eventWhereIndex] + "']"));
            System.out.println("(//*[@data-at-select='eventWhereAggr'])[" + index + "]//li[text()='" + eventWhereAggrArray[eventWhereIndex] + "']");
            if (element != null) {
                element.click();
                SeleniumUtils.pause(1);
            }
        }

    }

    public void selectEvenWhereOperator(int index) {
        driver.findElement(By.xpath("(//*[@data-at-select='eventWhereOperator'])[" + index + "]")).click();
        WebElement element = driver.findElement(By.xpath("(//*[@data-at-select='eventWhereOperator'])[" + index + "]//li[contains(text(),'equals')]"));
        if (element != null) {
            element.click();
        }
    }

    public void enterEventWhereData(int index) {
        WebElement element = driver.findElement(By.xpath("(//input[@placeholder='Excludes'])[" + index + "]"));
        element.click();
        element.clear();
        element.sendKeys("5");


    }

    public void selectEventWhereProp(int index) {
        driver.findElement(By.xpath("(//*[@data-at-select='eventWherePropContainer'])[" + index + "]")).click();
        SeleniumUtils.pause(1);
        WebElement element = driver.findElement(By.xpath("(//*[@data-at-select='eventWherePropContainer'])[" + index + "]//li[contains(text(),'device_type')]"));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
        }

    }


    public void selectLikeFromHour(int index)  {
        driver.findElement(By.xpath("(//*[@data-at-select='likeFromHour'])[" + index + "]")).click();
        SeleniumUtils.pause(1);
        WebElement element = driver.findElement(By.xpath("(//*[@data-at-select='likeFromHour'])[" + index + "]//li[text()='1 AM ']"));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
        }

    }

    public void selectLikeToHour(int index) {

        driver.findElement(By.xpath("(//*[@data-at-select='likeToHour'])[" + index + "]")).click();
        SeleniumUtils.pause(1);
        WebElement element = driver.findElement(By.xpath("(//*[@data-at-select='likeToHour'])[" + index + "]//li[text()='4 AM ']"));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
        }
    }

    public void selectSomeOptions(int index) {

        driver.findElement(By.xpath("(//*[@data-at-select='likeEventWeekday'])[" + index + "]")).click();
        SeleniumUtils.pause(1);
        WebElement element = driver.findElement(By.xpath("(//*[@data-at-select='likeEventWeekday'])[" + index + "]//li[text()='Monday']"));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
        }

    }


    public void selectDominantType(String dominantType, int i) {
        driver.findElement(By.xpath("(//*[@data-at-select='likeDominantType'])[" + i + "]")).click();
        SeleniumUtils.pause(1);
        String xpath = "(//*[@data-at-select='likeDominantType'])[" + i + "]//li[text()='" + dominantType + "']";
        WebElement element = driver.findElement(By.xpath(xpath));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
        }
    }

    public void selectSignificantValue(int significantValues, int i)  {
        driver.findElement(By.xpath("(//*[@data-at-select='likeSignificantVal'])[" + i + "]")).click();
        SeleniumUtils.pause(1);
        String xpath = "(//*[@data-at-select='likeSignificantVal'])[" + i + "]//li[text()='" + significantValues + "']";
        WebElement element = driver.findElement(By.xpath(xpath));
        if (element != null) {
            element.click();
        }
    }

    public void selectEventProperties(String eventProp, int i)  {
        driver.findElement(By.xpath("(//*[@data-at-select='likeEventProp'])[" + i + "]")).click();
        SeleniumUtils.pause(1);
        String xpath = "(//*[@data-at-select='likeEventProp'])[" + i + "]//li[text()='" + eventProp + "']";
        WebElement element = driver.findElement(By.xpath(xpath));
        if (element != null) {
            element.click();
        }
    }

    public void selectEventOperator(String operatorName, int i) {
        driver.findElement(By.xpath("(//*[@data-at-select='likeEventOperator'])[" + i + "]")).click();
        SeleniumUtils.pause(1);
        String xpath = "(//*[@data-at-select='likeEventOperator'])[" + i + "]//li[text()='" + operatorName + "']";
        WebElement element = driver.findElement(By.xpath(xpath));
        if (element != null) {
            element.click();
        }
    }

    public void enterPropValue(String value, int i) {
        WebElement element = driver.findElement(By.xpath("(//*[@class='ui-autocomplete-input'])[" + i + "]"));
        if (element != null) {
            element.click();
            SeleniumUtils.pause(1);
            element.sendKeys(value);
        }
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
        return maleDemographicPercent.getText();
    }

    public String getFemaleDemographicPercent() {
        return femaleDemographicPercent.getText();
    }


    public void viewDetails() {
        SeleniumUtils.scrollDown(driver);
        SeleniumUtils.performClick(driver, viewDetail);
    }

    public String getSaveSegmentText() {
        return saveSegment.getText();
    }

    public void saveSegments(String segment) {
        SeleniumUtils.performClick(driver, saveSegment);
        SeleniumUtils.pause(1);
        SeleniumUtils.enterInputText(driver, segmentName, segment);
        SeleniumUtils.pause(1);
        SeleniumUtils.performClick(driver, createBtn);
        SeleniumUtils.pause(1);
        SeleniumUtils.performClick(driver, confirm);
    }

    public void verifyCreateCampaign() {
        SeleniumUtils.performClick(driver, fpCreateCampaign);
        String currentWindow = driver.getWindowHandle();
        driver.switchTo().frame(0);
        SeleniumUtils.isClickable(continueBtn, driver);
        driver.switchTo().window(currentWindow);
        SeleniumUtils.performClick(driver, frameCloseBtn);

        driver.switchTo().frame(0);
        SeleniumUtils.pause(1);
        SeleniumUtils.elementHighlighter(driver, confirm);
        SeleniumUtils.pause(1);
        confirm.click();


    }

    public void deleteExistingCSVFiles() {
        FileUtility.deleteCSVFiles();
    }

    public void startDownload() {
        SeleniumUtils.performClick(driver, fpDownload);
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
        boolean flag = false;
        String profilePageText = null;
        for (WebElement kundliFace : kundliFaces) {
            if (flag == false) {
                SeleniumUtils.performClick(driver, kundliFace);
                profilePageText = SeleniumUtils.getElementText(driver, breadcumbText);
                flag = true;
            }
        }
        return profilePageText;
    }

    public String getProfilePageHeader() {
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

                    driver.findElement(By.xpath("(//*[@data-at-select='profileRow']//div[@class='evtOuter'])[" + j + "]")).click();

                    driver.findElement(By.xpath("(//*[@data-at-select='profileRow']//div[@class='evtOuter'])[" + j + "]//li[text()='" + eventPropertiesCommonProperty.get(i) + "']")).click();

                } catch (Exception e) {

                    driver.findElement(By.xpath("(//*[@data-at-select='profileRow']//div[@class='evtOuter'])[" + j + "]")).click();

                    driver.findElement(By.xpath("(//*[@data-at-select='profileRow']//div[@class='evtOuter'])[" + j + "]//li[text()='" + eventPropertiesCommonProperty.get(i) + "']")).click();


                }
            }
        }
    }

    public void enterUserWhoDidFilters(List<String> eventProperties3) {

        int size = eventProperties3.size();

        for (int i = 0; i < size; i++) {

            int j = i + 1;

            if (SeleniumUtils.getVisibility(By.id("addDidEvent"), 10, driver) != null) {
                SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.id("addDidEvent"), 10, driver));
            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("$(\"FEQuery\").animate({ scrollTop: \"" + 100 + PX);
            }


            try {
                driver.findElement(By.xpath("(//*[@data-at-select='eventMain']/div[@class='evtOuter'])[" + j + "]")).click();

                driver.findElement(By.xpath("(//*[@data-at-select='eventMain']/div[@class='evtOuter'])[" + j + "]//li[text()='" + eventProperties3.get(i) + "']")).click();


                switch (eventWhereAggrArray[j - 1]) {

                    case "Count":
                        selectEventWhereAggr(j, j - 1);
                        selectEvenWhereOperator(j);
                        enterEventWhereData(j);
                        selectAfterDateFromCalendar("Mar","2018","21");
                        break;

                    case "Property sum of":
                        selectEventWhereAggr(j, j - 1);
                        selectEventWhereProp(j);
                        selectEvenWhereOperator(j);
                        enterEventWhereData(j);
                        selectOnDateFromCalendar("Mar","2019","20");
                        break;
                    default:
                        logger.info("***default case enterUserWhoDidFilters***");
                        break;
                }


            } catch (Exception e) {

                driver.findElement(By.xpath("(//*[@data-at-select='eventMain']/div[@class='evtOuter'])[" + j + "]")).click();


                driver.findElement(By.xpath("(//*[@data-at-select='eventMain']/div[@class='evtOuter'])[" + j + "]//li[text()='" + eventProperties3.get(i) + "']")).click();

                switch (eventWhereAggrArray[j - 1]) {

                    case "Count":
                        selectEventWhereAggr(j, j - 1);
                        selectEvenWhereOperator(j);
                        enterEventWhereData(j);
                        selectAfterDateFromCalendar("Mar","2018","21");
                        break;

                    case "Property sum of":
                        selectEventWhereAggr(j, j - 1);
                        selectEventWhereProp(j);
                        selectEvenWhereOperator(j);
                        enterEventWhereData(j);
                        selectOnDateFromCalendar("Mar","2019","20");
                        break;
                    default:
                        logger.info("***default case enterUserWhoDidFilters***");
                        break;
                }

            }
        }
    }

    public void enterUserWhoDidNotFilters(List<String> eventProperties4) {
        int size = eventProperties4.size();
        for (int i = 0; i < size; i++) {

            int j = i + 1;

            if (SeleniumUtils.getVisibility(By.id("addDidNotEvent"), 10, driver) != null) {
                SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.id("addDidNotEvent"), 10, driver));
            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("$(\"didntClauseDiv\").animate({ scrollTop: \"" + 100 + PX);
            }
            try {

                driver.findElement(By.xpath("(//*[@id='SEQuery']//div[@data-at-select='eventMain']/div[@class='evtOuter'])[" + j + "]")).click();

                driver.findElement(By.xpath("(//*[@id='SEQuery']//div[@data-at-select='eventMain']/div[@class='evtOuter'])[" + j + "]//li[text()='" + eventProperties4.get(i) + "']")).click();

            } catch (Exception e) {
                driver.findElement(By.xpath("(//*[@id='SEQuery']//div[@data-at-select='eventMain']/div[@class='evtOuter'])[" + j + "]")).click();

                driver.findElement(By.xpath("(//*[@id='SEQuery']//div[@data-at-select='eventMain']/div[@class='evtOuter'])[" + j + "]//li[text()='" + eventProperties4.get(i) + "']")).click();
                SeleniumUtils.pause(2);
            }
        }
    }

    public void enterAddLikeDidEventsFilters(List<String> eventProperties1, List<String> eventProperties2) {

        int size = eventProperties1.size();
        for (int i = 0; i < size; i++) {

            int j = i + 1;

            if (SeleniumUtils.getVisibility(By.id("addLikeDidEvent"), 10, driver) != null) {
                SeleniumUtils.performClick(driver, SeleniumUtils.getVisibility(By.id("addLikeDidEvent"), 10, driver));
            } else {
                /*below 2 lines of code is for scroll into view*/
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("$(\"likeQuery\").animate({ scrollTop: \"" + 100 + PX);
            }

            try {
                driver.findElement(By.xpath("(//*[@data-at-select='likeType'])[" + j + "]")).click();

                driver.findElement(By.xpath("(//*[@data-at-select='likeType'])[" + j + "]//li[text()='" + eventProperties1.get(i) + "']")).click();

                driver.findElement(By.xpath("(//*[@data-at-select='likeEvent'])[" + j + "]")).click();

                driver.findElement(By.xpath("(//*[@data-at-select='likeEvent'])[" + j + "]//li[text()='" + eventProperties2.get(i) + "']")).click();

                selectDominantType("at least", j);
                selectSignificantValue(5, j);

                switch (eventProperties1.get(i)) {
                    case "Event property":
                        selectEventProperties("ct_os_version", j);
                        selectEventOperator("equals", j);
                        enterPropValue("8.0.0", j);
                        break;

                    case "Time of the day":
                        selectLikeFromHour(j);
                        selectLikeToHour(j);
                        selectAfterDateFromCalendar("Feb","2017","5");
                        break;

                    case "Day of the week":
                        selectSomeOptions(j);
                        selectBetweenDateFromCalendar("Feb","2017","Jan","2019","10","15");
                        break;

                    default:
                        break;

                }


            } catch (Exception e) {
                driver.findElement(By.xpath("(//*[@data-at-select='likeType'])[" + j + "]")).click();

                driver.findElement(By.xpath("(//*[@data-at-select='likeType'])[" + j + "]//li[text()='" + eventProperties1.get(i) + "']")).click();

                driver.findElement(By.xpath("(//*[@data-at-select='likeEvent'])[" + j + "]")).click();

                driver.findElement(By.xpath("(//*[@data-at-select='likeEvent'])[" + j + "]//li[text()='" + eventProperties2.get(i) + "']")).click();

                selectDominantType("at least", j);
                selectSignificantValue(5, j);

                switch (eventProperties1.get(i)) {
                    case "Event property":
                        selectEventProperties("Campaign id", j);
                        selectEventOperator("equals", j);
                        enterPropValue("1233444555", j);
                        selectBeforeDateFromCalendar("Mar","2018","10");
                        break;

                    case "Time of the day":
                        selectLikeFromHour(j);
                        selectLikeToHour(j);
                        selectAfterDateFromCalendar("Feb","2017","5");
                        break;

                    case "Day of the week":
                        selectSomeOptions(j);
                        selectBetweenDateFromCalendar("Feb","2017","Jan","2019","10","15");
                        break;

                    default:
                        break;


                }
            }

        }
    }

    private void selectBetweenDateFromCalendar(String fromMonth,String fromYear,String toMonth,String toYear,String fromDate,String toDate) {
        Calendar calendar = new Calendar(driver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("Between",Calendar.globalCalendarInstance);
        calendar.selectBetweenDate(fromMonth, fromYear, toMonth, toYear, fromDate, toDate);
        Calendar.globalCalendarInstance=Calendar.globalCalendarInstance+1;
    }

    private void selectBeforeDateFromCalendar(String month,String year,String date){
        Calendar calendar = new Calendar(driver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("Before",Calendar.globalCalendarInstance);
        calendar.selectBeforeDate(month,year,date);
        Calendar.globalCalendarInstance=Calendar.globalCalendarInstance+1;
    }

    private void selectAfterDateFromCalendar(String month,String year,String date) {
        Calendar calendar = new Calendar(driver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("After",Calendar.globalCalendarInstance);
        calendar.selectAfterDate(month,year,date);
        Calendar.globalCalendarInstance=Calendar.globalCalendarInstance+1;
    }

    private void selectOnDateFromCalendar(String onMonth,String onYear,String whichDate) {
        Calendar calendar = new Calendar(driver);
        calendar.openCalendar(Calendar.globalCalendarInstance);
        calendar.listDownCalendarItems(Calendar.globalCalendarInstance);
        calendar.pickSpecificCalendarItem("On",Calendar.globalCalendarInstance);
        calendar.selectOnDate(onMonth,onYear,whichDate);
        Calendar.globalCalendarInstance=Calendar.globalCalendarInstance+1;
    }


    public boolean getKundliFace() {
        SeleniumUtils.scrollDown(driver);
        int numberOfFaces = kundliFaces.size();
        return numberOfFaces >= 1;
    }

    public FindPeoplePage(WebDriver driverFromPreviousBrowser) {
        this.driver = driverFromPreviousBrowser;
        PageFactory.initElements(driverFromPreviousBrowser, this);
    }
}
