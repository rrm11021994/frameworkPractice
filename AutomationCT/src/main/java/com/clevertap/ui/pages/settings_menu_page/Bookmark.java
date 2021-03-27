package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Bookmark {

    WebDriver driver;


    @FindBy(xpath = "//*[contains(@class,'js-bookmarkType')]/div")
    private List<WebElement> bookmarksType;
    @FindBy(className = "confirm")
    private WebElement confirmDeletion;


    public void selectSpecificBookmarkTab(String bookmarkType) {
        for (WebElement bookmarkTab : bookmarksType) {
            if (bookmarkTab.getText().equalsIgnoreCase(bookmarkType)) {
                SeleniumUtils.waitAndClick(driver, bookmarkTab);
            }
        }
    }

    public void identifyAndDeleteBookmarkInTable(String bookmarkNameToBeDeleted) {

//        WebElement deleteBookmarkElement = driver.findElement(By.xpath("//*[@id='segment-bookmark-table']//a[text()='" + bookmarkNameToBeDeleted + "']/../../following-sibling::td//span"));
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath("//*[@id='segment-bookmark-table']//a[text()='" + bookmarkNameToBeDeleted + "']/../../following-sibling::td//span")));
//        SeleniumUtils.performClick(driver, deleteBookmarkElement);
        SeleniumUtils.pause(1);
        SeleniumUtils.waitAndClick(driver, confirmDeletion);
    }


    public Bookmark(WebDriver driverFromPreviousBrowser) {
        this.driver = driverFromPreviousBrowser;
        PageFactory.initElements(driverFromPreviousBrowser, this);
    }
}
