package com.clevertap.utils;

import com.clevertap.BaseTest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SeleniumUtils {

    private final static Logger logger = Logger.getLogger("SeleniumUtils");

    public static void moveToElementAndClick(WebElement element, WebDriver currentBrowserDriver) {
        Actions actions = new Actions(currentBrowserDriver);
        actions.moveToElement(element).build().perform();
        SeleniumUtils.pause(2);
        element.click();
    }

    public static void moveToElement(WebElement element, WebDriver currentBrowserDriver) {
        Actions actions = new Actions(currentBrowserDriver);
        actions.moveToElement(element).build().perform();
    }

    public static void scrollUpAndClickElement(WebDriver currentBrowserDriver, WebElement elemnt) {
        JavascriptExecutor js = (JavascriptExecutor) currentBrowserDriver;
        js.executeScript("window.scrollBy(0,-250)");
        elemnt.click();
    }

    public static void scrollUp(WebDriver currentBrowserDriver) {
        JavascriptExecutor js = (JavascriptExecutor) currentBrowserDriver;
        js.executeScript("window.scrollTo(0,0)");
    }

    public static void scrollDown(WebDriver currentBrowserDriver) {
        JavascriptExecutor js = (JavascriptExecutor) currentBrowserDriver;
        js.executeScript("window.scrollBy(0,1000)");


    }

    public static void scrollDown(WebDriver currentBrowserDriver, String scrollByWhatPixel) {
        JavascriptExecutor js = (JavascriptExecutor) currentBrowserDriver;
        js.executeScript("window.scrollBy(0," + scrollByWhatPixel + ")");
    }

    public static void scrollToBottom(WebDriver currentBrowserDriver) {
        JavascriptExecutor js = (JavascriptExecutor) currentBrowserDriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }


    public static void scrollDownLittle(WebDriver currentBrowserDriver) {
        JavascriptExecutor js = (JavascriptExecutor) currentBrowserDriver;
        js.executeScript("window.scrollBy(0,400)");


    }

    public static void scrollElementIntoView(WebDriver currentBrowserDriver,WebElement webElement){
        JavascriptExecutor js = (JavascriptExecutor) currentBrowserDriver;
        js.executeScript("arguments[0].scrollIntoView();", webElement);
    }


    /*This method will delete files older than n days
     * *this method is capable to take multiple folders at a time**/

    public static void deleteOlderFiles(int days, String... folderPath) {
        for (String folder : folderPath) {
            String dirPath = System.getProperty("user.dir") + folder;
            File directotry = new File(dirPath);
            if (directotry.exists()) {
                File[] files = directotry.listFiles();

                final long purgeTime =
                        System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000);

                for (File file : files) {
                    if ((file.lastModified() < purgeTime) && (file.getName().endsWith(".html")) || (file.getName().endsWith(".png"))) {
                        file.delete();
                    }
                }

                if (folder.equals("/reports/")) {
                    files = directotry.listFiles();
                    if (files.length > 10) {
                        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

                        for (int i = 10; i < files.length; i++) {
                            files[i].delete();
                        }
                    }
                }
            }
        }
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) {
        String encodedFile="";
        deleteOlderFiles(1);
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + "/output/FailedTestsScreenshots/"+ dateName + ".png";
        File finalDestination = new File(destination);
        try {
            FileUtils.copyFile(source, finalDestination);
            File f = new File(destination);
            FileInputStream fileInputStreamReader = new FileInputStream(f);
            byte[] bytes = new byte[(int) f.length()];
            fileInputStreamReader.read(bytes);
            encodedFile = new String(Base64.encodeBase64(bytes), "UTF-8");
            encodedFile = "data:image/png;base64," + encodedFile;
        }
        catch (IOException io){
            io.printStackTrace();
        }
        return encodedFile;
    }

    public static void elementHighlighter(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    public static boolean isClickable(WebElement el, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(el));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void waitForElementToLoad(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            Reporter.log("Inside waitForElementToLoad() catch -Element was not visibile within given time",true);
        }
    }

    /*Below method returns element one by one even after page refresh without any stale element exception*/

    public static WebElement getElementsOnPageRefresh(WebDriver driver, String locator) throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.xpath(locator));
        WebElement elementToReturn = null;
        for (WebElement element : elements) {
            try {
                SeleniumUtils.elementHighlighter(driver, element);
                elementToReturn = element;
                break;
            } catch (StaleElementReferenceException ex) {
                elements = driver.findElements(By.xpath(locator));
                elementToReturn = elements.get(0);
                SeleniumUtils.elementHighlighter(driver, elementToReturn);
                break;
            }
        }
        return elementToReturn;
    }

    /*This method is used to click on curd button i.e three dotted buttons, to check delete, edit and clone*/
    public static void clickCurdButton(WebDriver driver, String roleName) {
        String firstXpath = "//table[contains(@class,'datatable-resultset')]/tr//span[text()='";
        String secondXpath = "']/../../following-sibling::td[5]";

        String finalXpath = firstXpath + roleName + secondXpath;
        WebElement singleElement = driver.findElement(By.xpath(finalXpath));
        singleElement.click();
    }

    public static void performClick(WebDriver driver, WebElement elementToBeClicked) {
        WebDriverWait wait = new WebDriverWait(driver, 10);//dashboard is responding slowly, change again
//        SeleniumUtils.elementHighlighter(driver, elementToBeClicked);
        wait.until(ExpectedConditions.elementToBeClickable(elementToBeClicked));
        JavascriptExecutor ex = (JavascriptExecutor) driver;
        ex.executeScript("arguments[0].click()", elementToBeClicked);


    }

    public static String getElementText(WebDriver driver, WebElement elementToBeRead) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        SeleniumUtils.elementHighlighter(driver, elementToBeRead);
        wait.until(ExpectedConditions.visibilityOf(elementToBeRead));
        return elementToBeRead.getText();


    }

    public static void enterInputText(WebDriver driver, WebElement element, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        SeleniumUtils.elementHighlighter(driver, element);
        boolean textBoxEmpty=false;
        element.clear();
        while (!textBoxEmpty){
            element.clear();
            textBoxEmpty=true;
        }
        element.sendKeys(text);
    }

    public static WebElement getVisibility(By locator, int timeout, WebDriver driver) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }


    public static void selectBootStrapDrpDwnItem(WebDriver driver, WebElement elementToGenerateDrpDwn, List<WebElement> listToBeSelectedFrom, String optionToBeSelected) {
        Actions action = new Actions(driver);
        action.moveToElement(elementToGenerateDrpDwn).click().build().perform();

        for (WebElement element : listToBeSelectedFrom) {
            if (element.getAttribute("innerHTML").trim().equalsIgnoreCase(optionToBeSelected)) {
                element.click();
            }
        }
    }


    public static Map<String, Integer> getTooltipText(WebDriver driver, String pointsOnGraphXPath, String tootipXPath) {

        List<WebElement> tooltips = driver.findElements(By.xpath(pointsOnGraphXPath));

        HashMap<String, Integer> hashMap = new HashMap<>();
        for (WebElement tooltip : tooltips) {
            Actions actions = new Actions(driver);

            actions.clickAndHold(tooltip).perform();
            List<WebElement> rects = driver.findElements(By.xpath(tootipXPath)); /*identify the rectangle tooltip box*/

            for (WebElement element : rects) {
                if (!element.getText().isEmpty()) {
                    String tooltipText = element.getText();

                    String[] tooltipTextArray;
                    if (tooltipText.contains("(")) {

                        tooltipText = tooltipText.substring(0, tooltipText.indexOf('('));
                        tooltipTextArray = new String[1];
                        tooltipTextArray[0] = tooltipText;
                    } else if (tooltipText.contains("\n")) {
                        tooltipTextArray = tooltipText.split("\\n");
                    } else {
                        tooltipTextArray = new String[1];
                        tooltipTextArray[0] = tooltipText;
                    }

                    for (String s : tooltipTextArray) {
                        int totalValue = 0;
                        if(!s.contains(":")){
                            continue;
                        }
                        String key = s.split(":")[0].trim();
                        int value = Integer.parseInt(s.split(":")[1].trim().replaceAll(",", ""));
                        if (hashMap.containsKey(key)) {
                            totalValue = value + hashMap.get(key);
                            hashMap.put(key, totalValue);
                        } else {
                            hashMap.put(key, value);
                        }
                    }
                }
            }
        }
        return hashMap;
    }

    public void deleteExistingItems(WebDriver driver, WebElement confirm) throws InterruptedException {
        String xpath = "//table[contains(@class,'datatable-resultset')]/tr//span[contains(text(),'Automation')]/../../following-sibling::td[5]";
        String deleteIconXpath = "//*[contains(@id,'__BV_popover') ]//div[@class='action-icons-container']/span[3]";

        WebElement element = SeleniumUtils.getElementsOnPageRefresh(driver, xpath);

        do {

            element.click();
            WebElement deleteIcon = SeleniumUtils.getElementsOnPageRefresh(driver, deleteIconXpath);
            deleteIcon.click();
            Thread.sleep(1000);
            confirm.click();
            Thread.sleep(1000);
            confirm.click();
            driver.navigate().refresh();
            element = SeleniumUtils.getElementsOnPageRefresh(driver, xpath);
        } while (element != null);
    }

    public static void openElementInNewTab(WebDriver driver, WebElement ele) {
        String openLinkInNewTab = Keys.chord(Keys.COMMAND, Keys.RETURN);
        ele.sendKeys(openLinkInNewTab);
    }


    public static void waitForPageLoaded(WebDriver driver) {
        ExpectedCondition expectation = new ExpectedCondition() {
            @Override
            public Object apply(Object o) {
                return null;
            }

            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState").equals("complete");
            }
        };
        Wait<WebDriver> wait = new WebDriverWait(driver, 10);
        try {
            wait.until(expectation);
        } catch (Exception error) {
            //logger.fatal("something went wrong", error);
        }
    }

    /*Below method will hold the execution for specified time*/
    public static void pause(long timeOut) {
        try {
            TimeUnit.SECONDS.sleep(timeOut);
        } catch (InterruptedException ex) {
            //
        }
    }


    public static void clickElementWithoutStaleException(WebDriver driver, WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement localElement = element;
        int counter = 0;

        while (counter < 5) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(localElement));
                localElement.click();
            } catch (StaleElementReferenceException se) {
                counter++;
                localElement = element;
            }
        }

    }

    public static void switchToLastWindowHandle(WebDriver driver) {
        String lastWinHandle = "";
        Set<String> winHandles = driver.getWindowHandles();
        for (String winHandle : winHandles) {
            lastWinHandle = winHandle;
        }
        driver.switchTo().window(lastWinHandle);
    }

    public static String getCurrentLocalDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a");
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }

    public static void removePopup(WebDriver driver, String popupCloseBtnXpath) {
        try {
            driver.switchTo().frame(driver.findElement(By.id("wiz-iframe-intent")));
            driver.findElement(By.xpath(popupCloseBtnXpath)).click();

        } catch (NoSuchElementException nse) {
            Reporter.log("Something went wrong while removing popup , for xpath :" + popupCloseBtnXpath + " Reason : " + nse.getMessage(),true);
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (!(obj instanceof String) && obj == null){
            return true;
        }
        else{
            try{
                if(obj == null || obj.toString().isEmpty()){
                    return true;
                }
                else{
                    return false;
                }
            }catch(NullPointerException e){
                logger.error("isNullOrEmpty: Null string while checking the the string is null or Empty.");
                return true;
            }
        }
    }


    public static void selectElementFromDropDownItem(WebDriver driver, WebElement dropDownToClick, String elementToBeSelected){
        Actions action = new Actions(driver);
        action.moveToElement(dropDownToClick).click().build().perform();
        action.moveToElement(driver.findElement(By.xpath("//*[@class='evtOuter']//li[contains(text(),'"+elementToBeSelected+"')]"))).click().build().perform();
    }

    public static void waitForElementToLoad(WebDriver driver, WebElement element,int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            Reporter.log("Inside waitForElementToLoad() catch -Element was not visibile within given time",true);
        }
    }

    public static void waitForElementToClickable(WebDriver driver, WebElement element,int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver,timeout);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            Reporter.log("Inside waitForElementToClickable() catch -Element was not clickable within given time",true);
        }
    }

    public static void waitAndClick(WebDriver driver, WebElement element) {
            WebDriverWait wait = new WebDriverWait(driver,20);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);

    }

    /***
     * This method checks whether an alert is present, if present switches the driver to alert
     * NOTE : This method only switches to alert and does not handle/dismiss it, caller needs to
     * take care of alert depending upon the requirement
     * @param driver
     * @return Object of #Alert which if present else returns null
     */
    public static Alert switchToAlertIfPresent(WebDriver driver){

        Alert alert ;
        try {
             alert = driver.switchTo().alert();
             Reporter.log("Switching to alert",true);
        }catch(NoAlertPresentException n){
            alert = null;
        }
        return alert;
    }
}
