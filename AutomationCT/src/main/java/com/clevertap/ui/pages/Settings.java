package com.clevertap.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Settings {

    WebDriver driver;


    @FindBy(xpath = "//*[@id='Left-Nav']")
    private static WebElement leftNav;
    @CacheLookup
    @FindBy(xpath = "//*[@id='menu']/ul/li[6]/ul/li/a")
    private static List<WebElement> leftNavSubmenus;
    @FindBy(xpath = "//*[@id='menu']/ul/li[6]/ul/li[1]/a")
    private static WebElement settings;
    @FindBy(xpath = "//*[@id='email']")
    private static WebElement email;
    @FindBy(xpath = "//*[@id='role_chzn']")
    private static WebElement selectRole;
    @FindBy(xpath = "//*[@class='chzn-drop']//li")
    private static List<WebElement> memebrs;
    @FindBy(xpath = "//*[@id='invite']")
    private static WebElement invite;
    @FindBy(xpath = "//*[contains(@class,'sweet-alert')]")
    private static WebElement confirmationText;
    @FindBy(xpath = "//*[@class='confirm']")
    private static WebElement confirmOK;
    @FindBy(xpath = "//*[@id='fix-cols']//a")
    private static List<WebElement> anchors;
    @FindBy(xpath = "//*[@id='content-head']")
    private static WebElement contentHeader;
    @FindBy(xpath = "//*[@class='goToDashboard']")
    private static WebElement goToDashboard;
    @FindBy(xpath = "//*[@id='accountName']")
    private WebElement accountName;


    public void clickLeftNav() {
        try {
            Thread.sleep(1000);
            leftNav.click();

        } catch (InterruptedException e) {
            System.out.println("leftNav not found");
            e.printStackTrace();
        }

    }

    public int getSubmenusCount() {
        int submenuCount = leftNavSubmenus.size();
        return submenuCount;
    }

    public String clickLearnMore(String linkName){
        String contentHead=null;
        for (WebElement anchor:anchors){

            if(anchor.getText().contains(linkName)){
                anchor.click();
                ArrayList<String> numberOfTabsOpened=new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(numberOfTabsOpened.get(1));
                contentHead=contentHeader.getText();
                driver.close();
                driver.switchTo().window(numberOfTabsOpened.get(0));
                break;


            }
        }


        return contentHead;
    }

    public String clickIntegaratePlatform(String linkName){
        for (WebElement anchor:anchors){

            if(anchor.getText().contains(linkName)){
                anchor.click();
                break;


            }
        }


       return goToDashboard.getText();
    }

    public String verifyInviteUser(String emailId) throws InterruptedException {
        String postInviteText = null;
        try {
            settings.click();
            email.sendKeys(emailId);
            selectRole.click();
            for (WebElement el : memebrs) {
                String text = el.getAttribute("innerText");
                if (text.equalsIgnoreCase("Member")) {
                    el.click();
                    break;
                }
            }
            invite.click();
            Thread.sleep(500);
            postInviteText = confirmationText.getText();
            Thread.sleep(500);
            confirmOK.click();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return postInviteText;


    }

    public void updateAccountName() throws InterruptedException {
        Random random = new Random();
        int n = random.nextInt(50);
        n += 1;
        accountName.clear();
        accountName.sendKeys("TestAutomation" + n);
        Thread.sleep(500);
        confirmOK.click();
        Thread.sleep(500);
        confirmOK.click();
        Thread.sleep(500);
    }

    public Settings(WebDriver driverFromPreviousPage) {
        this.driver = driverFromPreviousPage;
        PageFactory.initElements(driverFromPreviousPage, this);
    }

}
