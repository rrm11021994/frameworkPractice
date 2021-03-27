package com.clevertap.api;

import com.clevertap.BaseTest;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;
import org.javatuples.Triplet;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.clevertap.utils.RestApiUtil.ApiUtility.*;


public class TestApi extends BaseTest {

    @Test//normal key value
    public void checkForJson1(){

        Reporter.log("jahsbdhjasb",true);

        String jsonBody = "{\n" +
                "    \"fruit\": \"Apple\",\n" +
                "    \"size\": \"Large\",\n" +
                "    \"color\": \"Red\"\n" +
                "}";

        System.out.println(jsonBody);
        System.out.println(getJsonBody(getJsonObject(jsonBody), "fruit", "mango").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "size", "medium").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "color", "yellow").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "seasonal", "yes").toString());
    }

    @Test//key within key then value
    public void checkForJson2(){

        String jsonBody = "{\n"+
                "\"employee\":{ \"name\":\"John\", \"age\":30, \"city\":\"New York\" }\n"+
                "}";

        System.out.println(jsonBody);
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/name", "Jane").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/age", 45).toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/city", "mumbai").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/organization", "CT").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "isHuman", true).toString());

    }

    @Test//key within key within key then value
    public void checkForJson3(){

        String jsonBody = "{\n" +
                "  \"employee\": {\n" +
                "    \"name\": \"John\",\n" +
                "    \"birth\": {\n" +
                "      \"city\": \"mumbai\",\n" +
                "      \"pincode\": 111111,\n" +
                "      \"NRI\":false\n" +
                "    }\n" +
                "  }\n" +
                "}";

        System.out.println(jsonBody);
//        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/birth/city", "kalyan").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/birth/pincode", 000000).toString());
//        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/birth/NRI", true).toString());
//        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/birth/NRI", 'Y').toString());

    }

    @Test//direct array with direct values no json object
    public void checkForJson4(){

        String jsonBody = "{\n" +
                "  \"Address\": [\n" +
                "    \"Office1\",\n" +
                "    \"Office2\"\n" +
                "  ],\n" +
                "  \"employee\": {\n" +
                "    \"name\": \"John\",\n" +
                "    \"birth\": {\n" +
                "      \"city\": \"mumbai\",\n" +
                "      \"pincode\": 111111,\n" +
                "      \"NRI\": false\n" +
                "    }\n" +
                "  }\n" +
                "}";
        System.out.println(jsonBody);
        //this method will work but in json file we will have to keep the jsonArrays as empty eg : Address:[]
        //will work also if we dont keep it empty but it will just keep on adding values and not remove any value
        System.out.println(getJsonBody(getJsonObject(jsonBody), "Address[0]", "kalyan").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "Address[0]", 22).toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "Address[0]", false).toString());


    }

    @Test//array within jsonObject direct values
    public void checkForJson5(){

        String jsonBody = "{\n" +
                "  \"employee\": {\n" +
                "    \"name\": \"John\",\n" +
                "    \"birth\": {\n" +
                "      \"city\": \"mumbai\",\n" +
                "      \"pincode\": 111111,\n" +
                "      \"NRI\": false\n" +
                "    },\n" +
                "    \"MobileNo\": [\n" +
                "      \"123\",\n" +
                "      \"456\",\n" +
                "      \"789\"\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        System.out.println(jsonBody);
        //this method will work but in json file we will have to keep the jsonArrays as empty eg : MobileNo:[]
        //will work also if we dont keep it empty but it will just keep on adding values and not remove any value
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/MobileNo[0]", "999").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/MobileNo[0]", 999).toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employee/MobileNo[0]", true).toString());


    }

    @Test//array having jsonObjects
    public void checkForJson6(){

        String jsonBody = "{\n" +
                "  \"employess\": [\n" +
                "    {\n" +
                "      \"employee\": {\n" +
                "        \"name\": \"John\",\n" +
                "        \"birth\": {\n" +
                "          \"city\": \"mumbai\",\n" +
                "          \"pincode\": 111111,\n" +
                "          \"NRI\": false\n" +
                "        },\n" +
                "        \"MobileNo\": [\n" +
                "          \"123\",\n" +
                "          \"456\",\n" +
                "          \"789\"\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"employee\": {\n" +
                "        \"name\": \"Jane\",\n" +
                "        \"birth\": {\n" +
                "          \"city\": \"SA\",\n" +
                "          \"pincode\": 0000,\n" +
                "          \"NRI\": true\n" +
                "        },\n" +
                "        \"MobileNo\": [\n" +
                "          \"3333\",\n" +
                "          \"21\",\n" +
                "          \"619\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        //this method will work but in json file we will have to keep the jsonArrays as empty
        // eg : MobileNo:[](in case where jsonArray is having only values)
        //will work also if we dont keep it empty but it will just keep on adding values and not remove any value
        System.out.println(jsonBody);
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employess[0]/employee/name", "Mohsin").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employess[0]/employee/MobileNo[0]", "MohsinKaNumber").toString());
        System.out.println(getJsonBody(getJsonObject(jsonBody), "employess[1]/employee/birth/NRI", false).toString());

    }

    //test case with Json having a JasonArray inside it and array also contains jsonObject eg "Organiation/Employees[0]/name"
    @Test
    public void checkForJson7(){

        String jsonBody = "{\n" +
                "  \"Clevertap\": {\n" +
                "    \"employess\": [\n" +
                "      {\n" +
                "        \"employee\": {\n" +
                "          \"name\": \"John\",\n" +
                "          \"birth\": {\n" +
                "            \"city\": \"mumbai\",\n" +
                "            \"pincode\": 111111,\n" +
                "            \"NRI\": false\n" +
                "          },\n" +
                "          \"MobileNo\": [\n" +
                "            \"123\",\n" +
                "            \"456\",\n" +
                "            \"789\"\n" +
                "          ]\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"employee\": {\n" +
                "          \"name\": \"Jane\",\n" +
                "          \"birth\": {\n" +
                "            \"city\": \"SA\",\n" +
                "            \"pincode\": 0000,\n" +
                "            \"NRI\": true\n" +
                "          },\n" +
                "          \"MobileNo\": [\n" +
                "            \"3333\",\n" +
                "            \"21\",\n" +
                "            \"619\"\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        //this method will work but in json file we will have to keep the jsonArrays as empty
        // eg : MobileNo:[](in case where jsonArray is having only values)
        //will work also if we dont keep it empty but it will just keep on adding values and not remove any value
//        System.out.println(jsonBody);
//        System.out.println(getJsonBody(getJsonObject(jsonBody), "Clevertap/employess[0]/employee/name", "Mohsin").toString());
//        System.out.println(getJsonBody(getJsonObject(jsonBody), "Clevertap/employess[1]/employee/MobileNo[0]", "MohsinKaNumber").toString());

//        System.out.println(getJsonBody("test",
//                "Clevertap/employess[0]/employee/name", "Mohsin","Clevertap/employess[1]/employee/MobileNo[0]", "MohsinKaNumber",
//                "Clevertap/employess[0]/employee/birth/NRI", true,"Clevertap/employess[1]/employee/birth/city", "kalyan"));

    }

    @Test
    public void uploadEvent(){
        Map<String,String> eventData=new HashMap<>();
        eventData.put("langauge","english,french");
        eventData.put("productName","Shoes");
        Response res=apiLibrary.uploadEventProperties(Triplet.with("manasa@clevertap.com","test-eventName1",eventData),Triplet.with("gaurav.yadav@clevertap.com","test 77",eventData));
        System.out.println("response is : "+res.asString());
    }

    @Test
    public void createEmailCampaign(){

//        Response createEmailCampaignRes=apiLibrary.createCampaign("CampaignJson/EmailCampaign","where/from",getOneMothBackDate(),"where/to",getCurrentDateAsPerPostmanBody(),"name","Gaurav Test 4");
//        JsonObject jsonObject=getJsonObjectFromJsonFile("CampaignJson/SmsCampaign");
//        System.out.println(apiLibrary.removeKey(jsonObject,"where"));

//        System.out.println(getJsonBody(jsonObject,"content/body","meri body"));

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/SmsCampaign");



    }

}
