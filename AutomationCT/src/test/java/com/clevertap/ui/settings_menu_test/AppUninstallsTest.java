package com.clevertap.ui.settings_menu_test;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.settings_menu_page.AppUninstalls;
import com.clevertap.utils.Mocha;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class AppUninstallsTest extends BaseTest{

    public static final String PASSED = "Passed";
    private Logger logger;
    private WebDriver driver;

    @BeforeClass
    public void initialize() {
        BaseTest baseTest= BaseTest.getInstance();
        driver=baseTest.getDriver();
        Mocha.openLeftNavMenu(driver,true, "Settings", "App uninstalls", "");
        logger = baseTest.configureLogger(getClass());
    }

    @Test(priority = 1, description = "Verify AppUninstall page open")
    public void openAppUninstallPage()  {
        String greenRGBA="rgba(30, 184, 88, 1)";
        String greyRGBA="rgba(210, 213, 216, 1)";
        String switchSliderText=null;

        logger.info("open Profile Page Test Started");
        AppUninstalls device = new AppUninstalls(driver);
        String AppUninstallPageHeaderText = device.getHeaderText();
        Assert.assertTrue(AppUninstallPageHeaderText.contains("Automatic uninstall detection"), "AppUninstallsPage successfully launched");
        switchSliderText=device.getSwitchSliderInnefrText();
        if (switchSliderText.contains("ON")){
            String cssValue=device.getCSSValue();
            Assert.assertTrue(cssValue.contains(greenRGBA),"Button is ON");
        }else {
            String cssValue=device.getCSSValue();
            Assert.assertTrue(cssValue.contains(greyRGBA),"Button is OFF");

        }

        logger.info("open AppUninstalls Page Test Finished");

    }


    @Test(priority =2,description = "Verify the given link is valid or not")
    public void openGivenLink() {
        logger.info("open Link Page Test Started");
        AppUninstalls link = new AppUninstalls(driver);
        boolean status=link.getValidLink();
        Assert.assertTrue(status,"");
        logger.info("openLinkPage Test Finished");
    }

}