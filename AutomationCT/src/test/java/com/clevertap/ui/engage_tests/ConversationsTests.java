package com.clevertap.ui.engage_tests;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.engage_page.ConversationsPage;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TestCaseGroups;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ConversationsTests extends BaseTest {

    private Logger logger = Logger.getLogger(getClass().getSimpleName());
    private WebDriver driver;
    private ConversationsPage conversationsPage;

    @BeforeClass(alwaysRun=true)
    public void initialize() {
        driver = getDriver();
        Mocha.openLeftNavMenu(driver,true, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONVERSATOINS.toString(), "");
        conversationsPage = new ConversationsPage(driver);
    }

    @Test (description = "Validate that most recent message in chat window and table are same.",
            groups = {TestCaseGroups.ALL, TestCaseGroups.WHATSAPPCRITICAL})
    public void verifyLatestConversationMessages(){
        logger.info("<<<<<verifyLatestConversationMessages");
        List<String> tablesMessages = new ArrayList<>();
        List<String> recentChatMessages = new ArrayList<>();
        int numberOfActiveConv = conversationsPage.getNumberOfActiveConv();

        //If Non active users, then make a user Active.
        // Steps: First send a campaign message and then reply from user mobile number to Business account.
        // This way only he will be visible in active tab on conversaion.
        if(numberOfActiveConv == 1){
            conversationsPage.raiseEventRunningWAppCampaign();
            SeleniumUtils.pause(20);
            conversationsPage.sendWhatsAppMessageAsAUser("Automation Message");
            SeleniumUtils.pause(5);
        }

        for(int i = 1; i <= numberOfActiveConv; i++){
            recentChatMessages.add(conversationsPage.getLatestMessageFromChat(i));
        }
        for(int i = 1; i <= numberOfActiveConv; i++){
            tablesMessages.add(conversationsPage.getLatestMessageFromTable(i));
        }
        boolean validateMessages = true;
        for(int i = 0; i < tablesMessages.size(); i++){
            if(!recentChatMessages.get(i).contains(tablesMessages.get(i))){
                validateMessages = false;
                break;
            }
        }
        Assert.assertTrue(validateMessages,
                "Most recent chat messages are matching with the messages in table.");
        logger.info(">>>>verifyLatestConversationMessages");
    }
}
