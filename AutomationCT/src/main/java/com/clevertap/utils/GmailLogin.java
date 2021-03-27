package com.clevertap.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class GmailLogin {

    private WebDriver driver;
    @FindBy(xpath = "//*[@type='email']")
    private WebElement googleEmailField;
    @FindBy(xpath = "//*[@id='identifierNext']")
    private WebElement nextButtonGMailBox;
    @FindBy(xpath = "//*[@type='password']")
    private WebElement passwordFieldGoogle;
    @FindBy(xpath = "//*[@id='passwordNext']")
    private WebElement nextButtonPasswordBox;
    @FindBy(xpath = "//div[@class='Cp']//table")
    private WebElement emailsTable;
    @FindBy(xpath = "//div[@role='listitem']")
    private List<WebElement> listSingUpInvites;
    @FindBy(xpath = "//span[text()='CleverTap Reset Password']") private WebElement resetPasswordEmail;
    @FindBy(xpath = "//a[text()='Reset password']") private WebElement resetPasswordCleverTapLink;
    @FindBy(xpath = "//input[@type='password' and @placeholder='Password']") private WebElement resetPasswordInputBox;
    @FindBy(xpath = "//input[@type='submit']") private WebElement resetPassSubmitBtn;

    @FindBy(xpath = "//input[@aria-label='Search mail']")
    private WebElement googleMailSearchTextBox;
    @FindBy(xpath = "//button[@aria-label='Search Mail']")
    private WebElement seachMailButton;
    @FindBy(xpath = "//div[@gh='tm']//div[@aria-label='Delete']")
    private WebElement deleteButtonAfterSearch;
    @FindBy(xpath = "//div[@data-tooltip='Inbox']//*[@title='Inbox']")
    private WebElement inboxButton;


    public GmailLogin(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void logInToGMail(String uName, String password) {
        SeleniumUtils.enterInputText(driver, googleEmailField, uName);
        SeleniumUtils.performClick(driver, nextButtonGMailBox);

        SeleniumUtils.enterInputText(driver, passwordFieldGoogle, password);
        SeleniumUtils.performClick(driver, nextButtonPasswordBox);
    }

    public void resetPassword(String newPassword){
        SeleniumUtils.enterInputText(driver, googleMailSearchTextBox, "CleverTap Support");
        SeleniumUtils.performClick(driver, seachMailButton);
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.performClick(driver,resetPasswordEmail);
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.performClick(driver,resetPasswordCleverTapLink);
        SeleniumUtils.waitForPageLoaded(driver);
        SeleniumUtils.switchToLastWindowHandle(driver);
        SeleniumUtils.enterInputText(driver,resetPasswordInputBox,newPassword);
        SeleniumUtils.performClick(driver,resetPassSubmitBtn);
        SeleniumUtils.waitForPageLoaded(driver);
    }

    public void deleteEmailsWithHeader(String header) throws InterruptedException {

        SeleniumUtils.enterInputText(driver, googleMailSearchTextBox, header);
        SeleniumUtils.performClick(driver, seachMailButton);
        SeleniumUtils.waitForPageLoaded(driver);
        List<WebElement> searchedMails = driver.findElements(By.xpath("//div[@role='main']//div[@class='Cp']//tbody/tr//div[@role='checkbox']"));
        if (searchedMails.size() > 0) {
            SeleniumUtils.waitForPageLoaded(driver);
            for (WebElement elements : searchedMails) {
                elements.click();
            }
            SeleniumUtils.waitForPageLoaded(driver);
            driver.findElement(By.xpath("//*[@id=':4']/div[2]/div[1]/div[1]/div/div/div[2]/div[3]/div")).click();
        }
        SeleniumUtils.performClick(driver, inboxButton);
    }
}