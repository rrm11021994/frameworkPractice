package com.clevertap.ui.pages.login_page;

import com.clevertap.utils.Mocha;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class LoginPageNegativeScenarios {

    private WebDriver driver;


    @FindBy(className = "ct-breadcrumb")
    private WebElement cleverTapHomePageHeader;
    @FindBy(name = "email")
    private static WebElement userEmail;
    @FindBy(name = "password")
    private static WebElement password;
    @FindBy(xpath = "//button[text()='Log in']")
    private static WebElement loginBtn;
    @FindBy(className = "confirm")
    private WebElement oKButton;
    @FindBy(id = "ellipsis_icon")
    private WebElement ellipsis_icon;
    @FindBy(xpath = "//*[text()='Logout']")
    private WebElement logOut;
    @FindBy(className = "sidebar__brand-long")
    private WebElement cleverTapLogo;
    @FindBy(xpath = "//input[@name='password']/..//div[text()='Invalid Credentials']")
    public WebElement invalidCredentialsLoc;
    @FindBy(className = "v-avatar")
    private WebElement profileLoc;
    @FindBy(xpath = "//div[text()='Switch to Classic CleverTap']")
    private WebElement switchToOldViewLoc;
    @FindBy(xpath = "//input[@name='password']/..//div[text()='Please enter valid credentials']")
    public WebElement pleaseEnterValidCredLoc;

    private static String newAccountSwitchLoc="//div[contains(@class,'ct-app-switcher')]//button";
    private static String expandLogoutOption="//span[@class='v-btn__content']//div[@class='v-avatar']";
    private static String switchToClevertapLoc="//div[text()='Switch to CleverTap Classic']";

    public void logOutApplication() {
        ellipsis_icon.click();
        SeleniumUtils.waitForElementToClickable(driver,logOut,10);
        logOut.click();
    }

    public WebElement getPasswordElement() {
        return password;
    }


    public WebElement getLoginElement() {
        SeleniumUtils.pause(1);
        return loginBtn;
    }

    public String checkPasswordEncrypted() {
        return password.getAttribute("type");
    }

    public void login(String ue, String pwd, boolean ifLogin){
        SeleniumUtils.enterInputText(driver,userEmail,ue);
        SeleniumUtils.enterInputText(driver,password,pwd);
        if (ifLogin) {
            SeleniumUtils.performClick(driver, loginBtn);
            Mocha.closePopup(driver);
        }

        SeleniumUtils.waitForPageLoaded(driver);
        if(driver.findElements(By.xpath(newAccountSwitchLoc)).size()>0){
            SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath(expandLogoutOption)));
            SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath(switchToClevertapLoc)));
            driver.switchTo().frame(driver.findElement(By.id("wiz-iframe-intent")));
            SeleniumUtils.pause(2);
            driver.findElement(By.xpath("//a[text()='Switch to Classic']")).click();
        }
    }

    public void hitOk() {
        SeleniumUtils.performClick(driver, oKButton);
    }

    public String getHeaderString() {
        return cleverTapHomePageHeader.getText();
    }


    public LoginPageNegativeScenarios(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

    public boolean verifyUserLoggedIn(){
        boolean flag=false;
        SeleniumUtils.waitForElementToClickable(driver,cleverTapLogo,20);
        if(driver.findElements(By.className("sidebar__brand-long")).size()>0){
            flag=true;
            Reporter.log("User Logged In Sucessfully ",true);
        }else{
            Reporter.log("something went wrong while logging in ",true);
        }

        return flag;
    }

    public void switchToOldView(){
        SeleniumUtils.waitAndClick(driver,profileLoc);
        SeleniumUtils.waitAndClick(driver,switchToOldViewLoc);

    }

}