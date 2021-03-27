package com.clevertap.ui.pages;


import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class Integration {

    WebDriver driver;

    @FindBy(xpath = "/html/body/div[5]/div[2]/nav/ul/li[6]/a/i")
    private static WebElement leftNav;
    @FindBy(xpath = "/html/body/div[5]/div[2]/nav/ul/li[6]/div/ul/li[4]/a")
    private WebElement integration;
    @FindBy(xpath = "//*[@class='drop-arrow']")
    private WebElement dropDownArrow;
    @FindBy(xpath = "//*[@id='dropDownBar']//li")
    private List<WebElement> appDropdownList;
    @FindBy(xpath = "//*[@id='test-acc-head']")
    private WebElement testPushNotification;
    @FindBy(xpath = "//*[@id='android_tgt_message_title']")
    private WebElement messageTitle;
    @FindBy(xpath = "//*[@id='android_tgt_message_text']")
    private WebElement messageBody;
    @FindBy(xpath = "//*[@id='gcm_tokens']")
    private WebElement gsmToken;
    @FindBy(xpath = "//*[@id='integrateApp']//div[6]//input")
    private WebElement sendPushNotification;
    @FindBy(xpath = "//*[contains(@class,'sweet-alert')]//p")
    private WebElement pushNotificationResult;
    @FindBy(xpath = "//*[contains(@class,'confirm')]")
    private WebElement confirm;
    @FindBy(xpath = "//*[@class='integration-steps__contents']")
    private WebElement textElement;
    @FindBy(xpath = "//*[@class='integration-steps__contents']//a[text()='stepwise iOS app integration guide']")
    private WebElement textElement2;
    @FindBy(xpath = "//*[@class='integration-steps__contents']//a[text()='Win RT app integration guide']")
    private WebElement textElement3;
    @FindBy(xpath = "//*[@class='integration-steps__contents']//a[text()='stepwise website integration guide']")
    private WebElement textElement4;
    @FindBy(xpath = "//*[@id='step3']/div[2]/div[5]/div/div/div[3]/div/div[2]/a")
    private WebElement textElement5;
    @FindBy(xpath = "//*[@class='integration-steps__contents']//a[text()='Click here']")
    private WebElement textElement6;
    @FindBy(xpath = "//*[@id='step3']/div[2]/div[5]/div/div/div[3]/div/div[2]/a")
    private WebElement textElement7;
    //  @FindBy(xpath = "//*[@id='step3']/div[2]/div[2]/div[1]//a[contains(text(),'Skip URL')]")
//  private WebElement URl;
    @FindBy(xpath = "(//*[contains(@class,'skip-btn')])[2]")
    private WebElement skipUrl;
    @FindBy(xpath = "//*[@id='step3']/div[2]/div[2]/div[2]/div[2]/div[2]/div[5]")
    private WebElement testPush;
    @FindBy(xpath ="//*[@id='ios_tgt_message_title']")
    private WebElement messageTitle2;
    @FindBy(xpath="//*[@id='ios_tgt_message_text']")
    private WebElement messageBody2;
    @FindBy(xpath = "//*[@id='apns_tokens']")
    private WebElement apnsToken;
    @FindBy(xpath="(//*[contains(@class,'pushTest')])[2]")
    private WebElement sendPushNotification2;
    @FindBy(xpath="//*[@id='step3']/div[2]/div[3]/div[2]/div[2]/div[2]/div[5]")
    private WebElement testpushWindow ;
    @FindBy(xpath="//*[@id='windows_tgt_message_title']")
    private WebElement messageTitle3;
    @FindBy(xpath="//*[@id='windows_tgt_message_text']")
    private WebElement messageBody3;
    @FindBy(xpath="//*[@id='test-push-winuris']")
    private WebElement uri1;
    @FindBy(xpath="//input[@value ='Send test push notification'][@data-platform='windows']")
    private WebElement sendPushNotification3;




    public void launchIntegrationPage() {

        try {
            leftNav.click();
            integration.click();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAppsFromDropdown() {
        List<String> appList = new ArrayList<String>();
        dropDownArrow.click();
        for (WebElement item : appDropdownList) {
            appList.add(item.getText());

        }
        return appList;
    }

    public void selectSpecificItemFromDropdown(String appName) {
        dropDownArrow.click();
        for (WebElement item : appDropdownList) {
            if (item.getText().contains(appName)) {
                item.click();
                testPush.click();
                break;
            }
        }
    }



    public void selectWindowsFromDropdown(String appName1){
        SeleniumUtils.scrollUp(this.driver);
        dropDownArrow.click();
        for(WebElement item: appDropdownList){
            if(item.getText().contains(appName1)){
                item.click();
                testpushWindow.click();
                break;
            }
        }
    }

    public List selectIndividualApp(){
        List status=new ArrayList();
        SeleniumUtils.scrollUp(this.driver);

        dropDownArrow.click();


        try {
            for (WebElement item : appDropdownList) {
                System.out.println(item.getAttribute("innerText"));


                if (item.getAttribute("innerText").contains("Android")) {
                    item.click();
                    /*write code to check the message*/
//                   WebElement list = driver.findElement(By.xpath("//*[@id='integrateApp']//a[contains(text(),'stepwise android app integration guide')]");
                    //                String text = textElement.getText();
//                    System.out.println("******* "+driver.getPageSource());

//                    System.out.println("***************"+driver.findElement(By.xpath("//*[@class='integration-steps__contents']//a")).getAttribute("innerText"));


                    if (driver.getPageSource().contains("stepwise android app integration guide")) {
                        status.add(true);
                    } else {
                        status.add(false);

                    }


                } else if (item.getAttribute("innerText").contains("iOS")) {
                    item.click();
                    Thread.sleep(4000);
                    skipUrl.click();


                    //   Thread.sleep(2000);
                    //String text = textElement2.getText();
                    // String text=driver.findElement(By.xpath("//*[@class='integration-steps__contents']//a")).getAttribute("innerText");
                    //   System.out.printf("*******", text);


                    if(driver.getPageSource().contains("stepwise iOS app integration guide")){
                        //if (text.contains("stepwise iOS app integration guide")) {
                        status.add(true);

                    } else {
                        status.add(false);
                    }

                } else if (item.getAttribute("innerText").contains("Windows")) {
                    item.click();
                    if(driver.getPageSource().contains("Win RT app integration guide")) {
                        //  if (text.contains("Win RT app integration guide")) {
                        status.add(true);
                    } else {
                        status.add(false);
                    }


                } else if (item.getAttribute("innerText").contains("Website")) {
                    item.click();
                    if(driver.getPageSource().contains("stepwise website integration guide")) {
                        //if (text.contains("stepwise website integration guide")) {
                        status.add(true);
                    } else {
                        status.add(false);
                    }

                } else if (item.getAttribute("innerText").contains("Magento")) {
                    item.click();
                    if(driver.getPageSource().contains("CleverTap automatically creates user profiles for your Magento users.")){
                        // if (text.contains("CleverTap automatically creates user profiles for your Magento users.")) {
                        status.add(true);
                    } else {
                        status.add(false);
                    }

                } else if (item.getAttribute("innerText").contains("Shopify")) {
                    item.click();
                    if(driver.getPageSource().contains("Click here")){
                        // if (text.contains("Click here")) {
                        status.add(true);
                    } else {
                        status.add(false);
                    }

                } else if (item.getAttribute("innerText").contains("WooCommerce")) {
                    if(driver.getPageSource().contains("CleverTap automatically creates user profiles for your WooCommerce users.")){
                        // if (text.contains("CleverTap automatically creates user profiles for your WooCommerce users.")) {
                        status.add(true);
                    } else {
                        status.add(false);
                    }

                }
                dropDownArrow.click();
                Thread.sleep(3000);

            }
        }catch(Exception e){
            e.printStackTrace();
            return status;

        }
        return status;
    }




    public String testPushNotifiucation(String title, String body, String token) throws InterruptedException {
        String result = null;
        testPushNotification.click();
        if (title != null) {
            messageTitle.sendKeys(title);
            if (body != null) {
                messageBody.sendKeys(body);
                gsmToken.sendKeys(token);
                sendPushNotification.click();
                Thread.sleep(2000); /*some wait is required here as DOM takes time to load the specific message*/

                result = pushNotificationResult.getAttribute("innerText");
                confirm.click();
            } else {
                System.out.println("Enter some message body");
            }

        } else {
            System.out.println("Enter a some message title");
        }
        return result;


    }



    public String sendTestPush(String title2, String body2, String token2) throws InterruptedException {
        String result = null;

        if (title2 != null) {
            messageTitle2.sendKeys(title2);
            if (body2 != null) {
                messageBody2.sendKeys(body2);
                apnsToken.sendKeys(token2);
                sendPushNotification2.click();
                Thread.sleep(2000); /*some wait is required here as DOM takes time to load the specific message*/

                result = pushNotificationResult.getAttribute("innerText");
                confirm.click();


            } else {
                System.out.println("Enter some message body");
            }

        } else {
            System.out.println("Enter some message title");
        }
        return result;


    }

    public String sendTestPushWindows(String title3, String body3, String token3) throws InterruptedException {
        String result = null;
        if (title3 != null) {
            messageTitle3.sendKeys(title3);
            if (body3 != null) {
                messageBody3 .sendKeys(body3);
                uri1.sendKeys(token3);
                sendPushNotification3.click();
                Thread.sleep(2000); /*some wait is required here as DOM takes time to load the specific message*/

                result = pushNotificationResult.getAttribute("innerText");
                confirm.click();


            } else {
                System.out.println("Enter some message body");
            }

        } else {
            System.out.println("Enter some message title");
        }
        return result;


    }



    public Integration(WebDriver driverFromPreviousBrowser) {
        this.driver = driverFromPreviousBrowser;
        PageFactory.initElements(driverFromPreviousBrowser, this);
    }
}