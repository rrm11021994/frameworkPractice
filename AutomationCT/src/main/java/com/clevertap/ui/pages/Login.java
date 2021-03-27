package com.clevertap.ui.pages;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

    private static WebDriver localDriver;
    private final static Logger logger = Logger.getLogger("Login");
    private static SweetAlert sweetAlert = new SweetAlert(localDriver);

    @FindBy(xpath = "//*[@type='email']")
    private static WebElement userEmail;
    @FindBy(xpath = "//*[@type='password']")
    private static WebElement password;
    @FindBy(id = "submitBtn")
    private static WebElement loginBtn;
    @FindBy(xpath = "//a[text()='Forgot Password?']")
    private static WebElement forgotPassword;
    @FindBy(id = "email")
    private static WebElement resetEmail;
    @FindBy(className = "icon-envelope")
    private static WebElement resetSubmitBtn;
    @FindBy(id = "ellipsis_icon")
    private static WebElement ellipsis_icon;
    @FindBy(xpath = "//*[text()='Logout']")
    private static WebElement logOut;


    public static void login(String userName, String pass) {
        if (userName != null && !userName.isEmpty()) {
            userEmail.sendKeys(userName);
            if (pass != null && !pass.isEmpty()) {
                password.sendKeys(pass);
                try {
                    loginBtn.click();
                } catch (Exception e) {
                    logger.error("Something went wrong while log in : " + e.getMessage());
                }
            }
        }
    }

    public static void forgotPassword(String email) {
        forgotPassword.click();
        resetEmail.clear();
        resetEmail.sendKeys(email);
        resetSubmitBtn.click();
        sweetAlert.sweetAlertConfirmOK();
    }

    public static void logOutApplication() {
        SeleniumUtils.pause(5);
        ellipsis_icon.click();
        try {
            SeleniumUtils.pause(5);
            logOut.click();
        } catch (ElementClickInterceptedException ei) {
            SeleniumUtils.scrollDownLittle(localDriver);
            logOut.click();
        }
    }

    public Login(WebDriver driverFromPreviousPage) {
        localDriver = driverFromPreviousPage;
        PageFactory.initElements(driverFromPreviousPage, this);
    }
}