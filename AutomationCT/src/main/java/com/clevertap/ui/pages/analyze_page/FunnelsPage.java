package com.clevertap.ui.pages.analyze_page;

import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class FunnelsPage {

    private WebDriver driver;
    private final static Logger logger = Logger.getLogger("Funnels");

    @FindBy(xpath = "//div[contains(@id,'selectSegment_chzn')]/a") private WebElement segmentSelector;
    @FindBy(xpath = "//div[contains(@class,'calendar-header')]") private WebElement durationSelector;
    @FindBy(id = "addAnotherStep") private WebElement addFunnelStepBtn;
    @FindBy(xpath = "//input[@value='View funnel']") public WebElement viewFunnel;
    @FindBy(xpath = "//*[name()='g' and contains(@class,'highcharts-data-label-color-undefined')]") private List<WebElement> listOfFunnelData;
    @FindBy(xpath = "//*[name()='g']/*[name()='rect' and @class='highcharts-point ']")
    public WebElement highChartLocForEvent;

    @FindBy(xpath = "//div[@class='funnel-action-container']//span[text()='Create campaign ']")
    public WebElement createCampaignBtn;

    @FindBy(xpath = "//div[@class='funnel-action-container']//span[text()='Create segment ']")
    public WebElement createSegmentBtn;

    @FindBy(id = "evtbkName")
    public WebElement segmentNameInput;

    @FindBy(xpath = "//button[contains(text(),'Create')]")
    public WebElement createSegmentInput;

    public void choseSegment(String optionToSelect){
        segmentSelector.click();
        driver.findElement(By.xpath("//div[contains(@id,'selectSegment_chzn')]//ul/li[text()='"+optionToSelect+"']")).click();
    }

    public void choseDuration(String optionToSelect){
        durationSelector.click();
        SeleniumUtils.pause(1);
        driver.findElement(By.xpath("//div[@id='ct_date_widget']//ul/li/span[text()='"+optionToSelect+"']")).click();
    }

    public FunnelsPage(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

    public void selectEvents(List<String> listOfEvents) throws InterruptedException {
        for(int i=1; i<=listOfEvents.size(); i++){
            driver.findElement(By.xpath("(//div[contains(@id,'evtSelection')])["+i+"]")).click();
            driver.findElement(By.xpath("(//div[contains(@id,'evtSelection')])["+i+"]/..//li[text()='"+listOfEvents.get(i-1)+"']")).click();
            addFunnelStepBtn.click();
            SeleniumUtils.scrollDown(driver,"200");
        }
    }

    public void setUpFunnel(String segment, String duration,List<String> listOfEvents) throws InterruptedException {
        choseSegment(segment);
        choseDuration(duration);
        selectEvents(listOfEvents);
        SeleniumUtils.scrollToBottom(driver);
        viewFunnel.click();
    }

    public List<String> getFunnelData(String environment){
        List<String> elementData = new ArrayList<>();
        for (WebElement ele:listOfFunnelData) {
            elementData.add(ele.getText());
            logger.info(environment + " - " + ele.getText());
        }
        return elementData;
    }
}
