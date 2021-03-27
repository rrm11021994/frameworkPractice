package com.clevertap.ui.pages.widget;

import com.clevertap.ui.pages.CTPageFactory;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SweetAlert {

    private static WebDriver localDriver;

    @FindBy(xpath = "//*[contains(@class,'sweet-alert')]")
    private static WebElement sweetAlertRectBox;
    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")
    public static WebElement sweetAlertConfirmOK;
    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]/div[contains(@class,'icon error')]")
    public WebElement sweetAlertError;
    @FindBy(xpath = "//*[contains(@class,'icon info')]")
    private static WebElement sweetAlertInfo;
    @FindBy(xpath = "//*[contains(@class,'icon success')]")
    private static WebElement sweetAlertSuccess;
    @FindBy(xpath = "//*[contains(@class,'icon warning')]")
    private static WebElement sweetAlertWarning;
    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]//h2[text()='Access denied']")
    private WebElement sweetAlertAccessDenied;
    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]//p[text()=\"You don't have access to this feature, please ask your account admin to grant access\"]")
    private WebElement sweetAlertNoAccessToFeature;
    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]//p[text()='Error in updating timezone']")
    private WebElement errorUpdatingValue;

    public boolean getSweetAlertErrorSignal() {
        SeleniumUtils.pause(1);
        boolean status = sweetAlertError.getAttribute("style").contains("block");
        SeleniumUtils.performClick(localDriver, sweetAlertConfirmOK);
        return status;
    }

    public boolean getSweetAlertInfoSignal() {
        boolean status = sweetAlertInfo.getAttribute("style").contains("block");
        SeleniumUtils.performClick(localDriver, sweetAlertConfirmOK);
        return status;
    }

    public boolean getSweetAlertSuccessSignal() {
        boolean status = sweetAlertSuccess.getAttribute("style").contains("block");
//        SeleniumUtils.performClick(localDriver, sweetAlertConfirmOK);
        SeleniumUtils.waitAndClick(localDriver,sweetAlertConfirmOK);
        return status;
    }

    public boolean getSweetAlertWarningSignal() {
        boolean status = sweetAlertWarning.getAttribute("style").contains("block");
        SeleniumUtils.performClick(localDriver, sweetAlertConfirmOK);
        return status;
    }

    public void sweetAlertConfirmOK() {
//        SeleniumUtils.pause(1);
//        sweetAlertConfirmOK.click();
        SeleniumUtils.waitForElementToClickable(localDriver,sweetAlertConfirmOK,10);
        sweetAlertConfirmOK.sendKeys(Keys.ENTER);
    }


    public boolean getSweetAlertRecBox() {
        boolean status = sweetAlertRectBox.getAttribute("style").contains("block");
        SeleniumUtils.performClick(localDriver, sweetAlertConfirmOK);
        return status;
    }


    public SweetAlert(WebDriver previousBrowserDriver) {
        localDriver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

    public void verifyAccessDeniedAlert(){
            SeleniumUtils.waitForElementToLoad(localDriver, sweetAlertAccessDenied);
            Assert.assertTrue(sweetAlertAccessDenied.isDisplayed(), "Access Denied popup was not shown");
//            SeleniumUtils.waitAndClick(localDriver, sweetAlertConfirmOK);
    }

    public void verifyAccessDeniedToFeature(){
        SeleniumUtils.waitForElementToLoad(localDriver,sweetAlertNoAccessToFeature);
        Assert.assertTrue(sweetAlertNoAccessToFeature.isDisplayed(),"No Access to feature popup was not shown");
//        SeleniumUtils.waitAndClick(localDriver,sweetAlertConfirmOK);
    }

    public void errorUpdatingValue(){
        SeleniumUtils.waitForElementToLoad(localDriver,errorUpdatingValue);
        Assert.assertTrue(errorUpdatingValue.isDisplayed(),"No Access to feature popup was not shown");
//        SeleniumUtils.waitAndClick(localDriver,sweetAlertConfirmOK);

    }



}

