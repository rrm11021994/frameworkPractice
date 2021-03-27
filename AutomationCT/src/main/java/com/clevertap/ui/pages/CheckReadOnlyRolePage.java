package com.clevertap.ui.pages;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckReadOnlyRolePage {

    WebDriver driver;

    @FindBy(xpath = "//*[contains(@class,'sweet-alert')]") private WebElement messageAlert;
    @FindBy(xpath = "//*[contains(@class,'confirm')]") private WebElement confirmOK;
    @FindBy(id = "createBoard") private WebElement createBoard;
    @FindBy(xpath = "//*[contains(@class,'cancel')]") private WebElement cancel;
    @FindBy(id = "createSegmentBtn") private WebElement createSegment;
    @FindBy(xpath = "//*[contains(@class,'js-createCampaign')]") private WebElement createCampaign;
    @FindBy(xpath = "//*[contains(@class,'journeyBtn')]") private WebElement journeyBtn;
    @FindBy(xpath = "//*[contains(@class,'campaign-controls')]//li[text()='Custom control group']") private WebElement customControlGroup;
    @FindBy(xpath = "//*[contains(@class,'js-create-custom-cg-btn')]") private WebElement createCustomControlGroup;
    @FindBy(xpath = "//*[contains(@class,'js-createRecommendation')]") private WebElement createRecommendation;
    @FindBy(xpath = "//*[contains(@class,'addButtonMargin')]") private WebElement viewRealImpact;
    @FindBy(xpath = "//*[contains(@class,'cardHeader')]") private WebElement realImpactCardHeader;


    public void openBoard() throws InterruptedException {
        SeleniumUtils.elementHighlighter(driver,createBoard);
        Thread.sleep(1000);
        createBoard.click();
    }

    public String getAccessDenialMessage(){
        return messageAlert.getText();
    }

    public void clickOK(){
        confirmOK.click();
    }

    public void clickCancel(){
        cancel.click();
    }

    public void createSegments() throws InterruptedException {
        SeleniumUtils.elementHighlighter(driver,createSegment);
        Thread.sleep(1000);
        createSegment.click();
        Thread.sleep(1000);
    }

    public void createCampaigns(){
        createCampaign.click();
    }
    public void createJourneys(){
        journeyBtn.click();
    }

    public void createCustomControlGroup(){
        customControlGroup.click();
        createCustomControlGroup.click();
    }

    public void createRecommendations(){
        createRecommendation.click();
    }

    public void clickViewRealImpact(){
        viewRealImpact.click();
    }

    public String getRealImpactCardHeaderText(){
        return realImpactCardHeader.getText();
    }

    public CheckReadOnlyRolePage(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }
}
