package com.clevertap.utils;

import com.clevertap.BaseTest;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.testng.Reporter;

import java.util.List;

import static com.clevertap.BaseTest.getValue;

public class Mocha {
    private static String prevMenu = "", prevSubMenu = "", prevSuperSubMenu = "", subMenuXpath = "";
    private final static Logger logger = Logger.getLogger("Mocha");
    protected static ExtentTest parentTest;
    private static int lastResult = 0;
    public static boolean forceNavigate = false;
    public static String currentAccountName;
    public static String url;
    public static String prodUrl;
    private static String newAccountSwitchLoc="//div[contains(@class,'ct-app-switcher')]//button";
    private static String expandLogoutOption="//span[@class='v-btn__content']//div[@class='v-avatar']";
    private static String switchToClevertapLoc="//div[text()='Switch to CleverTap Classic']";

    static{
        url = getUrl(getValue("environment"));
        Reporter.log("Automation is running on : "+url+" for account : "+getValue("AccountName"),true);
        prodUrl=getProdUrl(getValue("environment"));
    }

    public static void openLeftNavMenu(WebDriver mochaDriver, boolean login, String menuName, String subMenuName, String superSubMenuName,String ...usernameAndPass) {
        boolean navigatingToSamePage = (prevMenu + prevSubMenu + prevSuperSubMenu).equalsIgnoreCase(menuName + subMenuName + superSubMenuName);
        if ((forceNavigate) || (login) || !navigatingToSamePage || (navigatingToSamePage && lastResult == 2)) {
            String leftNavMenuXpath = "//*[contains(@class,'Dashboard-menuSection')]/li//div[contains(text(),'" + menuName + "')]";
            String leftNavSubmenuXpath = "/../..//a[contains(text(),'" + subMenuName + "')]";
            String leftNavSettingsMenu = "//*[contains(@class,'sidebar__menu-parentname') and contains(text(),'Settings')]";
            String leftNavSettingsSubMenu = "//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'" + subMenuName + "')]";
            String leftNavSettingsSuperSubMenu = "/../../ul/li/a[contains(text(),'" + superSubMenuName + "')]";
            String leftNavBackToMainMenu = "//*[contains(@class,'sidebar__menu-section')]//div[contains(text(),'Back to main menu')]";
            String rightNavAccountName = "//*[@class='accountNameTruncate']";
            String curdButton = "Group-29";
            String accountList = "//*[@class='tp-sec' and contains(text(),'";
            String subMainAccount = "//div[@class='ac-dd']//a[contains(text(),'";

            if (login) {
                mochaDriver.get(url);
                if(usernameAndPass.length==0) {
                    mochaDriver.findElement(By.xpath("//*[@name='email']")).sendKeys(BaseTest.getValue("UserName"));
                    mochaDriver.findElement(By.xpath("//*[@name='password']")).sendKeys(BaseTest.getValue("Password"));
                }else{
                    mochaDriver.findElement(By.xpath("//*[@name='email']")).sendKeys(usernameAndPass[0]);
                    mochaDriver.findElement(By.xpath("//*[@name='password']")).sendKeys(usernameAndPass[1]);
                }
                boolean flag=false;
                while (!flag){
//                    try {
                        Reporter.log("login button enabled ?????? "+mochaDriver.findElement(By.xpath("//button[text()='Log in']")).isEnabled(),true);
                        flag=mochaDriver.findElement(By.xpath("//button[text()='Log in']")).isEnabled();
                        SeleniumUtils.waitForElementToClickable(mochaDriver,mochaDriver.findElement(By.xpath("//button[text()='Log in']")),10);
                        mochaDriver.findElement(By.xpath("//button[text()='Log in']")).click();
//                        SeleniumUtils.waitAndClick(mochaDriver,mochaDriver.findElement(By.id("submitBtn")));
//                    }catch (Exception e){
//                        // Code Review Pending Gaurav
//                        Reporter.log("Something went wrong while login in :",true);
//                        Reporter.log("Trying again to login ",true);
//                        mochaDriver.findElement(By.xpath("//*[@type='email']")).sendKeys(BaseTest.getValue("UserName"));
//                        mochaDriver.findElement(By.xpath("//*[@type='password']")).sendKeys(BaseTest.getValue("Password"));
//                        Reporter.log("login button enabled ?????? "+mochaDriver.findElement(By.id("submitBtn")).isEnabled(),true);
//                        flag=mochaDriver.findElement(By.id("submitBtn")).isEnabled();
////                        mochaDriver.findElement(By.id("submitBtn")).click();
//                        SeleniumUtils.waitAndClick(mochaDriver,mochaDriver.findElement(By.id("submitBtn")));
//                    }

                }

                SeleniumUtils.waitForPageLoaded(mochaDriver);
                if(mochaDriver.findElements(By.xpath(newAccountSwitchLoc)).size()>0){
                    SeleniumUtils.waitAndClick(mochaDriver,mochaDriver.findElement(By.xpath(expandLogoutOption)));
                    SeleniumUtils.waitAndClick(mochaDriver,mochaDriver.findElement(By.xpath(switchToClevertapLoc)));
                    mochaDriver.switchTo().frame(mochaDriver.findElement(By.id("wiz-iframe-intent")));
                    SeleniumUtils.pause(2);
                    mochaDriver.findElement(By.xpath("//a[text()='Switch to Classic']")).click();
                }
                    closePopup(mochaDriver);
//                SeleniumUtils.removePopup(mochaDriver, BaseTest.getValue("PopupCloseBtnOnLogin"));
//                    if (mochaDriver.findElements(By.xpath("//a[@class='close-experience-banner']")).size()>0){
//                        SeleniumUtils.removePopup(mochaDriver, BaseTest.getValue("PopupCloseBtnOnLogin"));
//                        try {
//                            SeleniumUtils.waitAndClick(mochaDriver,mochaDriver.findElement(By.xpath("//a[@class='close-experience-banner']")));
//                        } catch (Exception e) {
//                            Reporter.log("no info bar found ",true);
//                        }
//                    }
//                WebElement accountName = mochaDriver.findElement(By.xpath(rightNavAccountName));
                List<WebElement> rightNavAccountNames = mochaDriver.findElements(By.xpath(rightNavAccountName));
                WebElement accountName = null;
                int count = 3;
                while(rightNavAccountNames.size()== 0 && count != 0){
                    SeleniumUtils.pause(1);
                    rightNavAccountNames = mochaDriver.findElements(By.xpath(rightNavAccountName));
                    count--;
                }
                if(rightNavAccountNames.size()==0){
                    Reporter.log("Right Nav Account Name not found",true);
                }else{
                   accountName = rightNavAccountNames.get(0);
                }
                currentAccountName = accountName.getText().trim();
                Reporter.log(("Current account name is : " + currentAccountName));
                Reporter.log("account to be logged in as : "+ BaseTest.getValue("AccountName"));
                if (accountName.getText().equalsIgnoreCase(BaseTest.getValue("AccountName"))) {
                    Reporter.log(("Logged into valid account"));
                } else {
                    mochaDriver.findElement(By.id(curdButton)).click();
                    SeleniumUtils.performClick(mochaDriver, mochaDriver.findElement(By.xpath(accountList + BaseTest.getValue("AccountName") + "')]")));
                    SeleniumUtils.performClick(mochaDriver, mochaDriver.findElement(By.xpath(subMainAccount + BaseTest.getValue("MainRequiredAccountName") + "')]")));
                }
            } else {
                closePopup(mochaDriver);
                if (!(prevMenu.equalsIgnoreCase("Settings") && menuName.equalsIgnoreCase("Settings"))) {
                    try {
                        WebElement menu = mochaDriver.findElement(By.xpath(leftNavBackToMainMenu));
                        SeleniumUtils.performClick(mochaDriver, menu);
                    } catch (Exception e) {
                        //Do Nothing
                    }
                }
            }

            /*handling top bar toggle */

//            if (mochaDriver.findElement(By.xpath("//*[contains(@class,'js-topbarToggle')]")).getAttribute("data-original-title").equalsIgnoreCase("Expand menu")) {
//                WebElement hamburgerEle = mochaDriver.findElement(By.className("hamburger-inner"));
//                SeleniumUtils.moveToElement(hamburgerEle, mochaDriver);
//                SeleniumUtils.performClick(mochaDriver, hamburgerEle);
//            }

            SeleniumUtils.waitForPageLoaded(mochaDriver);
            if(mochaDriver.findElements(By.xpath(newAccountSwitchLoc)).size()>0){
                SeleniumUtils.waitAndClick(mochaDriver,mochaDriver.findElement(By.xpath(expandLogoutOption)));
                SeleniumUtils.waitAndClick(mochaDriver,mochaDriver.findElement(By.xpath(switchToClevertapLoc)));
                mochaDriver.switchTo().frame(mochaDriver.findElement(By.id("wiz-iframe-intent")));
                SeleniumUtils.pause(2);
                mochaDriver.findElement(By.xpath("//a[text()='Switch to Classic']")).click();
            }

            try {
                if (menuName != null && !menuName.isEmpty() && (forceNavigate || !prevMenu.equalsIgnoreCase(menuName))) {
                    if (menuName.equalsIgnoreCase("Settings")) {
                        leftNavMenuXpath = leftNavSettingsMenu;
                    }
                    if (!(menuName.equalsIgnoreCase("boards")) || !login) {
                        WebElement menu = mochaDriver.findElement(By.xpath(leftNavMenuXpath));
                        SeleniumUtils.waitAndClick(mochaDriver,menu);
//                        SeleniumUtils.performClick(mochaDriver, menu);
                    }
                }

                if (subMenuName != null && !subMenuName.isEmpty() && (forceNavigate || !prevSubMenu.equalsIgnoreCase(subMenuName))) {
                    if (menuName.equalsIgnoreCase("Settings")) {
                        subMenuXpath = leftNavSettingsSubMenu;
                    } else {
                        subMenuXpath = leftNavMenuXpath + leftNavSubmenuXpath;
                    }
                    //Todo:Added this code so that in case page is not loaded refresh page and the again try to find submenu :  code review gaurav

                    int subMenuRetryCount=3;
                    List<WebElement> subMenuList = mochaDriver.findElements(By.xpath(subMenuXpath));
                    while(subMenuList.size()==0 &&subMenuRetryCount!=0){
                        SeleniumUtils.pause(1);
                        subMenuList = mochaDriver.findElements(By.xpath(subMenuXpath));
                        subMenuRetryCount--;
                    }
                    if(subMenuRetryCount == 0){
                        mochaDriver.navigate().refresh();
                        SeleniumUtils.waitForPageLoaded(mochaDriver);
                    }

                    WebElement subMenu = mochaDriver.findElement(By.xpath(subMenuXpath));
                    SeleniumUtils.waitAndClick(mochaDriver, subMenu);
                    String popupShowingSubMenus[] = BaseTest.getValue("PopupShowingSubMenus").split(",");
                    for (String s : popupShowingSubMenus) {
                        if (s.equalsIgnoreCase(subMenuName)) {
                            SeleniumUtils.removePopup(mochaDriver, BaseTest.getValue("PopupCloseBtnXpath"));
                            SeleniumUtils.removePopup(mochaDriver, BaseTest.getValue("PopupCloseBtnOnLogin"));
                        }
                    }
                }

                if (superSubMenuName != null && !superSubMenuName.isEmpty() && (forceNavigate || !prevSuperSubMenu.equalsIgnoreCase(superSubMenuName))) {
                    WebElement superSubMenu = mochaDriver.findElement(By.xpath(leftNavSettingsSubMenu + leftNavSettingsSuperSubMenu));
                    SeleniumUtils.waitAndClick(mochaDriver, superSubMenu);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Reporter.log("Something went wrong while navigating to desired page" + ex.getMessage(),true);
            }
        }
        prevMenu = menuName;
        prevSubMenu = subMenuName;
        prevSuperSubMenu = superSubMenuName;
        forceNavigate = false;
    }

    /**
     * returns the base URL of the configured env.
     * Possible values are sk1,sk2,sk3,sk4,sk5,eu1 ...
     * @param env
     * @return {@link String}
     */
    public static String getUrl(String env)
    {
        return getValue(env.toLowerCase()+"_ui");
    }

    /**
     * returns the base URL of the configured env.
     * Possible values are sk1,sk2,sk3,sk4,sk5,eu1 ...
     * @param env
     * @return {@link String}
     */
    public static String getProdUrl(String env)
    {
        if(env.toLowerCase().contains("eu")){
            return getValue("eu1_ui");

        }else if(env.toLowerCase().contains("sk")){
            return getValue("sk1_ui");
        }else if (env.toLowerCase().contains("in")){
            return getValue("in1_ui");
        }
        Reporter.log("Wrong value was sent for environment in config file",true);
        return null;
    }

    public static void closePopup(WebDriver mochaDriver){
        if (mochaDriver.findElements(By.id("wiz-iframe-intent")).size()>0){

            mochaDriver.switchTo().frame(mochaDriver.findElement(By.id("wiz-iframe-intent")));
            List<WebElement> popups=mochaDriver.findElements(By.xpath("//*[@class='annoucement-popup__btn']"));
            if (popups.size()>0){
//                    SeleniumUtils.waitAndClick(mochaDriver,popups.get(0));
                SeleniumUtils.waitForElementToClickable(mochaDriver,popups.get(0),10);
                popups.get(0).click();
            }
        }

        if (mochaDriver.findElements(By.xpath("//a[@class='close-experience-banner']")).size()>0){
            SeleniumUtils.removePopup(mochaDriver, BaseTest.getValue("PopupCloseBtnOnLogin"));
            try {
                SeleniumUtils.waitAndClick(mochaDriver,mochaDriver.findElement(By.xpath("//a[@class='close-experience-banner']")));
            } catch (Exception e) {
                Reporter.log("no info bar found ",true);
            }
        }

    }
}