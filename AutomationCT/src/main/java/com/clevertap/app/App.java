package com.clevertap.app;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {

    public static AppiumDriver<MobileElement> appiumDriver;
    public static DesiredCapabilities capabilities;


    public static void launchAgent(String envName, String osType) throws MalformedURLException {

        capabilities = new DesiredCapabilities();
        switch (osType.trim().toLowerCase()){
            case "android":
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
                break;
            case "ios":
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "IOS");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ios device name");
                break;
                default:

        }
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1.0");

        switch (envName.trim().toLowerCase()) {
            case "app":
                capabilities.setCapability(MobileCapabilityType.APP, "/Users/manmohanroy/Downloads/API Demos for Android_v1.9.0_apkpure.com.apk" );
//                capabilities.setCapability("appPackage","com.example.android.apis");
//                capabilities.setCapability("appActivity","com.example.android.apis.ApiDemos");
                break;
            case "web":
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
                capabilities.setCapability("noReset", true);
                capabilities.setCapability("chromedriverExecutable", "/Users/manmohanroy/Documents/DashboardAutomationRearchitecture/automation-dashboard/drivers_mac/chromedriver");
                // below line is to avoid error "Android: Cannot call non W3C standard command while in W3C mode"
                capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
                break;
            default:
        }

        //creating instance of appium driver
        appiumDriver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        appiumDriver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);


        //checking appium driver is instantiating or not properly
        Assert.assertNotNull(appiumDriver);


    }

}
