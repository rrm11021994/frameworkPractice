package com.clevertap.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CleverTapHomePage {

    WebDriver driver;

    @FindBy(xpath = "//*[@id='menu']/ul/li//div[text()='Analyze']")
    WebElement menu;
    @FindBy(xpath = "//*[@id='menu']/ul/li//div[text()='Analyze']/../../ul//a[text()='Events']")
    WebElement subMenu;
    @FindBy(xpath = "//*[@id='accountLists']")
    WebElement headerText;

    public void clickOnAction() {
        menu.click();
        subMenu.click();
    }

    public boolean getHeaderText() {
        String text = headerText.getText();
        if (text.contains("TEST-Automation13")) {
            return true;
        }
        return false;
    }

    public CleverTapHomePage(WebDriver driverFromPreviousPage) {
        this.driver = driverFromPreviousPage;
        PageFactory.initElements(driverFromPreviousPage, this);
    }
}
