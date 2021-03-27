package com.clevertap.ui.pages.journey_page;


import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.SeleniumUtils;
import org.javatuples.Quartet;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JourneyHomePage {

    private static int x = 0;
    private static int y = 0;
    private static int nodeCount = 0;
    private  static Map<String,Integer> nodeFrequency=new HashMap<String,Integer>();
    private static Set<String> createdNodes = new HashSet<String>();
    private static String createNodeJson = "";
    private static String linkDataNodeJson ="";
    private static String exitModelJson="";
    private boolean firstNode=false;
    private String finalJourneyJson="";
    private WebDriver driver;
    private SweetAlert sweetAlert;
    private String journeyName;
    private String setCSVKey;
    public static Map<String, Object> wholeJourney = new HashMap<String,Object>();
    private static WhenPage whenPage;
    public static final String DIV_CLASS_FILTERBTN_AND_TEXT = "//div[@class='filterbtn' and text()='";

    @FindBy(id = "publishJourney")
    public WebElement journeyPublishBtn;
    @FindBy(id = "jname")
    public WebElement journeyNameTextBox;
    @FindBy(className = "confirm")
    public WebElement saveJourneyBtn;
    @FindBy(xpath = "//div[@class='pull-right']//a")
    public WebElement createJourneyBtn;
    @FindBy(xpath = "//a[contains(@class,'campaign-edit')]")
    public WebElement editjourneyBtn;
    @FindBy(xpath = "//a[contains(@class,'campaign-clone')]")
    public WebElement cloneJourneyBtn;

    public JourneyHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        sweetAlert = new SweetAlert(this.driver);
    }


    public enum SegmentNodeNames {
        ACTION("Action"),
        INACTION("inaction"),
        PASTBEHAVIOR("Past behavior"),
        DATETIME("Date time"),
        JOURNEYTRIGGER("Journey Trigger"),
        PAGEVISIT("Page visit"),
        REFERRERENTRY("Referrer entry"),
        PAGECOUNT("Page count");

        private String text;
        SegmentNodeNames(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public enum LinkNodeNames {
        ACTION("segment_action"),
        INACTION("segment_inaction"),
        PASTBEHAVIOR("segment_batch"),
        DATETIME("segment_date_time"),
        JOURNEYTRIGGER("segment_journey_trigger"),
        PAGEVISIT("segment_page_visit"),
        REFERRERENTRY("segment_page_referrer"),
        PAGECOUNT("segment_page_count"),
        PUSH("message_push"),
        SMS("message_sms"),
        EMAIL("message_email"),
        WEBPUSH("message_web_push"),
        WEBHOOK("message_webhook"),
        INAPP("message_inapp"),
        WEBPOPUP("message_web_pop_up"),
        INBOX("message_inbox"),
        EXITINTENT("message_web_exit_intent");

        private String text;
        LinkNodeNames(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    public enum ActionToPerform {
        Yes("segment_qualified"),
        No("segment_not_qualified"),
        Sent("message_sent"),
        Failed("message_not_sent"),
        Clicked("message_clicked"),
        Unreachable("message_not_reachable"),
        Viewed("message_viewed"),;

        private String text;
        ActionToPerform(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

//    public enum EngageNodeNames {
//        PUSH("Push"),
//        SMS("SMS"),
//        EMAIL("Email"),
//        WEBPUSH("Web Push"),
//        WEBHOOK("Webhook"),
//        INAPP("In-app"),
//        WEBPOPUP("Web Pop-up"),
//        INBOX("Inbox"),
//        EXITINTENT("Exit Intent");
//
//        private String text;
//        EngageNodeNames(String text) {
//            this.text = text;
//        }
//
//        @Override
//        public String toString() {
//            return text;
//        }
//    }

    /***
     * This method generates a jsonBody corresponding to the node which is to be created
     * @param nodeName - name of the Node to be created,
     *                 If there are more than one node to be created of the same name in the whole journey then append
     *                 the number to the nodeName.
     *                 Eg: For more than one action Nodes, nodeName would be, Action, Action2,Action3 and so on
     * @return JSON representation of the node to be created in the form of #String
     */
    private String getJsonBody(String nodeName){

        String actualNodeName=nodeName;
        String json="";
        String node="";
        nodeName=nodeName.replaceAll("[0-9]","");

        for (LinkNodeNames linkNodeNames: LinkNodeNames.values()) {
            if(nodeName.equalsIgnoreCase(linkNodeNames.toString())){
                node=linkNodeNames.name();
                break;
            }
        }
        switch (node) {

            case "ACTION":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Action\",\n" +
                        "      \"category\": \"segment\",\n" +
                        "      \"builder\": \"action\",\n" +
                        "      \"sourceSvg\": \"/images/journey/action_icon.svg?v=0b511ba61ecc90db66ef82c3051f5185\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/action_icon-colored.svg?v=8b5821de4a85089466a6ee5cbdf547a4\",\n" +
                        "      \"preferredType\": 1,\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled segment\",\n" +
                        "      \"dummyMessageTypeForWhen\": \"sms\",\n" +
                        "      \"delayKey\": \"action\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_qualified\",\n" +
                        "          \"linkTitle\": \"Yes\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\",      \"isEntry\": false\n" +
                        "    },";
                break;

            case "INACTION":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Inaction\",\n" +
                        "      \"category\": \"segment\",\n" +
                        "      \"builder\": \"inaction\",\n" +
                        "      \"sourceSvg\": \"/images/journey/inaction_icon.svg?v=86d5d32f9b3d1a85a69628ad14d6c11d\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/inaction_icon-colored.svg?v=ed51ac19fbd349f7b9dc0c143f2e6719\",\n" +
                        "      \"preferredType\": 1,\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled segment\",\n" +
                        "      \"dummyMessageTypeForWhen\": \"sms\",\n" +
                        "      \"delayKey\": \"inaction\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_qualified\",\n" +
                        "          \"linkTitle\": \"Yes\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_not_qualified\",\n" +
                        "          \"linkTitle\": \"No\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\",      \"isEntry\": false\n" +
                        "    },";
                break;

            case "PASTBEHAVIOR":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Past behavior\",\n" +
                        "      \"category\": \"segment\",\n" +
                        "      \"builder\": \"batch\",\n" +
                        "      \"sourceSvg\": \"/images/journey/pbs_icon.svg?v=fa2d47ff24360faa5285431781ba90cf\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/pbs_icon-colored.svg?v=eb88447efcf38794abdb4b9cef32d0eb\",\n" +
                        "      \"preferredType\": 0,\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled segment\",\n" +
                        "      \"dummyMessageTypeForWhen\": \"sms\",\n" +
                        "      \"delayKey\": \"batch\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_qualified\",\n" +
                        "          \"linkTitle\": \"Yes\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_not_qualified\",\n" +
                        "          \"linkTitle\": \"No\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\",      \"isEntry\": false\n" +
                        "    },";
                break;

            case "DATETIME":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Date time\",\n" +
                        "      \"category\": \"segment\",\n" +
                        "      \"builder\": \"date-time\",\n" +
                        "      \"sourceSvg\": \"/images/journey/date_time_icon.svg?v=e23c11c695dffaab178cf5934de14116\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/date_time_icon_left_nav.svg?v=c2409e75c02a8dde459da0408d834753\",\n" +
                        "      \"preferredType\": 0,\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled segment\",\n" +
                        "      \"dummyMessageTypeForWhen\": \"sms\",\n" +
                        "      \"delayKey\": \"date_time\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_qualified\",\n" +
                        "          \"linkTitle\": \"Yes\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\",      \"isEntry\": false\n" +
                        "    },";
                break;

            case "JOURNEYTRIGGER":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Journey Trigger\",\n" +
                        "      \"category\": \"segment\",\n" +
                        "      \"builder\": \"journey-trigger\",\n" +
                        "      \"sourceSvg\": \"/images/journey/journey-trigger-icon.svg?v=453b96a61eea22d25377ece418872f55\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/journey-trigger-icon.svg?v=453b96a61eea22d25377ece418872f55\",\n" +
                        "      \"preferredType\": 1,\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled segment\",\n" +
                        "      \"dummyMessageTypeForWhen\": \"sms\",\n" +
                        "      \"delayKey\": \"journey-trigger\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_qualified\",\n" +
                        "          \"linkTitle\": \"Yes\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\",      \"isEntry\": false\n" +
                        "    },";
                break;

            case "PAGEVISIT":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Page visit\",\n" +
                        "      \"category\": \"segment\",\n" +
                        "      \"sourceSvg\": \"/images/journey/page_visit_icon.svg?v=dc2422157b702107516e2705be3f0b31\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/page_visit_icon-colored.svg?v=254c83b5603e3dd5cbe50533202f8390\",\n" +
                        "      \"preferredType\": 1,\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 16\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled segment\",\n" +
                        "      \"dummyMessageTypeForWhen\": \"web-popup\",\n" +
                        "      \"builder\": \"visit-page.html?type=page\",\n" +
                        "      \"delayKey\": \"web_page\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_qualified\",\n" +
                        "          \"linkTitle\": \"Yes\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\",      \"isEntry\": false\n" +
                        "    },";
                break;

            case "REFERRERENTRY":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Referrer entry\",\n" +
                        "      \"category\": \"segment\",\n" +
                        "      \"sourceSvg\": \"/images/journey/referrer_entry_icon.svg?v=276ee09a5ef65bab6092948ad03b4dbf\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/referrer_entry_icon-colored.svg?v=02a95cc5f8d4dc1cb3aa8675b19a5ea5\",\n" +
                        "      \"preferredType\": 1,\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled segment\",\n" +
                        "      \"dummyMessageTypeForWhen\": \"web-popup\",\n" +
                        "      \"builder\": \"visit-page.html?type=referrer\",\n" +
                        "      \"delayKey\": \"web_page\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_qualified\",\n" +
                        "          \"linkTitle\": \"Yes\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\",      \"isEntry\": false\n" +
                        "    },";
                break;

            case "PAGECOUNT":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Page count\",\n" +
                        "      \"category\": \"segment\",\n" +
                        "      \"sourceSvg\": \"/images/journey/page_count_icon.svg?v=e1d3e858f4d88b3240e27048a6421ea3\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/page_count_icon-colored.svg?v=63072bfb5c5a2f8e5e665d201d9e179f\",\n" +
                        "      \"preferredType\": 1,\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 16,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled segment\",\n" +
                        "      \"dummyMessageTypeForWhen\": \"web-popup\",\n" +
                        "      \"delayKey\": \"web_page\",\n" +
                        "      \"builder\": \"visit-page.html?type=count\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"segment_qualified\",\n" +
                        "          \"linkTitle\": \"Yes\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\",      \"isEntry\": false\n" +
                        "    },";
                break;

            case "PUSH":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Push\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Send push\",\n" +
                        "      \"mode\": 2,\n" +
                        "      \"sourceSvg\": \"/images/journey/mobile-push-icon.svg?v=bae68e0e007231a72ca390265fa840be\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/mobile-push-icon-colored.svg?v=be6dd6e8bb069b46569fe6caecb6f5ce\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 16,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"offline\",\n" +
                        "      \"builder\": \"push\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_sent\",\n" +
                        "          \"linkTitle\": \"Sent\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_not_sent\",\n" +
                        "          \"linkTitle\": \"Failed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_clicked\",\n" +
                        "          \"linkTitle\": \"Clicked\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_not_reachable\",\n" +
                        "          \"linkTitle\": \"Unreachable\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

            case "SMS":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"SMS\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Send SMS\",\n" +
                        "      \"mode\": 1,\n" +
                        "      \"sourceSvg\": \"/images/journey/mobile-sms-icon.svg?v=b3cbe1803fd4d077c85b1b9ae7520755\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/mobile-sms-icon-colored.svg?v=278bb6836c4f71b8275caf7d0c4f5e96\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 16,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"offline\",\n" +
                        "      \"builder\": \"sms\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_sent\",\n" +
                        "          \"linkTitle\": \"Sent\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_not_sent\",\n" +
                        "          \"linkTitle\": \"Failed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_not_reachable\",\n" +
                        "          \"linkTitle\": \"Unreachable\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

            case "EMAIL":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Email\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Send email\",\n" +
                        "      \"mode\": 0,\n" +
                        "      \"sourceSvg\": \"/images/journey/email-icon.svg?v=1bee2122e52c88d66d16b7f940aa38ad\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/email-icon-colored.svg?v=997d4223f7344b42b871e93ae5ff3b21\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 20\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"offline\",\n" +
                        "      \"builder\": \"email\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_sent\",\n" +
                        "          \"linkTitle\": \"Sent\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_not_sent\",\n" +
                        "          \"linkTitle\": \"Failed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_viewed\",\n" +
                        "          \"linkTitle\": \"Viewed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_clicked\",\n" +
                        "          \"linkTitle\": \"Clicked\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_not_reachable\",\n" +
                        "          \"linkTitle\": \"Unreachable\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

            case "WEBPUSH":
                json += " {\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Web Push\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Send web push\",\n" +
                        "      \"mode\": 6,\n" +
                        "      \"sourceSvg\": \"/images/journey/web-push-icon.svg?v=40143b6d9b1d7ab08667dcef48cd28cd\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/web-push-icon-colored.svg?v=0d4092402a5ee90ad2c28e6cae8284f8\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 16\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"offline\",\n" +
                        "      \"builder\": \"browser\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_sent\",\n" +
                        "          \"linkTitle\": \"Sent\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_not_sent\",\n" +
                        "          \"linkTitle\": \"Failed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_viewed\",\n" +
                        "          \"linkTitle\": \"Viewed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_clicked\",\n" +
                        "          \"linkTitle\": \"Clicked\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_not_reachable\",\n" +
                        "          \"linkTitle\": \"Unreachable\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

            case "WEBHOOK":
                json += "{\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Webhook\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Stream webhook\",\n" +
                        "      \"mode\": 7,\n" +
                        "      \"sourceSvg\": \"/images/journey/webhook-icon.svg?v=d059f9c04ef9db693e60fa97a1066b05\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/webhook-icon-colored.svg?v=fc660210120fda7449db576e5c41872b\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"offline\",\n" +
                        "      \"builder\": \"webhook\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_sent\",\n" +
                        "          \"linkTitle\": \"Sent\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

            case "INAPP":
                json += "{\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"In-app\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Show in-app\",\n" +
                        "      \"mode\": 3,\n" +
                        "      \"sourceSvg\": \"/images/journey/mobile-inapp-icon.svg?v=6a1a7e64d3d9e35e326557f1d160aabb\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/mobile-inapp-icon-colored.svg?v=a81f85e58099c0d2bbdc946b989ca871\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 16,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"online\",\n" +
                        "      \"builder\": \"inapp\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_viewed\",\n" +
                        "          \"linkTitle\": \"Viewed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_clicked\",\n" +
                        "          \"linkTitle\": \"Clicked\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

            case "WEBPOPUP":
                json += "{\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Web Pop-up\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Show web pop-up\",\n" +
                        "      \"mode\": 4,\n" +
                        "      \"sourceSvg\": \"/images/journey/web-popups-icon.svg?v=90823c1952112fc0c752851fbb06db5f\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/web-popups-icon-colored.svg?v=5618fe17d0bc27b5e62bb4911ea923b5\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 16\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"online\",\n" +
                        "      \"builder\": \"web-popup\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_sent\",\n" +
                        "          \"linkTitle\": \"Sent\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_viewed\",\n" +
                        "          \"linkTitle\": \"Viewed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_clicked\",\n" +
                        "          \"linkTitle\": \"Clicked\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

            case "INBOX":
                json += "{\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Inbox\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Show Inbox\",\n" +
                        "      \"mode\": 9,\n" +
                        "      \"sourceSvg\": \"/images/journey/mobile-inapp-icon.svg?v=6a1a7e64d3d9e35e326557f1d160aabb\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/mobile-inapp-icon-colored.svg?v=a81f85e58099c0d2bbdc946b989ca871\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 16,\n" +
                        "        \"height\": 18\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"offline\",\n" +
                        "      \"builder\": \"notificationinbox\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_sent\",\n" +
                        "          \"linkTitle\": \"Sent\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_viewed\",\n" +
                        "          \"linkTitle\": \"Viewed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_clicked\",\n" +
                        "          \"linkTitle\": \"Clicked\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

            case "EXITINTENT":
                json += "{\n" +
                        "      \"key\": \""+actualNodeName+"\",\n" +
                        "      \"name\": \"Exit Intent\",\n" +
                        "      \"category\": \"message\",\n" +
                        "      \"canvasName\": \"Show exit intent pop-up\",\n" +
                        "      \"mode\": 4,\n" +
                        "      \"sourceSvg\": \"/images/journey/web-exit-intent.svg?v=b34c88cff707b79891d8060095426665\",\n" +
                        "      \"sourceSvgColored\": \"/images/journey/web-exit-intent-colored.svg?v=5f82530651746a48a56b9dc7cafbc894\",\n" +
                        "      \"sourceSvgDesiredSize\": {\n" +
                        "        \"class\": \"go.Size\",\n" +
                        "        \"width\": 18,\n" +
                        "        \"height\": 16\n" +
                        "      },\n" +
                        "      \"content_name\": \"Untitled message\",\n" +
                        "      \"delayKey\": \"online\",\n" +
                        "      \"builder\": \"web-exitintent\",\n" +
                        "      \"allowedLinks\": [\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_sent\",\n" +
                        "          \"linkTitle\": \"Sent\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_viewed\",\n" +
                        "          \"linkTitle\": \"Viewed\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"linkKey\": \"message_clicked\",\n" +
                        "          \"linkTitle\": \"Clicked\"\n" +
                        "        }\n" +
                        "      ],\n" +
                        "      \"loc\":\"" + x + " " + y +
                        "\"    },";
                break;

        }

        if (nodeCount != 4) {
            x += 214;
        } else {
            x = 0;
            y += 94;
            nodeCount = 0;
        }

        nodeCount++;

        return json;
    }

    /***
     * This method creates nodes which are in ExitModel(Exit Nodes) of the Journey.
     * @param nodeName - name of the Node to be created,
     *                  If there are more than one node to be created of the same name in the whole journey then append
     *                  the number to the nodeName.
     *                  Eg: For more than one action Nodes, nodeName would be, Action, Action2,Action3 and so on
     */
    private void getJsonBodyForExitModel(String nodeName){

        exitModelJson+=getJsonBody(nodeName);

    }


    public String createAndLinkNode(Quartet<String,String,String,String>...NodeDetails){

        if(NodeDetails[0].getValue(0).toString().contains(LinkNodeNames.values().toString())){
            //TODO :handling first node is always segment
        }

        for (Quartet<String,String,String,String> node:NodeDetails) {
            boolean isExitNode = node.contains("");
            if (!isExitNode) {
                int nodeCounter = 0;
                for (int i = 0; i < node.getSize() && nodeCounter < 2; i++, nodeCounter++) {
                    if (!createdNodes.contains(node.getValue(i).toString())) {
                        createdNodes.add(node.getValue(i).toString());
                        createNodeJson+=getJsonBody(node.getValue(i).toString());
                        if (firstNode == false) {
                            createNodeJson = createNodeJson.replace("isEntry\": false", "isEntry\": true");
                            firstNode = true;
                        }
                    }

                }
                if (createdNodes.size() > 1 && createdNodes.contains(node.getValue(1).toString())) {
                    linkNodes(node);
                }

            } else{
                getJsonBodyForExitModel(node.getValue(0).toString());

            }
        }


        createNodeJson = "\n" +
                "  \"class\": \"go.GraphLinksModel\",\n" +
                "  \"nodeDataArray\": [" + createNodeJson;
        createNodeJson = createNodeJson.substring(0, createNodeJson.length() - 2) + "}]";

        linkDataNodeJson = "\"linkDataArray\": ["+ linkDataNodeJson;
        linkDataNodeJson = linkDataNodeJson.substring(0, linkDataNodeJson.length() - 2) + "}],";

        if(exitModelJson.length()>1) {
            exitModelJson = exitModelJson.replaceAll("isEntry\": false", "isExit\": true");
            exitModelJson = exitModelJson.substring(0, exitModelJson.length() - 2) + "}";
            exitModelJson = "\"exitModel\": {\n" +
                    "    \"class\": \"go.GraphLinksModel\",\n" +
                    "    \"nodeDataArray\": [" + exitModelJson + "],\n" +
                    "    \"linkDataArray\": [\n" +
                    "      \n" +
                    "    ]\n" +
                    "  }";
        }else{
            exitModelJson="\"exitModel\": {\n" +
                    "    \"class\": \"go.GraphLinksModel\",\n" +
                    "    \"nodeDataArray\": [\n" +
                    "      \n" +
                    "    ],\n" +
                    "    \"linkDataArray\": [\n" +
                    "      \n" +
                    "    ]\n" +
                    "  }";
        }

        finalJourneyJson= "{"+createNodeJson +","+ linkDataNodeJson +exitModelJson+"}";

        return finalJourneyJson;

    }

    private void linkNodes(Quartet<String, String, String, String> linkData){
        String name=null;
        int delayInMin=0;
        String delayText="";
        String [] tempArr=linkData.getValue3().split("-");

        switch (tempArr[1].toLowerCase()) {

            case "hours":
                delayInMin = Integer.parseInt(tempArr[0]) * 60;
                delayText = "Wait for " + tempArr[0] + " hours";
                break;

            case "days":
                delayInMin = (Integer.parseInt(tempArr[0]) * 24) * 60;
                delayText = " Wait for " + tempArr[0] + " days";
                break;

            case "minutes":
                delayInMin = Integer.parseInt(tempArr[0]);
                if(delayInMin==0){
                    delayText="Immediately";
                }else{
                    delayText = "Wait for " + tempArr[0] + " minutes";
                }
                break;
        }

        for (ActionToPerform str: ActionToPerform.values()){
            if (str.toString().equals(linkData.getValue(2).toString())){
                name=str.name();
                break;
            }
        }

        String status=linkData.getValue(2).toString();

        linkDataNodeJson +="{\n" +
                "  \"from\": \"" +linkData.getValue(0).toString()+"\",\n" +
                "  \"to\": \"" +linkData.getValue(1).toString()+"\",\n" +
                "  \"toSpot\": {\n" +
                "    \"class\": \"go.Spot\",\n" +
                "    \"enum\": \"NotRightSide\"\n" +
                "  },\n" +
                "  \"fromSpot\": {\n" +
                "    \"class\": \"go.Spot\",\n" +
                "    \"enum\": \"AllSides\"\n" +
                "  },\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"status\": \""+status+"\",\n" +
                "  \"delayText\": \""+delayText+"\",\n" +
                "  \"delayMinutes\": "+delayInMin+",\n" +
                "  \"initialTitle\": \"Immediately\",\n" +
                "  \"hardDays\": false,\n" +
                "  \"enabled\": false,\n" +
                "  \"formatter\": \"formatFor24HoursAbsoluteTime\",\n" +
                "  \"timeToSend\": \"1000\",\n" +
                "  \"type\": \"24HoursThenAbsoluteTime\"\n" +
                "},";
    }


//    public static void fillJourneyDetails(String csvKey){
//
//        System.out.println(journeyCSVMap);
//        for(String eachNode : journeyCSVMap.keySet()){
//            if(eachNode.contains(csvKey)) {
//                String nodeName = eachNode.split(" ")[1];
//                String actualNodeName = nodeName.replaceAll("[0-9]", "");
//                switch (actualNodeName.toLowerCase()) {
//                    case "action":
//                        Action action = new Action();
//                        if (journeyCSVMap.get(eachNode).containsKey(nodeName + ".startDate") &&
//                                Objects.nonNull(journeyCSVMap.get(eachNode).get(nodeName + ".startDate")) &&
//                                journeyCSVMap.get(eachNode).get(nodeName + ".startDate") != "") {
//                            action.getWhen().setJourneyStartTime(journeyCSVMap.get(eachNode).get(nodeName + ".startDate"));
//                        }
//                        wholeJourney.put(nodeName, action);
//                        break;
//
//                }
//            }
//        }
//
//    }
//
//    public void updateJourney(){
//        //switch case for nodes then
//        for(String nodeName : wholeJourney.keySet()){
//
//            String actualNodeName = nodeName.replaceAll("[0-9]", "");
//            switch (actualNodeName.toLowerCase()){
//                case "action":
//                    Action action = (Action)wholeJourney.get(nodeName);
//                    clickOnNode(nodeName);
////                    action.setActionPage(driver);
//
//            }
//
//        }
//
//    }

    public void clickOnNode(String node){

        System.out.println("Comes in clickOnNode");
    }


//    public static void fillJourneyDetails(String csvKey){
//
//        for(String eachNode : journeyCSVMap.keySet()){
//            if(eachNode.contains(csvKey)) {
//                String nodeName = eachNode.split(" ")[1];
//                String actualNodeName = nodeName.replaceAll("[0-9]", "");
//                switch (actualNodeName.toLowerCase()) {
//                    case "action":
//                        Action action = new Action();
//                        if (journeyCSVMap.get(eachNode).containsKey(nodeName + ".startDate") &&
//                                Objects.nonNull(journeyCSVMap.get(eachNode).get(nodeName + ".startDate")) &&
//                                journeyCSVMap.get(eachNode).get(nodeName + ".startDate") != "") {
//                            action.getWhen().setJourneyStartTime(journeyCSVMap.get(eachNode).get(nodeName + ".startDate"));
//                        }
//                        wholeJourney.put(nodeName, action);
//                        break;
//
//                }
//            }
//        }
//
//    }
//
//    public void updateJourney(){
//        //switch case for nodes then
//        for(String nodeName : wholeJourney.keySet()){
//
//            String actualNodeName = nodeName.replaceAll("[0-9]", "");
//            switch (actualNodeName.toLowerCase()){
//                case "action":
//                    Action action = (Action)wholeJourney.get(nodeName);
//                    action.setActionPage(driver);
//
//            }
//
//        }
//
//    }

    public void publishJourney(String journeyName){
        SeleniumUtils.waitAndClick(driver,journeyPublishBtn);
//        journeyPublishBtn.click();
        SeleniumUtils.waitForElementToLoad(driver,journeyNameTextBox);
        SeleniumUtils.enterInputText(driver,journeyNameTextBox,journeyName);
        SeleniumUtils.pause(1);//dont remove this wait it is required
        SeleniumUtils.waitAndClick(driver,saveJourneyBtn);
    }

    public void filterJourneyPage(){
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+"Status']")));
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+"Status']/..//ul//label[text()='Stopped']")));
        SeleniumUtils.waitAndClick(driver,driver.findElement(By.xpath(DIV_CLASS_FILTERBTN_AND_TEXT+"Status']/..//a")));
        SeleniumUtils.pause(1);//this wait is required dont remove it
    }

    public void clickShowJourneyAction(String journeyName){
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//*[@data-original-title='" + journeyName + "']/../..//div[@data-original-title='Show journey actions']")),10);
        WebElement element=driver.findElement(By.xpath("//*[@data-original-title='" + journeyName + "']/../..//div[@data-original-title='Show journey actions']"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
    }

}
