package com.clevertap.ui.pages.engage_page;

import com.clevertap.BaseTest;
import com.clevertap.ui.RestApiUtil.RestApi;
import com.clevertap.utils.Data;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.clevertap.BaseTest.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class ConversationsPage {

    private WebDriver driver;
    private Logger logger = Logger.getLogger(getClass().getSimpleName());
    @FindBy(xpath = "//div[@class='datatable-collection']//tr")
    private List<WebElement> usersConvList;
    @FindBy(xpath = "(//div[@class='message-box'])/div[last()]/div[last()]/div/span[1]")
    private WebElement latestMessageFromChat;
    private RestApi restAPI;

    @FindBy(className = "text-input")
    private WebElement chatInputBox;

    public ConversationsPage(WebDriver driver){
        this.driver = driver;
        restAPI = new RestApi();
        PageFactory.initElements(this.driver, this);
    }

    public int getNumberOfActiveConv(){
        int size = 0;
        if(!SeleniumUtils.isNullOrEmpty(usersConvList)){
            size = usersConvList.size();
        }
        return size;
    }

    public String getLatestMessageFromTable(int convNum){
        return driver.findElement(By.xpath("//div[@class='datatable-collection']//tr[" + convNum + "]//td[3]//span")).getAttribute("innerHTML").trim();
    }

    public String getLatestMessageFromChat(int convNum){
        String message = "";
        if(!SeleniumUtils.isNullOrEmpty(usersConvList)){
            usersConvList.get(convNum - 1).click();
            chatInputBox.sendKeys(Keys.chord(Data.randomAlphaNumeric("AutoTest message ",4) + Keys.RETURN));

            SeleniumUtils.pause(1);
            try {
                message = latestMessageFromChat.getAttribute("innerHTML").trim();
            }
            catch(NoSuchElementException e){
                logger.error("No messages found in conversations section.");
            }

        }
        return message;
    }

    public void raiseEventRunningWAppCampaign(){

        Map<Object, Object> evtDetails = new HashMap<>();
        JSONArray evtArray = new JSONArray();

        evtDetails.put("objectId", BaseTest.getValue("TestProfileGUID"));
        evtDetails.put("evtName", "Header");
        evtDetails.put("type", "event");

        evtArray.add(evtDetails);
        JSONObject payload = new JSONObject();
        payload.put("d", evtArray);

        restAPI.pushAction(payload, BaseTest.getValue("EventsUploadApi"), "POST");


    }

    public void sendWhatsAppMessageAsAUser(String message){
        JSONObject payload = new JSONObject();
        JSONObject from = new JSONObject();
        JSONObject to = new JSONObject();
        JSONObject messageObj = new JSONObject();

        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

        payload.put("message_uuid", "5841dc82-cf3c-44aa-982f-" + Data.randomAlphaNumeric("", 12).toLowerCase());
        to.put("number", "447418342145");
        to.put("type", "whatsapp");
        payload.put("to", to);
        from.put("number", getValue("MobileNumberWhatsApp"));
        from.put("type", "whatsapp");
        payload.put("from", from);
        payload.put("timestamp", format.format(new Date()));

        payload.put("direction", "inbound");
        JSONObject content = new JSONObject();
        content.put("type", "text");
        content.put("text", message);
        messageObj.put("content", content);
        payload.put("message", content);

        String accountID = getValue("X-CleverTap-Account-Id");
        String passcode = getValue("X-CleverTap-Passcode");
        String modifiedURL = getValue("EventsUploadApi") + "/" + accountID;
        restAPI.pushAction(payload, modifiedURL, "POST",  accountID, passcode);
    }
}
