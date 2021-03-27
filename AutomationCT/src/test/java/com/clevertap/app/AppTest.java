package com.clevertap.app;


import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

/**
 * Unit test for simple App.
 */
public class AppTest extends App
{


    @Test
    public void test() throws MalformedURLException {
        App.launchAgent("web","android");
        //below lines to login into facebook
        appiumDriver.get("https://www.facebook.com/");
        appiumDriver.findElement(By.xpath("//*[@type='email']")).sendKeys("manmohan@clevertap.com");
        appiumDriver.findElement(By.xpath("//*[@type='password']")).sendKeys("manmohan@clevertap.com");
        appiumDriver.findElement(By.xpath("//*[@value='Log In']")).click();

        appiumDriver.quit();
    }




}
