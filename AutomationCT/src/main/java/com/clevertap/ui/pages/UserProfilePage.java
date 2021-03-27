package com.clevertap.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UserProfilePage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='modeTbDash']/li")
    List<WebElement> buttons;
    @FindBy(xpath = "//*[@id='delprofilebtn']")
    WebElement deleteProfile;
    @FindBy(xpath = "//*[@id='accept']")
    WebElement acceptCheckbox;
    @FindBy(xpath = "//*[@class='confirm']")
    WebElement confirmButton;
    @FindBy(xpath = "//*[@class='confirm' and text()='OK']")
    WebElement reconfirm;
    @FindBy(xpath = "//*[@class='profile-header']")
    WebElement deleteMessage;
    @FindBy(xpath = "//*[@class='FL MT10 ML10']")
    WebElement filterByEventCheckBox;
    @FindBy(xpath = "//*[@id='noticeForEmptyEvents']")
    WebElement noticeForEmptyEvent;

    public List<WebElement> getButtons() {
        return buttons;
    }

    public boolean isButtonClickable(WebElement element) {

        return element.isEnabled();
    }

    public String deleteProfile() throws InterruptedException {
        if (!deleteMessage.getAttribute("innerText").contains("This profile has been marked for deletion.")) {
            deleteProfile.click();
            Thread.sleep(500);
            acceptCheckbox.click();
            Thread.sleep(500);
            confirmButton.click();
            Thread.sleep(500);
            reconfirm.click();
            Thread.sleep(500);
            return deleteMessage.getAttribute("innerText");
        }

        return "This profile is already deleted";


    }

    public String getFilterEventText() {
        filterByEventCheckBox.click(); /*uncheck the checkbox*/
        return noticeForEmptyEvent.getAttribute("innerText");
    }

    public UserProfilePage(WebDriver driverFromPreviousPage) {
        this.driver = driverFromPreviousPage;
        PageFactory.initElements(driverFromPreviousPage, this);
    }
}
