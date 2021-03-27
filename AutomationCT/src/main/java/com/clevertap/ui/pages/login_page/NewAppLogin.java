package com.clevertap.ui.pages.login_page;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewAppLogin {

    private WebDriver driver;

    @FindBy(xpath = "//*[@type='email']")
    private WebElement userName;
    @FindBy(xpath = "//*[@type='password']")
    private WebElement pword;
    @FindBy(id = "submitBtn")
    private WebElement loginBtn;
    @FindBy(id = "ellipsis_icon")
    private WebElement ellipsis_icon;
    @FindBy(className = "accountNameTruncate")
    private WebElement rightNavAccountName;
    @FindBy(xpath = "//a[contains(@class,'open')]")
    private WebElement addNewApp;
    @FindBy(xpath = "//div[contains(@class,'dd-add-app')]//a[contains(text(),'Add')]")
    private WebElement navAddNewApp;
    @FindBy(id = "appName")
    private WebElement appNameField;
    @FindBy(xpath = "//span[contains(text(),'Category')]")
    private WebElement catgOfBusiness;
    @FindBy(id = "addMyApp")
    private WebElement addMap;
    @FindBy(xpath = "//div[@data-platform='android']//a[contains(text(),'URL')]")
    private WebElement skipURL;
    @FindBy(className = "goToDashboard")
    private WebElement dashboardTab;

    public void addApp(String appName, String catgOfBusinessValue) {
        clickElement(ellipsis_icon, 5);
        clickElement(addNewApp, 5);
        clickElement(navAddNewApp, 5);
        SeleniumUtils.enterInputText(driver, appNameField, appName);
        clickElement(catgOfBusiness, 5);
        String dynamicXpath = "//li[contains(text(),'" + catgOfBusinessValue + "')]";
        driver.findElement(By.xpath(dynamicXpath)).click();
        addMap.click();
        skipURL.click();
        dashboardTab.click();
    }

    public void clickElement(WebElement ele, int timeOut) {
        SeleniumUtils.pause(timeOut);
        ele.click();
    }

    public String getAppName() {
        return (rightNavAccountName.getText());
    }

    public NewAppLogin(WebDriver driverFromPreviousPage) {
        this.driver = driverFromPreviousPage;
        PageFactory.initElements(driverFromPreviousPage, this);
    }
}
