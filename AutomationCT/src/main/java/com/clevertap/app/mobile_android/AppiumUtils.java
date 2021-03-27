package com.clevertap.app.mobile_android;//package org.CleverTap.mobile_android;
//
//import io.appium.java_client.MobileElement;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.service.local.AppiumDriverLocalService;
//import io.appium.java_client.service.local.AppiumServiceBuilder;
//import io.appium.java_client.service.local.flags.GeneralServerFlag;
//import org.apache.log4j.Logger;
//import org.openqa.selenium.remote.DesiredCapabilities;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AppiumUtils {
//
//    static DesiredCapabilities capabilities = new DesiredCapabilities();
//    static AndroidDriver<MobileElement> mobileDriver = null;
//    private static AppiumDriverLocalService appiumDriverLocalService;
//    private static AppiumServiceBuilder appiumServiceBuilder;
//    static String nodeJSPath = "/usr/local/bin/node";
//    static String appiumJSPath = "/usr/local/bin/appium";
//
//    private static final String DEVICE_NAME = "Pixel XL API 28";
//    private static final String PLATFORM_NAME = "Android";
//
//    static Logger logger = Logger.getLogger(AppiumUtils.class);
//
//    /* This method is to install and login the app*/
//    public static void appLoginForFirstTime(String appPath) throws MalformedURLException {
//
//        capabilities.setCapability("deviceName", DEVICE_NAME);
//        capabilities.setCapability("platformName", PLATFORM_NAME);
//        capabilities.setCapability("noReset", "true");
//        capabilities.setCapability("app", appPath);
//        capabilities.setCapability("autoGrantPermissions", "true");
//        capabilities.setCapability("autoAcceptAlerts", "true");
//        capabilities.setCapability("appPackage", "com.clevertap.beardedrobot");
//        capabilities.setCapability("appActivity", "com.clevertap.BeardedRobot.ConfigActivity");
//        mobileDriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
//    }
//
//    public static void appLogin() throws MalformedURLException {
//
//        capabilities.setCapability("deviceName", DEVICE_NAME);
//        capabilities.setCapability("platformName", PLATFORM_NAME);
//        capabilities.setCapability("noReset", "true");
//        capabilities.setCapability("autoGrantPermissions", "true");
//        capabilities.setCapability("autoAcceptAlerts", "true");
//        capabilities.setCapability("appPackage", "com.clevertap.beardedrobot");
//        capabilities.setCapability("appActivity", "com.clevertap.BeardedRobot.ConfigActivity");
//        mobileDriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
//    }
//
//    public static void startAppiumService() {
//
//        appiumServiceBuilder = new AppiumServiceBuilder();
//
//        appiumServiceBuilder.usingDriverExecutable(new File(nodeJSPath));
//        appiumServiceBuilder.withAppiumJS(new File(appiumJSPath));
//        appiumServiceBuilder.withIPAddress("0.0.0.0");
//        appiumServiceBuilder.usingPort(4723);
//        appiumServiceBuilder.withArgument(GeneralServerFlag.LOG_LEVEL, "info");
//
//        appiumDriverLocalService = AppiumDriverLocalService.buildService(appiumServiceBuilder);
//        appiumDriverLocalService.start();
//    }
//
//    public static void stopAppiumService() {
//
//        appiumDriverLocalService.stop();
//    }
//
//    public static void launchEmulator() throws IOException {
//
//        String deviceName = null;
//        List<String> allAVDs = new ArrayList<>();
//        Process p = Runtime.getRuntime().exec("/Users/bensequeira/Library/Android/sdk/emulator/emulator -list-avds");
//        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//        while ((deviceName = stdInput.readLine()) != null) {
//            allAVDs.add(deviceName);
//        }
//        p = Runtime.getRuntime().exec("/Users/bensequeira/Library/Android/sdk/emulator/emulator -avd Pixel_3_XL_API_28");
//        stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
//        stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//        logger.info("Input: " + stdInput);
//        logger.info("Error: " + stdError);
//    }
//
//
//}