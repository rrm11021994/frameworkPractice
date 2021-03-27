package com.clevertap.api.campaign;

import com.clevertap.BaseTest;
import com.clevertap.utils.Data;

import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.util.Set;

import static com.clevertap.utils.RestApiUtil.ApiUtility.*;
import static com.clevertap.utils.RestApiUtil.ApiUtility.getMemberValueAsString;

public class MobilePushCampaignTests extends BaseTest {

    private String campaignName;
    private int currentDate;//currentDate in YYYYMMdd format
    private int previousDate; // currentDate - 10 days in YYYYMMdd format


    @BeforeClass
    public void beforeClass(){

        Response profileUploadRes=responseFactory.uploadEventProperties(getJsonObjectFromJsonFile("ProfileJson/UploadProfile").toString());
        Assert.assertEquals(getMemberValueAsString(profileUploadRes,"status"),"success","Failed to upload user");

        currentDate = Integer.parseInt(addDateToCurrentDate(0,"YYYYMMdd"));
        previousDate = Integer.parseInt(addDateToCurrentDate(-10,"YYYYMMdd"));
    }

    @BeforeMethod
    public void beforeMethod(){

        campaignName = Data.randomAlphaNumeric("AutomationMobilePushCampaign",5);

    }

    @Test(description = "verify only estimates should be shown and campaign should not created when 'estimate_only' key is true")
    public void verifyCampShouldNotBeCreatedWhenEstimateIsTrue(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"control_group","where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate
                            ,"estimate_only",true);

        Response listOfCampsBefore = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        Assert.assertNull(getMemberValue(campRes,"id"),
                "Since campaign is not created, response should not contain id as key");

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
        "Response should contain estimates and the estimates object should not be null ie it should contain at-least one key");

        Response listOfCampsAfter = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertEquals(listOfCampsBefore.jsonPath().getList("targets.id").size()
                ,listOfCampsAfter.jsonPath().getList("targets.id").size(),
                "Number of campaigns before and after api call should be same");
        Assert.assertFalse(listOfCampsAfter.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was present in getListOfCampaign Api response");


    }

    @Test(description = "verify user should be able to create campaign without passing custom control group and estimate only key as false")
        public void createCampWithoutCCGAndEstimateAsFalse(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"control_group","where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate);
        
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName));
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId));
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","failed to stop campaign");

        apiLibrary.getCampaignReport(campId);
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName,"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","campaign status not changed to stopped in campList Response");
    }

    @Test(description = "verify when 'skip_estimate' key is true campaign should be created without calculating estimation")
    public void createCampWithSkipEstimateAsTrue(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"control_group");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"skip_estimate",true);
        
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertEquals(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size(),0,
                "Response should contain estimates object which should be empty ie it should not contain any key as skip_estimates was true");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

        apiLibrary.getCampaignReport(campId);
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName,"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","campaign status not changed to stopped in campList response");

    }

    @Test(description = "verify when 'common_profile_properties' in where is present and 'skip_estimate' key is not present in JSON,\n" +
            "campaign is created and estimation is providied in the create campaign api response")
    public void createCampWithCommonPropsInWhereAndWithoutSkipEstimateKeyInJSON(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"skip_estimate");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates object which should not be empty ie it should not contain any key " +
                        "as skip_estimate was not present in JSON and by default it is false");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName));
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId));
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

        apiLibrary.getCampaignReport(campId);
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName,"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","Campaign status was not changed to stopped in campaing List Response");

    }


    @Test(description = "verify user should be able to create campaign with multiple profile properties")
    public void verifyCampShouldBeCreatedWithMultipleProfileFields(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);

        String jsonBody=getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,
                "where/common_profile_properties/profile_fields[1]/name","test.dot7","where/common_profile_properties/profile_fields[1]/operator","equals"
                ,"where/common_profile_properties/profile_fields[1]/value","test.value");
        System.out.println(jsonBody);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify campaign is created when 'common_profile_properties' is not present in where section and 'respect_throttle' key is true")
    public void createCampWithoutCommonPropInWhereAndRespectThrottleAsTrue(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates object which should not be empty ie it should not contain any key " +
                        "as skip_estimate was not present in JSON and by default it is false");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify campaign is created with an empty Object in where section ie no filtering is applied along with 'respect_frequency_caps' as true")
    public void createCampaignWithEmptyObjectInWhereAndRespectFrequencyCapsAsTrue(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties","where/from","where/to","where/event_name");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName);
        
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates object which should not be empty ie it should not contain any key " +
                        "as skip_estimate was not present in JSON and by default it is false");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");

    }

    @Test(description = "verify campaign is created with 'respect_frequency_caps' and 'respect_throttle' keys as false")
    public void createCampWithRespectFrequencyCapsAsFalseAndRespectThrottleAsFalse(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate
                            ,"respect_throttle",false,"respect_frequency_caps",false);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates object which should not be empty ie it should not contain any key " +
                        "as skip_estimate was not present in JSON and by default it is false");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");

    }

    //"respect_frequency_caps" ByDefault is true
    //"respect_throttle" ByDefault is false
    @Test(description = "verify campaign is created with 'respect_frequency_caps' and 'respect_throttle' keys are not present in JSON," +
            " By default behaviors : 'respect_frequency_caps'-> true & 'respect_throttle' -> false")
    public void createCampWithOutRespectFrequencyCapsAndRespectThrottleKeysInJson(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties","respect_throttle","respect_frequency_caps");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate);
        
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates object which should not be empty ie it should not contain any key " +
                        "as skip_estimate was not present in JSON and by default it is false");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");

    }

    @Test(description = "verify campaign can be created when 'system_control_group_include' key is true in JSONBody")
    public void createCampWithSystemControlGroupIncludeKeyAsTrue(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"system_control_group_include",true);
        
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates object which should not be empty ie it should not contain any key " +
                        "as skip_estimate was not present in JSON and by default it is false");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");

    }

    @Test(description = "verify campaign can be created when 'system_control_group_include' key is false in JSONBody")
    public void createCampWithSystemControlGroupIncludeKeyAsFalse(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"system_control_group_include",false);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates object which should not be empty ie it should not contain any key " +
                        "as skip_estimate was not present in JSON and by default it is false");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");

    }

    @Test(description = "verify campaign can be created when 'system_control_group_include' and 'send_to_all_devices' keys not present in JSONBody")
    public void createCampWithSystemControlGroupIncludeKeyAndSendToAllDevicesNotPresentInJsonBody(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"system_control_group_include","send_to_all_devices");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"",false);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates object which should not be empty ie it should not contain any key " +
                        "as skip_estimate was not present in JSON and by default it is false");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");

    }

    @Test(description = "verify campaign is created with custom control group")
    public void verifyUserShouldBeAbleToCreateCampaignWithCustomControlGroup(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"control_group/percentage");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"control_group/type","custom","control_group/name","Test-Automation");

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify campaign should not be created when custom control group with passed value does not exist")
    public void verifyCampShouldNotBeCreatedWhenCustomControlGroupProvidedIsNotPresent(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"control_group/percentage");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"control_group/type","custom","control_group/name","RamndomNm77");

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),"No custom control group with name RamndomNm77 is defined","wrong message was given in response");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertFalse(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
    }

//    @Test(description = "verify Campaign should not be created when value for where.from is passed as future date")
    public void verifyCampShouldNotBeWhenWrongValuePassedForEventFromIsFutureDate(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);

        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/to",Integer.parseInt(addDateToCurrentDate(10,"YYYYMMdd")));

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertFalse(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");

    }

    @Test(description = "verify Campaign should not be created when value for where.from is greater than where.to")
    public void verifyCampShouldNotBeCreatedWhenFromDateIsGreaterToDate(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",Integer.parseInt(addDateToCurrentDate(10,"YYYYMMdd")));

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),"Query parse failure - The from date cannot be after the to date","wrong message was given in response");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertFalse(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
    }

    @Test(description = "verify user should be able to create campaign when only mandatory values are passed")
    public void verifyCampCanBeCreatedWithOnlyMandatoryField(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"estimate_only","where/common_profile_properties",
                "where/from","where/to","where/event_name","respect_frequency_caps","system_control_group_include",
                "control_group","respect_throttle","skip_estimate");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campaign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify campaign cannot be created without mandatory value",dataProvider = "Mandatory Values")
    public void verifyCampCannotBeCreatedWithoutMandatoryValues(String keyToRemove,String errorMessage){
        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"estimate_only","where/common_profile_properties","where/from",
                "where/to","where/event_name","respect_frequency_caps","system_control_group_include","control_group",
                "respect_throttle","skip_estimate",keyToRemove);

        String jsonBody;
        if(keyToRemove!="name") {
            jsonBody = getJsonBody(jsonObject, "name", campaignName);
        }else {
            jsonBody=jsonObject.toString();
        }
        
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),errorMessage,"wrong message was given in response");
    }

    @DataProvider(name = "Mandatory Values")
    public Object[][] mandatoryValuesDataProvider() {
        return new Object[][]{
                {"name", "Field name is empty"},
                {"content","Field title is empty (inside content)"},
                {"when","Field when is empty"},
                {"where","Query specified in where failed to parse"}
        };
    }

    @Test(description = "verify campaign can be created with future data and 'skip_estimate' key as false")
    public void createCampWithStartTimeAsLaterAndSkipEstimateAsFalse(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"when",addDateToCurrentDate(10,"YYYYMMdd HH:mm"));
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        Assert.assertTrue(getMemberValue(campRes, "estimates").getAsJsonObject().keySet().size()>0,
                "Response should contain estimates with key as 'estimate_key'");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,Integer.parseInt(addDateToCurrentDate(10,"YYYYMMdd")));
        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"pending","status of campaign was not changed to stopped");
        Assert.assertEquals(getMemberValue(campJsonObject,"id").getAsInt(),campId,"wromg value was present for campId");

        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,Integer.parseInt(addDateToCurrentDate(10,"YYYYMMdd")));

        campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
        Assert.assertEquals(getMemberValue(campJsonObject,"id").getAsInt(),campId,"wrong value was present for campId");
    }

    @Test(description = "verify campaign can be created with 'send_to_all_devices' key as false and control_group with type campaign")
    public void createCampWithSendToAllDevicesAsFalseAndWithCampControlGroup(){


        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate);
        
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName));
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId));
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","failed to stop campaign");

        apiLibrary.getCampaignReport(campId);
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName,"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","campaign status not changed to stopped in campList Response");

    }

    @Test(description = "verify campaign can be created with 'send_to_all_devices' key as true")
    public void createCampWithSendToAllDevicesAsTrue(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"send_to_all_devices",true);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName));
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId));
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","failed to stop campaign");

        apiLibrary.getCampaignReport(campId);
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName,"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","campaign status not changed to stopped in campList Response");

    }

    @Test(description = "verify campaign can be created for only IOS devices")
    public void createCampForOnlyIOSDevices(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"devices");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"devices[0]","ios");

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");

        Set<String> estimatesKeySet = getMemberValue(campRes, "estimates").getAsJsonObject().keySet();
        Assert.assertEquals(estimatesKeySet.size(),1,
                "Response should contain estimates and the estimates object should have only one key ie ios");
        Assert.assertTrue(estimatesKeySet.contains("ios"),
                "Response should contain estimates and the estimates object should have only one key ie ios");

        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName));
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId));
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","failed to stop campaign");

        apiLibrary.getCampaignReport(campId);
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName,"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","campaign status not changed to stopped in campList Response");

    }

    @Test(description = "verify campaign can be created for only android devices")
    public void createCampForOnlyAndroidDevices(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"devices");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"devices[0]","android");

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        Set<String> estimatesKeySet = getMemberValue(campRes, "estimates").getAsJsonObject().keySet();
        Assert.assertEquals(estimatesKeySet.size(),1,
                "Response should contain estimates and the estimates object should have only one key ie android");
        Assert.assertTrue(estimatesKeySet.contains("android"),
                "Response should contain estimates and the estimates object should have only one key ie android");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName));
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId));
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","failed to stop campaign");

        apiLibrary.getCampaignReport(campId);
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName,"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","campaign status not changed to stopped in campList Response");

    }

    @Test (description = "verify campaign should not be created when Notification channel key ('wzrk_cid') is not present in JSON")
    public void createCampWithoutNotificationChannelKeyInJson(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,/*"name",campaignName,"where/from",previousDate,"where/to",currentDate,*/"content/platform_specific/android/wzrk_cid",null);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),"Notification channel is required for devices having Android 8.0 or above");

    }

    @Test (description = "verify campaign should not be created with random Notification channel key ('wzrk_cid')")
    public void createCampWithRandomNotificationChannelKeyWhichIsNotPresent(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/MobilePush");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"content/platform_specific/android/wzrk_cid","RandomNotifIDjansdan");
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),"Invalid Notification Channel ID");

    }
}



