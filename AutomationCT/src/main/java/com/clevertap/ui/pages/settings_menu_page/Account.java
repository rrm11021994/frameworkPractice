package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Account {

    private WebDriver driver = null;
    private SweetAlert sweetAlert;

    @FindBy(xpath = "//*[@class='account-info-container']//strong/../following-sibling::div/div")private WebElement accIdInput;
    @FindBy(xpath = "//*[@class='account-info-container']//div[contains(@class,'account-title') and (text()='Passcode ')]/..//div[@id]")private WebElement passcodeInput;
    @FindBy(xpath = "//div[contains(text(),'Passcode')]/..//i") private WebElement uncoverPasscode;
    @FindBy(xpath = "//strong/../following-sibling::div/i") private WebElement uncoverAccountID;
    @FindBy(xpath = "//div[contains(text(),'Passcode')]/..//span[text()='Reset passcode']") private WebElement resetPasscodeBtn;
    @FindBy(id = "timezone_chzn") private WebElement timezoneDropdown;
    @FindBy(className = "ct-breadcrumb_left") public WebElement leftBreadCrumAccount;
    @FindBy(xpath = "//button[text()='Save timezone']") private WebElement saveTimeZone;
    @FindBy(xpath = "//button[text()='Proceed anyway']") public WebElement proceedAnywayBtn;

    public String getAccountId() throws InterruptedException{
        SeleniumUtils.performClick(driver,uncoverAccountID);
        SeleniumUtils.pause(1);
        return accIdInput.getAttribute("innerText");
    }

    public String getPasscode()throws InterruptedException{
        SeleniumUtils.performClick(driver,uncoverPasscode);
        SeleniumUtils.pause(1);
        return passcodeInput.getAttribute("innerText");
    }

    public void resetPasscode() throws InterruptedException{
        SeleniumUtils.performClick(driver,resetPasscodeBtn);
        sweetAlert.sweetAlertConfirmOK();
        SeleniumUtils.pause(1);
        sweetAlert.sweetAlertConfirmOK();
    }

    public Account(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
        sweetAlert=new SweetAlert(previousBrowserDriver);
    }

    public void selectTimezoneFromDropDown(String timeZone){
        SeleniumUtils.waitForElementToClickable(driver,timezoneDropdown,10);
        timezoneDropdown.click();
        driver.findElement(By.xpath("//ul[@class='chzn-results']/li[contains(text(),'"+timeZone+"')]")).click();
        saveTimeZone.click();
    }
}
