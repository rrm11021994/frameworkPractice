package com.clevertap.ui.pages.widget;

import com.clevertap.utils.LoadYamlFile;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Map;

public class ActionEventWidget extends LoadYamlFile {

    private WebDriver localDriver;

    @FindBy(xpath = "//*[@id='evtSelection1']/..//a")
    private WebElement openEventDropdown;
    @FindBy(xpath = "//*[@id='evtSelection1']/..//span")
    private WebElement selectedEvent;
    @FindBy(xpath = "//*[@id='propSelect_chzn']/..//a")
    private WebElement openEventPropDropdown;


    public void selectEvent(String event){
        openEventDropdown.click();
        SeleniumUtils.pause(1);
        localDriver.findElement(By.xpath("//*[@id='evtSelection1']/..//a/following-sibling::div//li[text()='"+event+"']")).click();
    }

    public void selEvent(String event){
        openEventPropDropdown.click();
        SeleniumUtils.pause(1);
        localDriver.findElement(By.xpath("//*[@id='propSelect']/..//a/following-sibling::div//li[text()='"+event+"']")).click();
    }

    public void verifyEvent(Map<String,Map<String,String>> csvMapObj){
        SeleniumUtils.pause(1);
        String selectedUserAsSoonAsDoesEvent=localDriver.findElement(By.xpath("//*[@id='evtSelection1']/..//span")).getText();
        Assert.assertTrue(selectedUserAsSoonAsDoesEvent.contains(csvMapObj.get(campaignMeta.getCsv_key()).get("AsSoonAsUserDoes")));
    }

    public String verifySetEvent(){
        return selectedEvent.getText();
    }


    public ActionEventWidget(WebDriver previousBrowserDriver) {
        localDriver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

}
