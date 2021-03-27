package com.clevertap.app.mobile_android;//package org.CleverTap.mobile_android;
//
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileElement;
//import io.appium.java_client.android.AndroidDriver;
//import org.apache.log4j.Logger;
//import org.openqa.selenium.By;
//import org.openqa.selenium.NoSuchElementException;
//
//import java.util.List;
//
//public class MobileActions extends AppiumUtils {
//
//    Logger logger = Logger.getLogger(MobileActions.class);
//
//    /* This method is to add account details */
//    public void enterAccountDetails(String accID, String accToken, String accRegion, String debugLevel, String LC_Staging, String emailID, String phNo, String userID, String CTID) throws InterruptedException {
//
//        Thread.sleep(2000);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/account_id")).sendKeys(accID);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/account_token")).sendKeys(accToken);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/account_region")).sendKeys(accRegion);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/debug_level")).sendKeys(debugLevel);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/lc_staging")).sendKeys(LC_Staging);
//        mobileDriver.hideKeyboard();
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/confirm")).click();
//        Thread.sleep(3000);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/login_button")).click();
//        Thread.sleep(2000);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/userEmail")).sendKeys(emailID);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/userPhone")).sendKeys(phNo);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/userId")).sendKeys(userID);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/ctid")).sendKeys(CTID);
//        mobileDriver.findElement(By.id("android:id/button1")).click();
//    }
//
//    /* This method is to login in app */
//    public void enterLoginDetails(String emailID, String phNo, String userID, String CTID) throws InterruptedException {
//
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/login_button")).click();
//        Thread.sleep(2000);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/userEmail")).sendKeys(emailID);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/userPhone")).sendKeys(phNo);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/userId")).sendKeys(userID);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/ctid")).sendKeys(CTID);
//        mobileDriver.findElement(By.id("android:id/button1")).click();
//    }
//
//    public void clickCoverImage() throws InterruptedException {
//
//        try {
//            Thread.sleep(2000);
//            mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/cover_button2")).click();
//        } catch (NoSuchElementException ne) {
//        }
//        try {
//            Thread.sleep(2000);
//            mobileDriver.findElement(By.id("android:id/button1")).click();
//        } catch (NoSuchElementException ne) {
//        }
//        try {
//            Thread.sleep(2000);
//            mobileDriver.findElement(By.xpath("//android.widget.ImageView")).click();
//            mobileDriver.navigate().back();
//        } catch (NoSuchElementException ne) {
//        }
//        try {
//            Thread.sleep(2000);
//            mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/interstitial_button2")).click();
//            mobileDriver.navigate().back();
//        } catch (NoSuchElementException ne) {
//        }
//        Thread.sleep(2000);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/buttonWarehouseLaunch")).click();
//        Thread.sleep(2000);
//        mobileDriver.findElement(By.id("com.clevertap.beardedrobot:id/cover_image")).click();
//    }
//
//    public boolean verifyPushNotification(String title) throws InterruptedException {
//
//        boolean flag = false;
//        mobileDriver.openNotifications();
//        Thread.sleep(2000);
//        List<MobileElement> allNotifications = mobileDriver.findElements(By.id("android:id/title"));
//        for (MobileElement mobileElement : allNotifications) {
//            logger.info("Notification Title: " + mobileElement.getText());
//            boolean status = mobileElement.getText().contains(title);
//            while (!status) {
//                Thread.sleep(2000);
//                status = mobileElement.getText().contains(title);
//            }
//            if (mobileElement.getText().contains(title)) {
//                flag = true;
//                break;
//            }
//        }
//        return flag;
//    }
//}