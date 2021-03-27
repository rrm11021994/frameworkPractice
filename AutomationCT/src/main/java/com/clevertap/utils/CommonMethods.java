package com.clevertap.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonMethods {




    /*This method is used to click on curd button i.e three dotted buttons, to check delete, edit and clone*/
    public void clickCurdButton(WebDriver driver,String roleName) {
        String firstXpath = "//table[contains(@class,'datatable-resultset')]/tr//span[text()='";
        String secondXpath = "']/../../following-sibling::td[5]";

        String finalXpath = firstXpath + roleName + secondXpath;
        WebElement singleElement = driver.findElement(By.xpath(finalXpath));
        singleElement.click();
    }
}
