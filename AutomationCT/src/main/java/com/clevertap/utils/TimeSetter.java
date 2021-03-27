package com.clevertap.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimeSetter {

    public WebDriver driver;
    String minPickerClass = "//input[@class='ct-time-picker__min']", hrPickerClass = "//input[@class='ct-time-picker__hr']";

    public TimeSetter(WebDriver driver) {
        this.driver = driver;
    }

    public void setTime(String hr_mm_PM, String xpathOfPane){
        String hours = hr_mm_PM.substring(0,2);
        String minuts = hr_mm_PM.substring(3,5);
        String meridiem = hr_mm_PM.substring(6,8);

        SeleniumUtils.waitForPageLoaded(driver);

        driver.findElement(By.xpath(xpathOfPane + minPickerClass)).clear();
        driver.findElement(By.xpath(xpathOfPane + hrPickerClass)).clear();

        SeleniumUtils.enterInputText(driver, driver.findElement(By.xpath(xpathOfPane + hrPickerClass)), hours);
        SeleniumUtils.enterInputText(driver, driver.findElement(By.xpath(xpathOfPane + minPickerClass)), minuts);

        driver.findElement(By.xpath(xpathOfPane + "//a")).click();
        driver.findElement(By.xpath(xpathOfPane + "//li[contains(text(),'" + meridiem + "')]")).click();
    }

}
