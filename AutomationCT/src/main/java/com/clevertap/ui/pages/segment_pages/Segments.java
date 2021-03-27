package com.clevertap.ui.pages.segment_pages;

import com.clevertap.ui.pages.widget.*;
import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Segments extends LoadYamlFile {

    public static final String ACTIONS = "actions";
    public static final String INACTION = "inaction";
    public static final String ACTIONS_WITH_USER_PROPERTIES = "actions with user properties";
    public static final String SINGLE_ACTION = "single action";
    public static final String INACTION_WITHIN_TIME = "inaction within time";
    public static final String ON_A_DATE_TIME = "on a date/ time";
    public static final String WHO_LIKE_EVENT_PROP = "whoLikeEventProp";
    public static final String WHO_LIKE_EVENT = "whoLikeEvent";
    public static final String WHO_DID_EVENT = "whoDidEvent";
    public static final String WHO_DID_NOT_EVENT = "whoDidNotEvent";
    public static final String AS_SOON_AS_USER_DOES = "AsSoonAsUserDoes";
    public static final String WHO_DID_OCCURENCE = "whoDidOccurence";
    public static final String EVENT_PROPERTY = "EventProperty";

    WebDriver driver;

    @FindBy(xpath = "//*[@class='ct-breadcrumb']")
    private WebElement pageTitle;
    @FindBy(xpath="//*[@id='createSegmentBtn']")
    private WebElement btnCreateSegment;
    @FindBy(className = "segCards_title")
    private List<WebElement> typesOfSegments;
    @FindBy(xpath = "//*[@class='segCards_action']/a")
    private List<WebElement> selectButtonForEachSegemntType;
    @FindBy(className = "ct-breadcrumb_title")
    private List<WebElement> breadcrumb;
    @FindBy(xpath = "//span[@class='ct-breadcrumb_title']")
    private WebElement segmentsBreadCrumbTitle;
    @FindBy(xpath = "//span[@class='ct-breadcrumb_title js-bc-secondary']")
    private WebElement segmentsSecondaryBreadCrumb;
    @FindBy(xpath = "//*[@id='addLikeDidEvent']")
    private WebElement addInterest;
    @FindBy(xpath = "//*[@id='addDidEvent']")
    public WebElement addBehaviour;
    @FindBy(className = "grid-title")
    private WebElement segmentTitle;
    @FindBy(xpath = "//*[@class='grid-title']//a")
    private WebElement changeSegmentLink;
    @FindBy(xpath = "//*[@id='saveSegment']")
    private WebElement saveSegment;
    @FindBy(xpath = "//*[@id='saveSegmentName']")
    private WebElement segmentName;
    @FindBy(xpath = "//*[@class='confirm']")
    private WebElement saveButton;
    @FindBy(xpath = "//input[@id='searchDiv']")
    private WebElement searchBoxSegment;
    @FindBy(xpath = "//*[@data-step-name='who_list']/a[text()='Select']")
    private WebElement whoPageSelectSegment;
    @FindBy(xpath = "//*[@for='filterStream']")
    private WebElement filterPastUserBehaviour;
    @FindBy(xpath="//*[@id='inputLoader']")
    private WebElement delayForInActionWithinTime;
    @FindBy(xpath = "(//div[@class='CT-table']//a)[1]")
    public WebElement firstSegmentLoc;
    @FindBy(xpath = "//span[@id='markHot']")
    public WebElement precomputedLink;
    @FindBy(xpath = "//div[@class='total-value']//following-sibling::a[text()='Campaign']")
    public WebElement segmentReachabilityCreateCampLink;
    @FindBy(xpath = "//div[@class='total-value']//following-sibling::a[text()='Journey']")
    public WebElement segmentReachabilityCreateJourneyLink;
    @FindBy(xpath = "//div[@class='card__body--quarter']//a[text()='Campaign']")
    public WebElement doMoreSegementCreateCampaign;
    @FindBy(xpath = "//div[@class='card__body--quarter']//a[text()='Journey']")
    public WebElement doMoreSegementCreateJourney;
    @FindBy(xpath = "//a[contains(@class,'is-disabled') and @id='newSegmentGoalBtn']")
    public WebElement createSegmenGoaltDisabledButton;
    @FindBy(id = "segmentGridView")
    public WebElement segmentGridView;
    @FindBy(id = "segmentListView")
    public WebElement segmentListView;

    public List<String> userWhoLikeType = new ArrayList<>();
    public List<String> userWhoLikeEvents = new ArrayList<>();
    public List<String> userWhoDid = new ArrayList<>();
    public List<String> userWhoDidNotDo = new ArrayList<>();
    public List<String> liveUserWhoDidEvent = new ArrayList<>();
    public List<String> liveUserWhoDidOccurences = new ArrayList<>();
    public List<String> pbsUserWhoDidNotDo = new ArrayList<>();

    public SegmentWidget segmentWidget;
    public DidWidget didWidget;
    public DidNotWidget didNotWidget;
    public CommonPropertyProfileWidget commonPropertyProfileWidget;
    public ActionEventWidget actionEventWidget;

    public String getHeaderText() {
        return SeleniumUtils.getElementText(driver, pageTitle);

    }

    public void saveSegment(String segment) {
        SeleniumUtils.performClick(driver, saveSegment);
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.enterInputText(driver, segmentName, segment);
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.performClick(driver, saveButton);

    }

    public List<String> getSegmentsNameList() {
        List<String> segNameList = new ArrayList<>();
        for (WebElement segment : typesOfSegments) {
            segNameList.add(segment.getAttribute("innerText"));
        }
        return segNameList;
    }


    public boolean verifyEachSegment(String segmentName) {

        WebElement segmemntElement = driver.findElement(By.xpath("//*[@class='segCards_title' and text()='" + segmentName + "']"));
        if (!SeleniumUtils.isClickable(segmemntElement, driver)) {
            SeleniumUtils.scrollDown(driver);
        }

        String individualSegmentText = SeleniumUtils.getElementText(driver, segmemntElement);
        SeleniumUtils.performClick(driver, segmemntElement);
        String individualSegmentTitle = SeleniumUtils.getElementText(driver, segmentTitle);
        if (individualSegmentTitle.contains(individualSegmentText)) {
            SeleniumUtils.performClick(driver, changeSegmentLink);
            return true;
        }

        return false;
    }


    public void clickSegment(String segmentName){
        WebElement segmentElement = driver.findElement(By.xpath("//*[@class='segCards_title' and text()='" + segmentName + "']"));
        if (!SeleniumUtils.isClickable(segmentElement, driver)) {
            SeleniumUtils.scrollDown(driver);
        }
        SeleniumUtils.waitForPageLoaded(driver);
        segmentElement.click();
    }


    public void sendTextToSearchBox(String newSegmentName) {
        SeleniumUtils.enterInputText(driver, searchBoxSegment, newSegmentName);
        SeleniumUtils.getElementText(driver, searchBoxSegment);

    }


    public void createSegment() {
        SeleniumUtils.waitAndClick(driver,btnCreateSegment);
    }

    public WebElement getBtnCreateSegment() {
        return btnCreateSegment;
    }

    public WebElement getSegmentsBreadCrumbTitle() {
        return segmentsBreadCrumbTitle;
    }

    public WebElement getSegmentsSecondaryBreadCrumb() {
        return segmentsSecondaryBreadCrumb;
    }

    /*this method is to set the query on segment page from CSV file*/
    public void setQueryFromCSV() throws InterruptedException {
        segmentWidget = new SegmentWidget(driver);
        didWidget = new DidWidget(driver);
        didNotWidget = new DidNotWidget((driver));
        actionEventWidget = new ActionEventWidget(driver);

        switch (campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()) {
            case ACTIONS:
            case INACTION:
            case ACTIONS_WITH_USER_PROPERTIES:
                userWhoLikeType.add(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_LIKE_EVENT_PROP));
                userWhoLikeEvents.add(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_LIKE_EVENT));
                if (!userWhoLikeEvents.get(0).isEmpty()) {
                    segmentWidget.segEnterAddLikeDidEventsFilters(userWhoLikeType, userWhoLikeEvents, segmentQueryCSVMap);
                }
                /*Set up user who did query*/
                userWhoDid.add(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_EVENT));
                if (!userWhoDid.get(0).isEmpty()) {
                    didWidget.segEnterUserWhoDidFilters(userWhoDid, segmentQueryCSVMap);
                }
                /*Set up user who did not do query*/
                userWhoDidNotDo.add(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_NOT_EVENT));
                if (!userWhoDidNotDo.get(0).isEmpty()) {
                    didNotWidget.segEnterUserWhoDidNotFilters(userWhoDidNotDo, segmentQueryCSVMap);
                }
                break;
            case SINGLE_ACTION:
            case INACTION_WITHIN_TIME:
                actionEventWidget.selectEvent(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(AS_SOON_AS_USER_DOES));
                //to set delay valid delay compulsory while creating inaction within time segment
                if(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase().equalsIgnoreCase(INACTION_WITHIN_TIME)){
                    delayForInActionWithinTime.sendKeys("1");
                }
                if (!driver.findElement(By.id("filterStream")).isSelected()) {
                    SeleniumUtils.performClick(driver, filterPastUserBehaviour);
                }
                liveUserWhoDidEvent.add(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_EVENT));
                liveUserWhoDidOccurences.add(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_OCCURENCE));
                pbsUserWhoDidNotDo.add(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(WHO_DID_NOT_EVENT));
                didWidget.enterLiveActionUserWhoDidFilters(liveUserWhoDidEvent, liveUserWhoDidOccurences);
                didNotWidget.segEnterLiveActionUserWhoDidNotFilters(pbsUserWhoDidNotDo, segmentQueryCSVMap);
                break;
            case ON_A_DATE_TIME:
                actionEventWidget.selectEvent(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(AS_SOON_AS_USER_DOES));
                actionEventWidget.selEvent(segmentQueryCSVMap.get(campaignMeta.getWho().getSaved_segment_type().trim().toLowerCase()).get(EVENT_PROPERTY));
                break;
            default:
        }
    }

    public Segments(WebDriver driverFromPreviousBrowser) {
        this.driver = driverFromPreviousBrowser;
        PageFactory.initElements(driverFromPreviousBrowser, this);
    }

    public boolean verifySegementActionsVisiblity(){
        boolean flag;

        if(driver.findElements(By.xpath("//div[@class='operation-tool']")).size()>0){
            flag=true;
        }else{
            flag=false;
        }

        return flag;
    }
}

